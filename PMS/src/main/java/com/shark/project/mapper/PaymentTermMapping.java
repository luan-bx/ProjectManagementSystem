package com.shark.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.project.entity.PaymentTermEntity;

public interface PaymentTermMapping {

	/*
	 * 插入一个付款条件记录
	 */
	@Insert("INSERT INTO `payment_term` (`name`) VALUES (#{name});")
	void insert(PaymentTermEntity paymentTermEntity);
	
	/*
	 * 获取全部付款条件选项
	 */
	@Select("select * from `payment_term`")
	List<PaymentTermEntity> getAll();
	
	/*
	 * 删除用户
	 */
	@Delete("delete from `payment_term` where id = #{id}")
	void deletePaymentTermById(int id);
}
