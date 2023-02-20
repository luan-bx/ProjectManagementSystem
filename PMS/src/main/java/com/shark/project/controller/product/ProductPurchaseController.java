package com.shark.project.controller.product;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shark.project.controller.StatusController;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.StatusEntity;
import com.shark.project.entity.product.ProductPurchaseEntity;
import com.shark.project.service.product.ProductPurchaseService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

//@Controller
@Component
@Slf4j
public class ProductPurchaseController {

	@Autowired
	protected ProductPurchaseService ProductPurchaseService;
	
	/*
	 * 插入一条采购记录
	 */
//	@RequestMapping("/productPurchase")
	public String productPurchase(ProductPurchaseEntity productPurchaseEntity, HttpServletRequest req) {
		
        String insertProductPurchase = ProductPurchaseService.insertProductPurchase( productPurchaseEntity);
        if(insertProductPurchase.equals(Constants.SUCCESSCODE)) {     	
            log.info("ProductPurchaseController/productPurchase, 生产-采购记录请求成功");
            return Constants.SUCCESS;
            }else {
	        // 生产记录请求失败 
	        log.info("ProductPurchaseController/productPurchase, 生产-采购记录请求失败");
	        return "product-purchase";
            	}
	}
}
