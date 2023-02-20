package com.shark.project.controller.design;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shark.project.entity.design.DesignRelationEntity;
import com.shark.project.service.design.DesignRelationService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class DesignRelationController {
	@Autowired
	protected DesignRelationService DesignRelationService;
	
	/*
	 * 插入一条设计关系表记录
	 */
	@RequestMapping("/designRelation")
	public String designRelation(DesignRelationEntity designRelationEntity, HttpServletRequest req) {
		
        String insertDesignRelation = DesignRelationService.insertDesignRelation( designRelationEntity);
        if(insertDesignRelation.equals(Constants.SUCCESSCODE)) {     	
            log.info("DesignRelationController/designRelation, 插入设计关系表记录请求成功");
            return "success";
            }else {
	        // 项目设计记录请求失败 
	        log.info("DesignRelationController/designRelation, 插入设计关系表记录请求失败");
	        return "fail";
            	}
	}
	
	/*
	 * 根据项目公司内部项目表好pro_id查询项目机械设计信息
	 */
	@RequestMapping("/mechancisByProid")
	public String mechancisByProid(DesignRelationEntity designRelationEntity, HttpServletRequest req) {
		
        String getMechancisByProid = DesignRelationService.getMechancisByProid( designRelationEntity.getDesiId());//desiID？？
        if(getMechancisByProid.equals(Constants.FAILCODE)) {     	
            log.info("DesignRelationController/getMechancisByProid, 查询失败");
            return "";//查询界面
            }else {      
	        log.info("DesignRelationController/getMechancisByProid, 查询成功"); 
	        return getMechancisByProid;
            	}
	}
}
