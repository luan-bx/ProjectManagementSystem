package com.shark.project.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shark.project.entity.SignEntity;

public interface SignMapping {

	/*
	 * 插入一条签订记录
	 */
	@Insert("INSERT INTO `pro_sign` (`po_id`, `sign_id`, `pro_id`, `customer_id`, `product_category`, `line_number`, "
			+ "`device_number`, `customer_engineer`, `delivery_date`, `payment_term`) VALUES"
			+ " (#{poId}, #{signId}, #{proId}, #{customerId}, #{productCategory}, #{lineNumber}, #{deviceNumber}, "
			+ "#{customerEngineer}, #{deliveryDate}, #{paymentTerm});")
	void insert(SignEntity signEntity);
	
	/*
	 * 更新签订记录
	 */
	@Update("UPDATE `pro_sign` SET `po_id` = #{poId}, `sign_id` = #{signId}, `pro_id` = #{proId}, `customer_id` = #{customerId}, "
			+ "`product_category` = #{productCategory}, `line_number` = #{lineNumber}, `device_number` = #{deviceNumber}, "
			+ "`customer_engineer` = #{customerEngineer}, `delivery_date` = #{deliveryDate}, `payment_term` = #{paymentTerm}"
			+ "WHERE `id` = #{id}")
	void update(SignEntity signEntity);
	
	/*
	 * 根据po查询签订记录
	 */
	@Select("SELECT * FROM `pro_sign` WHERE `po_id` = #{poId};")
	SignEntity getSignByPoId(String poId);
	
	/*
	 * 根据合同号查询签订记录
	 */
	@Select("SELECT * FROM `pro_sign` WHERE `sign_id` = #{signId};")
	SignEntity getSignBySignId(String signId);
	/*
	 * 根据公司项目编号查询签订记录
	 */
	@Select("SELECT * FROM `pro_sign` WHERE `pro_id` = #{proId};")
	SignEntity getSignByProId(String proId);
	/*
	 * 根据客户公司代码查询签订记录
	 */
	@Select("SELECT * FROM `pro_sign` WHERE `customer_id` = #{customerId};")
	SignEntity getSignByCustomerId(String customerId);
	/*
	 * 根据交货日期查询签订记录
	 */
	@Select("SELECT * FROM `pro_sign` WHERE `delivery_date` = #{deliveryDate};")
	SignEntity getSignByDeliveryDate(Timestamp deliveryDate);
	
	/*
	 * 根据id查询签订记录
	 */
	@Select("SELECT * FROM `pro_sign` WHERE `id` = #{id};")
	SignEntity getSignById(int id);
	
	/*
	 * 删除
	 */
	@Delete("delete from `pro_sign` where `id` = #{id}")
	void deleteById(int id);
}
