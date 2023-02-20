package com.shark.project.controller.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shark.project.controller.StatusController;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.StatusEntity;
import com.shark.project.entity.product.ProductOutsourceEntity;
import com.shark.project.service.product.ProductOutsourceService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

//@Controller
@Component
@Slf4j
public class ProductOutsourceController {

	@Autowired
	protected ProductOutsourceService ProductOutsourceService;

	/*
	 * 插入一条外协记录
	 */
//	@RequestMapping("/productOutsource")
	public String productOutsource(ProductOutsourceEntity productOutsourceEntity, String proname,
			@RequestParam(value = "file", required = false) List<MultipartFile> file, HttpServletRequest req) {
		if (file == null) {
			log.info("productOutsource , file为空");
			return Constants.PRODUCTOUT;
		}
		String insertProductOutsource = ProductOutsourceService.insertProductOutsource(productOutsourceEntity, proname,
				file, req);
		if (insertProductOutsource.equals(Constants.SUCCESSCODE)) {
			log.info("ProductOutsourceController/productOutsource, 生产外协记录请求成功");
			return Constants.SUCCESS;
		} else {
			// 生产记录请求失败
			log.info("ProductOutsourceController/productOutsource, 生产外协记录请求失败");
			return Constants.PRODUCTOUT; // 返回请求界面
		}
	}
}
