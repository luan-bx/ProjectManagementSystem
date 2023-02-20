package com.shark.project.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shark.project.entity.PaymentTermEntity;
import com.shark.project.entity.SignEntity;
import com.shark.project.mapper.PaymentTermMapping;
import com.shark.project.mapper.ProjectMapping;
import com.shark.project.mapper.SignMapping;
import com.shark.project.service.SignService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SignService {

	@Autowired
	private SignMapping signMapping;
	@Autowired
	protected ProjectMapping projectMapping;
	@Autowired
	protected PaymentTermMapping paymentTermMapping;
	/**
	 * 插入
	 * @param signEntity
	 * @param proname
	 * @param file
	 * @param req
	 * @return
	 */
	public String insertSign(SignEntity signEntity, String proname, HttpServletRequest req) {
		if(signEntity == null) {
			return Constants.FAILCODE;
//			没有请求信息传来，弹窗“请求失败，请重新请求”，返回请求页
		}
		try {
			signMapping.insert(signEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			req.setAttribute("error", "添加文件失败");
			return Constants.FAILCODE;
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
		if(signEntity == null) {
			return Constants.FAILCODE;
		}
		try {
			signMapping.update(signEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			req.setAttribute(Constants.ERROR, "更新失败");
			return Constants.FAILCODE;
		}
	}
	/**
	 * 通过poid去sign表查id
	 * @param poId
	 * @return
	 */
	public String getSignByPoId(String poId) {
		if(poId == null) {
			return Constants.FAILCODE;
		}
		try {		
			return JSON.toJSONString(signMapping.getSignByPoId(poId));
		} catch (Exception e) {
			log.info("查询失败",e);
			return Constants.FAILCODE;
		}
	}
	
	public String getSignBySignId(String signId) {
		if(signId == null) {
			return Constants.FAILCODE;
		}
		try {		
			return JSON.toJSONString(signMapping.getSignBySignId(signId));
		} catch (Exception e) {
			log.info("查询失败",e);
			return Constants.FAILCODE;
		}
	}
	
	public String getSignByProId(String proId) {
		if(proId == null) {
			return Constants.FAILCODE;
		}
		try {		
			return JSON.toJSONString(signMapping.getSignByProId(proId));
		} catch (Exception e) {
			log.info("查询失败",e);
			return Constants.FAILCODE;
		}
	}
	
	public String getSignByCustomerId(String customerId) {
		if(customerId == null) {
			return Constants.FAILCODE;
		}
		try {		
			return JSON.toJSONString(signMapping.getSignByCustomerId(customerId));
		} catch (Exception e) {
			log.info("查询失败",e);
			return Constants.FAILCODE;
		}
	}
	
	public String getSignByDeliveryDate(Timestamp productCategory) {
		if(productCategory == null) {
			return Constants.FAILCODE;
		}
		try {		
			return JSON.toJSONString(signMapping.getSignByDeliveryDate(productCategory));
		} catch (Exception e) {
			log.info("查询失败",e);
			return Constants.FAILCODE;
		}
	}
	/**
	 * 返回付款条件选项
	 * 
	 * @return
	 */
	public List<PaymentTermEntity> getPaymentTerm() {
		return paymentTermMapping.getAll();
	}
}
