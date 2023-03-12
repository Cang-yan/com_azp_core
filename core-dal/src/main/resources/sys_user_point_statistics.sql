DROP TABLE IF EXISTS `sys_user_point_statistics`;
CREATE TABLE `sys_user_point_statistics` (
  `id` varchar(32) NOT NULL  COMMENT '员工积分统计ID',
  `number` Integer(5) NOT NULL  DEFAULT 0 COMMENT '积分数量',
  `date` timestamp(0) NOT NULL  COMMENT '日期',
  `department_name` varchar(32) NOT NULL  COMMENT '部门名称',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `count` Integer(5) NOT NULL  DEFAULT 0 COMMENT '参与活动次数',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
