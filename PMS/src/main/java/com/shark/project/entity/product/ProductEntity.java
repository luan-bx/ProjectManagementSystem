package com.shark.project.entity.product;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductEntity {

	/*
	 *   `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `po_id` varchar(100) NOT NULL COMMENT '项目po号', 
	`sign_id` VARCHAR(100) NOT NULL COMMENT '合同号',
	`pro_id` VARCHAR(100) NOT NULL COMMENT '公司内部项目编号',
	`pur_id` int(10) NOT NULL COMMENT '采购表id——项目名称',
	`process_id` int(10) NOT NULL COMMENT '机械零件加工表id——项目名称',
	`out_id` int(10) NOT NULL COMMENT '外协表id——项目名称',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String poId;
	private String signId;
	private String proId;
//	private int purId;
//	private int processId;
//	private int outId;
	private String purId;
	private String processId;
	private String outId;
	private Timestamp created;
	private Timestamp updated;
}
