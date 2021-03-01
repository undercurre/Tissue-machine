/*
 *
 *
 */
package com.platform.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 临时语音表实体
 *
 * @author zqh
 * @date 2021-01-06 14:52:52
 */
@Data
@TableName("AD_MACHINE_PROVISIONAL_VOICE")
public class AdMachineProvisionalVoiceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private String id;
    /**
     * 临时语音内容
     */
    private String content;
    /**
     * 机柜ID
     */
    private String machineId;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 机柜名字
     */
    @TableField(exist = false)
    private String machineName;
}
