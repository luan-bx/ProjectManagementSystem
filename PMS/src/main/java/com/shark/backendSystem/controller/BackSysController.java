package com.shark.backendSystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shark.backendSystem.entity.CompanyNameEntity;
import com.shark.backendSystem.entity.DesignEleFieldEntity;
import com.shark.backendSystem.entity.DesignMecFieldEntity;
import com.shark.backendSystem.mapper.BackSysMapping;
import com.shark.backendSystem.service.BackSysService;
import com.shark.base.dto.ProjectDto;
import com.shark.base.mapper.ProjectDtoMapping;
import com.shark.project.controller.ProjectController;
import com.shark.project.controller.SignController;
import com.shark.project.controller.design.DesignController;
import com.shark.project.entity.PaymentTermEntity;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.SignEntity;
import com.shark.project.entity.design.DesignEntity;
import com.shark.project.mapper.ProjectMapping;
import com.shark.project.service.SignService;
import com.shark.project.service.design.StaffListService;
import com.shark.users.entity.DepartmentEntity;
import com.shark.users.entity.PostEntity;
import com.shark.users.entity.UserEntity;
import com.shark.users.mapper.DepartmentMapping;
import com.shark.users.mapper.PostMapping;
import com.shark.users.mapper.UserMapping;
import com.shark.users.service.UserService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BackSysController {

	@Autowired
	private BackSysService backSysService;
	@Autowired
	private UserService userService;
	@Autowired
	private SignController signController;
	@Autowired
	private ProjectController projectController;
	@Autowired
	private DesignController designController;
	@Autowired
	private BackSysMapping backSysMapping;
	@Autowired
	private PostMapping postMapping;
	@Autowired
	private DepartmentMapping departmentMapping;
	@Autowired
	private UserMapping userMapping;
	@Autowired
	private ProjectMapping projectMapping;
	@Autowired
	private ProjectDtoMapping projectDtoMapping;
	@Autowired
	private SignService signService;
	@Autowired
	private StaffListService staffListService;
	
	/*
	 * 管理员 跳转到后台系统
	 */
	@RequestMapping("backIndex")
	public String backIndex(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String newUser = backSysService.getNewUser(req);
		if(newUser.equals(Constants.FAILCODE)) {
			log.info("backSysController/backIndex:查询新用户信息失败！");
			req.setAttribute(Constants.ERROR, "查询新用户信息失败");
		}
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.INDEX;
	}
	/**
	 * 后台系统个人中心
	 * @param postId
	 * @param req
	 * @return
	 */
	@RequestMapping("/backPersonalCenterWeb")
	public String backPersonalCenterWeb(String postId, HttpServletRequest req, HttpServletResponse response) {
		
		Cookie[] cookies = req.getCookies();
		boolean flag = false;
		String userName = null;
		if (cookies != null) {
			for (Cookie ck : cookies) {
				if (ck.getName().equals(Constants.COOKIEHEAD)) {
					flag = true;
					userName = ck.getValue();
					log.info("BaseController/pms, 本次登录用户:{}", ck.getValue());
					break;
				}
			}
		}
		if (!flag) {
			return Constants.LOGIN;
		}
		UserEntity userEntity = userMapping.queryUserByUserName(userName);
		// 在userEntity里面填充postid-->postName, departid --> departname;
		userEntity.setDepartmentName(departmentMapping.getNameById(userEntity.getDepartmentId()));
		userEntity.setPostName(postMapping.getNameById(userEntity.getPostId()));


//		头像
//		 ServletOutputStream outputStream = null;
//		    try {
//		    	byte[] bytes = null;
//		    	Path path =Path.of(userEntity.getIcon());
//		    	bytes = Files.readAllBytes(path);
//		    	
//		        outputStream = response.getOutputStream();
//		        outputStream.write(bytes);
//		        outputStream.flush();
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		        if (outputStream != null) {
//		            outputStream.close();
//		        }
//		    }

			
			
		req.setAttribute("INFORMATION", userEntity);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.PERSONALCENTER;
	}
	
	/**
	 * 	后台系统修改头像
	 */
		@RequestMapping("/backIconUpdate")
		public String backIconUpdate(String username, @RequestParam(value = "file", required = false) List<MultipartFile> file,
				HttpServletRequest req, HttpServletResponse resp, String postId) throws IOException {

				userService.iconUpdate(username, file, resp);
				return backPersonalCenterWeb(postId, req, resp);
			
		}
	
	/*
	 * 全部用户信息
	 */
	/**
	 * 进入全部用户修改表
	 * 
	 * @param postId
	 * @param req
	 * @return
	 */
	@RequestMapping("/allUserEntity")
	public String allUserEntity(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		List<UserEntity> allUser = backSysService.getAllUser();
		if (allUser.equals(null)) {
			log.info("BackSysController/allUserEntity, 进入全部用户修改表失败");
			req.setAttribute(Constants.ERROR, "进入全部用户修改表失败");
			return Constants.BACKENDSYSTEM + Constants.INDEX;
		}
		// 将注册页面的部门、岗位两个选项的下拉框动态给前端
		List<DepartmentEntity> allDepart = userService.getAllDepart();
		List<PostEntity> allPost = userService.getAllPost();
		req.setAttribute(Constants.DEPARTMENT, allDepart);
		req.setAttribute(Constants.POST, allPost);
		req.setAttribute(Constants.ALLUSER, allUser);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.ALLUSER;
	}

	/**
	 * 提交单条用户修改表
	 * 
	 * @param postId
	 * @param req
	 * @return
	 */
	@RequestMapping("/updataOneUserEntity")
	public String updataOneUserEntity(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req, UserEntity userEntity, String originUserName) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String updataOneUserEntity = backSysService.updataOneUserEntity(userEntity, originUserName);
		if (updataOneUserEntity.equals(Constants.FAILCODE)) {
			log.info("BackSysController/updataOneUserEntity, 提交用户修改表失败");
			req.setAttribute(Constants.ERROR, "提交用户修改表失败");
			return allUserEntity(postId, companyNameEntity, req);
		}
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute("msg", "修改成功");
		return allUserEntity(postId, companyNameEntity, req);
	}

	/**
	 * 删除一条用户记录
	 * 
	 * @param userName——需要删除的用户的用户名
	 * @param proname
	 * @param req
	 * @param postId
	 * @return
	 */
	@RequestMapping("/deleteUser")
	public String deleteUser(String userName, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String deleteUser = backSysService.deleteUser(userName);
		if (deleteUser.equals(Constants.FAILCODE)) {
			log.info("BackSysController/deleteUser, 用户删除失败");
			req.setAttribute(Constants.ERROR, "用户删除失败");
			return allUserEntity(postId, companyNameEntity, req);
		}
		log.info("BackSysController/deleteUser, 用户删除成功");
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute("msg", "删除用户成功");
		return allUserEntity(postId, companyNameEntity, req);
	}

	/*
	 * 公司结构
	 */
	
	/**
	 * 进入公司结构修改表
	 * @param postId
	 * @param req
	 * @return
	 */
	@RequestMapping("/companyNameDepartmentPost")
	public String companyNameDepartmentPost(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req) {
		List<DepartmentEntity> allDepart = userService.getAllDepart();
		List<PostEntity> allPost = userService.getAllPost();
		CompanyNameEntity originCompanyNameEntity = backSysMapping.getCompanyNameById(1);
		req.setAttribute(Constants.DEPARTMENT, allDepart);
		req.setAttribute(Constants.POST, allPost);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		req.setAttribute("originCompanyNameEntity", originCompanyNameEntity);
		return Constants.BACKENDSYSTEM + Constants.CONSTRUCTION;
	}
	
	/**
	 * 修改公司名称
	 * @param postId
	 * @param companyName1
	 * @param companyName2
	 * @param req
	 * @return
	 */
	@RequestMapping("changeCompanyName")
	public String changeCompanyName(String postId, CompanyNameEntity companyNameEntity, String companyName1, String companyName2, HttpServletRequest req, HttpServletResponse response) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String changeCompanyName = backSysService.changeCompanyName(companyName1, companyName2, response);
		if (changeCompanyName.equals(Constants.FAILCODE)) {
			log.info("BackSysController/changeCompanyName, 修改公司名称失败");
			req.setAttribute(Constants.ERROR, "修改公司名称失败");
			return companyNameDepartmentPost(postId, companyNameEntity, req);
		}
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.SUCCESS;
	}
	/**
	 * 添加岗位，postEntity的number先设置与name一致
	 * @param postId
	 * @param req
	 * @param postEntity
	 * @return
	 */
	@RequestMapping("/newPost")
	public String newPost(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req, PostEntity postEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String newPost = backSysService.newPost(postEntity);
		if (newPost.equals(Constants.FAILCODE)) {
			log.info("BackSysController/newPost, 添加岗位失败");
			req.setAttribute(Constants.ERROR, "添加岗位失败");
			return companyNameDepartmentPost(postId, companyNameEntity, req);
		}
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.SUCCESS;
	}
	
	/**
	 * 添加部门，departmentEntity的number先设置与name一致
	 * @param postId
	 * @param req
	 * @param departmentEntity
	 * @return
	 */
	@RequestMapping("/newDepartment")
	public String newDepartment(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req, DepartmentEntity departmentEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String newDepartment = backSysService.newDepartment(departmentEntity);
		if (newDepartment.equals(Constants.FAILCODE)) {
			log.info("BackSysController/newDepartment, 添加部门失败");
			req.setAttribute(Constants.ERROR, "添加部门失败");
			return companyNameDepartmentPost(postId, companyNameEntity, req);
		}
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.SUCCESS;
	}
	//20220823-thg
	/**
	 * 修改岗位名称
	 * @author thg
	 * @param postId
	 * @param companyNameEntity
	 * @param req
	 * @param postName
	 * @param originName
	 */
	@RequestMapping("/postNameUpdate")
	public String postNameUpdate(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req, String postName, String originName) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String postUpdate = backSysService.postNameUpdate(postName, originName);
		if(postUpdate.equals(Constants.FAILCODE)) {
			log.info("backSysController/postNameUpdate:修改岗位名称失败");
			req.setAttribute(Constants.ERROR,"修改岗位名称失败");
			return companyNameDepartmentPost(postId, companyNameEntity, req);
		}
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.SUCCESS;
	}
	//20220823-thg
	/**
	 * 修改部门名称
	 * @author thg
	 * @param postId
	 * @param companyNameEntity
	 * @param req
	 * @param departmentName
	 * @param originName
	 */
	@RequestMapping("/departmentNameUpdate")
	public String departmentNameUpdate(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req, String departmentName, String originName) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String departmentUpdate = backSysService.departmentNameUpdate(departmentName, originName);
		if(departmentUpdate.equals(Constants.FAILCODE)) {
			log.info("backSysController/postNameUpdate:修改部门名称失败");
			req.setAttribute(Constants.ERROR,"修改部门名称失败");
			return companyNameDepartmentPost(postId, companyNameEntity, req);
		}
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.SUCCESS;
	}
	/**
	 * 删除岗位
	 * @param postId
	 * @param req
	 * @param name
	 * @return
	 */
	@RequestMapping("/delatePost")
	public String delatePost(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req, String name) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String delatePost = backSysService.delatePost(name);
		if (delatePost.equals(Constants.FAILCODE)) {
			log.info("BackSysController/delatePost, 删除岗位失败");
			req.setAttribute(Constants.ERROR, "删除岗位失败");
			return companyNameDepartmentPost(postId, companyNameEntity, req);
		}
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.SUCCESS;
	}
	/**
	 * 删除部门
	 * @param postId
	 * @param req
	 * @param name
	 * @return
	 */
	@RequestMapping("/delateDepartment")
	public String delateDepartment(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req, String name) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String delateDepartment = backSysService.delateDepartment(name);
		if (delateDepartment.equals(Constants.FAILCODE)) {
			log.info("BackSysController/delateDepartment, 删除部门失败");
			req.setAttribute(Constants.ERROR, "删除部门失败");
			return companyNameDepartmentPost(postId, companyNameEntity, req);
		}
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.SUCCESS;
	}
	/*
	 * 付款条件
	 */
	/**
	 * 进入付款条件修改表
	 * @param postId
	 * @param req
	 * @return
	 */
	@RequestMapping("/allPaymentTerm")
	public String allPaymentTerm(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		List<PaymentTermEntity> allPaymentTerm = backSysService.getAllPaymentTerm();
		if (allPaymentTerm.equals(null)) {
			log.info("BackSysController/allPaymentTerm, 进入付款条件修改表失败");
			req.setAttribute(Constants.ERROR, "进入付款条件修改表失败");
			//20220819-thg
			return Constants.BACKENDSYSTEM + Constants.INDEX;
		}
		req.setAttribute(Constants.ALLPAYMENTTERM, allPaymentTerm);
		req.setAttribute(Constants.POSTID, postId);
		//20220819-thg
		return Constants.BACKENDSYSTEM + Constants.PAYMENT;
	}
	/**
	 * 添加一条付款条件
	 * @param postId
	 * @param req
	 * @param paymentTermEntity
	 * @return
	 */
	@RequestMapping("newPaymentTerm")
	public String newPaymentTerm(String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req, PaymentTermEntity paymentTermEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String newPaymentTerm = backSysService.newPaymentTerm(paymentTermEntity);
		if (newPaymentTerm.equals(Constants.FAILCODE)) {
			log.info("BackSysController/newPaymentTerm, 添加一条付款条件失败");
			req.setAttribute(Constants.ERROR, "添加一条付款条件失败");
			return allPaymentTerm(postId, companyNameEntity, req);
		}
		req.setAttribute(Constants.POSTID, postId);
		//20220819-thg
		return Constants.BACKENDSYSTEM + Constants.SUCCESS;
	}
	/**
	 * 删除一条付款条件
	 * 
	 * @param id
	 * @param postId
	 * @param req
	 * @return
	 */
	@RequestMapping("/deletePaymentTerm")
	public String deletePaymentTerm(int id, String postId, CompanyNameEntity companyNameEntity, HttpServletRequest req) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String deletePaymentTerm = backSysService.deletePaymentTerm(id);
		if (deletePaymentTerm.equals(Constants.FAILCODE)) {
			log.info("BackSysController/deletePaymentTerm, 付款条件删除失败");
			req.setAttribute(Constants.ERROR, "付款条件删除失败");
			return allPaymentTerm(postId, companyNameEntity, req);
		}
		log.info("BackSysController/deletePaymentTerm, 付款条件删除成功");
		req.setAttribute(Constants.POSTID, postId);
		//20220819-thg
		return Constants.BACKENDSYSTEM + Constants.SUCCESS;
	}

	/*
	 * 20220822 thg
	 * 进入项目管理页
	 */
	@RequestMapping("/projectManage")
	public String projectManage(String postId, HttpServletRequest req, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		List<ProjectEntity> project = new ArrayList<ProjectEntity>();
		List<ProjectDto> allProject = new ArrayList<ProjectDto>();
		try {
			project = projectMapping.getAll();
			for(ProjectEntity p : project) {
				allProject.add(projectDtoMapping.getDtoById(p.getId()));
			}
		}
		catch(Exception e) {
			log.info("BackSysController/projectManage:查询全部项目信息失败",e);
			return backIndex(postId, companyNameEntity, req);
		}
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute("allProject", allProject);
		return Constants.BACKENDSYSTEM + Constants.PROJECTMANAGE;
		}
	/*
	 * 20220822-thg 后台系统全部详情页面
	 */
	@RequestMapping("/backDetail")
	public String backDetail(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
			req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
			projectController.reviewDetail(proname, req, postId, companyNameEntity);
			projectController.signDetail(proname, req, postId, companyNameEntity);
			projectController.designAllDetail(proname, req, postId, companyNameEntity);
			projectController.productAllDetail(proname, req, postId, companyNameEntity);
			projectController.assemblingDetail(proname, req, postId, companyNameEntity);
			req.setAttribute(Constants.POSTID, postId);
			req.setAttribute(Constants.PRONAME, proname);
			return Constants.BACKENDSYSTEM + Constants.PROJECTDETAIL;
		
	}
	
	/*
	 * 项目管理（签订、总设计、装配的查询功能用之前写的“详情”）
	 */
	/**
	 * 删除一条项目
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 * @return
	 */
	@RequestMapping("/projectDelete")
	public String projectDelete(String proname, HttpServletRequest req,String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		//20220822-thg,projectDelete方法参数修改
		String projectDelete = backSysService.projectDelete(projectMapping.getProjectByName(proname), req);
		//20220822-thg equal里面的参数应该是successcode而不是success
		if (!projectDelete.equals(Constants.SUCCESSCODE)) {
			log.info("BackSysController/projectDelete, 项目删除失败");
			req.setAttribute(Constants.ERROR, "项目删除失败");
			//20220822-thg
			return projectManage(postId, req, companyNameEntity);
		}
		log.info("BackSysController/projectDelete, 项目删除成功");
		req.setAttribute(Constants.POSTID, postId);
		//20220822-thg
		req.setAttribute("msg", "删除项目成功");
		return projectManage(postId, req, companyNameEntity);
	}
	
	
	/*
	 * 20220822-thg， 跳转到签订修改页面
	 */
	@RequestMapping("/signUpdateWeb")
	public String signUpdateWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		projectController.signDetail(proname, req, postId, companyNameEntity);
		// 付款条件的下拉框：签订人员和高级管理员
		List<PaymentTermEntity> paymentTerm = signService.getPaymentTerm();		
		req.setAttribute(Constants.PAYMENTTREM, paymentTerm);
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		req.setAttribute(Constants.PRONAME, proname);
		return Constants.BACKENDSYSTEM + Constants.SIGNUPDATE;
	}
	/**
	 * 签订更新
	 * @param signEntity
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 * @return
	 */
	@RequestMapping("/signUpdate")
	public String signUpdate(SignEntity signEntity, String proname, HttpServletRequest req,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String signUpdate = signController.signUpdate(signEntity, proname, req);
		if (!signUpdate.equals(Constants.SUCCESS)) {
			log.info("BackSysController/signUpdate, 签订更新失败");
			req.setAttribute(Constants.ERROR, "签订更新失败");
			//20220822-thg
			return backDetail(proname, req, postId, companyNameEntity);
		}
		log.info("BackSysController/signUpdate, 签订更新成功");
		req.setAttribute(Constants.POSTID, postId);
		//20220822-thg
		req.setAttribute("msg", "修改签订信息成功");
		return signUpdateWeb(proname, req, postId, companyNameEntity);
	}
	
	/*
	 * 20220822-thg,跳转到总设计更新页面
	 */
	@RequestMapping("/designUpdateWeb")
	public String designUpdateWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
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
		
		//详情
		projectController.designAllDetail(proname, req, postId, companyNameEntity);
		
		//页面需要的三个参数
		req.setAttribute(Constants.POSTID, postId);
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		req.setAttribute(Constants.PRONAME, proname);
		return Constants.BACKENDSYSTEM + Constants.DESIGNUPDATE;
	}
	
	/**
	 * 总设计更新
	 * @param designEntity
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 * @return
	 */
	@RequestMapping("/designUpdate")
	public String designUpdate(DesignEntity designEntity, String proname, HttpServletRequest req,
			String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String designUpdate = designController.designUpdate(designEntity, proname, req);
		if (!designUpdate.equals(Constants.SUCCESS)) {
			log.info("BackSysController/designUpdate, 总设计更新失败");
			req.setAttribute(Constants.ERROR, "总设计更新失败");
			return designUpdateWeb(proname, req, postId, companyNameEntity);
		}
		log.info("BackSysController/designUpdate, 总设计更新成功");
		req.setAttribute(Constants.POSTID, postId);
		//20220822-thg
		req.setAttribute("msg", "修改总设计信息成功");
		return designUpdateWeb(proname, req, postId, companyNameEntity);
	}
	
	/**
	 * 进入三个子设计修改表
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 * @return
	 */
	@RequestMapping("/eleMecSofDeleteWeb")
	public String eleMecSofDeleteWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String eleMecSofDeleteWeb = backSysService.eleMecSofDeleteWeb(proname, req);
		if (eleMecSofDeleteWeb.equals(Constants.FAILCODE)) {
			log.info("BackSysController/eleMecSofDeleteWeb, 进入设计修改表失败");
			req.setAttribute(Constants.ERROR, "进入设计修改表失败");
			return projectManage(postId, req, companyNameEntity);
		}
		req.setAttribute(Constants.POSTID, postId);
		//20220822-thg 
		req.setAttribute(Constants.PRONAME, proname);
		return Constants.BACKENDSYSTEM + Constants.MECELESOFDETAIL;
	}
	/**
	 * 三个子设计修改表——删除
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 * @param eleTime
	 * @param mecTime
	 * @param sofTime
	 * @return
	 */
	@RequestMapping("/eleMecSofDelete")
	public String eleMecSofDelete(String proname, HttpServletRequest req, String postId, 
			CompanyNameEntity companyNameEntity, String eleName, String mecName, String sofName) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String eleMecSofDelete = backSysService.eleMecSofDelete(proname, req, eleName, mecName, sofName);
		if (eleMecSofDelete.equals(Constants.FAILCODE)) {
			log.info("BackSysController/eleMecSofDelete, 进入设计修改表失败");
			req.setAttribute(Constants.ERROR, "进入设计修改表失败");
			return eleMecSofDeleteWeb(proname, req, postId, companyNameEntity);
		}
		req.setAttribute(Constants.POSTID, postId);
		//20220823-thg
		req.setAttribute("msg", "删除设计记录成功");
		return eleMecSofDeleteWeb(proname, req, postId, companyNameEntity);
	}
	/**
	 * 进入三个子生产修改表
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 * @return
	 */
	@RequestMapping("/outPurProDeleteWeb")
	public String outPurProDeleteWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String outPurProDeleteWeb = backSysService.outPurProDeleteWeb(proname, req);
		if (outPurProDeleteWeb.equals(Constants.FAILCODE)) {
			log.info("BackSysController/outPurProDeleteWeb, 进入生产修改表失败");
			req.setAttribute(Constants.ERROR, "进入生产修改表失败");
			//20220823-thg
			return backDetail(proname, req, postId, companyNameEntity);
		}
		//20220823-thg
		req.setAttribute(Constants.PRONAME, proname);
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.OUTPURPRODETAIL;
	}
	
	/**
	 * 三个子生产修改表——删除
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 * @param outId
	 * @param purId
	 * @param proId
	 * @return
	 */
	@RequestMapping("/outPurProDelete")
	public String outPurProDelete(String proname, HttpServletRequest req, String postId, 
			CompanyNameEntity companyNameEntity,  int outId, int purId, int proId) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String outPurProDelete = backSysService.outPurProDelete(proname, req, outId, purId, proId);
		if (outPurProDelete.equals(Constants.FAILCODE)) {
			log.info("BackSysController/outPurProDelete, 进入生产修改表失败");
			req.setAttribute(Constants.ERROR, "进入生产修改表失败");
			return outPurProDeleteWeb(proname, req, postId, companyNameEntity);
		}
		req.setAttribute(Constants.POSTID, postId);
		//20220823-thg
		req.setAttribute("msg", "删除生产记录成功");
		return outPurProDeleteWeb(proname, req, postId, companyNameEntity);
	}
	
	//20220823-thg
	/**
	 * 进入装配修改表
	 * @author thg
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 */
	@RequestMapping("/assembleDeleteWeb")
	public String assembleDeleteWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		
		if(projectController.assemblingDetail(proname, req, postId, companyNameEntity).equals(Constants.FAILCODE)) {
			log.info("backSysController:assembleDeleteWeb:进入装配修改表失败");
			req.setAttribute(Constants.ERROR, "进入装配修改页失败");
			return projectManage(postId, req, companyNameEntity);
		}
		return Constants.BACKENDSYSTEM + Constants.ASSEMBLEDETAIL;
	}
	
	/**
	 * 装配修改表——删除
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 * @param assembleId
	 * @return
	 */
	@RequestMapping("/assembleDelete")
	public String assembleDelete(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity, int assembleId) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String assembleDelete = backSysService.assembleDelete(proname, req, assembleId);
		if (assembleDelete.equals(Constants.FAILCODE)) {
			log.info("BackSysController/assembleDelete, 进入生产修改表失败");
			req.setAttribute(Constants.ERROR, "进入生产修改表失败");
			return assembleDeleteWeb(proname, req, postId, companyNameEntity);
		}
		req.setAttribute(Constants.POSTID, postId);
		//20220823-thg
		req.setAttribute("msg", "删除装配记录成功");
		return assembleDeleteWeb(proname, req, postId, companyNameEntity);
	}
	/*
	 * 流程管理
	 */
	/**
	 * 进入设计修改表，数据从前端js拿
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 * @return
	 */
	@RequestMapping("/designFieldWeb")
	public String designFieldWeb(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
//		String designFieldWeb = backSysService.designFieldWeb(proname, req);
//		if (designFieldWeb.equals(Constants.FAILCODE)) {
//			log.info("BackSysController/designFieldWeb, 进入设计修改表失败");
//			req.setAttribute(Constants.ERROR, "进入设计修改表失败");
//			return "后台系统首页";
//		}
		
		//20220825-thg，把目前的流程发给前端
		String designFieldWeb = backSysService.designFieldWeb(req);
		if(designFieldWeb.equals(Constants.FAILCODE)) {
			log.info("BackSysController/designFieldWeb, 进入设计修改表失败");
			req.setAttribute(Constants.ERROR, "进入设计修改表失败");
			return backIndex(postId, companyNameEntity, req);
		}
		req.setAttribute(Constants.POSTID, postId);
		return Constants.BACKENDSYSTEM + Constants.PROCESSMANAGE;
	}
	/**
	 * 电气设计字段修改
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 * @param EleField
	 * @return
	 */
	@RequestMapping("/designEleFieldUpdate")
	public String designEleFieldUpdate(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity, DesignEleFieldEntity designEleFieldEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String designEleFieldUpdate = backSysService.designEleFieldUpdate(proname, req, designEleFieldEntity);
		if (designEleFieldUpdate.equals(Constants.FAILCODE)) {
			log.info("BackSysController/designEleFieldUpdate, 电气设计字段修改失败");
			req.setAttribute(Constants.ERROR, "a失败");
			return designFieldWeb(proname, req, postId, companyNameEntity);
		}
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}
	
	/**
	 * 机械设计字段修改
	 * @param proname
	 * @param req
	 * @param postId
	 * @param companyNameEntity
	 * @param MecField
	 * @return
	 */
	@RequestMapping("/designMecFieldUpdate")
	public String designMecFieldUpdate(String proname, HttpServletRequest req, String postId, CompanyNameEntity companyNameEntity, DesignMecFieldEntity designMecFieldEntity) {
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);
		String designMecFieldUpdate = backSysService.designMecFieldUpdate(proname, req, designMecFieldEntity);
		if (designMecFieldUpdate.equals(Constants.FAILCODE)) {
			log.info("BackSysController/designMecFieldUpdate, 机械设计字段修改失败");
			req.setAttribute(Constants.ERROR, "机械设计字段修改失败");
			return designFieldWeb(proname, req, postId, companyNameEntity);
		}
		req.setAttribute(Constants.POSTID, postId);
		return toSuccessHtml(req);
	}
	

	/**
	 * success打勾页面
	 * 
	 * @param req
	 * @return
	 */
	private String toSuccessHtml(HttpServletRequest req) {
		req.setAttribute(Constants.POSTID, req.getParameter(Constants.POSTID));
		return Constants.BACKENDSYSTEM + Constants.SUCCESS;
	}
}
