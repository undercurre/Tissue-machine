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

import com.platform.common.utils.Constant;
import com.platform.common.utils.ShiroUtils;
import com.platform.datascope.DataScope;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysRoleOrgService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller公共组件
 *
 * @author zqh
 */
@Slf4j
public abstract class AbstractController {
    protected Logger logger = log;

    @Autowired
    private SysRoleOrgService sysRoleOrgService;

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 当前登录用户
     *
     * @return SysUserEntity
     */
    protected SysUserEntity getUser() {
        return ShiroUtils.getUserEntity();
    }

    /**
     * 当前登录用户ID
     *
     * @return userId
     */
    protected String getUserId() {
        return getUser().getUserId();
    }

    /**
     * 当前登录用户所属机构
     *
     * @return orgNo
     */
    protected String getOrgNo() {
        return getUser().getOrgNo();
    }

    /**
     * 当前登录用户的级别（若是员工则返回上级的级别）
     *
     * @return orgNo
     */
    protected Map<String, Object> getLevel() {
        Map<String, Object> map = new HashMap<String, Object>();
        SysUserEntity user = getUser();
        Integer level = null;
        if (user.getIsStaff() == 1) {
            user.setUserId(user.getCreateUserId());
            level = sysUserDao.getUserLevel(user.getCreateUserId());
            map.put("userId", user.getCreateUserId());
            map.put("level", level);
            return map;
        } else {
            level = sysUserDao.getUserLevel(user.getUserId());
            if (level == null) {
                level = Constant.roleLevel.ZDL.getValue();
            }
            map.put("userId", user.getUserId());
            map.put("level", level);
            return map;
        }
    }

    /**
     * 数据权限构造
     *
     * @return DataScope
     */
    protected DataScope getDataScope() {
        DataScope dataScope = new DataScope();
        dataScope.setOrgNos(sysRoleOrgService.queryOrgNoListByUserId(getUserId()));
        return dataScope;
    }

    /**
     * 数据权限构造
     *
     * @return DataScope
     */
    protected DataScope getDataScope(String userAlias, String orgAlias) {
        DataScope dataScope = new DataScope();
        dataScope.setUserAlias(userAlias);
        dataScope.setOrgAlias(orgAlias);
        dataScope.setOrgNos(sysRoleOrgService.queryOrgNoListByUserId(getUserId()));
        return dataScope;
    }
}
