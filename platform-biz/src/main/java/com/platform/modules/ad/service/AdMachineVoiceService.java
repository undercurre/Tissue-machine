/*
 *
 *
 */
package com.platform.modules.ad.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.ad.entity.AdMachineVoiceEntity;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 机柜语音表Service接口
 *
 * @author dxd
 * @date 2020-11-06 09:52:48
 */
public interface AdMachineVoiceService extends IService<AdMachineVoiceEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdMachineVoiceEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询机柜语音表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params, SysUserEntity user);

    /**
     * 新增机柜语音表
     *
     * @param adMachineVoice 机柜语音表
     * @return 新增结果
     */
    boolean add(AdMachineVoiceEntity adMachineVoice);

    /**
     * 根据主键更新机柜语音表
     *
     * @param adMachineVoice 机柜语音表
     * @return 更新结果
     */
    boolean update(AdMachineVoiceEntity adMachineVoice);

    /**
     * 根据主键删除机柜语音表
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);

    AdMachineVoiceEntity queryById(String id);

    List<String> queryValidList();

    boolean addProvisionalVoice(AdMachineVoiceEntity adMachineVoice);
}
