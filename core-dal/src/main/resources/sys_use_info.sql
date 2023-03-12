DROP TABLE IF EXISTS `sys_use_info`;
CREATE TABLE `sys_use_info` (
  `id` varchar(32) NOT NULL  COMMENT '用户信息ID',
  `point_number` Integer(5) NOT NULL  DEFAULT 0 COMMENT '积分数量',
  `head` varchar(256) COMMENT '头像',
  `level_id` varchar(32) NOT NULL  DEFAULT 1 COMMENT '积分等级',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
