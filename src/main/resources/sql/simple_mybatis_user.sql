/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : yizhuo

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-07-22 11:07:21
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `simple_mybatis_user`
-- ----------------------------
DROP TABLE IF EXISTS `simple_mybatis_user`;
CREATE TABLE `simple_mybatis_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名\r\n1212',
  `age` varchar(20) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of simple_mybatis_user
-- ----------------------------
INSERT INTO simple_mybatis_user VALUES ('1', '66632-typehandler', null);
INSERT INTO simple_mybatis_user VALUES ('2', '5522-typehandler', null);
INSERT INTO simple_mybatis_user VALUES ('3', '33-typehandler', null);

