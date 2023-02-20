package com.shark.users.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserEntity {
	/*
 `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码，加密存储',
	`openId` varchar(100) NOT NULL COMMENT '用户openid',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
	`gender` int(5) NOT NULL COMMENT '性别，0 未知，1 女性，2 男性',
	`icon` varchar(500) DEFAULT NULL COMMENT '头像地址',
	`number` VARCHAR(20) NOT NULL COMMENT '工号',
	`is_admin` tinyint(1) NOT NULL COMMENT '是否是高级管理员',
	`post_id` int(10) not null COMMENT '岗位id',
	`department_id` int(10) not null COMMENT '部门id',
	`wx_id` int(10) COMMENT '关联的微信用户表id',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String userName;
	private String password;
	private String openId;
	private String phone;
	private String email;
	private int gender;
	private String icon;
	private String number;
	// 1 true 0 false
//	private boolean isAdmin;
	private int postId;
	private String postName;
	private int departmentId;
	private String departmentName;
	private int wxId;
	private Timestamp created;
	private Timestamp updated;
}
