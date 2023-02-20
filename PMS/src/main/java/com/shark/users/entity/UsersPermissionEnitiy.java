package com.shark.users.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsersPermissionEnitiy {

	/*
	 * `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
	 */
	private int userId;
	private int permissionId;
}
