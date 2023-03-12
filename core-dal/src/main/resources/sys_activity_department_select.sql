DROP TABLE IF EXISTS `sys_activity_department_select`;
CREATE TABLE `sys_activity_department_select` (
  `id` varchar(32) NOT NULL  COMMENT '活动部门选择ID',
  `relation_id` varchar(32) NOT NULL  COMMENT '活动关联ID',
  `department_id` varchar(32) NOT NULL  COMMENT '部门ID',
  `type` Integer(5) COMMENT '类型',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
