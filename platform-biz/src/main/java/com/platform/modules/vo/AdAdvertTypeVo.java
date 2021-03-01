package com.platform.modules.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdAdvertTypeVo {
    /**
     * 广告类型
     */
    @ApiModelProperty("名称")
    private String name;
    /**
     * 该广告类型数量
     */
    @ApiModelProperty("数量")
    private Integer number;
}
