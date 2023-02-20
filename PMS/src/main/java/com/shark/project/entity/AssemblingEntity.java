package com.shark.project.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AssemblingEntity {

	/*
	 *   `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `po_id` varchar(100) NOT NULL COMMENT '项目po号', 
	`sign_id` VARCHAR(100) NOT NULL COMMENT '合同号',
	`pro_id` VARCHAR(100) NOT NULL COMMENT '公司内部项目编号',
	`me_name` VARCHAR(50) NOT NULL COMMENT '机械装配人员',
	`me_start_date` timestamp(0) NOT NULL COMMENT '机械装配开始日期',
	`me_end_date` timestamp(0) NOT NULL COMMENT '机械装配完成日期',
	`el_name` VARCHAR(50) NOT NULL COMMENT '电气装配人员',
	`el_start_date` timestamp(0) NOT NULL COMMENT '电气装配开始日期',
	`el_end_date` timestamp(0) NOT NULL COMMENT '电气装配完成日期',
	`so_name` VARCHAR(50) NOT NULL COMMENT '软件调试人员',
	`so_start_date` timestamp(0) NOT NULL COMMENT '软件调试开始日期',
	`so_end_date` timestamp(0) NOT NULL COMMENT '软件调试完成日期',
	`check_name` VARCHAR(50) NOT NULL COMMENT '检验和测试人员',
	`check_start_date` timestamp(0) NOT NULL COMMENT '检验和测试开始日期',
	`check_end_date` timestamp(0) NOT NULL COMMENT '检验和测试完成日期',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
  `me_predict_end_date` timestamp(0) NULL COMMENT '机械装配预计完成日期',
`el_predict_end_date` timestamp(0) NULL COMMENT '电气装配预计完成日期',
`so_predict_end_date` timestamp(0) NULL COMMENT '软件调试预计完成日期',
`check_predict_end_date` timestamp(0) NULL COMMENT '检验和测试预计完成日期',
	 */
	private int id;
	private String poId;
	private String signId;
	private String proId;
	private String meName;
	private Timestamp meStartDate;
	private Timestamp meEndDate;
	private String elName;
	private Timestamp elStartDate;
	private Timestamp elEndDate;
	private String soName;
	private Timestamp soStartDate;
	private Timestamp soEndDate;
	private String checkName;
	private Timestamp checkStartDate;
	private Timestamp checkEndDate;
	private Timestamp mePredictEndDate;
	private Timestamp elPredictEndDate;
	private Timestamp soPredictEndDate;
	private Timestamp checkPredictEndDate;
	private String projectName;
	private Timestamp created;
	private Timestamp updated;
}
