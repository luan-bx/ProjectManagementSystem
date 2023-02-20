package com.shark.project.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.project.entity.product.ProductPurchaseEntity;

public interface ProductPurchaseMapping {

	/*
	 * 插入一条采购记录
	 */
	@Insert("INSERT INTO `pro_product_purchase` (`name`, `model`, `number`, `supplier`, `type`, `price`, `order_date`, `delivery_date`, `arrival_date`, `warehousing_date`, `location_number`, `project_name`) VALUES " + 
			"	(#{name}, #{model}, #{number}, #{supplier}, #{type}, #{price}, #{orderDate}, #{deliveryDate}, #{arrivalDate}, #{warehousingDate}, #{locationNumber}, #{projectName});")
	void insert(ProductPurchaseEntity productPurchaseEntity);
	
	/*
	 * 根据fileUrl查询记录
	 */
	@Select("SELECT * FROM `pro_product_purchase` WHERE `name` = #{name};")
	ProductPurchaseEntity getProductPurchaseByName(String name);
	/*
	 * 根据数据库id查询记录
	 */
	@Select("SELECT * FROM `pro_product_purchase` WHERE `id` = #{id};")
	ProductPurchaseEntity getProductPurchaseById(int id);
	
	/**
	 * 查询所有项目
	 * @return
	 */
	@Select("select * from `pro_product_purchase`")
	List<ProductPurchaseEntity> getAllOut();
	
	/*
	 * 删除
	 */
	@Delete("delete from `pro_product_purchase` where id = #{id}")
	void deleteProductPurchaseById(int id);
}
