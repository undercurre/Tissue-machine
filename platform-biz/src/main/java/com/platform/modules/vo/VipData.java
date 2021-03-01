package com.platform.modules.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data

@ApiModel(value="会员类数据")
public class VipData {

    @ApiModelProperty(value="平台激活会员总数")
    private Integer vipUserCount;
    @ApiModelProperty(value="平台激活会员比例 注册数量与实际激活会员卡数量比例")
    private BigDecimal userVipProportion;
    @ApiModelProperty(value="今日用户激活会员数量")
    private Integer dayAddVipCount;
    @ApiModelProperty(value="本周用户激活会员数量")
    private Integer weekAddVipCount;
    @ApiModelProperty(value="本月用户激活会员数量")
    private Integer monthAddVipCount;
    @ApiModelProperty(value="本季度用户激活会员数量")
    private Integer quarterAddVipCount;
    @ApiModelProperty(value="本年度用户激活会员数量")
    private Integer yearAddVipCount;
    @ApiModelProperty(value="会员用户待领取纸巾总数")
    private Integer vipTissueCount;
    @ApiModelProperty(value="会员男比例")
    private BigDecimal manProportion;
    @ApiModelProperty(value="会员女比例")
    private BigDecimal ladyProportion;
    @ApiModelProperty(value="会员未知性别比例")
    private BigDecimal unknowProportion;
    @ApiModelProperty(value="男会员总数")
    private Integer manCount;
    @ApiModelProperty(value="女会员总数")
    private Integer ladyCount;
    @ApiModelProperty(value="未知会员总数")
    private Integer unknowCount;
}
