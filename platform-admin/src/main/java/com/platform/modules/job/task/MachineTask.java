package com.platform.modules.job.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.utils.Constant;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdMachineOperateEntity;
import com.platform.modules.ad.entity.AdMachineReportEntity;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.service.AdMachineOperateService;
import com.platform.modules.ad.service.AdMachineReportService;
import com.platform.modules.ad.service.AdTissueMachineService;
import com.platform.modules.sys.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Component("MachineTask")
public class MachineTask {

    @Autowired
    private AdTissueMachineService adTissueMachineService;
    @Autowired
    private AdMachineOperateService adMachineOperateService;
    @Autowired
    private AdMachineReportService adMachineReportService;
    @Autowired
    private SysConfigService sysConfigService;

    @Transactional(rollbackFor = Exception.class)
    public RestResponse sendMachineTask() {
        log.info("--------------------------开始执行查询机柜信息--------------------------");
        List<AdTissueMachineEntity> adTissueMachineEntityList = adTissueMachineService.list();
        Date date = new Date();
        adTissueMachineEntityList.forEach(item -> {

            // 查看机柜上报距离上次上报时间是否超时
            AdMachineReportEntity adMachineReportEntity = adMachineReportService.getOne(new QueryWrapper<AdMachineReportEntity>()
                    .eq("MACHINE_SN", item.getSn()));
            if (adMachineReportEntity != null) {
                if (DateUtils.addDateMinutes(adMachineReportEntity.getReportTime(), Integer.parseInt(sysConfigService.getValue("MACHINE_REPORT_TIMEOUT"))).before(date)) {
                    item.setStatus(Constant.MachineStatus.LX.getValue());
                    adTissueMachineService.sendMessage(item,Constant.MachineStatus.LX.getValue());
                    adTissueMachineService.update(item);
                }
            } else {
                item.setStatus(Constant.MachineStatus.LX.getValue());
                adTissueMachineService.update(item);
            }

            AdMachineOperateEntity adMachineOperateEntity = new AdMachineOperateEntity();
            String machineId = item.getId();
            // 请求获取CCID流量使用情况
            adMachineOperateEntity.setMachineId(machineId);
            adMachineOperateEntity.setOperateCode(Constant.MachineOperateType.CCID.getValue());
            adMachineOperateEntity.setCreateTime(date);
            adMachineOperateEntity.setStatus(Constant.ExecType.WZX.getValue());
            adMachineOperateEntity.setSortLevel(adMachineOperateService.count() + 1);
            adMachineOperateService.save(adMachineOperateEntity);
            // 获取设备版本号
            adMachineOperateEntity.setId(null);
            adMachineOperateEntity.setMachineId(machineId);
            adMachineOperateEntity.setOperateCode(Constant.MachineOperateType.VERSION.getValue());
            adMachineOperateEntity.setCreateTime(date);
            adMachineOperateEntity.setStatus(Constant.ExecType.WZX.getValue());
            adMachineOperateEntity.setSortLevel(adMachineOperateService.count() + 1);
            adMachineOperateService.save(adMachineOperateEntity);
            // 如果设备开启了定位，获取设备的经纬度
            if(item.getIsOpenLocate()==1) {
                adMachineOperateEntity.setId(null);
                adMachineOperateEntity.setMachineId(machineId);
                adMachineOperateEntity.setOperateCode(Constant.MachineOperateType.LOCATION.getValue());
                adMachineOperateEntity.setCreateTime(date);
                adMachineOperateEntity.setStatus(Constant.ExecType.WZX.getValue());
                adMachineOperateEntity.setSortLevel(adMachineOperateService.count() + 1);
                adMachineOperateService.save(adMachineOperateEntity);
            }
        });
        log.info("--------------------------结束执行查询机柜信息--------------------------");
        return RestResponse.success();
    }
}
