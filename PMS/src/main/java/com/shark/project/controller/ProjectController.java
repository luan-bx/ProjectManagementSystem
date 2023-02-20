package com.shark.project.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.shark.backendSystem.entity.CompanyNameEntity;
import com.shark.base.dto.ProjectDto;
import com.shark.base.mapper.ProjectDtoMapping;
import com.shark.project.controller.design.DesignController;
import com.shark.project.controller.design.DesignElectricalController;
import com.shark.project.controller.design.DesignMechancisController;
import com.shark.project.controller.design.DesignSoftwareController;
import com.shark.project.controller.product.ProductOutsourceController;
import com.shark.project.controller.product.ProductProcessController;
import com.shark.project.controller.product.ProductPurchaseController;
import com.shark.project.entity.AssemblingEntity;
import com.shark.project.entity.PaymentTermEntity;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.RequestEntity;
import com.shark.project.entity.SignEntity;
import com.shark.project.entity.StatusEntity;
import com.shark.project.entity.design.DesignElectricalEntity;
import com.shark.project.entity.design.DesignEntity;
import com.shark.project.entity.design.DesignMechancisEntity;
import com.shark.project.entity.design.DesignSoftwareEntity;
import com.shark.project.entity.design.EleEngineerEntity;
import com.shark.project.entity.design.MecEngineerEntity;
import com.shark.project.entity.design.SofEngineerEntity;
import com.shark.project.entity.product.ProductEntity;
import com.shark.project.entity.product.ProductOutsourceEntity;
import com.shark.project.entity.product.ProductProcessEntity;
import com.shark.project.entity.product.ProductPurchaseEntity;
import com.shark.project.mapper.ProjectMapping;
import com.shark.project.mapper.StatusMapping;
import com.shark.project.mapper.design.DesignMapping;
import com.shark.project.mapper.product.ProductOutsourceMapping;
import com.shark.project.mapper.product.ProductProcessMapping;
import com.shark.project.mapper.product.ProductPurchaseMapping;
import com.shark.project.service.ProjectService;
import com.shark.project.service.RequestService;
import com.shark.project.service.SignService;
import com.shark.project.service.StatusService;
import com.shark.project.service.design.DesignElectricalService;
import com.shark.project.service.design.DesignMechancisService;
import com.shark.project.service.design.DesignService;
import com.shark.project.service.design.DesignSoftwareService;
import com.shark.project.service.design.StaffListService;
import com.shark.project.service.product.ProductOutsourceService;
import com.shark.users.entity.DepartmentEntity;
import com.shark.util.Constants;
import com.shark.util.SendSms;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller

public class ProjectController {

	@Autowired
	protected ProjectService projectService;

	@Autowired
	protected StatusService statusService;
	@Autowired
	protected ProjectMapping projectMapping;
	@Autowired
	protected ProjectDtoMapping projectDtoMapping;
	@Autowired
	private RequestController requestController;
	@Autowired
	private SignController signController;
	@Autowired
	private DesignController designController;
	@Autowired
	private DesignElectricalController designElectricalController;
	@Autowired
	private DesignMechancisController designMechancisController;
	@Autowired
	private DesignSoftwareController designSoftwareController;
	@Autowired
	private ProductOutsourceController productOutsourceController;
	@Autowired
	private ProductProcessController productProcessController;
	@Autowired
	private ProductPurchaseController productPurchaseController;
	@Autowired
	private AssemblingController assemblingController;
	@Autowired
	private RequestService requestService;
	@Autowired
	private DesignMechancisService designMechancisService;
	@Autowired
	private DesignSoftwareService designSoftwareService;
	@Autowired
	private DesignElectricalService designElectricalService;
	@Autowired
	private ProductOutsourceService productOutsourceService;
	@Autowired
	private SignService signService;
	@Autowired
	private StaffListService staffListService;
	@Autowired
	protected DesignMapping designMapping;
	@Autowired
	protected DesignService designService;
	@Autowired
	protected ProductOutsourceMapping productOutsourceMapping;
	@Autowired
	protected ProductProcessMapping productProcessMapping;
	@Autowired
	protected ProductPurchaseMapping productPurchaseMapping;


	
	/**
	 * 1、首页中点击按钮 （统一用 “XXXWeb”） // Service层根据projectId查projectentity
	 * 2、进入对应页面后，调用提交表单方法 （与之前写的保持一致） //状态变更 // projectService里调用其他Controller层
	 * 3、侧边栏页面跳转（动态）（统一用“XXXProjectWeb”） 4、修改-方法：（1）页面跳转——“XXXUpdateWeb”
	 * （2）提交更新——“XXXUpdate”
	 */

	
	/**
	 * 侧边栏_首页——待管理员审核身份
	 * 
	 * @return
	 */
	@RequestMapping("/waitWeb")
	public String waitWeb(HttpServletRequest req, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.POSTID, req.getParameter(Constants.POSTID));
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		return Constants.WAIT;
	}

	/**
	 * 项目新建 （无首页中点击按钮）
	 * 
	 * @return
	 */

	/**
	 * 2.处理逻辑
	 * 
	 * @param requestEntity
	 * @param file
	 * @param req
	 * @return
	 */
	@RequestMapping("/request")
	public String request(RequestEntity requestEntity, CompanyNameEntity companyNameEntity, 
			@RequestParam(value = "file", required = false) List<MultipartFile> file, HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		/*
		 * 做pro_request表插入
		 */
		String request = requestController.request(requestEntity, file, req); // requestController注解改为了@Componenet,暂时不跳转首页
		if (!request.equals(Constants.SUCCESS)) {
			log.info("ProjectController/request, 项目新建失败");
			req.setAttribute(Constants.ERROR, "项目新建失败");
			req.setAttribute(Constants.NEWENTITY, requestEntity);
			return toNewHtml(req);
		}
		String requestSubmit = projectService.requestSubmit(requestEntity, req);
		if (requestSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/requestSubmit, 项目新建失败");
			req.setAttribute(Constants.ERROR, "项目新建失败");
			req.setAttribute(Constants.NEWENTITY, requestEntity);
			return toNewHtml(req);
		}
		log.info("ProjectController/request, 项目新建成功");
		// qh 20220412, 这里需要前端加上postId字段，这里相应带上
		return toSuccessHtml(req);
	}

	/**
	 * 3.侧边栏
	 * 
	 * @return
	 */
	@RequestMapping("/newProjectWeb")
	public String newProjectWeb(HttpServletRequest req, CompanyNameEntity companyNameEntity) {
		RequestEntity requestEntity = new RequestEntity();
		req.setAttribute(Constants.NEWENTITY, requestEntity);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		return toNewHtml(req);
	}

	public String toNewHtml(HttpServletRequest req) {
		// qh 20220413
		req.setAttribute(Constants.POSTID, req.getParameter(Constants.POSTID));
		return Constants.NEW;
	}

	/**
	 * 项目评审
	 */
	/**
	 * 1.首页点击按钮
	 * 
	 * @param proname
	 * @param req
	 * @return
	 */
	@RequestMapping("/reviewWeb")
	public String rewiewWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		ProjectDto projectDto = projectService.basicInformation(proname);
		String description = projectService.getDescription(proname);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.DESCRIPTION, description);	
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		return Constants.REVIEW; // 插入页面
	}

	/**
	 * 2.进行评审
	 * 
	 * @param submitPass
	 * @param proname
	 * @param file
	 * @param req
	 * @return
	 */
	@RequestMapping("/review")
	public String review(String submitPass, String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		// 项目通过——"项目签订中"，不通过——"项目请求中"
		String reviewSubmit = projectService.reviewSubmit(submitPass, proname, req);
		if (reviewSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/review, 项目评审失败");
			req.setAttribute(Constants.ERROR, "项目评审失败");
			return rewiewWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/review, 项目评审成功");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 3.侧边栏页面跳转
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/reviewProjectWeb")
	public String reviewProjectWeb(HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {

		List<ProjectDto> review = new ArrayList<ProjectDto>();
		List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[1]);
		for (int id : projectIdListBystatus) {
			review.add(projectDtoMapping.getDtoById(id));
		}
		req.setAttribute(Constants.REVIEWING, review);
		log.info("ProjectController/reviewProjectWeb, review:{}", JSON.toJSON(review));
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		return Constants.REVIEWCHECK;
	}

	/**
	 * 4.详情
	 * 
	 * @param requestEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/reviewDetail")
	public String reviewDetail(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String revDetail = projectService.revDetail(proname, req);
		if (revDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/reviewDetail, 评审详情查询失败");
			req.setAttribute(Constants.ERROR, "评审详情查询失败");
			return reviewProjectWeb(req, postId, companyNameEntity);
		}
		RequestEntity requestDetail = JSON.parseObject(revDetail, RequestEntity.class);
		// qh 20220413, 这里文件部分，只穿文件名，不要传递整个存储路径,
		if (requestDetail != null) {
			requestDetail.setDesignUrl(getFileName(requestDetail.getDesignUrl()));
			requestDetail.setQuotationUrl(getFileName(requestDetail.getQuotationUrl()));
			requestDetail.setSowUrl(getFileName(requestDetail.getSowUrl()));
		}
		req.setAttribute(Constants.NEWENTITY, requestDetail);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.REVIEWDETAIL;
	}

	// 文件下载
	@RequestMapping("/reviewDownloadFile")
	public void reviewDownloadFile(String proname, String fileName, HttpServletResponse resp) {
		requestService.downloadFile(proname, fileName, resp);
	}

	

	/**
	 * 项目签订
	 */
	/**
	 * 1.首页点击按钮
	 * 
	 * @param proname
	 * @param req
	 * @return
	 */
	@RequestMapping("/signWeb")
	public String signWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		try {
			req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
			ProjectDto projectDto = projectService.basicInformation(proname);
			req.setAttribute(Constants.INFORMATION, projectDto);
			// 信息默认值
			SignEntity signEntity = new SignEntity();
			req.setAttribute(Constants.SIGNENTITY, signEntity);
			// 付款条件的下拉框：签订人员和高级管理员
			List<PaymentTermEntity> paymentTerm = signService.getPaymentTerm();		
			System.out.println(paymentTerm);
			req.setAttribute(Constants.PAYMENTTREM, paymentTerm);
			// 修改付款条件的下拉框：高级管理员——放到后台管理系统做
		}catch (Exception e) {
			log.info("进入签订失败", e);
			return Constants.FAILCODE;
		}
		
		req.setAttribute(Constants.POSTID, postId);
		return Constants.SIGN; // 插入页面
	}

	/**
	 * 2.调用方法
	 * 
	 * @param signEntity
	 * @param proname
	 * @param file
	 * @param req
	 * @return
	 */
	@RequestMapping("/sign")
	public String sign(SignEntity signEntity, String proname, HttpServletRequest req,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		/*
		 * 做pro_sign表插入
		 */
		String sign = signController.sign(signEntity, proname, req);
		if (!sign.equals(Constants.SUCCESS)) {
			log.info("ProjectController/sign, 项目签订失败");
			req.setAttribute(Constants.ERROR, "项目签订失败");
			return signWeb(proname, req, postId, companyNameEntity);
		}
		String signSubmit = projectService.signSubmit(signEntity, proname, req);
		if (signSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/signSubmit, 项目签订失败");
			req.setAttribute(Constants.ERROR, "项目签订失败");
			return signWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/sign, 项目签订成功");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 3.侧边栏页面跳转
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/signProjectWeb")
	public String signProjectWeb(HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		List<ProjectDto> sign = new ArrayList<ProjectDto>();
		List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[2]);
		for (int id : projectIdListBystatus) {
			sign.add(projectDtoMapping.getDtoById(id));
		}
		req.setAttribute(Constants.SIGNING, sign);
		log.info("ProjectController/signProjectWeb, sign:{}", JSON.toJSON(sign));
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		return Constants.SIGNCHECK;
	}

	/**
	 * 4.详情
	 * 
	 * @param requestEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/signDetail")
	public String signDetail(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String sigDetail = projectService.sigDetail(proname, req);
		if (sigDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/signDetail, 签订详情查询失败");
			req.setAttribute(Constants.ERROR, "签订详情查询失败");
			return signProjectWeb(req, postId, companyNameEntity);
		}
		SignEntity signEntity = JSON.parseObject(sigDetail, SignEntity.class);
		req.setAttribute(Constants.SIGNENTITY, signEntity);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.SIGNDETAIL;
	}

	/**
	 * 项目设计
	 */
	// 侧边栏页面跳转（只需一个）
	@RequestMapping("/designProjectWeb")
	public String designProjectWeb(HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {

		List<ProjectDto> design = new ArrayList<ProjectDto>();
		List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[3]);
		for (int id : projectIdListBystatus) {
			design.add(projectDtoMapping.getDtoById(id));
		}
		req.setAttribute(Constants.DESIGNING, design);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		log.info("ProjectController/designProjectWeb, design:{}", JSON.toJSON(design));
		return Constants.DESIGNCHECK;
	}

	/**
	 * 一、基本信息
	 */
	// 1.check页面点击按钮
	@RequestMapping("/designWeb")
	public String designWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		// 基本信息
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		DesignEntity flag = designService.getDesignEntityByPoId(idInformation.getPoId());
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		  if(flag==null) {

		// 信息默认值
		DesignEntity designEntity = new DesignEntity();
		req.setAttribute(Constants.DESIGNENTITY, designEntity);
		// 全部 eleEngineer的下拉框
		List<String> eleEngineer = staffListService.getEleEngineer();
		req.setAttribute(Constants.ELEENGINEER, eleEngineer);
		// 全部 sofEngineer的下拉框
		List<String> sofEngineer = staffListService.getSofEngineer();
		req.setAttribute(Constants.SOFENGINEER, sofEngineer);
		// 全部 MecEngineer的下拉框
		List<String> MecEngineer = staffListService.getMecEngineer();
		req.setAttribute(Constants.MECENGINEER, MecEngineer);
		// 无任务 eleEngineer的下拉框
		List<String> eleEngineerBusy = staffListService.getEleEngineerNoBusy();
		req.setAttribute(Constants.ELEENGINEERNoBUSY, eleEngineerBusy);
		// 无任务 sofEngineer的下拉框
		List<String> sofEngineerBusy = staffListService.getSofEngineerNoBusy();
		req.setAttribute(Constants.SOFENGINEERNoBUSY, sofEngineerBusy);
		// 无任务 MecEngineer的下拉框
		List<String> MecEngineerBusy = staffListService.getMecEngineerNoBusy();
		req.setAttribute(Constants.MECENGINEERNoBUSY, MecEngineerBusy);
				
				
		// 修改人员名单的下拉框：高级管理员——放到后台管理系统做
		
		return Constants.DESIGN; // 插入页面
		  }
		  else {
		   req.setAttribute("designEntity",flag); 
		   return Constants.REDESIGN;
		  }

	}
	
	//第二次进入
	@RequestMapping("/reDesign")
	public String reDesign(String mecEndDate, String eleEndDate, String sofEndDate, String poId, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity, String proname) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String reDesign = projectService.reDesignSubmit(mecEndDate, eleEndDate, sofEndDate, poId);
		if (reDesign.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designSubmit, 插入设计实际完成时间失败");
			req.setAttribute(Constants.ERROR, "插入设计实际完成时间失败");
			req.setAttribute(Constants.POSTID, postId);
			return designWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController.reDesign:插入设计实际完成时间成功");
		return toSuccessHtml(req);
	}
	
	
	// 2.调用方法
	@RequestMapping("/design")
	public String design(DesignEntity designEntity, String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);

		String design = designController.design(designEntity, req);
		if (!design.equals(Constants.SUCCESS)) {
			log.info("ProjectController/design, 项目设计失败");
			req.setAttribute(Constants.ERROR, "项目设计失败");
			return designWeb(proname, req, postId, companyNameEntity);
		}
		String signSubmit = projectService.designSubmit(designEntity, proname, req);
		if (signSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designSubmit, 项目设计失败");
			req.setAttribute(Constants.ERROR, "项目设计失败");
			req.setAttribute(Constants.POSTID, postId);
			return designWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/design, 项目设计-基本信息成功");
		return toSuccessHtml(req);
	}

	/**
	 * 4.详情
	 * 
	 * @param requestEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/designDetail")
	public String designDetail(String proname, HttpServletRequest req, HttpServletResponse resp, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String desDetail = projectService.desDetail(proname, req);
		if (desDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/desDetail, 总设计详情查询失败");
			req.setAttribute(Constants.ERROR, "总设计详情查询失败");
			return designProjectWeb(req, postId, companyNameEntity);
		}
		DesignEntity designEntity = JSON.parseObject(desDetail, DesignEntity.class);
		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.DESIGNENTITY, designEntity);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.DESIGNDETAIL;
	}

	/**
	 * 二、电器
	 */
	// 1.check页面点击按钮
	@RequestMapping("/designElectricalWeb")
	public String designElectricalWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//20220826-thg,加入流程管理
		String eleField = projectService.getEleFieldEntity(req);
		if(eleField.equals(Constants.FAILCODE)) {
			log.info("projectController/designElectricalWeb:电气设计流程查询失败");
			req.setAttribute(Constants.ERROR, "电气设计流程查询失败");
		}
		// req.set("isexit", xxx)
		return Constants.ELEDESIGN; // 插入页面
	}

	// 2.调用方法
	@RequestMapping("/designElectrical")
	public String designElectrical(DesignElectricalEntity designElectricalEntity, String proname,
			@RequestParam(value = "file", required = false) List<MultipartFile> file, HttpServletRequest req,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//绑定项目名字
		designElectricalEntity.setProjectName(proname);
		String designElectrical = designElectricalController.designElectrical(designElectricalEntity, proname, file,
				req);
		if (!designElectrical.equals(Constants.SUCCESS)) {
			log.info("ProjectController/designElectrical, 项目设计-电器失败");
			req.setAttribute(Constants.ERROR, "项目设计-电器失败");
			return designElectricalWeb(proname, req, postId, companyNameEntity);
		}
		String designElectricalSubmit = projectService.designElectricalSubmit(designElectricalEntity, proname, req);
		if (designElectricalSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designElectricalSubmit, 项目设计-电器失败");
			req.setAttribute(Constants.ERROR, "项目设计-电器失败");
			return designElectricalWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/designElectrical, 项目设计-电器成功");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 4.详情
	 * 
	 * @param requestEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/eleDesignDetail")
	public String designElectricalDetail(String proname, HttpServletRequest req, HttpServletResponse resp,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String designEleDetail = projectService.designEleDetail(proname, req);
		if (designEleDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designElectricalDetail, 机械设计详情查询失败");
			req.setAttribute(Constants.ERROR, "机械设计详情查询失败");
			return designProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.ELEDESIGNDETAIL;
	}

	// 文件下载
	@RequestMapping("/designElectricalDownloadFile")
	public void designElectricalDownloadFile(String proname, String fileName, HttpServletResponse resp) {
		designElectricalService.downloadFile(proname, fileName, resp);
	}

	/**
	 * 三、机械
	 */
	// 1.check页面点击按钮
	@RequestMapping("/designMechancisWeb")
	public String designMechancisWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//20220826-thg,加入流程管理
		String mecField = projectService.getMecFieldEntity(req);
		if(mecField.equals(Constants.FAILCODE)) {
			log.info("projectController/designElectricalWeb:电气设计流程查询失败");
			req.setAttribute(Constants.ERROR, "电气设计流程查询失败");
		}
		return Constants.MECDESIGN; // 插入页面
	}

	// 2.调用方法
	@RequestMapping("/designMechancis")
	public String designMechancis(DesignMechancisEntity designMechancisEntity, String proname,
			@RequestParam(value = "file", required = false) List<MultipartFile> file, HttpServletRequest req,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//绑定项目名字
		designMechancisEntity.setProjectName(proname);
		String designMechancis = designMechancisController.designMechancis(designMechancisEntity, proname, file, req);
		if (!designMechancis.equals(Constants.SUCCESS)) {
			log.info("ProjectController/designMechancis, 项目设计-机械失败");
			req.setAttribute(Constants.ERROR, "项目设计-机械失败");
			return designMechancisWeb(proname, req, postId, companyNameEntity);
		}
		String designMechancisSubmit = projectService.designMechancisSubmit(designMechancisEntity, proname, req);
		if (designMechancisSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designMechancisSubmit, 项目设计-机械失败");
			req.setAttribute(Constants.ERROR, "项目设计-机械失败");
			return designMechancisWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/designMechancis, 项目设计-机械成功");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 4.详情
	 * 
	 * @param requestEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/mecDesignDetail")
	public String designMechancisDetail(String proname, HttpServletRequest req, HttpServletResponse resp,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String designMecDetail = projectService.designMecDetail(proname, req);
		if (designMecDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designMechancisDetail, 机械设计详情查询失败");
			req.setAttribute(Constants.ERROR, "机械设计详情查询失败");
			return designProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.MECDESIGNDETAIL;
	}

	// 文件下载
	@RequestMapping("/designMechancisDownloadFile")
	public void designMechancisDownloadFile(String proname, String fileName, HttpServletResponse resp) {
		log.info("asdwasdwa" + proname);
		designMechancisService.downloadFile(proname, fileName, resp);
	}

	/**
	 * 四、软件
	 */
	// 1.check页面点击按钮
	@RequestMapping("/designSoftwareWeb")
	public String designSoftwareWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		return Constants.SOFDESIGN; // 插入页面
	}

	// 2.调用方法
	@RequestMapping("/designSoftware")
	public String designSoftware(DesignSoftwareEntity designSoftwareEntity, String proname, String postId, CompanyNameEntity companyNameEntity,
			@RequestParam(value = "file", required = false) List<MultipartFile> file, HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//绑定项目名字
		designSoftwareEntity.setProjectName(proname);
		String designSoftware = designSoftwareController.designSoftware(designSoftwareEntity, proname, file, req);
		if (!designSoftware.equals(Constants.SUCCESS)) {
			log.info("ProjectController/designSoftware, 项目设计-软件失败");
			req.setAttribute(Constants.ERROR, "项目设计-软件失败");
			return designSoftwareWeb(proname, req, postId, companyNameEntity);
		}
		String designSoftwareSubmit = projectService.designSoftwareSubmit(designSoftwareEntity, proname, req);
		if (designSoftwareSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designSoftwareSubmit, 项目设计-软件失败");
			req.setAttribute(Constants.ERROR, "项目设计-软件失败");
			return designSoftwareWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/designSoftware, 项目设计-软件成功");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 4.详情
	 * 
	 * @param requestEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/sofDesignDetail")
	public String sofDesignDetail(String proname, HttpServletRequest req, HttpServletResponse resp, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String softwareDesignDetail = projectService.softwareDesignDetail(proname, req);
		if (softwareDesignDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designMechancisDetail, 机械设计详情查询失败");
			req.setAttribute(Constants.ERROR, "机械设计详情查询失败");
			designProjectWeb(req, postId, companyNameEntity);
		}
		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.SOFDESIGNDETAIL;
	}

	// 文件下载
	@RequestMapping("/designSoftwareDownloadFile")
	public void designSoftwareDownloadFile(String proname, String fileName, HttpServletResponse resp) {
		designSoftwareService.downloadFile(proname, fileName, resp);
	}

	/**
	 * 设计审核人员
	 * 
	 * @param proname
	 * @param req
	 * @param resp
	 * @param postId
	 * @return
	 */
	@RequestMapping("/designAllDetail")
	public String designAllDetail(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String desDetail = projectService.desDetail(proname, req);
		if (desDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/desDetail, 总设计详情查询失败");
			req.setAttribute(Constants.ERROR, "总设计详情查询失败");
			return designProjectWeb(req, postId, companyNameEntity);
		}
		DesignEntity designEntity = JSON.parseObject(desDetail, DesignEntity.class);
		req.setAttribute(Constants.DESIGNENTITY, designEntity);
		String designEleDetail = projectService.designEleDetail(proname, req);
		if (designEleDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designElectricalDetail, 机械设计详情查询失败");
			req.setAttribute(Constants.ERROR, "机械设计详情查询失败");
			return designProjectWeb(req, postId, companyNameEntity);
		}
		String designMecDetail = projectService.designMecDetail(proname, req);
		if (designMecDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designMechancisDetail, 机械设计详情查询失败");
			req.setAttribute(Constants.ERROR, "机械设计详情查询失败");
			return designProjectWeb(req, postId, companyNameEntity);
		}

		String softwareDesignDetail = projectService.softwareDesignDetail(proname, req);
		if (softwareDesignDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designMechancisDetail, 机械设计详情查询失败");
			req.setAttribute(Constants.ERROR, "机械设计详情查询失败");
			return designProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.DESIGNALLDETAIL;
	}

	/**
	 * 审核按钮，进行状态更新
	 */
	@RequestMapping("/designPass")
	public String designPass(String passSubmit, String proname, String postId, CompanyNameEntity companyNameEntity, HttpServletResponse resp,
			HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String designPassSubmit = projectService.designPassSubmit(passSubmit, proname, req);
		if (designPassSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designPass, 项目设计审核失败");
			req.setAttribute(Constants.ERROR, "项目设计审核失败");
			return designAllDetail(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/designPass, 项目设计审核成功");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 项目生产
	 */
	/**
	 * 一、外协
	 */
	// 1.首页点击按钮
	@RequestMapping("/productOutsourceWeb")
	public String productOutsourceWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		ProductOutsourceEntity produceOutsourceEntity = new ProductOutsourceEntity();
		req.setAttribute(Constants.PRODUCTOUTSOURCEENTITY, produceOutsourceEntity);
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		return Constants.PRODUCTOUT; // 插入页面
	}

	// 2.调用方法
	@RequestMapping("/productOutsource")
	public String productOutsource(ProductOutsourceEntity productOutsourceEntity, String proname, String postId, CompanyNameEntity companyNameEntity,
			@RequestParam(value = "file", required = false) List<MultipartFile> file, HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//绑定项目名字
		productOutsourceEntity.setProjectName(proname);
		String productOutsource = productOutsourceController.productOutsource(productOutsourceEntity, proname, file, req);
		if (!productOutsource.equals(Constants.SUCCESS)) {
			log.info("ProjectController/productOutsource, 项目生产-外协失败");
			req.setAttribute(Constants.ERROR, "项目生产-外协失败");
			return productOutsourceWeb(proname, req, postId, companyNameEntity);
		}
		String productOutsourceSubmit = projectService.productOutsourceSubmit(productOutsourceEntity, proname, req);
		if (productOutsourceSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productOutsourceSubmit, 项目生产-外协失败");
			req.setAttribute(Constants.ERROR, "项目生产-外协失败");
			return productOutsourceWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/productOutsource, 项目生产-外协成功");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}
	 
	 
	/**
	 * 4.详情
	 * 
	 * @param requestEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/productOutsourceDetail")
	public String productOutsourceDetail(String proname, HttpServletRequest req, HttpServletResponse resp,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String productOutDetail = projectService.productOutDetail(proname, req);
		if (productOutDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productOutDetail, 外协生产详情查询失败");
			req.setAttribute(Constants.ERROR, "外协生产详情查询失败");
			return productProjectWeb(req, postId, companyNameEntity);
		}
		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.PRODUCTOUTDETAIL;
		
		
	}

	// 文件下载
	@RequestMapping("/productOutDownloadFile")
	public void productOutDownloadFile(String proname, String fileName, HttpServletResponse resp) {
		productOutsourceService.downloadFile(proname, fileName, resp);
	}

	/**
	 * 二、零件加工
	 */
	// 1.首页点击按钮
	@RequestMapping("/productProcessWeb")
	public String productProcessWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		ProductProcessEntity productProcessEntity = new ProductProcessEntity();
		req.setAttribute(Constants.PRODUCTPROCESSENTITY, productProcessEntity);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		return Constants.PRODUCTPROCESS; // 插入页面
	}

	// 2.调用方法
	@RequestMapping("/productProcess")
	public String productProcess(ProductProcessEntity productProcessEntity, String proname, HttpServletRequest req,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//绑定项目名字
		productProcessEntity.setProjectName(proname);
		String productProcess = productProcessController.productProcess(productProcessEntity, req);
		if (!productProcess.equals(Constants.SUCCESS)) {
			log.info("ProjectController/productProcess, 项目生产-加工失败");
			req.setAttribute(Constants.ERROR, "项目生产-加工失败");
			return productProcessWeb(proname, req, postId, companyNameEntity);
		}
		String productProcessSubmit = projectService.productProcessSubmit(productProcessEntity, proname, req);
		if (productProcessSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productProcessSubmit, 项目生产-加工失败");
			req.setAttribute(Constants.ERROR, "项目生产-加工失败");
			return productProcessWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/productProcess, 项目生产-加工成功");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 4.详情
	 * 
	 * @param requestEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/productProcessDetail")
	public String productProcessDetail(String proname, HttpServletRequest req, HttpServletResponse resp,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String productProDetail = projectService.productProDetail(proname, req);
		if (productProDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productProDetail, 零件加工生产详情查询失败");
			req.setAttribute(Constants.ERROR, "零件加工生产详情查询失败");
			return productProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.POSTID, postId);
		return Constants.PRODUCTPROCESSDETAIL;
	}

	/**
	 * 三、采购
	 */
	// 1.首页点击按钮
	@RequestMapping("/productPurchaseWeb")
	public String productPurchaseWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		ProductPurchaseEntity productPurchaseEntity = new ProductPurchaseEntity();
		req.setAttribute(Constants.PRODUCTPURCHASEENTITY, productPurchaseEntity);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.PRODUCTPURCHASE; // 插入页面
	}

	// 2.调用方法
	@RequestMapping("/productPurchase")
	public String productPurchase(ProductPurchaseEntity productPurchaseEntity, String proname, HttpServletRequest req,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//绑定项目名字
		productPurchaseEntity.setProjectName(proname);
		String productPurchase = productPurchaseController.productPurchase(productPurchaseEntity, req);
		if (!productPurchase.equals(Constants.SUCCESS)) {
			log.info("ProjectController/productPurchase, 项目生产-采购失败");
			req.setAttribute(Constants.ERROR, "项目生产-采购失败");
			return productPurchaseWeb(proname, req, postId, companyNameEntity);
		}
		String productPurchaseSubmit = projectService.productPurchaseSubmit(productPurchaseEntity, proname, req);
		if (productPurchaseSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productPurchaseSubmit, 项目生产-采购失败");
			req.setAttribute(Constants.ERROR, "项目生产-采购失败");
			return productPurchaseWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/productPurchase, 项目生产-采购成功");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 4.详情
	 * 
	 * @param requestEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/productPurchaseDetail")
	public String productPurchaseDetail(String proname, HttpServletRequest req, HttpServletResponse resp,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String productPurDetail = projectService.productPurDetail(proname, req);
		if (productPurDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productPurDetail, 采购生产详情查询失败");
			req.setAttribute(Constants.ERROR, "采购生产详情查询失败");
			return productProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.POSTID, postId);
		return Constants.PRODUCTPURCHASEDETAIL;
	}

	// 3.侧边栏页面跳转（一个就可以）
	@RequestMapping("/productProjectWeb")
	public String productProjectWeb(HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);

		List<ProjectDto> product = new ArrayList<ProjectDto>();
		List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[4]);
		for (int id : projectIdListBystatus) {
			product.add(projectDtoMapping.getDtoById(id));
		}
		req.setAttribute("producting", product);
		log.info("ProjectController/productProjectWeb, product:{}", JSON.toJSON(product));
		req.setAttribute(Constants.POSTID, postId);
		return Constants.PRODUCTCHECK;
	}

	/**
	 * 生产审核人员
	 * 
	 * @param proname
	 * @param req
	 * @param resp
	 * @param postId
	 * @return
	 */
	@RequestMapping("/productAllDetail")
	public String productAllDetail(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		
		String productOutDetail = projectService.productOutDetail(proname, req);
		if (productOutDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productOutDetail, 外协生产详情查询失败");
			return productProjectWeb(req, postId, companyNameEntity);
		}

		String productProDetail = projectService.productProDetail(proname, req);
		if (productProDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productProDetail, 零件加工生产详情查询失败");
			return productProjectWeb(req, postId, companyNameEntity);
		}
		String productPurDetail = projectService.productPurDetail(proname, req);
		if (productPurDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productPurDetail, 采购生产详情查询失败");
			return productProjectWeb(req, postId, companyNameEntity);
		}
	
		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.PRODUCTALLDETAIL;
	}

	/**
	 * 审核按钮，进行状态更新
	 */
	@RequestMapping("/productPass")
	public String productPass(String passSubmit, String proname, String postId, CompanyNameEntity companyNameEntity, HttpServletResponse resp,
			HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String productPassSubmit = projectService.productPassSubmit(passSubmit, proname, req);
		if (productPassSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productPass, 项目生产审核失败");
			req.setAttribute(Constants.ERROR, "项目生产审核失败");
			return productAllDetail(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/productPass, 项目生产审核成功");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 项目装配
	 */
	// 1.首页点击按钮
	@RequestMapping("/assembleWeb")
	public String assemblingWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		AssemblingEntity assemblingEntity = new AssemblingEntity();
		req.setAttribute(Constants.ASSEMBLEENTITY, assemblingEntity);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.PRONAME, proname);
		AssemblingEntity flag = projectService.assembleIsSubmitted(idInformation.getPoId());
		if(flag==null) {
			return Constants.ASSEMBLE; // 插入页面
		}
		else {
			req.setAttribute("assembleEntity", flag);
			return Constants.REASSEMBLE;
		}
	}

	// 2.调用方法
	@RequestMapping("/assembling")
	public String assembling(AssemblingEntity assemblingEntity, String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//绑定项目名字
		assemblingEntity.setProjectName(proname);
		String assembling = assemblingController.assembling(assemblingEntity);
		if (!assembling.equals(Constants.SUCCESS)) {
			log.info("ProjectController/assembling, 项目装配失败");
			req.setAttribute(Constants.ERROR, "项目装配失败");
			return assemblingWeb(proname, req, postId, companyNameEntity);
		}
		String designSoftwareSubmit = projectService.assemblingSubmit(assemblingEntity, proname, req);
		if (designSoftwareSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/assemblingSubmit, 项目装配失败");
			req.setAttribute(Constants.ERROR, "项目装配失败");
			return assemblingWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/assembling, 项目装配成功");
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.PRONAME, proname);
		return toSuccessHtml(req);
	}

	// 3.侧边栏页面跳转
	@RequestMapping("/assembleProjectWeb")
	public String assembleProjectWeb(HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);

		List<ProjectDto> assemble = new ArrayList<ProjectDto>();
		List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[5]);
		for (int id : projectIdListBystatus) {
			assemble.add(projectDtoMapping.getDtoById(id));
		}
		req.setAttribute(Constants.ASSEMBLING, assemble);
		log.info("ProjectController/assembleProjectWeb, assemble:{}", JSON.toJSON(assemble));
		req.setAttribute(Constants.POSTID, postId);
		return Constants.ASSEMBLECHECK;
	}

	/**
	 * 4.详情
	 * 
	 * @param requestEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/assembleDetail")
	public String assemblingDetail(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String assembleDetail = projectService.assembleDetail(proname, req);
		if (assembleDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/assembleDetail, 装配详情查询失败");
			req.setAttribute(Constants.ERROR, "装配详情查询失败");
			return assembleProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.PRONAME, proname);
		return Constants.ASSEMBLEDETAIL;
	}
	/**
	 * 审核按钮，进行状态更新
	 */
	@RequestMapping("/assemblePass")
	public String assemblePass(String passSubmit, String proname, String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String assemblePassSubmit = projectService.assemblePassSubmit(passSubmit, proname, req);
		if (assemblePassSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/assemblePass, 项目装配审核失败");
			req.setAttribute(Constants.ERROR, "项目装配审核失败");
			return assemblingDetail(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/assemblePass, 项目装配审核成功");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}
	/*
	 * thg 提交装配实际完成时间
	 */
	@RequestMapping("/reAssembling")
	public String reAssembing(String poId, String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity, String meEndDate, String elEndDate, String soEndDate, String checkEndDate) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String flag = projectService.reAssembingSubmit(poId, meEndDate, elEndDate, soEndDate, checkEndDate);
		req.setAttribute(Constants.POSTID, postId);
		if(flag.equals(Constants.SUCCESS)) {
			log.info("projectController/reAssembilng:提交装配实际时间成功");
			return toSuccessHtml(req);
		}
		else {
			log.info("projectController/reAssembilng:提交装配实际时间失败");
			req.setAttribute(Constants.ERROR, "提交装配实际时间失败");
			return assemblingWeb(proname, req, postId, companyNameEntity);
		}
	}
	
	/**
	 * 管理员-全部详情
	 * 
	 * @param requestEntity
	 * @param req
	 * @return
	 */
	@RequestMapping("/allDetail")
	public String allDetail(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		reviewDetail(proname, req, postId, companyNameEntity);
		signDetail(proname, req, postId, companyNameEntity);
		designAllDetail(proname, req, postId, companyNameEntity);
		productAllDetail(proname, req, postId, companyNameEntity);
		assemblingDetail(proname, req, postId, companyNameEntity);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.PRONAME, proname);
		return Constants.ALLDETAIL;
	}
	/**
	 * qh success打勾页面
	 * 
	 * @param req
	 * @return
	 */
	public String toSuccessHtml(HttpServletRequest req) {
		req.setAttribute(Constants.POSTID, req.getParameter(Constants.POSTID));
		return Constants.SUCCESS;
	}
	
	/**
	 * thg 跳转到404页面
	 */
	@RequestMapping("/404")
	public String to404Html(HttpServletRequest req) {
		return "404";
	}
	
	/**
	 * thg 跳转到短信服务配置页面
	 */
	@RequestMapping("/msgConfig")
	public String toMsgConfigHtml(HttpServletRequest req) {
		return "msgConfig";
	}
	
	@RequestMapping("/sendMsgConfig")
	public String sendMsgConfig(String accessKeyId, String accessKeySecret, String sign, String templateCode, String phone, HttpServletRequest request) {
		if(SendSms.SendMessageCode("123456", phone, accessKeyId, accessKeySecret, sign, templateCode)) {
			SendSms.sendMsgMap.put("AccessKeySecret", accessKeySecret);
			SendSms.sendMsgMap.put("AccessKeyId", accessKeyId);
			SendSms.sendMsgMap.put("sign", sign);
			SendSms.sendMsgMap.put("templateCode", templateCode);
			System.out.println(SendSms.sendMsgMap);
			return Constants.LOGIN;
		}else {
			request.setAttribute("msg", "验证失败！请确认您所填写的信息是否有误或您的阿里云短信服务是否到期！");
			return "msgConfig";
		}
	}
	/**
	 * 拿到文件url的文件名
	 * 
	 * @param path
	 * @return
	 */
	private String getFileName(String path) {
		String[] split = path.split("/");
		return split[split.length - 1]+"/"+split[split.length - 2];
	}

}
