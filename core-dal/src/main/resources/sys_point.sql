DROP TABLE IF EXISTS `sys_point`;
CREATE TABLE `sys_point` (
  `id` varchar(32) NOT NULL  COMMENT '积分ID',
  `type` Integer(5) NOT NULL  COMMENT '积分类型',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `point_number` Integer(5) NOT NULL  DEFAULT 0 COMMENT '积分数量',
  `relation_id` varchar(32) COMMENT '积分来源ID',
  `get_time` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '积分获取时间',
  `title` varchar(64) COMMENT '积分标题',
  `template_id` varchar(32) COMMENT '模板ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
