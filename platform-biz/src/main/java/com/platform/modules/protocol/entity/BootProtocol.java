package com.platform.modules.protocol.entity;

import java.util.Arrays;

/**
 * Created by lw on 2020/09/01 14:53
 * 机柜开机通信协议
 */
public class BootProtocol extends BaseProtocol {
    /**
     * 随机数
     */
    private Integer[] Rand = new Integer[4];
    /**
     * 魔术字
     */
    private Integer[] Magic = new Integer[2];
    /**
     * BoxID 的长度
     */
    private Integer[] BoxIDLen = new Integer[2];
    /**
     * 柜机 SN和柜身的二维码对应
     */
    private String BoxID;
    /**
     * 加密数据体的长度
     */
    private Integer[] ReqDataLen = new Integer[2];
    /**
     * 加密的数据体
     */
    private String ReqData;

    public Integer[] getRand() {
        return Rand;
    }

    public void setRand(Integer[] rand) {
        Rand = rand;
    }

    public Integer[] getMagic() {
        return Magic;
    }

    public void setMagic(Integer[] magic) {
        Magic = magic;
    }

    public Integer[] getBoxIDLen() {
        return BoxIDLen;
    }

    public void setBoxIDLen(Integer[] boxIDLen) {
        BoxIDLen = boxIDLen;
    }

    public String getBoxID() {
        return BoxID;
    }

    public void setBoxID(String boxID) {
        BoxID = boxID;
    }

    public Integer[] getReqDataLen() {
        return ReqDataLen;
    }

    public void setReqDataLen(Integer[] reqDataLen) {
        ReqDataLen = reqDataLen;
    }

    public String getReqData() {
        return ReqData;
    }

    public void setReqData(String reqData) {
        ReqData = reqData;
    }

    @Override
    public String toString() {
        return "BootProtocol{" +
                "packetLen=" + Arrays.toString(getPacketLen()) +
                ", command=" + getCommand() +
                ", vsn=" + getVsn() +
                ", checkSum=" + getCheckSum() +
                ", token=" + Arrays.toString(getToken()) +
                "Rand=" + Arrays.toString(Rand) +
                ", Magic=" + Arrays.toString(Magic) +
                ", BoxIDLen=" + Arrays.toString(BoxIDLen) +
                ", BoxID='" + BoxID + '\'' +
                ", ReqDataLen=" + Arrays.toString(ReqDataLen) +
                ", ReqData='" + ReqData + '\'' +
                '}';
    }

    public BootProtocol() {
    }

}
