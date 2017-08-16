/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50170
Source Host           : localhost:3306
Source Database       : ssm

Target Server Type    : MYSQL
Target Server Version : 50170
File Encoding         : 65001

Date: 2017-08-16 21:44:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(36) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(20) NOT NULL COMMENT '用户帐号',
  `name` varchar(20) NOT NULL COMMENT '用户名',
  `pass` varchar(32) NOT NULL COMMENT '密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐(密码加密时使用以防止穷举暴力破解,一般由随机数生成)',
  `status` char(1) DEFAULT NULL COMMENT '状态(1 未锁定 0 锁定)',
  `manager` bigint(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `SYS_USER_FK` (`manager`),
  CONSTRAINT `SYS_USER_FK` FOREIGN KEY (`manager`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('101', 'admin', '管理员', '3bdabf30b9f0a7a60d0f5a6237aac19d', '540fcc7f47e4d63e20e0e18f35b4fb46', '1', null);
INSERT INTO `sys_user` VALUES ('102', 'gavin', '部门经理', '3986874b369863a88de3f82728004977', '10086', '1', '101');
INSERT INTO `sys_user` VALUES ('103', 'tester', '测试用户', 'de9c6681b94e69c90b39913fe39d9f5c', '7fc03271eca636e2c89496be6921c18b', '1', '102');
