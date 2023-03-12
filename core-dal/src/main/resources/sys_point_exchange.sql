DROP TABLE IF EXISTS `sys_point_exchange`;
CREATE TABLE `sys_point_exchange` (
  `id` varchar(32) NOT NULL  COMMENT '积分兑换ID',
  `product_id` varchar(32) NOT NULL  COMMENT '商品ID',
  `point_num` Integer(5) NOT NULL  DEFAULT 0 COMMENT '积分数量',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `exchange_num` Integer(5) NOT NULL  COMMENT '兑换数量',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
