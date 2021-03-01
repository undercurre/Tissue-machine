/*
 *
 *
 */
package com.platform.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.common.utils.StringUtils;
import com.platform.modules.ad.dao.AdMachineVoiceDao;
import com.platform.modules.ad.entity.AdMachineOperateEntity;
import com.platform.modules.ad.entity.AdMachineProvisionalVoiceEntity;
import com.platform.modules.ad.entity.AdMachineVoiceEntity;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.service.AdMachineOperateService;
import com.platform.modules.ad.service.AdMachineProvisionalVoiceService;
import com.platform.modules.ad.service.AdMachineVoiceService;
import com.platform.modules.ad.service.AdTissueMachineService;
import com.platform.modules.sys.dao.SysUserDao;
import com.platform.modules.sys.entity.SysUserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 机柜语音表Service实现类
 *
 * @author dxd
 * @date 2020-11-06 09:52:48
 */
@Service("adMachineVoiceService")
public class AdMachineVoiceServiceImpl extends ServiceImpl<AdMachineVoiceDao, AdMachineVoiceEntity> implements AdMachineVoiceService {
    @Autowired
    private AdMachineOperateService adMachineOperateService;
    @Autowired
    private AdTissueMachineService adTissueMachineService;
    @Autowired
    private AdMachineProvisionalVoiceService adMachineProvisionalVoiceService;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public List<AdMachineVoiceEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params, SysUserEntity user) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        params.put("date", new Date());
        Integer level = sysUserDao.getUserLevel(user.getUserId());
        Page<AdMachineVoiceEntity> page = new Query<AdMachineVoiceEntity>(params).getPage();
        if (user.getIsStaff() == 1) {
            level = Constant.roleLevel.DL.getValue();
            user.setUserId(user.getCreateUserId());
        }

        if (level == Constant.roleLevel.ZDL.getValue()) {
            return page.setRecords(baseMapper.selectAdMachineVoicePage(page, params));
        } else {
            List<AdMachineVoiceEntity> list = baseMapper.selectAdMachineVoicePage(page, params);
            list = list.stream().filter(e-> StringUtils.isNotBlank(e.getAgentId()))
                    .filter(e->e.getAgentId().equals(user.getUserId())).collect(Collectors.toList());
            return page.setRecords(list);
        }
    }

    @Override
    @Transactional
    public boolean add(AdMachineVoiceEntity adMachineVoice) {
        System.out.println(adMachineVoice);
        Date date = new Date();
        AdMachineVoiceEntity content = new AdMachineVoiceEntity();
        content.setContent(adMachineVoice.getContent());
        content.setValidTime(adMachineVoice.getValidTime());
        content.setCreateTime(date);
        this.save(content);

        for(String machineId :adMachineVoice.getMachineIds()){
            // 先删除机柜语音表里原来有的
            AdMachineVoiceEntity machineVoiceEntity = this.getOne(new QueryWrapper<AdMachineVoiceEntity>()
                    .eq("MACHINE_ID", machineId)
                    .last("LIMIT 1 "));
            if (machineVoiceEntity != null) {
                this.delete(machineVoiceEntity.getId());
            }
            if(StringUtils.isNotBlank(machineId)) {
                // 修改机柜语音表
                AdMachineVoiceEntity adMachineVoiceEntity = new AdMachineVoiceEntity();
                adMachineVoiceEntity.setSuperiorid(content.getId());
                adMachineVoiceEntity.setMachineId(machineId);
                adMachineVoiceEntity.setCreateTime(new Date());
                adMachineVoiceEntity.setValidTime(content.getValidTime());
                this.save(adMachineVoiceEntity);

                // 更新机柜表语音内容
                AdTissueMachineEntity machine = adTissueMachineService.getById(machineId);
                machine.setContent(adMachineVoice.getContent());
                adTissueMachineService.updateById(machine);

                // 修改机柜操作表
                AdMachineOperateEntity adMachineOperateEntity = new AdMachineOperateEntity();
                adMachineOperateEntity.setMachineId(machineId);
                adMachineOperateEntity.setContent(content.getContent());
                adMachineOperateEntity.setCreateTime(new Date());
                adMachineOperateEntity.setOperateCode(Constant.MachineOperateType.VOICE.getValue());
                adMachineOperateEntity.setStatus(Constant.ExecType.WZX.getValue());
                adMachineOperateEntity.setSortLevel(adMachineOperateService.count() + 1);
                adMachineOperateService.save(adMachineOperateEntity);
            }
        }
        return true;
    }

    /**
     * 根据语音内容追加设备
     * @param adMachineVoice 机柜语音表
     * @return
     */
    @Override
    public boolean update(AdMachineVoiceEntity adMachineVoice) {
        System.out.println(adMachineVoice);
        Date date = new Date();
        for(String machineId :adMachineVoice.getMachineIds()){
            if(StringUtils.isNotBlank(machineId)) {
                //加入到语音表
                AdMachineVoiceEntity adMachineVoiceEntity = new AdMachineVoiceEntity();
                adMachineVoiceEntity.setSuperiorid(adMachineVoice.getId());
                adMachineVoiceEntity.setCreateTime(date);
                adMachineVoiceEntity.setValidTime(adMachineVoice.getValidTime());
                adMachineVoiceEntity.setMachineId(machineId);
                this.save(adMachineVoiceEntity);

                // 更新机柜表语音内容
                AdTissueMachineEntity machine = adTissueMachineService.getById(machineId);
                machine.setContent(adMachineVoice.getContent());
                adTissueMachineService.updateById(machine);

                // 加入机柜操作表
                AdMachineOperateEntity adMachineOperateEntity = new AdMachineOperateEntity();
                adMachineOperateEntity.setMachineId(machineId);
                adMachineOperateEntity.setContent(adMachineVoice.getContent());
                adMachineOperateEntity.setCreateTime(date);
                adMachineOperateEntity.setOperateCode(Constant.MachineOperateType.VOICE.getValue());
                adMachineOperateEntity.setStatus(Constant.ExecType.WZX.getValue());
                adMachineOperateEntity.setSortLevel(adMachineOperateService.count() + 1);
                adMachineOperateService.save(adMachineOperateEntity);
            }
        }
        return true;
    }

    @Override
    public boolean delete(String id) {
        AdMachineVoiceEntity adMachineVoiceEntity = this.getOne(new QueryWrapper<AdMachineVoiceEntity>().eq("MACHINE_ID", id).gt("VALID_TIME", new Date()));
        if (adMachineVoiceEntity != null) {
            return this.removeById(adMachineVoiceEntity.getId());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public AdMachineVoiceEntity queryById(String id) {
        AdMachineVoiceEntity adMachineVoiceEntity = this.baseMapper.queryById(id);
        //父级记录
        if(StringUtils.isNullOrEmpty(adMachineVoiceEntity.getContent())){
            AdMachineVoiceEntity byId = this.getById(id);
            adMachineVoiceEntity.setContent(byId.getContent());
        }
        ArrayList<String> machineIdList = new ArrayList<>();
        machineIdList.add(adMachineVoiceEntity.getMachineId());
        adMachineVoiceEntity.setMachineIds(machineIdList);
        return adMachineVoiceEntity;
    }

    @Override
    public List<String> queryValidList() {
        return this.baseMapper.queryValidList();
    }

    @Override
    public boolean addProvisionalVoice(AdMachineVoiceEntity adMachineVoice) {
        for(String machineId :adMachineVoice.getMachineIds()){
            if(StringUtils.isNotBlank(machineId)){
                AdTissueMachineEntity machine = adTissueMachineService.getById(machineId);
                Date date = new Date();
                int count = adMachineOperateService.count();
                // 加入机柜操作表 先加临时语音 后将原来语音加回
                AdMachineOperateEntity adMachineOperateEntity = new AdMachineOperateEntity();
                adMachineOperateEntity.setMachineId(machineId);
                adMachineOperateEntity.setContent(adMachineVoice.getContent());
                adMachineOperateEntity.setCreateTime(date);
                adMachineOperateEntity.setOperateCode(Constant.MachineOperateType.VOICE.getValue());
                adMachineOperateEntity.setStatus(Constant.ExecType.WZX.getValue());
                adMachineOperateEntity.setSortLevel(count+1);
                adMachineOperateService.save(adMachineOperateEntity);

                AdMachineOperateEntity pastOperate = new AdMachineOperateEntity();
                pastOperate.setMachineId(machineId);
                pastOperate.setCreateTime(date);
                pastOperate.setOperateCode(Constant.MachineOperateType.VOICE.getValue());
                pastOperate.setStatus(Constant.ExecType.WZX.getValue());
                pastOperate.setContent(machine.getContent());
                pastOperate.setSortLevel(count+2);
                adMachineOperateService.save(pastOperate);

                AdMachineProvisionalVoiceEntity voiceEntity = new AdMachineProvisionalVoiceEntity();
                voiceEntity.setContent(adMachineVoice.getContent());
                voiceEntity.setCreateTime(date);
                voiceEntity.setMachineId(machineId);
                adMachineProvisionalVoiceService.add(voiceEntity);
            }
        }
        return true;
    }
}
