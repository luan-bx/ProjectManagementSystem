package com.shark.project.entity.design;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DesignMechancisEntity {

	/*
	 * `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `bom_url` varchar(300) NOT NULL COMMENT 'BOM表存储路径', 
  `threeD_url` varchar(300) NOT NULL COMMENT '机械设计3D图纸',
	`twoD_url` varchar(300) NOT NULL COMMENT '2D图纸', 
	`gas_url` varchar(300) NOT NULL COMMENT '气路图', 
	`comp_url` varchar(300) NOT NULL COMMENT '装配组件图', 
	`prof_url` varchar(300) NOT NULL COMMENT '型材框架图', 
	`vul_list_url` varchar(300) NOT NULL COMMENT '易损件清单', 
	`vul_draw_url` varchar(300) NOT NULL COMMENT '易损件图纸',  
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String bomUrl;
	private String threeDUrl;
	private String twoDUrl;
	private String gasUrl;
	private String compUrl;
	private String profUrl;
	private String vulListUrl;
	private String vulDrawUrl;
	private String projectName;
	private Timestamp created;
	private Timestamp updated;
}
