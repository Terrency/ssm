/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50170
Source Host           : localhost:3306
Source Database       : ssm

Target Server Type    : MYSQL
Target Server Version : 50170
File Encoding         : 65001

Date: 2017-08-16 21:44:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(36) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(36) NOT NULL COMMENT '用户ID',
  `role_id` bigint(36) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `SYS_USER_ROLE_UK` (`user_id`,`role_id`),
  KEY `SYS_USER_ROLE_FK_2` (`role_id`),
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('10001', '101', '1001');
INSERT INTO `sys_user_role` VALUES ('10005', '101', '1002');
INSERT INTO `sys_user_role` VALUES ('10007', '102', '1005');
INSERT INTO `sys_user_role` VALUES ('10006', '103', '1005');
