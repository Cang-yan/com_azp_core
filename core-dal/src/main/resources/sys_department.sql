DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` varchar(32) NOT NULL  COMMENT '部门ID',
  `department_point_id` varchar(32) COMMENT '部门积分管理ID',
  `name` varchar(32) NOT NULL  COMMENT '名称',
  `code` varchar(32) NOT NULL  COMMENT '部门编号',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
