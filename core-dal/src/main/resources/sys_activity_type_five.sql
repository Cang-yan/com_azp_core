DROP TABLE IF EXISTS `sys_activity_type_five`;
CREATE TABLE `sys_activity_type_five` (
  `id` varchar(32) NOT NULL  COMMENT '类型5活动ID',
  `user_id` varchar(32) COMMENT '用户ID',
  `score` Integer(5) COMMENT '得分',
  `activity_sub_category` varchar(32) COMMENT '活动小类ID',
  `point` Integer(5) COMMENT '积分',
  `duration` varchar(32) COMMENT '时间',
  `type` Integer(5) COMMENT '类型',
  `year` varchar(32) COMMENT '年度',
  `month` varchar(32) COMMENT '月度',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
