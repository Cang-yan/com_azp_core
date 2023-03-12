DROP TABLE IF EXISTS `sys_notification`;
CREATE TABLE `sys_notification` (
  `id` varchar(32) NOT NULL  COMMENT '通知ID',
  `content` varchar(128) COMMENT '内容',
  `title` varchar(32) COMMENT '标题',
  `type` Integer(5) NOT NULL  COMMENT '类型',
  `template_id` varchar(32) COMMENT '模板ID',
  `relation_id` varchar(32) COMMENT '关联ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
