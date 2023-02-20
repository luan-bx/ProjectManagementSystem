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
import com.shark.project.entity.design.DesignSoftwareEntity;
import com.shark.project.service.design.DesignSoftwareService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

//@Controller
@Component
@Slf4j
public class DesignSoftwareController {

	@Autowired
	protected DesignSoftwareService DesignSoftwareService;
	
	/*
	 * 插入一条软件设计记录
	 */ 
//	@RequestMapping("/designSoftware")
	public String designSoftware(DesignSoftwareEntity designSoftwareEntity,String proname,
			@RequestParam(value = "file", required = false)List<MultipartFile> file,HttpServletRequest req) {
		if(file ==  null) {
			log.info("designSoftware , file为空");
			return "sof-design";
		}
        String insertDesignSoftware = DesignSoftwareService.insertDesignSoftware( designSoftwareEntity,proname,file, req);
        if(insertDesignSoftware.equals(Constants.SUCCESSCODE)) {     	
            log.info("DesignSoftwareController/designSoftware, 项目软件设计记录请求成功");
            return Constants.SUCCESS;
            }else {
	        // 项目软件设计记录请求失败 
	        log.info("DesignSoftwareController/designSoftware, 项目软件设计记录请求失败");
	        return "sof-design";
            	}
	}
}
