DROP TABLE IF EXISTS `sys_activity_type_six`;
CREATE TABLE `sys_activity_type_six` (
  `id` varchar(32) NOT NULL  COMMENT '类型6活动ID',
  `point` Integer(5) NOT NULL  COMMENT '积分',
  `date` timestamp(0) NOT NULL  COMMENT '日期',
  `activity_sub_category_id` varchar(32) NOT NULL  COMMENT '活动小类ID',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
