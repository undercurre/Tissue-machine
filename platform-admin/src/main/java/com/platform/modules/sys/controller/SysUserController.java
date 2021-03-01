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
package com.platform.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.AbstractAssert;
import com.platform.common.validator.ValidatorUtils;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import com.platform.modules.sys.dao.SysUserRoleDao;
import com.platform.modules.sys.entity.SysStaffMenuEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.form.PasswordForm;
import com.platform.modules.sys.service.SysStaffMenuService;
import com.platform.modules.sys.service.SysUserDepartmentService;
import com.platform.modules.sys.service.SysUserRoleService;
import com.platform.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author zqh
 */
@RestController
@RequestMapping("/sys/user")
@Api(tags = "SysUserController|系统用户相关接口")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysStaffMenuService sysStaffMenuService;
    @Autowired
    private SysUserDepartmentService sysUserDepartmentService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("sys:dict:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<SysUserEntity> list = sysUserService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 所有用户列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {

        //如需数据权限，在参数中添加DataScope
        params.put("dataScope", getDataScope());

        Page page = sysUserService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 所有员工列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/staffList")
    @RequiresPermissions("sys:user:list")
    @ApiOperation(value = "根据创建者ID获取创建者创建的员工", notes = "根据创建者ID获取创建者创建的员工", httpMethod = "GET")
    public RestResponse staffList(@RequestParam @ApiParam(hidden = true) Map<String, Object> params, @Param("createUserId") String createUserId) {

        Page page = sysUserService.queryStaffPage(params, createUserId);

        return RestResponse.success().put("page", page);
    }

    /**
     * 所有运维人员列表
     *
     * @return RestResponse
     */
    @GetMapping("/workerList")
    @RequiresPermissions("sys:user:list")
    @ApiOperation(value = "获取所有运维人员列表", notes = "获取所有运维人员列表", httpMethod = "GET")
    public RestResponse workerList() {
        List<SysUserEntity> data = sysUserService.queryAllWorker(getUserId());

        return RestResponse.success().put("data", data);
    }

    /**
     * 获取登录的用户信息
     *
     * @return RestResponse
     */
    @GetMapping("/info")
    @ApiOperation(value = "获取登录的用户信息", notes = "获取登录的用户信息", httpMethod = "GET")
    public RestResponse info() {
        SysUserEntity user = getUser();
        user.setRoleList(sysUserRoleDao.queryRoleList(user.getUserId()));
        return RestResponse.success().put("user", user);
    }


    /**
     * 修改登录用户密码
     *
     * @param form form
     * @return RestResponse
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public RestResponse password(@RequestBody PasswordForm form) {
        AbstractAssert.isBlank(form.getNewPassword(), "新密码不为能空");

        //sha256加密
        String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return RestResponse.error("原密码不正确");
        }

        return RestResponse.success();
    }

    /**
     * 根据主键查询详情
     *
     * @param userId 主键
     * @return RestResponse
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    @ApiOperation(value = "根据ID获取用户详情", notes = "根据ID获取用户详情", httpMethod = "GET")
    public RestResponse info(@PathVariable("userId") String userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<String> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        // 获取用户部门列表
        List<String> departmentIdList = sysUserDepartmentService.queryDepartmentIdList(userId);
        user.setDepartmentIdList(departmentIdList);

        return RestResponse.success().put("user", user);
    }

    /**
     * 根据主键获取员工信息详情
     *
     * @return RestResponse
     */
    @GetMapping("/staffInfo/{userId}")
    @ApiOperation(value = "根据ID获取员工信息详情", notes = "根据ID获取员工信息详情", httpMethod = "GET")
    public RestResponse staffInfo(@PathVariable("userId") String userId) {
        SysUserEntity user = sysUserService.getById(userId);

        user.setRoleIdList(sysUserRoleService.queryRoleIdList(userId));

//        user.setPcMenuIdList(sysStaffMenuService.queryAllMenuByUserId(userId, Constant.WhereMenu.PC.getValue(), Constant.WhereMenu.PC.getValue()));

//        user.setMobileMenuIdList(sysStaffMenuService.queryAllMenuByUserId(userId, Constant.WhereMenu.MOBILE.getValue(), Constant.WhereMenu.MOBILE.getValue()));

        return RestResponse.success().put("user", user);
    }


    /**
     * 保存用户
     *
     * @param user user
     * @return RestResponse
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    @ApiOperation(value = "新增系统用户", notes = "新增系统用户", httpMethod = "POST")
    public RestResponse save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        Map<String, Object> params = new HashMap<>(2);
//        params.put("dataScope", getDataScope());

        user.setCreateUserId(getUserId());

        sysUserService.add(user, params);

        return RestResponse.success();
    }

    /**
     * 保存员工
     *
     * @param user user
     * @return RestResponse
     */
    @SysLog("保存员工")
    @PostMapping("/saveStaff")
    @RequiresPermissions("sys:user:save")
    @Transactional
    @ApiOperation(value = "新增员工", notes = "新增员工", httpMethod = "POST")
    public RestResponse saveStaff(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        Map<String, Object> params = new HashMap<>(2);
//        params.put("dataScope", getDataScope());

        user.setCreateUserId(getUserId());
        user.setIsStaff(1);
        sysUserService.add(user, params);
//        for(String menuId: user.getPcMenuIdList()){
//            SysStaffMenuEntity sysStaffMenuEntity = new SysStaffMenuEntity();
//            sysStaffMenuEntity.setStaffId(user.getUserId());
//            sysStaffMenuEntity.setMenuId(menuId);
//            sysStaffMenuEntity.setMenuType(Constant.WhereMenu.PC.getValue());
//            sysStaffMenuService.save(sysStaffMenuEntity);
//        }
//        for(String menuId: user.getMobileMenuIdList()){
//            SysStaffMenuEntity sysStaffMenuEntity = new SysStaffMenuEntity();
//            sysStaffMenuEntity.setStaffId(user.getUserId());
//            sysStaffMenuEntity.setMenuId(menuId);
//            sysStaffMenuEntity.setMenuType(Constant.WhereMenu.MOBILE.getValue());
//            sysStaffMenuService.save(sysStaffMenuEntity);
//        }
        return RestResponse.success();
    }

    /**
     * 修改用户
     *
     * @param user user
     * @return RestResponse
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    @ApiOperation(value = "修改系统用户信息", notes = "修改系统用户信息", httpMethod = "POST")
    public RestResponse update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        Map<String, Object> params = new HashMap<>(2);
        params.put("dataScope", getDataScope());

        user.setCreateUserId(getUserId());
        user.setCreateUserOrgNo(getOrgNo());

        // 判断此OPEN_ID是否已经绑定运维人员了
        if (StringUtils.isNotBlank(user.getMpOpenId())){
            SysUserEntity sysUserEntity = sysUserService.getOne(new QueryWrapper<SysUserEntity>().eq("MP_OPEN_ID", user.getMpOpenId()));
            if (sysUserEntity != null && !(sysUserEntity.getUserId().equals(user.getUserId()))){
                return RestResponse.error("此微信OPEN_ID已经绑定了运维人员！");
            }
        }

        sysUserService.update(user, params);


        return RestResponse.success();
    }

    /**
     * 修改员工
     *
     * @param staff user
     * @return RestResponse
     */
    @SysLog("修改员工")
    @PostMapping("/updateStaff")
    @RequiresPermissions("sys:user:update")
    @ApiOperation(value = "修改系统员工信息", notes = "修改系统员工信息", httpMethod = "POST")
    public RestResponse updateStaff(@RequestBody SysUserEntity staff) {
        ValidatorUtils.validateEntity(staff, UpdateGroup.class);
        sysUserService.updateStaff(staff);
//        // 修改员工权限
//        sysStaffMenuService.deleteAllMenuByStaff(staff);
//        for(String menuId: staff.getPcMenuIdList()){
//            SysStaffMenuEntity sysStaffMenuEntity = new SysStaffMenuEntity();
//            sysStaffMenuEntity.setStaffId(staff.getUserId());
//            sysStaffMenuEntity.setMenuId(menuId);
//            sysStaffMenuEntity.setMenuType(Constant.WhereMenu.PC.getValue());
//            sysStaffMenuService.save(sysStaffMenuEntity);
//        }
//        for(String menuId: staff.getMobileMenuIdList()){
//            SysStaffMenuEntity sysStaffMenuEntity = new SysStaffMenuEntity();
//            sysStaffMenuEntity.setStaffId(staff.getUserId());
//            sysStaffMenuEntity.setMenuId(menuId);
//            sysStaffMenuEntity.setMenuType(Constant.WhereMenu.MOBILE.getValue());
//            sysStaffMenuService.save(sysStaffMenuEntity);
//        }
        return RestResponse.success();
    }

    /**
     * 删除用户
     *
     * @param userIds userIds
     * @return RestResponse
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    @ApiOperation(value = "删除员工", notes = "删除员工", httpMethod = "POST")
    public RestResponse delete(@RequestBody String[] userIds) {
        if (ArrayUtils.contains(userIds, Constant.SUPER_ADMIN)) {
            return RestResponse.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return RestResponse.error("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return RestResponse.success();
    }

    /**
     * 重置密码
     *
     * @param userIds userIds
     * @return RestResponse
     */
    @SysLog("重置密码")
    @PostMapping("/resetPw")
    @RequiresPermissions("sys:user:resetPw")
    public RestResponse resetPw(@RequestBody String[] userIds) {
        if (ArrayUtils.contains(userIds, Constant.SUPER_ADMIN)) {
            return RestResponse.error("系统管理员不能重置");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return RestResponse.error("当前用户不能重置");
        }

        sysUserService.resetPw(userIds);

        return RestResponse.success();
    }

    /**
     * 获取代理商列表
     *
     * @return RestResponse
     */
    @SysLog("获取代理商列表")
    @GetMapping("/queryAgentList")
//    @RequiresPermissions("sys:user:list")
    @ApiOperation(value = "获取所有角色为代理商的系统用户", notes = "获取所有角色为代理商的系统用户", httpMethod = "GET")
    public RestResponse queryAgentList(@RequestParam Map<String, Object> params) {

        List<SysUserEntity> sysUserEntityList = sysUserService.queryAgentList(Integer.parseInt((String)params.get("whereNeedAgentList")), getUser());

        return RestResponse.success().put("data", sysUserEntityList);
    }
}
