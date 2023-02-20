package com.shark.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shark.project.entity.AssemblingEntity;
import com.shark.project.mapper.AssemblingMapping;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j

public class AssemblingService {
	
	@Autowired
	private AssemblingMapping assemblingMapping;

	public String insertAssembling(AssemblingEntity assemblingEntity) {
		if(assemblingEntity == null) {
			return Constants.FAILCODE;
//			没有请求信息传来，弹窗“请求失败，请重新请求”，返回请求页
		}
		try {
			
			assemblingMapping.insert(assemblingEntity);
			log.info("AssemblingService/insertAssembling,数据库请求成功");
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("插入装配记录请求失败",e);
			return Constants.FAILCODE;
		}
	}
	
	public String getAssemblingByPoId(String poId) {
		if(poId == null) {
			return Constants.FAILCODE;    
		}
		try {		
			return JSON.toJSONString(assemblingMapping.getAssemblingByPoId(poId));
		} catch (Exception e) {
			log.info("查询失败",e);
			return Constants.FAILCODE;
		}
	}
}
