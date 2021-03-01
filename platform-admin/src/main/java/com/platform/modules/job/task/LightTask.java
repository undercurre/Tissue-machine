package com.platform.modules.job.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.ad.entity.AdMachineLightEntity;
import com.platform.modules.ad.entity.AdMachineOperateEntity;
import com.platform.modules.ad.entity.AdMachineReportEntity;
import com.platform.modules.ad.entity.AdTissueMachineEntity;
import com.platform.modules.ad.service.AdMachineLightService;
import com.platform.modules.ad.service.AdMachineOperateService;
import com.platform.modules.ad.service.AdMachineReportService;
import com.platform.modules.ad.service.AdTissueMachineService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component("LightTask")
public class LightTask {

    @Autowired
    private AdMachineLightService adMachineLightService;

    @Autowired
    private AdMachineReportService adMachineReportService;

    @Autowired
    private AdTissueMachineService adTissueMachineService;

    @Autowired
    private AdMachineOperateService adMachineOperateService;

    public RestResponse lightChangeTask () {
        System.out.println(adTissueMachineService.list().toString());
        System.out.println(adTissueMachineService.list().size());
        adTissueMachineService.list().forEach( item -> {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String dateStr = dateFormat.format(date);

            List<AdMachineLightEntity> adMachineLightEntityList = adMachineLightService.list(new QueryWrapper<AdMachineLightEntity>()
                    .eq("MACHINE_SN", item.getSn())
                    .lt("START_TIME", dateStr)
                    .gt("END_TIME", dateStr));

            AdMachineOperateEntity adMachineOperateEntity = new AdMachineOperateEntity();
            adMachineOperateEntity.setMachineId(item.getId());
            adMachineOperateEntity.setOperateCode(Constant.MachineOperateType.LIGHT.getValue());
            adMachineOperateEntity.setCreateTime(date);
            adMachineOperateEntity.setStatus(Constant.ExecType.WZX.getValue());
            adMachineOperateEntity.setSortLevel(adMachineOperateService.count() + 1);

            // 如果在开灯的时间段中，就向机柜操作表中加入开灯指令
            if (adMachineLightEntityList != null && adMachineLightEntityList.size() > 0) {
                adMachineOperateEntity.setContent("01");
                item.setLightStatus(1);
            } else { // 如果不在开灯的时间段中，就向机柜操作表中加入关灯指令
                adMachineOperateEntity.setContent("00");
                item.setLightStatus(0);
            }
            adTissueMachineService.updateById(item);
            adMachineOperateService.add(adMachineOperateEntity);
        });
        return RestResponse.success();
    }
}
