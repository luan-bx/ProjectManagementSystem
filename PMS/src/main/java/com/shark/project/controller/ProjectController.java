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
	 * 1???????????????????????? ???????????? ???XXXWeb?????? // Service?????????projectId???projectentity
	 * 2??????????????????????????????????????????????????? ????????????????????????????????? //???????????? // projectService???????????????Controller???
	 * 3???????????????????????????????????????????????????XXXProjectWeb?????? 4?????????-????????????1????????????????????????XXXUpdateWeb???
	 * ???2????????????????????????XXXUpdate???
	 */

	
	/**
	 * ?????????_????????????????????????????????????
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
	 * ???????????? ??????????????????????????????
	 * 
	 * @return
	 */

	/**
	 * 2.????????????
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
		 * ???pro_request?????????
		 */
		String request = requestController.request(requestEntity, file, req); // requestController???????????????@Componenet,?????????????????????
		if (!request.equals(Constants.SUCCESS)) {
			log.info("ProjectController/request, ??????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????");
			req.setAttribute(Constants.NEWENTITY, requestEntity);
			return toNewHtml(req);
		}
		String requestSubmit = projectService.requestSubmit(requestEntity, req);
		if (requestSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/requestSubmit, ??????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????");
			req.setAttribute(Constants.NEWENTITY, requestEntity);
			return toNewHtml(req);
		}
		log.info("ProjectController/request, ??????????????????");
		// qh 20220412, ????????????????????????postId???????????????????????????
		return toSuccessHtml(req);
	}

	/**
	 * 3.?????????
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
	 * ????????????
	 */
	/**
	 * 1.??????????????????
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
		return Constants.REVIEW; // ????????????
	}

	/**
	 * 2.????????????
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
		// ??????????????????"???????????????"??????????????????"???????????????"
		String reviewSubmit = projectService.reviewSubmit(submitPass, proname, req);
		if (reviewSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/review, ??????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????");
			return rewiewWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/review, ??????????????????");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 3.?????????????????????
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
	 * 4.??????
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
			log.info("ProjectController/reviewDetail, ????????????????????????");
			req.setAttribute(Constants.ERROR, "????????????????????????");
			return reviewProjectWeb(req, postId, companyNameEntity);
		}
		RequestEntity requestDetail = JSON.parseObject(revDetail, RequestEntity.class);
		// qh 20220413, ?????????????????????????????????????????????????????????????????????,
		if (requestDetail != null) {
			requestDetail.setDesignUrl(getFileName(requestDetail.getDesignUrl()));
			requestDetail.setQuotationUrl(getFileName(requestDetail.getQuotationUrl()));
			requestDetail.setSowUrl(getFileName(requestDetail.getSowUrl()));
		}
		req.setAttribute(Constants.NEWENTITY, requestDetail);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.REVIEWDETAIL;
	}

	// ????????????
	@RequestMapping("/reviewDownloadFile")
	public void reviewDownloadFile(String proname, String fileName, HttpServletResponse resp) {
		requestService.downloadFile(proname, fileName, resp);
	}

	

	/**
	 * ????????????
	 */
	/**
	 * 1.??????????????????
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
			// ???????????????
			SignEntity signEntity = new SignEntity();
			req.setAttribute(Constants.SIGNENTITY, signEntity);
			// ?????????????????????????????????????????????????????????
			List<PaymentTermEntity> paymentTerm = signService.getPaymentTerm();		
			System.out.println(paymentTerm);
			req.setAttribute(Constants.PAYMENTTREM, paymentTerm);
			// ?????????????????????????????????????????????????????????????????????????????????
		}catch (Exception e) {
			log.info("??????????????????", e);
			return Constants.FAILCODE;
		}
		
		req.setAttribute(Constants.POSTID, postId);
		return Constants.SIGN; // ????????????
	}

	/**
	 * 2.????????????
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
		 * ???pro_sign?????????
		 */
		String sign = signController.sign(signEntity, proname, req);
		if (!sign.equals(Constants.SUCCESS)) {
			log.info("ProjectController/sign, ??????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????");
			return signWeb(proname, req, postId, companyNameEntity);
		}
		String signSubmit = projectService.signSubmit(signEntity, proname, req);
		if (signSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/signSubmit, ??????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????");
			return signWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/sign, ??????????????????");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 3.?????????????????????
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
	 * 4.??????
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
			log.info("ProjectController/signDetail, ????????????????????????");
			req.setAttribute(Constants.ERROR, "????????????????????????");
			return signProjectWeb(req, postId, companyNameEntity);
		}
		SignEntity signEntity = JSON.parseObject(sigDetail, SignEntity.class);
		req.setAttribute(Constants.SIGNENTITY, signEntity);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.SIGNDETAIL;
	}

	/**
	 * ????????????
	 */
	// ???????????????????????????????????????
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
	 * ??????????????????
	 */
	// 1.check??????????????????
	@RequestMapping("/designWeb")
	public String designWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		// ????????????
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		DesignEntity flag = designService.getDesignEntityByPoId(idInformation.getPoId());
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		  if(flag==null) {

		// ???????????????
		DesignEntity designEntity = new DesignEntity();
		req.setAttribute(Constants.DESIGNENTITY, designEntity);
		// ?????? eleEngineer????????????
		List<String> eleEngineer = staffListService.getEleEngineer();
		req.setAttribute(Constants.ELEENGINEER, eleEngineer);
		// ?????? sofEngineer????????????
		List<String> sofEngineer = staffListService.getSofEngineer();
		req.setAttribute(Constants.SOFENGINEER, sofEngineer);
		// ?????? MecEngineer????????????
		List<String> MecEngineer = staffListService.getMecEngineer();
		req.setAttribute(Constants.MECENGINEER, MecEngineer);
		// ????????? eleEngineer????????????
		List<String> eleEngineerBusy = staffListService.getEleEngineerNoBusy();
		req.setAttribute(Constants.ELEENGINEERNoBUSY, eleEngineerBusy);
		// ????????? sofEngineer????????????
		List<String> sofEngineerBusy = staffListService.getSofEngineerNoBusy();
		req.setAttribute(Constants.SOFENGINEERNoBUSY, sofEngineerBusy);
		// ????????? MecEngineer????????????
		List<String> MecEngineerBusy = staffListService.getMecEngineerNoBusy();
		req.setAttribute(Constants.MECENGINEERNoBUSY, MecEngineerBusy);
				
				
		// ?????????????????????????????????????????????????????????????????????????????????
		
		return Constants.DESIGN; // ????????????
		  }
		  else {
		   req.setAttribute("designEntity",flag); 
		   return Constants.REDESIGN;
		  }

	}
	
	//???????????????
	@RequestMapping("/reDesign")
	public String reDesign(String mecEndDate, String eleEndDate, String sofEndDate, String poId, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity, String proname) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String reDesign = projectService.reDesignSubmit(mecEndDate, eleEndDate, sofEndDate, poId);
		if (reDesign.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designSubmit, ????????????????????????????????????");
			req.setAttribute(Constants.ERROR, "????????????????????????????????????");
			req.setAttribute(Constants.POSTID, postId);
			return designWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController.reDesign:????????????????????????????????????");
		return toSuccessHtml(req);
	}
	
	
	// 2.????????????
	@RequestMapping("/design")
	public String design(DesignEntity designEntity, String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);

		String design = designController.design(designEntity, req);
		if (!design.equals(Constants.SUCCESS)) {
			log.info("ProjectController/design, ??????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????");
			return designWeb(proname, req, postId, companyNameEntity);
		}
		String signSubmit = projectService.designSubmit(designEntity, proname, req);
		if (signSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designSubmit, ??????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????");
			req.setAttribute(Constants.POSTID, postId);
			return designWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/design, ????????????-??????????????????");
		return toSuccessHtml(req);
	}

	/**
	 * 4.??????
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
			log.info("ProjectController/desDetail, ???????????????????????????");
			req.setAttribute(Constants.ERROR, "???????????????????????????");
			return designProjectWeb(req, postId, companyNameEntity);
		}
		DesignEntity designEntity = JSON.parseObject(desDetail, DesignEntity.class);
		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.DESIGNENTITY, designEntity);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.DESIGNDETAIL;
	}

	/**
	 * ????????????
	 */
	// 1.check??????????????????
	@RequestMapping("/designElectricalWeb")
	public String designElectricalWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//20220826-thg,??????????????????
		String eleField = projectService.getEleFieldEntity(req);
		if(eleField.equals(Constants.FAILCODE)) {
			log.info("projectController/designElectricalWeb:??????????????????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????????????????");
		}
		// req.set("isexit", xxx)
		return Constants.ELEDESIGN; // ????????????
	}

	// 2.????????????
	@RequestMapping("/designElectrical")
	public String designElectrical(DesignElectricalEntity designElectricalEntity, String proname,
			@RequestParam(value = "file", required = false) List<MultipartFile> file, HttpServletRequest req,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//??????????????????
		designElectricalEntity.setProjectName(proname);
		String designElectrical = designElectricalController.designElectrical(designElectricalEntity, proname, file,
				req);
		if (!designElectrical.equals(Constants.SUCCESS)) {
			log.info("ProjectController/designElectrical, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return designElectricalWeb(proname, req, postId, companyNameEntity);
		}
		String designElectricalSubmit = projectService.designElectricalSubmit(designElectricalEntity, proname, req);
		if (designElectricalSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designElectricalSubmit, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return designElectricalWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/designElectrical, ????????????-????????????");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 4.??????
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
			log.info("ProjectController/designElectricalDetail, ??????????????????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????????????????");
			return designProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.ELEDESIGNDETAIL;
	}

	// ????????????
	@RequestMapping("/designElectricalDownloadFile")
	public void designElectricalDownloadFile(String proname, String fileName, HttpServletResponse resp) {
		designElectricalService.downloadFile(proname, fileName, resp);
	}

	/**
	 * ????????????
	 */
	// 1.check??????????????????
	@RequestMapping("/designMechancisWeb")
	public String designMechancisWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//20220826-thg,??????????????????
		String mecField = projectService.getMecFieldEntity(req);
		if(mecField.equals(Constants.FAILCODE)) {
			log.info("projectController/designElectricalWeb:??????????????????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????????????????");
		}
		return Constants.MECDESIGN; // ????????????
	}

	// 2.????????????
	@RequestMapping("/designMechancis")
	public String designMechancis(DesignMechancisEntity designMechancisEntity, String proname,
			@RequestParam(value = "file", required = false) List<MultipartFile> file, HttpServletRequest req,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//??????????????????
		designMechancisEntity.setProjectName(proname);
		String designMechancis = designMechancisController.designMechancis(designMechancisEntity, proname, file, req);
		if (!designMechancis.equals(Constants.SUCCESS)) {
			log.info("ProjectController/designMechancis, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return designMechancisWeb(proname, req, postId, companyNameEntity);
		}
		String designMechancisSubmit = projectService.designMechancisSubmit(designMechancisEntity, proname, req);
		if (designMechancisSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designMechancisSubmit, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return designMechancisWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/designMechancis, ????????????-????????????");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 4.??????
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
			log.info("ProjectController/designMechancisDetail, ??????????????????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????????????????");
			return designProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.MECDESIGNDETAIL;
	}

	// ????????????
	@RequestMapping("/designMechancisDownloadFile")
	public void designMechancisDownloadFile(String proname, String fileName, HttpServletResponse resp) {
		log.info("asdwasdwa" + proname);
		designMechancisService.downloadFile(proname, fileName, resp);
	}

	/**
	 * ????????????
	 */
	// 1.check??????????????????
	@RequestMapping("/designSoftwareWeb")
	public String designSoftwareWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		ProjectDto projectDto = projectService.basicInformation(proname);
		SignEntity idInformation = projectService.IdInformation(proname);
		req.setAttribute(Constants.INFORMATION, projectDto);
		req.setAttribute(Constants.INFORMATIONID, idInformation);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		return Constants.SOFDESIGN; // ????????????
	}

	// 2.????????????
	@RequestMapping("/designSoftware")
	public String designSoftware(DesignSoftwareEntity designSoftwareEntity, String proname, String postId, CompanyNameEntity companyNameEntity,
			@RequestParam(value = "file", required = false) List<MultipartFile> file, HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//??????????????????
		designSoftwareEntity.setProjectName(proname);
		String designSoftware = designSoftwareController.designSoftware(designSoftwareEntity, proname, file, req);
		if (!designSoftware.equals(Constants.SUCCESS)) {
			log.info("ProjectController/designSoftware, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return designSoftwareWeb(proname, req, postId, companyNameEntity);
		}
		String designSoftwareSubmit = projectService.designSoftwareSubmit(designSoftwareEntity, proname, req);
		if (designSoftwareSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designSoftwareSubmit, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return designSoftwareWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/designSoftware, ????????????-????????????");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 4.??????
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
			log.info("ProjectController/designMechancisDetail, ??????????????????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????????????????");
			designProjectWeb(req, postId, companyNameEntity);
		}
		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.SOFDESIGNDETAIL;
	}

	// ????????????
	@RequestMapping("/designSoftwareDownloadFile")
	public void designSoftwareDownloadFile(String proname, String fileName, HttpServletResponse resp) {
		designSoftwareService.downloadFile(proname, fileName, resp);
	}

	/**
	 * ??????????????????
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
			log.info("ProjectController/desDetail, ???????????????????????????");
			req.setAttribute(Constants.ERROR, "???????????????????????????");
			return designProjectWeb(req, postId, companyNameEntity);
		}
		DesignEntity designEntity = JSON.parseObject(desDetail, DesignEntity.class);
		req.setAttribute(Constants.DESIGNENTITY, designEntity);
		String designEleDetail = projectService.designEleDetail(proname, req);
		if (designEleDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designElectricalDetail, ??????????????????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????????????????");
			return designProjectWeb(req, postId, companyNameEntity);
		}
		String designMecDetail = projectService.designMecDetail(proname, req);
		if (designMecDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designMechancisDetail, ??????????????????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????????????????");
			return designProjectWeb(req, postId, companyNameEntity);
		}

		String softwareDesignDetail = projectService.softwareDesignDetail(proname, req);
		if (softwareDesignDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designMechancisDetail, ??????????????????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????????????????");
			return designProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.DESIGNALLDETAIL;
	}

	/**
	 * ?????????????????????????????????
	 */
	@RequestMapping("/designPass")
	public String designPass(String passSubmit, String proname, String postId, CompanyNameEntity companyNameEntity, HttpServletResponse resp,
			HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String designPassSubmit = projectService.designPassSubmit(passSubmit, proname, req);
		if (designPassSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/designPass, ????????????????????????");
			req.setAttribute(Constants.ERROR, "????????????????????????");
			return designAllDetail(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/designPass, ????????????????????????");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * ????????????
	 */
	/**
	 * ????????????
	 */
	// 1.??????????????????
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
		return Constants.PRODUCTOUT; // ????????????
	}

	// 2.????????????
	@RequestMapping("/productOutsource")
	public String productOutsource(ProductOutsourceEntity productOutsourceEntity, String proname, String postId, CompanyNameEntity companyNameEntity,
			@RequestParam(value = "file", required = false) List<MultipartFile> file, HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//??????????????????
		productOutsourceEntity.setProjectName(proname);
		String productOutsource = productOutsourceController.productOutsource(productOutsourceEntity, proname, file, req);
		if (!productOutsource.equals(Constants.SUCCESS)) {
			log.info("ProjectController/productOutsource, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return productOutsourceWeb(proname, req, postId, companyNameEntity);
		}
		String productOutsourceSubmit = projectService.productOutsourceSubmit(productOutsourceEntity, proname, req);
		if (productOutsourceSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productOutsourceSubmit, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return productOutsourceWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/productOutsource, ????????????-????????????");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}
	 
	 
	/**
	 * 4.??????
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
			log.info("ProjectController/productOutDetail, ??????????????????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????????????????");
			return productProjectWeb(req, postId, companyNameEntity);
		}
		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.PRODUCTOUTDETAIL;
		
		
	}

	// ????????????
	@RequestMapping("/productOutDownloadFile")
	public void productOutDownloadFile(String proname, String fileName, HttpServletResponse resp) {
		productOutsourceService.downloadFile(proname, fileName, resp);
	}

	/**
	 * ??????????????????
	 */
	// 1.??????????????????
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
		return Constants.PRODUCTPROCESS; // ????????????
	}

	// 2.????????????
	@RequestMapping("/productProcess")
	public String productProcess(ProductProcessEntity productProcessEntity, String proname, HttpServletRequest req,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//??????????????????
		productProcessEntity.setProjectName(proname);
		String productProcess = productProcessController.productProcess(productProcessEntity, req);
		if (!productProcess.equals(Constants.SUCCESS)) {
			log.info("ProjectController/productProcess, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return productProcessWeb(proname, req, postId, companyNameEntity);
		}
		String productProcessSubmit = projectService.productProcessSubmit(productProcessEntity, proname, req);
		if (productProcessSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productProcessSubmit, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return productProcessWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/productProcess, ????????????-????????????");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 4.??????
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
			log.info("ProjectController/productProDetail, ????????????????????????????????????");
			req.setAttribute(Constants.ERROR, "????????????????????????????????????");
			return productProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.POSTID, postId);
		return Constants.PRODUCTPROCESSDETAIL;
	}

	/**
	 * ????????????
	 */
	// 1.??????????????????
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
		return Constants.PRODUCTPURCHASE; // ????????????
	}

	// 2.????????????
	@RequestMapping("/productPurchase")
	public String productPurchase(ProductPurchaseEntity productPurchaseEntity, String proname, HttpServletRequest req,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//??????????????????
		productPurchaseEntity.setProjectName(proname);
		String productPurchase = productPurchaseController.productPurchase(productPurchaseEntity, req);
		if (!productPurchase.equals(Constants.SUCCESS)) {
			log.info("ProjectController/productPurchase, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return productPurchaseWeb(proname, req, postId, companyNameEntity);
		}
		String productPurchaseSubmit = projectService.productPurchaseSubmit(productPurchaseEntity, proname, req);
		if (productPurchaseSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productPurchaseSubmit, ????????????-????????????");
			req.setAttribute(Constants.ERROR, "????????????-????????????");
			return productPurchaseWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/productPurchase, ????????????-????????????");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * 4.??????
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
			log.info("ProjectController/productPurDetail, ??????????????????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????????????????");
			return productProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.POSTID, postId);
		return Constants.PRODUCTPURCHASEDETAIL;
	}

	// 3.??????????????????????????????????????????
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
	 * ??????????????????
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
			log.info("ProjectController/productOutDetail, ??????????????????????????????");
			return productProjectWeb(req, postId, companyNameEntity);
		}

		String productProDetail = projectService.productProDetail(proname, req);
		if (productProDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productProDetail, ????????????????????????????????????");
			return productProjectWeb(req, postId, companyNameEntity);
		}
		String productPurDetail = projectService.productPurDetail(proname, req);
		if (productPurDetail.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productPurDetail, ??????????????????????????????");
			return productProjectWeb(req, postId, companyNameEntity);
		}
	
		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.PRODUCTALLDETAIL;
	}

	/**
	 * ?????????????????????????????????
	 */
	@RequestMapping("/productPass")
	public String productPass(String passSubmit, String proname, String postId, CompanyNameEntity companyNameEntity, HttpServletResponse resp,
			HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String productPassSubmit = projectService.productPassSubmit(passSubmit, proname, req);
		if (productPassSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/productPass, ????????????????????????");
			req.setAttribute(Constants.ERROR, "????????????????????????");
			return productAllDetail(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/productPass, ????????????????????????");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}

	/**
	 * ????????????
	 */
	// 1.??????????????????
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
			return Constants.ASSEMBLE; // ????????????
		}
		else {
			req.setAttribute("assembleEntity", flag);
			return Constants.REASSEMBLE;
		}
	}

	// 2.????????????
	@RequestMapping("/assembling")
	public String assembling(AssemblingEntity assemblingEntity, String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//??????????????????
		assemblingEntity.setProjectName(proname);
		String assembling = assemblingController.assembling(assemblingEntity);
		if (!assembling.equals(Constants.SUCCESS)) {
			log.info("ProjectController/assembling, ??????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????");
			return assemblingWeb(proname, req, postId, companyNameEntity);
		}
		String designSoftwareSubmit = projectService.assemblingSubmit(assemblingEntity, proname, req);
		if (designSoftwareSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/assemblingSubmit, ??????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????");
			return assemblingWeb(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/assembling, ??????????????????");
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.PRONAME, proname);
		return toSuccessHtml(req);
	}

	// 3.?????????????????????
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
	 * 4.??????
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
			log.info("ProjectController/assembleDetail, ????????????????????????");
			req.setAttribute(Constants.ERROR, "????????????????????????");
			return assembleProjectWeb(req, postId, companyNameEntity);
		}

		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.PRONAME, proname);
		return Constants.ASSEMBLEDETAIL;
	}
	/**
	 * ?????????????????????????????????
	 */
	@RequestMapping("/assemblePass")
	public String assemblePass(String passSubmit, String proname, String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String assemblePassSubmit = projectService.assemblePassSubmit(passSubmit, proname, req);
		if (assemblePassSubmit.equals(Constants.FAILCODE)) {
			log.info("ProjectController/assemblePass, ????????????????????????");
			req.setAttribute(Constants.ERROR, "????????????????????????");
			return assemblingDetail(proname, req, postId, companyNameEntity);
		}
		log.info("ProjectController/assemblePass, ????????????????????????");
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}
	/*
	 * thg ??????????????????????????????
	 */
	@RequestMapping("/reAssembling")
	public String reAssembing(String poId, String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity, String meEndDate, String elEndDate, String soEndDate, String checkEndDate) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String flag = projectService.reAssembingSubmit(poId, meEndDate, elEndDate, soEndDate, checkEndDate);
		req.setAttribute(Constants.POSTID, postId);
		if(flag.equals(Constants.SUCCESS)) {
			log.info("projectController/reAssembilng:??????????????????????????????");
			return toSuccessHtml(req);
		}
		else {
			log.info("projectController/reAssembilng:??????????????????????????????");
			req.setAttribute(Constants.ERROR, "??????????????????????????????");
			return assemblingWeb(proname, req, postId, companyNameEntity);
		}
	}
	
	/**
	 * ?????????-????????????
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
	 * qh success????????????
	 * 
	 * @param req
	 * @return
	 */
	public String toSuccessHtml(HttpServletRequest req) {
		req.setAttribute(Constants.POSTID, req.getParameter(Constants.POSTID));
		return Constants.SUCCESS;
	}
	
	/**
	 * thg ?????????404??????
	 */
	@RequestMapping("/404")
	public String to404Html(HttpServletRequest req) {
		return "404";
	}
	
	/**
	 * thg ?????????????????????????????????
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
			request.setAttribute("msg", "??????????????????????????????????????????????????????????????????????????????????????????????????????");
			return "msgConfig";
		}
	}
	/**
	 * ????????????url????????????
	 * 
	 * @param path
	 * @return
	 */
	private String getFileName(String path) {
		String[] split = path.split("/");
		return split[split.length - 1]+"/"+split[split.length - 2];
	}

}
