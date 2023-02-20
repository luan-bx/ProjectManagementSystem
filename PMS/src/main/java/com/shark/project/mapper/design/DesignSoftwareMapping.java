package com.shark.project.mapper.design;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.project.entity.design.DesignMechancisEntity;
import com.shark.project.entity.design.DesignSoftwareEntity;
import com.shark.project.entity.product.ProductOutsourceEntity;

public interface DesignSoftwareMapping {

	@Insert("INSERT INTO `pro_design_software` (`prog_url`, `project_name`) VALUES (#{progUrl}, #{projectName});")
	void insert(DesignSoftwareEntity designSoftwateEntity);
	
	/*
	 * 根据progUrl查询记录
	 */
	@Select("SELECT * FROM `pro_design_software` WHERE `prog_url` = #{progUrl};")
	DesignSoftwareEntity getDesignSoftwareByProgUrl(String progUrl);
	
	/*
	 * 根据数据库id查询记录
	 */
	@Select("SELECT * FROM `pro_design_software` WHERE `id` = #{id};")
	DesignSoftwareEntity getDesignSoftwareById(String id);
	/**
	 * 查询所有项目
	 * @return
	 */
	@Select("select * from `pro_design_software`")
	List<DesignSoftwareEntity> getAllOut();
	
	/*
	 * 删除
	 */
	@Delete("delete from `pro_design_software` where id = #{id}")
	void deleteDesignSoftwareById(int id);
}
