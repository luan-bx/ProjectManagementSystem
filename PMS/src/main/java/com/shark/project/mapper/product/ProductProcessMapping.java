package com.shark.project.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.project.entity.product.ProductProcessEntity;

public interface ProductProcessMapping {

	/*
	 * 插入一条零件加工记录
	 */
	@Insert("INSERT INTO `pro_product_process` (`name`, `graph_number`, `number`, `price`, `start_date`, `end_date`, `project_name`) VALUES " + 
			"	(#{name}, #{graphNumber}, #{number}, #{price}, #{startDate}, #{endDate}, #{projectName});")
	void insert(ProductProcessEntity productProcessEntity);
	
	/*
	 * 根据name查询记录
	 */
	@Select("SELECT * FROM `pro_product_process` WHERE `name` = #{name};")
	ProductProcessEntity getProductProcessByName(String name);
	/*
	 * 根据数据库id查询记录
	 */
	@Select("SELECT * FROM `pro_product_process` WHERE `id` = #{id};")
	ProductProcessEntity getProductProcessById(int id);
	/**
	 * 查询所有项目
	 * @return
	 */
	@Select("select * from `pro_product_process`")
	List<ProductProcessEntity> getAllOut();
	
	/*
	 * 删除
	 */
	@Delete("delete from `pro_product_process` where id = #{id}")
	void deleteProductProcessById(int id);
}
