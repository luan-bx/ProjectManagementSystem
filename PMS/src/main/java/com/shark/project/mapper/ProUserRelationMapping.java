package com.shark.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.project.entity.ProUserRelationEntity;

public interface ProUserRelationMapping {

	/*
	 * 插入一条项目-用户关系记录
	 */
	@Insert("INSERT into `pro_user_relation` (`project_id`, `user_id`) VALUE (#{projectId}, #{userId});")
	void insert(ProUserRelationEntity proUserRelationEntity);
	
	/*
	 * 根据userId,查询相关的proId
	 */
	@Select("select `pro_id` from `pro_user_relation` where `user_id` = #{userId}")
	List<Integer> getProIdListByUserId(int userId);
	
	/*
	 * 根据proId, 查询相关的userId
	 */
	@Select("select `user_id` from `pro_user_relation` where `project_id` = #{projectId}")
	List<Integer> getUserIdListByProId(int proId);
}
