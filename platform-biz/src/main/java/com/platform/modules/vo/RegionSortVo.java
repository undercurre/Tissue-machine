package com.platform.modules.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegionSortVo {

    @ApiModelProperty("设备分布地区名称")
    private String regionName;
    @ApiModelProperty("设备分布数量")
    private Integer number;
    @ApiModelProperty("会员分布数量")
    private Integer userNumber;
    @ApiModelProperty("纸巾派发分布数量")
    private Integer tissueNumber;
}
