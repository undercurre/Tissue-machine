package com.platform.modules.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MachineTotalVo {

    @ApiModelProperty("设备投放总数量")
    private Integer machineTotal;

    @ApiModelProperty("在线设备数量")
    private Integer onlineMachineTotal;

    @ApiModelProperty("异常设备数量")
    private Integer unusualMachineTotal;

    @ApiModelProperty("市级机柜数量排行")
    private List<RegionSortVo> cityTissueMachineSort;

    @ApiModelProperty("区级机柜数量排行")
    private List<RegionSortVo> districtTissueMachineSort;
}
