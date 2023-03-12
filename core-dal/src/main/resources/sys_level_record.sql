DROP TABLE IF EXISTS `sys_level_record`;
CREATE TABLE `sys_level_record` (
  `id` varchar(32) NOT NULL  COMMENT '等级排行ID',
  `level_id` varchar(32) COMMENT '等级类型ID',
  `person_number` Integer(5) COMMENT '人数',
  `role_type` varchar(32) NOT NULL  COMMENT '作用范围',
  `department_id` varchar(32) NOT NULL  DEFAULT '0' COMMENT '部门ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
