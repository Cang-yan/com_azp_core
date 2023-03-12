DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `id` varchar(32) NOT NULL  COMMENT '组别ID',
  `group_name` varchar(32) NOT NULL  COMMENT '组别名称',
  `code` varchar(32) NOT NULL  COMMENT '组别编号',
  `department_id` varchar(32) NOT NULL  COMMENT '部门ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
