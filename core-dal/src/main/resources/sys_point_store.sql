DROP TABLE IF EXISTS `sys_point_store`;
CREATE TABLE `sys_point_store` (
  `id` varchar(32) NOT NULL  COMMENT '积分商城ID',
  `product_type` Integer(5) COMMENT '商品类别',
  `product_name` varchar(128) COMMENT '商品名称',
  `product_serial` varchar(32) COMMENT '商品编号',
  `point_number` Integer(5) COMMENT '所需积分数',
  `image` varchar(128) COMMENT '图片',
  `description` varchar(150) COMMENT '兑换说明',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
