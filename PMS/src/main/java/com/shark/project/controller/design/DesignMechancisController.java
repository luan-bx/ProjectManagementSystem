package com.shark.project.controller.design;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shark.project.controller.StatusController;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.StatusEntity;
import com.shark.project.entity.design.DesignMechancisEntity;
import com.shark.project.service.design.DesignMechancisService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

//@Controller
@Component
@Slf4j
public class DesignMechancisController {

	@Autowired
	protected DesignMechancisService DesignMechancisService;
	
	/*
	 * 插入一条机械设计师记录
	 */ 
//	@RequestMapping("/designMechancis")
	public String designMechancis(DesignMechancisEntity designMechancisEntity, String proname,
			@RequestParam(value = "file", required = false)List<MultipartFile> file,HttpServletRequest req) {
		if(file ==  null) {
			log.info("designMechancis , file为空");
			return "mec-design";
		}
        String insertDesignMechancis = DesignMechancisService.insertDesignMechancis( designMechancisEntity,proname, file, req);
        if(insertDesignMechancis.equals(Constants.SUCCESSCODE)) {     	
            log.info("DesignMechancisController/designMechancis, 插入机械设计师记录请求成功");
            return Constants.SUCCESS;
            }else {
	        // 项目设计记录请求失败 
	        log.info("DesignMechancisController/designMechancis, 插入机械设计师记录请求失败");
	        return "mec-design";
            	}
  
	}
	

}
