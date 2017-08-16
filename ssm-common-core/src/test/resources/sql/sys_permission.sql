/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50170
Source Host           : localhost:3306
Source Database       : ssm

Target Server Type    : MYSQL
Target Server Version : 50170
File Encoding         : 65001

Date: 2017-08-16 21:44:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(36) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(128) DEFAULT NULL COMMENT '资源代码',
  `name` varchar(128) NOT NULL COMMENT '资源名称',
  `type` varchar(32) NOT NULL COMMENT '资源类型(菜单|功能)',
  `icon` varchar(32) NOT NULL COMMENT '资源图标',
  `url` varchar(128) DEFAULT NULL COMMENT '资源路径',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父结点ID',
  `parent_ids` varchar(128) DEFAULT NULL COMMENT '父结点ID列表串',
  `status` char(1) DEFAULT NULL COMMENT '状态(1 可用 0 不可用)',
  `seq` int(11) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`),
  KEY `SYS_PERMISSION_FK` (`parent_id`),
  CONSTRAINT `sys_permission_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `sys_permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '', '系统管理', 'MENU', '', '', null, null, '1', '1');
INSERT INTO `sys_permission` VALUES ('2', '', '流程管理', 'MENU', '', '', null, '', '1', '2');
INSERT INTO `sys_permission` VALUES ('11', 'user:query', '用户管理', 'MENU', '', 'user/list', '1', '1/', '1', '101');
INSERT INTO `sys_permission` VALUES ('12', 'user:create', '用户新增', 'FUNC', '', 'user/add', '11', '1/11/', '1', '102');
INSERT INTO `sys_permission` VALUES ('13', 'user:delete', '用户删除', 'FUNC', '', 'user/delete', '11', '1/11/', '1', '103');
INSERT INTO `sys_permission` VALUES ('14', 'user:update', '用户修改', 'FUNC', '', 'user/edit', '11', '1/11/', '1', '104');
INSERT INTO `sys_permission` VALUES ('15', 'user:query', '用户查询', 'FUNC', '', 'user/query', '11', '1/11/', '1', '105');
INSERT INTO `sys_permission` VALUES ('21', 'role:query', '角色管理', 'MENU', '', 'role/list', '1', '1/', '1', '201');
INSERT INTO `sys_permission` VALUES ('22', 'role:create', '角色新增', 'FUNC', '', 'role/add', '21', '1/21/', '1', '202');
INSERT INTO `sys_permission` VALUES ('23', 'role:delete', '角色删除', 'FUNC', '', 'role/delete', '21', '1/21/', '1', '203');
INSERT INTO `sys_permission` VALUES ('24', 'role:update', '角色修改', 'FUNC', '', 'role/edit', '21', '1/21/', '1', '204');
INSERT INTO `sys_permission` VALUES ('25', 'role:query', '角色查询', 'FUNC', '', 'role/query', '21', '1/21/', '1', '205');
INSERT INTO `sys_permission` VALUES ('31', 'permission:query', '权限管理', 'MENU', '', 'permission/list', '1', '1/', '1', '301');
INSERT INTO `sys_permission` VALUES ('32', 'permission:create', '权限新增', 'FUNC', '', 'permission/add', '31', '1/31/', '1', '302');
INSERT INTO `sys_permission` VALUES ('33', 'permission:delete', '权限删除', 'FUNC', '', 'permission/delete', '31', '1/31/', '1', '303');
INSERT INTO `sys_permission` VALUES ('34', 'permission:update', '权限修改', 'FUNC', '', 'permission/edit', '31', '1/31/', '1', '304');
INSERT INTO `sys_permission` VALUES ('35', 'permission:query', '权限查询', 'FUNC', '', 'permission/query', '31', '1/31/', '1', '305');
INSERT INTO `sys_permission` VALUES ('36', 'process:deploy', '部署管理', 'MENU', '', 'process/deploy', '2', '2/', '1', '11');
INSERT INTO `sys_permission` VALUES ('37', 'leave:list', '业务流程', 'MENU', '', 'leave/list', '2', '2/', '1', '22');
INSERT INTO `sys_permission` VALUES ('38', 'process:task', '我的任务', 'MENU', '', 'process/task', '2', '2/', '1', '33');
INSERT INTO `sys_permission` VALUES ('39', 'process:historyTask', '历史任务', 'MENU', '', 'process/historyTask', '2', '2/', '1', '44');
