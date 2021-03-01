package com.platform.modules.app.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.platform.common.utils.*;
import com.platform.modules.ad.entity.*;
import com.platform.modules.ad.service.*;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysConfigService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service("payService")
public class PayService {
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private AdOrderService orderService;
    @Autowired
    private AdOrderTissueService adOrderTissueService;
    @Autowired
    private AdMachineStockService adMachineStockService;
    @Autowired
    private AdTissueMachineService adTissueMachineService;
    @Autowired
    private AdMachinePushService adMachinePushService;
    @Value("${wx.pay.baseNotifyUrl}")
    private String baseNotifyUrl;
    @Value("${wx.pay.spbillCreateIp}")
    private String spbillCreateIp;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private AdAgentBalanceLogService adAgentBalanceLogService;
    @Autowired
    private MallAccountLogService accountLogService;
    @Autowired
    private AdRechargeLevelService adRechargeLevelService;
    @Autowired
    private MallUserService mallUserService;
    @Autowired
    private AdCouponService adCouponService;
    @Autowired
    private AdUserCouponService adUserCouponService;
    @Autowired
    private MallAccountLogService mallAccountLogService;

    /**
     * 商品统一下单请求
     *
     * @param loginUser
     * @param jsonParam
     * @return
     */
    public RestResponse payPrepay(MallUserEntity loginUser, JSONObject jsonParam) {
        String orderId = jsonParam.getString("orderId");
        String tradeType = jsonParam.getString("tradeType");
        AdOrderEntity orderInfo = orderService.getById(orderId);
        if (null == orderInfo) {
            return RestResponse.error("订单已取消！");
        }
        if (!loginUser.getId().equals(orderInfo.getUserId())) {
            return RestResponse.error("非法操作！");
        }
        if (orderInfo.getOrderStatus().equals(Constant.OrderStatus.YZF.getValue())) {
            return RestResponse.error("订单已支付，请不要重复操作！");
        }

        StringBuilder body = new StringBuilder();
        if(orderInfo.getOrderType() != 3) {
            Map<String, Object> orderGoodsParam = new HashMap<>(2);
            orderGoodsParam.put("orderId", orderId);
            //订单的商品
            List<AdOrderTissueEntity> orderGoods = adOrderTissueService.queryAll(orderGoodsParam);
            if (null != orderGoods && orderGoods.size() > 0) {
                for (AdOrderTissueEntity goodsVo : orderGoods) {
                    if (body.toString().getBytes(StandardCharsets.UTF_8).length < 90) {
                        body.append(goodsVo.getGoodsName()).append("*").append(goodsVo.getGoodsCount()).append("、");
                    } else {
                        body.append("...");
                    }
                }
                if (body.length() > 0) {
                    body = new StringBuilder(body.substring(0, body.length() - 1));
                }
            }
        } else {
            body.append("高级会员").append("*").append("1");
        }
        Integer fromType = orderInfo.getFromType();
        try {
            WxPayMpOrderResult data = null;
            if (ObjectUtils.equals(fromType, Constant.FromType.MP.getValue())
                    || ObjectUtils.equals(orderInfo.getFromType(), Constant.FromType.MA.getValue())) {
                //调用统一下单接口，并组装生成支付所需参数对象
                WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
                request.setBody(body.toString());
                request.setOutTradeNo(orderInfo.getOrderSn());
                //支付金额
                request.setTotalFee(BaseWxPayRequest.yuanToFen(orderInfo.getActualPrice().toString()));
                request.setSpbillCreateIp(spbillCreateIp);
                // 回调地址
                request.setNotifyUrl(baseNotifyUrl + "/app/pay/notify");
                // 交易类型APP
                request.setTradeType(tradeType);
                request.setOpenid(loginUser.getOpenId());
                try {
                    data = wxPayService.createOrder(request);
                } catch (WxPayException e) {
                    /**
                     * 参考:微信支付出现OUT_TRADE_NO_USED:商户订单号重复
                     *
                     * 场景：使用微信支付，在微信支付界面，由于余额不足等原因，没有进行支付并关闭了支付页面，此时订单状态为“待支付”，从业务角度来说，应该允许用户继续支付。但是再次支付时，微信接口返回“201 商户订单号重复”的错误提示。
                     * 解决办法：待支付的订单号（即商户订单号，out_trade_no），再次支付时，务必保持商品描述字段和上次请求的内容完全一致。
                     * 　　　　另外，如果价格改变，也不能重复提交，只能重新生成订单号，重新向微信发起支付请求
                     * 这里如果客户下单后点击支付然后取消付款，在联系客服后台修改价格，用户再次下单就会产生此错误
                     * 这里处理方式是在订单号头部添加XG
                     */
                    if (e.getErrCodeDes().contains("商户订单号重复")) {
                        orderInfo.setOrderSn("XG" + orderInfo.getOrderSn());
                        orderService.update(orderInfo);
                        request.setOutTradeNo("XG" + orderInfo.getOrderSn());
                        data = wxPayService.createOrder(request);
                    }
                }
            }
            return RestResponse.success().put("data", data);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.error("下单失败,error=" + e.getMessage());
        }
    }


    /**
     * 商品统一下单请求回调接口
     *
     * @param xmlData
     * @return
     */
    public String notify(String xmlData) {
        try {
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(xmlData);
            String resultCode = result.getResultCode();
            String outTradeNo = result.getOutTradeNo();
            if (resultCode.equalsIgnoreCase(Constant.FAIL)) {
                //订单编号
                log.error("订单" + outTradeNo + "支付失败");
                return setXml(Constant.SUCCESS, "OK");
            } else if (resultCode.equalsIgnoreCase(Constant.SUCCESS)) {
                //订单编号
                log.error("订单" + outTradeNo + "支付成功");
                // 业务处理
                AdOrderEntity orderInfo = orderService.getOne(new QueryWrapper<AdOrderEntity>().eq("ORDER_SN", outTradeNo));
                MallUserEntity loginUser = mallUserService.getById(orderInfo.getUserId());
                //微信支付
                if ((!orderInfo.getOrderType().equals(Constant.OrderType.GMHY.getValue())) && orderInfo.getPayType().equals(Constant.orderPayType.WXZF.getValue())) {
                    String machineId = orderInfo.getMachineId();
                    AdTissueMachineEntity machineInfo = adTissueMachineService.getById(machineId);
                    List<AdOrderTissueEntity> orderTissue = adOrderTissueService.list(new QueryWrapper<AdOrderTissueEntity>().eq("ORDER_ID", orderInfo.getId()));
                    // 已经支付,不再执行
                    if (orderInfo.getOrderStatus().equals(Constant.OrderStatus.YZF.getValue())) {
                        return setXml(Constant.SUCCESS, "OK");
                    }
                    int goodsTotal = 0;
                    System.out.println(orderInfo.getComsumePrice());
                    System.out.println(loginUser.getBalance());
                    //扣除余额
                    loginUser.setBalance((loginUser.getBalance().subtract(orderInfo.getComsumePrice())).setScale(2, BigDecimal.ROUND_HALF_UP));
                    mallUserService.updateById(loginUser);
                    //添加余额明细记录
                    MallAccountLogEntity accountLogEntity = new MallAccountLogEntity();
                    accountLogEntity.setAddTime(new Date());
                    accountLogEntity.setUserId(loginUser.getId());
                    accountLogEntity.setPrice(orderInfo.getComsumePrice());
                    accountLogEntity.setLogDesc("购买商品");
                    accountLogEntity.setType(2);
                    accountLogEntity.setOrderType(1);
                    accountLogEntity.setFromType(1);
                    accountLogEntity.setOrderSn(orderInfo.getOrderSn());
                    mallAccountLogService.save(accountLogEntity);

                    for (AdOrderTissueEntity e : orderTissue) {
                        AdMachineStockEntity one = adMachineStockService.getOne(new QueryWrapper<AdMachineStockEntity>().eq("MACHINE_ID", orderInfo.getMachineId())
                                .eq("GOODS_ID", e.getGoodsId())
                                .ne("STOCK", 0)
                                .orderByAsc("LEVEL")
                                .last("LIMIT 1"));
                        one.setStock(one.getStock() - e.getGoodsCount());
                        adMachineStockService.update(one);
                        goodsTotal += e.getGoodsCount();
                    }
                    //计算商品总数
                    machineInfo.setTissueNumber(machineInfo.getTissueNumber() - goodsTotal);
                    // 判断机柜纸巾数少于预警线
                    if (machineInfo.getTissueNumber() <= Integer.parseInt(sysConfigService.getValue("MACHINE_TISSUE_WARING"))) {
                        machineInfo.setStatus(Constant.MachineStatus.ZJYJ.getValue());
                        adTissueMachineService.sendMessage(machineInfo,Constant.MachineStatus.ZJYJ.getValue());
                    }
                    adTissueMachineService.update(machineInfo);
                    Date date = new Date();
                    //加入到机柜纸巾发放表
                    AdMachinePushEntity adMachinePushEntity = new AdMachinePushEntity();
                    adMachinePushEntity.setMachineSn(machineInfo.getSn());
                    adMachinePushEntity.setNumber(goodsTotal);
                    adMachinePushEntity.setOrderId(orderInfo.getId());
                    adMachinePushEntity.setCreatedTime(date);
                    adMachinePushService.add(adMachinePushEntity);

                    UpdateWrapper<AdOrderEntity> orderUpdateWrapper = new UpdateWrapper<>();
                    if (orderInfo.getOrderStatus().equals(Constant.OrderStatus.DFK.getValue())) {
                        orderUpdateWrapper.eq("ID", orderInfo.getId()).set("EXPIRE_TIME", null)
                                .set("ORDER_STATUS", Constant.OrderStatus.YZF.getValue()).set("PAY_TIME", date);
                        orderService.update(orderUpdateWrapper);
                    }
                    // 修改优惠券状态
                    if (StringUtils.isNotBlank(orderInfo.getCouponId())){
                        AdCouponEntity adCouponEntity = adCouponService.getById(orderInfo.getCouponId());
                        if (null != adCouponEntity && adCouponEntity.getStatus() == 1) {
                            AdUserCouponEntity userCouponEntity = adUserCouponService.getOne(new QueryWrapper<AdUserCouponEntity>()
                                    .eq("STATUS", 0)
                                    .eq("COUPON_ID", orderInfo.getCouponId())
                                    .eq("USER_ID", loginUser.getId())
                                    .last("LIMIT 1"));
                            if (userCouponEntity != null && userCouponEntity.getStatus() == 0) {
                                userCouponEntity.setStatus(1);
                                userCouponEntity.setUsedTime(new Date());
                                userCouponEntity.setOrderId(orderInfo.getId());
                                adUserCouponService.update(userCouponEntity);
                            }
                        }
                    }

                    String balancePrice = sysConfigService.getValue(Constant.AGENT_BALANCE_ORDER_PRICE);
                    //订单价格和代理获得收益的比例
                    BigDecimal balanceExchangePrice = new BigDecimal(balancePrice);
                    //收益
                    BigDecimal money = orderInfo.getTotalPrice().multiply(balanceExchangePrice);
                    //添加代理余额
                    SysUserEntity sysUserEntity = sysUserDao.queryById(machineInfo.getAgentId());
                    sysUserEntity.setBalance(sysUserEntity.getBalance().add(money));
                    sysUserDao.updateById(sysUserEntity);
                    //加入到流水表
                    AdAgentBalanceLogEntity adAgentBalanceLogEntity = new AdAgentBalanceLogEntity();
                    adAgentBalanceLogEntity.setAgentId(machineInfo.getAgentId());
                    adAgentBalanceLogEntity.setBalance(money);
                    adAgentBalanceLogEntity.setCreateTime(date);
                    adAgentBalanceLogEntity.setType(Constant.BalanceLogType.IN.getValue());
                    adAgentBalanceLogEntity.setOrderId(orderInfo.getId());
                    adAgentBalanceLogService.add(adAgentBalanceLogEntity);

                } else {
                    // 购买会员订单
                    // 已经支付,不再执行
                    if (orderInfo.getOrderStatus().equals(Constant.OrderStatus.YZF.getValue())) {
                        return setXml(Constant.SUCCESS, "OK");
                    }

                    // 获取下单的用户信息
                    MallUserEntity mallUserEntity = mallUserService.getOne(new QueryWrapper<MallUserEntity>().eq("ID", orderInfo.getUserId()));

                    // 给用户增加免费领取的纸巾数量
                    mallUserEntity.setUserLevelId(Constant.UserLevel.GJHY.getValue().toString());
                    mallUserEntity.setTissueCount(mallUserEntity.getTissueCount() + Integer.parseInt(sysConfigService.getValue("VIP_TISSUE_NUMBER")));
                    mallUserService.update(mallUserEntity);

                    // 更改订单信息
                    UpdateWrapper<AdOrderEntity> orderUpdateWrapper = new UpdateWrapper<>();
                    if (orderInfo.getOrderStatus().equals(Constant.OrderStatus.DFK.getValue())) {
                        orderUpdateWrapper.eq("ID", orderInfo.getId()).set("EXPIRE_TIME", null)
                                .set("ORDER_STATUS", Constant.OrderStatus.YZF.getValue()).set("PAY_TIME", new Date());
                        orderService.update(orderUpdateWrapper);
                    }
                }
            } else {
                log.error("订单处理异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setXml(Constant.SUCCESS, "OK");
    }



    /**
     * 余额支付（购买商品）
     *
     * @param loginUser
     * @param jsonParam
     * @return
     */
    public RestResponse buyByYue(MallUserEntity loginUser, JSONObject jsonParam) {
        String orderId = jsonParam.getString("orderId");
        AdOrderEntity orderInfo = orderService.getById(orderId);
        try {
            if(orderInfo.getPayType().equals(Constant.orderPayType.YEZF.getValue())) {
                MallUserEntity userEntity = mallUserService.getById(orderInfo.getUserId());
                // 余额
                if (userEntity.getBalance().subtract(orderInfo.getComsumePrice()).compareTo(BigDecimal.ZERO) > -1){
                    // 修改余额
                    userEntity.setBalance(userEntity.getBalance().subtract(orderInfo.getComsumePrice()));
                    mallUserService.update(userEntity);

                    //添加余额明细记录
                    MallAccountLogEntity accountLogEntity = new MallAccountLogEntity();
                    accountLogEntity.setAddTime(new Date());
                    accountLogEntity.setUserId(loginUser.getId());
                    accountLogEntity.setPrice(orderInfo.getComsumePrice());
                    accountLogEntity.setLogDesc("购买商品");
                    accountLogEntity.setType(2);
                    accountLogEntity.setOrderType(1);
                    accountLogEntity.setFromType(1);
                    accountLogEntity.setOrderSn(orderInfo.getOrderSn());
                    mallAccountLogService.save(accountLogEntity);

                    String machineId = orderInfo.getMachineId();
                    AdTissueMachineEntity machineInfo = adTissueMachineService.getById(machineId);
                    List<AdOrderTissueEntity> orderTissue = adOrderTissueService.list(new QueryWrapper<AdOrderTissueEntity>().eq("ORDER_ID", orderInfo.getId()));
                    // 已经支付,不再执行
                    if (orderInfo.getOrderStatus().equals(Constant.OrderStatus.YZF.getValue())) {
                        return RestResponse.success("支付成功");
                    }
                    int goodsTotal = 0;
                    for (AdOrderTissueEntity e : orderTissue) {
                        AdMachineStockEntity one = adMachineStockService.getOne(new QueryWrapper<AdMachineStockEntity>().eq("MACHINE_ID", orderInfo.getMachineId())
                                .eq("GOODS_ID", e.getGoodsId())
                                .ne("STOCK", 0)
                                .orderByAsc("LEVEL")
                                .last("LIMIT 1"));
                        one.setStock(one.getStock() - e.getGoodsCount());
                        adMachineStockService.update(one);
                        goodsTotal += e.getGoodsCount();
                    }
                    // 计算商品总数
                    machineInfo.setTissueNumber(machineInfo.getTissueNumber() - goodsTotal);
                    // 判断机柜纸巾数是否少于预警线
                    if (machineInfo.getTissueNumber() <= Integer.parseInt(sysConfigService.getValue("MACHINE_TISSUE_WARING"))) {
                        machineInfo.setStatus(Constant.MachineStatus.ZJYJ.getValue());
                        adTissueMachineService.sendMessage(machineInfo,Constant.MachineStatus.ZJYJ.getValue());
                    }
                    adTissueMachineService.update(machineInfo);
                    Date date = new Date();
                    // 加入到机柜纸巾发放表
                    AdMachinePushEntity adMachinePushEntity = new AdMachinePushEntity();
                    adMachinePushEntity.setMachineSn(machineInfo.getSn());
                    adMachinePushEntity.setNumber(goodsTotal);
                    adMachinePushEntity.setOrderId(orderInfo.getId());
                    adMachinePushEntity.setCreatedTime(date);
                    adMachinePushService.add(adMachinePushEntity);

                    UpdateWrapper<AdOrderEntity> orderUpdateWrapper = new UpdateWrapper<>();
                    if (orderInfo.getOrderStatus().equals(Constant.OrderStatus.DFK.getValue())) {
                        orderUpdateWrapper.eq("ID", orderInfo.getId())
                                .set("EXPIRE_TIME", null)
                                .set("ORDER_STATUS", Constant.OrderStatus.YZF.getValue())
                                .set("PAY_TIME", date);
                    }
                    orderService.update(orderUpdateWrapper);

                    String balancePrice = sysConfigService.getValue(Constant.AGENT_BALANCE_ORDER_PRICE);
                    // 订单价格和代理获得收益的比例
                    BigDecimal balanceExchangePrice = new BigDecimal(balancePrice);
                    // 收益
                    BigDecimal money = orderInfo.getTotalPrice().multiply(balanceExchangePrice);
                    // 添加代理余额
                    String userId = machineInfo.getAgentId();
                    SysUserEntity sysUserEntity = sysUserDao.queryById(userId);
                    sysUserEntity.setBalance(sysUserEntity.getBalance().add(money));
                    sysUserDao.updateById(sysUserEntity);


                    // 加入到流水表
                    AdAgentBalanceLogEntity adAgentBalanceLogEntity = new AdAgentBalanceLogEntity();
                    adAgentBalanceLogEntity.setAgentId(machineInfo.getAgentId());
                    adAgentBalanceLogEntity.setBalance(money);
                    adAgentBalanceLogEntity.setType(Constant.BalanceLogType.IN.getValue());
                    adAgentBalanceLogEntity.setCreateTime(date);
                    adAgentBalanceLogEntity.setOrderId(orderInfo.getId());
                    adAgentBalanceLogService.add(adAgentBalanceLogEntity);
                    // 修改优惠券状态
                    if (StringUtils.isNotBlank(orderInfo.getCouponId())){
                        AdCouponEntity adCouponEntity = adCouponService.getById(orderInfo.getCouponId());
                        if (null != adCouponEntity && adCouponEntity.getStatus() == 1) {
                            AdUserCouponEntity userCouponEntity = adUserCouponService.getOne(new QueryWrapper<AdUserCouponEntity>()
                                    .eq("STATUS", 0)
                                    .eq("COUPON_ID", orderInfo.getCouponId())
                                    .eq("USER_ID", loginUser.getId())
                                    .last("LIMIT 1"));
                            if (userCouponEntity != null && userCouponEntity.getStatus() == 0) {
                                userCouponEntity.setStatus(1);
                                userCouponEntity.setUsedTime(new Date());
                                userCouponEntity.setOrderId(orderInfo.getId());
                                adUserCouponService.update(userCouponEntity);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestResponse.success("支付成功！");
    }



    /**
     * 余额充值统一下单请求
     *
     * @param loginUser
     * @param jsonParam
     * @return
     */
    public RestResponse prepayYue(MallUserEntity loginUser, JSONObject jsonParam) {
        String rechargeId = jsonParam.getString("rechargeId");
        Integer fromType = jsonParam.getInteger("fromType");
        String tradeType = jsonParam.getString("tradeType");
        //获取充值档位详情信息
        AdRechargeLevelEntity rechargeLevelEntity = adRechargeLevelService.getById(rechargeId);
        String orderSn = StringUtils.transFromType(fromType) + StringUtils.generateOrderNumber();
        //添加余额明细记录
        MallAccountLogEntity accountLogEntity = new MallAccountLogEntity();
        accountLogEntity.setAddTime(new Date());
        accountLogEntity.setUserId(loginUser.getId());
        accountLogEntity.setPrice(rechargeLevelEntity.getBalance());
        accountLogEntity.setLogDesc("用户余额充值");
        accountLogEntity.setType(0);
        accountLogEntity.setOrderType(0);
        accountLogEntity.setFromType(fromType);
        accountLogEntity.setOrderSn(orderSn);
        accountLogEntity.setRechangeLevelId(rechargeId);
        accountLogService.save(accountLogEntity);

        //添加订单
        AdOrderEntity order = new AdOrderEntity();

        order.setOrderSn(orderSn);
        order.setFromType(1);
        order.setUserId(loginUser.getId());
        order.setOrderType(Constant.OrderType.YECZ.getValue());
        order.setOrderStatus(Constant.OrderStatus.DFK.getValue());
        order.setActualPrice(rechargeLevelEntity.getNeedPrice());
        order.setTotalPrice(rechargeLevelEntity.getNeedPrice());
        order.setCreateTime(new Date());
        orderService.add(order);

        try {
            WxPayMpOrderResult data = null;
            if (ObjectUtils.equals(accountLogEntity.getFromType(), Constant.FromType.MP.getValue())
                    || ObjectUtils.equals(accountLogEntity.getFromType(), Constant.FromType.MA.getValue())) {

                WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
                request.setBody("本次充值金额：" + rechargeLevelEntity.getBalance() + "元");
                request.setOutTradeNo(accountLogEntity.getOrderSn());
                //支付金额
                request.setTotalFee(BaseWxPayRequest.yuanToFen(rechargeLevelEntity.getNeedPrice().toString()));
                request.setSpbillCreateIp(spbillCreateIp);
                // 回调地址
                request.setNotifyUrl(baseNotifyUrl + "/app/pay/prepayYueNotify");
                // 交易类型APP
                request.setTradeType(tradeType);
                request.setOpenid(loginUser.getOpenId());

                //调用统一下单接口，并组装生成支付所需参数对象
                data = wxPayService.createOrder(request);
            }
            if (ObjectUtils.equals(accountLogEntity.getFromType(), Constant.FromType.QQ.getValue())) {
                //todo QQ钱包支付
            }
            return RestResponse.success().put("data", data);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.error("下单失败,error=" + e.getMessage());
        }
    }

    /**
     * 余额充值统一下单请求回调接口
     *
     * @param xmlData
     * @return
     */
    public String prepayYueNotify(String xmlData) {
        try {
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(xmlData);
            String resultCode = result.getResultCode();
            //订单编号
            String outTradeNo = result.getOutTradeNo();
            if (resultCode.equalsIgnoreCase(Constant.FAIL)) {
                log.error("充值订单：" + outTradeNo + "支付失败");
                return setXml(Constant.SUCCESS, "OK");
            } else if (resultCode.equalsIgnoreCase(Constant.SUCCESS)) {
                log.error("充值订单" + outTradeNo + "支付成功");
                // 业务处理
                MallAccountLogEntity accountLogEntity = accountLogService.getOne(new QueryWrapper<MallAccountLogEntity>().eq("ORDER_SN", outTradeNo));
                // 已经支付,不再执行
                if (accountLogEntity.getType() == 1) {
                    return setXml(Constant.SUCCESS, "OK");
                }
                accountLogEntity.setType(1);
                accountLogEntity.setAddTime(new Date());
                accountLogService.update(accountLogEntity);

                AdOrderEntity orderInfo = orderService.getOne(new QueryWrapper<AdOrderEntity>().eq("ORDER_SN", outTradeNo));
                //未付款的余额充值订单
                if(orderInfo.getOrderStatus().equals(Constant.OrderStatus.DFK.getValue()) && orderInfo.getOrderType().equals(Constant.OrderType.YECZ.getValue())){
                    orderInfo.setOrderStatus(Constant.OrderStatus.YZF.getValue());
                    orderInfo.setPayTime(new Date());
                    orderService.updateById(orderInfo);
                }
                if (accountLogEntity.getOrderType() == 0) {
                    AdRechargeLevelEntity adRechargeLevelEntity = adRechargeLevelService.getById(accountLogEntity.getRechangeLevelId());
                    MallUserEntity userEntity = mallUserService.getById(accountLogEntity.getUserId());
                    userEntity.setBalance(userEntity.getBalance().add(adRechargeLevelEntity.getBalance()));
                    mallUserService.update(userEntity);
                }
                if (Constant.FromType.MA.getValue().equals(accountLogEntity.getFromType())) {
                    // TODO 微信通知
                } else if (accountLogEntity.getFromType().equals(Constant.FromType.MP.getValue())) {
                    //TODO 公众号下单，发送消息
                } else if (accountLogEntity.getFromType().equals(Constant.FromType.QQ.getValue())) {
                    //TODO QQ下单，发送消息
                }
            } else {
                log.error("充值订单处理异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setXml(Constant.SUCCESS, "OK");
    }

    /**
     * 返回微信服务
     *
     * @param returnCode
     * @param returnMsg
     * @return
     */
    public String setXml(String returnCode, String returnMsg) {
        return "<xml><return_code><![CDATA[" + returnCode + "]]></return_code><return_msg><![CDATA[" + returnMsg + "]]></return_msg></xml>";
    }
}
