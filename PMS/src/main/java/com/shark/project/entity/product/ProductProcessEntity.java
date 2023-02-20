package com.shark.project.entity.product;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductProcessEntity {

	/*
	 *   `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `name` varchar(100) NOT NULL COMMENT '名称', 
	`graph_number` VARCHAR(100) NOT NULL COMMENT '图号',
	`number` int(50) NOT NULL COMMENT '数量',
	`price` DOUBLE(10,2) NOT NULL COMMENT '价格',
	`start_date` timestamp(0) NOT NULL COMMENT '加工开始日期',
	`end_date` timestamp(0) NOT NULL COMMENT '实际加工完成日期',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String name;
	private String graphNumber;
	private int number;
	private double price;
	private Timestamp startDate;
	private Timestamp endDate;
	private String projectName;
	private Timestamp created;
	private Timestamp updated;
}
