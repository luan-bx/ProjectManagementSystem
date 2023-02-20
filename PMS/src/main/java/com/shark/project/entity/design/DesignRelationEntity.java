package com.shark.project.entity.design;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class DesignRelationEntity {

	/*
	 *   `desi_id` int(100) NOT NULL COMMENT '项目设计表id',
  `mech_id` int(100) NOT NULL COMMENT '机械设计表id', 
	`elec_id` int(100) NOT NULL COMMENT '电气设计表id', 
	`soft_id` int(100) NOT NULL COMMENT '软件设计表id', 
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int desiId;
//	private int mechId;
//	private int elecId;
//	private int softId;
	private String mechId;
	private String elecId;
	private String softId;
	private Timestamp created;
	private Timestamp updated;
}
