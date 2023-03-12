DROP TABLE IF EXISTS `sys_activity_type_four`;
CREATE TABLE `sys_activity_type_four` (
  `id` varchar(32) NOT NULL  COMMENT '类型4活动ID',
  `name` varchar(64) NOT NULL  COMMENT '名称',
  `group_date` timestamp(0) NULL  COMMENT '组队日期',
  `activity_sub_category_id` varchar(32) COMMENT '活动小类ID',
  `status` Integer(5) DEFAULT 1 COMMENT '状态',
  `point` Integer(5) COMMENT '积分奖励',
  `rank` Integer(5) COMMENT '排名',
  `group_point` Integer(5) COMMENT '队伍积分',
  `periods_number` Integer(5) COMMENT '期数',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
