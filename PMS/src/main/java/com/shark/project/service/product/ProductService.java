package com.shark.project.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shark.project.entity.product.ProductEntity;
import com.shark.project.mapper.product.ProductMapping;
import com.shark.project.service.product.ProductService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
	
	@Autowired
	private ProductMapping productMapping;
	
	public String insertProduct(ProductEntity productEntity) {
		if(productEntity == null) {
			return Constants.FAILCODE;
//			没有请求信息传来，弹窗“请求失败，请重新请求”，返回请求页
		}
		try {
			
			productMapping.insert(productEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("插入生产记录失败");
			return Constants.FAILCODE;
		}
	}	
	public String getProductByPoId(String poId) {
		if(poId == null) {
			return Constants.FAILCODE;    
		}
		try {		
			return JSON.toJSONString(productMapping.getProductByPoId(poId));
		} catch (Exception e) {
			log.info("查询失败",e);
			return Constants.FAILCODE;
		}
	}
}
