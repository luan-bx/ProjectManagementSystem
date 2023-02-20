package com.shark.project.service.design;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.shark.project.controller.design.DesignRelationController;
import com.shark.project.entity.design.DesignRelationEntity;
import com.shark.project.mapper.design.DesignRelationMapping;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DesignRelationService {

	@Autowired
	private DesignRelationMapping DesignRelationMapping;
	
	public String insertDesignRelation(DesignRelationEntity designRelationEntity) {
		if(designRelationEntity == null) {
			return Constants.FAILCODE;
//			没有请求信息传来，弹窗“请求失败，请重新请求”，返回请求页
		}
		try {
			
			DesignRelationMapping.insert(designRelationEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("插入设计关系表记录失败");
			return Constants.FAILCODE;
		}
	}	
	
	public String getMechancisByProid(int id) {
//		if(id == null) {
//			return Constants.FAILCODE;
//		}
		try {		
			return String.valueOf(DesignRelationMapping.getMechancisByProid(id));
		} catch (Exception e) {
			log.info("查询失败");
			return Constants.FAILCODE;
		}
	}
	
	
	
	
}
