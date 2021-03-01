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
package com.platform.modules.mp.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.utils.RestResponse;
import com.platform.modules.mp.entity.WxMpMenu;
import me.chanjar.weixin.common.bean.menu.WxMenu;

/**
 * 微信菜单业务
 *
 * @author lengleng
 * @date 2019-03-27 20:45:18
 */
public interface WxMenuService extends IService<WxMpMenu> {

    /**
     * 查询菜单信息
     *
     * @return
     */
    String getMenu(String appId);

    /**
     * 新增微信菜单
     *
     * @param wxMenus json
     * @return
     */
    Boolean menuCreateByDb(JSONObject wxMenus);

    /**
     * 发布到微信
     *
     * @return
     */
    boolean push(WxMenu menu);

    /**
     * 删除菜单
     * menuId 菜单ID
     *
     * @return
     */
    boolean delete(String menuId);

    /**
     * 从数据库删除菜单
     *
     * @param appId
     * @return
     */
    boolean menuDeleteByDb(String appId);
}
