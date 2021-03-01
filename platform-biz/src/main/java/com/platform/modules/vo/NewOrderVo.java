package com.platform.modules.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NewOrderVo {

    @ApiModelProperty("模板标题")
    private String title;

    @ApiModelProperty("工单编号")
    private String workOrderId;

    @ApiModelProperty("服务类型")
    private String workType;

    @ApiModelProperty("下单时间")
    private String date;

    @ApiModelProperty("客户姓名")
    private String name;

    @ApiModelProperty("客户地址")
    private String address;

    @ApiModelProperty("备注")
    private String remark;

}
