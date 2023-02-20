package com.shark.backendSystem.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DesignEleFieldEntity {

	/*
	 * `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `bom_url` varchar(300) NOT NULL COMMENT 'BOM表存储路径', 
  `graph_url` varchar(300) NOT NULL COMMENT '电气图纸',
	`list_url` varchar(300) NOT NULL COMMENT '备件清单', 
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String dEleBomUrl;
	private String dEleGraphUrl;
	private String dEleListUrl;
	private Timestamp created;
	private Timestamp updated;
}
