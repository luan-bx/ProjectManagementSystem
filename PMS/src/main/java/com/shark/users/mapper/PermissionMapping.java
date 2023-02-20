package com.shark.users.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.users.entity.PermissionEntity;

public interface PermissionMapping {

	/*
	 * 插入一个权限记录
	 */
	@Insert("INSERT INTO `sys_permission` (`description`) VALUES (#{description});")
	void insert(PermissionEntity permissionEntity);
	
	/*
	 * test
	 */
	@Select("select * from `sys_permission`")
	List<PermissionEntity> getAll();
}
