package com.shark.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shark.project.entity.StatusEntity;

public interface StatusMapping {

	/*
	 * 插入一条状态记录
	 */
	@Insert("INSERT into `pro_status` (`status_name`)VALUES(#{statusName});")
	void insert(StatusEntity statusEntity);
	
	/*
	 * 根据状态描述查询id
	 */
	@Select("SELECT id FROM `pro_status` WHERE `status_name` = #{statusName};")
	int getIdByStatusName(String statusName);
	
	/*
	 * 修改状态描述
	 */
	@Update("UPDATE `pro_status` SET `status_name` = #{newName} WHERE `status_name` = #{statusName};")
	void updateStatusName(String statusName, String newName);
	
	/*
	 * 查询所有状态
	 */
	@Select("select * from `pro_status`")
	List<StatusEntity> getAllStatus();
	
	/*
	 * 根据id查询状态信息
	 */
	@Select("SELECT * FROM `pro_status` WHERE `id` = #{id};")
	StatusEntity getStatusById(int id);
}
