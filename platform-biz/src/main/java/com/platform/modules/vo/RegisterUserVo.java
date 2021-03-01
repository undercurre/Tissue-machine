package com.platform.modules.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RegisterUserVo {

    @ApiModelProperty("平台用户注册总数")
    private Integer registerTotal;

    @ApiModelProperty("本年注册总数")
    private Integer thisYearRegisterTotal;

    @ApiModelProperty("本季度注册总数")
    private Integer thisSeasonRegisterTotal;

    @ApiModelProperty("本月注册总数")
    private Integer thisMonthRegisterTotal;

    @ApiModelProperty("本周注册总数")
    private Integer thisWeekRegisterTotal;

    @ApiModelProperty("本日注册总数")
    private Integer todayRegisterTotal;

    @ApiModelProperty("男性用户比例")
    private BigDecimal maleRatio;

    @ApiModelProperty("女性用户比例")
    private BigDecimal femaleRatio;

    @ApiModelProperty("未知用户比例")
    private BigDecimal unKnownRatio;

    @ApiModelProperty("市级会员数量排行")
    private List<RegionSortVo> cityUserSort;

    @ApiModelProperty("区级会员数量排行")
    private List<RegionSortVo> districtUserSort;
}
