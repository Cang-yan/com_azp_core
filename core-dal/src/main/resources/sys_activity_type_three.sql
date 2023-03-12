DROP TABLE IF EXISTS `sys_activity_type_three`;
CREATE TABLE `sys_activity_type_three` (
  `id` varchar(32) NOT NULL  COMMENT '类型3活动ID',
  `name` varchar(64) COMMENT '名称',
  `begin_date` timestamp(0) NULL  COMMENT '开始时间',
  `end_date` timestamp(0) NULL  COMMENT '结束日期',
  `serial` varchar(20) COMMENT '编号',
  `brand` varchar(20) COMMENT '品牌',
  `description` varchar(500) COMMENT '详情描述',
  `point` Integer(5) COMMENT '积分',
  `activity_sub_category_id` varchar(32) NOT NULL  COMMENT '活动小类ID',
  `status` Integer(5) DEFAULT 0 COMMENT '状态',
  `is_outstanding` Integer(1) COMMENT '是否优秀',
  `create_user_id` varchar(32) NOT NULL  COMMENT '活动创建用户',
  `review_idea` varchar(128) COMMENT '审核意见',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
