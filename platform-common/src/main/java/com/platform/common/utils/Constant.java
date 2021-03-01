/*
 *
 *      Copyright (c) 2018-2099, lipengjun All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the fly2you.cn developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lipengjun (939961241@qq.com)
 *
 */
package com.platform.common.utils;

/**
 * 常量
 *
 * @author zqh
 */
public class Constant {
    public static final String WEBSOCKET_USER = "WEBSOCKET_USER";

    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    /**
     * 商城订单
     */
    public static final Integer NORMAL_ORDER = 1;
    /**
     * 店铺自提订单
     */
    public static final Integer SHOPS_ORDER = 2;
    /**
     * 秒杀订单
     */
    public static final Integer SECKILL_ORDER = 3;
    /**
     * 超级管理员ID
     */
    public static final String SUPER_ADMIN = "1";
    /**
     * 超级管理员所属机构
     */
    public static final String SUPER_ADMIN_ORG = "01";

    public static final String DEFAULT_PW = "888888";

    /**
     * 6小时后过期
     */
    public static final int EXPIRE = 3600 * 6;

    /**
     * 移动端--720小时(30天)后过期
     */
    public static final int APP_EXPIRE = 3600 * 720;

    /**
     * 下单支付过期时间，单位分
     */
    public static final String ORDER_EXPIRE = "ORDER_EXPIRE";

    /**
     * 云存储配置KEY
     */
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

    /**
     * 短信配置KEY
     */
    public final static String SMS_CONFIG_KEY = "SMS_CONFIG_KEY";
    /**
     * 邮费
     */
    public final static String SHIPPING_FEE = "SHIPPING_FEE";
    /**
     * 免运费门槛
     */
    public final static String SHIPPING_FEE_FREE = "SHIPPING_FEE_FREE";
    /**
     * 短信
     */
    public final static String PRE_SMS = "PRE_SMS:";
    /**
     * 是否开启余额支付
     */
    public final static String RECHARGE_STATUS = "RECHARGE_STATUS";

    /**
     * 是否开启分销商申请审核
     */
    public final static String DISTRIBUTION_AUDIT = "DISTRIBUTION_AUDIT";
    /**
     * 是否开启提现审核
     */
    public final static String WITHDRAW_AUDIT = "WITHDRAW_AUDIT";
    /**
     * 一级分销提成比例
     */
    public final static String COMMISSION_TYPE_DIST_1 = "COMMISSION_TYPE_DIST_1";
    /**
     * 二级分销提成比例
     */
    public final static String COMMISSION_TYPE_DIST_2 = "COMMISSION_TYPE_DIST_2";
    /**
     * 一级推广提成比例
     */
    public final static String COMMISSION_TYPE_PROMO_1 = "COMMISSION_TYPE_PROMO_1";
    /**
     * 二级推广提成比例
     */
    public final static String COMMISSION_TYPE_PROMO_2 = "COMMISSION_TYPE_PROMO_2";
    /**
     * 单次最低提现
     */
    public final static String WITHDRAW_SINGLE_LOWEST = "WITHDRAW_SINGLE_LOWEST";
    /**
     * 单次最高提现
     */
    public final static String WITHDRAW_SINGLE_HIGHEST = "WITHDRAW_SINGLE_HIGHEST";
    /**
     * 单日最高提现额度
     */
    public final static String WITHDRAW_DAY_HIGHEST = "WITHDRAW_DAY_HIGHEST";
    /**
     * 手续费最小值
     */
    public final static String COMMISSION_CHARGE_MIN = "COMMISSION_CHARGE_MIN";
    /**
     * 手续费比例
     */
    public final static String COMMISSION_CHARGE_RATIO = "COMMISSION_CHARGE_RATIO";
    /**
     * 收货后可申请退款时间
     */
    public final static String ALLOW_REFUND_TIME = "ALLOW_REFUND_TIME";
    /**
     * 机柜出纸异常订单数阈值
     */
    public final static String MACHINE_ORDER_FAIL_COUNT = "MACHINE_ORDER_FAIL_COUNT";
    /**
     * 代理设备订单价格和获得的收益的比例
     */
    public final static String AGENT_BALANCE_ORDER_PRICE = "AGENT_BALANCE_ORDER_PRICE";
    /**
     * 支付时使用优惠券后最低支付金额（单位：元）
     */
    public final static String MIN_PAY_MONEY = "MIN_PAY_MONEY";
    /**
     * 权限前缀
     */
    public static final String SESSION = "SESSION:";

    /**
     * 系统缓存前缀
     */
    public static final String SYS_CACHE = "SYS_CACHE:";

    /**
     * 业务系统缓存前缀
     */
    public static final String MTM_CACHE = "MTM_CACHE:";
    /**
     * sysconfig机柜上报时密钥的KEY
     */
    public final static String MACHINE_SEND_KEY = "MACHINE_SEND_KEY";
    /**
     * 机柜电量预警线
     */
    public final static String MACHINE_ELECTRICITY_WARING = "MACHINE_ELECTRICITY_WARING";
    /**
     * 出货页面请求时间(秒数)
     */
    public final static String INSHIPMENT_OVERTIME = "INSHIPMENT_OVERTIME";

    /**
     * sysconfig时服务器返回时候的密钥的KEY
     */
    public static final String SERVER_RESPON_KEY = "SERVER_RESPON_KEY";

    public static final String STR_ZERO = "0";
    public static final String STR_ONE = "1";
    public static final String STR_TWO = "2";
    public static final String STR_THREE = "3";
    public static final String STR_FOUR = "4";

    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;

    /**
     * 未关注
     */
    public static final int UNSUBSCRIBE = 0;
    /**
     * 关注
     */
    public static final int SUBSCRIBE = 1;
    /**
     * 空字符串
     */
    public static final String BLANK = "";
    /**
     * 斜杠
     */
    public static final String SLASH = "/";
    /**
     * 逗号
     */
    public static final String COMMA = ",";
    /**
     * 句号
     */
    public static final String DOT = ".";
    /**
     * 冒号
     */
    public static final String COLON = ":";
    /**
     * 下划线
     */
    public static final String UNDERSCORE = "_";
    /**
     * 换行符
     */
    public static final String LINE_BREAK = "\\r\\n";

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    public static final String BPMN20 = ".bpmn20.xml";

    public static final String IMAGE = "image";

    public static final String XML = "xml";
    public static final String PNG = "png";
    public static final String BAR = "bar";
    public static final String ZIP = "zip";
    public static final String BPMN = "bpmn";

    /**
     * 系统邮件签名
     */
    public static final String SIGNATURE_STR = "<br><font color='red'>-------------------------------------------------------------------<br>以上内容为邮件系统自动发送，请勿直接回复。</font>";

    /**
     * 系统自动邮件
     */
    public static final int SYS_SEND = 0;
    /**
     * 操作人主动邮件
     */
    public static final int USER_SEND = 1;

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3),
        /**
         * 服务器存储
         */
        DISCK(4),
        /**
         * FastDFS
         */
        FAST_DFS(5);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 订单状态
     */
    public enum OrderStatus {

        /**
         * 0:订单创建成功等待付款
         */
        DFK(0),
        /**
         * 1 已支付
         */
        YZF(1),
        /**
         * 2 退款中
         */
        TKZ(2),
        /**
         * 3 已退款
         */
        YTK(3),
        /**
         * 4 已失效
         */
        YSX(4);

        private Integer value;

        OrderStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 发货状态，商品配送情况
     */
    public enum ShippingStatus {
        /**
         * 1:未发货
         */
        WFH(1),
        /**
         * 2:已发货
         */
        YFH(2),
        /**
         * 3:已收货
         */
        YSH(3),
        /**
         * 4:退货
         */
        TH(4);

        private Integer value;

        ShippingStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 付款状态
     */
    public enum PayStatus {
        /**
         * 1:未付款
         */
        WFK(1),
        /**
         * 2:付款中
         */
        FKZ(2),
        /**
         * 3:已付款
         */
        YFK(3),
        /**
         * 4:退款
         */
        TK(4);

        private Integer value;

        PayStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 优惠券类型：1:代金券 2:折扣
     */
    public enum CouponType {
        /**
         * 1:代金券
         */
        DJQ(1),
        /**
         * 2:折扣
         */
        ZK(2);

        private  Integer value;

        CouponType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 优惠券使用状态
     */
    public enum CouponStatus {
        /**
         * 0: 未使用
         */
        WSY(0),
        /**
         * 1: 已使用
         */
        YSY(1),
        /**
         * 2: 已过期
         */
        YGQ(2);

        private  Integer value;

        CouponStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 用户下单来源类型
     */
    public enum FromType {
        /**
         * 1:微信小程序
         */
        MA(1),
        /**
         * 2:微信公众号
         */
        MP(2),
        /**
         * 3:app
         */
        APP(3),
        /**
         * 4:H5
         */
        H5(4),
        /**
         * 支付宝小程序
         */
        ALI(5),
        /**
         * QQ小程序
         */
        QQ(6);

        private  Integer value;

        FromType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 订单类型
     */
    public enum OrderType {
        /**
         * 1:普通订单
         */
        PTDD(1),
        /**
         * 2:免费领取
         */
        MFLQ(2),
        /**
         * 3:购买会员
         */
        GMHY(3),
        /**
         * 4:余额充值
         */
        YECZ(4);

        private  Integer value;

        OrderType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 提成分类
     */
    public enum CommissionType {
        /**
         * 1:一级分销提成
         */
        DIST_1(1),
        /**
         * 2:二级分销提成
         */
        DIST_2(2),
        /**
         * 3:一级promotion
         */
        PROMO_1(3),
        /**
         * 4:二级推广提成
         */
        PROMO_2(4);

        private final Integer value;

        CommissionType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 提现方式
     */
    public enum WithdrawType {
        /**
         * 余额
         */
        ENT_PAY(1),
        /**
         * 银行卡
         */
        PAY_BANK(2);

        private final Integer value;

        WithdrawType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 审核状态
     */
    public enum AuditStatus {
        /**
         * 审核进行中
         */
        AUDITING(0),
        /**
         * 审核通过
         */
        AUDIT_PASSED(1),
        /**
         * 审核不通过
         */
        AUDIT_NOT_PASSED(2);

        private final Integer value;

        AuditStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 佣金定时到账状态
     */
    public enum AmountStatus {
        /**
         * 未到账，时间未到
         */
        WDZ(0),
        /**
         * 已到账
         */
        YDZ(1),
        /**
         * 购买者取消订单，失效
         */
        YSX(2);

        private final Integer value;

        AmountStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 推广状态
     */
    public enum PromoStatus {
        /**
         * 加入购物车
         */
        CART(1),
        /**
         * 已生成订单
         */
        ORDER(2),
        /**
         * 已支付
         */
        PAY(3);

        private final Integer value;

        PromoStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 银行卡状态
     */
    public enum CardStatus {
        /**
         * 已绑定
         */
        YBD(1),
        /**
         * 已解绑
         */
        YJB(2);

        private final Integer value;

        CardStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    public enum ShipmentStatus {
        /**
         * 初始化
         */
        INIT(0),
        /**
         * 出货成功
         */
        SUCCESS(1),
        /**
         * 出货失败
         */
        FAILURE(2);

        private final Integer value;

        ShipmentStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    public enum PatchType {
        /**
         * 机柜贴片
         */
        JGTP(1),
        /**
         * 下单页面贴片
         */
        XDTP(2),
        /**
         * 个人中心贴片
         */
        GRZXTP(3),
        /**
         * 出货成功贴片
         */
        CHCGTP(4),
        /**
         * 首页轮播图
         */
        SYLB(5);

        private final Integer value;

        PatchType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 操作机柜命令
     */
    public enum MachineOperateType {
        /**
         * 更新语音
         */
        VOICE(1),
        /**
         * 获取SIM卡CCID
         */
        CCID(2),
        /**
         * 获取设备版本
         */
        VERSION(3),
        /**
         * 获取设备位置信息
         */
        LOCATION(4),
        /**
         * 获取服务器URL
         */
        SERVER_URL(5),
        /**
         * 重启设备
         */
        RESTART(6),
        /**
         * 灯光开关
         */
        LIGHT(7);

        private final Integer value;

        MachineOperateType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 操作机柜执行状态
     */
    public enum ExecType {
        /**
         * 未执行
         */
        WZX(1),
        /**
         * 申请执行
         */
        SQZX(2),
        /**
         * 执行成功
         */
        ZXCG(3),
        /**
         * 执行异常
         */
        ZXYC(4);

        private final Integer value;

        ExecType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    public enum OrderShipmentStatus {
        /**
         * 未申请出货
         */
        WSQCH(0),
        /**
         * 申请出货
         */
        SQCH(1),
        /**
         * 出货成功
         */
        CHCG(2),
        /**
         * 出货异常
         */
        CHYC(3);

        private final Integer value;

        OrderShipmentStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    public enum userFromType {
        /**
         * 注册
         */
        ZC(0),
        /**
         * 下单
         */
        XD(1);

        private final Integer value;

        userFromType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 角色等级
     */
    public enum roleLevel {
        /**
         * 总代理
         */
        ZDL(1),
        /**
         * 代理
         */
        DL(2);

        private final Integer value;

        roleLevel(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 将AgentList返回到哪个地方
     */
    public enum whereNeedAgentList {
        /**
         * 机柜新增
         */
        JGXZ(1),
        /**
         * 商品新增
         */
        SPXZ(2);

        private final Integer value;

        whereNeedAgentList(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 是否员工
     */
    public enum isStaff {
        /**
         * 否
         */
        NO(0),
        /**
         * 是
         */
        YES(1);

        private final Integer value;

        isStaff(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 机柜状态
     */
    public enum MachineStatus {
        /**
         * 正常
         */
        SBZC(1),
        /**
         * 设备维修中
         */
        SBWX(2),
        /**
         * 出纸异常
         */
        CZYC(3),
        /**
         * 位置异常
         */
        WZYC(4),
        /**
         * 纸巾预警
         */
        ZJYJ(5),
        /**
         * 电量预警
         */
        DLYJ(6),
        /**
         * 语音修改异常
         */
        YYXGYC(7),
        /**
         * 离线
         */
        LX(8);

        private final Integer value;

        MachineStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 银行卡提现状态
     */
    public enum WithdrawalStatus {
        /**
         * 正常
         */
        DSH(0),
        /**
         * 提现成功
         */
        TXCG(1),
        /**
         * 提现失败
         */
        TXSB(2);


        private final Integer value;

        WithdrawalStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 菜单类型
     */
    public enum WhereMenu {
        /**
         * PC端
         */
        PC(0),
        /**
         * 手机端
         */
        MOBILE(1);

        private final Integer value;

        WhereMenu(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 工单状态
     */
    public enum workOrderStatus {
        /**
         * 待开始
         */
        DKS(0),
        /**
         * 进行中
         */
        JXZ(1),
        /**
         * 已处理
         */
        YCL(2),
        /**
         * 已作废
         */
        YZF(3),
        /**
         * 被移除
         */
        BYC(4);

        private final Integer value;

        workOrderStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 工单是否完结的状态
     */
    public enum workOrderIsEnd {
        /**
         * 未完结
         */
        WWJ(0),
        /**
         * 待确认
         */
        ZDDQR(1),
        /**
         * 待确认
         */
        FDDQR(2),
        /**
         * 完结
         */
        WJ(3);

        private final Integer value;

        workOrderIsEnd(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 工单是否软删除的状态
     */
    public enum workOrderIsDeleted {
        /**
         * 未删除
         */
        WSC(0),
        /**
         * 被指派者删除
         */
        BZPZSC(1),
        /**
         * 创建者删除
         */
        CJZSC(2),
        /**
         * 二者都删除
         */
        DSC(3);

        private final Integer value;

        workOrderIsDeleted(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }


    /**
     * 机柜使用状态
     */
    public enum MachineUseStatus {
        /**
         * 未使用
         */
        WSY(0),
        /**
         * 使用中
         */
        SYZ(1);

        private final Integer value;

        MachineUseStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 机柜使用状态
     */
    public enum orderPayType {
        /**
         * 余额支付
         */
        YEZF(1),
        /**
         * 微信支付
         */
        WXZF(2);

        private final Integer value;

        orderPayType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 会员等级
     */
    public enum UserLevel {
        /**
         * 普通会员
         */
        PTHY(1),
        /**
         * 高级会员
         */
        GJHY(2);

        private final Integer value;

        UserLevel(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 单位性质
     */
    public enum workUnitType {
        /**
         * 企业
         */
        QY(1),
        /**
         * 商家
         */
        SJ(2),
        /**
         * 个人
         */
        GR(3);

        private final Integer value;

        workUnitType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
    /**
     * 投放状态
     */
    public enum advertStatus {
        /**
         * 待回访
         */
        DHF(0),
        /**
         * 已回访
         */
        YHF(1);

        private final Integer value;

        advertStatus(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 会员等级
     */
    public enum BalanceLogType {
        /**
         * 收入
         */
        IN(1),
        /**
         * 支出
         */
        OUT(2);

        private final Integer value;

        BalanceLogType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 公众号模板消息
     */
    public enum TemplateId {
        /**
         * 新工单提醒
         */
        newWorkOrder(1),
        /**
         * 设备状态提醒
         */
        machineStatus(2);

        private final Integer value;

        TemplateId(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 工单推送标题
     */
    public enum WorkTypeTitle {
        /**
         * 有新的工单需要处理
         */
        XYCL(1),
        /**
         * 有工单需要确认完结
         */
        QRWJ(2),
        /**
         * 工单已确认完结
         */
        YWJ(3);

        private final Integer value;

        WorkTypeTitle(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}
