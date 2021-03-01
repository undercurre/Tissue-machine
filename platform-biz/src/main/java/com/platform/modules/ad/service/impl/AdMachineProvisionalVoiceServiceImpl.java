/*
 *
 *
 */
package com.platform.modules.ad.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.ad.dao.AdMachineProvisionalVoiceDao;
import com.platform.modules.ad.entity.AdMachineProvisionalVoiceEntity;
import com.platform.modules.ad.service.AdMachineProvisionalVoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 临时语音表Service实现类
 *
 * @author zqh
 * @date 2021-01-06 14:52:52
 */
@Service("adMachineProvisionalVoiceService")
public class AdMachineProvisionalVoiceServiceImpl extends ServiceImpl<AdMachineProvisionalVoiceDao, AdMachineProvisionalVoiceEntity> implements AdMachineProvisionalVoiceService {

    @Override
    public List<AdMachineProvisionalVoiceEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<AdMachineProvisionalVoiceEntity> page = new Query<AdMachineProvisionalVoiceEntity>(params).getPage();
        return page.setRecords(baseMapper.selectAdMachineProvisionalVoicePage(page, params));
    }

    @Override
    public boolean add(AdMachineProvisionalVoiceEntity adMachineProvisionalVoice) {
        return this.save(adMachineProvisionalVoice);
    }

    @Override
    public boolean update(AdMachineProvisionalVoiceEntity adMachineProvisionalVoice) {
        return this.updateById(adMachineProvisionalVoice);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
