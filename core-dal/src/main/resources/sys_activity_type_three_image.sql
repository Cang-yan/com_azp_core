DROP TABLE IF EXISTS `sys_activity_type_three_image`;
CREATE TABLE `sys_activity_type_three_image` (
  `id` varchar(32) NOT NULL  COMMENT '活动类型3图片ID',
  `activity_type_three_id` varchar(32) NOT NULL  COMMENT '类型3活动ID',
  `url` varchar(128) NOT NULL  COMMENT '图片url',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
