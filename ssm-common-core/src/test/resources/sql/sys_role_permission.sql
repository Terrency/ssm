/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50170
Source Host           : localhost:3306
Source Database       : ssm

Target Server Type    : MYSQL
Target Server Version : 50170
File Encoding         : 65001

Date: 2017-08-16 21:44:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(36) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(36) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `SYS_ROLE_PERMISSION_UK` (`role_id`,`permission_id`),
  KEY `SYS_ROLE_PERMISSION_FK_2` (`permission_id`),
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100152 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('100000', '1001', '1');
INSERT INTO `sys_role_permission` VALUES ('100100', '1001', '2');
INSERT INTO `sys_role_permission` VALUES ('100001', '1001', '11');
INSERT INTO `sys_role_permission` VALUES ('100002', '1001', '12');
INSERT INTO `sys_role_permission` VALUES ('100003', '1001', '13');
INSERT INTO `sys_role_permission` VALUES ('100004', '1001', '14');
INSERT INTO `sys_role_permission` VALUES ('100005', '1001', '15');
INSERT INTO `sys_role_permission` VALUES ('100006', '1001', '21');
INSERT INTO `sys_role_permission` VALUES ('100007', '1001', '22');
INSERT INTO `sys_role_permission` VALUES ('100008', '1001', '23');
INSERT INTO `sys_role_permission` VALUES ('100009', '1001', '24');
INSERT INTO `sys_role_permission` VALUES ('100010', '1001', '25');
INSERT INTO `sys_role_permission` VALUES ('100011', '1001', '31');
INSERT INTO `sys_role_permission` VALUES ('100012', '1001', '32');
INSERT INTO `sys_role_permission` VALUES ('100013', '1001', '33');
INSERT INTO `sys_role_permission` VALUES ('100014', '1001', '34');
INSERT INTO `sys_role_permission` VALUES ('100015', '1001', '35');
INSERT INTO `sys_role_permission` VALUES ('100101', '1001', '36');
INSERT INTO `sys_role_permission` VALUES ('100102', '1001', '37');
INSERT INTO `sys_role_permission` VALUES ('100103', '1001', '38');
INSERT INTO `sys_role_permission` VALUES ('100104', '1001', '39');
INSERT INTO `sys_role_permission` VALUES ('100016', '1002', '1');
INSERT INTO `sys_role_permission` VALUES ('100017', '1002', '11');
INSERT INTO `sys_role_permission` VALUES ('100018', '1002', '12');
INSERT INTO `sys_role_permission` VALUES ('100019', '1002', '13');
INSERT INTO `sys_role_permission` VALUES ('100020', '1002', '14');
INSERT INTO `sys_role_permission` VALUES ('100021', '1002', '15');
INSERT INTO `sys_role_permission` VALUES ('100147', '1005', '2');
INSERT INTO `sys_role_permission` VALUES ('100148', '1005', '36');
INSERT INTO `sys_role_permission` VALUES ('100149', '1005', '37');
INSERT INTO `sys_role_permission` VALUES ('100150', '1005', '38');
INSERT INTO `sys_role_permission` VALUES ('100151', '1005', '39');
