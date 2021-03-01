/*
 *
 *
 */
package com.platform.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.ad.entity.AdMachineProvisionalVoiceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 临时语音表Dao
 *
 * @author zqh
 * @date 2021-01-06 14:52:52
 */
@Mapper
public interface AdMachineProvisionalVoiceDao extends BaseMapper<AdMachineProvisionalVoiceEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<AdMachineProvisionalVoiceEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<AdMachineProvisionalVoiceEntity> selectAdMachineProvisionalVoicePage(IPage page, @Param("params") Map<String, Object> params);
}
