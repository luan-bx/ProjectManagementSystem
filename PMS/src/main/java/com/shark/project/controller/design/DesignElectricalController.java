package com.shark.project.controller.design;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shark.project.controller.StatusController;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.StatusEntity;
import com.shark.project.entity.design.DesignElectricalEntity;
import com.shark.project.service.design.DesignElectricalService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;
//@Controller
@Component
@Slf4j
public class DesignElectricalController {

	
	@Autowired
	protected DesignElectricalService DesignElectricalService;

	
	/*
	 * 插入一条电气设计记录
	 */
//	@RequestMapping("/designElectrical")
	public String designElectrical(DesignElectricalEntity designElectricalEntity, String proname,
			@RequestParam(value = "file", required = false) List<MultipartFile> file, HttpServletRequest req) {
		if (file == null) {
			log.info("designElectrical , file为空");
			return "ele-design";
		}
		String insertDesignElectrical = DesignElectricalService.insertDesignElectrical(designElectricalEntity, proname,
				file, req);
		if (insertDesignElectrical.equals(Constants.SUCCESSCODE)) {
			log.info("DesignElectricalController/designElectrical, 项目电气设计记录请求成功");
			return Constants.SUCCESS;
		} else {
			// 项目电气设计记录请求失败
			log.info("DesignElectricalController/designElectrical, 项目电气设计记录请求失败");
			return "ele-design";
		}
	}
}
