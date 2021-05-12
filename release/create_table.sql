
CREATE TABLE `t_jpa_user` (
  `id` bigint not null auto_increment comment 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `age` tinyint(4) DEFAULT NULL COMMENT '年龄'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;