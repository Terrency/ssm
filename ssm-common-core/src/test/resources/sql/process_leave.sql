/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50170
Source Host           : localhost:3306
Source Database       : ssm

Target Server Type    : MYSQL
Target Server Version : 50170
File Encoding         : 65001

Date: 2017-08-16 21:43:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `process_leave`
-- ----------------------------
DROP TABLE IF EXISTS `process_leave`;
CREATE TABLE `process_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `days` int(11) NOT NULL COMMENT '请假天数',
  `content` varchar(32) NOT NULL COMMENT '事由',
  `apply_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '请假时间',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `applicant` varchar(32) DEFAULT NULL COMMENT '申请人',
  `status` int(11) NOT NULL COMMENT '状态(0初始录入 1正在申请 2正在审批 3审批完成)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of process_leave
-- ----------------------------
INSERT INTO `process_leave` VALUES ('10', '15', '旅游', '2017-07-23 09:45:00', '世界这么大', 'tester', '2');

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
