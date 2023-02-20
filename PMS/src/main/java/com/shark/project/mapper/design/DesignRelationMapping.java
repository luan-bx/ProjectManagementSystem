package com.shark.project.mapper.design;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.design.DesignMechancisEntity;
import com.shark.project.entity.design.DesignRelationEntity;

public interface DesignRelationMapping {

	/*
	 * 插入一条设计关系表记录
	 */
	@Insert("INSERT INTO `pro_design_relation` (`desi_id`, `mech_id`, `elec_id`, `soft_id`) VALUES "
			+ "(#{desiId}, #{mechId}, #{elecId}, #{softId});")
	void insert(DesignRelationEntity designRelationEntity);
	
	/*
	 * 根据项目公司内部项目表好pro_id查询项目机械设计信息
	 */
	@Select("SELECT m.*"
			+ " FROM pro_design d, pro_design_mechanics m, pro_design_relation r "
			+ " WHERE r.`desi_id` = d.id and r.`mech_id` = m.id and d.pro_id = 'proId';")
	
	DesignMechancisEntity getMechancisByProid(int proId);
	/*
	 * 根据desiId查询设计关系表
	 */
	@Select("SELECT * FROM `pro_design_relation` WHERE `desi_id` = #{desiId};")
	DesignRelationEntity getDesignRelationByDesiId(int desiId);

	/**
	 * 更新记录
	 * @param 
	 */
	@Update("UPDATE `pro_design_relation` SET `desi_id` = #{desiId}, `mech_id` = #{mechId}, `elec_id` = #{elecId}, "
			+ "`soft_id` = #{softId} "
			+ "WHERE `desi_id` = #{desiId}")
	void update(DesignRelationEntity designRelationEntity);

}
