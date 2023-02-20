package com.shark.users.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEntity {

	/*
	 * `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
	`name` VARCHAR(20) NOT NULL COMMENT '部门名称',
	`number` VARCHAR(10) COMMENT '部门代号',
	`created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String name;
	private String number;
	private Timestamp created;
	private Timestamp updated;
}
