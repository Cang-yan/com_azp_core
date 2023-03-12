DROP TABLE IF EXISTS `sys_notification_user`;
CREATE TABLE `sys_notification_user` (
  `id` varchar(32) NOT NULL  COMMENT '通知用户状态ID',
  `notification_id` varchar(32) COMMENT '通知ID',
  `type` Integer(5) COMMENT '发送类型',
  `user_id` varchar(32) COMMENT '用户ID',
  `status` Integer(5) DEFAULT 0 COMMENT '状态',
  `sender_id` varchar(32) COMMENT '发送人ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
