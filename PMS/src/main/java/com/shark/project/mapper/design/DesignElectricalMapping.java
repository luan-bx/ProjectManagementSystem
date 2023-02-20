package com.shark.project.mapper.design;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.project.entity.design.DesignElectricalEntity;
import com.shark.project.entity.design.DesignSoftwareEntity;

public interface DesignElectricalMapping {

	/*
	 * 插入一条电气设计记录
	 */
	@Insert("INSERT INTO `pro_design_electrical` (`bom_url`, `graph_url`, `list_url`, `project_name`) VALUES "
			+ "	(#{bomUrl}, #{graphUrl}, #{listUrl}, #{projectName});")
	void insert(DesignElectricalEntity designElectricalEntity);
	
	/*
	 * 根据bomUrl查询记录
	 */
	@Select("SELECT * FROM `pro_design_electrical` WHERE `bom_url` = #{bomUrl};")
	DesignElectricalEntity getDesignElectricalByBomUrl(String bomUrl);
	
	/*
	 * 根据数据库id查询记录
	 */
	@Select("SELECT * FROM `pro_design_electrical` WHERE `id` = #{id};")
	DesignElectricalEntity getDesignElectricalById(String id);
	
	/**
	 * 查询所有项目
	 * @return
	 */
	@Select("select * from `pro_design_electrical`")
	List<DesignElectricalEntity> getAllOut();
	
	/*
	 * 删除
	 */
	@Delete("delete from `pro_design_electrical` where id = #{id}")
	void deleteDesignElectricalById(int id);
}
