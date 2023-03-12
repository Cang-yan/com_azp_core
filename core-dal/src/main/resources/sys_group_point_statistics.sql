DROP TABLE IF EXISTS `sys_group_point_statistics`;
CREATE TABLE `sys_group_point_statistics` (
  `id` varchar(32) NOT NULL  COMMENT '组队积分统计ID',
  `point_num` Integer(5) COMMENT '积分数量',
  `group_id` varchar(32) NOT NULL  COMMENT '队伍ID',
  `date` timestamp(0) NOT NULL  COMMENT '日期',
  `count` Integer(5) NOT NULL  DEFAULT 0 COMMENT '参与活动次数',
  `status` Integer(5) NOT NULL  DEFAULT 0 COMMENT '状态',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
