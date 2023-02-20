package com.shark.project.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shark.project.entity.product.ProductPurchaseEntity;
import com.shark.project.mapper.product.ProductPurchaseMapping;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductPurchaseService {

	@Autowired
	private ProductPurchaseMapping productPurchaseMapping;
	
	public String insertProductPurchase(ProductPurchaseEntity productPurchaseEntity) {
		if(productPurchaseEntity == null) {
			return Constants.FAILCODE;
//			没有请求信息传来，弹窗“请求失败，请重新请求”，返回请求页
		}
		try {
			productPurchaseMapping.insert(productPurchaseEntity);
			log.info("ProductPurchaseService/insertProductPurchase,数据库请求成功");
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("插入生产-采购记录失败",e);
			return Constants.FAILCODE;
		}
	}	
	
	public String getProductPurchaseByName(String name) {
		if(name == null) {
			return Constants.FAILCODE;    
		}
		try {		
			return JSON.toJSONString(productPurchaseMapping.getProductPurchaseByName(name));
		} catch (Exception e) {
			log.info("查询失败",e);
			return Constants.FAILCODE;
		}
	}
}
