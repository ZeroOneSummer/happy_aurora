-- 用户操作日志表
CREATE TABLE `t_sys_user_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module_code` varchar(255) NOT NULL DEFAULT '',
  `type` tinyint(2) NOT NULL,
  `title` varchar(255) NOT NULL DEFAULT '',
  `operator_id` bigint(20) NOT NULL,
  `operate_time` datetime NOT NULL,
  `content` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8mb4;
