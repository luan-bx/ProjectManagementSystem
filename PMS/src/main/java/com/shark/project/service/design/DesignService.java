package com.shark.project.service.design;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.SignEntity;
import com.shark.project.entity.design.DesignEntity;
import com.shark.project.mapper.ProjectMapping;
import com.shark.project.mapper.design.DesignMapping;
import com.shark.project.service.design.DesignService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DesignService {

	@Autowired
	private DesignMapping designMapping;
	
	@Autowired
	private ProjectMapping projectMapping;
	
	public String insertDesign(DesignEntity designEntity) {
		if(designEntity == null) {
			return Constants.FAILCODE;
//			没有请求信息传来，弹窗“请求失败，请重新请求”，返回请求页
		}
		try {
			designMapping.insert(designEntity);
			log.info("DesignService/insertDesign,数据库请求成功");
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("插入项目设计记录失败", e);
			return Constants.FAILCODE;
		}
	}	
	
	public String getDesignByPoId(String poId) {
		if(poId == null) {
			return Constants.FAILCODE;    
		}
		try {		
			return JSON.toJSONString(designMapping.getDesignByPoId(poId));
		} catch (Exception e) {
			log.info("查询失败");
			return Constants.FAILCODE;
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
		if(designEntity == null) {
			return Constants.FAILCODE;
		}
		try {
			designMapping.updateDesignById(designEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			req.setAttribute(Constants.ERROR, "更新失败");
			return Constants.FAILCODE;
		}
	}
	
	/**
	 * qh
	 * 根据id查记录
	 * @param id
	 * @return
	 */
	public String getDesignById(int id) {
		try {		
			return JSON.toJSONString(designMapping.getDesignById(id));
		} catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	
	public DesignEntity getDesignEntityByPoId(String poId) {
		   return designMapping.getDesignByPoId(poId); 
		 }
}
