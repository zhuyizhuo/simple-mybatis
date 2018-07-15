/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : yizhuo

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-07-15 23:05:03
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `simple_mybatis_order`
-- ----------------------------
DROP TABLE IF EXISTS `simple_mybatis_order`;
CREATE TABLE `simple_mybatis_order` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `gmt_create` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of simple_mybatis_order
-- ----------------------------
