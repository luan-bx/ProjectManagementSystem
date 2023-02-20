package com.shark.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.SignEntity;
import com.shark.project.entity.StatusEntity;
import com.shark.project.service.SignService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

//@Controller
@Component
@Slf4j
public class SignController {

	@Autowired
	protected SignService signService;

	/*
	 * 项目签订界面，填好信息，点击“提交”，插入一个签订记录
	 */
//	@RequestMapping("/sign")
	public String sign(SignEntity signEntity, String proname, HttpServletRequest req) {
		String insertSign = signService.insertSign(signEntity, proname, req);
		if (insertSign.equals(Constants.SUCCESSCODE)) {
			log.info("SigntController/sign, 签订记录成功");
			return Constants.SUCCESS;
		} else {
			// 项目请求失败
			log.info("SignController/sign, 签订记录失败");
			return Constants.SIGN;
		}
	}
	/**
	 * 更新
	 * @param signEntity
	 * @param proname
	 * @param file
	 * @param req
	 * @return
	 */
	public String signUpdate(SignEntity signEntity, String proname, HttpServletRequest req) {
		String signUpdate = signService.signUpdate(signEntity, proname, req);
		if (signUpdate.equals(Constants.SUCCESSCODE)) {
			log.info("SigntController/UpdateSign, 签订记录更新成功");
			return Constants.SUCCESS;
		} else {
			// 项目请求失败
			log.info("SignController/UpdateSign, 签订记录更新失败");
			return Constants.SIGN;
		}
	}

	/*
	 * 根据po查询签订记录
	 */
	@RequestMapping("/signByPoId")
	public String signByPoId(SignEntity signEntity, HttpServletRequest req) {

		String getSignByPoId = signService.getSignByPoId(signEntity.getPoId());
		if (getSignByPoId.equals(Constants.FAILCODE)) {
			log.info("SignController/signByPoId, 查询失败");
			return "";// 查询界面
		} else {
			log.info("SignController/signByPoId, 查询成功");
			return getSignByPoId;
		}
	}

	/*
	 * 根据合同号查询签订记录
	 */
	@RequestMapping("/signBySignId")
	public String signBySignId(SignEntity signEntity, HttpServletRequest req) {

		String getSignBySignId = signService.getSignBySignId(signEntity.getSignId());
		if (getSignBySignId.equals(Constants.FAILCODE)) {
			log.info("SignController/signBySignId, 查询失败");
			return "";// 查询界面
		} else {
			log.info("SignController/signBySignId, 查询成功");
			return getSignBySignId;
		}
	}

	/*
	 * 根据公司项目编号查询签订记录
	 */
	@RequestMapping("/signByProId")
	public String signByProId(SignEntity signEntity, HttpServletRequest req) {

		String getSignByProId = signService.getSignByProId(signEntity.getProId());
		if (getSignByProId.equals(Constants.FAILCODE)) {
			log.info("SignController/signByProId, 查询失败");
			return "";// 查询界面
		} else {
			log.info("SignController/signByProId, 查询成功");
			return getSignByProId;
		}
	}

	/*
	 * 根据客户公司代码查询签订记录
	 */
	@RequestMapping("/signByCustomerId")
	public String signByCustomerId(SignEntity signEntity, HttpServletRequest req) {

		String getSignByCustomerId = signService.getSignByCustomerId(signEntity.getCustomerId());
		if (getSignByCustomerId.equals(Constants.FAILCODE)) {
			log.info("SignController/signByCustomerId, 查询失败");
			return "";// 查询界面
		} else {
			log.info("SignController/signByCustomerId, 查询成功");
			return getSignByCustomerId;
		}
	}

	/*
	 * 根据交货日期查询签订记录
	 */
	@RequestMapping("/signByDeliveryDate")
	public String signByDeliveryDate(SignEntity signEntity, HttpServletRequest req) {

		String getSignByDeliveryDate = signService.getSignByDeliveryDate(signEntity.getDeliveryDate());
		if (getSignByDeliveryDate.equals(Constants.FAILCODE)) {
			log.info("SignController/signByDeliveryDate, 查询失败");
			return "";// 查询界面
		} else {
			log.info("SignController/signByDeliveryDate, 查询成功");
			return getSignByDeliveryDate;
		}
	}

}
