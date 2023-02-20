package com.shark.project.mapper.design;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shark.project.entity.SignEntity;
import com.shark.project.entity.design.DesignEntity;

public interface DesignMapping {

	/*
	 * 插入一条设计记录
	 */
	@Insert("INSERT INTO `pro_design` (`po_id`, `sign_id`, `pro_id`, `mec_engineer`, `ele_engineer`, `sof_engineer`, `mec_start_date`, "
			+ "`mec_predict_end_date`, `ele_start_date`, `ele_predict_end_date`,"
			+ "`sof_start_date`, `sof_predict_end_date`) VALUES "
			+ "(#{poId}, #{signId}, #{proId}, #{mecEngineer}, #{eleEngineer}, #{sofEngineer}, #{mecStartDate}, #{mecPredictEndDate}, "
			+ "#{eleStartDate}, #{elePredictEndDate}, "
			+ "#{sofStartDate}, #{sofPredictEndDate});")
	void insert(DesignEntity designEntity);
	
	/*
	 * 根据合同号查询记录
	 */
	@Select("SELECT * FROM `pro_design` WHERE `po_id` = #{poId};")
	DesignEntity getDesignByPoId(String poId);
	
	/*
	 * 根据数据库id查询记录
	 */
	@Select("SELECT * FROM `pro_design` WHERE `id` = #{id};")
	DesignEntity getDesignById(int id);
	
	/*
	 * 根据mecEngineer查询记录
	 */
	@Select("SELECT * FROM `pro_design` WHERE `mec_engineer` = #{mecEngineer};")
	List<DesignEntity> getDesignByMecEngineer(String mecEngineer);
	
	/*
	 * 根据eleEngineer查询记录
	 */
	@Select("SELECT * FROM `pro_design` WHERE `ele_engineer` = #{eleEngineer};")
	List<DesignEntity> getDesignByEleEngineer(String eleEngineer);
	
	/*
	 * 根据sofEngineer查询记录
	 */
	@Select("SELECT * FROM `pro_design` WHERE `sof_engineer` = #{sofEngineer};")
	List<DesignEntity> getDesignBySofEngineer(String sofEngineer);
	
	
	/*
	 * 插入设计实际完成日期
	 */
	
	@Insert("UPDATE `pro_design` SET `mec_end_date` = #{mecEndDate}, `ele_end_date` = #{eleEndDate}, "
			+ "`sof_end_date` = #{sofEndDate} WHERE `po_id` = #{poId}")
	void updateEndDate(@Param("mecEndDate")String mecEndDate, @Param("eleEndDate")String eleEndDate,
			@Param("sofEndDate")String sofEndDate, @Param("poId")String poId);
	
	/*
	 * 更新全部
	 */
	@Update("UPDATE `pro_design` SET `po_id`=#{poId}, `sign_id`=#{signId}, `pro_id`=#{proId}, `mec_engineer`=#{mecEngineer}, "
			+ "`ele_engineer`=#{eleEngineer}, `sof_engineer`=#{sofEngineer}, `mec_start_date`=#{mecStartDate}, "
			+ "`mec_predict_end_date`=#{mecPredictEndDate}, `ele_start_date`=#{eleStartDate}, "
			+ "`ele_predict_end_date`=#{elePredictEndDate}, `sof_start_date`=#{sofStartDate}, `sof_predict_end_date`=#{sofPredictEndDate},"
			                                                                                      //20220822-thg,这里WHERE前面少了个空格
			+ "`mec_end_date` = #{mecEndDate}, `ele_end_date` = #{eleEndDate}, `sof_end_date` = #{sofEndDate} "+ "WHERE `id`=#{id};")
	void updateDesignById(DesignEntity designEntity);
	
	/*
	 * 删除
	 */
	//20220822-thg 改成根据id查询，该表不存在项目名称字段
	@Delete("delete from `pro_design` where `id` = #{id}")
	void deleteById(int id);
}
