/*
 *
 *
 */
package com.platform.modules.ad.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.ad.entity.AdMachineProvisionalVoiceEntity;

import java.util.List;
import java.util.Map;

/**
 * 临时语音表Service接口
 *
 * @author zqh
 * @date 2021-01-06 14:52:52
 */
public interface AdMachineProvisionalVoiceService extends IService<AdMachineProvisionalVoiceEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdMachineProvisionalVoiceEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询临时语音表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增临时语音表
     *
     * @param adMachineProvisionalVoice 临时语音表
     * @return 新增结果
     */
    boolean add(AdMachineProvisionalVoiceEntity adMachineProvisionalVoice);

    /**
     * 根据主键更新临时语音表
     *
     * @param adMachineProvisionalVoice 临时语音表
     * @return 更新结果
     */
    boolean update(AdMachineProvisionalVoiceEntity adMachineProvisionalVoice);

    /**
     * 根据主键删除临时语音表
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
}
