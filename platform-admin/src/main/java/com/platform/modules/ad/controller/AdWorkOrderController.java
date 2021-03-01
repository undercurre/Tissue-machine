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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.HttpUtils;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.ad.dao.AdWorkOrderDao;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.entity.AdWorkOrderImageEntity;
import com.platform.modules.ad.service.AdTissueMachineService;
import com.platform.modules.ad.service.AdWorkOrderImageService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ad.entity.AdWorkOrderEntity;
import com.platform.modules.ad.service.AdWorkOrderService;

import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.sys.service.impl.SysUserRoleServiceImpl;
import com.platform.modules.vo.LocationVo;
import io.swagger.annotations.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller
 *
 * @author zqh
 * @date 2020-10-19 14:05:28
 */
@RestController
@RequestMapping("ad/workorder")
@Api(tags = "AdWorkOrderController|工单管理相关接口")
public class AdWorkOrderController extends AbstractController {
    @Autowired
    private AdWorkOrderService adWorkOrderService;
    @Autowired
    private AdWorkOrderDao adWorkOrderDao;
    @Autowired
    private AdWorkOrderImageService adWorkOrderImageService;
    @Autowired
    private SysUserRoleServiceImpl sysUserRoleService;
    @Autowired
    private AdTissueMachineService adTissueMachineService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ad:workorder:list")
    @ApiOperation(value = "获取所有工单", notes = "获取所有工单", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workType", value = "工单类型", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "工单状态", dataType = "int"),
            @ApiImplicitParam(name = "isEnd", value = "工单是否完结", dataType = "int")
    })
    public RestResponse queryAll(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {
        List<AdWorkOrderEntity> list = adWorkOrderService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ad:workorder:list")
    @ApiOperation(value = "分页待领取获取工单", notes = "分页待领取获取工单", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页页数", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "单页数据量", dataType = "int"),
            @ApiImplicitParam(name = "workType", value = "工单类型", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "工单状态", dataType = "int"),
            @ApiImplicitParam(name = "isEnd", value = "工单是否完结", dataType = "int")
    })
    public RestResponse list(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {
        Page page = null;
        if (sysUserRoleService.isWorkerManager(getUserId())) {
            params.put("isWorkerManager", 1);
            params.put("userId", getUserId());
            params.put("status", Constant.workOrderStatus.DKS.getValue().toString());
            page = adWorkOrderService.queryPage(params);
        } else {
            params.put("status", Constant.workOrderStatus.DKS.getValue().toString());
            params.put("userId", getUserId());
            params.put("isWorker", 1);
            page = adWorkOrderService.queryPage(params);
        }

        return RestResponse.success().put("page", page);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/appList")
    @RequiresPermissions("ad:workorder:list")
    @ApiOperation(value = "移动端分页获取工单", notes = "移动端分页获取工单", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页页数", dataType = "int", required = true),
            @ApiImplicitParam(name = "limit", value = "单页数据量", dataType = "int", required = true),
            @ApiImplicitParam(name = "name", value = "工单名", dataType = "string", required = false),
            @ApiImplicitParam(name = "isCreator", value = "我创建的 1：选中", dataType = "int", required = false),
            @ApiImplicitParam(name = "isWorker", value = "指派我的 1：选中", dataType = "int", required = false),
            @ApiImplicitParam(name = "startTime", value = "完成时限（开始时间）", dataType = "string", required = false),
            @ApiImplicitParam(name = "deadlineTime", value = "完成时限（结束时间）", dataType = "string", required = false)
    })
    public RestResponse appList(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {

        Page page = adWorkOrderService.AppQueryPage(params, getUserId());

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ad:workorder:info")
    @ApiOperation(value = "根据工单ID获取工单的详细信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工单ID", dataType = "String", paramType = "path")
    public RestResponse info(@PathVariable("id") String id) {
        AdWorkOrderEntity adWorkOrder = adWorkOrderService.getDetailById(id);

        return RestResponse.success().put("workorder", adWorkOrder);
    }

    /**
     * 新增
     *
     * @param adWorkOrder adWorkOrder
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("ad:workorder:save")
    @ApiOperation(value = "新增工单", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workerId", value = "运维人员ID", required = true),
            @ApiImplicitParam(name = "workType", value = "工单类型 0：采购单 1：维修单 2：送货单 3：印刷单", required = true),
            @ApiImplicitParam(name = "name", value = "工单名称", required = true),
            @ApiImplicitParam(name = "des", value = "工单描述", required = true),
            @ApiImplicitParam(name = "remark", value = "完成情况描述", required = false),
            @ApiImplicitParam(name = "address", value = "区域定位", required = false),
            @ApiImplicitParam(name = "imageUrlList", value = "工单图片", required = false)
    })
    public RestResponse save(@RequestBody @ApiParam(hidden = true) AdWorkOrderEntity adWorkOrder) {

        adWorkOrder.setCreateTime(new Date());

        // 判断是否已经有指派人员
//        if (adWorkOrder.getWorkerId() == null || adWorkOrder.getWorkerId().trim().equals("")) {
//            adWorkOrder.setStatus(0);
//        } else {
//            adWorkOrder.setStatus(1);
//            adWorkOrder.setAcceptTime(new Date());
//        }

        // 判断是否有parentId，然后为其增加orderNum

        if (adWorkOrder.getWorkerId() == null) {
            return RestResponse.error("请选择运维人员！");
        }
        if (adWorkOrder.getParentId() != null && !adWorkOrder.getParentId().trim().equals("")){
            adWorkOrder.setOrderNum(adWorkOrderDao.getSonNum(adWorkOrder.getParentId()));
        }
        adWorkOrder.setStatus(Constant.workOrderStatus.DKS.getValue());
        adWorkOrder.setCreatorId(getUserId());
        if (StringUtils.isNotBlank(adWorkOrder.getMachineId())) {
            adWorkOrder.setAddress(adTissueMachineService.getOne(new QueryWrapper<AdTissueMachineEntity>().eq("ID", adWorkOrder.getMachineId())).getAddress());
        }
        adWorkOrderService.add(adWorkOrder);

        if (adWorkOrder.getImageList() != null) {
            for (AdWorkOrderImageEntity adWorkOrderImageEntity: adWorkOrder.getImageList()) {
                AdWorkOrderImageEntity image = new AdWorkOrderImageEntity();
                image.setImageUrl(adWorkOrderImageEntity.getImageUrl());
                image.setWorkOrderId(adWorkOrder.getId());
                image.setCreateTime(new Date());
                adWorkOrderImageService.save(image);
            }
        }

        // 获取指派人的openId
        SysUserEntity sysUserEntity = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("USER_ID", adWorkOrder.getWorkerId()));
        // 公众号推送消息
        if (StringUtils.isNotBlank(sysUserEntity.getMpOpenId())) {
            try {
                adWorkOrderService.sendNewWorkOrder(adWorkOrder, sysUserEntity.getMpOpenId(), Constant.WorkTypeTitle.XYCL.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param adWorkOrder adWorkOrder
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("ad:workorder:update")
    @ApiOperation(value = "修改工单", httpMethod = "POST")
    public RestResponse update(@RequestBody AdWorkOrderEntity adWorkOrder) {

        adWorkOrderService.update(adWorkOrder);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("ad:workorder:delete")
    @ApiOperation(value = "运维经理删除工单", httpMethod = "POST")
    public RestResponse delete(@RequestBody String[] ids) {
        AdWorkOrderEntity adWorkOrderEntity = adWorkOrderService.getById(ids[0]);
        // 如果工单是个父级工单
        if (adWorkOrderEntity.getParentId() == null) {
            // 删除所有的子级工单
            adWorkOrderService.list(new QueryWrapper<AdWorkOrderEntity>().eq("PARENT_ID", adWorkOrderEntity.getId())).forEach(item -> {
                adWorkOrderService.delete(item.getId());
            });
            // 删除父工单
            adWorkOrderService.delete(adWorkOrderEntity.getId());
        } else {
            adWorkOrderService.delete(adWorkOrderEntity.getId());
        }

        return RestResponse.success();
    }

    /**
     * 运维人员软删除
     *
     * @param id id
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/softDelete")
    @RequiresPermissions("ad:workorder:softdelete")
    @ApiOperation(value = "运维人员软删除工单", httpMethod = "POST")
    public RestResponse softDelete(@RequestParam String id) {
        AdWorkOrderEntity adWorkOrderEntity = adWorkOrderService.getById(id);
//        adWorkOrderEntity.setStatus(Constant.workOrderStatus.BYC.getValue());
        String userId = getUserId();
        if (adWorkOrderEntity.getWorkerId().equals(userId)) {
            if (adWorkOrderEntity.getIsDeleted() == Constant.workOrderIsDeleted.CJZSC.getValue()) {
                adWorkOrderEntity.setIsDeleted(Constant.workOrderIsDeleted.DSC.getValue());
            } else {
                adWorkOrderEntity.setIsDeleted(Constant.workOrderIsDeleted.BZPZSC.getValue());
            }
        }

        if (adWorkOrderEntity.getCreatorId().equals(userId)) {
            if (adWorkOrderEntity.getIsDeleted() == Constant.workOrderIsDeleted.BZPZSC.getValue()) {
                adWorkOrderEntity.setIsDeleted(Constant.workOrderIsDeleted.DSC.getValue());
            } else {
                adWorkOrderEntity.setIsDeleted(Constant.workOrderIsDeleted.CJZSC.getValue());
            }
        }
        adWorkOrderService.update(adWorkOrderEntity);

        return RestResponse.success();
    }

    @RequestMapping("/myworkorder")
    @RequiresPermissions("ad:workorder:list")
    @ApiOperation(value = "获取我的工单", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workType", value = "工单类型", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "工单状态", dataType = "int"),
            @ApiImplicitParam(name = "isEnd", value = "工单是否完结", dataType = "int"),
            @ApiImplicitParam(name = "isCreator", value = "我创建的 1：选中", dataType = "int"),
            @ApiImplicitParam(name = "isWorker", value = "指派我的 1：选中", dataType = "int"),
    })
    public RestResponse myWorkOrder(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) {

        Page page = null;
        System.out.println(params.toString());
        if (sysUserRoleService.isWorkerManager(getUserId())) {
            params.put("isWorkerManager", 1);
            params.put("userId", getUserId());
            page = adWorkOrderService.queryPage(params);
        } else {
            params.put("userId", getUserId());
            params.put("isCreator", 1);
            params.put("isWorker", 1);
            page = adWorkOrderService.queryMyWorkOrderPage(params);
        }
        return RestResponse.success().put("page", page).put("user", getUser());
    }

    /**
     * 领取工单
     *
     * @param workId 工单Id
     * @return RestResponse
     */
    @RequestMapping("/getWorkOrder")
    @ApiOperation(value = "领取工单", httpMethod = "POST")
    public RestResponse getWorkOrder(@RequestParam String workId) {
        AdWorkOrderEntity adWorkOrderEntity = adWorkOrderService.getById(workId);
        adWorkOrderEntity.setAcceptTime(new Date());
        adWorkOrderEntity.setStatus(1);
        adWorkOrderService.update(adWorkOrderEntity);
        return RestResponse.success();
    }

    /**
     * 工单状态转为待确认完结
     *
     * @param workId 工单Id
     * @return RestResponse
     */
    @RequestMapping("/endWork")
    @Transactional
    @ApiOperation(value = "工单状态转为待确认完结", httpMethod = "POST")
    public RestResponse endWork(@RequestParam String workId) {
        AdWorkOrderEntity adWorkOrderEntity = adWorkOrderService.getById(workId);
        // 如果工单是一个子级工单
        if (adWorkOrderEntity.getParentId() != null && !adWorkOrderEntity.getParentId().trim().equals("")) {
            // 所有子级工单完结
            adWorkOrderService.list(new QueryWrapper<AdWorkOrderEntity>().eq("PARENT_ID", adWorkOrderEntity.getParentId())).forEach(item -> {
                item.setIsEnd(Constant.workOrderIsEnd.ZDDQR.getValue());
                item.setFinishTime(new Date());
                adWorkOrderService.update(item);
            });
            // 父级工单完结
            AdWorkOrderEntity fatherAdWorkOrderEntity = adWorkOrderService.getById(adWorkOrderEntity.getParentId());
            fatherAdWorkOrderEntity.setIsEnd(Constant.workOrderIsEnd.FDDQR.getValue());
            adWorkOrderService.update(fatherAdWorkOrderEntity);

        } else {

            // 所有子级工单完结
            adWorkOrderService.list(new QueryWrapper<AdWorkOrderEntity>().eq("PARENT_ID", adWorkOrderEntity.getId())).forEach(item -> {
                item.setIsEnd(Constant.workOrderIsEnd.ZDDQR.getValue());
                item.setFinishTime(new Date());
                adWorkOrderService.update(item);
            });
            // 父工单完结
            adWorkOrderEntity.setIsEnd(Constant.workOrderIsEnd.FDDQR.getValue());
            adWorkOrderEntity.setFinishTime(new Date());
            adWorkOrderService.update(adWorkOrderEntity);

        }

        // 发送公众号模板消息给部门经理
        try {
            adWorkOrderService.sendMessageToManage(adWorkOrderEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RestResponse.success();
    }

    /**
     * 完结待确认工单
     *
     * @param workId 工单Id
     * @return RestResponse
     */
    @RequestMapping("/commitWorkOrder")
    @Transactional
    @ApiOperation(value = "完结待确认工单", httpMethod = "POST")
    public RestResponse commit(@RequestParam String workId) {
        AdWorkOrderEntity adWorkOrderEntity = adWorkOrderService.getById(workId);

        // 如果工单是一个子级工单
        if (adWorkOrderEntity.getParentId() != null && !adWorkOrderEntity.getParentId().trim().equals("")) {

            // 将自身改变成已处理状态
            adWorkOrderEntity.setStatus(Constant.workOrderStatus.YCL.getValue());
            adWorkOrderService.update(adWorkOrderEntity);

            // 所有子级工单完结
            adWorkOrderService.list(new QueryWrapper<AdWorkOrderEntity>().eq("PARENT_ID", adWorkOrderEntity.getParentId())).forEach(item -> {
                item.setIsEnd(Constant.workOrderIsEnd.WJ.getValue());
                item.setStatus(Constant.workOrderStatus.YCL.getValue());
                item.setFinishTime(new Date());
                adWorkOrderService.update(item);
            });
            // 父级工单完结
            AdWorkOrderEntity fatherAdWorkOrderEntity = adWorkOrderService.getById(adWorkOrderEntity.getParentId());
            fatherAdWorkOrderEntity.setIsEnd(Constant.workOrderIsEnd.WJ.getValue());
            fatherAdWorkOrderEntity.setStatus(Constant.workOrderStatus.YCL.getValue());
            adWorkOrderService.update(fatherAdWorkOrderEntity);
        } else {

            // 所有子级工单完结
            adWorkOrderService.list(new QueryWrapper<AdWorkOrderEntity>().eq("PARENT_ID", adWorkOrderEntity.getId())).forEach(item -> {
                item.setIsEnd(Constant.workOrderIsEnd.WJ.getValue());
                item.setStatus(Constant.workOrderStatus.YCL.getValue());
                item.setFinishTime(new Date());
                adWorkOrderService.update(item);
            });
            // 父工单完结
            adWorkOrderEntity.setIsEnd(Constant.workOrderIsEnd.WJ.getValue());
            adWorkOrderEntity.setStatus(Constant.workOrderStatus.YCL.getValue());
            adWorkOrderEntity.setFinishTime(new Date());
            adWorkOrderService.update(adWorkOrderEntity);
        }

        // 发送公众号模板消息给最后提交完结的人
        try {
            adWorkOrderService.sendFinishWorkMessageToWorker(adWorkOrderEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RestResponse.success();
    }

    /**
     * 打回待确认完结工单
     *
     * @param workId 工单Id
     * @return RestResponse
     */
    @RequestMapping("/rollbackWorkOrder")
    @Transactional
    @ApiOperation(value = "打回待确认工单", httpMethod = "POST")
    public RestResponse rollback(@RequestParam String workId) {
        AdWorkOrderEntity adWorkOrderEntity = adWorkOrderService.getById(workId);

        // 如果工单是一个子级工单
        if (adWorkOrderEntity.getParentId() != null && !adWorkOrderEntity.getParentId().trim().equals("")) {

            // 改变自身的状态
            adWorkOrderService.update(adWorkOrderEntity);

            // 所有子级工单完结
            adWorkOrderService.list(new QueryWrapper<AdWorkOrderEntity>().eq("PARENT_ID", adWorkOrderEntity.getParentId())).forEach(item -> {
                item.setIsEnd(Constant.workOrderIsEnd.WWJ.getValue());
                item.setFinishTime(new Date());
                adWorkOrderService.update(item);
            });
            // 父级工单完结
            AdWorkOrderEntity fatherAdWorkOrderEntity = adWorkOrderService.getById(adWorkOrderEntity.getParentId());
            fatherAdWorkOrderEntity.setIsEnd(Constant.workOrderIsEnd.WWJ.getValue());
            adWorkOrderService.update(fatherAdWorkOrderEntity);
        } else {

            // 所有子级工单完结
            adWorkOrderService.list(new QueryWrapper<AdWorkOrderEntity>().eq("PARENT_ID", adWorkOrderEntity.getId())).forEach(item -> {
                item.setIsEnd(Constant.workOrderIsEnd.WWJ.getValue());
                item.setFinishTime(new Date());
                adWorkOrderService.update(item);
            });
            // 父工单完结
            adWorkOrderEntity.setIsEnd(Constant.workOrderIsEnd.WWJ.getValue());
            adWorkOrderEntity.setFinishTime(new Date());
            adWorkOrderService.update(adWorkOrderEntity);
        }

        return RestResponse.success();
    }

    /**
     * 阶段完成工单
     *
     * @param adWorkOrder AdWorkOrderEntity
     * @return RestResponse
     */
    @RequestMapping("/finishWork")
    public RestResponse finishWork(@RequestBody @ApiParam(hidden = true) AdWorkOrderEntity adWorkOrder) {
        AdWorkOrderEntity adWorkOrderEntity = adWorkOrderService.getById(adWorkOrder.getId());
        adWorkOrderEntity.setStatus(Constant.workOrderStatus.YCL.getValue());
        adWorkOrderEntity.setFinishTime(new Date());
        adWorkOrderEntity.setRemark(adWorkOrder.getRemark());
        adWorkOrderEntity.setLongitude(adWorkOrder.getLongitude());
        adWorkOrderEntity.setLatitude(adWorkOrder.getLatitude());
        adWorkOrderEntity.setAddress(adWorkOrder.getAddress());
        adWorkOrderService.update(adWorkOrderEntity);

        List<AdWorkOrderImageEntity> imageList = new ArrayList<>();
        String workOrderId = adWorkOrder.getId();
        if (adWorkOrder.getImageList() != null) {
            for (AdWorkOrderImageEntity e: adWorkOrder.getImageList()) {
                AdWorkOrderImageEntity adWorkOrderImageEntity = new AdWorkOrderImageEntity();
                adWorkOrderImageEntity.setImageUrl(e.getImageUrl());
                adWorkOrderImageEntity.setCreateTime(new Date());
                adWorkOrderImageEntity.setWorkOrderId(workOrderId);
                adWorkOrderImageService.save(adWorkOrderImageEntity);
                imageList.add(adWorkOrderImageEntity);
            }
            adWorkOrderEntity.setImageList(imageList);
        }

        AdWorkOrderEntity newWorkOrder = new AdWorkOrderEntity();
        newWorkOrder.setMachineId(adWorkOrder.getMachineId());
        newWorkOrder.setName(adWorkOrder.getName());
        newWorkOrder.setDes(adWorkOrder.getRemark());
        newWorkOrder.setCreateTime(new Date());

        if (StringUtils.isNullOrEmpty(adWorkOrder.getWorkType())) {
            newWorkOrder.setWorkType(adWorkOrderEntity.getWorkType());
        } else {
            newWorkOrder.setWorkType(adWorkOrder.getWorkType());
        }

        newWorkOrder.setWorkerId(adWorkOrder.getWorkerId());
        newWorkOrder.setIsAvail(1);
        newWorkOrder.setIsEnd(0);
        newWorkOrder.setStatus(Constant.workOrderStatus.DKS.getValue());
        newWorkOrder.setCreatorId(adWorkOrderEntity.getWorkerId());

        if (StringUtils.isNullOrEmpty(adWorkOrder.getStartTime()) || StringUtils.isNullOrEmpty(adWorkOrder.getDeadlineTime())) {
            newWorkOrder.setStartTime(adWorkOrderEntity.getStartTime());
            newWorkOrder.setDeadlineTime(adWorkOrderEntity.getDeadlineTime());
        } else {
            newWorkOrder.setStartTime(adWorkOrder.getStartTime());
            newWorkOrder.setDeadlineTime(adWorkOrder.getDeadlineTime());
        }

        if (adWorkOrder.getMachineId() != null){
            newWorkOrder.setAddress(adTissueMachineService.getOne(new QueryWrapper<AdTissueMachineEntity>().eq("ID", adWorkOrder.getMachineId())).getAddress());
        }
        if (adWorkOrder.getParentId() == null) {
            newWorkOrder.setParentId(adWorkOrder.getId());
            newWorkOrder.setOrderNum(1);
        } else {
            newWorkOrder.setParentId(adWorkOrder.getParentId());
            newWorkOrder.setOrderNum(adWorkOrderService.list(new QueryWrapper<AdWorkOrderEntity>().eq("PARENT_ID", adWorkOrder.getParentId())).size() + 1);
        }
        adWorkOrderService.save(newWorkOrder);

        // 获取指派人的openId
        SysUserEntity sysUserEntity = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("USER_ID", newWorkOrder.getWorkerId()));
        // 公众号推送消息
        if (StringUtils.isNotBlank(sysUserEntity.getMpOpenId())) {
            try {
                adWorkOrderService.sendNewWorkOrder(newWorkOrder, sysUserEntity.getMpOpenId(), Constant.WorkTypeTitle.XYCL.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return RestResponse.success().put("newWorkOrder", newWorkOrder).put("fatherWorkOrder", adWorkOrderEntity);
    }

    /**
     * 作废工单
     *
     * @param params 请求参数
     * @return RestResponse
     */
    @RequestMapping("/changeIsAvail")
    @ApiOperation(value = "作废工单", notes = "作废工单", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workId", value = "工单Id"),
            @ApiImplicitParam(name = "reason", value = "作废理由")
    })
    public RestResponse changeIsAvail(@RequestParam Map<String, Object> params) {
        AdWorkOrderEntity adWorkOrderEntity = adWorkOrderService.getById((String) params.get("workId"));
        adWorkOrderEntity.setIsAvail(0);
        adWorkOrderEntity.setReason((String) params.get("reason"));
        adWorkOrderService.update(adWorkOrderEntity);
        return RestResponse.success();
    }

    /**
     * 根据经纬度获取详细地址
     *
     * @param params 经纬度信息
     * @return RestResponse
     */
    @RequestMapping("/getAddress")
    @ApiOperation(value = "根据经纬度获取详细地址", notes = "根据经纬度获取详细地址", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longitude", value = "经度"),
            @ApiImplicitParam(name = "latitude", value = "纬度"),
            @ApiImplicitParam(name = "get_poi", value = "是否获取周边信息 0:获取 1:不获取")
    })
    public RestResponse getAddress(@RequestParam @ApiParam(hidden = true) Map<String, Object> params) throws Exception{
        try{
            JSONObject jsonObject = adWorkOrderService.getAddress(params);
            return RestResponse.success().put("address", jsonObject);
        } catch (Exception e) {
            throw new Exception("传入参数错误!");
        }
    }
}
