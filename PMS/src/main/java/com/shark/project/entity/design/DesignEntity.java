package com.shark.project.entity.design;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DesignEntity {

	/*
	 *   `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `po_id` varchar(100) NOT NULL COMMENT '项目po号', 
	`sign_id` VARCHAR(100) NOT NULL COMMENT '合同号',
	`pro_id` VARCHAR(100) NOT NULL COMMENT '公司内部项目编号',
	`mec_engineer` VARCHAR(50) NOT NULL COMMENT '机械设计工程师',
	`ele_engineer` VARCHAR(50) NOT NULL COMMENT '电气设计工程师',
	`sof_engineer` VARCHAR(50) NOT NULL COMMENT '软件设计工程师',
	`mec_start_date` timestamp(0) NOT NULL COMMENT '机械设计开始日期',
	`mec_end_date` timestamp(0) NOT NULL COMMENT '机械设计完成日期',
	`ele_start_date` timestamp(0) NOT NULL COMMENT '电气设计开始日期',
	`ele_end_date` timestamp(0) NOT NULL COMMENT '电气设计完成日期',
	`sof_start_date` timestamp(0) NOT NULL COMMENT '软件开始日期',
	`sof_end_date` timestamp(0) NOT NULL COMMENT '软件完成日期',
//	`tip` VARCHAR(300) NOT NULL COMMENT '备注',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
  `mec_predict_end_date` timestamp(0) NULL COMMENT '机械设计预计完成日期',
`ele_predict_end_date` timestamp(0) NULL COMMENT '电气设计预计完成日期',
`sof_predict_end_date` timestamp(0) NULL COMMENT '软件预计完成日期',
	 */
	private int id;
	private String poId;
	private String signId;
	private String proId;
	private String mecEngineer;
	private String eleEngineer;
	private String sofEngineer;
	private Timestamp mecStartDate;
	private Timestamp mecPredictEndDate;
	private Timestamp mecEndDate;
	private Timestamp eleStartDate;
	private Timestamp elePredictEndDate;
	private Timestamp eleEndDate;
	private Timestamp sofStartDate;
	private Timestamp sofPredictEndDate;
	private Timestamp sofEndDate;
//	private String tip;
	private Timestamp created;
	private Timestamp updated;
	
	
	
	
}
