DROP TABLE IF EXISTS `sys_activity_type_four_periods`;
CREATE TABLE `sys_activity_type_four_periods` (
  `id` varchar(32) NOT NULL  COMMENT '活动4-期数ID',
  `periods_number` Integer(5) COMMENT '期数',
  `begin_date` timestamp(0) NULL  COMMENT '开始时间',
  `end_date` timestamp(0) NULL  COMMENT '结束时间',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
