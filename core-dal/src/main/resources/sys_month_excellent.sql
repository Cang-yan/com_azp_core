DROP TABLE IF EXISTS `sys_month_excellent`;
CREATE TABLE `sys_month_excellent` (
  `id` varchar(32) NOT NULL  COMMENT '月度优秀奖ID',
  `year` varchar(32) NOT NULL  COMMENT '年度',
  `month` varchar(32) NOT NULL  COMMENT '月别',
  `department` varchar(32) NOT NULL  COMMENT '部门',
  `group` varchar(32) NOT NULL  COMMENT '组别',
  `user_code` varchar(32) NOT NULL  COMMENT '工号',
  `name` varchar(32) NOT NULL  COMMENT '姓名',
  `type` varchar(32) NOT NULL  COMMENT '类型',
  `score` decimal(10,4) NOT NULL  COMMENT '得分',
  `point` Integer(5) COMMENT '积分',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
