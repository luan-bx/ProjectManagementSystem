package com.shark.users.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shark.users.entity.DepartmentEntity;

public interface DepartmentMapping {

	/*
	 * 插入一个部门记录
	 */
	@Insert("INSERT INTO `department` (`name`, `number`) VALUES(#{name}, #{number});")
	void insert(DepartmentEntity departmentEntity);
	
	/*
	 * 通过name查询id
	 */
	@Select("select id from `department` where name=#{name}")
	int getIdByName(String name);
	/*
	 * 通过id查询name
	 */
	@Select("select name from `department` where id=#{id}")
	String getNameById(int id);
	
	/*
	 * test
	 */
	@Select("select * from `department`")
	List<DepartmentEntity> getAll();
	
	/*
	 * 删除
	 */
	@Delete("delete from `department` where name = #{name}")
	void deleteDepartmentByName(String name);
	/*
	 * 20220823-thg
	 * 根据原部门名修改岗位名
	 */
	@Update("UPDATE `department` SET `name`=#{name} WHERE `name`=#{originName}")
	void updateDepartmentNameByName(@Param("name")String name, @Param("originName")String originName);
	/*
	 * 修改用户表中部门名称
	 */
	@Update("UPDATE `user` SET `department_name`=#{departmentName} WHERE `department_name`=#{originName}")
	void updateDepartmentNameFromUserByOriginName(@Param("departmentName")String departmentName, @Param("originName")String originName);
}
