package com.shark.project.mapper.design;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.project.entity.design.DesignMechancisEntity;
import com.shark.project.entity.design.DesignSoftwareEntity;

public interface DesignMechancisMapping {

	/*
	 * 插入一条机械设计师记录
	 */
	@Insert("INSERT INTO `pro_design_mechanics` (`bom_url`, `threeD_url`, `twoD_url`, `gas_url`, `comp_url`, `prof_url`, `vul_list_url`, `vul_draw_url`, `project_name`) VALUES "
			+ "	(#{bomUrl}, #{threeDUrl}, #{twoDUrl}, #{gasUrl}, #{compUrl}, #{profUrl}, #{vulListUrl}, #{vulDrawUrl}, #{projectName});")
	void insert(DesignMechancisEntity designMechancisEntity);
	
	/*
	 * 根据bomUrl查询记录
	 */
	@Select("SELECT * FROM `pro_design_mechanics` WHERE `bom_url` = #{bomUrl};")
	DesignMechancisEntity getDesignMechancisByBomUrl(String bomUrl);
	/*
	 * 根据数据库id查询记录
	 */
	@Select("SELECT * FROM `pro_design_mechanics` WHERE `id` = #{id};")
	DesignMechancisEntity getDesignMechancisById(String id);
	
	/**
	 * 查询所有项目
	 * @return
	 */
	@Select("select * from `pro_design_mechanics`")
	List<DesignMechancisEntity> getAllOut();
	
	/*
	 * 删除
	 */
	@Delete("delete from `pro_design_mechanics` where id = #{id}")
	void deleteDesignMechancisById(int id);
}
