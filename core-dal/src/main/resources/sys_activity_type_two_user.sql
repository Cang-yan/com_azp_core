DROP TABLE IF EXISTS `sys_activity_type_two_user`;
CREATE TABLE `sys_activity_type_two_user` (
  `id` varchar(32) NOT NULL  COMMENT '类型2活动-用户ID',
  `status` Integer(5) DEFAULT 0 COMMENT '状态',
  `end_date` timestamp(0) NULL  COMMENT '完成时间',
  `point` Integer(5) COMMENT '积分',
  `sign_date` timestamp(0) NULL  COMMENT '报名时间',
  `review_date` timestamp(0) NULL  COMMENT '审核时间',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `activity_type_two_id` varchar(32) COMMENT '类型2活动ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
