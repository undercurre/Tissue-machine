package com.platform.modules.protocol.utils;

public enum FuncCodeEnum {

    CODE_0x60(0x60,"机柜登陆及响应"),
    CODE_0x61(0x61,"心跳及响应"),
    CODE_0x62(0x62,"查询机柜软件版本号及响应"),
    CODE_0x63(0x63,"设置机柜服务器地址及响应"),
    CODE_0x64(0x64,"查询机柜库存及响应"),
    CODE_0x65(0x65,"借充电宝及响应"),
    CODE_0x66(0x66,"还充电宝及响应"),
    CODE_0x67(0x67,"远程重启机柜及响应"),
    CODE_0x68(0x68,"远程升级及响应"),
    CODE_0x69(0x69,"查询ICCID"),
    CODE_0x6A(0x6A,"查询服务器地址及响应"),
    CODE_0x80(0x80,"强制弹出充电宝"),
    CODE_0x77(0x77,"查询机柜语音播报音量"),
    CODE_0x70(0x70,"设置机柜语音播报音量"),
    CODE_0x71(0x71, "查询机柜网络信息(仅 MOD)"),
    CODE_0x72(0x72,"查询机柜网络信息");

    private Integer code;

    private String msg;

    FuncCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "FuncCodeEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}

