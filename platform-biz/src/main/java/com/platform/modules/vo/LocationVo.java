package com.platform.modules.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationVo {

    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 是否返回周边信息 0:返回 1:不返回
     */
    private Integer get_poi;
}
