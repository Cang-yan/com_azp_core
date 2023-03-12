DROP TABLE IF EXISTS `sys_award_special_time`;
CREATE TABLE `sys_award_special_time` (
  `id` varchar(32) NOT NULL  COMMENT '特殊时节奖ID',
  `year` varchar(32) NOT NULL  COMMENT '年度',
  `month` varchar(32) NOT NULL  COMMENT '月别',
  `department` varchar(32) COMMENT '部门',
  `group` varchar(32) COMMENT '组别',
  `user_code` varchar(32) COMMENT '工号',
  `user_name` varchar(32) COMMENT '姓名',
  `type` varchar(32) COMMENT '类型',
  `award_name` varchar(32) COMMENT '奖项名称',
  `award_reason` varchar(32) COMMENT '获奖原因',
  `point` Integer(5) COMMENT '积分',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
