package com.shark.project.controller.product;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shark.project.controller.StatusController;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.StatusEntity;
import com.shark.project.entity.product.ProductProcessEntity;
import com.shark.project.service.product.ProductProcessService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

//@Controller
@Component
@Slf4j
public class ProductProcessController {

	@Autowired
	protected ProductProcessService ProductProcessService;
	
	/*
	 * 插入一条零件加工记录
	 */
//	@RequestMapping("/productProcess")
	public String productProcess(ProductProcessEntity productProcessEntity, HttpServletRequest req) {
        String insertProductProcess = ProductProcessService.insertProductProcess( productProcessEntity);
        if(insertProductProcess.equals(Constants.SUCCESSCODE)) {   
            log.info("ProductProcessController/productProcess, 生产-零件加工记录请求成功");
            return Constants.SUCCESS;
            }else {
	        // 生产-零件加工记录请求失败 
	        log.info("ProductProcessController/productProcess, 生产-零件加工记录请求失败");
	        return "product-process";
            	}
	}
}
