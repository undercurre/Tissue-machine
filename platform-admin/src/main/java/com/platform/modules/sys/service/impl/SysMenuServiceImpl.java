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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.common.utils.StringUtils;
import com.platform.modules.sys.dao.SysMenuDao;
import com.platform.modules.sys.entity.SysMenuEntity;
import com.platform.modules.sys.entity.SysRoleEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysMenuService;
import com.platform.modules.sys.service.SysRoleMenuService;
import com.platform.modules.sys.service.SysStaffMenuService;
import com.platform.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zqh
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysStaffMenuService sysStaffMenuService;

    @Override
    public List<SysMenuEntity> queryListParentId(String parentId, List<String> menuIdList) {
        List<SysMenuEntity> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> queryListParentId(String parentId) {
        return baseMapper.selectList(new QueryWrapper<SysMenuEntity>().eq("PARENT_ID", parentId).orderByAsc("ORDER_NUM"));
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(String userId, Integer whereMenu) {
        //系统管理员，拥有最高权限
        if (Constant.SUPER_ADMIN.equals(userId)) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<String> menuIdList = sysUserService.queryAllMenuId(userId, whereMenu);
        return getAllMenuList(menuIdList);
    }

    @Override
    public List<SysMenuEntity> getStaffMenuList(String userId, Integer whereMenu) {

        //职员菜单列表
        List<String> menuIdList = sysStaffMenuService.queryAllMenuByUserId(userId, whereMenu, null);
        return getAllMenuList(menuIdList);
    }

    @Override
    public List<SysMenuEntity> getUserMenuDetailList(String userId, Integer whereMenu){

        List<String> menuIdList = new ArrayList<>();
        if (userId.equals(Constant.SUPER_ADMIN)){
            menuIdList = sysUserService.queryAllMenuId(null, whereMenu);
        } else {
            menuIdList = sysUserService.queryAllMenuId(userId, whereMenu);
        }

        List<SysMenuEntity> sysMenuEntityList = this.getSysMenuEntityList(menuIdList);

        return sysMenuEntityList;
    }

    @Override
    public List<SysMenuEntity> getStaffMenuDetailList(String userId, Integer whereMenu){
        List<String> menuIdList = sysStaffMenuService.queryAllMenuByUserId(userId, Constant.WhereMenu.PC.getValue(), whereMenu);

        List<SysMenuEntity> sysMenuEntityList = this.getSysMenuEntityList(menuIdList);

        return sysMenuEntityList;
    }

    @Override
    public List<SysMenuEntity> getSysMenuEntityList(List<String> menuIdList) {
        return baseMapper.getSysMenuEntityList(menuIdList);
    }

    @Override
    public boolean delete(String menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        Map<String, Object> map = new HashMap<>(2);
        map.put("menu_id", menuId);
        return sysRoleMenuService.removeByMap(map);
    }

    @Override
    public List<SysMenuEntity> queryList(Map<String, Object> params) {
        return baseMapper.queryList(params);
    }

    @Override
    public boolean add(SysMenuEntity menu) {
        String parentId = menu.getParentId();
        String maxId = baseMapper.queryMaxIdByParentId(parentId);

        menu.setMenuId(StringUtils.addOne(parentId, maxId));
        return this.save(menu);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuEntity> getAllMenuList(List<String> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId("0", menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<String> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<>();

        for (SysMenuEntity entity : menuList) {
            //目录
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }

}
