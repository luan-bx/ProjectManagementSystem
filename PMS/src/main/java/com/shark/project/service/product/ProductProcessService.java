package com.shark.project.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shark.project.entity.product.ProductProcessEntity;
import com.shark.project.mapper.product.ProductProcessMapping;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductProcessService {

	@Autowired
	private ProductProcessMapping productProcessMapping;
	
	public String insertProductProcess(ProductProcessEntity productProcessEntity) {
		if(productProcessEntity == null) {
			return Constants.FAILCODE;
//			没有请求信息传来，弹窗“请求失败，请重新请求”，返回请求页
		}
		try {
			productProcessMapping.insert(productProcessEntity);
			log.info("ProductProcessService/insertProductProcess,数据库请求成功");
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("插入生产-零加工记录失败",e);
			return Constants.FAILCODE;
		}
	}	
	public String getProductProcessByName(String name) {
		if(name == null) {
			return Constants.FAILCODE;    
		}
		try {		
			return JSON.toJSONString(productProcessMapping.getProductProcessByName(name));
		} catch (Exception e) {
			log.info("查询失败",e);
			return Constants.FAILCODE;
		}
	}
}
