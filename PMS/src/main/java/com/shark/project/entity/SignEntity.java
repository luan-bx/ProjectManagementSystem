package com.shark.project.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignEntity {

	/*
	 *   `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `po_id` varchar(100) NOT NULL COMMENT '项目po号', 
	`sign_id` VARCHAR(100) NOT NULL COMMENT '合同号',
	`pro_id` VARCHAR(100) NOT NULL COMMENT '公司内部项目编号',
	`customer_id` VARCHAR(100) NOT NULL COMMENT '客户代码',
	`product_category` VARCHAR(50) COMMENT '产品类别',
	`line_number` VARCHAR(100) COMMENT '生产线编号',
	`device_number` VARCHAR(100) COMMENT '设备工位号',
	`customer_engineer` VARCHAR(50) COMMENT '对应的客户工程师',
	`delivery_date` timestamp(0) NOT NULL COMMENT '交货日期',
	`pay_url` VARCHAR(300) NOT NULL COMMENT '付款条款文件本地磁盘路径',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String poId;
	private String signId;
	private String proId;
	private String customerId;
	private String productCategory;
	private String lineNumber;
	private String deviceNumber;
	private String customerEngineer;
	private Timestamp deliveryDate;
	private String paymentTerm;
	private Timestamp created;
	private Timestamp updated;
}
