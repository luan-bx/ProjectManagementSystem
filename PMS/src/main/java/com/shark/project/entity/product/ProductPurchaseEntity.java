package com.shark.project.entity.product;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductPurchaseEntity {

	/*
	 *   `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `name` varchar(100) NOT NULL COMMENT '名称', 
	`model` VARCHAR(100) NOT NULL COMMENT '型号',
	`number` int(50) NOT NULL COMMENT '数量',
	`supplier` VARCHAR(100) NOT NULL COMMENT '供应商',
	`type` VARCHAR(50) NOT NULL COMMENT '类别',
	`price` DOUBLE(10,2) NOT NULL COMMENT '价格',
	`order_date` timestamp(0) NOT NULL COMMENT '下单日期',
	`delivery_date` timestamp(0) NOT NULL COMMENT '货期',
	`arrival_date` timestamp(0) NOT NULL COMMENT '实际到货日期',
	`warehousing_date` timestamp(0) NOT NULL COMMENT '入库日期',
	`location_number` int(50) NOT NULL COMMENT '库位号',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String name;
	private String model;
	private int number;
	private String supplier;
	private String type;
	private double price;
	private Timestamp orderDate;
	private Timestamp deliveryDate;
	private Timestamp arrivalDate;
	private Timestamp warehousingDate;
	private int locationNumber;
	private String projectName;
	private Timestamp created;
	private Timestamp updated;
}
