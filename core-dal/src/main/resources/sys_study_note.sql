DROP TABLE IF EXISTS `sys_study_note`;
CREATE TABLE `sys_study_note` (
  `id` varchar(32) NOT NULL  COMMENT '学习心得ID',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `content` varchar(1024) COMMENT '内容',
  `activity_type_two_user_id` varchar(32) NOT NULL  COMMENT '类型2活动用户ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
