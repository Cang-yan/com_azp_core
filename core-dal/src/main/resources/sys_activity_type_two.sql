DROP TABLE IF EXISTS `sys_activity_type_two`;
CREATE TABLE `sys_activity_type_two` (
  `id` varchar(32) NOT NULL  COMMENT '类型2活动ID',
  `name` varchar(64) NOT NULL  COMMENT '名称',
  `begin_date` timestamp(0) NULL  COMMENT '开始日期',
  `end_date` timestamp(0) NULL  COMMENT '结束日期',
  `image` varchar(128) COMMENT '展示图',
  `description` varchar(1024) COMMENT '详情描述',
  `point` Integer(5) COMMENT '积分',
  `activity_sub_category_id` varchar(32) NOT NULL  COMMENT '活动小类ID',
  `status` Integer(5) DEFAULT 1 COMMENT '状态',
  `participants_number` Integer(5) NOT NULL  DEFAULT 0 COMMENT '参与人数',
  `limit_number` Integer(5) COMMENT '限定人数',
  `department_id` varchar(32) COMMENT '部门',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
