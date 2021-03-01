package com.platform.modules.protocol.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by lw on 2020年10月15日 15:46:12
 * 机柜基础通信协议
 */
@Data
public class TissueProtocol implements Serializable {

    /**
     * 设备ID号
     */
    @JsonProperty("ID")
    private String ID;
    /**
     * 信号强度
     */
    @JsonProperty("SG")
    private Integer SG;
    /**
     * 设备类型
     */
    @JsonProperty("TP")
    private  Integer TP;
    /**
     * 命令
     */
    @JsonProperty("CM")
    private String CM;
    /**
     * 电池电量
     */
    @JsonProperty("BT")
    private Integer BT;
    /**
     * 设备状态
     */
    @JsonProperty("ST")
    private Integer ST;
    /**
     * 错误代码
     */
    @JsonProperty("EC")
    private  String EC;
    /**
     * 密钥
     */
    @JsonProperty("KY")
    private String KY;
    /**
     * 数据内容
     */
    @JsonProperty("DATA")
    private String DATA;

}
