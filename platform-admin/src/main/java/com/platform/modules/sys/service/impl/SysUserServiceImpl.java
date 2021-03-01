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
package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.modules.ad.entity.AdWorkOrderEntity;
import com.platform.modules.ad.service.AdWorkOrderService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.dao.SysUserRoleDao;
import com.platform.modules.sys.entity.SysUserDepartmentEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysRoleService;
import com.platform.modules.sys.service.SysUserDepartmentService;
import com.platform.modules.sys.service.SysUserRoleService;
import com.platform.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zqh
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserDepartmentService sysUserDepartmentService;

    @Override
    public List<SysUserEntity> queryAll(Map<String, Object> params) {
        return baseMapper.selectListPage(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.CREATE_TIME");
        params.put("asc", false);
        Page<SysUserEntity> page = new Query<SysUserEntity>(params).getPage();
        List<SysUserEntity> sysUserEntityList = baseMapper.selectListPage(page, params);
        return page.setRecords(sysUserEntityList);
    }

    @Override
    public Page queryStaffPage(Map<String, Object> params, String createUserId) {
        //排序
        params.put("sidx", "T.CREATE_TIME");
        params.put("asc", false);
        Page<SysUserEntity> page = new Query<SysUserEntity>(params).getPage();
        return page.setRecords(baseMapper.selectStaffByCreateUserId(page, params, createUserId));
    }

    @Override
    public List<String> queryAllMenuId(String userId, Integer whereMenu) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("menuType", whereMenu);
        return baseMapper.queryAllMenuId(params);
    }

    @Override
    public SysUserEntity queryByUserName(String userName) {
        return this.getOne(new QueryWrapper<SysUserEntity>().eq("USER_NAME", userName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUserEntity user, Map<String, Object> params) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(Constant.DEFAULT_PW, salt).toHex());
        user.setSalt(salt);
        this.save(user);

//        检查角色是否越权
//        checkRole(user, params);

        // 保存用户与部门之间的关系
        String userId = user.getUserId();
        user.getDepartmentIdList().forEach(e -> {
            sysUserDepartmentService.add(SysUserDepartmentEntity.builder()
                    .userId(userId)
                    .departmentId(e)
                    .build());
        });

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserEntity user, Map<String, Object> params) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }

        this.updateById(user);

        //检查角色是否越权
//        checkRole(user, params);
        // 修改用户与部门间的关系(先删除原本的关联部门，然后将新传进来的部门新增)
        String userId = user.getUserId();
        Map<String, Object> columnMap = new HashMap<>(2);
        columnMap.put("USER_ID", user.getUserId());
        sysUserDepartmentService.removeByMap(columnMap);
        user.getDepartmentIdList().forEach(e -> {
            sysUserDepartmentService.add(SysUserDepartmentEntity.builder()
                    .userId(userId)
                    .departmentId(e)
                    .build());
        });

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(userId, user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStaff(SysUserEntity staff){
        SysUserEntity s = this.getById(staff);
        s.setUserName(staff.getUserName());
        s.setSex(staff.getSex());
        s.setRealName(staff.getRealName());
        if(staff.getEmail() != null){
            s.setEmail(staff.getEmail());
        }
        if(staff.getMobile() != null){
            s.setMobile(staff.getMobile());
        }
        this.updateById(s);

        sysUserRoleService.saveOrUpdate(staff.getUserId(), staff.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] userIds) {
        this.removeByIds(Arrays.asList(userIds));
    }

    @Override
    public boolean updatePassword(String userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<SysUserEntity>().eq("USER_ID", userId).eq("PASSWORD", password));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPw(String[] userIds) {
        for (int i = 0; i < userIds.length; i++) {
            SysUserEntity user = this.getById(userIds[i]);

            user.setPassword(new Sha256Hash(Constant.DEFAULT_PW, user.getSalt()).toHex());

            this.updateById(user);
        }
    }

    @Override
    public List<SysUserEntity> queryAllWorker(String userId){
        return sysUserRoleDao.queryAllWorker(userId);
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserEntity user, Map<String, Object> params) {
        if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
            return;
        }

        //查询用户权限下的角色列表
        List<String> roleIdList = sysRoleService.queryRoleIdList(params);

        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new BusinessException("新增用户所选角色，不是本人创建");
        }
    }

    @Override
    public List<SysUserEntity> queryAgentList(Integer whereNeedAgentList, SysUserEntity user) {
        Integer level = sysUserDao.getUserLevel(user.getUserId());
        if (user.getIsStaff() == 1) {
            // 获取上级level
            level = sysUserDao.getUserLevel(user.getCreateUserId());
            user.setUserId(user.getCreateUserId());
        }

        if (whereNeedAgentList == Constant.whereNeedAgentList.JGXZ.getValue() || level == Constant.roleLevel.ZDL.getValue()) {
            return sysUserRoleDao.queryAgentList();
        } else if (whereNeedAgentList == Constant.whereNeedAgentList.SPXZ.getValue()) {
            return sysUserRoleDao.queryAgentList().stream().filter(e->e.getUserId().equals(user.getUserId())).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public boolean isWorker(String userId){
        List<String> roleNameList = baseMapper.getRoleNameByUserId(userId);
            for (String role : roleNameList) {
                if (StringUtils.isNotEmpty(role) && role.equals("运维人员")) {
                    return true;
                }
            }
        return false;
    }

    @Override
    public boolean isOperationManage(String userId) {
        for (String role :baseMapper.getRoleNameByUserId(userId)) {
            if (role.equals("运维经理")){
                return true;
            }
        }
        return false;
    }
}
