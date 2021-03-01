package com.platform.modules.protocol.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.ad.dao.AdOrderDao;
import com.platform.modules.ad.entity.*;
import com.platform.modules.ad.service.*;
import com.platform.modules.protocol.service.ProtocolRefoundService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("protocolRefoundService")
public class ProtocolRefoundServiceImpl extends ServiceImpl<AdOrderDao, AdOrderEntity> implements ProtocolRefoundService {
    @Autowired
    private AdOrderTissueService adOrderTissueService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private AdOrderService adOrderService;
    @Autowired
    private AdTissueMachineService adTissueMachineService;
    @Autowired
    private AdMachineStockService adMachineStockService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private AdMachinePushService adMachinePushService;
    @Autowired
    private AdUserCouponService adUserCouponService;
    @Autowired
    private MallUserService userService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private MallAccountLogService mallAccountLogService;
    @Autowired
    private AdAgentBalanceLogService adAgentBalanceLogService;

    @Override
    public RestResponse refund(AdOrderEntity order, Integer pushSuccessNumber) {
        if (order.getOrderStatus().equals(Constant.OrderStatus.YZF.getValue()) && (order.getFromType().equals(Constant.FromType.MA.getValue()))) {
            //普通购买订单退款
            if (order.getOrderType().equals(Constant.OrderType.PTDD.getValue())) {
                //微信支付退款
                if (order.getPayType().equals(Constant.orderPayType.WXZF.getValue())) {
                    try {
                        //获取订单中商品纸巾记录
                        List<AdOrderTissueEntity> orderTissueList = adOrderTissueService.list(new QueryWrapper<AdOrderTissueEntity>()
                                .eq("ORDER_ID", order.getId())
                                .orderByAsc("SORT_LEVEL"));
                        BigDecimal tPrice = new BigDecimal(0);
                        //计算成功出纸的总价格
                        Date date = new Date();
                        int failNumber = order.getNumber() - pushSuccessNumber;
                        //修改机柜库存
                        AdTissueMachineEntity tissueMachine = adTissueMachineService.getById(order.getMachineId());
                        tissueMachine.setTissueNumber(tissueMachine.getTissueNumber() + order.getNumber() - pushSuccessNumber);
                        String sysMachineOrderFailCount = sysConfigService.getValue(Constant.MACHINE_ORDER_FAIL_COUNT);
                        int sysFailCountMax = Integer.valueOf(sysMachineOrderFailCount);
                        //如果当前出纸异常单数大于系统参数，则设备异常
                        if ((tissueMachine.getOrderFailCount() + 1) >= sysFailCountMax) {
                            tissueMachine.setStatus(Constant.MachineStatus.CZYC.getValue());
                            adTissueMachineService.sendMessage(tissueMachine,Constant.MachineStatus.CZYC.getValue());
                        }
                        tissueMachine.setOrderFailCount(tissueMachine.getOrderFailCount() + 1);
                        tissueMachine.setIsUsed(Constant.MachineUseStatus.WSY.getValue());
                        adTissueMachineService.updateById(tissueMachine);
                        int total = 0;
                        for (AdOrderTissueEntity e : orderTissueList) {
                            //出货异常的纸巾
                            if (pushSuccessNumber < e.getGoodsCount()) {
                                //计算当时商品单价
                                total += e.getGoodsCount()-pushSuccessNumber;
                                //求单价
                                BigDecimal pPrice = e.getGoodsPrice().divide(BigDecimal.valueOf(e.getGoodsCount()));
                                //已经出货成功的纸巾的总价
                                tPrice = tPrice.add(pPrice.multiply(BigDecimal.valueOf(pushSuccessNumber)));
                                e.setSellTime(date);
                                e.setStatus(Constant.ShipmentStatus.FAILURE.getValue());
                                e.setShipmentNumber(pushSuccessNumber);
                                adOrderTissueService.update(e);
                                //修改机柜详情库存表库存
                                AdMachineStockEntity machineStock = adMachineStockService.getById(e.getStockId());
                                machineStock.setStock(machineStock.getStock() + e.getGoodsCount() - pushSuccessNumber);
                                adMachineStockService.updateById(machineStock);
                                pushSuccessNumber = 0;
                                if (total >= failNumber) {
                                    break;
                                }
                                continue;
                            }
                            //出货正常的纸巾
                            pushSuccessNumber = pushSuccessNumber - e.getGoodsCount();
                            e.setShipmentNumber(e.getGoodsCount());
                            e.setStatus(Constant.ShipmentStatus.SUCCESS.getValue());
                            e.setSellTime(date);
                            adOrderTissueService.update(e);
                            tPrice = tPrice.add(e.getGoodsPrice());
                        }
                        //需退的金额
                        BigDecimal refoundFee = order.getTotalPrice().subtract(tPrice);
                        //代理扣除收益
                        SysUserEntity agent = sysUserDao.queryById(tissueMachine.getAgentId());
                        String value = sysConfigService.getValue(Constant.AGENT_BALANCE_ORDER_PRICE);
                        //代理设备订单价格和获得的收益的比例
                        BigDecimal rote = new BigDecimal(value);
                        agent.setBalance(agent.getBalance().subtract(refoundFee.multiply(rote)));
                        sysUserDao.updateById(agent);
                        //加入到后台系统用户余额流水表
                        AdAgentBalanceLogEntity adAgentBalanceLogEntity = new AdAgentBalanceLogEntity();
                        adAgentBalanceLogEntity.setAgentId(agent.getUserId());
                        adAgentBalanceLogEntity.setBalance(refoundFee.multiply(rote));
                        adAgentBalanceLogEntity.setCreateTime(date);
                        adAgentBalanceLogEntity.setType(Constant.BalanceLogType.OUT.getValue());
                        adAgentBalanceLogEntity.setOrderId(order.getId());
                        adAgentBalanceLogService.add(adAgentBalanceLogEntity);


                        BigDecimal refundBalance = new BigDecimal(0);
                        // 优惠价格小于所需退款金额
                        if ((order.getCouponPrice().compareTo(BigDecimal.valueOf(0))>0) && (order.getCouponPrice().compareTo(refoundFee) ==-1))  {
                            //退还优惠券
                            adUserCouponService.backCoupon(order);
                            refoundFee = refoundFee.subtract(order.getCouponPrice());
                        }
                        // 如果实际支付金额仍小于退款金额  退实际金额 +退余额
                        if (order.getActualPrice().compareTo(refoundFee) == -1) {
                            refundBalance = refoundFee.subtract(order.getActualPrice());
                            refoundFee = order.getActualPrice();
                        }
                        if (refoundFee.compareTo(BigDecimal.valueOf(0)) > 0) {
                            //微信支付
                            WxPayRefundRequest request = new WxPayRefundRequest();
                            request.setOutTradeNo(order.getOrderSn());
                            request.setTotalFee(BaseWxPayRequest.yuanToFen(order.getActualPrice().toString()));
                            request.setRefundFee(BaseWxPayRequest.yuanToFen(refoundFee.toString()));
                            request.setOutRefundNo(StringUtils.getBundleId());
                            request.setRefundDesc("出货失败，退还金额");
                            WxPayRefundResult result = wxPayService.refund(request);
                            System.out.println(result);
                            //微信退款成功
                            if (result.getResultCode().equals(Constant.SUCCESS)) {
                                order.setOrderStatus(Constant.OrderStatus.YTK.getValue());
                                order.setShipmentStatus(Constant.OrderShipmentStatus.CHYC.getValue());
                                adOrderService.update(order);
                            }
                        } else { //没有微信支付
                            order.setOrderStatus(Constant.OrderStatus.YTK.getValue());
                            order.setShipmentStatus(Constant.OrderShipmentStatus.CHYC.getValue());
                            adOrderService.update(order);
                        }
                        //退还余额
                        if (refundBalance.compareTo(BigDecimal.valueOf(0)) > 0) {
                            MallUserEntity user = userService.getById(order.getUserId());
                            user.setBalance(user.getBalance().add(refundBalance));
                            userService.updateById(user);
                            //添加余额明细记录
                            MallAccountLogEntity accountLogEntity = new MallAccountLogEntity();
                            accountLogEntity.setAddTime(new Date());
                            accountLogEntity.setUserId(user.getId());
                            accountLogEntity.setPrice(refoundFee);
                            accountLogEntity.setLogDesc("购买商品退款");
                            accountLogEntity.setType(1);
                            accountLogEntity.setOrderType(1);
                            accountLogEntity.setFromType(1);
                            accountLogEntity.setOrderSn(order.getOrderSn());
                            mallAccountLogService.save(accountLogEntity);
                        }

                        //修改出纸操作表记录
                        AdMachinePushEntity newPush = adMachinePushService.getOne(new QueryWrapper<AdMachinePushEntity>()
                                .eq("ORDER_ID", order.getId()));
                        newPush.setStatus(Constant.OrderShipmentStatus.CHYC.getValue());
                        adMachinePushService.update(newPush);
                        System.out.println("退款成功~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    } catch (Exception e) {
                        throw new BusinessException("退款失败：" + e.getMessage());
                    }
                }
                //余额支付退款
                else if (order.getPayType().equals(Constant.orderPayType.YEZF.getValue())) {
                    //获取订单中商品纸巾记录
                    List<AdOrderTissueEntity> orderTissueList = adOrderTissueService.list(new QueryWrapper<AdOrderTissueEntity>()
                            .eq("ORDER_ID", order.getId())
                            .orderByAsc("SORT_LEVEL"));
                    BigDecimal tPrice = new BigDecimal(0);
                    //计算成功出纸的总价格
                    Date date = new Date();
                    int failNumber = order.getNumber() - pushSuccessNumber;
                    //修改机柜库存
                    AdTissueMachineEntity tissueMachine = adTissueMachineService.getById(order.getMachineId());
                    tissueMachine.setTissueNumber(tissueMachine.getTissueNumber() + order.getNumber() - pushSuccessNumber);
                    String sysMachineOrderFailCount = sysConfigService.getValue(Constant.MACHINE_ORDER_FAIL_COUNT);
                    int sysFailCountMax = Integer.valueOf(sysMachineOrderFailCount);
                    //如果当前出纸异常单数大于系统参数，则设备异常
                    if ((tissueMachine.getOrderFailCount() + 1) >= sysFailCountMax) {
                        tissueMachine.setStatus(Constant.MachineStatus.CZYC.getValue());
                        adTissueMachineService.sendMessage(tissueMachine,Constant.MachineStatus.CZYC.getValue());
                    }
                    tissueMachine.setOrderFailCount(tissueMachine.getOrderFailCount() + 1);
                    tissueMachine.setIsUsed(Constant.MachineUseStatus.WSY.getValue());
                    adTissueMachineService.updateById(tissueMachine);
                    int total = 0;
                    for (AdOrderTissueEntity e : orderTissueList) {
                        //出货异常的纸巾
                        if (pushSuccessNumber < e.getGoodsCount()) {
                            //计算当时商品单价
                            total += e.getGoodsCount()-pushSuccessNumber;
                            //求单价
                            BigDecimal pPrice = e.getGoodsPrice().divide(BigDecimal.valueOf(e.getGoodsCount()));
                            //已经出货成功的纸巾的总价
                            tPrice = tPrice.add(pPrice.multiply(BigDecimal.valueOf(pushSuccessNumber)));
                            e.setSellTime(date);
                            e.setStatus(Constant.ShipmentStatus.FAILURE.getValue());
                            e.setShipmentNumber(pushSuccessNumber);
                            adOrderTissueService.update(e);

                            //修改机柜详情库存表库存
                            AdMachineStockEntity machineStock = adMachineStockService.getById(e.getStockId());
                            machineStock.setStock(machineStock.getStock() + e.getGoodsCount() - pushSuccessNumber);
                            adMachineStockService.updateById(machineStock);
                            pushSuccessNumber = 0;
                            if (total >= failNumber) {
                                break;
                            }
                            continue;
                        }
                        //出货正常的纸巾
                        pushSuccessNumber = pushSuccessNumber - e.getGoodsCount();
                        e.setShipmentNumber(e.getGoodsCount());
                        e.setStatus(Constant.ShipmentStatus.SUCCESS.getValue());
                        e.setSellTime(date);
                        adOrderTissueService.update(e);
                        tPrice = tPrice.add(e.getGoodsPrice());
                    }
                    //需退的金额
                    BigDecimal refoundFee = order.getTotalPrice().subtract(tPrice);
                    //代理扣除收益
                    SysUserEntity agent = sysUserDao.queryById(tissueMachine.getAgentId());
                    String value = sysConfigService.getValue(Constant.AGENT_BALANCE_ORDER_PRICE);
                    //代理设备订单价格和获得的收益的比例
                    BigDecimal rote = new BigDecimal(value);
                    agent.setBalance(agent.getBalance().subtract(refoundFee.multiply(rote)));
                    sysUserDao.updateById(agent);

                    //加入到后台系统用户余额流水表
                    AdAgentBalanceLogEntity adAgentBalanceLogEntity = new AdAgentBalanceLogEntity();
                    adAgentBalanceLogEntity.setAgentId(agent.getUserId());
                    adAgentBalanceLogEntity.setBalance(refoundFee.multiply(rote));
                    adAgentBalanceLogEntity.setCreateTime(date);
                    adAgentBalanceLogEntity.setType(Constant.BalanceLogType.OUT.getValue());
                    adAgentBalanceLogEntity.setOrderId(order.getId());
                    adAgentBalanceLogService.add(adAgentBalanceLogEntity);


                    // 优惠价格小于所需退款金额
                    if ((order.getCouponPrice().compareTo(BigDecimal.valueOf(0))>0) && (order.getCouponPrice().compareTo(refoundFee) ==-1)) {
                        //退还优惠券
                        adUserCouponService.backCoupon(order);
                        refoundFee = refoundFee.subtract(order.getCouponPrice());
                    }
                    //退还余额
                    if (refoundFee.compareTo(BigDecimal.valueOf(0)) > 0) {
                        MallUserEntity user = userService.getById(order.getUserId());
                        user.setBalance(user.getBalance().add(refoundFee));
                        userService.updateById(user);

                        //添加余额明细记录
                        MallAccountLogEntity accountLogEntity = new MallAccountLogEntity();
                        accountLogEntity.setAddTime(new Date());
                        accountLogEntity.setUserId(user.getId());
                        accountLogEntity.setPrice(refoundFee);
                        accountLogEntity.setLogDesc("购买商品退款");
                        accountLogEntity.setType(1);
                        accountLogEntity.setOrderType(1);
                        accountLogEntity.setFromType(1);
                        accountLogEntity.setOrderSn(order.getOrderSn());
                        mallAccountLogService.save(accountLogEntity);

                    }
                    order.setOrderStatus(Constant.OrderStatus.YTK.getValue());
                    order.setShipmentStatus(Constant.OrderShipmentStatus.CHYC.getValue());
                    adOrderService.update(order);

                    //修改出纸操作表记录
                    AdMachinePushEntity newPush = adMachinePushService.getOne(new QueryWrapper<AdMachinePushEntity>()
                            .eq("ORDER_ID", order.getId()));
                    newPush.setStatus(Constant.OrderShipmentStatus.CHYC.getValue());
                    adMachinePushService.update(newPush);
                    System.out.println("余额退款成功~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                }
            }
                //免费领取退款
                else {
                    try {
                        //修改机柜表库存
                        AdTissueMachineEntity tissueMachine = adTissueMachineService.getById(order.getMachineId());
                        tissueMachine.setTissueNumber(tissueMachine.getTissueNumber() + 1);
                        String sysMachineOrderFailCount = sysConfigService.getValue(Constant.MACHINE_ORDER_FAIL_COUNT);
                        int sysFailCountMax = Integer.parseInt(sysMachineOrderFailCount);
                        //如果当前出纸异常单数大于系统参数，则设备异常
                        if ((tissueMachine.getOrderFailCount() + 1) >= sysFailCountMax) {
                            tissueMachine.setStatus(Constant.MachineStatus.CZYC.getValue());
                            adTissueMachineService.sendMessage(tissueMachine,Constant.MachineStatus.CZYC.getValue());
                        }
                        tissueMachine.setOrderFailCount(tissueMachine.getOrderFailCount() + 1);
                        tissueMachine.setIsUsed(Constant.MachineUseStatus.WSY.getValue());
                        adTissueMachineService.updateById(tissueMachine);
                        AdOrderTissueEntity orderTissue = adOrderTissueService.getOne(new QueryWrapper<AdOrderTissueEntity>().eq("ORDER_ID", order.getId()));
                        //修改机柜详情库存表
                        AdMachineStockEntity machineStock = adMachineStockService.getOne(new QueryWrapper<AdMachineStockEntity>()
                                .eq("MACHINE_ID", order.getMachineId())
                                .eq("GOODS_ID", orderTissue.getGoodsId())
                                .orderByAsc("LEVEL")
                                .last("LIMIT 1"));
                        machineStock.setStock(machineStock.getStock() + 1);
                        adMachineStockService.updateById(machineStock);
                        // 退还用户纸巾余额
                        MallUserEntity user = userService.getById(order.getUserId());
                        user.setTissueCount(user.getTissueCount()+1);
                        userService.updateById(user);
                        // 退还可领取次数
                        orderTissue.setStatus(Constant.OrderShipmentStatus.CHYC.getValue());
                        adOrderTissueService.update(orderTissue);
                        order.setOrderStatus(Constant.OrderStatus.YTK.getValue());
                        order.setShipmentStatus(Constant.OrderShipmentStatus.CHYC.getValue());
                        adOrderService.update(order);
                        // 修改出纸操作表记录
                        AdMachinePushEntity newPush = adMachinePushService.getOne(new QueryWrapper<AdMachinePushEntity>()
                                .eq("ORDER_ID", order.getId()));
                        newPush.setStatus(Constant.OrderShipmentStatus.CHYC.getValue());
                        adMachinePushService.update(newPush);
                    } catch (Exception e) {
                        throw new BusinessException("退款失败：" + e.getMessage());
                    }
                }
            }
            return RestResponse.success();
        }
}
