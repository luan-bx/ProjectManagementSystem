package com.shark.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shark.project.entity.StatusEntity;
import com.shark.project.service.StatusService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class StatusController {

	@Autowired
	protected StatusService StatusService;

	/*
	 * 插入一条状态记录
	 */
//	@RequestMapping("/status")
	public String status(StatusEntity statusEntity, HttpServletRequest req) {

		String insertstatus = StatusService.insertstatus(statusEntity);
		if (insertstatus.equals(Constants.SUCCESSCODE)) {
			log.info("StatusController/request, 项目请求成功");
//            	return  ?;
		} else {
			// 项目请求失败
			log.info("StatusController/request, 项目请求失败");
		}

		return " "; // 返回请求界面
	}

	/*
	 * 根据状态描述查询id
	 */
	@RequestMapping("/idByStatusName")
	public String idByStatusName(StatusEntity statusEntity, HttpServletRequest req) {
		int getIdByStatusName = StatusService.getIdByStatusName(statusEntity.getStatusName());
		if (getIdByStatusName == 0) {
			log.info("StatusController/idByStatusName, 查询失败");
			return "";// 查询界面
		} else {
			log.info("StatusController/idByStatusName, 查询成功");
			return "";
		}
	}

	/*
	 * 修改状态,通过
	 */
	@RequestMapping("/pass")
	public String pass(StatusEntity statusEntity) {
		// 变更状态，到默认的下一个状态
		String passupdateStatus = StatusService.passupdateStatus(statusEntity.getStatusName());
		if (passupdateStatus.equals(Constants.FAILCODE)) {
			log.info("StatusController/pass, 查询失败");
			return "index";
		} else {
			log.info("StatusController/pass, 查询成功");
//	        return passupdateStatus;
			return "dagou";
		}
	}

	/*
	 * 修改状态,不通过
	 */
	@RequestMapping("/noPass")
	public String noPass(StatusEntity statusEntity) {
		String noPassupdateStatus = StatusService.noPassupdateStatus(statusEntity.getStatusName());
		if (noPassupdateStatus.equals(Constants.FAILCODE)) {
			log.info("StatusController/pass, 查询失败");
			return "index";
		} else {
			log.info("StatusController/pass, 查询成功");
//	        return noPassupdateStatus;
			return "dagou";
		}
	}
}
