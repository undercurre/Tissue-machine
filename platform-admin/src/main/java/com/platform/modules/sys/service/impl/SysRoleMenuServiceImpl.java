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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.modules.sys.dao.SysRoleMenuDao;
import com.platform.modules.sys.entity.SysMenuEntity;
import com.platform.modules.sys.entity.SysRoleMenuEntity;
import com.platform.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 *
 * @author zqh
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(String roleId, List<String> menuIdList, Integer menuType) {
//        //先删除角色与菜单关系
//        deleteBatch(new String[]{roleId});
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleId", roleId);
        params.put("menuType", menuType);
        this.baseMapper.deleteByRoleIdAndMenuType(params);

        if (menuIdList.size() == 0) {
            return;
        }

        //保存角色与菜单关系
        List<SysRoleMenuEntity> list = new ArrayList<>(menuIdList.size());
        for (String menuId : menuIdList) {
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);
            sysRoleMenuEntity.setMenuType(menuType);
            list.add(sysRoleMenuEntity);
        }
        this.saveBatch(list);
    }

    @Override
    public List<String> queryMenuIdList(String roleId, Integer menuType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("roleId", roleId);
        params.put("menuType", menuType);
        return baseMapper.queryMenuIdList(params);
    }


    @Override
    public int deleteBatch(String[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }

}
