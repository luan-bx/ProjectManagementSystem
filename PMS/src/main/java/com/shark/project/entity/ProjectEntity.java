package com.shark.project.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity {

	/*
	 `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
	`name` varchar(100) NOT NULL COMMENT '项目名称', 
	`status_id` int(10) NOT NULL COMMENT '项目状态id',
	`request_id` int(10) COMMENT '项目请求id',
	`pro_sign_id` int(10) COMMENT '项目签订id',
	`pro_design_id` int(10) COMMENT '项目设计id',
	`pro_product_id` int(10) COMMENT '项目生产id',
	`pro_assembling_id` int(10) COMMENT '项目装配id',
	`created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String name;
	private int statusId;
	private int requestId;
	private int proSignId;
	private int proDesignId;
	private int proProductId;
	private int proAssemblingId;
//	private String proAssemblingId;
	private Timestamp created;
	private Timestamp updated;
}
