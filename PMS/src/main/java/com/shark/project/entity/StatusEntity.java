package com.shark.project.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusEntity {

	/*
	 * `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `status_name` varchar(50) NOT NULL COMMENT '流程描述', 
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String statusName;
	private Timestamp created;
	private Timestamp updated;
}
