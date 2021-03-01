package com.platform.modules.protocol.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
/**
 *不能用@Data注解
 **/
@JSONType(orders = { "ID", "TP","CM","KY","DATA"})
public class ResponProtocol  implements Serializable {
    /**
     * 设备ID号
     */
    @JSONField(name = "ID",ordinal=1)
    private String ID;
    /**
     * 设备类型
     */
    @JSONField(name = "TP",ordinal=2)
    private  Integer TP;
    /**
     * 操作指令
     */
    @JSONField(name = "CM",ordinal=3)
    private String CM;
    /**
     * 密钥
     */
    @JSONField(name = "KY",ordinal=6)
    private String KY;
    /**
     * 数据内容
     */
    @JSONField(name = "DATA",ordinal=7)
    private String DATA;


    @JsonProperty("ID")
    public String getID() {
        return ID;
    }
    @JsonProperty("ID")
    public void setID(String ID) {
        this.ID = ID;
    }
    @JsonProperty("CM")
    public String getCM() {
        return CM;
    }
    @JsonProperty("CM")
    public void setCM(String CM) {
        this.CM = CM;
    }
    @JsonProperty("TP")
    public Integer getTP() {
        return TP;
    }
    @JsonProperty("TP")
    public void setTP(Integer TP) {
        this.TP = TP;
    }
    @JsonProperty("KY")
    public String getKY() {
        return KY;
    }
    @JsonProperty("KY")
    public void setKY(String KY) {
        this.KY = KY;
    }
    @JsonProperty("DATA")
    public String getDATA() {
        return DATA;
    }
    @JsonProperty("DATA")
    public void setDATA(String DATA) {
        this.DATA = DATA;
    }
}