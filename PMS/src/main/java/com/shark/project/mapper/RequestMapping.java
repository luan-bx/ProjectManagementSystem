package com.shark.project.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.project.entity.RequestEntity;

public interface RequestMapping {

	/*
	 * 插入一个项目请求记录
	 */
	@Insert("INSERT into `pro_request` (`name`, `company_name`, `description`, `sow_url`, `quotation_url`, `design_url`)VALUES"
			+ "	(#{name}, #{companyName}, #{description}, #{sowUrl}, #{quotationUrl}, #{designUrl})")
	void insert(RequestEntity requestEntity);
	
	/*
	 * 根据项目名称查询项目信息
	 */
	@Select("SELECT * FROM `pro_request` WHERE `name` = #{name};")
	RequestEntity getRequestByName(String name);
	
	/*
	 * 根据客户公司名称查询项目信息
	 */
	@Select("SELECT * FROM `pro_request` WHERE `company_name` = #{companyName};")
	RequestEntity getRequestBycompanyName(String companyName);
	/*
	 * 删除
	 */
	@Delete("delete from `pro_request` where `name` = #{name}")
	void deleteByName(String name);
}
