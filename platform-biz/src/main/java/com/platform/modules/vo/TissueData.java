package com.platform.modules.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value="纸巾类类数据")
public class TissueData {

    @ApiModelProperty(value="纸巾库存总数量 （后台纸巾商品总数）")
    private Integer tissueGoodsCount;
    @ApiModelProperty(value="纸巾总派发数量")
    private Integer tissueSellCount;
    @ApiModelProperty(value="今日纸巾派发数量")
    private Integer daySellTissueCount;
    @ApiModelProperty(value="本周纸巾派发数量")
    private Integer weekSellTissueCount;
    @ApiModelProperty(value="本月纸巾派发数量")
    private Integer monthSellTissueCount;
    @ApiModelProperty(value="本季度纸巾派发数量")
    private Integer quarterSellTissueCount;
    @ApiModelProperty(value="本年度纸巾派发数量")
    private Integer yearSellTissueCount;
    @ApiModelProperty(value="今日人均纸巾派发数量")
    private BigDecimal dayPerSellTissueCount;
    @ApiModelProperty(value="本周人均纸巾派发数量")
    private BigDecimal weekPerSellTissueCount;
    @ApiModelProperty(value="本月人均纸巾派发数量")
    private BigDecimal monthPerSellTissueCount;
    @ApiModelProperty(value="本季度人均纸巾派发数量")
    private BigDecimal quarterPerSellTissueCount;
    @ApiModelProperty(value="本年度人均纸巾派发数量")
    private BigDecimal yearPerSellTissueCount;
    @ApiModelProperty(value="市级纸巾派发数量排行")
    private List<RegionSortVo> cityTissueSellSort;
    @ApiModelProperty(value="区级纸巾派发数量排行")
    private List<RegionSortVo> districtTissueSellSort;
}
