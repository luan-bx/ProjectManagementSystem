package com.shark.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shark.project.entity.AssemblingEntity;
import com.shark.project.entity.StatusEntity;
import com.shark.project.service.AssemblingService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

//@Controller
@Component
@Slf4j
public class AssemblingController {

	@Autowired
	protected AssemblingService assemblingService;
	/*
	 * 插入一条装配记录
	 */
//	@RequestMapping("/assembling")
	public String assembling(AssemblingEntity assemblingEntity) {
		
        String insertAssembling = assemblingService.insertAssembling( assemblingEntity);
        if(insertAssembling.equals(Constants.SUCCESSCODE)) {     	
            log.info("AssemblingController/Assembling, 生产外协记录请求成功");
            return Constants.SUCCESS;
            }else {
	        // 生产记录请求失败 
	        log.info("AssemblingController/Assembling, 生产外协记录请求失败");
	        return "assemble";
            	}
	}
}
