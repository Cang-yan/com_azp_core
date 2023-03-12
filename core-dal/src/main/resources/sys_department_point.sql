DROP TABLE IF EXISTS `sys_department_point`;
CREATE TABLE `sys_department_point` (
  `id` varchar(32) NOT NULL  COMMENT '部门积分ID',
  `point_legacy` Integer(5) COMMENT '积分余额',
  `department_id` varchar(32) NOT NULL  COMMENT '部门ID',
  `point_limit` Integer(5) NOT NULL  DEFAULT 2000 COMMENT '积分限额',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
