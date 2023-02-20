package com.shark.project.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestEntity {

	/*
	 *   `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `name` varchar(100) NOT NULL COMMENT '项目名称', 
	`company_name` VARCHAR(200) NOT NULL COMMENT '公司名称',
	`description` VARCHAR(500) COMMENT '项目简单描述',
	`sow_url` VARCHAR(300) NOT NULL COMMENT '项目的sow文件本地磁盘路径',
	`quotation_url` VARCHAR(300) NOT NULL COMMENT '项目的报价文件本地磁盘路径',
	`design_url` VARCHAR(300) NOT NULL COMMENT '项目的设计方案文件本地磁盘路径',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String name;
	private String companyName;
	private String description;
	private String sowUrl;
	private String quotationUrl;
	private String designUrl;
	private Timestamp created;
	private Timestamp updated;
}
