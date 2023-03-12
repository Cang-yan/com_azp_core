DROP TABLE IF EXISTS `sys_activity_sub_category`;
CREATE TABLE `sys_activity_sub_category` (
  `id` varchar(32) NOT NULL  COMMENT '活动小类ID',
  `activity_category_id` varchar(32) COMMENT '大类ID',
  `name` varchar(64) NOT NULL  COMMENT '名称',
  `rule` varchar(500) COMMENT '规则说明',
  `point` Integer(5) NOT NULL  DEFAULT 0 COMMENT '获得积分数（只有类型3需要）',
  `on_top` Integer(1) NOT NULL  DEFAULT 0 COMMENT '是否置顶',
  `on_top_date` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '置顶时间',
  `status` Integer(5) NOT NULL  DEFAULT 0 COMMENT '状态',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
