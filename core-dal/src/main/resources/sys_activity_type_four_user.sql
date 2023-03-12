DROP TABLE IF EXISTS `sys_activity_type_four_user`;
CREATE TABLE `sys_activity_type_four_user` (
  `id` varchar(32) NOT NULL  COMMENT '类型4活动-人ID',
  `activity_type_four_id` varchar(32) COMMENT '类型4活动ID',
  `user_id` varchar(32) NOT NULL  COMMENT '用户（队员）',
  `status` Integer(5) NOT NULL  DEFAULT 0 COMMENT '状态',
  `place` Integer(5) NOT NULL  DEFAULT 0 COMMENT '位次',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
