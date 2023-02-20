package com.shark.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shark.project.entity.RequestEntity;
import com.shark.project.service.RequestService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

//@Controller
@Component
@Slf4j
public class RequestController {

	@Autowired
	protected RequestService RequestService;

	/*
	 * 项目请求界面，填好信息，点击“提交”，插入一个项目请求
	 */
//	@RequestMapping("/request")
	public String request(RequestEntity requestEntity, List<MultipartFile> file, HttpServletRequest req) {
		String insertRequest = RequestService.insertRequest(requestEntity, file, req);
		if (insertRequest.equals(Constants.SUCCESSCODE)) {
			log.info("RequestController/request, 项目请求成功");
			return Constants.SUCCESS;
		} else {
			// 项目请求失败
			log.info("RequestController/request, 项目请求失败");
			req.setAttribute("companyName", requestEntity.getCompanyName());
			req.setAttribute("name", requestEntity.getName());
			req.setAttribute("description", requestEntity.getDescription());
			return Constants.NEW;
		}
	}

	/*
	 * 根据项目名称查询项目信息
	 */
//	@RequestMapping("/requestByName")
	public String requestByName(RequestEntity requestEntity, HttpServletRequest req) {
		return RequestService.getRequestByName(requestEntity.getName());
	}

	/*
	 * 根据客户公司名称查询项目信息
	 */
	@RequestMapping("/requestBycompanyName")
	public String requestBycompanyName(RequestEntity requestEntity, HttpServletRequest req) {

		String getRequestBycompanyName = RequestService.getRequestBycompanyName(requestEntity.getCompanyName());
		if (getRequestBycompanyName.equals(Constants.FAILCODE)) {
			log.info("RequestController/requestBycompanyName, 查询失败");
			return "";// 查询界面
		} else {
			log.info("RequestController/requestBycompanyName, 查询成功");
			return getRequestBycompanyName;
		}
	}

}
