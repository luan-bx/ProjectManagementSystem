package com.shark.project.controller.design;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shark.project.service.design.DesignService;
import com.shark.util.Constants;
import com.shark.project.controller.StatusController;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.SignEntity;
import com.shark.project.entity.StatusEntity;
import com.shark.project.entity.design.DesignEntity;

import lombok.extern.slf4j.Slf4j;

//@Controller
@Component
@Slf4j
public class DesignController {

	@Autowired
	protected DesignService designService;

	/*
	 * 插入一条设计记录
	 */
//	@RequestMapping("/design")
	public String design(DesignEntity designEntity, HttpServletRequest req) {
		String insertDesign = designService.insertDesign(designEntity);
		if (insertDesign.equals(Constants.SUCCESSCODE)) {
			log.info("DesignController/design, 项目设计记录请求成功");
			return Constants.SUCCESS;
		} else {
			// 项目设计记录请求失败
			log.info("DesignController/design, 项目设计记录请求失败");
			return Constants.DESIGN;
		}
	}
	
	/**
	 * 更新
	 * @param designEntity
	 * @param proname
	 * @param file
	 * @param req
	 * @return
	 */
	public String designUpdate(DesignEntity designEntity, String proname, HttpServletRequest req) {
		String designUpdate = designService.designUpdate(designEntity, proname, req);
		if (designUpdate.equals(Constants.SUCCESSCODE)) {
			log.info("DesignController/designUpdate, 总设计更新成功");
			return Constants.SUCCESS;
		} else {
			// 项目请求失败
			log.info("DesignController/designUpdate, 总设计更新失败");
			return Constants.SIGN;
		}
	}
}
