
-- CREATE DATABASE ssm CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
-- USE ssm;

CREATE TABLE SYS_USER (
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	code VARCHAR(32) NOT NULL COMMENT '用户帐号',
	name VARCHAR(32) NOT NULL COMMENT '用户名',
  pass VARCHAR(32) NOT NULL COMMENT '密码',
	salt VARCHAR(32) DEFAULT NULL COMMENT '盐(密码加密时使用以防止穷举暴力破解,一般由随机数生成)',
  status CHAR(1) DEFAULT NULL COMMENT '状态(1 未锁定 0 锁定)',
  manager BIGINT(20) DEFAULT NULL COMMENT '上级',
  PRIMARY KEY SYS_USER_PK (id),
  FOREIGN KEY SYS_USER_FK (manager) REFERENCES SYS_USER (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE SYS_ROLE (
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	name VARCHAR(128) NOT NULL COMMENT '角色名称',
	status CHAR(1) DEFAULT NULL COMMENT '状态(1 可用 0 不可用)',
  remark VARCHAR(200) NOT NULL COMMENT '备注',
	UNIQUE KEY SYS_ROLE_NAME_UK (name),
  PRIMARY KEY SYS_ROLE_PK (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE SYS_PERMISSION (
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  code VARCHAR(32) DEFAULT NULL COMMENT '资源代码',
	name VARCHAR(32) NOT NULL COMMENT '资源名称',
  type VARCHAR(32) NOT NULL COMMENT '资源类型(菜单|功能)',
  icon VARCHAR(32) NOT NULL COMMENT '资源图标',
  url VARCHAR(128) DEFAULT NULL COMMENT '资源路径',
  parent_id BIGINT(20) DEFAULT NULL COMMENT '父结点ID',
	parent_ids VARCHAR(32) DEFAULT NULL COMMENT '父结点ID列表串',
	status CHAR(1) DEFAULT NULL COMMENT '状态(1 可用 0 不可用)',
  seq INT(11) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY SYS_PERMISSION_PK (id),
  FOREIGN KEY SYS_PERMISSION_FK (parent_id) REFERENCES SYS_PERMISSION (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE SYS_USER_ROLE (
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	user_id BIGINT(20) NOT NULL COMMENT '用户ID',
	role_id BIGINT(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY SYS_USER_ROLE_PK (id),
	UNIQUE  KEY SYS_USER_ROLE_UK (user_id, role_id),
	FOREIGN KEY SYS_USER_ROLE_FK_1 (user_id) REFERENCES SYS_USER (id),
	FOREIGN KEY SYS_USER_ROLE_FK_2 (role_id) REFERENCES SYS_ROLE (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE SYS_ROLE_PERMISSION (
	id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	role_id BIGINT(20) NOT NULL COMMENT '角色ID',
	permission_id BIGINT(20) NOT NULL COMMENT '权限ID',
  PRIMARY KEY SYS_ROLE_PERMISSION_PK (id),
	UNIQUE  KEY SYS_ROLE_PERMISSION_UK (role_id, permission_id),
	FOREIGN KEY SYS_ROLE_PERMISSION_FK_1 (role_id) REFERENCES SYS_ROLE (id),
	FOREIGN KEY SYS_ROLE_PERMISSION_FK_2 (permission_id) REFERENCES SYS_PERMISSION (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO SYS_PERMISSION (id, code, name, type, url, parent_id, parent_ids, status, seq)
VALUES ( 1, NULL,                  '系统管理', 'MENU', NULL,               NULL,    NULL, '1',   1),
       (11, 'user:query',          '用户管理', 'MENU', 'user/list',           1,    '1/', '1', 101),
       (12, 'user:create',         '用户新增', 'FUNC', 'user/add',           11, '1/11/', '1', 102),
       (13, 'user:delete',         '用户删除', 'FUNC', 'user/delete',        11, '1/11/', '1', 103),
       (14, 'user:update',         '用户修改', 'FUNC', 'user/edit',          11, '1/11/', '1', 104),
       (15, 'user:query',          '用户查询', 'FUNC', 'user/query',         11, '1/11/', '1', 105),
       (21, 'role:query',          '角色管理', 'MENU', 'role/list',           1,    '1/', '1', 201),
       (22, 'role:create',         '角色新增', 'FUNC', 'role/add',           21, '1/21/', '1', 202),
       (23, 'role:delete',         '角色删除', 'FUNC', 'role/delete',        21, '1/21/', '1', 203),
       (24, 'role:update',         '角色修改', 'FUNC', 'role/edit',          21, '1/21/', '1', 204),
       (25, 'role:query',          '角色查询', 'FUNC', 'role/query',         21, '1/21/', '1', 205),
       (31, 'permission:query',    '权限管理', 'MENU', 'permission/list',     1,    '1/', '1', 301),
       (32, 'permission:create',   '权限新增', 'FUNC', 'permission/add',     31, '1/31/', '1', 302),
       (33, 'permission:delete',   '权限删除', 'FUNC', 'permission/delete',  31, '1/31/', '1', 303),
       (34, 'permission:update',   '权限修改', 'FUNC', 'permission/edit',    31, '1/31/', '1', 304),
       (35, 'permission:query',    '权限查询', 'FUNC', 'permission/query',   31, '1/31/', '1', 305),
       ( 2, NULL,                  '流程管理', 'MENU', NULL,               NULL,    NULL, '1', 400),
       (41, 'process:deploy',      '部署管理', 'MENU', 'process/deploy',      2,    '2/', '1', 401),
       (42, NULL,                  '业务流程', 'MENU', NULL,                  2,    '2/', '1', 402),
       (43, 'process:task',        '我的任务', 'MENU', 'process/task',        2,    '2/', '1', 403),
       (44, 'process:historyTask', '历史任务', 'MENU', 'process/historyTask', 2,    '2/', '1', 404);

INSERT INTO sys_user (id, code, name, pass, salt, status)
VALUES (101, 'admin', 'Admin', (SELECT MD5(CONCAT('10086','111111'))), '10086', '1'),
       (102, 'gavin', 'Gavin', (SELECT MD5(CONCAT('10086','111111'))), '10086', '1');

INSERT INTO sys_role (id, name, status)
VALUES (1001, '用户管理员', '1'),
       (1002, '角色管理员', '1');

INSERT INTO sys_user_role (id, user_id, role_id)
VALUES (10001, 101, 1001),
       (10002, 102, 1002);

INSERT INTO sys_role_permission (id, role_id, permission_id)
VALUES (100000, 1001,  1),
       (100001, 1001, 11),
       (100002, 1001, 12),
       (100003, 1001, 13),
       (100004, 1001, 14),
       (100005, 1001, 15),
       (100006, 1001, 21),
       (100007, 1001, 22),
       (100008, 1001, 23),
       (100009, 1001, 24),
       (100010, 1001, 25),
       (100011, 1001, 31),
       (100012, 1001, 32),
       (100013, 1001, 33),
       (100014, 1001, 34),
       (100015, 1001, 35),
       (100200, 1001,  2),
       (100201, 1001, 41),
       (100202, 1001, 42),
       (100203, 1001, 43),
       (100204, 1001, 44);

CREATE TABLE PROCESS_LEAVE (
  id         BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
  days       INT(11)     NOT NULL COMMENT '请假天数',
  content    VARCHAR(32) NOT NULL COMMENT '事由',
  leave_time TIMESTAMP   NOT NULL COMMENT '请假时间',
  remark     VARCHAR(32) DEFAULT NULL COMMENT '备注',
  applicant  VARCHAR(32) DEFAULT NULL COMMENT '申请人',
  status     INT(11)     NOT NULL COMMENT '状态(0初始录入 1正在申请 2正在审批 3审批完成)',
  PRIMARY KEY SYS_USER_PK (id)
) ENGINE=InnoDB DEFAULT CHARSET = utf8;