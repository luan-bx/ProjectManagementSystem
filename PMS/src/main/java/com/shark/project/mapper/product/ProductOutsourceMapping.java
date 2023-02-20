package com.shark.project.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.shark.project.entity.product.ProductOutsourceEntity;

public interface ProductOutsourceMapping {

	/*
	 * 插入一条外协记录
	 */
	@Insert("INSERT into `pro_product_outsource` (`po_id`, `company`, `eng_name`, `content_desc`, `file_url`, `project_name`) VALUES "
			+ "(#{poId}, #{company}, #{engName}, #{contentDesc}, #{fileUrl}, #{projectName});")
	void insert(ProductOutsourceEntity productOutsourceEntity);
	/*
	 * 根据fileUrl查询记录
	 */
	@Select("SELECT * FROM `pro_product_outsource` WHERE `file_url` = #{fileUrl};")
	ProductOutsourceEntity getProductOutsourceByFileUrl(String fileUrl);
	/*
	 * 根据数据库id查询记录
	 */
	@Select("SELECT * FROM `pro_product_outsource` WHERE `id` = #{id};")
	ProductOutsourceEntity getProductOutsourceByOutId(int id);
	
	/**
	 * 查询所有项目
	 * @return
	 */
	@Select("select * from `pro_product_outsource`")
	List<ProductOutsourceEntity> getAllOut();
	
	/*
	 * 删除
	 */
	@Delete("delete from `pro_product_outsource` where id = #{id}")
	void deleteProductOutsourceById(int id);
}
