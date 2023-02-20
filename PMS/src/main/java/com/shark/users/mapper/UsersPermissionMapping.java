package com.shark.users.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.users.entity.UsersPermissionEnitiy;

public interface UsersPermissionMapping {

	/*
	 * 插入一条用户-权限关联记录
	 */
	@Insert("INSERT INTO `sys_users_permission` VALUES(#{userId}, #{permissionId});")
	void insert(UsersPermissionEnitiy usersPermissionEnitiy);
	
	/*
	 * test
	 */
	@Select("select * from `sys_users_permission`")
	List<UsersPermissionEnitiy> getAll();
}
