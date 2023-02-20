package com.shark.project.entity.product;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductOutsourceEntity {

	/*
	 *   `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `po_id` varchar(100) NOT NULL COMMENT '项目poId', 
	`company` VARCHAR(100) NOT NULL COMMENT '外协公司名称',
	`eng_name` VARCHAR(10) NOT NULL COMMENT '外协公司对接人员',
	`content_desc` VARCHAR(100) NOT NULL COMMENT '外协内容简单描述',
	`file_url` varchar(300) NOT NULL COMMENT '外协文件存放路径',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String poId;
	private String company;
	private String engName;
	private String contentDesc;
	private String fileUrl;
	private String projectName;
	private Timestamp created;
	private Timestamp updated;
}
