package com.shark.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shark.project.controller.ProUserRelationController;
import com.shark.project.entity.ProUserRelationEntity;
import com.shark.project.service.ProUserRelationService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ProUserRelationController {
	
	@Autowired
	protected ProUserRelationService ProUserRelationService;
	
	/*
	 * 插入一条项目-用户关系记录
	 */
	@RequestMapping("/proUserRelation")
	public String proUserRelation(ProUserRelationEntity proUserRelationEntity ) {
		
        String insertProUserRelation = ProUserRelationService.insertProUserRelation( proUserRelationEntity);
        if(insertProUserRelation.equals(Constants.SUCCESSCODE)) {     	
            log.info("ProUserRelationController/proUserRelation, 插入项目-用户关系记录请求成功");
//            	return  ?;
            }else {
	        // 生产记录请求失败 
	        log.info("ProUserRelationController/proUserRelation, 插入项目-用户关系记录请求失败");
            	}
   
        return " "; //返回请求界面
	}
	
	/*
	 * 根据userId,查询相关的proId
	 */
	@RequestMapping("/proIdListByUserId")
	public String proIdListByUserId(ProUserRelationEntity proUserRelationEntity, HttpServletRequest req) {
		
        String getProIdListByUserId = ProUserRelationService.getProIdListByUserId( proUserRelationEntity.getUserId());
        if(getProIdListByUserId.equals(Constants.FAILCODE)) {     	
            log.info("SignController/proIdListByUserId, 查询失败");
            return "";//查询界面
            }else {      
	        log.info("SignController/proIdListByUserId, 查询成功"); 
	        return getProIdListByUserId;
            	}
	}
	
	/*
	 * 根据proId, 查询相关的userId
	 */
	@RequestMapping("/userIdListByProId")
	public String userIdListByProId(ProUserRelationEntity proUserRelationEntity, HttpServletRequest req) {
		
        String getUserIdListByProId = ProUserRelationService.getUserIdListByProId( proUserRelationEntity.getProjectId());
        if(getUserIdListByProId.equals(Constants.FAILCODE)) {     	
            log.info("SignController/userIdListByProId, 查询失败");
            return "";//查询界面
            }else {      
	        log.info("SignController/userIdListByProId, 查询成功"); 
	        return getUserIdListByProId;
            	}
	}
	
}
