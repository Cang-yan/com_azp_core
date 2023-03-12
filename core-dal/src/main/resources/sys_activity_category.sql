DROP TABLE IF EXISTS `sys_activity_category`;
CREATE TABLE `sys_activity_category` (
  `id` varchar(32) NOT NULL  COMMENT '活动大类ID',
  `name` varchar(64) NOT NULL  COMMENT '大类名称',
  `diy_text` varchar(150) COMMENT '自定义文本',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
