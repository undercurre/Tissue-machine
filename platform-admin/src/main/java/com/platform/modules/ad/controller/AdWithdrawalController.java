/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.modules.ad.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.dao.AdWithdrawalDao;
import com.platform.modules.ad.entity.AdOrderEntity;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdWithdrawalEntity;
import com.platform.modules.ad.service.AdWithdrawalService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.sys.service.SysUserService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * 提现记录表Controller
 *
 * @author dxd
 * @date 2020-11-11 16:18:10
 */
@RestController
@RequestMapping("ad/withdrawal")
@Api(tags="AdWithdrawalController|提现相关接口")
public class AdWithdrawalController extends AbstractController {
    @Autowired
    private AdWithdrawalService adWithdrawalService;
    @Autowired
    private AdWithdrawalDao adWithdrawalDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:withdrawal:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdWithdrawalEntity> list = adWithdrawalService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询提现记录表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:withdrawal:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adWithdrawalService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 分页查询用户的提现记录表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/listByUser")
    @RequiresPermissions("ad:withdrawal:list")
    public RestResponse listByUser(@RequestParam Map<String, Object> params) {
        params.put("userId", getUserId());
        System.out.println(params.toString());
        Page page = adWithdrawalService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:withdrawal:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdWithdrawalEntity adWithdrawal = adWithdrawalDao.getDetailById(id);

        return RestResponse.success().put("withdrawal", adWithdrawal);
    }

    /**
     * 新增提现记录表
     *
     * @param adWithdrawal adWithdrawal
     * @return RestResponse
     */
    @SysLog("新增提现记录表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:withdrawal:save")
    public RestResponse save(@RequestBody AdWithdrawalEntity adWithdrawal) {
        // 校验金额
        if (adWithdrawal.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return RestResponse.error("请输入大于0的金额");
        }
        // 获取最小提现金额
        BigDecimal minPrice = new BigDecimal(sysConfigService.getValue(Constant.WITHDRAW_SINGLE_LOWEST));
        if (adWithdrawal.getAmount().compareTo(minPrice) < 0) {
            return RestResponse.error("不能低于最小提现金额，最小提现金额为" + new BigDecimal(sysConfigService.getValue(Constant.WITHDRAW_SINGLE_LOWEST)));
        }
        // 获取用户Id
        SysUserEntity sysUser = getUser();
        String sysUserId = sysUser.getUserId();
        SysUserEntity sysUserEntity = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("USER_ID", sysUserId));
        if (sysUserEntity != null) {
            // 用户余额 > 提现金额
            if (sysUserEntity.getBalance().compareTo(adWithdrawal.getAmount()) < 0){
                return RestResponse.error("您的账户余额不足，请重新确认");
            }
            // 手续费最小值
            BigDecimal commissionMin = new BigDecimal(sysConfigService.getValue(Constant.COMMISSION_CHARGE_MIN));
            // 手续费比例
            BigDecimal commissionRatio = new BigDecimal(sysConfigService.getValue(Constant.COMMISSION_CHARGE_RATIO));
            // 手续费
            BigDecimal commission = adWithdrawal.getAmount().multiply(commissionRatio);
            // 手续费 <= 手续费最小值，按最小值算
            if (commission.compareTo(commissionMin) <= 0) {
                commission = commissionMin;
            }
            // 用户余额 > 提现金额 + 手续费
            if (sysUserEntity.getBalance().compareTo(adWithdrawal.getAmount().add(commission)) < 0){
                return RestResponse.error("您的账户余额不足以扣除提现金额和手续费，请重新确认");
            }
            adWithdrawal.setCommission(commission);
            // 修改账号余额
            BigDecimal newBalance = sysUserEntity.getBalance().subtract(adWithdrawal.getAmount()).subtract(commission);
            sysUserEntity.setBalance(newBalance);
            sysUserService.updateById(sysUserEntity);
        }

        adWithdrawalService.add(adWithdrawal);

        return RestResponse.success();
    }

    /**
     * 提现手续费
     *
     * @param adWithdrawal adWithdrawal
     * @return RestResponse
     */
    @SysLog("提现手续费")
    @RequestMapping("/commission")
    public RestResponse commission(@RequestBody AdWithdrawalEntity adWithdrawal) {

        BigDecimal commission = new BigDecimal(0.00);
        // 获取用户Id
        SysUserEntity sysUser = getUser();
        String sysUserId = sysUser.getUserId();
        SysUserEntity sysUserEntity = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("USER_ID", sysUserId));
        if (sysUserEntity != null) {
            // 手续费最小值
            BigDecimal commissionMin = new BigDecimal(sysConfigService.getValue(Constant.COMMISSION_CHARGE_MIN));
            // 手续费比例
            BigDecimal commissionRatio = new BigDecimal(sysConfigService.getValue(Constant.COMMISSION_CHARGE_RATIO));
            // 手续费
            commission = adWithdrawal.getAmount().multiply(commissionRatio);
            // 手续费 <= 手续费最小值，按最小值算
            if (commission.compareTo(commissionMin) <= 0) {
                commission = commissionMin;
            }
        }
        BigDecimal data = commission.setScale(2, RoundingMode.HALF_UP);
        BigDecimal sum = adWithdrawal.getAmount().subtract(data).setScale(2, RoundingMode.HALF_UP);
        return RestResponse.success().put("commission", data).put("sum", sum);
    }

    /**
     * 修改提现记录表
     *
     * @param adWithdrawal adWithdrawal
     * @return RestResponse
     */
    @SysLog("修改提现记录表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:withdrawal:update")
    public RestResponse update(@RequestBody AdWithdrawalEntity adWithdrawal) {

        adWithdrawalService.update(adWithdrawal);

        return RestResponse.success();
    }

    /**
     * 根据主键删除提现记录表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除提现记录表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:withdrawal:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adWithdrawalService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 根据主键改变提现记录的状态
     *
     * @param params 请求参数
     * @return RestResponse
     */
    @RequestMapping("/changeStatus")
    @RequiresPermissions("ad:withdrawal:update")
    @ApiOperation(value = "改变提现记录的状态", notes = "改变提现记录的状态", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "params", value = "请求参数", examples = @Example({
                    @ExampleProperty(mediaType = "id", value = "af56b5561ccca93b97cf999165140294"),
                    @ExampleProperty(mediaType = "status", value = "1")
            }), required = true, dataType = "string")
    })
    public RestResponse changeStatus(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {

        adWithdrawalService.changeStatus(params, getUser());

        return RestResponse.success();
    }

    /**
     * 获取用户最近使用过的银行卡
     *
     * @return RestResponse
     */
    @SysLog("删除提现记录表")
    @RequestMapping("/getLastestCard")
    @RequiresPermissions("ad:withdrawal:getLastestCard")
    @ApiOperation(notes = "获取用户最近使用的银行卡", value = "获取用户最近使用的银行卡")
    public RestResponse getLastestCard() {
        AdWithdrawalEntity adWithdrawalEntity = adWithdrawalService.getLastestCard(getUserId());
        String userBankId = null;
        if (adWithdrawalEntity != null) {
            userBankId = adWithdrawalEntity.getUserBankId();
        }

        return RestResponse.success().put("data", userBankId);
    }
}
