package com.platform.modules.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.platform.annotation.LoginUser;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.ad.dao.MallUserLevelDao;
import com.platform.modules.ad.entity.*;
import com.platform.modules.ad.service.*;
import com.platform.modules.protocol.service.ProtocolRefoundService;
import com.platform.modules.sys.service.SysConfigService;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import oracle.jdbc.driver.Const;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.poi.hpsf.Decimal;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/app/order")
@Api(tags = "AppOrderController|APP纸巾机下单接口")
public class AppOrderController {
    @Autowired
    private AdOrderService adOrderService;
    @Autowired
    private AdTissueMachineService adTissueMachineService;
    @Autowired
    private AdMachineStockService adMachineStockService;
    @Autowired
    private AdGoodsService adGoodsService;
    @Autowired
    private AdMessageService adMessageService;
    @Autowired
    private AdOrderTissueService adOrderTissueService;
    @Autowired
    private MallUserLevelService mallUserLevelService;
    @Autowired
    private AdMachineAgentService adMachineAgentService;
    @Autowired
    private AdMachinePushService adMachinePushService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private ProtocolRefoundService protocolRefoundService;
    @Autowired
    private AdCouponService adCouponService;
    @Autowired
    private AdUserCouponService adUserCouponService;
    @Autowired
    private MallAccountLogService mallAccountLogService;
    @Autowired
    private MallUserService mallUserService;

    /**
     * 获取订单列表信息
     */
    @GetMapping("list")
    @ApiOperation(value = "订单列表", notes = "根据用户ID获取订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
    })
    public RestResponse orderList(@LoginUser MallUserEntity loginUser) {
        List<AdOrderEntity> orderList = adOrderService.getOrderListByUserId(loginUser.getId());
        orderList.forEach(e -> {
            String keyValue = new String();
            HashMap<String, Integer> map = new HashMap<>();
            if (e.getOrderType().equals(Constant.OrderType.YECZ.getValue())) {
                MallAccountLogEntity accountLogEntity = mallAccountLogService.getOne(new QueryWrapper<MallAccountLogEntity>().eq("ORDER_SN", e.getOrderSn()));
                e.setRechargeBalance(accountLogEntity.getPrice());
            } else if(e.getOrderType() == (Constant.OrderType.PTDD.getValue()) || e.getOrderType() == (Constant.OrderType.MFLQ.getValue())) {
                List<AdOrderTissueEntity> tissueOrder = adOrderTissueService.list(new QueryWrapper<AdOrderTissueEntity>().eq("ORDER_ID", e.getId()));
                Iterator<AdOrderTissueEntity> iterator = tissueOrder.iterator();
                while (iterator.hasNext()) {
                    AdOrderTissueEntity next = iterator.next();
                    map.merge(next.getGoodsName(), next.getGoodsCount(), Integer::sum);
                }
                for(Map.Entry<String, Integer> entry: map.entrySet()){
                    keyValue += entry.getKey() + "X" + entry.getValue() + ",";
                }
                StringBuilder s1 = new StringBuilder(keyValue);
                s1 = s1.deleteCharAt(s1.length() - 1);
                keyValue = s1.toString();
                e.setKeyValue(keyValue);
            }
        });
        return RestResponse.success().put("data", orderList);
    }

    /**
     * 获取交易明细
     */
    @GetMapping("transactionList")
    @ApiOperation(value = "获取交易明细", notes = "获取交易明细")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
    })
    public RestResponse transactionList(@LoginUser MallUserEntity loginUser) throws InvocationTargetException, IllegalAccessException {
        List<AdOrderEntity> orderList = adOrderService.list(new QueryWrapper<AdOrderEntity>()
                .eq("USER_ID", loginUser.getId())
                .eq("ORDER_STATUS",Constant.OrderStatus.YZF.getValue())
                .eq("ORDER_TYPE", Constant.OrderType.PTDD.getValue())
                .orderByDesc("CREATE_TIME"));
        ListIterator<AdOrderEntity> iterator = orderList.listIterator();
        while (iterator.hasNext()) {
            AdOrderEntity e = iterator.next();
            //如果是即是微信支付又扣除余额的订单
            if (e != null && (e.getActualPrice().compareTo(BigDecimal.valueOf(0)) > 0)
                    && (e.getComsumePrice().compareTo(BigDecimal.valueOf(0)) > 0)) {
                AdOrderEntity newOrder = new AdOrderEntity();
                BeanUtils.copyProperties(newOrder, e);
                e.setComsumePrice(BigDecimal.valueOf(0));
                e.setOrderPayType(1);
                newOrder.setActualPrice(BigDecimal.valueOf(0));
                newOrder.setOrderPayType(2);
                iterator.add(newOrder);
            } else {
                if (e.getActualPrice().compareTo(BigDecimal.valueOf(0)) > 0) {
                    e.setOrderPayType(1);
                }
                if (e.getComsumePrice().compareTo(BigDecimal.valueOf(0)) > 0) {
                    e.setOrderPayType(2);
                }
            }
        }
        return RestResponse.success().put("data", orderList);
    }


    /**
     * 提交订单
     */
    @PostMapping("submitOrder")
    @ApiOperation(value = "提交订单", notes = "购物车页面提交订单，商品为购物车中已经选中的")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "jsonParam", value = "JSON格式参数", examples = @Example({
                    @ExampleProperty(mediaType = "orderType", value = "订单类型 1：普通订单  2：免费领取"),
                    @ExampleProperty(mediaType = "fromType", value = "用户下单来源类型 1:微信小程序 2:微信公众号 3:app 4:H5 5:支付宝小程序 6:QQ小程序"),
                    @ExampleProperty(mediaType = "price", value = "价格"),
                    @ExampleProperty(mediaType = "comsumePrice", value = "消费金"),
                    @ExampleProperty(mediaType = "machineId", value = "机柜ID"),
                    @ExampleProperty(mediaType = "number", value = "数目"),
                    @ExampleProperty(mediaType = "couponId", value = "优惠券ID")
            }), required = true, dataType = "string")
    })
    @Transactional(rollbackFor = Exception.class)
    public RestResponse submitOrder(@LoginUser MallUserEntity loginUser, @RequestBody JSONObject jsonParam) {
        String userId = loginUser.getId();
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户为空!");
        }
        Integer orderType = jsonParam.getInteger("orderType");
        BigDecimal price = jsonParam.getBigDecimal("price");
        BigDecimal comsumePrice = jsonParam.getBigDecimal("comsumePrice");
        String machineId = jsonParam.getString("machineId");
        Integer fromType = jsonParam.getInteger("fromType");
        Integer number = jsonParam.getInteger("number");
        String couponId = jsonParam.getString("couponId");
        if(loginUser.getTissueCount().compareTo(1) < 0){
            return RestResponse.error("您没有可领取的纸巾");
        }
        if (number == null) {
            return RestResponse.error("请选择数量");
        }
        if (number <= 0) {
            return RestResponse.error("请选择大于0包纸巾");
        }
        if (machineId == null) {
            return RestResponse.error("机柜信息异常");
        }
        if (orderType == null) {
            return RestResponse.error("下单类型信息异常");
        }
        Map<String, Integer> userRemainNumber = adOrderService.getUserRemainNumber(loginUser);
        if (orderType == Constant.OrderType.PTDD.getValue() && (number > userRemainNumber.get("userRemainBuyToday"))) {
            throw new BusinessException("购买数量超过今日可购买次数");
        }
        if (orderType == Constant.OrderType.MFLQ.getValue() && (number > userRemainNumber.get("userRemainFreeToday"))) {
            throw new BusinessException("领取数量超过今日可购买次数");
        }
        AdTissueMachineEntity machineInfo = adTissueMachineService.getById(machineId);
        List<AdMachineStockEntity> machineStockInfo = adMachineStockService.list(new QueryWrapper<AdMachineStockEntity>()
                .eq("MACHINE_ID", machineId)
                .ne("STOCK", 0)
                .orderByAsc("LEVEL"));
        if (machineInfo.getTissueNumber() < number || machineInfo.getTissueNumber() <= 0) {
            throw new BusinessException("商品库存不足");
        }
        //如果设备正在使用，返回isUse=1给前端
        if (machineInfo.getIsUsed() == Constant.MachineUseStatus.SYZ.getValue()) {
            return RestResponse.success().put("isUse", 1);
        }
        // 判断有多少种商品
        Map<String, Integer> goodsMap = new LinkedHashMap<>();
        Integer t = number;
        //value计算每种商品卖了多少个
        for (AdMachineStockEntity e : machineStockInfo) {
            StringBuffer idGoodsId = new StringBuffer();
            idGoodsId.append(e.getGoodsId());
            idGoodsId.append(":");
            idGoodsId.append(e.getId());
            Integer stock = e.getStock();
            goodsMap.put(String.valueOf(idGoodsId), stock);
            if (t <= stock) {
                goodsMap.put(String.valueOf(idGoodsId), t);
                break;
            }
            t -= stock;
        }
        Iterator<Map.Entry<String, Integer>> iterator = goodsMap.entrySet().iterator();
        AdOrderEntity order = new AdOrderEntity();
        List<AdOrderTissueEntity> otList = new ArrayList<>();
        BigDecimal goodsTotalPrice = BigDecimal.valueOf(0.00);
        //orderTissue表排序指数
        int sortLevel = 1;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();

            //key为:goodsID+machineStockId
            String key = entry.getKey();
            String[] split = key.split(":");
            //订单商品详情
            AdGoodsEntity goods = adGoodsService.getById(split[0]);
            AdOrderTissueEntity orderTissue = new AdOrderTissueEntity();
            orderTissue.setStatus(Constant.ShipmentStatus.INIT.getValue());

            orderTissue.setGoodsId(split[0]);
            orderTissue.setUserId(loginUser.getId());
            orderTissue.setGoodsCount(entry.getValue());
            orderTissue.setGoodsName(goods.getName());
            orderTissue.setGoodsPrice(goods.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
            orderTissue.setOrderId(order.getId());
//            orderTissue.setStatus(0);
            orderTissue.setSortLevel(sortLevel);
            orderTissue.setStockId(split[1]);
            otList.add(orderTissue);
            //排序指数+1
            sortLevel += 1;
            //计算总价格
            goodsTotalPrice = goodsTotalPrice.add(goods.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }

        String orderSn = StringUtils.transFromType(fromType) + StringUtils.generateOrderNumber();
        // 免费领取
        if (orderType.equals(Constant.OrderType.MFLQ.getValue())) {
            if (machineStockInfo.size() == 0) {
                throw new BusinessException("商品库存不足，请下次再来");
            } else {
                // 减机柜表总库存
                machineInfo.setTissueNumber(machineInfo.getTissueNumber() - number);
                // 判断机柜纸巾数是否少于预警线
                if (machineInfo.getTissueNumber() <= Integer.parseInt(sysConfigService.getValue("MACHINE_TISSUE_WARING"))) {
                    machineInfo.setStatus(Constant.MachineStatus.ZJYJ.getValue());
                }
                machineInfo.setIsUsed(1);
                adTissueMachineService.update(machineInfo);

                // 减machineStock表的第一个记录
                AdMachineStockEntity maStock = machineStockInfo.get(0);
                maStock.setStock(maStock.getStock() - number);
                adMachineStockService.update(maStock);
//            order.setActivityId(activityId);
//            修改用户剩余纸巾数
            loginUser.setTissueCount(loginUser.getTissueCount()-number);
            mallUserService.update(loginUser);
                order.setPayTime(new Date());
                order.setOrderStatus(Constant.OrderStatus.YZF.getValue());
            }
        } else {
            order.setOrderStatus(Constant.OrderStatus.DFK.getValue());

        }

        //优惠金额
        BigDecimal couponPrice = new BigDecimal(0);
        if (StringUtils.isNotBlank(couponId)) {
            AdCouponEntity adCouponEntity = adCouponService.getById(couponId);
            if (null != adCouponEntity && adCouponEntity.getStatus() == 1) {
                AdUserCouponEntity userCouponEntity = adUserCouponService.getOne(new QueryWrapper<AdUserCouponEntity>()
                        .eq("STATUS", 0)
                        .eq("COUPON_ID", couponId)
                        .eq("USER_ID", loginUser.getId())
                        .last("LIMIT 1"));
                if (userCouponEntity != null && userCouponEntity.getStatus() == 0) {
                    // 商品总价 >= 最低消费金额
                    BigDecimal minPirce = new BigDecimal(sysConfigService.getValue(Constant.MIN_PAY_MONEY));
                    System.out.println(minPirce);
                    if (goodsTotalPrice.compareTo(minPirce) > -1) {
                        if (null != adCouponEntity) {
                            Integer couponType = adCouponEntity.getCouponType();
                            //代金券类型
                            if (Constant.CouponType.DJQ.getValue().equals(couponType)) {
                                couponPrice = adCouponEntity.getSubPrice();
                                // 最低消费金额 > 商品总价 - 优惠券价格
                                if (minPirce.compareTo(goodsTotalPrice.subtract(couponPrice)) > 0) {
                                    couponPrice = goodsTotalPrice.subtract(minPirce);
                                }
                            }
                            //折扣
                            if (Constant.CouponType.ZK.getValue().equals(couponType)) {
                                //这里使用商品总价乘以（1-折扣/10），然后四舍五入保留两位小数
                                couponPrice = new BigDecimal(1).subtract(adCouponEntity.getDiscount().divide(new BigDecimal(10))).multiply(goodsTotalPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                            }
                        }
                    }
                }
            }
        }

        BigDecimal orderTotalPrice = goodsTotalPrice;
        //减去其它支付的金额后，要实际支付的金额
        BigDecimal actualPrice = orderTotalPrice.subtract(couponPrice);
        //使用的余额
        BigDecimal useBalance = new BigDecimal(0);
        if (loginUser.getBalance().compareTo(BigDecimal.valueOf(0)) > -1) {
            //如果余额足够支付,则是余额支付  否则则是微信支付(先扣除余额再微信支付剩余金额)
            if (loginUser.getBalance().compareTo(actualPrice) > -1) {
                useBalance = actualPrice;
                actualPrice = BigDecimal.valueOf(0);
            } else {
                useBalance = loginUser.getBalance();
                actualPrice = actualPrice.subtract(useBalance);
            }
        }
        // 判断支付类型
        Integer payType;
        if (actualPrice.compareTo(BigDecimal.ZERO) == 0) {
            payType = Constant.orderPayType.YEZF.getValue();
        } else {
            payType = Constant.orderPayType.WXZF.getValue();
        }
        order.setPayType(payType);
        order.setActualPrice(actualPrice);
        order.setComsumePrice(useBalance);
        order.setTotalPrice(orderTotalPrice);
        order.setNumber(number);
        order.setOrderType(orderType);
        order.setOrderSn(orderSn);
        order.setUserId(loginUser.getId());
        order.setFromType(fromType);
        order.setMachineId(machineId);
        order.setIsDelete(0);
        Date date = new Date();
        order.setCreateTime(date);
        order.setExpireTime(DateUtils.addDateMinutes(date, Integer.parseInt("30")));
        order.setCouponId(couponId);
        order.setCouponPrice(couponPrice);
        order.setShipmentStatus(Constant.OrderShipmentStatus.WSQCH.getValue());
        adOrderService.save(order);
        otList.forEach(ot -> {
            ot.setOrderId(order.getId());
            adOrderTissueService.add(ot);
        });
        //免费领取创建订单后加入到机柜发放表
        if (orderType.equals(Constant.OrderType.MFLQ.getValue())) {
            AdMachinePushEntity adMachinePushEntity = new AdMachinePushEntity();
            adMachinePushEntity.setMachineSn(machineInfo.getSn());
            adMachinePushEntity.setNumber(number);
            adMachinePushEntity.setOrderId(order.getId());
            adMachinePushEntity.setCreatedTime(date);
            adMachinePushService.add(adMachinePushEntity);
        }
        //消息中心
        AdMessageEntity message = new AdMessageEntity();
        message.setUserId(loginUser.getId());
        message.setCreateTime(date);
        message.setOrderId(order.getId());
        message.setHadRead(0);
        message.setIsDelete(0);
        message.setContent("下单成功：请亲尽快取走您的纸巾，祝您体验愉快，开启美好一天");
        adMessageService.add(message);

        // 下单成功或者免费领取纸巾后记录该会员使用过该设备
        List<AdMachineAgentEntity> agentEntityList = adMachineAgentService.list(new QueryWrapper<AdMachineAgentEntity>().eq("USER_ID", userId));
        AdTissueMachineEntity adTissueMachineEntity = adTissueMachineService.getOne(new QueryWrapper<AdTissueMachineEntity>().eq("ID", machineId));

        if (StringUtils.isEmpty(adTissueMachineEntity)) {
            throw new BusinessException("该机器不存在!");
        }
        AdMachineAgentEntity machineAgentEntity = new AdMachineAgentEntity();
        if (agentEntityList != null) {
            Boolean isRegister = false;
            for (AdMachineAgentEntity agentEntity : agentEntityList) {
                if (agentEntity.getMachineId().equals(adTissueMachineEntity.getId())) {
                    isRegister = true;
                    System.out.println(isRegister);
                }
            }
            // false才能新增
            if (!isRegister) {
                machineAgentEntity.setFromType(Constant.userFromType.XD.getValue());
                machineAgentEntity.setMachineId(adTissueMachineEntity.getId());
                machineAgentEntity.setUserId(userId);
                adMachineAgentService.add(machineAgentEntity);
            }
        }

        return RestResponse.success().put("data", order);
    }


    /**
     * 创建激活VIP订单
     */
    @PostMapping("submitVipOrder")
    @ApiOperation(value = "创建激活VIP订单", notes = "创建激活VIP订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "jsonParam", value = "JSON格式参数", examples = @Example({
                    @ExampleProperty(mediaType = "fromType", value = "用户下单来源类型 1:微信小程序 2:微信公众号 3:app 4:H5 5:支付宝小程序 6:QQ小程序"),
                    @ExampleProperty(mediaType = "orderType", value = "订单类型 1：普通订单 2：免费领取 3:购买会员"),
            }), required = true, dataType = "string")
    })
    public RestResponse submitVipOrder(@LoginUser MallUserEntity loginUser, @RequestBody JSONObject jsonParam) {

        Integer fromType = jsonParam.getInteger("fromType");
        Integer orderType = jsonParam.getInteger("orderType");

        if (orderType == null || !orderType.equals(Constant.OrderType.GMHY.getValue()) || fromType == null) {
            return RestResponse.error("下单类型信息异常!");
        }

        if (loginUser.getUserLevelId().equals(Constant.UserLevel.GJHY.getValue().toString())) {
            return RestResponse.error("您已经是高级会员了!");
        }
        BigDecimal vipPrice = BigDecimal.valueOf(Double.parseDouble(sysConfigService.getValue("VIP_PRICE")));

        Date date = new Date();
        AdOrderEntity orderEntity = new AdOrderEntity();
        orderEntity.setFromType(fromType);
        orderEntity.setOrderSn(StringUtils.transFromType(fromType) + StringUtils.generateOrderNumber());
        orderEntity.setUserId(loginUser.getId());
        orderEntity.setOrderType(orderType);
        orderEntity.setNumber(1);
        orderEntity.setOrderStatus(Constant.OrderStatus.DFK.getValue());
        orderEntity.setCreateTime(date);
        orderEntity.setTotalPrice(vipPrice);
        orderEntity.setActualPrice(vipPrice);
        orderEntity.setCreateTime(date);
        orderEntity.setIsDelete(0);
        orderEntity.setPayType(Constant.orderPayType.WXZF.getValue());
        orderEntity.setExpireTime(DateUtils.addDateMinutes(date, Integer.parseInt("30")));
        adOrderService.add(orderEntity);
        return RestResponse.success().put("data", orderEntity);
    }

    /**
     * 软删除订单
     */
    @PostMapping("deleteById")
    @ApiOperation(value = "订单列表", notes = "根据用户ID获取订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body", name = "jsonParam", value = "JSON格式参数", examples = @Example({
                    @ExampleProperty(mediaType = "orderId", value = "订单编号")
            }), required = true, dataType = "string")
    })
    public RestResponse deleteById(@LoginUser MallUserEntity loginUser, @RequestBody JSONObject jsonParam) {
        String orderId = jsonParam.getString("orderId");
        adOrderService.deleteOrderById(orderId);
        return RestResponse.success("删除成功");
    }

    /**
     * * order出货信息，出货中页面循环查询
     */
    @GetMapping("orderShipmentStatus")
    @ApiOperation(value = "订单详情", notes = "根据订单ID获取订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单ID", required = true, dataType = "int", example = "1")
    })
    public RestResponse orderShipmentStatus(@LoginUser MallUserEntity loginUser, String orderId) {
        AdOrderEntity orderInfo = adOrderService.queryById(orderId);
        if (null == orderInfo) {
            return RestResponse.error("订单不存在！");
        }
        if (!loginUser.getId().equals(orderInfo.getUserId())) {
            return RestResponse.error("越权操作！");
        }
        return RestResponse.success().put("data", orderInfo);
    }


    /**
     * * detail
     */
    @GetMapping("detail")
    @ApiOperation(value = "订单详情", notes = "根据订单ID获取订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单ID", required = true, dataType = "int", example = "1")
    })
    public RestResponse detail(@LoginUser MallUserEntity loginUser, String orderId) {
        AdOrderEntity orderInfo = adOrderService.queryById(orderId);
        List<AdOrderTissueEntity> orderTissueList = adOrderTissueService
                .list(new QueryWrapper<AdOrderTissueEntity>().eq("ORDER_ID", orderInfo.getId()));
        int shipmentNumber = 0;
        if (orderTissueList.size() > 0) {
            shipmentNumber = orderTissueList.stream().mapToInt(AdOrderTissueEntity::getShipmentNumber).sum();
        }
        orderInfo.setShipmentNumber(shipmentNumber);
        if (null == orderInfo) {
            return RestResponse.error("订单不存在！");
        }
        if (!loginUser.getId().equals(orderInfo.getUserId())) {
            return RestResponse.error("越权操作！");
        }
        return RestResponse.success().put("data", orderInfo);
    }

    /**
     * * 获取超时时间
     */
    @GetMapping("getOverTime")
    @ApiOperation(value = "获取超时时间", notes = "获取超时时间")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
    })
    public RestResponse getOverTime(@LoginUser MallUserEntity loginUser) {
        if (StringUtils.isBlank(loginUser.getId())) {
            return RestResponse.error("请登录后重试！");
        }

        Integer overTime = Integer.valueOf(sysConfigService.getValue(Constant.INSHIPMENT_OVERTIME));

        return RestResponse.success().put("data", overTime);
    }

    /**
     * * 退款
     */
    @GetMapping("refund")
    @ApiOperation(value = "退款", notes = "退款")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = true, dataType = "string")
    })
    public RestResponse refund(@LoginUser MallUserEntity loginUser, String orderId) {
        if (StringUtils.isBlank(loginUser.getId())) {
            return RestResponse.error("请登录后重试！");
        }
        if (StringUtils.isBlank(orderId)) {
            return RestResponse.error("该订单不存在！");
        }

        AdOrderEntity order = adOrderService.getById(orderId);
        System.out.println(order);
        if (order != null) {
            protocolRefoundService.refund(order, 0);
        }
        return RestResponse.success();
    }

    /**
     * 订单提交前的检验和填写相关订单信息
     */
    @GetMapping("checkout")
    @ApiOperation(value = "校验订单", notes = "订单提交前的检验和填写相关订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "number", value = "number", dataType = "int", example = "1"),
            @ApiImplicitParam(paramType = "query", name = "machineId", value = "machineId", dataType = "string", example = "1"),
            @ApiImplicitParam(paramType = "query", name = "couponId", value = "couponId", dataType = "string", example = "1"),
    })
    @SuppressWarnings("unchecked")
    public RestResponse checkout(@LoginUser MallUserEntity loginUser, @RequestParam(required = false) String machineId, @RequestParam(required = false) Integer number,
                                 @RequestParam(required = false) String couponId) {
        Map<String, Object> resultObj = new HashMap<>(16);

        // * 获取要购买的商品和总价
        Integer checkedGoodsCount = 0;

        AdTissueMachineEntity machineInfo = adTissueMachineService.getById(machineId);
        List<AdMachineStockEntity> machineStockInfo = adMachineStockService.list(new QueryWrapper<AdMachineStockEntity>()
                .eq("MACHINE_ID", machineId)
                .ne("STOCK", 0)
                .orderByAsc("LEVEL"));

        // 判断有多少种商品
        Map<String, Integer> goodsMap = new HashMap<>();
        Integer t = number;
        //value计算每种商品卖了多少个
        for (AdMachineStockEntity e : machineStockInfo) {
            StringBuffer idGoodsId = new StringBuffer();
            idGoodsId.append(e.getGoodsId());
            idGoodsId.append(":");
            idGoodsId.append(e.getId());
            Integer stock = e.getStock();
            goodsMap.put(String.valueOf(idGoodsId), stock);
            if (t <= stock) {
                goodsMap.put(String.valueOf(idGoodsId), t);
                break;
            }
            t -= stock;
        }
        Iterator<Map.Entry<String, Integer>> iterator = goodsMap.entrySet().iterator();
        BigDecimal goodsTotalPrice = BigDecimal.valueOf(0.00);
        //orderTissue表排序指数
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();

            //key为:goodsID+machineStockId
            String key = entry.getKey();
            String[] split = key.split(":");
            //订单商品详情
            AdGoodsEntity goods = adGoodsService.getById(split[0]);
            //计算总价格
            goodsTotalPrice = goodsTotalPrice.add(goods.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }

        // 共计优惠金额
        BigDecimal sumSubPrice = new BigDecimal(0);
        // 优惠金额
        BigDecimal subPrice = new BigDecimal(0);
        if (StringUtils.isNotBlank(couponId)) {
            AdCouponEntity adCouponEntity = adCouponService.getById(couponId);
            if (null != adCouponEntity) {
                // 商品总价 >= 最低消费金额
                BigDecimal minPirce = new BigDecimal(sysConfigService.getValue(Constant.MIN_PAY_MONEY));
                System.out.println(minPirce);
                if (goodsTotalPrice.compareTo(minPirce) > -1) {
                    if (null != adCouponEntity) {
                        Integer couponType = adCouponEntity.getCouponType();
                        //代金券类型
                        if (Constant.CouponType.DJQ.getValue().equals(couponType)) {
                            subPrice = adCouponEntity.getSubPrice();
                            // 最低消费金额 > 商品总价 - 优惠券价格
                            if (minPirce.compareTo(goodsTotalPrice.subtract(subPrice)) > 0) {
                                subPrice = goodsTotalPrice.subtract(minPirce);
                            }
                        }
                        //折扣
                        if (Constant.CouponType.ZK.getValue().equals(couponType)) {
                            //这里使用商品总价乘以（1-折扣/10），然后四舍五入保留两位小数
                            subPrice = new BigDecimal(1).subtract(adCouponEntity.getDiscount().divide(new BigDecimal(10))).multiply(goodsTotalPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                    }
                }
            }
        }
        sumSubPrice = sumSubPrice.add(subPrice);
        BigDecimal orderTotalPrice = goodsTotalPrice;
        //减去其它支付的金额后，要实际支付的金额
        BigDecimal actualPrice = orderTotalPrice.subtract(sumSubPrice);
        //使用的余额
        BigDecimal useBalance = new BigDecimal(0);
        if (loginUser.getBalance().compareTo(BigDecimal.valueOf(0)) > -1) {
            if (loginUser.getBalance().compareTo(actualPrice) > -1) {
                useBalance = actualPrice;
                actualPrice = BigDecimal.valueOf(0);
            } else {
                useBalance = loginUser.getBalance();
                actualPrice = actualPrice.subtract(useBalance);
            }
        }
        // 判断支付类型
        String payType;
        if (actualPrice.compareTo(BigDecimal.ZERO) == 0) {
            payType = Constant.orderPayType.YEZF.getValue().toString();
        } else {
            payType = Constant.orderPayType.WXZF.getValue().toString();
        }

        resultObj.put("sumSubPrice", sumSubPrice);
        resultObj.put("useBalance", useBalance);
        resultObj.put("goodsTotalPrice", goodsTotalPrice);
        resultObj.put("orderTotalPrice", orderTotalPrice);
        resultObj.put("actualPrice", actualPrice);
        resultObj.put("payType", payType);

        return RestResponse.success(resultObj);
    }

    /**
     * * 获取用户纸巾领取记录
     */
    @GetMapping("queryTissueDeal")
    @ApiOperation(value = "获取用户纸巾领取记录", notes = "获取用户纸巾领取记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "用户token", required = true, dataType = "string"),
    })
    public RestResponse queryTissueDeal(@LoginUser MallUserEntity loginUser) {
        List<AdOrderEntity> adOrderEntityList = adOrderService.list(new QueryWrapper<AdOrderEntity>()
                .eq("USER_ID", loginUser.getId())
                .eq("ORDER_TYPE", Constant.OrderType.MFLQ.getValue())
                .eq("SHIPMENT_STATUS", Constant.OrderShipmentStatus.CHCG.getValue()));
        return RestResponse.success().put("data", adOrderEntityList);
    }

}

