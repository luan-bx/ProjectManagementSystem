package com.shark.project.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProUserRelationEntity {

	/*
	`project_id` VARCHAR(100) NOT NULL COMMENT '项目表id',
	`user_id` int(10) NOT NULL COMMENT '用户数据库id',
	`created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
	`updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int projectId;
	private int userId;
	private Timestamp created;
	private Timestamp updated;
}
