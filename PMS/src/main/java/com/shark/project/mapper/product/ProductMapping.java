package com.shark.project.mapper.product;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.shark.project.entity.design.DesignRelationEntity;
import com.shark.project.entity.product.ProductEntity;

public interface ProductMapping {

	/*
	 * 插入一条生产记录
	 */
	@Insert("INSERT into `pro_product` (`po_id`, `sign_id`, `pro_id`, `pur_id`, `process_id`, `out_id`)values "
			+ "	(#{poId}, #{signId}, #{proId}, #{purId}, #{processId}, #{outId});")
	void insert(ProductEntity productEntity);
	
	/*
	 * 根据Id查询设计关系表
	 */
	@Select("SELECT * FROM `pro_product` WHERE `id` = #{id};")
	ProductEntity getProductByProductId(int id);
	/*
	 * 根据PoId查询设计关系表
	 */
	@Select("SELECT * FROM `pro_product` WHERE `po_id` = #{poId};")
	ProductEntity getProductByPoId(String poId);
	/**
	 * 更新记录
	 * @param 
	 */
	@Update("UPDATE `pro_product` SET `po_id` = #{poId}, `sign_id` = #{signId}, `pro_id` = #{proId}, "
			+ "`pur_id` = #{purId} , `process_id` = #{processId}, `out_id` = #{outId} "
			+ "WHERE `id` = #{id};")
	void update(ProductEntity productEntity);
	/*
	 * 20220824-thg
	 * 根据Id删除生产表记录
	 */
	@Delete("DELETE FROM `pro_product` WHERE `id`=#{id};")
	void deleteById(int id);
}
