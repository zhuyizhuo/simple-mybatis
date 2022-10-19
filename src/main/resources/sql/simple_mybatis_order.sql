/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : yizhuo

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-07-22 11:07:27
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `simple_mybatis_order`
-- ----------------------------
DROP TABLE IF EXISTS `simple_mybatis_order`;
CREATE TABLE `simple_mybatis_order` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) DEFAULT NULL COMMENT '订单号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `gmt_create` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

INSERT INTO simple_mybatis_order (id, order_no, user_id, gmt_create) VALUES (1, '1', '2', '2020-10-19 16:01:56');
INSERT INTO simple_mybatis_order (id, order_no, user_id, gmt_create) VALUES (2, '2', '22', '2020-10-19 16:02:25');