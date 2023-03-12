DROP TABLE IF EXISTS `sys_level`;
CREATE TABLE `sys_level` (
  `id` varchar(32) NOT NULL  COMMENT '等级ID',
  `name` varchar(32) COMMENT '等级名称',
  `min_point` Integer(5) NOT NULL  COMMENT '最小积分数',
  `max_point` Integer(5) NOT NULL  COMMENT '最大积分数',
  `tag` varchar(128) COMMENT '标识',
  `idx` Integer(5) NOT NULL  COMMENT '索引',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
