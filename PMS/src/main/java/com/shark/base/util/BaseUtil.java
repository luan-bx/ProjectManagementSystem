package com.shark.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.shark.base.dto.ProjectDto;
import com.shark.base.mapper.ProjectDtoMapping;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.mapper.ProjectMapping;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseUtil {
	
	@Autowired
	protected ProjectDtoMapping projectDtoMapping;
	@Autowired
	protected ProjectMapping projectMapping;

	/**
	 * 跳转首页，封装的方法
	 * @param req
	 * @return
	 */
	protected String toIndex(HttpServletRequest req, HttpServletResponse response) {
		
		List<ProjectEntity> allProject = projectMapping.getAll();
				
		List<ProjectDto> pending = new ArrayList<ProjectDto>();
		List<ProjectDto> processing = new ArrayList<ProjectDto>();
		List<ProjectDto> finished = new ArrayList<ProjectDto>();
		for (ProjectEntity pro : allProject) {
			if (pro.getStatusId() < 3) {
				pending.add(projectDtoMapping.getDtoById(pro.getId()));
			} else if (pro.getStatusId() < 7) {
				processing.add(projectDtoMapping.getDtoById(pro.getId()));
			} else {
				finished.add(projectDtoMapping.getDtoById(pro.getId()));
			}
		}
		req.setAttribute("pending", pending);
		log.info("BaseController/pms, pending:{}", JSON.toJSONString(pending));
		req.setAttribute("processing", processing);
		log.info("BaseController/pms, processing:{}", JSON.toJSONString(processing));
		req.setAttribute("finished", finished);
		log.info("BaseController/pms, finished:{}", JSON.toJSONString(finished));
		return Constants.INDEX;
	}
	
}
