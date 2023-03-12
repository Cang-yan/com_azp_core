DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL  COMMENT '用户ID',
  `user_code` varchar(32) NOT NULL  COMMENT '员工编号',
  `name` varchar(32) NOT NULL  COMMENT '姓名',
  `account` varchar(32) COMMENT '账号',
  `password` varchar(32) COMMENT '密码',
  `department_id` varchar(32) NOT NULL  COMMENT '部门ID',
  `work_date` varchar(32) NOT NULL  COMMENT '入职时间',
  `group_id` varchar(32) NOT NULL  COMMENT '组别ID',
  `user_info_id` varchar(32) COMMENT '用户信息ID',
  `status` Integer(5) NOT NULL  DEFAULT 0 COMMENT '状态',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` varchar(32) NOT NULL  COMMENT '部门ID',
  `department_point_id` varchar(32) COMMENT '部门积分管理ID',
  `name` varchar(32) NOT NULL  COMMENT '名称',
  `code` varchar(32) NOT NULL  COMMENT '部门编号',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_activity_category`;
CREATE TABLE `sys_activity_category` (
  `id` varchar(32) NOT NULL  COMMENT '活动大类ID',
  `name` varchar(64) NOT NULL  COMMENT '大类名称',
  `diy_text` varchar(150) COMMENT '自定义文本',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
DROP TABLE IF EXISTS `sys_activity_type_one`;
CREATE TABLE `sys_activity_type_one` (
  `id` varchar(32) NOT NULL  COMMENT '类型1活动ID',
  `name` varchar(64) NOT NULL  COMMENT '名称',
  `begin_date` timestamp(0) NOT NULL  COMMENT '开始日期',
  `end_date` timestamp(0) NULL  COMMENT '结束日期',
  `image` varchar(128) COMMENT '展示图',
  `description` varchar(1024) COMMENT '详情描述',
  `activity_sub_category_id` varchar(32) NOT NULL  COMMENT '活动小类ID',
  `status` Integer(5) DEFAULT 1 COMMENT '状态',
  `participants_number` Integer(5) COMMENT '参加人数',
  `get_point` Integer(5) NOT NULL  DEFAULT 0 COMMENT '获得积分',
  `de_point` Integer(5) NOT NULL  DEFAULT 0 COMMENT '扣减积分',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_activity_type_one_user`;
CREATE TABLE `sys_activity_type_one_user` (
  `id` varchar(32) NOT NULL  COMMENT '类型1活动-用户ID',
  `status` Integer(5) DEFAULT 0 COMMENT '状态',
  `begin_date` timestamp(0) NULL  COMMENT '开始时间',
  `end_date` timestamp(0) NULL  COMMENT '完成时间',
  `user_id` varchar(32) COMMENT '用户ID',
  `activity_type_one_id` varchar(32) COMMENT '类型1活动ID',
  `point` Integer(5) NOT NULL  DEFAULT 0 COMMENT '积分变更数',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
DROP TABLE IF EXISTS `sys_activity_type_two_user`;
CREATE TABLE `sys_activity_type_two_user` (
  `id` varchar(32) NOT NULL  COMMENT '类型2活动-用户ID',
  `status` Integer(5) DEFAULT 0 COMMENT '状态',
  `end_date` timestamp(0) NULL  COMMENT '完成时间',
  `point` Integer(5) COMMENT '积分',
  `sign_date` timestamp(0) NULL  COMMENT '报名时间',
  `review_date` timestamp(0) NULL  COMMENT '审核时间',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `activity_type_two_id` varchar(32) COMMENT '类型2活动ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
DROP TABLE IF EXISTS `sys_activity_type_four`;
CREATE TABLE `sys_activity_type_four` (
  `id` varchar(32) NOT NULL  COMMENT '类型4活动ID',
  `name` varchar(64) NOT NULL  COMMENT '名称',
  `group_date` timestamp(0) NULL  COMMENT '组队日期',
  `activity_sub_category_id` varchar(32) COMMENT '活动小类ID',
  `status` Integer(5) DEFAULT 1 COMMENT '状态',
  `point` Integer(5) COMMENT '积分奖励',
  `rank` Integer(5) COMMENT '排名',
  `group_point` Integer(5) COMMENT '队伍积分',
  `periods_number` Integer(5) COMMENT '期数',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_activity_type_four_user`;
CREATE TABLE `sys_activity_type_four_user` (
  `id` varchar(32) NOT NULL  COMMENT '类型4活动-人ID',
  `activity_type_four_id` varchar(32) COMMENT '类型4活动ID',
  `user_id` varchar(32) NOT NULL  COMMENT '用户（队员）',
  `status` Integer(5) NOT NULL  DEFAULT 0 COMMENT '状态',
  `place` Integer(5) NOT NULL  DEFAULT 0 COMMENT '位次',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_activity_type_four_user_point`;
CREATE TABLE `sys_activity_type_four_user_point` (
  `id` varchar(32) NOT NULL  COMMENT '类型4活动-用户-积分ID',
  `point_type` Integer(5) COMMENT '积分类型',
  `point` Integer(5) COMMENT '积分',
  `activity_type_four_user_id` varchar(32) COMMENT '类型4-人管理ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_activity_type_five`;
CREATE TABLE `sys_activity_type_five` (
  `id` varchar(32) NOT NULL  COMMENT '类型5活动ID',
  `user_id` varchar(32) COMMENT '用户ID',
  `score` Integer(5) COMMENT '得分',
  `activity_sub_category` varchar(32) COMMENT '活动小类ID',
  `point` Integer(5) COMMENT '积分',
  `duration` varchar(32) COMMENT '时间',
  `type` Integer(5) COMMENT '类型',
  `year` varchar(32) COMMENT '年度',
  `month` varchar(32) COMMENT '月度',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
DROP TABLE IF EXISTS `sys_study_note`;
CREATE TABLE `sys_study_note` (
  `id` varchar(32) NOT NULL  COMMENT '学习心得ID',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `content` varchar(1024) COMMENT '内容',
  `activity_type_two_user_id` varchar(32) NOT NULL  COMMENT '类型2活动用户ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_point`;
CREATE TABLE `sys_point` (
  `id` varchar(32) NOT NULL  COMMENT '积分ID',
  `type` Integer(5) NOT NULL  COMMENT '积分类型',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `point_number` Integer(5) NOT NULL  DEFAULT 0 COMMENT '积分数量',
  `relation_id` varchar(32) COMMENT '积分来源ID',
  `get_time` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '积分获取时间',
  `title` varchar(64) COMMENT '积分标题',
  `template_id` varchar(32) COMMENT '模板ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_level_record`;
CREATE TABLE `sys_level_record` (
  `id` varchar(32) NOT NULL  COMMENT '等级排行ID',
  `level_id` varchar(32) COMMENT '等级类型ID',
  `person_number` Integer(5) COMMENT '人数',
  `role_type` varchar(32) NOT NULL  COMMENT '作用范围',
  `department_id` varchar(32) NOT NULL  DEFAULT '0' COMMENT '部门ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_level`;
CREATE TABLE `sys_level` (
  `id` varchar(32) NOT NULL  COMMENT '等级ID',
  `name` varchar(32) COMMENT '等级名称',
  `min_point` Integer(5) NOT NULL  COMMENT '最小积分数',
  `max_point` Integer(5) NOT NULL  COMMENT '最大积分数',
  `tag` varchar(128) COMMENT '标识',
  `idx` Integer(5) NOT NULL  COMMENT '索引',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_department_point`;
CREATE TABLE `sys_department_point` (
  `id` varchar(32) NOT NULL  COMMENT '部门积分ID',
  `point_legacy` Integer(5) COMMENT '积分余额',
  `department_id` varchar(32) NOT NULL  COMMENT '部门ID',
  `point_limit` Integer(5) NOT NULL  DEFAULT 2000 COMMENT '积分限额',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
DROP TABLE IF EXISTS `sys_login_record`;
CREATE TABLE `sys_login_record` (
  `id` varchar(32) NOT NULL  COMMENT '登录记录ID',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `time` timestamp(0) NULL  COMMENT '时间',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_activity_type_six`;
CREATE TABLE `sys_activity_type_six` (
  `id` varchar(32) NOT NULL  COMMENT '类型6活动ID',
  `point` Integer(5) NOT NULL  COMMENT '积分',
  `date` timestamp(0) NOT NULL  COMMENT '日期',
  `activity_sub_category_id` varchar(32) NOT NULL  COMMENT '活动小类ID',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_activity_department_select`;
CREATE TABLE `sys_activity_department_select` (
  `id` varchar(32) NOT NULL  COMMENT '活动部门选择ID',
  `relation_id` varchar(32) NOT NULL  COMMENT '活动关联ID',
  `department_id` varchar(32) NOT NULL  COMMENT '部门ID',
  `type` Integer(5) COMMENT '类型',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_activity_type_three_image`;
CREATE TABLE `sys_activity_type_three_image` (
  `id` varchar(32) NOT NULL  COMMENT '活动类型3图片ID',
  `activity_type_three_id` varchar(32) NOT NULL  COMMENT '类型3活动ID',
  `url` varchar(128) NOT NULL  COMMENT '图片url',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `id` varchar(32) NOT NULL  COMMENT '组别ID',
  `group_name` varchar(32) NOT NULL  COMMENT '组别名称',
  `code` varchar(32) NOT NULL  COMMENT '组别编号',
  `department_id` varchar(32) NOT NULL  COMMENT '部门ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_award_good_eye`;
CREATE TABLE `sys_award_good_eye` (
  `id` varchar(32) NOT NULL  COMMENT '火眼金睛ID',
  `year` varchar(32) NOT NULL  COMMENT '年度',
  `quarter` varchar(32) NOT NULL  COMMENT '季度',
  `department` varchar(32) COMMENT '部门',
  `group` varchar(32) COMMENT '组别',
  `user_code` varchar(32) COMMENT '工号',
  `user_name` varchar(32) COMMENT '姓名',
  `award_name` varchar(32) COMMENT '奖项名称',
  `award_reason` varchar(32) COMMENT '获奖原因',
  `point` Integer(5) COMMENT '积分',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_month_excellent`;
CREATE TABLE `sys_month_excellent` (
  `id` varchar(32) NOT NULL  COMMENT '月度优秀奖ID',
  `year` varchar(32) NOT NULL  COMMENT '年度',
  `month` varchar(32) NOT NULL  COMMENT '月别',
  `department` varchar(32) NOT NULL  COMMENT '部门',
  `group` varchar(32) NOT NULL  COMMENT '组别',
  `user_code` varchar(32) NOT NULL  COMMENT '工号',
  `name` varchar(32) NOT NULL  COMMENT '姓名',
  `type` varchar(32) NOT NULL  COMMENT '类型',
  `score` decimal(10,4) NOT NULL  COMMENT '得分',
  `point` Integer(5) COMMENT '积分',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_three_month_excellent`;
CREATE TABLE `sys_three_month_excellent` (
  `id` varchar(32) NOT NULL  COMMENT '季度优秀奖ID',
  `year` varchar(32) NOT NULL  COMMENT '年度',
  `three_month` varchar(32) NOT NULL  COMMENT '季度',
  `department` varchar(32) NOT NULL  COMMENT '部门',
  `group` varchar(32) NOT NULL  COMMENT '组别',
  `user_code` varchar(32) NOT NULL  COMMENT '工号',
  `name` varchar(32) NOT NULL  COMMENT '姓名',
  `type` varchar(32) NOT NULL  COMMENT '类型',
  `score` decimal(10,4) NOT NULL  COMMENT '得分',
  `point` Integer(5) COMMENT '积分',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_year_excellent`;
CREATE TABLE `sys_year_excellent` (
  `id` varchar(32) NOT NULL  COMMENT '年度优秀奖ID',
  `year` varchar(32) NOT NULL  COMMENT '年度',
  `department` varchar(32) NOT NULL  COMMENT '部门',
  `group` varchar(32) NOT NULL  COMMENT '组别',
  `user_code` varchar(32) NOT NULL  COMMENT '工号',
  `name` varchar(32) NOT NULL  COMMENT '姓名',
  `type` varchar(32) NOT NULL  COMMENT '类型',
  `score` decimal(10,4) NOT NULL  COMMENT '得分',
  `point` Integer(5) COMMENT '积分',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_use_info`;
CREATE TABLE `sys_use_info` (
  `id` varchar(32) NOT NULL  COMMENT '用户信息ID',
  `point_number` Integer(5) NOT NULL  DEFAULT 0 COMMENT '积分数量',
  `head` varchar(256) COMMENT '头像',
  `level_id` varchar(32) NOT NULL  DEFAULT 1 COMMENT '积分等级',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_group_point_statistics`;
CREATE TABLE `sys_group_point_statistics` (
  `id` varchar(32) NOT NULL  COMMENT '组队积分统计ID',
  `point_num` Integer(5) COMMENT '积分数量',
  `group_id` varchar(32) NOT NULL  COMMENT '队伍ID',
  `date` timestamp(0) NOT NULL  COMMENT '日期',
  `count` Integer(5) NOT NULL  DEFAULT 0 COMMENT '参与活动次数',
  `status` Integer(5) NOT NULL  DEFAULT 0 COMMENT '状态',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_user_point_statistics`;
CREATE TABLE `sys_user_point_statistics` (
  `id` varchar(32) NOT NULL  COMMENT '员工积分统计ID',
  `number` Integer(5) NOT NULL  DEFAULT 0 COMMENT '积分数量',
  `date` timestamp(0) NOT NULL  COMMENT '日期',
  `department_name` varchar(32) NOT NULL  COMMENT '部门名称',
  `user_id` varchar(32) NOT NULL  COMMENT '用户ID',
  `count` Integer(5) NOT NULL  DEFAULT 0 COMMENT '参与活动次数',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_activity_type_four_periods`;
CREATE TABLE `sys_activity_type_four_periods` (
  `id` varchar(32) NOT NULL  COMMENT '活动4-期数ID',
  `periods_number` Integer(5) COMMENT '期数',
  `begin_date` timestamp(0) NULL  COMMENT '开始时间',
  `end_date` timestamp(0) NULL  COMMENT '结束时间',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_award_skill_excellence`;
CREATE TABLE `sys_award_skill_excellence` (
  `id` varchar(32) NOT NULL  COMMENT '技能突出奖ID',
  `year` varchar(32) NOT NULL  COMMENT '年度',
  `month` varchar(32) NOT NULL  COMMENT '月别',
  `department` varchar(32) COMMENT '部门',
  `group` varchar(32) COMMENT '组别',
  `user_code` varchar(32) COMMENT '工号',
  `user_name` varchar(32) COMMENT '姓名',
  `type` varchar(32) COMMENT '类型',
  `award_name` varchar(32) COMMENT '奖项名称',
  `award_reason` varchar(32) COMMENT '获奖原因',
  `point` Integer(5) COMMENT '积分',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_award_special_time`;
CREATE TABLE `sys_award_special_time` (
  `id` varchar(32) NOT NULL  COMMENT '特殊时节奖ID',
  `year` varchar(32) NOT NULL  COMMENT '年度',
  `month` varchar(32) NOT NULL  COMMENT '月别',
  `department` varchar(32) COMMENT '部门',
  `group` varchar(32) COMMENT '组别',
  `user_code` varchar(32) COMMENT '工号',
  `user_name` varchar(32) COMMENT '姓名',
  `type` varchar(32) COMMENT '类型',
  `award_name` varchar(32) COMMENT '奖项名称',
  `award_reason` varchar(32) COMMENT '获奖原因',
  `point` Integer(5) COMMENT '积分',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_award_rain_aid`;
CREATE TABLE `sys_award_rain_aid` (
  `id` varchar(32) NOT NULL  COMMENT '雨中送援ID',
  `year` varchar(32) NOT NULL  COMMENT '年度',
  `month` varchar(32) NOT NULL  COMMENT '月别',
  `department` varchar(32) COMMENT '部门',
  `group` varchar(32) COMMENT '组别',
  `user_code` varchar(32) COMMENT '工号',
  `user_name` varchar(32) COMMENT '姓名',
  `type` varchar(32) COMMENT '类型',
  `time` varchar(32) COMMENT '时间',
  `point` Integer(5) COMMENT '积分',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `sys_award_snow_heat`;
CREATE TABLE `sys_award_snow_heat` (
  `id` varchar(32) NOT NULL  COMMENT '雪中送炭ID',
  `year` varchar(32) NOT NULL  COMMENT '年度',
  `month` varchar(32) NOT NULL  COMMENT '月别',
  `department` varchar(32) COMMENT '部门',
  `group` varchar(32) COMMENT '组别',
  `user_code` varchar(32) COMMENT '工号',
  `user_name` varchar(32) COMMENT '姓名',
  `type` varchar(32) COMMENT '类型',
  `time` varchar(32) COMMENT '时间',
  `point` Integer(5) COMMENT '积分',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
DROP TABLE IF EXISTS `sys_tips`;
CREATE TABLE `sys_tips` (
  `id` varchar(32) NOT NULL  COMMENT '小贴士ID',
  `content` varchar(1024) NOT NULL  COMMENT '文本',
  `gmt_update` timestamp(0) NOT NULL  DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
  `gmt_create` timestamp(0) NOT NULL  DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
