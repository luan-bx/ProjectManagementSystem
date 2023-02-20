package com.shark.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shark.backendSystem.entity.DesignEleFieldEntity;
import com.shark.backendSystem.entity.DesignMecFieldEntity;
import com.shark.project.entity.ProjectEntity;

public interface ProjectMapping {

	/**
	 * 查询所有项目
	 * @return
	 */
	@Select("select * from `project`")
	List<ProjectEntity> getAll();
	
	/*
	 * 删除
	 */
	@Delete("delete from `project` where `name` = #{name}")
	void deleteProjectByName(String name);
	
	/**
	 * 根据id查询项目
	 * @param id
	 * @return
	 */
	@Select("select * from `project` where `id` = #{id}")
	ProjectEntity getProjectById(int id);
	
	/**
	 * 根据name查询项目
	 * @param name
	 * @return
	 */
	@Select("select * from `project` where `name` = #{name}")
	ProjectEntity getProjectByName(String name);
	
	/**
	 * 根据projectId查询项目
	 * @param projectId
	 * @return
	 */
	@Select("select * from `project` where `projectId` = #{projectId}")
	ProjectEntity getProjectByProjectId(int projectId);
	
	
	
	/**
	 * 插入一个项目记录
	 * @param projectEntity
	 */
	@Insert("INSERT INTO `project` (`name`, `status_id`, `request_id`) VALUES (#{name}, #{statusId}, #{requestId});")
	void insert(ProjectEntity projectEntity);
	
	/**
	 * 更新项目记录
	 * @param projectEntity
	 */
	@Update("UPDATE `project` SET `status_id` = #{statusId}, `request_id` = #{requestId}, `pro_sign_id` = #{proSignId}, "
			+ "`pro_design_id` = #{proDesignId}, `pro_product_id` = #{proProductId}, `pro_assembling_id` = #{proAssemblingId} "
			+ "WHERE `id` = #{id}")
	void update(ProjectEntity projectEntity);
	
	/**
	 * 	qh
	 *  根据项目状态查询project id列表
	 * @param status
	 * @return
	 */
	@Select("select p.id from `project` p, `pro_status` s where p.`status_id` = s.id and s.status_name = #{status};")
	List<Integer> getProjectListBystatus(String status);
	
	/**
	 * 20220826-thg
	 * 查询电气设计流程
	 * 
	 */
	@Select("SELECT * FROM `design_ele_field`;")
	DesignEleFieldEntity getEleFieldEntity();
	/**
	 * 20220826-thg
	 * 查询机械设计流程
	 */
	@Select("SELECT * FROM `design_mec_field`;")
	DesignMecFieldEntity getMecFieldEntity();
}
