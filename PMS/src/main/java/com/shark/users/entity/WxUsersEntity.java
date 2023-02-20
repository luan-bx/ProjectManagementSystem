package com.shark.users.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WxUsersEntity {

	/*
	 * `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（自增长）',
  `nick_name` varchar(50) NOT NULL COMMENT '微信用户名',
  `avatar_url` varchar(500) NOT NULL COMMENT '微信头像地址',
	`openId` varchar(100) NOT NULL COMMENT '微信网关返回的用户唯一标识',
  `phone` varchar(20) NOT NULL COMMENT '手机号', # 注册时通过手机号和users表进行关联
	`user_id` int(10) COMMENT '关联的用户表id',
  `created` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动插入，创建时间',
  `updated` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '自动插入，修改时间',
	 */
	private int id;
	private String nickName;
	private String avatarUrl;
	private String openId;
	private String phone;
	private int userId;
	private Timestamp created;
	private Timestamp updated;
}