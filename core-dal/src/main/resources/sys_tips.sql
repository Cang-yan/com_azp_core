DROP TABLE IF EXISTS `sys_tips`;
CREATE TABLE `sys_tips` (
  `id` varchar(32) NOT NULL  COMMENT '小贴士ID',
  `content` varchar(1024) NOT NULL  COMMENT '文本',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
