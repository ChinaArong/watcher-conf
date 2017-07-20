
CREATE TABLE `distribution_conf_group` (
  `group_name` varchar(100) NOT NULL,
  `group_title` varchar(100) NOT NULL COMMENT '描述',
  `del_flag`  tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标识位',
  PRIMARY KEY (`group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `distribution_conf_group` VALUES ('default', '默认分组',0);

CREATE TABLE `distribution_conf_node` (
  `node_group` varchar(100) NOT NULL COMMENT '分组',
  `node_key` varchar(100) NOT NULL COMMENT '配置Key',
  `node_value` varchar(512) DEFAULT NULL COMMENT '配置Value',
  `node_desc` varchar(100) DEFAULT NULL COMMENT '配置简介',
  `del_flag`  tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除标识位',
  UNIQUE KEY `u_group_key` (`node_group`,`node_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `distribution_conf_node` VALUES ('default', 'key01', '168', '测试配置01',0)
, ('default', 'key02', '127.0.0.1:3307', '测试配置02',0);

COMMIT;

