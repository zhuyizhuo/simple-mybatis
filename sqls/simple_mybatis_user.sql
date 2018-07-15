/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : yizhuo

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-07-15 23:05:19
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `simple_mybatis_user`
-- ----------------------------
DROP TABLE IF EXISTS `simple_mybatis_user`;
CREATE TABLE `simple_mybatis_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `age` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'test-mybatis',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of simple_mybatis_user
-- ----------------------------
INSERT INTO simple_mybatis_user VALUES ('1', '66632-typehandler', null);
INSERT INTO simple_mybatis_user VALUES ('2', '5522-typehandler', null);
INSERT INTO simple_mybatis_user VALUES ('3', '33-typehandler', null);
INSERT INTO simple_mybatis_user VALUES ('4', '33-typehandler', null);
INSERT INTO simple_mybatis_user VALUES ('5', '33-typehandler', null);
INSERT INTO simple_mybatis_user VALUES ('6', '33-typehandler', null);
INSERT INTO simple_mybatis_user VALUES ('7', '33-typehandler', null);
INSERT INTO simple_mybatis_user VALUES ('8', '33-typehandler', null);
INSERT INTO simple_mybatis_user VALUES ('9', '33-typehandler', null);
INSERT INTO simple_mybatis_user VALUES ('10', '33-typehandler', null);
