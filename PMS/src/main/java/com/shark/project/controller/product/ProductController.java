package com.shark.project.controller.product;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.shark.project.controller.StatusController;
import com.shark.project.controller.product.ProductController;
import com.shark.project.entity.StatusEntity;
import com.shark.project.entity.product.ProductEntity;
import com.shark.project.service.product.ProductService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ProductController {

	@Autowired
	protected ProductService ProductService;
	@Autowired
	protected StatusController statusController;
	
	/*
	 * 插入一条生产记录
	 */
	@RequestMapping("/product")
	public String product(ProductEntity productEntity, StatusEntity statusEntity, HttpServletRequest req) {
	
        String insertProduct = ProductService.insertProduct( productEntity);
        if(insertProduct.equals(Constants.SUCCESSCODE)) {     	
        	statusController.pass(statusEntity);
            log.info("ProductController/product, 生产记录请求成功");
            return "success";
            }else {
	        // 生产记录请求失败 
	        log.info("ProductController/product, 生产记录请求失败");
	        return "fail";
            	}
	}
}
