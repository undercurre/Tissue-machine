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
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.dao.AdGoodsBannerDao;
import com.platform.modules.ad.entity.AdGoodsBannerEntity;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.service.AdGoodsBannerService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdGoodsEntity;
import com.platform.modules.ad.service.AdGoodsService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.dao.SysUserRoleDao;
import com.platform.modules.sys.entity.SysUserEntity;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品表Controller
 *
 * @author zqh
 * @date 2020-09-21 17:48:45
 */
@RestController
@RequestMapping("ad/goods")
@Api(tags = "AdGoodsController|商品相关接口")
public class AdGoodsController extends AbstractController {
    @Autowired
    private AdGoodsService adGoodsService;
    @Autowired
    private AdGoodsBannerService adGoodsBannerService;
    @Autowired
    private AdGoodsBannerDao adGoodsBannerDao;
    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:goods:list")
    @ApiOperation(value = "获取所有商品", notes = "获取所有商品", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "商品名称", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "商品上架状态", dataType = "int")
    })
    public RestResponse queryAll(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {
        List<AdGoodsEntity> list = adGoodsService.queryAll(params);

        return RestResponse.success().put("list", list);
    }
    /**
     * 查询上架商品
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAllOnSale")
    @RequiresPermissions("ad:goods:list")
    @ApiOperation(value = "获取所有上架商品", notes = "获取所有上架商品", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "商品名称", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "商品上架状态", dataType = "int")
    })
    public RestResponse queryAllOnSale(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {
        List<AdGoodsEntity> list = adGoodsService.list(new QueryWrapper<AdGoodsEntity>().eq("STATUS",1));
        Integer level = null;
        SysUserEntity user = getUser();
        if (user.getIsStaff() == 1) {
            user.setUserId(user.getCreateUserId());
            level = sysUserDao.getUserLevel(user.getCreateUserId());
        } else {
            level = sysUserDao.getUserLevel(user.getUserId());
        }

        if (level == Constant.roleLevel.ZDL.getValue()) {
            return RestResponse.success().put("list",adGoodsService.list());
        } else {
            return RestResponse.success().put("list",adGoodsService.list(new QueryWrapper<AdGoodsEntity>().eq("AGENT_ID", user.getUserId())));
        }

    }
    /**
     * 分页查询商品表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:goods:list")
    @ApiOperation(value = "分页获取商品", notes = "分页获取商品", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页页数", dataType = "int", required = true),
            @ApiImplicitParam(name = "limit", value = "单页数据量", dataType = "int", required = true),
            @ApiImplicitParam(name = "name", value = "商品名称", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "商品上架状态", dataType = "int")
    })
    public RestResponse list(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {
        Page page = adGoodsService.queryPage(params, getUser());

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:goods:info")
    @ApiOperation(value = "根据商品ID查询商品详细信息", notes = "根据商品ID查询商品详细信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商品ID", paramType = "path", dataType = "String")
    public RestResponse info(@PathVariable("id") String id) {
        AdGoodsEntity adGoods = adGoodsService.getById(id);
        adGoods.setImageUrlList(adGoodsBannerDao.queryGoodsBannerByGoodsId(adGoods.getId()));
        return RestResponse.success().put("goods", adGoods);
    }

    /**
     * 新增商品表
     *
     * @param adGoods adGoods
     * @return RestResponse
     */
    @SysLog("新增商品表")
    @RequestMapping("/save")
    @RequiresPermissions("ad:goods:save")
    @ApiOperation(value = "新增商品", httpMethod = "POST")
    public RestResponse save(@RequestBody AdGoodsEntity adGoods) {

        adGoods.setCreateTime(new Date());
        adGoods.setUpdateTime(new Date());
        adGoodsService.add(adGoods);

        for(AdGoodsBannerEntity a:adGoods.getImageUrlList()){
            AdGoodsBannerEntity adGoodsBannerEntity = new AdGoodsBannerEntity();
            adGoodsBannerEntity.setGoodsId(adGoods.getId());
            adGoodsBannerEntity.setCreateTime(new Date());
            adGoodsBannerEntity.setImageUrl(a.getUrl());
            adGoodsBannerEntity.setIsDelete(0);
            adGoodsBannerService.add(adGoodsBannerEntity);
        }

        return RestResponse.success();
    }

    /**
     * 修改商品表
     *
     * @param adGoods adGoods
     * @return RestResponse
     */
    @SysLog("修改商品表")
    @RequestMapping("/update")
    @RequiresPermissions("ad:goods:update")
    @ApiOperation(value = "修改商品", httpMethod = "POST")
    public RestResponse update(@RequestBody AdGoodsEntity adGoods) {

        adGoodsBannerDao.deleteGoodsBannerByGoodsId(adGoods.getId());
        for (AdGoodsBannerEntity a:adGoods.getImageUrlList()){
            System.out.println(a.toString());
            AdGoodsBannerEntity adGoodsBannerEntity = new AdGoodsBannerEntity();
            adGoodsBannerEntity.setGoodsId(adGoods.getId());
            adGoodsBannerEntity.setCreateTime(new Date());
            adGoodsBannerEntity.setImageUrl(a.getUrl());
            adGoodsBannerEntity.setIsDelete(0);
            adGoodsBannerService.add(adGoodsBannerEntity);
        }
        adGoods.setUpdateTime(new Date());
        adGoodsService.update(adGoods);

        return RestResponse.success();
    }

    /**
     * 根据主键删除商品表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除商品表")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:goods:delete")
    @ApiOperation(value = "删除商品", httpMethod = "POST")
    public RestResponse delete(@RequestBody String[] ids) {

        for(String id: ids){
            adGoodsBannerDao.deleteGoodsBannerByGoodsId(id);
        }
        adGoodsService.deleteBatch(ids);

        return RestResponse.success();
    }

    @RequestMapping("changeStatus")
    @RequiresPermissions("ad:goods:update")
    @ApiOperation(value = "更改商品上架状态", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "更改后的状态 0:未上架 1:已上架", dataType = "int")
    })
    public RestResponse changeStatus(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {

        AdGoodsEntity adGoodsEntity = adGoodsService.getById((String) params.get("id"));
        adGoodsEntity.setStatus(Integer.parseInt((String) params.get("status")));
        adGoodsService.update(adGoodsEntity);
        return RestResponse.success();
    }
}
