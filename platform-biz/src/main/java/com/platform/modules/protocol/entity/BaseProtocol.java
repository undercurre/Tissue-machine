package com.platform.modules.protocol.entity;

import com.platform.modules.protocol.utils.FuncCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * Created by lw on 2020/09/01 14:53
 * 机柜基础通信协议
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseProtocol {
    /**
     * 数据包的长度
     */
    private Integer[] packetLen = new Integer[2];
    /**
     * 命令字
     */
    private FuncCodeEnum command;
    /**
     * 协议版本号
     */
    private Integer vsn;
    /**
     * 有效数据（ Payload）的字节异或
     */
    private Integer checkSum;
    /**
     * 会话令牌
     */
    private Integer[] token = new Integer[4];


    @Override
    public String toString() {
        return "BaseProtocol{" +
                "packetLen=" + Arrays.toString(packetLen) +
                ", command=" + command +
                ", vsn=" + vsn +
                ", checkSum=" + checkSum +
                ", token=" + Arrays.toString(token) +
                '}';
    }

    public String getMessage() {
        StringBuffer sb = new StringBuffer();
        String interval = " ";
        Integer[] packetLen = getPacketLen();
        FuncCodeEnum command = getCommand();
        Integer vsn = getVsn();
        Integer checkSum = getCheckSum();
        Integer[] token = getToken();

        for (int i = 0; i < packetLen.length; i++) {
            sb.append(Integer.toHexString(packetLen[i])).append(interval);
        }
        sb.append(Integer.toHexString(command.getCode())).append(interval);
        sb.append(Integer.toHexString(vsn)).append(interval);
        sb.append(Integer.toHexString(checkSum)).append(interval);
        for (int i = 0; i < token.length; i++) {
            sb.append(Integer.toHexString(token[i])).append(interval);
        }
        String message = sb.toString();
        return message.substring(0, message.length() - 1);
    }
}
