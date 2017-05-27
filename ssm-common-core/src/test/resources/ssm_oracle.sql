CREATE TABLE SYS_USER (
  id       NUMBER(20),
  code     VARCHAR2(32),
  name     VARCHAR2(32),
  pass     VARCHAR2(32),
  salt     VARCHAR2(32),
  status   CHAR(1)
);
-- Add constraint
ALTER TABLE SYS_USER MODIFY (id NOT NULL);
ALTER TABLE SYS_USER MODIFY (code NOT NULL);
ALTER TABLE SYS_USER MODIFY (name NOT NULL);
ALTER TABLE SYS_USER MODIFY (pass NOT NULL);
ALTER TABLE SYS_USER MODIFY (status DEFAULT '1' );
ALTER TABLE SYS_USER ADD CONSTRAINT SYS_USER_PK PRIMARY KEY (ID);
ALTER TABLE SYS_USER ADD CONSTRAINT SYS_USER_CHK CHECK (status IN ('0', '1'));
-- Add comments to the columns
COMMENT ON COLUMN SYS_USER.id IS '主键';
COMMENT ON COLUMN SYS_USER.code IS '用户帐号';
COMMENT ON COLUMN SYS_USER.name IS '用户名';
COMMENT ON COLUMN SYS_USER.pass IS '密码';
COMMENT ON COLUMN SYS_USER.salt IS '盐(密码加密时使用以防止穷举暴力破解,一般由随机数生成)';
COMMENT ON COLUMN SYS_USER.status IS '状态(1 未锁定 0 锁定)';

CREATE TABLE SYS_ROLE (
  id     NUMBER(20),
  name   VARCHAR2(32),
  status CHAR(1),
  remark VARCHAR2(200)
);
-- Add constraint
ALTER TABLE SYS_ROLE MODIFY (id NOT NULL);
ALTER TABLE SYS_ROLE MODIFY (name NOT NULL);
ALTER TABLE SYS_ROLE MODIFY (status DEFAULT '1');
ALTER TABLE SYS_ROLE ADD CONSTRAINT SYS_ROLE_PK PRIMARY KEY (id);
ALTER TABLE SYS_ROLE ADD CONSTRAINT SYS_ROLE_CHK CHECK (status IN ('0', '1'));
-- Add comments to the columns
COMMENT ON COLUMN SYS_ROLE.ID IS '主键';
COMMENT ON COLUMN SYS_ROLE.name IS '角色名称';
COMMENT ON COLUMN SYS_ROLE.status IS '是否可用(1 可用 0 不可用)';
COMMENT ON COLUMN SYS_ROLE.remark IS '备注';

CREATE TABLE SYS_PERMISSION (
  id         NUMBER(20),
  code       VARCHAR2(32),
  name       VARCHAR2(32),
  type       CHAR(4),
  icon       VARCHAR(32),
  url        VARCHAR2(128),
  parent_id  NUMBER(20),
  parent_ids VARCHAR2(32),
  status     CHAR(1),
  seq        NUMBER(11)
);
-- Add constraint
ALTER TABLE SYS_PERMISSION MODIFY (id NOT NULL);
ALTER TABLE SYS_PERMISSION MODIFY (name NOT NULL);
ALTER TABLE SYS_PERMISSION MODIFY (status DEFAULT '1');
ALTER TABLE SYS_PERMISSION ADD CONSTRAINT SYS_PERMISSION_PK PRIMARY KEY (id);
ALTER TABLE SYS_PERMISSION ADD CONSTRAINT SYS_PERMISSION_CHK CHECK (status IN ('0', '1'));
-- Add comments to the columns
COMMENT ON COLUMN SYS_PERMISSION.id IS '主键';
COMMENT ON COLUMN SYS_PERMISSION.code IS '权限代码字符串';
COMMENT ON COLUMN SYS_PERMISSION.name IS '资源名称';
COMMENT ON COLUMN SYS_PERMISSION.type IS '资源类型(菜单|功能)';
COMMENT ON COLUMN SYS_PERMISSION.icon IS '资源图标';
COMMENT ON COLUMN SYS_PERMISSION.url IS '资源路径';
COMMENT ON COLUMN SYS_PERMISSION.parent_id IS '父结点ID';
COMMENT ON COLUMN SYS_PERMISSION.parent_ids IS '父结点ID列表串';
COMMENT ON COLUMN SYS_PERMISSION.status IS '是否可用(1 可用 0 不可用)';
COMMENT ON COLUMN SYS_PERMISSION.seq IS '排序号';

CREATE TABLE SYS_USER_ROLE (
  id      NUMBER(20),
  user_id NUMBER(20),
  role_id NUMBER(20)
);
-- Add constraint
ALTER TABLE SYS_USER_ROLE MODIFY (id NOT NULL);
ALTER TABLE SYS_USER_ROLE MODIFY (user_id NOT NULL);
ALTER TABLE SYS_USER_ROLE MODIFY (role_id NOT NULL);
ALTER TABLE SYS_USER_ROLE ADD CONSTRAINT SYS_USER_ROLE_PK PRIMARY KEY (id);
ALTER TABLE SYS_USER_ROLE ADD CONSTRAINT SYS_USER_ROLE_UK UNIQUE KEY (user_id, role_id);
ALTER TABLE SYS_USER_ROLE ADD CONSTRAINT SYS_USER_ROLE_FK1 FOREIGN KEY (user_id) REFERENCES SYS_USER (id);
ALTER TABLE SYS_USER_ROLE ADD CONSTRAINT SYS_USER_ROLE_FK2 FOREIGN KEY (role_id) REFERENCES SYS_ROLE (id);
-- Add comments to the columns
COMMENT ON COLUMN SYS_USER_ROLE.id IS '主键';
COMMENT ON COLUMN SYS_USER_ROLE.user_id IS '用户ID';
COMMENT ON COLUMN SYS_USER_ROLE.role_id IS '角色ID';

CREATE TABLE SYS_ROLE_PERMISSION (
  id            NUMBER(20),
  role_id       NUMBER(20),
  permission_id NUMBER(20)
);
-- Add constraint
ALTER TABLE SYS_ROLE_PERMISSION MODIFY (id NOT NULL);
ALTER TABLE SYS_ROLE_PERMISSION MODIFY (role_id NOT NULL);
ALTER TABLE SYS_ROLE_PERMISSION MODIFY (sys_permission_id NOT NULL);
ALTER TABLE SYS_ROLE_PERMISSION ADD CONSTRAINT SYS_ROLE_PERMISSION_PK PRIMARY KEY (id);
ALTER TABLE SYS_ROLE_PERMISSION ADD CONSTRAINT SYS_ROLE_PERMISSION_UK UNIQUE KEY (role_id, permission_id);
ALTER TABLE SYS_ROLE_PERMISSION ADD CONSTRAINT SYS_ROLE_PERMISSION_FK1 FOREIGN KEY (role_id) REFERENCES SYS_ROLE (id);
ALTER TABLE SYS_ROLE_PERMISSION ADD CONSTRAINT SYS_ROLE_PERMISSION_FK2 FOREIGN KEY (permission_id) REFERENCES SYS_PERMISSION (id);
-- Add comments to the columns
COMMENT ON COLUMN SYS_ROLE_PERMISSION.role_id IS '角色ID';
COMMENT ON COLUMN SYS_ROLE_PERMISSION.permission_id IS '权限ID';

INSERT INTO SYS_PERMISSION (id, name, type, url, code, parent_id, parent_ids, seq, status)
VALUES (1, '系统管理', 'MENU', NULL, NULL, NULL, NULL, 1, '1');
INSERT INTO SYS_PERMISSION (id, name, type, url, code, parent_id, parent_ids, seq, status)
VALUES (11, '用户管理', 'MENU', 'user/list', 'user:query', 1, '1/', 1011, '1');
INSERT INTO SYS_PERMISSION (id, name, type, url, code, parent_id, parent_ids, seq, status)
VALUES (12, '用户新增', 'PERMISSION', 'user/add', 'user:create', 11, '1/11/', 102, '1');
INSERT INTO SYS_PERMISSION (id, name, type, url, code, parent_id, parent_ids, seq, status)
VALUES (13, '用户删除', 'PERMISSION', 'user/delete', 'user:delete', 11, '1/11/', 103, '1');
INSERT INTO SYS_PERMISSION (id, name, type, url, code, parent_id, parent_ids, seq, status)
VALUES (14, '用户修改', 'PERMISSION', 'user/edit', 'user:update', 11, '1/11/', 104, '1');
INSERT INTO SYS_PERMISSION (id, name, type, url, code, parent_id, parent_ids, seq, status)
VALUES (15, '用户查询', 'PERMISSION', 'user/query', 'user:query', 11, '1/11/', 105, '1');
INSERT INTO SYS_PERMISSION (id, name, type, url, code, parent_id, parent_ids, seq, status)
VALUES (21, '角色管理', 'MENU', 'role/list', 'role:query', 1, '1/', 201, '1');
INSERT INTO SYS_PERMISSION (id, name, type, url, code, parent_id, parent_ids, seq, status)
VALUES (22, '角色新增', 'PERMISSION', 'role/add', 'role:create', 21, '1/21/', 202, '1');
INSERT INTO SYS_PERMISSION (id, name, type, url, code, parent_id, parent_ids, seq, status)
VALUES (23, '角色删除', 'PERMISSION', 'role/delete', 'role:delete', 21, '1/21/', 203, '1');
INSERT INTO SYS_PERMISSION (id, name, type, url, code, parent_id, parent_ids, seq, status)
VALUES (24, '角色修改', 'PERMISSION', 'role/edit', 'role:update', 21, '1/21/', 204, '1');
INSERT INTO SYS_PERMISSION (id, name, type, url, code, parent_id, parent_ids, seq, status)
VALUES (25, '角色查询', 'PERMISSION', 'role/query', 'role:query', 21, '1/21/', 205, '1');

INSERT INTO sys_user (id, code, name, pass, salt, status)
VALUES (101, 'admin', 'Admin', '3986874b369863a88de3f82728004977', '10086', '1');
INSERT INTO sys_user (id, code, name, pass, salt, status)
VALUES (102, 'gavin', 'Gavin', '3986874b369863a88de3f82728004977', '10086', '1');

INSERT INTO sys_role (id, name, status) VALUES (1001, '用户管理员', '1');
INSERT INTO sys_role (id, name, status) VALUES (1002, '角色管理员', '1');

INSERT INTO sys_user_role (id, user_id, role_id) VALUES (1001, 101, 1001);
INSERT INTO sys_user_role (id, user_id, role_id) VALUES (1002, 102, 1002);

INSERT INTO sys_role_permission (id, role_id, permission_id) VALUES (100000, 101,  1);
INSERT INTO sys_role_permission (id, role_id, permission_id) VALUES (100001, 101, 11);
INSERT INTO sys_role_permission (id, role_id, permission_id) VALUES (100002, 101, 12);
INSERT INTO sys_role_permission (id, role_id, permission_id) VALUES (100003, 101, 13);
INSERT INTO sys_role_permission (id, role_id, permission_id) VALUES (100004, 101, 14);
INSERT INTO sys_role_permission (id, role_id, permission_id) VALUES (100005, 101, 15);
INSERT INTO sys_role_permission (id, role_id, permission_id) VALUES (100006, 101, 21);
INSERT INTO sys_role_permission (id, role_id, permission_id) VALUES (100007, 101, 22);
INSERT INTO sys_role_permission (id, role_id, permission_id) VALUES (100008, 101, 23);
INSERT INTO sys_role_permission (id, role_id, permission_id) VALUES (100009, 101, 24);
INSERT INTO sys_role_permission (id, role_id, permission_id) VALUES (100010, 101, 25);
