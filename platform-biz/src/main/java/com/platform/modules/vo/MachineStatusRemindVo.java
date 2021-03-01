package com.platform.modules.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class MachineStatusRemindVo {

    @ApiModelProperty("设备编号")
    private String machineSn;

    @ApiModelProperty("设备状态")
    private String machineStatus;

    @ApiModelProperty("时间")
    private String date;

    @ApiModelProperty("备注")
    private String remark;
}
