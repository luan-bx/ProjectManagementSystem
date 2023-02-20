package com.shark.project.entity.design;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DesignSoftwareEntity {

	/*
	 *   `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `prog_url` varchar(300) NOT NULL COMMENT 'PLC程序存储路径', 
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
  PRIMARY KEY (`id`) USING BTREE
	 */
	private int id;
	private String progUrl;
	private String projectName;
	private Timestamp created;
	private Timestamp updated;
}
