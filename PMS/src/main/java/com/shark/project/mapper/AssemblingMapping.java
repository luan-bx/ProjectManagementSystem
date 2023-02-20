package com.shark.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shark.project.entity.AssemblingEntity;
import com.shark.project.entity.product.ProductEntity;
import com.shark.project.entity.product.ProductProcessEntity;

public interface AssemblingMapping {

	/*
	 * 插入一条装配记录
	 */
	@Insert("INSERT INTO `pro_assembling` (`po_id`, `sign_id`, `pro_id`, `me_name`, `me_start_date`, `me_end_date`, `me_predict_end_date`, `el_name`, `el_start_date`, `el_end_date`," + 
			"	`el_predict_end_date`, `so_name`, `so_start_date`, `so_end_date`, `so_predict_end_date`, `check_name`, `check_start_date`, `check_end_date`, `check_predict_end_date`, `project_name`) VALUES " + 
			"	(#{poId}, #{signId}, #{proId}, #{meName}, #{meStartDate}, #{meEndDate}, #{mePredictEndDate}, #{elName}, #{elStartDate}, #{elEndDate}, #{elPredictEndDate}, #{soName}, #{soStartDate}, #{soEndDate}, #{soPredictEndDate}, #{checkName}, #{checkStartDate}, #{checkEndDate}, #{checkPredictEndDate}, #{projectName});")
	void insert(AssemblingEntity assemblingEntity);
	/*
	 * 根据fileUrl查询记录
	 */
	@Select("SELECT * FROM `pro_assembling` WHERE `po_id` = #{poId};")
	AssemblingEntity getAssemblingByPoId(String poId);
	/*
	 * 根据Id查询设计关系表
	 */
	@Select("SELECT * FROM `pro_assembling` WHERE `id` = #{id};")
	AssemblingEntity getAssemblingId(int id);
	
	/*
	 * 根据合同号查询记录
	 */
	@Select("SELECT * FROM `pro_assembling` WHERE `po_id` = #{poId};")
	AssemblingEntity getAssembleByPoId(String poId);
	
	/*
	 * 插入装配实际完成时间
	 */
	@Update("UPDATE `pro_assembling` SET `me_end_date` = #{meEndDate}, `el_end_date` = #{elEndDate}, `so_end_date` = #{soEndDate}, `check_end_date` = #{checkEndDate}")
	void reAssembingSubmit(@Param("meEndDate")String meEndDate, @Param("elEndDate")String elEndDate, @Param("soEndDate")String soEndDate, @Param("checkEndDate")String checkEndDate, @Param("poId")String poId);

	
	/**
	 * 查询所有项目
	 * @return
	 */
	@Select("select * from `pro_assembling`")
	List<AssemblingEntity> getAllOut();
	
	/*
	 * 删除
	 */
	@Delete("delete from `pro_assembling` where id = #{id}")
	void deleteAssemblingById(int id);
}
