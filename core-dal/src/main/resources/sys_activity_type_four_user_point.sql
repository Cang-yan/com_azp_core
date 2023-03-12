DROP TABLE IF EXISTS `sys_activity_type_four_user_point`;
CREATE TABLE `sys_activity_type_four_user_point` (
  `id` varchar(32) NOT NULL  COMMENT '类型4活动-用户-积分ID',
  `point_type` Integer(5) COMMENT '积分类型',
  `point` Integer(5) COMMENT '积分',
  `activity_type_four_user_id` varchar(32) COMMENT '类型4-人管理ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
