package com.shark.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shark.project.entity.ProUserRelationEntity;
import com.shark.project.mapper.ProUserRelationMapping;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProUserRelationService {

	@Autowired
	private ProUserRelationMapping ProUserRelationMapping;
	
	public String insertProUserRelation(ProUserRelationEntity proUserRelationEntity) {
		if(proUserRelationEntity == null) {
			return Constants.FAILCODE;
//			没有请求信息传来，弹窗“请求失败，请重新请求”，返回请求页
		}
		try {
			ProUserRelationMapping.insert(proUserRelationEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("项目-用户关系记录失败");
			return Constants.FAILCODE;
		}
	}
	
	public String getProIdListByUserId(int userId) {
		if(userId == 0) {
			return Constants.FAILCODE;
		}
		try {		
			return String.valueOf(ProUserRelationMapping.getProIdListByUserId(userId));
		} catch (Exception e) {
			log.info("查询失败");
			return Constants.FAILCODE;
		}
	}
	
	public String getUserIdListByProId(int proId) {
		if(proId == 0) {
			return Constants.FAILCODE;
		}
		try {		
			return String.valueOf(ProUserRelationMapping.getUserIdListByProId(proId));
		} catch (Exception e) {
			log.info("查询失败");
			return Constants.FAILCODE;
		}
	}
	
}
