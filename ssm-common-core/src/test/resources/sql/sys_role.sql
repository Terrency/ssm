/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50170
Source Host           : localhost:3306
Source Database       : ssm

Target Server Type    : MYSQL
Target Server Version : 50170
File Encoding         : 65001

Date: 2017-08-16 21:44:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(36) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) NOT NULL COMMENT '角色名称',
  `status` char(1) DEFAULT NULL COMMENT '状态(1 可用 0 不可用)',
  `remark` varchar(200) NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `SYS_ROLE_NAME_UK` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1001', '超级管理员', '1', '');
INSERT INTO `sys_role` VALUES ('1002', '用户管理员', '1', '');
INSERT INTO `sys_role` VALUES ('1005', '测试角色', '1', '');
