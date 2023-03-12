DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL  COMMENT '用户ID',
  `user_code` varchar(32) NOT NULL  COMMENT '员工编号',
  `name` varchar(32) NOT NULL  COMMENT '姓名',
  `account` varchar(32) COMMENT '账号',
  `password` varchar(32) COMMENT '密码',
  `department_id` varchar(32) NOT NULL  COMMENT '部门ID',
  `work_date` varchar(32) NOT NULL  COMMENT '入职时间',
  `group_id` varchar(32) NOT NULL  COMMENT '组别ID',
  `user_info_id` varchar(32) COMMENT '用户信息ID',
  `status` Integer(5) NOT NULL  DEFAULT 0 COMMENT '状态',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
