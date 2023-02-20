package com.shark.users.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissionEntity {

	/*
	 * `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String description;
	private Timestamp created;
	private Timestamp updated;
}
