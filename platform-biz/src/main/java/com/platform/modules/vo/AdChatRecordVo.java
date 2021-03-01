package com.platform.modules.vo;

import lombok.Data;


import java.util.Date;

@Data
public class AdChatRecordVo {

    /**
     * 用户openId
     */
    private String openid;
    /**
     * 操作码
     */
    private Integer opercode;
    /**
     * 聊天记录
     */
    private String text;
    /**
     * 信息发送时间
     */
    private String time;
    /**
     * 客服ID
     */
    private String worker;
}
