package com.platform.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券列表实体
 *
 * @author gjw
 * @date 2020-12-11 10:51:39
 */
@Data
@TableName("AD_COUPON")
public class AdCouponEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 优惠券标题
     */
    private String title;
    /**
     * 优惠券编号
     */
    private String couponSn;
    /**
     * 优惠券类型：1:代金券 2:折扣
     */
    private Integer couponType;
    /**
     * 最低消费金额
     */
    private BigDecimal minPrice;
    /**
     * 优惠金额
     */
    private BigDecimal subPrice;
    /**
     * 折扣率
     */
    private BigDecimal discount;
    /**
     * 有效期开始时间
     */
    private Date beginTime;
    /**
     * 有效期结束时间
     */
    private Date endTime;
    /**
     * 优惠券数量
     */
    private Integer totalCount;
    /**
     * 已发放数量
     */
    private Integer sendCount;
    /**
     * 指定使用类型 0：代理商发放
     */
    private Integer limitType;
    /**
     * 状态 1:可领用 2：过期 3：禁用
     */
    private Integer status;
    /**
     * 代理商ID
     */
    private String agentId;
    /**
     * 每人限领数量
     */
    private Integer limitUser;
}
