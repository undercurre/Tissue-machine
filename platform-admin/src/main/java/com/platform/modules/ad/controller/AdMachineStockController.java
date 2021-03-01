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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdGoodsEntity;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.service.AdGoodsService;
import com.platform.modules.ad.service.AdTissueMachineService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdMachineStockEntity;
import com.platform.modules.ad.service.AdMachineStockService;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Response;

import java.util.List;
import java.util.Map;

/**
 * 机柜库存表Controller
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@RestController
@RequestMapping("ad/machinestock")
public class AdMachineStockController extends AbstractController {
    @Autowired
    private AdMachineStockService adMachineStockService;
    @Autowired
    private AdTissueMachineService adTissueMachineService;
    @Autowired
    private AdGoodsService adGoodsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:machinestock:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AdMachineStockEntity> list = adMachineStockService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询机柜库存表
     *
     * @param params 查询参数a
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:machinestock:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = adMachineStockService.queryPage(params, getUser());

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:machinestock:info")
    public RestResponse info(@PathVariable("id") String id) {
        AdMachineStockEntity adMachineStock = adMachineStockService.getById(id);

        return RestResponse.success().put("machinestock", adMachineStock);
    }

    /**
     * 新增机柜库存表
     *
     * @param adMachineStock adMachineStock
     * @return RestResponse
     */
    @SysLog("新增机柜库存表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:machinestock:save")
    @Transactional
    public RestResponse save(@RequestBody AdMachineStockEntity adMachineStock) {

        if (adMachineStock.getStock() <= 0) {
            return RestResponse.error("新增库存应大于0！");
        }

        // 修改商品库存
        AdGoodsEntity adGoodsEntity = adGoodsService.getById(adMachineStock.getGoodsId());
        if (adGoodsEntity.getStock() < adMachineStock.getStock()) {
            return RestResponse.error("商品库存不足！");
        } else {
            adGoodsEntity.setStock(adGoodsEntity.getStock() - adMachineStock.getStock());
            adGoodsService.update(adGoodsEntity);
        }

        // 修改机柜库存
        AdTissueMachineEntity adTissueMachineEntity = adTissueMachineService.getById(adMachineStock.getMachineId());
        adTissueMachineEntity.setTissueNumber(adTissueMachineEntity.getTissueNumber() + adMachineStock.getStock());
        adTissueMachineService.update(adTissueMachineEntity);

        if (adMachineStock.getLevel() == null) {
            AdMachineStockEntity ad = adMachineStockService.getOne(new QueryWrapper<AdMachineStockEntity>()
                    .eq("MACHINE_ID", adMachineStock.getMachineId())
                    .select("MAX(LEVEL) AS MAX_LEVEL"));
            if (ad == null) {
                adMachineStock.setMaxLevel(1);
            } else {
                adMachineStock.setLevel(ad.getMaxLevel() + 1);
            }

        } else {
            AdMachineStockEntity ad = adMachineStockService.getOne(new QueryWrapper<AdMachineStockEntity>()
                    .eq("MACHINE_ID", adMachineStock.getMachineId())
                    .select("MAX(LEVEL) AS MAX_LEVEL"));
            if (ad != null) {
                int tempLevel = ad.getMaxLevel() + 1;

                AdMachineStockEntity adMachineStockEntity = adMachineStockService.getOne(new QueryWrapper<AdMachineStockEntity>()
                        .eq("MACHINE_ID", adMachineStock.getMachineId())
                        .eq("LEVEL", adMachineStock.getLevel()));

                adMachineStock.setLevel(tempLevel);

                // 如果能找到此level的记录， 则交换level
                if (adMachineStockEntity != null) {
                    adMachineStock.setLevel(adMachineStockEntity.getLevel());
                    adMachineStockEntity.setLevel(tempLevel);
                    adMachineStockService.update(adMachineStockEntity);
                }
            }
        }
        adMachineStockService.add(adMachineStock);

        return RestResponse.success();
    }

    /**
     * 修改机柜库存表
     *
     * @param adMachineStock adMachineStock
     * @return RestResponse
     */
    @SysLog("修改机柜库存表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:machinestock:update")
    @Transactional
    public RestResponse update(@RequestBody AdMachineStockEntity adMachineStock) {
        if (adMachineStock.getStock() < 0) {
            return RestResponse.error("商品库存不能为负！");
        }
        AdMachineStockEntity adMachineStockEntity = adMachineStockService.getById(adMachineStock.getId());
        // 计算库存的变化量 (原本的库存 - 要更新的库存)：是负数的话说明机柜里面的纸巾增加了， 正数的话说明机柜里面的纸巾减少了。
        Integer changeNum = adMachineStockEntity.getStock() - adMachineStock.getStock();
        AdGoodsEntity adGoodsEntity = adGoodsService.getById(adMachineStock.getGoodsId());

        // 更新商品的库存量
        if (adGoodsEntity.getStock() + changeNum < 0) {
            return RestResponse.error("商品库存不足！");
        } else {
            adGoodsEntity.setStock(adGoodsEntity.getStock() + changeNum);
            adGoodsService.update(adGoodsEntity);
        }

        // 更新机柜中纸巾的库存量
        AdTissueMachineEntity adTissueMachineEntity = adTissueMachineService.getById(adMachineStock.getMachineId());
        if (adTissueMachineEntity.getTissueNumber() - changeNum < 0) {
            return RestResponse.error("机柜商品库存不足！");
        } else {
            adTissueMachineEntity.setTissueNumber(adTissueMachineEntity.getTissueNumber() - changeNum);
            adTissueMachineService.update(adTissueMachineEntity);
        }

        // 更改排序
        if (adMachineStock.getLevel() != adMachineStockEntity.getLevel()) {

            int tempLevel = adMachineStockEntity.getLevel();

            // 找出排序有冲突的记录,交换排序
            AdMachineStockEntity ad = adMachineStockService.getOne(new QueryWrapper<AdMachineStockEntity>()
                    .eq("MACHINE_ID", adMachineStock.getMachineId())
                    .eq("LEVEL", adMachineStock.getLevel()));

            // 如果有冲突,则交换
            if (ad != null) {
                adMachineStock.setLevel(ad.getLevel());
                ad.setLevel(tempLevel);
                adMachineStockService.update(ad);
            }

        }

        adMachineStockService.update(adMachineStock);

        return RestResponse.success();
    }

    /**
     * 根据主键删除机柜库存表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除机柜库存表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:machinestock:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        adMachineStockService.deleteBatch(ids);

        return RestResponse.success();
    }
}
