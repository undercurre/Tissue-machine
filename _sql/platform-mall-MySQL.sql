/*
Navicat MySQL Data Transfer

Source Server         :
Source Server Version : 50722
Source Host           :
Source Database       : platform-plus

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-07-18 09:31:24

pdm与sql文件不一致时以sql为准
*/

SET FOREIGN_KEY_CHECKS=0;



-- ----------------------------
-- Table structure for `MALL_ATTACHMENT`
-- ----------------------------
DROP TABLE IF EXISTS `MALL_ATTACHMENT`;
CREATE TABLE `MALL_ATTACHMENT` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `BUSSINESS_ID` varchar(32) DEFAULT NULL COMMENT '业务表ID',
  `NAME` varchar(512) DEFAULT NULL COMMENT '名称',
  `URL` varchar(512) DEFAULT NULL COMMENT 'URL',
  PRIMARY KEY (`ID`),
  KEY `BUSSINESS_ID` (`BUSSINESS_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

-- ----------------------------
-- Records of MALL_ATTACHMENT
-- ----------------------------
INSERT INTO `MALL_ATTACHMENT` VALUES ('0ae89faae2254bd8b49c061f5d0c5535', '8947fd4ea8e0a99de6c184214ed180d4', '35538160e3b41ae559031fa8c82fcebb', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/35538160e3b41ae559031fa8c82fcebb.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('2458095895684b8bb7ceceab53c92921', '8947fd4ea8e0a99de6c184214ed180d4', 'f2107c529bcc5c51bc3ce2b5cc9948db', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/f2107c529bcc5c51bc3ce2b5cc9948db.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('2754fc14c248448a9f9092b873bc3c37', 'b7c4fbf95493bd294054fe4296d0d9ad', '47c131a02d5d5b97ddcd19c16b391bbb', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/47c131a02d5d5b97ddcd19c16b391bbb.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('27f0edae006440a5bc4971d6cf55dfd1', '83dc4aa9cb99212840fae9e1b0c7b361', '810555afa6919c766a33422edefb1bc8', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/810555afa6919c766a33422edefb1bc8.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('2f074cbec8004f44a4b08e34210a9115', 'c8135bfe31c1e39c1788b66683720ceb', '6bdf224d6c0276a2737d6af775b6ed8a', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/6bdf224d6c0276a2737d6af775b6ed8a.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('32b8a2f41c5e45b2b5ae2e30d1e0d0d7', '83dc4aa9cb99212840fae9e1b0c7b361', '79200063ab5893cf3fdd16f428e4d505', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/79200063ab5893cf3fdd16f428e4d505.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('37b8b6d997e9478996abafcdd8852396', '8947fd4ea8e0a99de6c184214ed180d4', '9b4ee214032f7707c15943a1f1dfc881', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/9b4ee214032f7707c15943a1f1dfc881.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('420066313c2a4135b435dd6d662d6b4c', 'c8135bfe31c1e39c1788b66683720ceb', '517914d4f7d872b17a55e9c3864df717', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/517914d4f7d872b17a55e9c3864df717.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('45f14e46153940f6946537dfb559d671', 'c8135bfe31c1e39c1788b66683720ceb', '2eca5d0f8a1ce61baf32311264cebdd1', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/2eca5d0f8a1ce61baf32311264cebdd1.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('4d420a8accfa6fda17e9c7b647c73379', 'c3def8ba9211a8d777ef4f7004e67a19', 'e3f5272b1e404aa99bfa1a8aacd05d21', 'https://platform-wxmall-1251990035.cos.ap-shanghai.myqcloud.com/upload/20191107/e3f5272b1e404aa99bfa1a8aacd05d21.png');
INSERT INTO `MALL_ATTACHMENT` VALUES ('641418f93ded4b98873f6da2829f043a', 'b7c4fbf95493bd294054fe4296d0d9ad', '586f42c66523559838fbb97b7315bab6', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/586f42c66523559838fbb97b7315bab6.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('84dfec5315024ecebc778be80cbeb9f7', '83dc4aa9cb99212840fae9e1b0c7b361', '38a0b23950b79611fb565bae14351a11', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/38a0b23950b79611fb565bae14351a11.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('ae471e0aa8f84e3d99eb042181fbb1ab', 'b7c4fbf95493bd294054fe4296d0d9ad', 'cb78d268c517c15381aeb5b5905101fe', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/cb78d268c517c15381aeb5b5905101fe.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('b1415bef306e449380d1af52ae658227', 'c8135bfe31c1e39c1788b66683720ceb', '6fa8774f6da6cc473ba3714aec95f6b6', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/6fa8774f6da6cc473ba3714aec95f6b6.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('b48975cac59d4eb0bbfdee8da2bfa690', 'b7c4fbf95493bd294054fe4296d0d9ad', '5300c083dcc0c6a856364d883f3284e8', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/5300c083dcc0c6a856364d883f3284e8.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('cd7313c3c40a4c3ea52f8033042ea9be', '83dc4aa9cb99212840fae9e1b0c7b361', '79200063ab5893cf3fdd16f428e4d505', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/79200063ab5893cf3fdd16f428e4d505.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('dedac9ce7cf440cf9f43ea6897301a85', '8947fd4ea8e0a99de6c184214ed180d4', '97c6d4c7e80855966f0d38392b42a570', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/97c6d4c7e80855966f0d38392b42a570.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('e13f2a58a97e64bee5c2652ce6a525e7', 'c3def8ba9211a8d777ef4f7004e67a19', 'c8c09be5576b4e73a4f981009491cd00', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/c8c09be5576b4e73a4f981009491cd00.png');
INSERT INTO `MALL_ATTACHMENT` VALUES ('bf0d62f734d845b882259c995e7d3708', '5505597d436be5a2b05479bd50705439', '989d0d84d55e4a77a1c6dafa0a3bc207', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/989d0d84d55e4a77a1c6dafa0a3bc207.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('18056ce28f3548e18bca742cb7004125', '5505597d436be5a2b05479bd50705439', '3664e1b166b8dd54d05edd631e6966f9', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/3664e1b166b8dd54d05edd631e6966f9.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('afae25ba38144f49aac29f78bd56ee20', '5505597d436be5a2b05479bd50705439', 'ac649a9fc8332aae1c60e8a10fb5a775', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/ac649a9fc8332aae1c60e8a10fb5a775.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('a61a275a6055491aa224c26fc5a5dd01', '5505597d436be5a2b05479bd50705439', '61a44e7426fbc32db87afd48d85f2e99', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/attachment/61a44e7426fbc32db87afd48d85f2e99.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('95a3df29557e45e9f944400d57c94f19', '6ee376d38feffca45e7b7c7cb95c0fbc', '1114545734c867.jpg', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/image/20191122/5bd274a77b2a48a68fab2828dc16a32d.jpg');
INSERT INTO `MALL_ATTACHMENT` VALUES ('f1ac521ea310562ef5fc12827aade854', '6ee376d38feffca45e7b7c7cb95c0fbc', '软著.png', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/image/20191122/24725dc0a9824444b6aada5f9cdce939.png');




-- ----------------------------
-- Table structure for `MALL_ACCOUNT_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `MALL_ACCOUNT_LOG`;
CREATE TABLE `MALL_ACCOUNT_LOG` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `USER_ID` varchar(32) NOT NULL,
  `TYPE` tinyint(4) NOT NULL COMMENT '类型：1=收入，2=支出',
  `PRICE` decimal(10,2) NOT NULL COMMENT '变动金额',
  `LOG_DESC` text NOT NULL COMMENT '变动说明',
  `ADD_TIME` datetime NOT NULL,
  `ORDER_TYPE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单类型 0--充值 1--商城订单 2--秒杀订单 3--拼团订单 4--商城订单退款 5--秒杀订单退款 6--拼团订单退款',
  `ORDER_SN` varchar(32) DEFAULT '0' COMMENT '订单编号',
  `FROM_TYPE` tinyint(4) DEFAULT NULL COMMENT '用户下单来源类型 1:微信小程序 2:微信公众号 3:app 4:H5 5:支付宝小程序',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户余额变动记录';

-- ----------------------------
-- Records of MALL_ACCOUNT_LOG
-- ----------------------------


-- ----------------------------
-- Table structure for `MALL_BANNER`
-- ----------------------------
DROP TABLE IF EXISTS `MALL_BANNER`;
CREATE TABLE `MALL_BANNER` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `MEDIA_TYPE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型 0:图片 1:链接 2:文本',
  `TITLE` varchar(128) DEFAULT '' COMMENT '标题',
  `IMAGE_URL` text COMMENT '图片',
  `LINK` varchar(256) DEFAULT '' COMMENT '链接',
  `CONTENT` text COMMENT '文本',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `ENABLED` tinyint(4) DEFAULT '1' COMMENT '状态 0:禁用 1:启用',
  `VIDEO_URL` varchar(256) DEFAULT '' COMMENT '视频链接',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页轮播配置';

-- ----------------------------
-- Records of MALL_BANNER
-- ----------------------------
INSERT INTO `MALL_BANNER` VALUES ('1', 1, '商业版', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/banner/38a0b23950b79611fb565bae14351a11.jpg', '/pages/goods/goods?id=83dc4aa9cb99212840fae9e1b0c7b361', '商业版', '2020-11-05 08:37:20', 1, null);
INSERT INTO `MALL_BANNER` VALUES ('2', 0, '双十一', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/banner/aa860e44fbd468a7804c1a84796c4827.jpg', null, null, '2020-11-05 08:37:29', 1, null);
INSERT INTO `MALL_BANNER` VALUES ('3', 0, '纷格新品', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/banner/f02a4f8d5bbaf7f32e131c1f08ff21ae.jpg', null, null, '2020-11-05 08:37:31', 1, null);
INSERT INTO `MALL_BANNER` VALUES ('4', 0, '居家生活', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/platform-plus-mall/banner/79b2dbf762217e6e532f335bda4c85b3.jpg', null, null, '2020-10-05 08:37:37', 1, null);
INSERT INTO `MALL_BANNER` VALUES ('471d15d1d9f5e929b4f1a870829ca431', 0, '111', 'https://platform-wxmall.oss-cn-beijing.aliyuncs.com/image/20200301/bed930af04ae43acb0b5d8b6062c8f81.png', '', null, '2020-08-29 00:00:00', 1, null);


-- ----------------------------
-- Table structure for `MALL_SHIPPING`
-- ----------------------------
DROP TABLE IF EXISTS `MALL_SHIPPING`;
CREATE TABLE `MALL_SHIPPING` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `CODE` varchar(64) DEFAULT NULL COMMENT '快递公司编码',
  `NAME` varchar(128) DEFAULT NULL COMMENT '快递公司名称',
  `STATUS` tinyint(4) DEFAULT '1' COMMENT '状态 0：禁用 1：正常',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快递公司配置';

-- ----------------------------
-- Records of MALL_SHIPPING
-- ----------------------------
INSERT INTO `MALL_SHIPPING` VALUES ('1', 'AJ', '安捷快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('10', 'CSCY', '长沙创一', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('100', 'PANEX', '泛捷快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('101', 'PCA', 'PCA Express', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('102', 'UEQ', 'UEQ Express', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('11', 'CDSTKY', '成都善途速运', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('12', 'DBL', '德邦', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('13', 'DSWL', 'D速物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('14', 'DTWL', '大田物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('15', 'EMS', 'EMS', '0');
INSERT INTO `MALL_SHIPPING` VALUES ('16', 'FAST', '快捷速递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('17', 'FEDEX', 'FEDEX联邦(国内件）', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('18', 'FEDEX_GJ', 'FEDEX联邦(国际件）', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('19', 'FKD', '飞康达', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('2', 'ANE', '安能物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('20', 'GDEMS', '广东邮政', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('21', 'GSD', '共速达', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('22', 'GTO', '国通快递', '0');
INSERT INTO `MALL_SHIPPING` VALUES ('23', 'GTSD', '高铁速递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('24', 'HFWL', '汇丰物流', '0');
INSERT INTO `MALL_SHIPPING` VALUES ('25', 'HHTT', '天天快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('26', 'HLWL', '恒路物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('27', 'HOAU', '天地华宇', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('28', 'hq568', '华强物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('29', 'HTKY', '百世快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('3', 'AXD', '安信达快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('30', 'HXLWL', '华夏龙物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('31', 'HYLSD', '好来运快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('32', 'JGSD', '京广速递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('33', 'JIUYE', '九曳供应链', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('34', 'JJKY', '佳吉快运', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('35', 'JLDT', '嘉里物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('36', 'JTKD', '捷特快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('37', 'JXD', '急先达', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('38', 'JYKD', '晋越快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('39', 'JYM', '加运美', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('4', 'BQXHM', '北青小红帽', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('40', 'JYWL', '佳怡物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('41', 'KYWL', '跨越物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('42', 'LB', '龙邦快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('43', 'LHT', '联昊通速递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('44', 'MHKD', '民航快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('45', 'MLWL', '明亮物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('46', 'NEDA', '能达速递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('47', 'PADTF', '平安达腾飞快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('48', 'QCKD', '全晨快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('49', 'QFKD', '全峰快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('5', 'BFDF', '百福东方', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('50', 'QRT', '全日通快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('51', 'RFD', '如风达', '0');
INSERT INTO `MALL_SHIPPING` VALUES ('52', 'SAD', '赛澳递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('53', 'SAWL', '圣安物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('54', 'SBWL', '盛邦物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('55', 'SDWL', '上大物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('56', 'SF', '顺丰快递', '0');
INSERT INTO `MALL_SHIPPING` VALUES ('57', 'SFWL', '盛丰物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('58', 'SHWL', '盛辉物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('59', 'ST', '速通物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('6', 'BTWL', '百世快运', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('60', 'STO', '申通快递', '0');
INSERT INTO `MALL_SHIPPING` VALUES ('61', 'STWL', '速腾快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('62', 'SURE', '速尔快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('63', 'TSSTO', '唐山申通', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('64', 'UAPEX', '全一快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('65', 'UC', '优速快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('66', 'WJWL', '万家物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('67', 'WXWL', '万象物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('68', 'XBWL', '新邦物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('69', 'XFEX', '信丰快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('7', 'CCES', 'CCES快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('70', 'XYT', '希优特', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('71', 'XJ', '新杰物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('72', 'YADEX', '源安达快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('73', 'YCWL', '远成物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('74', 'YD', '韵达快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('75', 'YDH', '义达国际物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('76', 'YFEX', '越丰物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('77', 'YFHEX', '原飞航物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('78', 'YFSD', '亚风快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('79', 'YTKD', '运通快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('8', 'CITY100', '城市100', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('80', 'YTO', '圆通速递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('81', 'YXKD', '亿翔快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('82', 'YZPY', '邮政平邮/小包', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('83', 'ZENY', '增益快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('84', 'ZHQKD', '汇强快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('85', 'ZJS', '宅急送', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('86', 'ZTE', '众通快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('87', 'ZTKY', '中铁快运', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('88', 'ZTO', '中通速递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('89', 'ZTWL', '中铁物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('9', 'COE', 'COE东方快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('90', 'ZYWL', '中邮物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('91', 'AMAZON', '亚马逊物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('92', 'SUBIDA', '速必达物流', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('93', 'RFEX', '瑞丰速递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('94', 'QUICK', '快客快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('95', 'CJKD', '城际快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('96', 'CNPEX', 'CNPEX中邮快递', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('97', 'HOTSCM', '鸿桥供应链', '1');
INSERT INTO `MALL_SHIPPING` VALUES ('99', 'AYCA', '澳邮专线', '1');

-- ----------------------------
-- Table structure for `MALL_TEMPLATE_CONF`
-- ----------------------------
DROP TABLE IF EXISTS `MALL_TEMPLATE_CONF`;
CREATE TABLE `MALL_TEMPLATE_CONF` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `TEMPLATE_TYPE` tinyint(4) DEFAULT '1' COMMENT '模板类型 0:新订单提醒	 1:下单成功通知 2:订单评价提醒 3:退款 4:秒杀成功通知 5:订单配送通知',
  `TEMPLATE_ID` varchar(64) DEFAULT '' COMMENT '推送模板ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信订阅消息';

-- ----------------------------
-- Records of MALL_TEMPLATE_CONF
-- ----------------------------
INSERT INTO `MALL_TEMPLATE_CONF` VALUES ('0', 0, 'DTuRopf48UD5T4cxl-xfhEaDgCn3MQGC2Zgrw41MwFU');
INSERT INTO `MALL_TEMPLATE_CONF` VALUES ('1', 1, 'zHUyp74CW9WOjYmEWn_sdjJ650tlnO3t7VhDWLBOIkU');
INSERT INTO `MALL_TEMPLATE_CONF` VALUES ('2', 2, 'FWkWilLIrl2_uXiuGFFIuYswjT9PGyeXmusTI_11Yhg');
INSERT INTO `MALL_TEMPLATE_CONF` VALUES ('3', 3, 'Gzprigz9UMS-hwzuvaeAE0ULTCCuEYvGsK29E1ZoQvI');
INSERT INTO `MALL_TEMPLATE_CONF` VALUES ('4', 4, '2AnnInQ46mZG4cwu-sC3KjgBOX2gDMFHI3PfAxNsZ88');
INSERT INTO `MALL_TEMPLATE_CONF` VALUES ('5', 5, 'Kvl-JSiIFmfTrlymfFgGibn9HcJ76OFP61OvxIlIuoY');

-- ----------------------------
-- Table structure for `MALL_USER`
-- ----------------------------
DROP TABLE IF EXISTS `MALL_USER`;
CREATE TABLE `MALL_USER` (
  `ID` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '用户ID',
  `USER_NAME` varchar(128) NOT NULL DEFAULT '' COMMENT '用户名',
  `PASSWORD` varchar(128) CHARACTER SET utf8 DEFAULT '' COMMENT '密码',
  `GENDER` tinyint(4) DEFAULT NULL COMMENT '用户的性别（1是男性，2是女性，0是未知）',
  `BIRTHDAY` datetime DEFAULT NULL COMMENT '生日',
  `REGISTER_TIME` datetime DEFAULT NULL COMMENT '注册时间',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '最后登录时间',
  `LAST_LOGIN_IP` varchar(32) CHARACTER SET utf8 DEFAULT '' COMMENT '最后登录IP',
  `USER_LEVEL_ID` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '会员等级ID',
  `NICKNAME` varchar(60) DEFAULT '' COMMENT '微信昵称',
  `MOBILE` varchar(32) CHARACTER SET utf8 DEFAULT '' COMMENT '手机号',
  `REGISTER_IP` varchar(64) CHARACTER SET utf8 DEFAULT '' COMMENT '注册ip',
  `HEAD_IMG_URL` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '用户头像',
  `ALI_USER_ID` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '支付宝用户标识',
  `OPEN_ID` varchar(64) CHARACTER SET utf8 DEFAULT '' COMMENT '用户的标识',
  `QQ_OPEN_ID` varchar(64) DEFAULT NULL COMMENT 'QQ用户标识',
  `MP_OPEN_ID` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '公众号用户的标识',
  `UNION_ID` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户唯一标识',
  `SUBSCRIBE` tinyint(4) DEFAULT '0' COMMENT '公众号关注状态（1是关注，0是未关注），未关注时获取不到其余信息',
  `SUBSCRIBE_TIME` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户关注公众号时间，为时间戳。如果用户曾多次关注，则取最后关注时间',
  `SIGN_ALL_INTEGRAL` decimal(10,2) DEFAULT NULL COMMENT '签到、购物获得总积分',
  `SIGN_USED_INTEGRAL` decimal(10,2) DEFAULT NULL COMMENT '已兑换积分',
  `BALANCE` decimal(10,2) DEFAULT '0.00' COMMENT '余额',
  PRIMARY KEY (`ID`),
  KEY `OPEN_ID` (`OPEN_ID`) USING BTREE,
  KEY `NICKNAME` (`NICKNAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员';

-- ----------------------------
-- Records of MALL_USER
-- ----------------------------

-- ----------------------------
-- Table structure for `MALL_USER_LEVEL`
-- ----------------------------
DROP TABLE IF EXISTS `MALL_USER_LEVEL`;
CREATE TABLE `MALL_USER_LEVEL` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `NAME` varchar(64) NOT NULL DEFAULT '' COMMENT '等级名称',
  `MONEY` decimal(10,2) DEFAULT NULL COMMENT '会员完成订单金额满足则升级',
  `DISCOUNT` decimal(10,2) DEFAULT NULL COMMENT '折扣',
  `IMAGE_URL` varchar(128) DEFAULT NULL COMMENT '会员等级图片',
  `DESCRIPTION` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员等级管理';

-- ----------------------------
-- Records of MALL_USER_LEVEL
-- ----------------------------
INSERT INTO `MALL_USER_LEVEL` VALUES ('1', 'VIP1', '0.00', '1.00', null, '0');
INSERT INTO `MALL_USER_LEVEL` VALUES ('2', 'VIP2', '500.00', '0.95', null, '500');
INSERT INTO `MALL_USER_LEVEL` VALUES ('3', 'VIP3', '2000.00', '0.90', null, '2000');
INSERT INTO `MALL_USER_LEVEL` VALUES ('4', 'VIP4', '5000.00', '0.85', null, '5000');
INSERT INTO `MALL_USER_LEVEL` VALUES ('5', 'VIP5', '20000.00', '0.80', null, '20000');

-- ----------------------------
-- Table structure for `MALL_USER_SIGN_RECORD`
-- ----------------------------
DROP TABLE IF EXISTS `MALL_USER_SIGN_RECORD`;
CREATE TABLE `MALL_USER_SIGN_RECORD` (
  `ID` varchar(32) NOT NULL COMMENT '主键',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '会员ID',
  `SIGN_TIME` datetime DEFAULT NULL COMMENT '签到时间',
  `SIGN_INTEGRAL` decimal(10,2) DEFAULT NULL COMMENT '本次签到获得积分',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户签到记录';

-- ----------------------------
-- Records of MALL_USER_SIGN_RECORD
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_BLOB_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_IBFK_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_CALENDARS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_CRON_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_IBFK_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_FIRED_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_JOB_DETAILS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_LOCKS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_PAUSED_TRIGGER_GRPS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_SCHEDULER_STATE`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_SIMPLE_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_IBFK_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_SIMPROP_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_IBFK_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `QRTZ_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `QRTZ_TRIGGERS_IBFK_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for `SCHEDULE_JOB`
-- ----------------------------
DROP TABLE IF EXISTS `SCHEDULE_JOB`;
CREATE TABLE `SCHEDULE_JOB` (
  `JOB_ID` varchar(32) NOT NULL COMMENT '任务id',
  `BEAN_NAME` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `METHOD_NAME` varchar(100) DEFAULT NULL COMMENT '方法名',
  `PARAMS` varchar(2000) DEFAULT NULL COMMENT '参数',
  `CRON_EXPRESSION` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`JOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of SCHEDULE_JOB
-- ----------------------------
INSERT INTO `SCHEDULE_JOB` VALUES ('7a737ccd0cf55d1447a73ad8d4417c83', 'orderTask', 'expireOrder', null, '0 0/1 * * * ?', 0, '过期自动取消订单', '2019-07-19 16:54:54');
INSERT INTO `SCHEDULE_JOB` VALUES ('1bbb4265010759616070eb7366d9da63', 'liveTask', 'getLiveList', '{start:0,limit:100}', '0 0 0 * * ?', 0, '获取直播房间列表', '2020-03-30 21:19:28');
INSERT INTO `SCHEDULE_JOB` VALUES ('7c6128ea1124c0cf0e643fb2a53abaa5', 'couponTask', 'expireCoupon', '', '0 0 0 * * ?', 0, '优惠券过期', '2020-04-20 17:56:36');
INSERT INTO `SCHEDULE_JOB` VALUES ('a28b9be050a7c7c8be3ae4ba9bf6a289', 'commissionTask', 'amountArrive', '', '0 0 6 * * ?', 0, '佣金定时到账', '2020-06-08 16:15:37');
-- ----------------------------
-- Table structure for `SCHEDULE_JOB_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `SCHEDULE_JOB_LOG`;
CREATE TABLE `SCHEDULE_JOB_LOG` (
  `LOG_ID` varchar(32) NOT NULL COMMENT '任务日志id',
  `JOB_ID` varchar(32) NOT NULL COMMENT '任务id',
  `BEAN_NAME` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `METHOD_NAME` varchar(100) DEFAULT NULL COMMENT '方法名',
  `PARAMS` varchar(2000) DEFAULT NULL COMMENT '参数',
  `STATUS` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `ERROR` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `TIMES` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`LOG_ID`),
  KEY `JOB_ID` (`JOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Records of SCHEDULE_JOB_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_CAPTCHA`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_CAPTCHA`;
CREATE TABLE `SYS_CAPTCHA` (
  `UUID` char(36) NOT NULL COMMENT 'uuid',
  `CODE` varchar(6) NOT NULL COMMENT '验证码',
  `EXPIRE_TIME` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统验证码';

-- ----------------------------
-- Records of SYS_CAPTCHA
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_CONFIG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_CONFIG`;
CREATE TABLE `SYS_CONFIG` (
  `ID` varchar(32) NOT NULL,
  `PARAM_KEY` varchar(50) DEFAULT NULL COMMENT 'key',
  `PARAM_VALUE` varchar(2000) DEFAULT NULL COMMENT 'value',
  `STATUS` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PARAM_KEY` (`PARAM_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of SYS_CONFIG
-- ----------------------------
INSERT INTO `SYS_CONFIG` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"type\":2,\"qiniuDomain\":\"\",\"qiniuPrefix\":\"\",\"qiniuAccessKey\":\"\",\"qiniuSecretKey\":\"\",\"qiniuBucketName\":\"\",\"aliyunDomain\":\"https://platform-wxmall.oss-cn-beijing.aliyuncs.com\",\"aliyunPrefix\":\"image\",\"aliyunEndPoint\":\"oss-cn-beijing.aliyuncs.com\",\"aliyunAccessKeyId\":\"LTAIHnbxK6isFflU\",\"aliyunAccessKeySecret\":\"TU8bW7Ry8hFBtCpjCO5XjJtyjqZiqV\",\"aliyunBucketName\":\"platform-wxmall\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qcloudBucketName\":\"\",\"diskPath\":\"/usr/local/nginx/html/upload\",\"proxyServer\":\"http://132.232.89.47/upload\"}', '0', '云存储配置信息');
INSERT INTO `SYS_CONFIG` VALUES ('2', 'SMS_CONFIG_KEY', '{\"domain\":\"http://web.cr6868.com/asmx/smsservice.aspx?\",\"name\":\"lipengjun\",\"pwd\":\"\",\"sign\":\"【微同工作室】\",\"type\":1}', '0', '短信配置');
INSERT INTO `SYS_CONFIG` VALUES ('3', 'ORDER_EXPIRE', '30', 1, '下单支付过期时间，单位分钟');
INSERT INTO `SYS_CONFIG` VALUES ('4', 'SHIPPING_FEE', '12', 1, '运费');
INSERT INTO `SYS_CONFIG` VALUES ('5', 'SHIPPING_FEE_FREE', '80', 1, '免运费门槛');
INSERT INTO `SYS_CONFIG` VALUES ('6', 'RECHARGE_STATUS', '2', 1, '是否开启余额支付，1：开启 2：禁用');
INSERT INTO `SYS_CONFIG` VALUES ('7', 'DISTRIBUTION_AUDIT', '1', 1, '是否开启分销商申请审核，1：开启 2：禁用');
INSERT INTO `SYS_CONFIG` VALUES ('8', 'WITHDRAW_AUDIT', '1', 1, '是否开启提现审核，1：开启 2：禁用');
INSERT INTO `SYS_CONFIG` VALUES ('9', 'COMMISSION_TYPE_DIST_1', '0', 1, '一级分销提成比例');
INSERT INTO `SYS_CONFIG` VALUES ('10', 'COMMISSION_TYPE_DIST_2', '0', 1, '二级分销提成比例');
INSERT INTO `SYS_CONFIG` VALUES ('11', 'COMMISSION_TYPE_PROMO_1', '0', 1, '一级推广提成比例');
INSERT INTO `SYS_CONFIG` VALUES ('12', 'COMMISSION_TYPE_PROMO_2', '0', 1, '二级推广提成比例');
INSERT INTO `SYS_CONFIG` VALUES ('13', 'WITHDRAW_SINGLE_LOWEST', '1', 1, '单次最低提现额度');
INSERT INTO `SYS_CONFIG` VALUES ('14', 'WITHDRAW_SINGLE_HIGHEST', '5000', 1, '单次最高提现额度');
INSERT INTO `SYS_CONFIG` VALUES ('15', 'WITHDRAW_DAY_HIGHEST', '5000', 1, '当日最高提现额度');
INSERT INTO `SYS_CONFIG` VALUES ('16', 'ALLOW_REFUND_TIME', '7', 1, '收货后可申请退款时间');
INSERT INTO `SYS_CONFIG` VALUES ('17', 'USER_DAILY_BUY_NUM', '5', 1, '用户每天可以买的纸巾数目');
INSERT INTO `SYS_CONFIG` VALUES ('18', 'USER_DAILY_FREE_NUM', '5', 1, '用户每天可以免费获得的纸巾数目');


-- ----------------------------
-- Table structure for `SYS_DICT`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DICT`;
CREATE TABLE `SYS_DICT` (
  `ID` varchar(32) NOT NULL,
  `GROUP_ID` varchar(32) DEFAULT NULL COMMENT '所属分组ID',
  `NAME` varchar(100) DEFAULT NULL COMMENT '字典名称',
  `VALUE` varchar(64) DEFAULT NULL COMMENT '字典值',
  `SORT` int(11) DEFAULT NULL COMMENT '排序号',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态码',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- ----------------------------
-- Records of SYS_DICT
-- ----------------------------
INSERT INTO `SYS_DICT` VALUES ('37f73ea6b07c40ab8baec7f58b10e69e', '0b5e3fc9c30a4839a881bef0f85fc8af', '男', '1', '1', '1', null);
INSERT INTO `SYS_DICT` VALUES ('3b3cd7a1d75611afa42478cf0db98a9f', '756acef68d0acb5b9d90676689720b94', '商品相关', '1', '1', '1', null);
INSERT INTO `SYS_DICT` VALUES ('7936bc509417490ba0df9d938ccd1ce4', '2bbfcb36f9414b71a5d65f497be93496', '是', '1', '1', '1', null);
INSERT INTO `SYS_DICT` VALUES ('7fd70974ea18040f767e7a68130cd85d', '756acef68d0acb5b9d90676689720b94', '客户服务', '3', '3', '1', null);
INSERT INTO `SYS_DICT` VALUES ('957f3e162dc65c88233988b4533b54cb', '756acef68d0acb5b9d90676689720b94', '其他', '7', '7', '1', null);
INSERT INTO `SYS_DICT` VALUES ('979439be76954bc1852fdf2aeccf3cbc', '0b5e3fc9c30a4839a881bef0f85fc8af', '未知', '0', '3', '1', null);
INSERT INTO `SYS_DICT` VALUES ('a93382cde1b3e89ac9727b5d38ed2e9f', '756acef68d0acb5b9d90676689720b94', '产品建议', '6', '6', '1', null);
INSERT INTO `SYS_DICT` VALUES ('cbab5cc984cee053a56182915da1d32d', '756acef68d0acb5b9d90676689720b94', '功能异常', '5', '5', '1', null);
INSERT INTO `SYS_DICT` VALUES ('cd58ce94393df39d22958631c7c3c4ad', '756acef68d0acb5b9d90676689720b94', '优惠活动', '4', '4', '1', null);
INSERT INTO `SYS_DICT` VALUES ('d931f0a83fad5780f20ea6760b5ee222', '756acef68d0acb5b9d90676689720b94', '物流状况', '2', '2', '1', null);
INSERT INTO `SYS_DICT` VALUES ('f6cf775c5cea4c7b8858eb2ce0501177', '2bbfcb36f9414b71a5d65f497be93496', '否', '0', '2', '1', null);
INSERT INTO `SYS_DICT` VALUES ('fc982423addd41e3852c70f8396a0c6c', '0b5e3fc9c30a4839a881bef0f85fc8af', '女', '2', '2', '1', null);

-- ----------------------------
-- Table structure for `SYS_DICT_GROUP`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DICT_GROUP`;
CREATE TABLE `SYS_DICT_GROUP` (
  `ID` varchar(32) NOT NULL,
  `CODE` varchar(64) NOT NULL COMMENT '分组编码',
  `NAME` varchar(100) DEFAULT NULL COMMENT '分组名称',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`,`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典分组';

-- ----------------------------
-- Records of SYS_DICT_GROUP
-- ----------------------------
INSERT INTO `SYS_DICT_GROUP` VALUES ('0b5e3fc9c30a4839a881bef0f85fc8af', 'SEX', '性别', null, '性别，1：男 2：女 0：未知');
INSERT INTO `SYS_DICT_GROUP` VALUES ('2bbfcb36f9414b71a5d65f497be93496', 'IS_NOT', '是否', null, '1：是 0：否');
INSERT INTO `SYS_DICT_GROUP` VALUES ('756acef68d0acb5b9d90676689720b94', 'FEED_TYPE', '反馈类型', '2019-07-02 21:49:52', '1:商品相关, 2:物流状况, 3:客户服务,4:优惠活动, 5:功能异常, 6:产品建议, 7:其他');

-- ----------------------------
-- Table structure for `SYS_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_LOG`;
CREATE TABLE `SYS_LOG` (
  `ID` varchar(32) NOT NULL,
  `USER_NAME` varchar(50) DEFAULT NULL COMMENT '用户名',
  `OPERATION` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `METHOD` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `PARAMS` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `TIME` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `IP` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of SYS_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_MAIL_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_MAIL_LOG`;
CREATE TABLE `SYS_MAIL_LOG` (
  `ID` varchar(32) NOT NULL,
  `SENDER` varchar(100) NOT NULL COMMENT '发送人',
  `RECEIVER` varchar(4000) NOT NULL COMMENT '接收人',
  `SUBJECT` varchar(500) NOT NULL COMMENT '邮件主题',
  `CONTENT` varchar(4000) DEFAULT NULL COMMENT '发送内容',
  `SEND_DATE` datetime DEFAULT NULL COMMENT '发送时间',
  `TYPE` tinyint(4) DEFAULT NULL COMMENT '0：系统发送邮件 1：用户发送邮件',
  `SEND_RESULT` tinyint(4) DEFAULT NULL COMMENT '发送结果 0:发送成功 1:发送失败',
  `CREATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '创建者ID',
  `CREATE_USER_ORG_NO` varchar(32) DEFAULT NULL COMMENT '创建人所属机构',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件发送日志';

-- ----------------------------
-- Records of SYS_MAIL_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_MENU`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_MENU`;
CREATE TABLE `SYS_MENU` (
  `MENU_ID` VARCHAR(8) NOT NULL,
  `PARENT_ID` VARCHAR(8) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `NAME` VARCHAR(50) DEFAULT NULL COMMENT '菜单名称',
  `URL` VARCHAR(200) DEFAULT NULL COMMENT '菜单URL',
  `PERMS` VARCHAR(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `TYPE` tinyint DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `ICON` VARCHAR(50) DEFAULT NULL COMMENT '菜单图标',
  `ORDER_NUM` INT(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of SYS_MENU
-- ----------------------------
INSERT INTO `SYS_MENU` VALUES ('10', '0', '系统管理', null, null, '0', 'system', '0');
INSERT INTO `SYS_MENU` VALUES ('1001', '10', '菜单管理', 'sys/menu', 'sys:menu:list,sys:menu:info', '1', 'menu', '1');
INSERT INTO `SYS_MENU` VALUES ('100101', '1001', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100102', '1001', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100103', '1001', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('1002', '10', '组织机构', 'sys/org', 'sys:org:list,sys:org:info', '1', 'org', '2');
INSERT INTO `SYS_MENU` VALUES ('100201', '1002', '新增', null, 'sys:org:save', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100202', '1002', '修改', null, 'sys:org:update', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100203', '1002', '删除', null, 'sys:org:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('1003', '10', '系统参数', 'sys/config', 'sys:config:list,sys:config:info', '1', 'xitongpeizhi', '3');
INSERT INTO `SYS_MENU` VALUES ('100301', '1003', '新增', null, 'sys:config:save', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100302', '1003', '修改', null, 'sys:config:update', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100303', '1003', '删除', null, 'sys:config:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('1004', '10', '字典管理', 'sys/dictgroup', 'sys:dictgroup:list,sys:dictgroup:info,sys:dict:list,sys:dict:info', '1', 'dict', '4');
INSERT INTO `SYS_MENU` VALUES ('100401', '1004', '数据字典新增', null, 'sys:dict:save', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100402', '1004', '数据字典修改', null, 'sys:dict:update', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100403', '1004', '数据字典删除', null, 'sys:dict:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100404', '1004', '数据字典分组新增', null, 'sys:dictgroup:save', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100405', '1004', '数据字典分组修改', null, 'sys:dictgroup:update', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100406', '1004', '数据字典分组删除', null, 'sys:dictgroup:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('1005', '10', '文件上传', 'oss/oss', 'sys:oss:list', '1', 'oss', '5');
INSERT INTO `SYS_MENU` VALUES ('100501', '1005', '云存储配置', null, 'sys:oss:config', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100502', '1005', '删除', null, 'sys:oss:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('1006', '10', '系统日志', 'sys/log', 'sys:log:list', '1', 'log', '6');
INSERT INTO `SYS_MENU` VALUES ('1007', '10', '邮件系统', 'sys/maillog', 'sys:maillog:list,sys:maillog:info', '1', 'email', '7');
INSERT INTO `SYS_MENU` VALUES ('100701', '1007', '删除', null, 'sys:maillog:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('100702', '1007', '邮箱配置', null, 'sys:maillog:config', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('11', '0', '权限管理', null, null, '0', 'auth', '1');
INSERT INTO `SYS_MENU` VALUES ('1101', '11', '管理员列表', 'sys/user', 'sys:user:list,sys:user:info', '1', 'admin', '1');
INSERT INTO `SYS_MENU` VALUES ('110101', '1101', '重置密码', null, 'sys:user:resetPw', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('110102', '1101', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('110103', '1101', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('110104', '1101', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('1102', '11', '角色管理', 'sys/role', 'sys:role:list,sys:role:info', '1', 'role', '2');
INSERT INTO `SYS_MENU` VALUES ('110201', '1102', '新增', null, 'sys:role:save,sys:menu:list', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('110202', '1102', '修改', null, 'sys:role:update,sys:menu:list', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('110203', '1102', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('12', '0', '短信平台', null, null, '0', 'duanxinpingtai', '2');
INSERT INTO `SYS_MENU` VALUES ('1211', '12', '短信配置', 'sys/smslog', 'sys:smslog:list', '1', 'duanxin', '1');
INSERT INTO `SYS_MENU` VALUES ('121101', '1211', '修改配置', null, 'sys:smslog:config', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('121102', '1211', '删除', null, 'sys:smslog:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('121103', '1211', '发送短信', null, 'sys:smslog:send', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('13', '0', '任务调度', null, null, '0', 'diaodu', '3');
INSERT INTO `SYS_MENU` VALUES ('1301', '13', '定时任务', 'job/schedule', 'sys:schedule:list,sys:schedule:info', '1', 'job', '1');
INSERT INTO `SYS_MENU` VALUES ('130101', '1301', '删除', null, 'sys:schedule:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('130102', '1301', '暂停', null, 'sys:schedule:pause', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('130103', '1301', '恢复', null, 'sys:schedule:resume', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('130104', '1301', '立即执行', null, 'sys:schedule:run', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('130105', '1301', '日志列表', null, 'sys:schedule:log', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('130106', '1301', '新增', null, 'sys:schedule:save', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('130107', '1301', '修改', null, 'sys:schedule:update', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('14', '0', '工作流管理', null, null, '0', 'activiti', '4');
INSERT INTO `SYS_MENU` VALUES ('1401', '14', '流程操作', 'act/reprocdef', 'act:reprocdef:list', '1', 'procdef', '1');
INSERT INTO `SYS_MENU` VALUES ('140101', '1401', '激活,挂起', null, 'act:reprocdef:update', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('140102', '1401', '删除', null, 'act:reprocdef:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('140103', '1401', '转为模型', null, 'act:reprocdef:convertToModel', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('140104', '1401', '部署流程', null, 'act:reprocdef:deploy', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('1402', '14', '模型管理', 'act/remodel', 'act:remodel:list', '1', 'model', '2');
INSERT INTO `SYS_MENU` VALUES ('140201', '1402', '新增', null, 'act:remodel:save', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('140202', '1402', '编辑', null, 'act:remodel:update', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('140203', '1402', '部署', null, 'act:remodel:deploy', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('140204', '1402', '导出', null, 'act:remodel:export', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('140205', '1402', '删除', null, 'act:remodel:delete', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('15', '0', '开发工具', null, null, '0', 'dev', '5');
INSERT INTO `SYS_MENU` VALUES ('1501', '15', '在线用户管理', 'sys/usertoken', 'sys:usertoken:list', '1', 'zaixian', '1');
INSERT INTO `SYS_MENU` VALUES ('150101', '1501', '强制下线', null, 'sys:usertoken:offline', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('1502', '15', '缓存信息', 'sys/redis', 'sys:cache:queryAll', '1', 'redis', '2');
INSERT INTO `SYS_MENU` VALUES ('150201', '1502', '删除', null, 'sys:cache:deleteCache', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('1503', '15', 'SQL监控', 'http://localhost:8888/platform-admin/druid/sql.html', null, '1', 'sql', '3');
INSERT INTO `SYS_MENU` VALUES ('1504', '15', '接口文档', 'http://localhost:8889/platform-api/doc.html', null, '1', 'interface', '4');
INSERT INTO `SYS_MENU` VALUES ('1505', '15', '代码生成器', 'gen/generator', 'sys:generator:list', '1', 'code', '5');
INSERT INTO `SYS_MENU` VALUES ('150501', '1505', '生成代码', null, 'sys:generator:code', '2', null, '0');
INSERT INTO `SYS_MENU` VALUES ('16', '0', '会员管理', null, null, 0, 'vip', 6);
INSERT INTO `SYS_MENU` VALUES ('1601', '16', '会员等级配置', 'mall/userlevel', 'mall:userlevel:list,mall:userlevel:info', 1, 'level', 1);
INSERT INTO `SYS_MENU` VALUES ('160101', '1601', '新增', null, 'mall:userlevel:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('160102', '1601', '修改', null, 'mall:userlevel:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('160103', '1601', '删除', null, 'mall:userlevel:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1602', '16', '我的会员', 'mall/user', 'mall:user:list,mall:user:info', 1, 'admin', 2);
INSERT INTO `SYS_MENU` VALUES ('160201', '1602', '用户账户余额变动记录', null, 'mall:accountlog:list', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1603', '16', '收货地址', 'mall/address', 'mall:address:list,mall:address:info', 1, 'dangdifill', 3);
INSERT INTO `SYS_MENU` VALUES ('160301', '1603', '删除', null, 'mall:address:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1604', '16', '会员优惠券', 'mall/usercoupon', 'mall:usercoupon:list,mall:usercoupon:info', 1, 'coupon', 4);
INSERT INTO `SYS_MENU` VALUES ('160401', '1604', '删除', null, 'mall:usercoupon:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1605', '16', '会员收藏', 'mall/collect', 'mall:collect:list,mall:collect:info', 1, 'collect', 5);
INSERT INTO `SYS_MENU` VALUES ('160501', '1605', '删除', null, 'mall:collect:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1606', '16', '历史足迹', 'mall/footprint', 'mall:footprint:list,mall:footprint:info', 1, 'footprint', 6);
INSERT INTO `SYS_MENU` VALUES ('160601', '1606', '删除', null, 'mall:footprint:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1607', '16', '搜索历史', 'mall/searchhistory', 'mall:searchhistory:list,mall:searchhistory:info', 1, 'history', 7);
INSERT INTO `SYS_MENU` VALUES ('160701', '1607', '删除', null, 'mall:searchhistory:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1608', '16', '会员签到记录', 'mall/usersignrecord', 'mall:usersignrecord:list,mall:usersignrecord:info', 1, 'sign', 8);
INSERT INTO `SYS_MENU` VALUES ('160801', '1608', '删除', null, 'mall:usersignrecord:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1609', '16', '会员购物车', 'mall/cart', 'mall:cart:list,mall:cart:info', 1, 'cart', 9);
INSERT INTO `SYS_MENU` VALUES ('160901', '1609', '删除', null, 'mall:cart:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1610', '16', '会员反馈', 'mall/feedback', 'mall:feedback:list,mall:feedback:info', 1, 'feedback', 10);
INSERT INTO `SYS_MENU` VALUES ('161002', '1610', '删除', null, 'mall:feedback:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('17', '0', '商城配置', null, null, 0, 'mall', 7);
INSERT INTO `SYS_MENU` VALUES ('1701', '17', '轮播设置', 'mall/banner', 'mall:banner:list,mall:banner:info', 1, 'banner', 1);
INSERT INTO `SYS_MENU` VALUES ('170101', '1701', '新增', null, 'mall:banner:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170102', '1701', '修改', null, 'mall:banner:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170103', '1701', '删除', null, 'mall:banner:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1702', '17', '商品分类', 'mall/category', 'mall:category:list,mall:category:info', 1, 'leibie', 3);
INSERT INTO `SYS_MENU` VALUES ('170201', '1702', '新增', null, 'mall:category:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170202', '1702', '修改', null, 'mall:category:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170203', '1702', '删除', null, 'mall:category:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1703', '17', '首页频道', 'mall/channel', 'mall:channel:list,mall:channel:info', 1, 'kuqu', 3);
INSERT INTO `SYS_MENU` VALUES ('170301', '1703', '新增', null, 'mall:channel:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170302', '1703', '修改', null, 'mall:channel:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170303', '1703', '删除', null, 'mall:channel:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1704', '17', '商品详情参数', 'mall/attribute', 'mall:attribute:list,mall:attribute:info', 1, 'kuwei', 4);
INSERT INTO `SYS_MENU` VALUES ('170401', '1704', '新增', null, 'mall:attribute:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170402', '1704', '修改', null, 'mall:attribute:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170403', '1704', '删除', null, 'mall:attribute:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1705', '17', '品牌制造商', 'mall/brand', 'mall:brand:list,mall:brand:info', 1, 'brand', 5);
INSERT INTO `SYS_MENU` VALUES ('170501', '1705', '新增', null, 'mall:brand:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170502', '1705', '修改', null, 'mall:brand:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170503', '1705', '删除', null, 'mall:brand:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1706', '17', '商品问答', 'mall/issue', 'mall:issue:list,mall:issue:info', 1, 'issue', 6);
INSERT INTO `SYS_MENU` VALUES ('170601', '1706', '新增', null, 'mall:issue:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170602', '1706', '修改', null, 'mall:issue:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170603', '1706', '删除', null, 'mall:issue:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1707', '17', '搜索关键词', 'mall/keywords', 'mall:keywords:list,mall:keywords:info', 1, 'sousuo', 7);
INSERT INTO `SYS_MENU` VALUES ('170701', '1707', '新增', null, 'mall:keywords:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170702', '1707', '修改', null, 'mall:keywords:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170703', '1707', '删除', null, 'mall:keywords:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1708', '17', '快递公司配置', 'mall/shipping', 'mall:shipping:list,mall:shipping:info', 1, 'shipping', 8);
INSERT INTO `SYS_MENU` VALUES ('170801', '1708', '新增', null, 'mall:shipping:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170802', '1708', '修改', null, 'mall:shipping:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170803', '1708', '删除', null, 'mall:shipping:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1709', '17', '商城公告', 'mall/bulletin', 'mall:bulletin:list,mall:bulletin:info', 1, 'bulletin', 9);
INSERT INTO `SYS_MENU` VALUES ('170901', '1709', '新增', null, 'mall:bulletin:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170902', '1709', '修改', null, 'mall:bulletin:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('170903', '1709', '删除', null, 'mall:bulletin:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1710', '17', '专题分类', 'mall/topiccategory', 'mall:topiccategory:list,mall:topiccategory:info', 1, 'leibie', 10);
INSERT INTO `SYS_MENU` VALUES ('171001', '1710', '新增', null, 'mall:topiccategory:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('171002', '1710', '修改', null, 'mall:topiccategory:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('171003', '1710', '删除', null, 'mall:topiccategory:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1711', '17', '专题', 'mall/topic', 'mall:topic:list,mall:topic:info', 1, 'collect', 11);
INSERT INTO `SYS_MENU` VALUES ('171101', '1711', '新增', null, 'mall:topic:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('171102', '1711', '修改', null, 'mall:topic:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('171103', '1711', '删除', null, 'mall:topic:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('18', '0', '商品管理', null, null, 0, 'goods', 8);
INSERT INTO `SYS_MENU` VALUES ('1801', '18', '商品列表', 'mall/goods', 'mall:goods:list,mall:goods:info', 1, 'shop', 1);
INSERT INTO `SYS_MENU` VALUES ('180101', '1801', '新增', null, 'mall:goods:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('180102', '1801', '修改', null, 'mall:goods:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('180103', '1801', '删除', null, 'mall:goods:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('180104', '1801', '上架、下架', null, 'mall:goods:changeSale', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1802', '18', '商品评价', 'mall/comment', 'mall:comment:list,mall:comment:info', 1, 'bianji', 2);
INSERT INTO `SYS_MENU` VALUES ('180201', '1802', '审核', null, 'mall:commentpicture:list,mall:comment:changeStatus', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1803', '18', '秒杀配置', 'mall/seckill', 'mall:seckill:list,mall:seckill:info', 1, 'miaosha', 3);
INSERT INTO `SYS_MENU` VALUES ('180301', '1803', '新增', null, 'mall:seckill:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('180302', '1803', '修改', null, 'mall:seckill:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('180303', '1803', '删除', null, 'mall:seckill:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('19', '0', '店铺管理', null, null, 0, 'shop', 9);
INSERT INTO `SYS_MENU` VALUES ('1901', '19', '店铺列表', 'mall/shops', 'mall:shops:list,mall:shops:info', 1, 'shops', 1);
INSERT INTO `SYS_MENU` VALUES ('190101', '1901', '新增店铺', null, 'mall:shops:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190102', '1901', '修改店铺', null, 'mall:shops:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190103', '1901', '删除店铺', null, 'mall:shops:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1902', '19', '我的店铺', 'mall/myshop', 'mall:shops:myShop,mall:shops:info', 1, 'myshop', 1);
INSERT INTO `SYS_MENU` VALUES ('190201', '1902', '修改店铺', null, 'mall:shops:myUpdate', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1903', '19', '商品分类', 'mall/shopscategory', 'mall:shopscategory:list,mall:shopscategory:info', 1, 'leibie', 3);
INSERT INTO `SYS_MENU` VALUES ('190301', '1903', '新增', null, 'mall:shopscategory:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190302', '1903', '修改', null, 'mall:shopscategory:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190303', '1903', '删除', null, 'mall:shopscategory:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1904', '19', '店铺商品配置', 'mall/shopsgoods', 'mall:shopsgoods:list,mall:shopsgoods:info', 1, 'wuliaoguanliye', 4);
INSERT INTO `SYS_MENU` VALUES ('190401', '1904', '新增', null, 'mall:shopsgoods:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190402', '1904', '修改', null, 'mall:shopsgoods:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('190403', '1904', '删除', null, 'mall:shopsgoods:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('1905', '19', '店铺打票机', 'sys/printer', 'sys:printer:list,sys:printer:info', 1, 'print', 5);
INSERT INTO `SYS_MENU` VALUES ('190501', '1905', '新增', '', 'sys:printer:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('190502', '1905', '修改', '', 'sys:printer:update', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('190503', '1905', '删除', '', 'sys:printer:delete', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('20', '0', '微信设置', null, null, 0, 'wechat', 10);
INSERT INTO `SYS_MENU` VALUES ('2001', '20', '公众号配置', 'wx/mpconfig', 'wx:mpconfig:list,wx:mpconfig:info', 1, 'mp', 1);
INSERT INTO `SYS_MENU` VALUES ('200101', '2001', '新增', '', 'wx:mpconfig:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('200102', '2001', '修改', '', 'wx:mpconfig:update', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('200103', '2001', '删除', '', 'wx:mpconfig:delete', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('2002', '20', '小程序配置', 'wx/maconfig', 'wx:maconfig:list,wx:maconfig:info', 1, 'xiaochengxu', 2);
INSERT INTO `SYS_MENU` VALUES ('200201', '2002', '新增', '', 'wx:maconfig:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('200202', '2002', '修改', '', 'wx:maconfig:update', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('200203', '2002', '删除', '', 'wx:maconfig:delete', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('2003', '20', '订阅消息设置', 'mall/templateconf', 'mall:templateconf:list,mall:templateconf:info', 1, 'tempmsg', 3);
INSERT INTO `SYS_MENU` VALUES ('200301', '2003', '修改', null, 'mall:templateconf:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('21', '0', '订单管理', null, null, 0, 'orders', 11);
INSERT INTO `SYS_MENU` VALUES ('2101', '21', '所有订单', 'mall/order', 'mall:order:list,mall:order:info', 1, 'myorder', 1);
INSERT INTO `SYS_MENU` VALUES ('210101', '2101', '发货', null, 'mall:order:sendGoods', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('210102', '2101', '确认收货', null, 'mall:order:confirmReceive', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('210103', '2101', '修改价格', null, 'mall:order:modPrice', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('2102', '21', '我的店铺订单', 'mall/myorder', 'mall:order:myOrder,mall:order:info', 1, 'wuliaoguanli', 2);
INSERT INTO `SYS_MENU` VALUES ('210201', '2102', '发货', null, 'mall:order:sendGoods', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('210202', '2102', '确认收货', null, 'mall:order:confirmReceive', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('210203', '2102', '修改价格', null, 'mall:order:modPrice', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('2103', '21', '秒杀订单', 'mall/skillorder', 'mall:order:list,mall:order:info', 1, 'xiangqu', 3);
INSERT INTO `SYS_MENU` VALUES ('210301', '2103', '发货', null, 'mall:order:sendGoods', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('210302', '2103', '确认收货', null, 'mall:order:confirmReceive', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('210303', '2103', '修改价格', null, 'mall:order:modPrice', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('22', '0', '推广管理', null, null, 0, 'gift', 12);
INSERT INTO `SYS_MENU` VALUES ('2201', '22', '优惠券管理', 'mall/coupon', 'mall:coupon:list,mall:coupon:info', 1, 'coupons', 1);
INSERT INTO `SYS_MENU` VALUES ('220101', '2201', '新增', null, 'mall:coupon:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('220102', '2201', '修改', null, 'mall:coupon:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('220103', '2201', '删除', null, 'mall:coupon:delete', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('220104', '2201', '发放优惠券', null, 'mall:coupon:sendUser', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('2202', '22', '分销商列表', 'mall/dist', 'mall:dist:list,mall:dist:info', 1, 'admin', 2);
INSERT INTO `SYS_MENU` VALUES ('220201', '2202', '修改', NULL, 'mall:dist:update', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('220202', '2202', '删除', NULL, 'mall:dist:delete', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('220203', '2202', '审核通过', NULL, 'mall:dist:confirmAudit', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('2203', '22', '分销订单', 'mall/distorder', 'mall:distorder:list,mall:distorder:info', 1, 'myorder', 3);
INSERT INTO `SYS_MENU` VALUES ('220301', '2203', '删除', '', 'mall:distorder:delete', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('220302', '2203', '审核通过', '', 'mall:distorder:confirmAudit', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('23', '0', '直播管理', '', '', 0, 'live', 13);
INSERT INTO `SYS_MENU` VALUES ('2301', '23', '直播房间', 'mall/room', 'mall:room:list,mall:room:info', 1, 'room', 1);

-- ----------------------------
-- Table structure for `SYS_ORG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ORG`;
CREATE TABLE `SYS_ORG` (
  `ORG_NO` varchar(10) NOT NULL COMMENT '机构编码',
  `ORG_NAME` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `PARENT_NO` varchar(10) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `ORG_TYPE` int(11) DEFAULT NULL COMMENT '级别',
  `STATUS` int(11) DEFAULT '1' COMMENT '状态  0：无效   1：有效',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  `CREATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '创建者ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ORG_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- ----------------------------
-- Records of SYS_ORG
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_OSS`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_OSS`;
CREATE TABLE `SYS_OSS` (
  `ID` varchar(32) NOT NULL,
  `URL` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of SYS_OSS
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_ROLE`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE`;
CREATE TABLE `SYS_ROLE` (
  `ROLE_ID` varchar(32) NOT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `REMARK` varchar(100) DEFAULT NULL COMMENT '备注',
  `CREATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '创建者ID',
  `CREATE_USER_ORG_NO` varchar(32) DEFAULT NULL COMMENT '创建者所属机构',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of SYS_ROLE
-- ----------------------------
INSERT INTO `SYS_ROLE` VALUES ('af6008ecfe093a5d7a5f744ff67e2824', '商城总管理员', '商城总管理员', '1', '01', '2019-07-05 04:45:27');
INSERT INTO `SYS_ROLE` VALUES ('fa178703acdc4f1ca673dd593d1402a6', '超级管理员', '超级管理员', '1', '01', '2019-03-04 10:51:23');

-- ----------------------------
-- Table structure for `SYS_ROLE_MENU`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE_MENU`;
CREATE TABLE `SYS_ROLE_MENU` (
  `ID` varchar(32) NOT NULL,
  `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `MENU_ID` varchar(8) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of SYS_ROLE_MENU
-- ----------------------------
INSERT INTO `SYS_ROLE_MENU` VALUES ('006946d35d14ddb79cc8a0c2db119e96', 'af6008ecfe093a5d7a5f744ff67e2824', '1901');
INSERT INTO `SYS_ROLE_MENU` VALUES ('012b47d4d5450690e86530b24a88136f', 'fa178703acdc4f1ca673dd593d1402a6', '110201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('01948b026068f3e8d78ee9e35064010e', 'fa178703acdc4f1ca673dd593d1402a6', '110203');
INSERT INTO `SYS_ROLE_MENU` VALUES ('01c001f4cd5aa9ec58f55e04e64ef1d1', 'fa178703acdc4f1ca673dd593d1402a6', '160201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('04952507267ec7b7c369b58733923100', 'af6008ecfe093a5d7a5f744ff67e2824', '160101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('064d5c1759660f8c39a3fb3a0679d542', 'fa178703acdc4f1ca673dd593d1402a6', '100402');
INSERT INTO `SYS_ROLE_MENU` VALUES ('072c85d69bbbfa3cdc3d9afd56d870b5', 'af6008ecfe093a5d7a5f744ff67e2824', '170203');
INSERT INTO `SYS_ROLE_MENU` VALUES ('08bbf43a954787c0fd393d21984f65a3', 'fa178703acdc4f1ca673dd593d1402a6', '13');
INSERT INTO `SYS_ROLE_MENU` VALUES ('0add09622a3826bf7baa314167921f3b', 'fa178703acdc4f1ca673dd593d1402a6', '130102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('0b6b23f1c8e1eddb59ecdbfb4198dcef', 'fa178703acdc4f1ca673dd593d1402a6', '121103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('0bc1c5ec495ae3c1089fb0587cb0b86f', 'af6008ecfe093a5d7a5f744ff67e2824', '170802');
INSERT INTO `SYS_ROLE_MENU` VALUES ('0be61eaef8efcdc6d1aa2165f9e0d306', 'af6008ecfe093a5d7a5f744ff67e2824', '190302');
INSERT INTO `SYS_ROLE_MENU` VALUES ('0d0591740d591e9b739bec29211341d7', 'af6008ecfe093a5d7a5f744ff67e2824', '170401');
INSERT INTO `SYS_ROLE_MENU` VALUES ('0e3b92dfb45390f7d99cdd8e56b1e3e5', 'af6008ecfe093a5d7a5f744ff67e2824', '160301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('0e5c23df05dbabcc94bf447e2fc691f0', 'fa178703acdc4f1ca673dd593d1402a6', '1607');
INSERT INTO `SYS_ROLE_MENU` VALUES ('0f78862b267af24157ecb7d8fe3a6216', 'fa178703acdc4f1ca673dd593d1402a6', '1706');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1019d30a4a76ac6b2f8c4fd461fa75f0', 'fa178703acdc4f1ca673dd593d1402a6', '1903');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1020060908c3bf5760bfa1ee03fa00e1', 'fa178703acdc4f1ca673dd593d1402a6', '130105');
INSERT INTO `SYS_ROLE_MENU` VALUES ('10fdb00746ae44539e4324e8f23c3ff3', 'fa178703acdc4f1ca673dd593d1402a6', '170201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('11265a741d25b411dfac5965872d7607', 'af6008ecfe093a5d7a5f744ff67e2824', '170202');
INSERT INTO `SYS_ROLE_MENU` VALUES ('117c82c97996eed07ad2dafa39527697', 'fa178703acdc4f1ca673dd593d1402a6', '100502');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1186a2bd31b1cad597fa550572af7867', 'fa178703acdc4f1ca673dd593d1402a6', '1505');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1308d19e7f818802be9b8411885dee35', 'af6008ecfe093a5d7a5f744ff67e2824', '210102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('136e222abf811b752fdd1d68c82eb28a', 'fa178703acdc4f1ca673dd593d1402a6', '100201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1394e0acccbe0204de01d858c60325fb', 'fa178703acdc4f1ca673dd593d1402a6', '180303');
INSERT INTO `SYS_ROLE_MENU` VALUES ('13d8712bee55343b41050f28c4b30ea2', 'fa178703acdc4f1ca673dd593d1402a6', '190403');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1436f52060bbdf155df8fcf40441fbc1', 'af6008ecfe093a5d7a5f744ff67e2824', '1605');
INSERT INTO `SYS_ROLE_MENU` VALUES ('143869f432ac505747ea6787714ec5f4', 'fa178703acdc4f1ca673dd593d1402a6', '140103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('15121b0b6a168b11052015374ae29a3c', 'fa178703acdc4f1ca673dd593d1402a6', '1708');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1514e9086f636565976eae629bf55477', 'fa178703acdc4f1ca673dd593d1402a6', '1609');
INSERT INTO `SYS_ROLE_MENU` VALUES ('157ea03b83b00a0fe4bb95a2f4a0c23a', 'af6008ecfe093a5d7a5f744ff67e2824', '170201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1673011310eddeca0e07373f77c7aadc', 'af6008ecfe093a5d7a5f744ff67e2824', '220101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('186dee9a9b73187fee0cd0383bc704a3', 'af6008ecfe093a5d7a5f744ff67e2824', '190102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1944ba541665d0df9fa206440b296061', 'fa178703acdc4f1ca673dd593d1402a6', '1401');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1b1a3c55b2d518ff48a470a9afbef7a7', 'fa178703acdc4f1ca673dd593d1402a6', '1707');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1b457b8d10c522badf5a19153bc81b64', 'fa178703acdc4f1ca673dd593d1402a6', '100406');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1d0cde9e56adfafd7b4693f965b172ec', 'fa178703acdc4f1ca673dd593d1402a6', '170403');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1d80b5401957c980c905928564d0d1cc', 'af6008ecfe093a5d7a5f744ff67e2824', '1707');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1f0d6900a52141b7562c9697d8a1a82e', 'fa178703acdc4f1ca673dd593d1402a6', '190102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1f6df69ea5a6502a8aaa29634e4b6c80', 'fa178703acdc4f1ca673dd593d1402a6', '170501');
INSERT INTO `SYS_ROLE_MENU` VALUES ('1fe90593aa0dadcc7b007a63aa1b0955', 'af6008ecfe093a5d7a5f744ff67e2824', '17');
INSERT INTO `SYS_ROLE_MENU` VALUES ('2001fc4fc6e0222204aab0b7e9c7e10d', 'af6008ecfe093a5d7a5f744ff67e2824', '160501');
INSERT INTO `SYS_ROLE_MENU` VALUES ('20b64bad32489b953a9446e1e53e5e9d', 'fa178703acdc4f1ca673dd593d1402a6', '21');
INSERT INTO `SYS_ROLE_MENU` VALUES ('21d1ec68b4561ac384262147cf31ad86', 'af6008ecfe093a5d7a5f744ff67e2824', '2102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('234680f3ab3aa02eebd0a473f65dfcfb', 'fa178703acdc4f1ca673dd593d1402a6', '170701');
INSERT INTO `SYS_ROLE_MENU` VALUES ('2508d8454901a42bd9baccfece22e820', 'fa178703acdc4f1ca673dd593d1402a6', '190101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('27c0fb4f02e25c1ae28914a1763e9720', 'fa178703acdc4f1ca673dd593d1402a6', '1504');
INSERT INTO `SYS_ROLE_MENU` VALUES ('2b3dc6631d7d93a819a308d6f8212962', 'af6008ecfe093a5d7a5f744ff67e2824', '1702');
INSERT INTO `SYS_ROLE_MENU` VALUES ('2e1ed7d0bb2e3d1db2b9c153bd04d3df', 'fa178703acdc4f1ca673dd593d1402a6', '200301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('2e26a12362715b2b94a597d38ca3c5b4', 'fa178703acdc4f1ca673dd593d1402a6', '1402');
INSERT INTO `SYS_ROLE_MENU` VALUES ('311034a0009b049b0835368f93027576', 'fa178703acdc4f1ca673dd593d1402a6', '160203');
INSERT INTO `SYS_ROLE_MENU` VALUES ('31808a8e25a232dcb27b7cd16199d0c8', 'af6008ecfe093a5d7a5f744ff67e2824', '1609');
INSERT INTO `SYS_ROLE_MENU` VALUES ('33577d2584f3889811d441c09bfd84c0', 'af6008ecfe093a5d7a5f744ff67e2824', '190101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3363d3d4ed436c9ec27451e2e9d5ce51', 'fa178703acdc4f1ca673dd593d1402a6', '160102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3385b97286cbc7aec23c05212b3b5b46', 'af6008ecfe093a5d7a5f744ff67e2824', '1602');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3523c53dc73382a2a7e25e703434fcba', 'af6008ecfe093a5d7a5f744ff67e2824', '1704');
INSERT INTO `SYS_ROLE_MENU` VALUES ('377ea905e39de6f5ae71282743e29ed2', 'fa178703acdc4f1ca673dd593d1402a6', '170203');
INSERT INTO `SYS_ROLE_MENU` VALUES ('379b55dd7e989a5585d45ec3dd0c03b8', 'af6008ecfe093a5d7a5f744ff67e2824', '170302');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3833648878c7184c570a2b96389c6b6b', 'af6008ecfe093a5d7a5f744ff67e2824', '16');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3a25069513f77c6416459ef690e0095b', 'af6008ecfe093a5d7a5f744ff67e2824', '170602');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3ac8835d4ab0733a7163c6730955c66b', 'af6008ecfe093a5d7a5f744ff67e2824', '180303');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3aedce31996b925e455347288f85a7a2', 'af6008ecfe093a5d7a5f744ff67e2824', '1708');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3b03b1e3f44cd9d5fc8fe59303c901e3', 'fa178703acdc4f1ca673dd593d1402a6', '170802');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3b2ec2046733f2375a35330a3017ee93', 'fa178703acdc4f1ca673dd593d1402a6', '190302');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3d87b30895b904e7e215c6687407552e', 'fa178703acdc4f1ca673dd593d1402a6', '110104');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3f069ffc2b046d258538db9d642a3387', 'fa178703acdc4f1ca673dd593d1402a6', '100202');
INSERT INTO `SYS_ROLE_MENU` VALUES ('3fee8615f8093af074ee8b2963e5c7f1', 'fa178703acdc4f1ca673dd593d1402a6', '130103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('4008c06425b1892c9f178ddcff1b49fa', 'af6008ecfe093a5d7a5f744ff67e2824', '190402');
INSERT INTO `SYS_ROLE_MENU` VALUES ('41ab68b45a425bfa09a0d00517dc46b5', 'af6008ecfe093a5d7a5f744ff67e2824', '1610');
INSERT INTO `SYS_ROLE_MENU` VALUES ('437e3ef763c7cdf6a2213d9556b9681a', 'af6008ecfe093a5d7a5f744ff67e2824', '1606');
INSERT INTO `SYS_ROLE_MENU` VALUES ('43f1d16be9f9af85ab637358a15e462c', 'fa178703acdc4f1ca673dd593d1402a6', '190103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('442a89be21cf0510d9bd8dd376f3cb33', 'af6008ecfe093a5d7a5f744ff67e2824', '220103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('46930d548a1be5c47b893ff888502d4b', 'fa178703acdc4f1ca673dd593d1402a6', '22');
INSERT INTO `SYS_ROLE_MENU` VALUES ('4a783b580bd31441712d9b20893694a1', 'fa178703acdc4f1ca673dd593d1402a6', '160801');
INSERT INTO `SYS_ROLE_MENU` VALUES ('4bb44074a0731a4c2623d0aea679502a', 'fa178703acdc4f1ca673dd593d1402a6', '190402');
INSERT INTO `SYS_ROLE_MENU` VALUES ('4c6b11e1a1be9a0f5ef86cc2ca4650c3', 'fa178703acdc4f1ca673dd593d1402a6', '170702');
INSERT INTO `SYS_ROLE_MENU` VALUES ('4d85e3a1b0a7711830651d685cea32f3', 'fa178703acdc4f1ca673dd593d1402a6', '12');
INSERT INTO `SYS_ROLE_MENU` VALUES ('4eead1d0ae31af48f1250499e68d9f08', 'fa178703acdc4f1ca673dd593d1402a6', '1004');
INSERT INTO `SYS_ROLE_MENU` VALUES ('4fdcef781b5226882fdc1e803e8ce932', 'fa178703acdc4f1ca673dd593d1402a6', '2101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('501c2dc34ef696fa94604ff3996b3d0e', 'af6008ecfe093a5d7a5f744ff67e2824', '190301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('5052eef57f721695fea98516a5920439', 'af6008ecfe093a5d7a5f744ff67e2824', '210202');
INSERT INTO `SYS_ROLE_MENU` VALUES ('50be871a07cd5896ec357c5b5426fe5d', 'fa178703acdc4f1ca673dd593d1402a6', '1901');
INSERT INTO `SYS_ROLE_MENU` VALUES ('50d0a1995f391716c572057883e7a68d', 'fa178703acdc4f1ca673dd593d1402a6', '130107');
INSERT INTO `SYS_ROLE_MENU` VALUES ('52a38ae2f8b8b5ff1a3b8d895d9e76fe', 'af6008ecfe093a5d7a5f744ff67e2824', '170803');
INSERT INTO `SYS_ROLE_MENU` VALUES ('53c6f0e20858c285a6bc0fbb8eee08f0', 'fa178703acdc4f1ca673dd593d1402a6', '1608');
INSERT INTO `SYS_ROLE_MENU` VALUES ('54ff2b9618d98283f36b8d47a8554b39', 'fa178703acdc4f1ca673dd593d1402a6', '180201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('557f499f3849cd272a4f195ec2391c73', 'af6008ecfe093a5d7a5f744ff67e2824', '170703');
INSERT INTO `SYS_ROLE_MENU` VALUES ('5754b76fb702036639a79ff08478fb9b', 'af6008ecfe093a5d7a5f744ff67e2824', '200301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('584e42b5f9531fc349c9985955451623', 'fa178703acdc4f1ca673dd593d1402a6', '100701');
INSERT INTO `SYS_ROLE_MENU` VALUES ('588ca7ab7b0d7771ef6cb3ddb67f87b7', 'fa178703acdc4f1ca673dd593d1402a6', '1003');
INSERT INTO `SYS_ROLE_MENU` VALUES ('5b08415836b71bf32cc69f23405a3e8f', 'af6008ecfe093a5d7a5f744ff67e2824', '160601');
INSERT INTO `SYS_ROLE_MENU` VALUES ('5ca99d863fcf9ed7a6f528b2b60c17a3', 'af6008ecfe093a5d7a5f744ff67e2824', '160401');
INSERT INTO `SYS_ROLE_MENU` VALUES ('5d921cd4a6101f1d3c8ccb4f71e6a904', 'af6008ecfe093a5d7a5f744ff67e2824', '170603');
INSERT INTO `SYS_ROLE_MENU` VALUES ('5dfa6d61d4e0a86bd0bde24f59bcaa14', 'fa178703acdc4f1ca673dd593d1402a6', '1803');
INSERT INTO `SYS_ROLE_MENU` VALUES ('5e3d89f3abc754cd74d5b0b7820f1186', 'fa178703acdc4f1ca673dd593d1402a6', '16');
INSERT INTO `SYS_ROLE_MENU` VALUES ('6093cdf84c1e9f2c7f16a04af8fa6512', 'af6008ecfe093a5d7a5f744ff67e2824', '1803');
INSERT INTO `SYS_ROLE_MENU` VALUES ('617d0f66caa56cd67519eb59a1fd73f3', 'fa178703acdc4f1ca673dd593d1402a6', '10');
INSERT INTO `SYS_ROLE_MENU` VALUES ('62576742a91727af73d0a9b491045d4a', 'af6008ecfe093a5d7a5f744ff67e2824', '1802');
INSERT INTO `SYS_ROLE_MENU` VALUES ('62acfe14fd58c2b00f8e7d35a7923d3d', 'fa178703acdc4f1ca673dd593d1402a6', '150201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('64464a5de4217e5c39521e8b4719ea22', 'fa178703acdc4f1ca673dd593d1402a6', '140205');
INSERT INTO `SYS_ROLE_MENU` VALUES ('68b5891b11f0cbcbff8b082b5114d6f4', 'af6008ecfe093a5d7a5f744ff67e2824', '170701');
INSERT INTO `SYS_ROLE_MENU` VALUES ('68b5d12e0fd498c50e2f6c1c5f811e0b', 'fa178703acdc4f1ca673dd593d1402a6', '160401');
INSERT INTO `SYS_ROLE_MENU` VALUES ('6930a7dee9a215029d1dc7dd8bac4cce', 'fa178703acdc4f1ca673dd593d1402a6', '210201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('6a03ae090d8c421eac9411531af1ed53', 'af6008ecfe093a5d7a5f744ff67e2824', '1608');
INSERT INTO `SYS_ROLE_MENU` VALUES ('6bea2cce9335f052400f028d7d923b0b', 'fa178703acdc4f1ca673dd593d1402a6', '170601');
INSERT INTO `SYS_ROLE_MENU` VALUES ('6d080ac9a64714a1c0b3aaa10801e1c7', 'af6008ecfe093a5d7a5f744ff67e2824', '170403');
INSERT INTO `SYS_ROLE_MENU` VALUES ('6d0a0e98526272ab6fa74e4ada197ec8', 'fa178703acdc4f1ca673dd593d1402a6', '190301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('6db69efbff80baf95801f83396d9d083', 'af6008ecfe093a5d7a5f744ff67e2824', '170102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('6e422d7d337b220e3159aea2d7a60163', 'fa178703acdc4f1ca673dd593d1402a6', '1701');
INSERT INTO `SYS_ROLE_MENU` VALUES ('6f77d8e765d5226303e3276bd3523912', 'fa178703acdc4f1ca673dd593d1402a6', '121102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('703e5ed6b8dcd9c8a40e85401c8eaf8e', 'fa178703acdc4f1ca673dd593d1402a6', '160301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('70752e43a02ec998ac097d660a294f36', 'fa178703acdc4f1ca673dd593d1402a6', '170502');
INSERT INTO `SYS_ROLE_MENU` VALUES ('714abb14fdd089fde273e0e51648fe2b', 'fa178703acdc4f1ca673dd593d1402a6', '1603');
INSERT INTO `SYS_ROLE_MENU` VALUES ('71784d191851f6d5a14b8bc774d980dd', 'fa178703acdc4f1ca673dd593d1402a6', '170803');
INSERT INTO `SYS_ROLE_MENU` VALUES ('71ce1116dd4814e18484e96ea6f7526c', 'af6008ecfe093a5d7a5f744ff67e2824', '1604');
INSERT INTO `SYS_ROLE_MENU` VALUES ('737493143a28a0cb37bc1cad8cfa8898', 'af6008ecfe093a5d7a5f744ff67e2824', '160801');
INSERT INTO `SYS_ROLE_MENU` VALUES ('7439204324e43cf1c472c107335fe512', 'af6008ecfe093a5d7a5f744ff67e2824', '1607');
INSERT INTO `SYS_ROLE_MENU` VALUES ('7585e812bf59caa00f92c2df65f2180b', 'fa178703acdc4f1ca673dd593d1402a6', '1802');
INSERT INTO `SYS_ROLE_MENU` VALUES ('7615d9be6a79dad66ee68667690c3f5f', 'fa178703acdc4f1ca673dd593d1402a6', '140203');
INSERT INTO `SYS_ROLE_MENU` VALUES ('7644488f738ed72c8c8486bc1f2a4be7', 'af6008ecfe093a5d7a5f744ff67e2824', '170501');
INSERT INTO `SYS_ROLE_MENU` VALUES ('769d20b9cb4cd03ac751adf1379adeb8', 'fa178703acdc4f1ca673dd593d1402a6', '1001');
INSERT INTO `SYS_ROLE_MENU` VALUES ('7715dce68025ced3e3a98293b58dc17c', 'af6008ecfe093a5d7a5f744ff67e2824', '1903');
INSERT INTO `SYS_ROLE_MENU` VALUES ('77c9a51ffd87e67709d9dff8d1ba38d8', 'af6008ecfe093a5d7a5f744ff67e2824', '18');
INSERT INTO `SYS_ROLE_MENU` VALUES ('786154de80a13f8d2f37436cf877070a', 'fa178703acdc4f1ca673dd593d1402a6', '100403');
INSERT INTO `SYS_ROLE_MENU` VALUES ('79c20e885fab4b8f7da39cb0ce91a76e', 'af6008ecfe093a5d7a5f744ff67e2824', '20');
INSERT INTO `SYS_ROLE_MENU` VALUES ('79f575842d118140d205bb6d88e881cd', 'fa178703acdc4f1ca673dd593d1402a6', '160103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('79ffbce2ab8cd40d675c0a7337d5f917', 'af6008ecfe093a5d7a5f744ff67e2824', '160204');
INSERT INTO `SYS_ROLE_MENU` VALUES ('7b74fa075926bb814ce7eb9380059d60', 'af6008ecfe093a5d7a5f744ff67e2824', '1703');
INSERT INTO `SYS_ROLE_MENU` VALUES ('7b8d2654a912c006c680f2fbd7df35a3', 'fa178703acdc4f1ca673dd593d1402a6', '220102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('7c0719d2f42d82aa8a145e0bc21ad979', 'af6008ecfe093a5d7a5f744ff67e2824', '1902');
INSERT INTO `SYS_ROLE_MENU` VALUES ('7ce238122b1712659deba9ac5bdb3f77', 'fa178703acdc4f1ca673dd593d1402a6', '140104');
INSERT INTO `SYS_ROLE_MENU` VALUES ('7dd13ebbd39b10330585f0c431a5cbe2', 'fa178703acdc4f1ca673dd593d1402a6', '1605');
INSERT INTO `SYS_ROLE_MENU` VALUES ('7f38b2e5aa5960e0a3fb13d540d51a81', 'fa178703acdc4f1ca673dd593d1402a6', '1602');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8237d63667b818e1e741cbb59016aba6', 'fa178703acdc4f1ca673dd593d1402a6', '100702');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8370f60ba76ee1d79feb45b87d04252e', 'fa178703acdc4f1ca673dd593d1402a6', '161002');
INSERT INTO `SYS_ROLE_MENU` VALUES ('83ecd7da263b25e88af96b3c9b42d6e0', 'af6008ecfe093a5d7a5f744ff67e2824', '160901');
INSERT INTO `SYS_ROLE_MENU` VALUES ('84669025dbfe79d1b75ec11c451dec81', 'af6008ecfe093a5d7a5f744ff67e2824', '21');
INSERT INTO `SYS_ROLE_MENU` VALUES ('85211d59bb1265807ee66673e5fcf36f', 'af6008ecfe093a5d7a5f744ff67e2824', '170103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8619abaa31e7c1de3f9aeec011dfbd66', 'fa178703acdc4f1ca673dd593d1402a6', '140102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('86baa5e5d135ab1fe411f56aaa33c397', 'af6008ecfe093a5d7a5f744ff67e2824', '2101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('88243a0b786af375e0a50fb3fa79b00b', 'af6008ecfe093a5d7a5f744ff67e2824', '220104');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8836679746fa33a1f01d96a486097cb6', 'fa178703acdc4f1ca673dd593d1402a6', '180101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8860c8586983d04bdb1f1a78be91776b', 'fa178703acdc4f1ca673dd593d1402a6', '1604');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8880e56642f2330a1f1e025febe3595e', 'fa178703acdc4f1ca673dd593d1402a6', '170103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('89ac326deb2f1d06d13f50f88ccd2353', 'af6008ecfe093a5d7a5f744ff67e2824', '190103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8a95e1cdb7c48f8103f7357fbb0c215a', 'af6008ecfe093a5d7a5f744ff67e2824', '180101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8ab868971c63d0ce1026e94b5c0cd857', 'fa178703acdc4f1ca673dd593d1402a6', '220101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8b6dd76a7c8bbc87903ccf005651c596', 'fa178703acdc4f1ca673dd593d1402a6', '1502');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8ca889f73dd55b705d844b6c776944c0', 'fa178703acdc4f1ca673dd593d1402a6', '15');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8cf8550485314ad581db66fd295b9c29', 'fa178703acdc4f1ca673dd593d1402a6', '170401');
INSERT INTO `SYS_ROLE_MENU` VALUES ('8ddae8a68dada7d05d737175c47258ff', 'af6008ecfe093a5d7a5f744ff67e2824', '19');
INSERT INTO `SYS_ROLE_MENU` VALUES ('901b85646dea4f54c4140e677b572e5b', 'fa178703acdc4f1ca673dd593d1402a6', '110102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('90afede255581b434cde50f3d3832b95', 'af6008ecfe093a5d7a5f744ff67e2824', '161002');
INSERT INTO `SYS_ROLE_MENU` VALUES ('91f57881a568552ea8ddb0d91f231e46', 'af6008ecfe093a5d7a5f744ff67e2824', '170801');
INSERT INTO `SYS_ROLE_MENU` VALUES ('94f715a9008f449a9aeb36dafe96f86b', 'fa178703acdc4f1ca673dd593d1402a6', '19');
INSERT INTO `SYS_ROLE_MENU` VALUES ('950c6ee9ebdc758336f1f5955c11efb9', 'af6008ecfe093a5d7a5f744ff67e2824', '160203');
INSERT INTO `SYS_ROLE_MENU` VALUES ('95afd22e91c92116e7dd8bdc03a088bb', 'af6008ecfe093a5d7a5f744ff67e2824', '190303');
INSERT INTO `SYS_ROLE_MENU` VALUES ('960aa859adb7a292cf2f53d86122faee', 'fa178703acdc4f1ca673dd593d1402a6', '1301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('963980c2f7154eb36ee1068594fe5a79', 'fa178703acdc4f1ca673dd593d1402a6', '150101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('96fe97c280bf0696174efb5851971025', 'fa178703acdc4f1ca673dd593d1402a6', '130101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('970ae2b990f88438a0362894e838a409', 'fa178703acdc4f1ca673dd593d1402a6', '210102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('97a19679656ca7842e40802e4e2aa145', 'fa178703acdc4f1ca673dd593d1402a6', '110103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('97dad15d6643304cbaf3325eed2066b3', 'fa178703acdc4f1ca673dd593d1402a6', '100404');
INSERT INTO `SYS_ROLE_MENU` VALUES ('98320c81c69684ed4c5dd02860bd87aa', 'fa178703acdc4f1ca673dd593d1402a6', '1005');
INSERT INTO `SYS_ROLE_MENU` VALUES ('990b690f048c41b7a889710c1554f1cb', 'fa178703acdc4f1ca673dd593d1402a6', '170602');
INSERT INTO `SYS_ROLE_MENU` VALUES ('9994bf00176eb8f2cdbee2f49dc1778f', 'fa178703acdc4f1ca673dd593d1402a6', '110202');
INSERT INTO `SYS_ROLE_MENU` VALUES ('9b2481746bc2de2422b50d80538c444a', 'af6008ecfe093a5d7a5f744ff67e2824', '180302');
INSERT INTO `SYS_ROLE_MENU` VALUES ('9d01507c1631ef54dc18830decba8319', 'fa178703acdc4f1ca673dd593d1402a6', '1705');
INSERT INTO `SYS_ROLE_MENU` VALUES ('9e8fd35d574f0e7f53c3730cd37c3abb', 'af6008ecfe093a5d7a5f744ff67e2824', '160201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('9f99f95b2cb05bd7af8926eee2c88892', 'af6008ecfe093a5d7a5f744ff67e2824', '190401');
INSERT INTO `SYS_ROLE_MENU` VALUES ('a07f769ee7b2f873af6fb22bc452d3ba', 'af6008ecfe093a5d7a5f744ff67e2824', '1801');
INSERT INTO `SYS_ROLE_MENU` VALUES ('a26216e831d798efe2c8c2d8e12b19f0', 'af6008ecfe093a5d7a5f744ff67e2824', '190403');
INSERT INTO `SYS_ROLE_MENU` VALUES ('a31dd24a820239656ca805b6d98d0d3b', 'fa178703acdc4f1ca673dd593d1402a6', '100405');
INSERT INTO `SYS_ROLE_MENU` VALUES ('a3edeaec140471d4ba8933a3ebf4c28b', 'fa178703acdc4f1ca673dd593d1402a6', '170603');
INSERT INTO `SYS_ROLE_MENU` VALUES ('a4410ddc1f6af7c1ea9b63325b9154c7', 'af6008ecfe093a5d7a5f744ff67e2824', '160701');
INSERT INTO `SYS_ROLE_MENU` VALUES ('a50846e81d6241c7f89550ac7012815f', 'fa178703acdc4f1ca673dd593d1402a6', '170402');
INSERT INTO `SYS_ROLE_MENU` VALUES ('a6ceae54c6897862dc4e5059b1cfe468', 'fa178703acdc4f1ca673dd593d1402a6', '160701');
INSERT INTO `SYS_ROLE_MENU` VALUES ('a7e0cc5ef2f143bc1d45c952af339c42', 'fa178703acdc4f1ca673dd593d1402a6', '100503');
INSERT INTO `SYS_ROLE_MENU` VALUES ('a8a4cf040efe075995919f84666bda4a', 'fa178703acdc4f1ca673dd593d1402a6', '2003');
INSERT INTO `SYS_ROLE_MENU` VALUES ('a973642e8d1e2e24d794f73c8f7ff418', 'fa178703acdc4f1ca673dd593d1402a6', '1101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('aa8f8347d9c8e68db2b3de960f7e36d5', 'fa178703acdc4f1ca673dd593d1402a6', '17');
INSERT INTO `SYS_ROLE_MENU` VALUES ('aaf624c8ad9c45408d80be9e211d7b64', 'fa178703acdc4f1ca673dd593d1402a6', '190303');
INSERT INTO `SYS_ROLE_MENU` VALUES ('ac269600e21d5cca8dbf811a1bad2351', 'fa178703acdc4f1ca673dd593d1402a6', '190401');
INSERT INTO `SYS_ROLE_MENU` VALUES ('ad0ea0e5d21c453d5f3486b5fbd92ef4', 'fa178703acdc4f1ca673dd593d1402a6', '140201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('ad1a139324d6dbd70a22baa4deed9b99', 'fa178703acdc4f1ca673dd593d1402a6', '160101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('ad2711350937f4bb56acddf1c525682a', 'fa178703acdc4f1ca673dd593d1402a6', '20');
INSERT INTO `SYS_ROLE_MENU` VALUES ('aef400120911517151a93cdd5d39d3ad', 'fa178703acdc4f1ca673dd593d1402a6', '220103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('b0060bb3ca7229fca4ea0810c397ec25', 'fa178703acdc4f1ca673dd593d1402a6', '140204');
INSERT INTO `SYS_ROLE_MENU` VALUES ('b06da57abd1aebbbeedb3478ea027eda', 'fa178703acdc4f1ca673dd593d1402a6', '100303');
INSERT INTO `SYS_ROLE_MENU` VALUES ('b08bdd8651649603f876a1a73df49936', 'fa178703acdc4f1ca673dd593d1402a6', '140101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('b101241abc1c87d311fa346d1f42e827', 'af6008ecfe093a5d7a5f744ff67e2824', '1603');
INSERT INTO `SYS_ROLE_MENU` VALUES ('b170118ba80ac2028e99e5e8dde69e77', 'af6008ecfe093a5d7a5f744ff67e2824', '22');
INSERT INTO `SYS_ROLE_MENU` VALUES ('b34a8396bbcba0a53a7c043853477023', 'fa178703acdc4f1ca673dd593d1402a6', '140202');
INSERT INTO `SYS_ROLE_MENU` VALUES ('b47d8377a43faa11d9406e4e87dd46a5', 'af6008ecfe093a5d7a5f744ff67e2824', '180301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('b7852b74894f03bfe89b01fcf1eceb6c', 'fa178703acdc4f1ca673dd593d1402a6', '1601');
INSERT INTO `SYS_ROLE_MENU` VALUES ('b7891e1a87523c5e3c4f3ebcf852ba78', 'fa178703acdc4f1ca673dd593d1402a6', '160204');
INSERT INTO `SYS_ROLE_MENU` VALUES ('b8ddba5d01fafb85213f86429282ff1f', 'fa178703acdc4f1ca673dd593d1402a6', '180103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('b8fc2f1f40cde0ecbe9ac49e0064a158', 'fa178703acdc4f1ca673dd593d1402a6', '170801');
INSERT INTO `SYS_ROLE_MENU` VALUES ('bc851074ca62604a161a52e47e5dda5f', 'fa178703acdc4f1ca673dd593d1402a6', '180301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('bce894cf6770fab33d8f23e18c581f24', 'fa178703acdc4f1ca673dd593d1402a6', '150501');
INSERT INTO `SYS_ROLE_MENU` VALUES ('bd8e2bb137cd0380a9683eb3703dfb54', 'fa178703acdc4f1ca673dd593d1402a6', '18');
INSERT INTO `SYS_ROLE_MENU` VALUES ('bf30a7a3eb3eb2ec4e1dea54c48d235f', 'fa178703acdc4f1ca673dd593d1402a6', '1002');
INSERT INTO `SYS_ROLE_MENU` VALUES ('bf51c60df0b2aed513fe6eef64415f84', 'af6008ecfe093a5d7a5f744ff67e2824', '180201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('c02fb76ab0bfbab4a09d61d5bb59e136', 'af6008ecfe093a5d7a5f744ff67e2824', '170303');
INSERT INTO `SYS_ROLE_MENU` VALUES ('c2aa1c774d42e9ed14a3d312b7a0142a', 'fa178703acdc4f1ca673dd593d1402a6', '1606');
INSERT INTO `SYS_ROLE_MENU` VALUES ('c37758ad560e5766fb423e4bcc4d1082', 'fa178703acdc4f1ca673dd593d1402a6', '1610');
INSERT INTO `SYS_ROLE_MENU` VALUES ('c5a48c608f90464b2e98a1387d64607e', 'af6008ecfe093a5d7a5f744ff67e2824', '170702');
INSERT INTO `SYS_ROLE_MENU` VALUES ('c5c23a9f90758ec0bac96d328290bd34', 'af6008ecfe093a5d7a5f744ff67e2824', '210201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('c6b760fb67704a3ebca8bda9c12215ca', 'af6008ecfe093a5d7a5f744ff67e2824', '180102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('ca3f27d96349651b2210bfb9da5f10ef', 'fa178703acdc4f1ca673dd593d1402a6', '11');
INSERT INTO `SYS_ROLE_MENU` VALUES ('ca97a6963411ff737bc2f90c333171e4', 'fa178703acdc4f1ca673dd593d1402a6', '100203');
INSERT INTO `SYS_ROLE_MENU` VALUES ('cd85b9141f440dc7e5d36abaa0dac1f1', 'fa178703acdc4f1ca673dd593d1402a6', '160202');
INSERT INTO `SYS_ROLE_MENU` VALUES ('cf673f4bcf0abf688764b8a7e000b9eb', 'fa178703acdc4f1ca673dd593d1402a6', '190201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('cfec887680373ec7261a49f1ae633ec1', 'af6008ecfe093a5d7a5f744ff67e2824', '1706');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d10229994515c2121917adecb3d30ad5', 'fa178703acdc4f1ca673dd593d1402a6', '170101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d113cf44c63afd5567fb9529ac3be0cc', 'af6008ecfe093a5d7a5f744ff67e2824', '220102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d13734998b1a7cd72717f387310d9ddb', 'af6008ecfe093a5d7a5f744ff67e2824', '1701');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d21511d1b9b8cfa1097e469983bc2bf1', 'fa178703acdc4f1ca673dd593d1402a6', '100401');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d2a0563b07c8cfef178361c7e508499a', 'fa178703acdc4f1ca673dd593d1402a6', '2102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d3e501ec92915a8bc6b7546b10d87ab3', 'af6008ecfe093a5d7a5f744ff67e2824', '160102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d5fcd7119d20f75248aa55d0134a836c', 'fa178703acdc4f1ca673dd593d1402a6', '130106');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d67340fa40eee24f96d460105ca6fd2f', 'fa178703acdc4f1ca673dd593d1402a6', '1211');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d76db250dac3240945f2716cfefb4bec', 'fa178703acdc4f1ca673dd593d1402a6', '110101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d8ca1b6a5a0f832bcd51703b169418f0', 'fa178703acdc4f1ca673dd593d1402a6', '160601');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d9256c6597216dec428d942e8a86055c', 'fa178703acdc4f1ca673dd593d1402a6', '170301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d95950f3f3dffaa67184890f81d6349b', 'fa178703acdc4f1ca673dd593d1402a6', '170703');
INSERT INTO `SYS_ROLE_MENU` VALUES ('d9fc2f94942e7cb0c2a782cd1cf67e63', 'fa178703acdc4f1ca673dd593d1402a6', '130104');
INSERT INTO `SYS_ROLE_MENU` VALUES ('da5192a0440c03bee736b4278e7f41bb', 'af6008ecfe093a5d7a5f744ff67e2824', '180103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('dafd79a30cff5b8094b11f30572d55ef', 'af6008ecfe093a5d7a5f744ff67e2824', '1904');
INSERT INTO `SYS_ROLE_MENU` VALUES ('db5ca3f75ebb222b3896cfb4e07b4f2c', 'af6008ecfe093a5d7a5f744ff67e2824', '2003');
INSERT INTO `SYS_ROLE_MENU` VALUES ('dbb3c54f6632635cf2724f600669f4a3', 'af6008ecfe093a5d7a5f744ff67e2824', '170402');
INSERT INTO `SYS_ROLE_MENU` VALUES ('dd60ff9c57bc7efd703d5783d5599a50', 'af6008ecfe093a5d7a5f744ff67e2824', '170601');
INSERT INTO `SYS_ROLE_MENU` VALUES ('dde4fb56bd8c90efa2e19111310cdd1b', 'af6008ecfe093a5d7a5f744ff67e2824', '170503');
INSERT INTO `SYS_ROLE_MENU` VALUES ('de2f8678dafa478dff55c33f07a11cd3', 'fa178703acdc4f1ca673dd593d1402a6', '100301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('de83af5c29e091661fc185b0e6b69ecd', 'fa178703acdc4f1ca673dd593d1402a6', '1801');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e15800d7331512e84db796b1ee5988f2', 'fa178703acdc4f1ca673dd593d1402a6', '180102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e2ad41b9eb1f2d40afb8e34d78b71764', 'fa178703acdc4f1ca673dd593d1402a6', '2201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e2e0b9f8ea5a19d7d7abb4c2191620f3', 'fa178703acdc4f1ca673dd593d1402a6', '1702');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e40a5e16190b0680b8b42da515a3ba8f', 'af6008ecfe093a5d7a5f744ff67e2824', '170502');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e4a5b8553f559a61b684af5b4cbc37bf', 'af6008ecfe093a5d7a5f744ff67e2824', '1705');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e66ab23fe34fc82b257112df4f7e95a5', 'fa178703acdc4f1ca673dd593d1402a6', '210101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e6f7e0e4525d85f008879adcd85be62f', 'af6008ecfe093a5d7a5f744ff67e2824', '210101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e72ff19d02472f28dddd560e662aadd7', 'fa178703acdc4f1ca673dd593d1402a6', '1703');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e7650431540d404173c31651f1444e46', 'fa178703acdc4f1ca673dd593d1402a6', '100302');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e8cb938dd5f31432fc9bd7db45728de0', 'fa178703acdc4f1ca673dd593d1402a6', '170303');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e9c6860d78935a256bbe3174b92bede9', 'fa178703acdc4f1ca673dd593d1402a6', '1503');
INSERT INTO `SYS_ROLE_MENU` VALUES ('e9ddb26faa459a5abe94c999e62b0e80', 'fa178703acdc4f1ca673dd593d1402a6', '100103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('eab105f5563de59867fc2078d7dd0abf', 'fa178703acdc4f1ca673dd593d1402a6', '100501');
INSERT INTO `SYS_ROLE_MENU` VALUES ('ecdd6b6efc5d983e99bdd0d209bf50e6', 'fa178703acdc4f1ca673dd593d1402a6', '160901');
INSERT INTO `SYS_ROLE_MENU` VALUES ('ece31c0f7383749b8cb0ea814ce3aae1', 'af6008ecfe093a5d7a5f744ff67e2824', '160103');
INSERT INTO `SYS_ROLE_MENU` VALUES ('eda282029cbc519181307bc7adfbf090', 'fa178703acdc4f1ca673dd593d1402a6', '1704');
INSERT INTO `SYS_ROLE_MENU` VALUES ('edaf712f51c0a4ab909dd46693704189', 'fa178703acdc4f1ca673dd593d1402a6', '1006');
INSERT INTO `SYS_ROLE_MENU` VALUES ('edbdd2d81fe83e245d35fc96e4ccaaaf', 'fa178703acdc4f1ca673dd593d1402a6', '210202');
INSERT INTO `SYS_ROLE_MENU` VALUES ('ee74376671bfa4c90ddaa9cfcc644570', 'fa178703acdc4f1ca673dd593d1402a6', '170503');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f0cf87e8755abc34f84ab6ce360392cc', 'af6008ecfe093a5d7a5f744ff67e2824', '170101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f2476eefef13f2a1f5de9fc89ba7a663', 'fa178703acdc4f1ca673dd593d1402a6', '160501');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f274ac77a449562abceb07c6f338f606', 'af6008ecfe093a5d7a5f744ff67e2824', '190201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f2a107d8b396cad9235f227e2c26a78c', 'fa178703acdc4f1ca673dd593d1402a6', '170202');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f4bb68622afea724b35ff2d090ba5a08', 'af6008ecfe093a5d7a5f744ff67e2824', '170301');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f4bf446f5a07144bcce442282a046d5b', 'fa178703acdc4f1ca673dd593d1402a6', '1904');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f692d9ee03ea0a256bd68d5e8c55d47b', 'fa178703acdc4f1ca673dd593d1402a6', '1007');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f7b9653e8897660202507ba3cc35b6cb', 'fa178703acdc4f1ca673dd593d1402a6', '180302');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f8073adc2a85285e92da141d1f05c99b', 'af6008ecfe093a5d7a5f744ff67e2824', '1601');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f8baa68beb97cc60dbebcbe6bd893a55', 'fa178703acdc4f1ca673dd593d1402a6', '1102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f900d9aa687109567e05e32ecc6dad66', 'fa178703acdc4f1ca673dd593d1402a6', '220104');
INSERT INTO `SYS_ROLE_MENU` VALUES ('f9277a054b34b4dd83dd76736a5a0681', 'fa178703acdc4f1ca673dd593d1402a6', '121101');
INSERT INTO `SYS_ROLE_MENU` VALUES ('fa15dcd81deeb211ac6ba14d87329ef6', 'af6008ecfe093a5d7a5f744ff67e2824', '2201');
INSERT INTO `SYS_ROLE_MENU` VALUES ('fad43306aabc0ce62b6b3c130814ccbd', 'fa178703acdc4f1ca673dd593d1402a6', '1501');
INSERT INTO `SYS_ROLE_MENU` VALUES ('fb14ffd8cda5cd9f218fdf435cfa1af1', 'fa178703acdc4f1ca673dd593d1402a6', '1902');
INSERT INTO `SYS_ROLE_MENU` VALUES ('fb9eaa8a00c720480a611bfba4b6e67d', 'fa178703acdc4f1ca673dd593d1402a6', '170302');
INSERT INTO `SYS_ROLE_MENU` VALUES ('fc765e41cff461f998fcebc76ba6f96a', 'fa178703acdc4f1ca673dd593d1402a6', '14');
INSERT INTO `SYS_ROLE_MENU` VALUES ('fcb7eb94f9d3893d1c414af783eb1bb7', 'fa178703acdc4f1ca673dd593d1402a6', '170102');
INSERT INTO `SYS_ROLE_MENU` VALUES ('fccbb2f724eb929f8b16813d0537e0a8', 'af6008ecfe093a5d7a5f744ff67e2824', '160202');
INSERT INTO `SYS_ROLE_MENU` VALUES ('fe2422d4f241b3d25587935ce7a86098', 'fa178703acdc4f1ca673dd593d1402a6', '100102');

-- ----------------------------
-- Table structure for `SYS_ROLE_ORG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE_ORG`;
CREATE TABLE `SYS_ROLE_ORG` (
  `ID` varchar(32) NOT NULL,
  `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `ORG_NO` varchar(32) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与机构对应关系';

-- ----------------------------
-- Records of SYS_ROLE_ORG
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_SMS_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_SMS_LOG`;
CREATE TABLE `SYS_SMS_LOG` (
  `ID` VARCHAR(32) NOT NULL COMMENT '主键',
  `USER_ID` VARCHAR(32) DEFAULT NULL COMMENT '操作人ID',
  `TEMPLATE_ID` INT(11) DEFAULT NULL COMMENT '模板ID',
  `CODE` INT(11) DEFAULT NULL COMMENT '验证码',
  `CONTENT` TEXT COMMENT '发送内容（1-500 个汉字）UTF-8编码',
  `MOBILE` TEXT COMMENT '手机号码。多个以英文逗号隔开',
  `STIME` DATETIME DEFAULT NULL COMMENT '发送时间',
  `SIGN` VARCHAR(32) DEFAULT NULL COMMENT '必填参数。用户签名',
  `SEND_STATUS` INT(1) DEFAULT NULL COMMENT '0成功 1失败',
  `SEND_ID` VARCHAR(32) DEFAULT NULL COMMENT '发送编号',
  `SUCCESS_NUM` INT(11) DEFAULT NULL COMMENT '成功提交数',
  `RETURN_MSG` VARCHAR(50) DEFAULT NULL COMMENT '返回消息',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信发送日志';

-- ----------------------------
-- Records of SYS_SMS_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_USER`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER`;
CREATE TABLE `SYS_USER` (
  `USER_ID` varchar(32) NOT NULL,
  `USER_NAME` varchar(50) NOT NULL COMMENT '用户名',
  `REAL_NAME` varchar(64) NOT NULL,
  `SEX` tinyint(4) NOT NULL,
  `ORG_NO` varchar(32) DEFAULT NULL COMMENT '机构编码',
  `SALT` varchar(20) DEFAULT NULL COMMENT '盐',
  `EMAIL_HOST` varchar(32) DEFAULT NULL COMMENT '邮件服务器地址',
  `EMAIL` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `EMAIL_PW` varchar(64) DEFAULT NULL COMMENT '用户邮箱密码',
  `MOBILE` varchar(100) DEFAULT NULL COMMENT '手机号',
  `STATUS` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `PASSWORD` varchar(100) DEFAULT NULL COMMENT '密码',
  `CREATE_USER_ID` varchar(32) DEFAULT NULL COMMENT '创建者ID',
  `CREATE_USER_ORG_NO` varchar(32) DEFAULT NULL COMMENT '创建人所属机构',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `USERNAME` (`USER_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of SYS_USER
-- ----------------------------
INSERT INTO `SYS_USER` VALUES ('1', 'admin', 'zqh-admin', '1', '01', 'YzcmCZNvbXocrsz9dm8e', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', '', null, '2016-11-11 11:11:11');
INSERT INTO `SYS_USER` VALUES ('10d5b18fa91779f097704d527e0aeb5e', 'test4', 'zqh4', '1', '01', 'T3Nvf0dN5IQGMzayTPs6', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', '39993704b566d53de2ded56a38b372b0e215238d2343141962709c9e7e358c46', '1', '01', '2019-03-11 23:05:17');
INSERT INTO `SYS_USER` VALUES ('16987229cb8f012b51c299533a53c45c', 'test5', 'zqh5', '1', '01', 'zcEzwNdAHHygzuVy4Ljk', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', 'ec3a1db51fa7044f5d38b1a45165b2388bf8b5215b36845c5aeb7117f4bbef71', '1', '01', '2019-03-11 23:05:32');
INSERT INTO `SYS_USER` VALUES ('3386e8e3afb3530148f38c92e88d2760', 'test10', 'zqh10', '1', '01', 'ZuTZqYiw6nXeuFKZlHQN', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', 'fcd254fca577b68bea0ab4e14676b7e192ec413db3861b33d0a18be610d27946', '1', '01', '2019-03-11 23:06:45');
INSERT INTO `SYS_USER` VALUES ('51318681197e770fa84a939c7d9b2b7e', 'admins', 'admin', '1', '01', '2SDXftTJvA3fVuT43IQi', null, '1323533175@qq.com', null, '13324799433', '1', 'bc471c048504039cc8697faceb4d45495218e041a2b3d8b9aaa4fd7a99c50446', '1', '01', '2019-07-06 11:17:02');
INSERT INTO `SYS_USER` VALUES ('55a0a53a3ddb547ae39ddce0c99ac0db', 'test1', 'zqh1', '1', '01', 'KqJO0t4DtdbY29VkZA1e', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', 'c3f1c525c2e0397406592518e43c4fa3eb96e6fb435f0e50d7ba75ca7c354960', '1', '01', '2019-03-11 23:03:52');
INSERT INTO `SYS_USER` VALUES ('693978641514b2a487c5327d21c7f1db', 'test', 'zqh-test', '1', '01', 'lYOxAMBSU46GVxLu28Y1', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', '9134f289eaa675f3888799dd8af3839f1990a4e127210bb0ce3189cd68c2ef1d', '1', '01', '2019-03-11 23:00:51');
INSERT INTO `SYS_USER` VALUES ('7a02e309fd132233ef89485919917147', 'test3', 'zqh3', '1', '01', 'YBs7blBYCr58cbcU9HMG', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', '24add603f66b05d7c794581e0d2784f7470cbac92b01211394f693429b6cf93a', '693978641514b2a487c5327d21c7f1db', '01', '2019-03-11 23:04:56');
INSERT INTO `SYS_USER` VALUES ('84ae26116e52ee5b5684c477b439a73b', 'test8', 'zqh8', '1', '01', 'MHAtQb1Zad7Hn4wTr2Oa', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', 'b851051cfabb78e2fb2c750e40df1d382fde820198b308a66d366cbc09d560e2', '1', '01', '2019-03-11 23:06:18');
INSERT INTO `SYS_USER` VALUES ('ad78b5f00e8b46b12bcc5d4ac849fdca', 'test2', 'zqh2', '1', '01', 'yIl4rKGSufzwAHUmzjFK', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', '2c0baf6b3596a33d7924c504ef4587eda3171e33e7548f67d29336d9aecf970b', '1', '01', '2019-03-11 23:04:42');
INSERT INTO `SYS_USER` VALUES ('bf7eec379a8ea3ed1621bddc459877ea', 'test6', 'zqh6', '1', '01', '5sbTwa23WYhRp3BaO91w', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', '6444b3222fa8377e4a98c91e09dba87e85ad265bb99516763a2418f4d15d766b', '1', '01', '2019-03-11 23:05:44');
INSERT INTO `SYS_USER` VALUES ('f1f0f8fa1b0192b4c81d39a400fcc13f', 'test9', 'zqh9', '1', '01', 'AYEkfWVoC4u4JIKqV99Q', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', '96959c5dece443fd378f5e9d418a6f525596860aba86d3f78de841a64fc5ec75', '1', '01', '2019-03-11 23:06:32');
INSERT INTO `SYS_USER` VALUES ('fdae4efe8d90c57eba22c36fcaa7b3cd', 'test7', 'zqh7', '1', '01', 'TyUnTe9S1nq5gA0C7dnx', 'smtp.qq.com', '939961241@qq.com', '', '15209831990', '1', 'c9570370f46d9dde0c4c521d7ed3dec0e6909136095e23211ce722be05398a11', '1', '01', '2019-03-11 23:06:06');

-- ----------------------------
-- Table structure for `SYS_USER_ROLE`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER_ROLE`;
CREATE TABLE `SYS_USER_ROLE` (
  `ID` varchar(32) NOT NULL,
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of SYS_USER_ROLE
-- ----------------------------
INSERT INTO `SYS_USER_ROLE` VALUES ('07382473f93d70516234de4a8ecab7f4', 'f1f0f8fa1b0192b4c81d39a400fcc13f', 'af6008ecfe093a5d7a5f744ff67e2824');
INSERT INTO `SYS_USER_ROLE` VALUES ('0da11560df417a5618469bb93b90c0cd', '10d5b18fa91779f097704d527e0aeb5e', 'af6008ecfe093a5d7a5f744ff67e2824');
INSERT INTO `SYS_USER_ROLE` VALUES ('1aa0bf6f003f823a6879913e50e65dee', '3386e8e3afb3530148f38c92e88d2760', 'af6008ecfe093a5d7a5f744ff67e2824');
INSERT INTO `SYS_USER_ROLE` VALUES ('2d512eae901197d302519883d4ef9e59', '84ae26116e52ee5b5684c477b439a73b', 'af6008ecfe093a5d7a5f744ff67e2824');
INSERT INTO `SYS_USER_ROLE` VALUES ('3b4046ef919fcfd0231db6c7e2aa5208', '7a02e309fd132233ef89485919917147', 'af6008ecfe093a5d7a5f744ff67e2824');
INSERT INTO `SYS_USER_ROLE` VALUES ('3dce4464dbd881178303c5b4d3683e1e', 'bf7eec379a8ea3ed1621bddc459877ea', 'af6008ecfe093a5d7a5f744ff67e2824');
INSERT INTO `SYS_USER_ROLE` VALUES ('55372fcf93361eda52931161c82af84d', '55a0a53a3ddb547ae39ddce0c99ac0db', 'af6008ecfe093a5d7a5f744ff67e2824');
INSERT INTO `SYS_USER_ROLE` VALUES ('9bbee6ad7651c658f335cd7f961d81c4', 'ad78b5f00e8b46b12bcc5d4ac849fdca', 'af6008ecfe093a5d7a5f744ff67e2824');
INSERT INTO `SYS_USER_ROLE` VALUES ('9d1de327342331f16613c9b03c7aca39', '4a6682abbdd300a0c17907fc601243c6', 'fa178703acdc4f1ca673dd593d1402a6');
INSERT INTO `SYS_USER_ROLE` VALUES ('b8d17328e86a87bd72e208838bf82de3', 'b8573ad84a9f4ab1dcae307abcbdb10b', 'fa178703acdc4f1ca673dd593d1402a6');
INSERT INTO `SYS_USER_ROLE` VALUES ('bef8228d20326d43139e35fd1462ec9b', '693978641514b2a487c5327d21c7f1db', 'af6008ecfe093a5d7a5f744ff67e2824');
INSERT INTO `SYS_USER_ROLE` VALUES ('cc5450ec2957f5401389205b7372a7f1', 'fdae4efe8d90c57eba22c36fcaa7b3cd', 'af6008ecfe093a5d7a5f744ff67e2824');
INSERT INTO `SYS_USER_ROLE` VALUES ('dec1652954d0282037a6a474b0464546', '51318681197e770fa84a939c7d9b2b7e', 'af6008ecfe093a5d7a5f744ff67e2824');
INSERT INTO `SYS_USER_ROLE` VALUES ('ffa577a7ee83707ac488ba6092992f41', '16987229cb8f012b51c299533a53c45c', 'af6008ecfe093a5d7a5f744ff67e2824');

-- ----------------------------
-- Table structure for `SYS_USER_TOKEN`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER_TOKEN`;
CREATE TABLE `SYS_USER_TOKEN` (
  `USER_ID` varchar(32) NOT NULL,
  `TOKEN` varchar(100) NOT NULL COMMENT 'token',
  `EXPIRE_TIME` datetime DEFAULT NULL COMMENT '过期时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `TOKEN` (`TOKEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- ----------------------------
-- Records of SYS_USER_TOKEN
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_PRINTER`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_PRINTER`;
CREATE TABLE `SYS_PRINTER` (
  `ID` varchar(32) NOT NULL,
  `NAME` varchar(100) NOT NULL COMMENT '打印机名称',
  `SN` varchar(100) DEFAULT NULL COMMENT '打印机编号',
  `SHOPS_ID` varchar(100) DEFAULT NULL COMMENT '所属门店',
  `STUB_SN` varchar(100) DEFAULT NULL COMMENT '存根打印机',
  PRIMARY KEY (`ID`),
  KEY `SN` (`SN`) USING BTREE,
  KEY `SHOPS_ID` (`SHOPS_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='飞鹅打印机';

-- ----------------------------
-- Records of SYS_PRINTER
-- ----------------------------

-- ----------------------------
-- Table structure for `WX_MP_MENU`
-- ----------------------------
DROP TABLE IF EXISTS `WX_MP_MENU`;
CREATE TABLE `WX_MP_MENU` (
  `ID` varchar(32) NOT NULL,
  `WX_APPID` varchar(100) DEFAULT NULL,
  `MENU` json DEFAULT NULL COMMENT '菜单',
  `CREATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `DEL_FLAG` char(1) DEFAULT '0' COMMENT '删除标记',
  `PUB_FLAG` char(1) DEFAULT '0' COMMENT '发布标志',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信菜单表';

-- ----------------------------
--  Records of `WX_MP_MENU`
-- ----------------------------
INSERT INTO `WX_MP_MENU` VALUES ('1', 'wxb340c435ee3873dc', '{"button": [{"url": "http://uniapp.fly2you.cn", "name": "uniapp商城", "type": "view"}, {"url": "http://mp.weixin.qq.com", "name": "小程序商城", "type": "miniprogram", "appId": "wxeca4341756496160", "pagePath": "/pages/index/index"}, {"name": "更多", "subButtons": [{"url": "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=Mzg4MTIzNjM2OQ==#wechat_redirect", "name": "历史消息", "type": "view"}, {"url": "https://gitee.com/fuyang_lipengjun/platform-plus", "name": "开源地址", "type": "view"}, {"url": "https://fly2you.cn", "name": "官网", "type": "view"}]}]}', '2019-12-03 07:47:17', '2020-04-05 08:41:22', '1', '1');

DROP TABLE IF EXISTS `WX_MP_CONFIG`;
CREATE TABLE `WX_MP_CONFIG` (
    `ID` varchar(32) NOT NULL,
    `APP_ID` varchar(64) DEFAULT NULL COMMENT '微信公众号appId',
    `SECRET` varchar(128) DEFAULT NULL COMMENT '微信公众号secret',
    `TOKEN` varchar(128) DEFAULT NULL COMMENT '公众号token',
    `AES_KEY` varchar(128) DEFAULT NULL COMMENT '微信公众号EncodingAESKey',
    `CONTENT` varchar(1000) DEFAULT NULL COMMENT '关注后回复',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信公众号配置';
INSERT INTO `WX_MP_CONFIG` VALUES ('1', 'wxb340c435ee3873dc', '', '', '', '您好，欢迎关注安徽微同科技有限公司微信公众号，您可以直接回复消息发现更多服务（如：文档、官网）。');

DROP TABLE IF EXISTS `WX_MA_CONFIG`;
CREATE TABLE `WX_MA_CONFIG` (
    `ID` varchar(32) NOT NULL,
    `APP_ID` varchar(64) DEFAULT NULL COMMENT '微信小程序appId',
    `SECRET` varchar(128) DEFAULT NULL COMMENT '微信小程序secret',
    `TOKEN` varchar(128) DEFAULT NULL COMMENT '小程序token',
    `AES_KEY` varchar(128) DEFAULT NULL COMMENT '微信小程序EncodingAESKey',
    `MSG_DATA_FORMAT` varchar(32) DEFAULT NULL COMMENT '消息格式，XML或者JSON',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信小程序配置';
INSERT INTO `WX_MA_CONFIG` VALUES ('1', 'wxeca4341756496160', '', '', '', 'JSON');


-- ----------------------------
-- Table structure for `MALL_BANK_TYPE`
-- ----------------------------
DROP TABLE IF EXISTS `MALL_BANK_TYPE`;
CREATE TABLE `MALL_BANK_TYPE` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `BANK_NAME` varchar(20) NOT NULL COMMENT '银行名称',
  `BANK_CODE` varchar(11) NOT NULL COMMENT '银行ID',
  PRIMARY KEY (`ID`),
  KEY `BANK_NAME` (`BANK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行类型表';

-- ----------------------------
--  Records of `MALL_BANK_TYPE`
-- ----------------------------
INSERT INTO MALL_BANK_TYPE (ID, BANK_NAME, BANK_CODE) VALUES( 1, '中国工商银行', '1002'),( 2, '中国农业银行', '1005'),( 3, '中国建设银行', '1003'),( 4, '中国银行', '1026'),( 5, '交通银行', '1020'),( 6, '招商银行', '1001'),( 7, '中国邮政储蓄银行', '1066'),( 8, '中国民生银行', '1006'),( 9, '平安银行', '1010'),( 10, '中信银行', '1021'),( 11, '浦发银行', '1004'),( 12, '兴业银行', '1009'),( 13, '中国光大银行', '1022'),( 14, '广发银行', '1027'),( 15, '华夏银行', '1025'),( 16, '宁波银行', '1056'),( 17, '北京银行', '4836'),( 18, '上海银行', '1024'),( 19, '南京银行', '1054'),( 20, '长子县融汇村镇银行', '4755'),( 21, '长沙银行', '4216'),( 22, '浙江泰隆商业银行', '4051'),( 23, '中原银行', '4753'),( 24, '企业银行（中国）', '4761'),( 25, '顺德农商银行', '4036'),( 26, '衡水银行', '4752'),( 27, '长治银行', '4756'),( 28, '大同银行', '4767'),( 29, '河南省农村信用社', '4115'),( 30, '宁夏黄河农村商业银行', '4150'),( 31, '山西省农村信用社联合社', '4156'),( 32, '安徽省农村信用社联合社', '4166'),( 33, '甘肃省农村信用社联合社', '4157'),( 34, '天津农村商业银行', '4153'),( 35, '广西壮族自治区农村信用社联合社', '4113'),( 36, '陕西省农村信用社联合社', '4108'),( 37, '深圳农村商业银行', '4076'),( 38, '宁波鄞州农村商业银行', '4052'),( 39, '浙江省农村信用社联合社', '4764'),( 40, '江苏省农村信用社联合社', '4217'),( 41, '江苏紫金农村商业银行股份有限公司', '4072'),( 42, '北京中关村银行股份有限公司', '4769'),( 43, '星展银行（中国）有限公司', '4778'),( 44, '枣庄银行股份有限公司', '4766'),( 45, '海口联合农村商业银行股份有限公司', '4758'),( 46, '南洋商业银行（中国）有限公司', '4763');

-- ----------------------------
-- Table structure for `MALL_USER_BANK_CARD`
-- ----------------------------
DROP TABLE IF EXISTS `MALL_USER_BANK_CARD`;
CREATE TABLE `MALL_USER_BANK_CARD` (
    `ID` varchar(32) NOT NULL COMMENT '主键',
    `USER_ID` varchar(32) DEFAULT NULL COMMENT '会员ID',
    `CARD_NAME` varchar(32) DEFAULT NULL COMMENT '收款人姓名',
    `CARD_NUMBER` varchar(32) DEFAULT NULL COMMENT '银行卡号',
    `CARD_TYPE` varchar(32) DEFAULT NULL COMMENT '卡类型',
    `BANK_TYPE_ID` int(11) unsigned NOT NULL COMMENT '银行ID',
    `CARD_STATUS` tinyint(1) NOT NULL COMMENT '1：已绑定 2：已解绑',
    `BOUND_AT`  datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '绑定时间',
    PRIMARY KEY (`ID`),
    KEY `USER_ID` (`USER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户银行卡表';

-- ----------------------------
--  Records of `SYS_MENU`
-- ----------------------------
INSERT INTO `SYS_MENU` VALUES ('1611', '16', '会员银行卡', 'mall/userbankcard', 'mall:userbankcard:list,mall:userbankcard:info', 1, 'card', 11);
INSERT INTO `SYS_MENU` VALUES ('161101', '1601', '新增', null, 'mall:userbankcard:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('161102', '1601', '修改', null, 'mall:userbankcard:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('161103', '1601', '删除', null, 'mall:userbankcard:delete', 2, null, 0);

INSERT INTO `SYS_MENU` VALUES ('1611', '16', '会员银行卡', 'mall/userbankcard', 'mall:userbankcard:list,mall:userbankcard:info', 1, 'card', 11);
INSERT INTO `SYS_MENU` VALUES ('161101', '1601', '新增', null, 'mall:userbankcard:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('161102', '1601', '修改', null, 'mall:userbankcard:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('161103', '1601', '删除', null, 'mall:userbankcard:delete', 2, null, 0);

INSERT INTO `SYS_MENU` VALUES ('23', '0', '订单管理', null, null, '0', 'system', '0');
INSERT INTO `SYS_MENU` VALUES ('2301', '23', '订单管理', 'mall/order', 'ad:order:list,ad:order:info', 1, 'order', 11);
INSERT INTO `SYS_MENU` VALUES ('230101', '2301', '新增', null, 'ad:order:save', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('230102', '2301', '修改', null, 'ad:order:update', 2, null, 0);
INSERT INTO `SYS_MENU` VALUES ('230103', '2301', '删除', null, ' ad:order:delete', 2, null, 0);

INSERT INTO `SYS_MENU` VALUES ('22', '0', '机柜管理', '', '', '0', 'model', '12');
INSERT INTO `SYS_MENU` VALUES ('2201', '22', '机柜管理', 'mall/tissuemachine', 'ad:tissuemachine:list,ad:tissuemachine:info', '1', 'mall', '0');
INSERT INTO `SYS_MENU` VALUES ('220101', '2201', '新增', '', 'ad:tissuemachine:save', '2', '', '0');
INSERT INTO `SYS_MENU` VALUES ('220102', '2201', '修改', '', 'ad:tissuemachine:update', '2', '', '0');
INSERT INTO `SYS_MENU` VALUES ('220103', '2201', '删除', '', 'ad:tissuemachine:delete', '2', '', '0');
INSERT INTO `SYS_MENU` VALUES ('2202', '22', '贴片广告管理', 'mall/patch', 'ad:patch:list,ad:patch:info', '1', 'kuwei', '1');
INSERT INTO `SYS_MENU` VALUES ('220201', '2202', '新增', '', 'ad:patch:save', '2', '', '0');
INSERT INTO `SYS_MENU` VALUES ('220202', '2202', '修改', '', 'ad:patch:update', '2', '', '0');
INSERT INTO `SYS_MENU` VALUES ('220203', '2202', '删除', '', 'ad:patch:delete', '2', '', '0');

INSERT INTO `SYS_MENU` VALUES ('26', '0', '广告管理', '', '', '0', 'bulletin', '16');
INSERT INTO `SYS_MENU` VALUES ('2601', '26', '扫码广告管理', 'mall/scan', 'ad:scan:list,ad:scan:info', '1', 'media', '0');
INSERT INTO `SYS_MENU` VALUES ('260101', '2601', '新增', '', 'ad:scan:save', '2', '', '0');
INSERT INTO `SYS_MENU` VALUES ('260102', '2601', '修改', '', 'ad:scan:update', '2', '', '0');
INSERT INTO `SYS_MENU` VALUES ('260103', '2601', '删除', '', 'ad:scan:delete', '2', '', '0');

INSERT INTO `SYS_MENU` VALUES ('2204', '22', '机柜商品库存管理', 'mall/machinestock', 'ad:machinestock:list,ad:machinestock:info', '1', 'media', '0');
INSERT INTO `SYS_MENU` VALUES ('220401', '2204', '新增', '', 'ad:machinestock:save', '2', '', '0');
INSERT INTO `SYS_MENU` VALUES ('220402', '2204', '修改', '', 'ad:machinestock:update', '2', '', '0');
INSERT INTO `SYS_MENU` VALUES ('220403', '2204', '删除', '', 'ad:machinestock:delete', '2', '', '0');

INSERT INTO `SYS_MENU` VALUES ('31', '0', '客服管理', '', '', 0, 'qq', 11);
INSERT INTO `SYS_MENU` VALUES ('3101', '31', '客服消息管理', 'mall/chatrecord', 'ad:chatrecord:list,ad:chatrecord:info', 1, 'feedback', 0);
INSERT INTO `SYS_MENU` VALUES ('310101', '3101', '新增', '', 'ad:chatrecord:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('310102', '3101', '修改', '', 'ad:chatrecord:update', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('310103', '3101', '删除', '', 'ad:chatrecord:delete', 2, '', 0);

INSERT INTO `SCHEDULE_JOB` (`JOB_ID`, `BEAN_NAME`, `METHOD_NAME`, `PARAMS`, `CRON_EXPRESSION`, `STATUS`, `REMARK`, `CREATE_TIME`) VALUES ('2812fd28cd30086ce004d8beed645696', 'MachineTask', 'sendMachineTask', '', '0 0/5 * * * ? ', '0', '发送获取CCID剩余流量、机柜版本号、经纬度请求给机柜', '2020-11-06 16:38:57');

INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('2603', '26', '语音广告管理', 'mall/machinevoice', 'ad:machinevoice:list,ad:machinevoice:info', '1', 'bulletin', '3');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('260301', '2603', '新增', '', 'ad:machinevoice:save', '2', '', '0');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('260302', '2603', '修改', '', 'ad:machinevoice:update', '2', '', '0');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('260303', '2603', '删除', '', 'ad:machinevoice:delete', '2', '', '0');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('220104', '2201', '重启', '', 'ad:tissuemachine:restart', '2', '', '0');

INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('29', '0', '财务管理', NULL, NULL, '0', 'diamond', '7');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('2901', '29', '账户余额', '', '', '0', 'tempmsg', '0');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('290101', '2901', '银行卡管理', 'mall/userbank', 'ad:userbank:list,ad:userbank:info', '1', 'card', '0');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('29010101', '290101', '新增', '', 'ad:userbank:save', '2', '', '0');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('29010102', '290101', '修改', '', 'ad:userbank:update', '2', '', '0');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('29010103', '290101', '删除', '', 'ad:userbank:delete', '2', '', '0');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('290102', '2901', '申请提现', 'mall/userwithdrawal', 'ad:withdrawal:list,ad:withdrawal:info', '1', 'interface', '1');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('29010201', '290102', '新增', '', 'ad:withdrawal:save', '2', '', '0');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('29010202', '290102', '修改', '', 'ad:withdrawal:save', '2', '', '0');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('2902', '29', '提现审核列表', 'mall/withdrawal', 'ad:withdrawal:list,ad:withdrawal:info', '1', 'zhedie', '2');
INSERT INTO `SYS_MENU` (`MENU_ID`, `PARENT_ID`, `NAME`, `URL`, `PERMS`, `TYPE`, `ICON`, `ORDER_NUM`) VALUES ('290201', '2902', '修改', '', 'ad:withdrawal:update', '2', '', '0');

INSERT INTO `SYS_MENU` VALUES ('1603', '16', '公众号关注用户表', 'mall/mpuser', ' ad:mpuser:list,ad:mpuser:info', 1, 'feedback', 0);
INSERT INTO `SYS_MENU` VALUES ('160301', '1603', '新增', '', 'ad:mpuser:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('160302', '1603', '修改', '', 'ad:mpuser:update', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('160303', '1603', '删除', '', 'ad:mpuser:delete', 2, '', 0);


INSERT INTO `SYS_MENU` VALUES ('1603', '16', '公众号关注用户表', 'mall/mpuser', ' ad:mpuser:list,ad:mpuser:info', 1, 'feedback', 0);
INSERT INTO `SYS_MENU` VALUES ('160301', '1603', '新增', '', 'ad:mpuser:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('160302', '1603', '修改', '', 'ad:mpuser:update', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('160303', '1603', '删除', '', 'ad:mpuser:delete', 2, '', 0);

INSERT INTO `SYS_MENU` VALUES ('2205', '22', '机柜操作记录', 'mall/machineoperate', ' ad:machineoperate:list,ad:machineoperate:info', 1, 'feedback', 0);
INSERT INTO `SYS_MENU` VALUES ('220501', '2205', '新增', '', 'ad:machineoperate:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('220502', '2205', '修改', '', 'ad:machineoperate:update', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('220503', '2205', '删除', '', 'ad:machineoperate:delete', 2, '', 0);


INSERT INTO `SYS_MENU` VALUES ('1008', '10', '余额充值管理', 'mall/rechargelevel', 'ad:rechargelevel:list,ad:rechargelevel:info', 1, 'feedback', 0);
INSERT INTO `SYS_MENU` VALUES ('100801', '1008', '新增', '', 'ad:rechargelevel:save', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('100802', '1008', '修改', '', 'ad:rechargelevel:update', 2, '', 0);
INSERT INTO `SYS_MENU` VALUES ('100803', '1008', '删除', '', 'ad:rechargelevel:delete', 2, '', 0);

INSERT INTO `SYS_MENU` VALUES ('2604', '26', '临时语音管理', 'mall/machineprovisionalvoice', ' ad:machineprovisionalvoice:list,ad:machineprovisionalvoice:info', 1,  'feedback', 0,0);
INSERT INTO `SYS_MENU` VALUES ('260401', '2604', '新增', '', 'ad:machineprovisionalvoice:save', 2, '', 0,0);
INSERT INTO `SYS_MENU` VALUES ('260402', '2604', '修改', '', 'ad:machineprovisionalvoice:update', 2, '', 0,0);
INSERT INTO `SYS_MENU` VALUES ('260403', '2604', '删除', '', 'ad:machineprovisionalvoice:delete', 2, '', 0,0);

INSERT INTO `SYS_MENU` VALUES ('2206', '22', '分组管理', 'mall/group', ' ad:group:list,ad:group:info', 1,  'feedback', 0,0);
INSERT INTO `SYS_MENU` VALUES ('220601', '2206', '新增', '', 'ad:group:save', 2, '', 0,0);
INSERT INTO `SYS_MENU` VALUES ('220602', '2206', '修改', '', 'ad:group:update', 2, '', 0,0);
INSERT INTO `SYS_MENU` VALUES ('220603', '2206', '删除', '', 'ad:group:delete', 2, '', 0,0);

INSERT INTO `SYS_MENU` VALUES ('2207', '22', '分组管理', 'mall/machinegroup', ' ad:machinegroup:list,ad:machinegroup:info', 1,  'feedback', 0,0);
INSERT INTO `SYS_MENU` VALUES ('220701', '2207', '新增', '', 'ad:machinegroup:save', 2, '', 0,0);
INSERT INTO `SYS_MENU` VALUES ('220702', '2207', '修改', '', 'ad:machinegroup:update', 2, '', 0,0);
INSERT INTO `SYS_MENU` VALUES ('220703', '2207', '删除', '', 'ad:machinegroup:delete', 2, '', 0,0);




-- ----------------------------
-- Table structure for ad_machine_report
-- ----------------------------
DROP TABLE IF EXISTS `AD_MACHINE_REPORT`;
CREATE TABLE `AD_MACHINE_REPORT` (
  `ID` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT 'ID',
  `MACHINE_SN` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '机柜ID',
  `REPORT_TIME` datetime DEFAULT NULL COMMENT '最近一次的上报时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `AD_GROUP` (
  `ID` varchar(32) NOT NULL,
  `USER_OPEN_ID` varchar(32) DEFAULT NULL COMMENT '负责人OPEN_ID',
  `GROUP_NAME` varchar(255) DEFAULT NULL COMMENT '分组(片区)名字',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备分组(片区)表';


CREATE TABLE `AD_MACHINE_GROUP` (
  `ID` varchar(32) NOT NULL,
  `GROUP_ID` varchar(32) DEFAULT NULL COMMENT '分组ID',
  `MACHINE_ID` varchar(32) DEFAULT NULL COMMENT '机柜ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机柜分组表';




CREATE TABLE `AD_TISSUE_MACHINE` (
  `ID` varchar(32) NOT NULL COMMENT '纸巾机ID',
  `SN` varchar(128) NOT NULL COMMENT '终端唯一码',
  `NAME` varchar(64) DEFAULT NULL COMMENT '纸巾机名称',
  `STATUS` smallint(4) DEFAULT NULL COMMENT '纸巾机状态 1正常 2设备维修中 3 出纸异常 4 位置异常 5 纸巾不足预警 6 电量不足预警 7 语音修改异常 8 离线',
  `LATITUDE` decimal(32,6) DEFAULT NULL COMMENT '小程序地图纬度、安装经纬度',
  `LONGITUDE` decimal(32,6) DEFAULT NULL COMMENT '小程序地图经度、安装经纬度',
  `MACHINE_IMAGE` varchar(255) DEFAULT NULL COMMENT '机器图片',
  `ADDRESS` varchar(256) DEFAULT NULL COMMENT '地址',
  `WORK_TIME` varchar(256) DEFAULT NULL COMMENT '工作时间 如：周一至周五 9:00-22:00',
  `TISSUE_NUMBER` int(11) DEFAULT NULL COMMENT '剩余纸巾数量',
  `CREATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改设备时间',
  `REMARK` text COMMENT '备注',
  `IS_DELETE` tinyint(1) DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
  `MACHINE_LOGO` varchar(255) DEFAULT NULL COMMENT '纸巾机Logo',
  `IS_SHOW_LOGO` tinyint(1) DEFAULT '0' COMMENT '是否在地图显示Logo  0不显示 1显示',
  `AGENT_ID` varchar(32) DEFAULT NULL COMMENT '代理userId',
  `MACHINE_VERSION` varchar(255) DEFAULT NULL COMMENT '设备版本号',
  `SIM_CCID` varchar(255) DEFAULT NULL COMMENT '设备ICCID卡号',
  `SERVER_URL` varchar(255) DEFAULT NULL COMMENT '服务器URL',
  `REPORT_LATITUDE` decimal(32,6) DEFAULT NULL COMMENT '设备上报纬度',
  `REPORT_LONGITUDE` decimal(32,6) DEFAULT NULL COMMENT '设备上报经度',
  `IS_OPEN_LOCATE` tinyint(1) DEFAULT NULL COMMENT '是否开启定位，0不开启（室内），1开启（室外）',
  `ELECTRICITY_QUANTITY` int(11) DEFAULT '20' COMMENT '设备电量',
  `ORDER_FAIL_COUNT` int(11) DEFAULT '0' COMMENT '出纸异常订单数量',
  `IS_USED` tinyint(1) DEFAULT '0' COMMENT '是否正在使用中 1使用中  0未使用',
  `LIGHT_STATUS` tinyint(1) DEFAULT '0' COMMENT '灯条状态',
  `USED_FLOW` decimal(10,2) DEFAULT '0.00' COMMENT '已使用流量',
  `CONTENT` varchar(255) DEFAULT NULL COMMENT '语音内容',
  `PROVINCE_CODE` int(11) DEFAULT NULL COMMENT '省级区划代码',
  `CITY_CODE` int(11) DEFAULT NULL COMMENT '市级区划代码',
  `DISTRICT_CODE` int(11) DEFAULT NULL COMMENT '区级区划代码',
  `PROVINCE` varchar(64) DEFAULT NULL COMMENT '省级区划',
  `CITY` varchar(64) DEFAULT '' COMMENT '市级区划',
  `DISTRICT` varchar(64) DEFAULT NULL COMMENT '区级区划',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE KEY `SN` (`SN`) USING BTREE COMMENT 'SN码唯一索引',
  KEY `AGEND_ID` (`AGENT_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='纸巾机表';