package com.shark.backendSystem.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shark.backendSystem.entity.CompanyNameEntity;
import com.shark.backendSystem.entity.DesignEleFieldEntity;
import com.shark.backendSystem.entity.DesignMecFieldEntity;
import com.shark.backendSystem.mapper.BackSysMapping;
import com.shark.base.dto.ProjectDto;
import com.shark.project.entity.AssemblingEntity;
import com.shark.project.entity.PaymentTermEntity;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.design.DesignElectricalEntity;
import com.shark.project.entity.design.DesignMechancisEntity;
import com.shark.project.entity.design.DesignSoftwareEntity;
import com.shark.project.entity.product.ProductOutsourceEntity;
import com.shark.project.entity.product.ProductProcessEntity;
import com.shark.project.entity.product.ProductPurchaseEntity;
import com.shark.project.mapper.AssemblingMapping;
import com.shark.project.mapper.PaymentTermMapping;
import com.shark.project.mapper.ProjectMapping;
import com.shark.project.mapper.RequestMapping;
import com.shark.project.mapper.SignMapping;
import com.shark.project.mapper.StatusMapping;
import com.shark.project.mapper.design.DesignElectricalMapping;
import com.shark.project.mapper.design.DesignMapping;
import com.shark.project.mapper.design.DesignMechancisMapping;
import com.shark.project.mapper.design.DesignSoftwareMapping;
import com.shark.project.mapper.product.ProductMapping;
import com.shark.project.mapper.product.ProductOutsourceMapping;
import com.shark.project.mapper.product.ProductProcessMapping;
import com.shark.project.mapper.product.ProductPurchaseMapping;
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
@Service

//扫不到这个mapping，换个文件目录就可以，也可以加这一条指向哪个目录
@MapperScan(value = "com.shark.backendSystem.mapper")
public class BackSysService {

	@Autowired
	private BackSysMapping backSysMapping;
	@Autowired
	private UserService userService;
	@Autowired
	private PaymentTermMapping paymentTermMapping;
	@Autowired
	private DepartmentMapping departmentMapping;
	@Autowired
	private PostMapping postMapping;
	@Autowired
	private ProjectMapping projectMapping;
	@Autowired
	private DesignMechancisMapping designMechancisMapping;
	@Autowired
	private DesignMapping designMapping;
	@Autowired
	private DesignElectricalMapping designElectricalMapping;
	@Autowired
	private DesignSoftwareMapping designSoftwareMapping;
	@Autowired
	private ProductMapping productMapping;
	@Autowired
	private ProductOutsourceMapping productOutsourceMapping;
	@Autowired
	private ProductProcessMapping productProcessMapping;
	@Autowired
	private ProductPurchaseMapping productPurchaseMapping;
	@Autowired
	private AssemblingMapping assemblingMapping;
	@Autowired
	private StatusMapping statusMapping;
	@Autowired
	private RequestMapping requestMapping;
	@Autowired
	private SignMapping signMapping;
	
	/**
	 * 全部用户信息
	 * @return
	 */
	
	/*
	 * 全部用户信息
	 */
	public List<UserEntity> getAllUser() {
		try {
			List<UserEntity> allUser = backSysMapping.getAll();
			log.info("BackSysService/getAllUser, 获取全部用户信息成功");
			return allUser;
		} catch (Exception e) {
			// TODO: handle exception
			log.info("BackSysService/getAllUser, 获取全部用户信息失败, ", e);
			return null;
		}
	}
	
	/*
	 * 20220826-thg，新注册用户信息
	 */
	public String getNewUser(HttpServletRequest req){
		try {
			List<UserEntity> newUser = backSysMapping.getNew();
			log.info("BackSysService/getNewUser, 获取新用户信息成功");
			req.setAttribute("newUser", newUser);
			return Constants.SUCCESSCODE;
		}catch (Exception e) {
			// TODO: handle exception
			log.info("BackSysService/getNewUser, 获取新用户信息失败",e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 更新一条用户记录
	 * 修改密码不包括在这里面
	 */
	public String updataOneUserEntity(UserEntity userEntity, String originUserName) {
		if (userEntity == null) {
			return Constants.FAILCODE; 
		}
		try {
			//id通过名字去查数据库，再插进来
			userEntity.setDepartmentId(userService.departmentId(userEntity.getDepartmentName()));
			userEntity.setPostId(userService.postId(userEntity.getPostName()));
			// 修改一条用户记录
			backSysMapping.updateUserById( userEntity.getPhone(), userEntity.getEmail(),userEntity.getNumber(), 
					userEntity.getPostId(),postMapping.getNameById(userEntity.getPostId()), userEntity.getDepartmentId(),departmentMapping.getNameById(userEntity.getDepartmentId()) , originUserName);;
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("BackSysService/updataOneUserEntity, 修改一条用户记录失败, ", e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 删除一条用户记录
	 */
	public String deleteUser(String userName) {
		if (userName == null) {
			return Constants.FAILCODE; 
		}
		try {
			// 删除一条用户记录
			backSysMapping.deleteUserByUserName(userName);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("BackSysService/deleteUser, 删除一条用户记录失败, ", e);
			return Constants.FAILCODE;
		}
	}
	
	
	/**
	 * 公司结构
	 * @return
	 */
	/*
	 * 修改公司名称
	 */
	public String changeCompanyName(String companyName1, String companyName2, HttpServletResponse response) {
		if (companyName1 == null || companyName2 == null) {
			return Constants.FAILCODE; 
		}
		try {
			int id = 1;
			CompanyNameEntity companyNameEntity = backSysMapping.getCompanyNameById(id);
			companyNameEntity.setCompanyName1(companyName1);
			companyNameEntity.setCompanyName2(companyName2);
			backSysMapping.updateCompanyName(companyNameEntity);
			response.addCookie(new Cookie("companyName1", companyName1));
			response.addCookie(new Cookie("companyName2", companyName2));
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("BackSysService/changeCompanyName, 添加岗位失败, ", e);
			return Constants.FAILCODE;
		}
	}
	/*
	 * 添加岗位
	 */
	public String newPost(PostEntity postEntity) {
		if (postEntity == null) {
			return Constants.FAILCODE; 
		}
		try {
			postEntity.setNumber(postEntity.getName());
			postMapping.insert(postEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("BackSysService/newPost, 添加岗位失败, ", e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 添加部门
	 */
	public String newDepartment(DepartmentEntity departmentEntity) {
		if (departmentEntity == null) {
			return Constants.FAILCODE; 
		}
		try {
			departmentEntity.setNumber(departmentEntity.getName());
			departmentMapping.insert(departmentEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("BackSysService/newDepartment, 添加部门失败, ", e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 20220823-thg
	 * 修改岗位名称
	 */
	public String postNameUpdate(String postName, String originName) {
		if(postName==null||originName==null) {
			return Constants.FAILCODE;
		}
		try {
			postMapping.updateNameByName(postName, originName);
			postMapping.updatePostNameFromUserByOriginName(postName, originName);
			return Constants.SUCCESSCODE;
			
		}catch(Exception e) {
			log.info("backSysService/postNameUpdate:修改岗位名称失败");
			return Constants.FAILCODE;
		}
		
	}
	/*
	 * 20220823-thg
	 * 修改部门名称
	 */
	public String departmentNameUpdate(String departmentName, String originName) {
		if(departmentName==null||originName==null) {
			return Constants.FAILCODE;
		}
		try {
			departmentMapping.updateDepartmentNameByName(departmentName, originName);
			departmentMapping.updateDepartmentNameFromUserByOriginName(departmentName, originName);
			return Constants.SUCCESSCODE;
		}catch(Exception e) {
			log.info("backSysService/departmentNameUpdate:修改部门名称失败");
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 删除部门
	 */
	public String delateDepartment(String name) {
		if (name == null) {
			return Constants.FAILCODE; 
		}
		try {
			//删除之前，把其他表有关字段的更新为待定字段，如本项目postId = 16 为“待管理员审核”
			List<UserEntity> allUser = backSysMapping.getAll();
			for (UserEntity userEntity : allUser) {
				if (userEntity.getDepartmentName() == name) {
					userEntity.setDepartmentId(5);
					userEntity.setDepartmentName(postMapping.getNameById(5));
				}else {
					continue;
				}
			}
			departmentMapping.deleteDepartmentByName(name);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("BackSysService/deletePaymentTerm, 删除部门失败, ", e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 删除岗位
	 */
	public String delatePost(String name) {
		if (name == null) {
			return Constants.FAILCODE; 
		}
		try {
			List<UserEntity> allUser = backSysMapping.getAll();
			for (UserEntity userEntity : allUser) {
				if (userEntity.getPostName() == name) {
					userEntity.setPostId(16);
					userEntity.setPostName(postMapping.getNameById(16));
				}else {
					continue;
				}
			}
			postMapping.deletePostByName(name);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("BackSysService/delatePost, 删除岗位失败, ", e);
			return Constants.FAILCODE;
		}
	}
	/**
	 * 付款条件
	 * @return
	 */
	
	/*
	 * 获取全部付款条件
	 */
	public List<PaymentTermEntity> getAllPaymentTerm() {
		try {
			List<PaymentTermEntity> allPaymentTerm = paymentTermMapping.getAll();
			log.info("BackSysService/getPaymentTerm, 获取全部付款条件成功");
			return allPaymentTerm;
		} catch (Exception e) {
			// TODO: handle exception
			log.info("BackSysService/getPaymentTerm, 获取全部付款条件失败, ", e);
			return null;
		}
	}
	
	/*
	 * 添加一条付款条件
	 */
	public String newPaymentTerm(PaymentTermEntity paymentTermEntity) {
		if (paymentTermEntity == null) {
			return Constants.FAILCODE; 
		}
		try {
			paymentTermMapping.insert(paymentTermEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("BackSysService/newPaymentTerm, 添加一条付款条件失败, ", e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 删除一条付款条件
	 */
	public String deletePaymentTerm(int id) {
		if (id == 0) {
			return Constants.FAILCODE; 
		}
		try {
			paymentTermMapping.deletePaymentTermById(id);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("BackSysService/deletePaymentTerm, 删除一条付款条件失败, ", e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 删除一条项目
	 */
	//20220822-thg,方法参数的proname改成projectEntity
	public String projectDelete(ProjectEntity projectEntity, HttpServletRequest req) {
		//20220822-thg
		if (projectEntity == null) {
			return Constants.FAILCODE; 
		}
		try {
			//20220822-thg
			String proname = projectEntity.getName();
			//删除项目表的信息
			projectMapping.deleteProjectByName(proname);
			//删除全部本地文件
			String docPath = Constants.FILEPATH + Constants.PROJECT + proname;
			deleteAllFile(docPath);
			//删除各个流程的信息
			//20220822-thg
			requestMapping.deleteByName(proname);
			//20220822-thg
			signMapping.deleteById(projectEntity.getProSignId());
			//20220822-thg
			designMapping.deleteById(projectEntity.getProDesignId());
			
			List<DesignElectricalEntity> allEle = designElectricalMapping.getAllOut();
			List<DesignMechancisEntity> allMec = designMechancisMapping.getAllOut();
			List<DesignSoftwareEntity> allSof = designSoftwareMapping.getAllOut();
			for (DesignElectricalEntity designEle : allEle) {
				if (designEle.getProjectName().equals(proname)) {
					designElectricalMapping.deleteDesignElectricalById(designEle.getId());
				} else {
					continue;
				}
			}
			for (DesignMechancisEntity designMec : allMec) {
				if (designMec.getProjectName().equals(proname)) {
					designMechancisMapping.deleteDesignMechancisById(designMec.getId());
				} else {
					continue;
				}
			}
			for (DesignSoftwareEntity designSof : allSof) {
				if (designSof.getProjectName().equals(proname)) {
					designSoftwareMapping.deleteDesignSoftwareById(designSof.getId());
				} else {
					continue;
				}
			}
			//20220824-thg,删除生产总表记录
			productMapping.deleteById(projectEntity.getProProductId());
			
			List<ProductOutsourceEntity> allOut = productOutsourceMapping.getAllOut();
			List<ProductPurchaseEntity> allPur = productPurchaseMapping.getAllOut();
			List<ProductProcessEntity> allPro = productProcessMapping.getAllOut();
			for (ProductOutsourceEntity productOut : allOut) {
				if (productOut.getProjectName().equals(proname)) {
					productOutsourceMapping.deleteProductOutsourceById(productOut.getId());
				} else {
					continue;
				}
			}
			for (ProductPurchaseEntity productPur : allPur) {
				if (productPur.getProjectName().equals(proname)) {
					productPurchaseMapping.deleteProductPurchaseById(productPur.getId());
				} else {
					continue;
				}
			}
			for (ProductProcessEntity productPro : allPro) {
				if (productPro.getProjectName().equals(proname)) {
					productProcessMapping.deleteProductProcessById(productPro.getId());
				} else {
					continue;
				}
			}
			//20220824-thg,删除装配表记录
			assemblingMapping.deleteAssemblingById(Integer.valueOf(projectEntity.getProAssemblingId()));
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("BackSysService/projectDelete, 删除一条项目失败, ", e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 三个设计在数据库中存储的文件名给前台列表
	 */
	public String eleMecSofDeleteWeb(String proname, HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			req.setAttribute("status", statusMapping.getStatusById(projectEntity.getStatusId()).getStatusName());
			List<DesignElectricalEntity> allEle = designElectricalMapping.getAllOut();
			List<DesignMechancisEntity> allMec = designMechancisMapping.getAllOut();
			List<DesignSoftwareEntity> allSof = designSoftwareMapping.getAllOut();
			List<String> thisProjectEle = new ArrayList<String>();
			List<String> thisProjectMec = new ArrayList<String>();
			List<String> thisProjectSof = new ArrayList<String>();
			
			for (DesignElectricalEntity designEle : allEle) {
				if (designEle.getProjectName().equals(proname) ) {
					thisProjectEle.add(getFileName(designEle.getBomUrl()));
				}else {
					continue;
				}
			}
			for (DesignMechancisEntity designMec : allMec) {
				if (designMec.getProjectName().equals(proname) ) {
					thisProjectMec.add(getFileName(designMec.getBomUrl()));
				}else {
					continue;
				}
			}
			for (DesignSoftwareEntity designSof : allSof) {
				if (designSof.getProjectName().equals(proname) ) {
					//20220823-thg
					thisProjectSof.add(getFileName3(designSof.getProgUrl()));
				}else {
					continue;
				}
			}
			if(thisProjectEle.isEmpty()) {
				req.setAttribute("thisProjectEle", null);
			}else {
			req.setAttribute("thisProjectElec", thisProjectEle);
			log.info("BackSysService/EleMecSofUpdateWeb, thisProjectEle:{}", JSON.toJSONString(thisProjectEle));
			}
			if(thisProjectMec.isEmpty()) {
				req.setAttribute("thisProjectMec", null);
			}else {
			req.setAttribute("thisProjectMech", thisProjectMec);
			log.info("BackSysService/EleMecSofUpdateWeb, thisProjectMec:{}", JSON.toJSONString(thisProjectMec));
			}
			if(thisProjectSof.isEmpty()) {
				req.setAttribute("thisProjectSof", null);
			}else {
			req.setAttribute("thisProjectSoft", thisProjectSof);
			log.info("BackSysService/EleMecSofUpdateWeb, thisProjectSof:{}", JSON.toJSONString(thisProjectSof));
			}
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	/*
	 * 三个设计的删除
	 */
	public String eleMecSofDelete(String proname, HttpServletRequest req, 
			String eleName, String mecName, String sofName) {
		try {
			List<DesignElectricalEntity> allEle = designElectricalMapping.getAllOut();
			List<DesignMechancisEntity> allMec = designMechancisMapping.getAllOut();
			List<DesignSoftwareEntity> allSof = designSoftwareMapping.getAllOut();
			String docPath = Constants.FILEPATH + Constants.PROJECT + proname + "/design/";
			if(eleName != null) {
				for (DesignElectricalEntity designEle : allEle) {
					if (getFileName(designEle.getBomUrl()).equals(eleName) ) {
						designElectricalMapping.deleteDesignElectricalById(designEle.getId());
						deleteAllFile(docPath + eleName);
					}else {
						continue;
					}
				}
				log.info("电气设计删除文件" + eleName +"成功！");
			}
			if(mecName != null) {
				for (DesignMechancisEntity designMec : allMec) {
					if (getFileName(designMec.getBomUrl()).equals(mecName) ) {
						designMechancisMapping.deleteDesignMechancisById(designMec.getId());
						deleteAllFile(docPath + mecName);
					}else {
						continue;
					}
				}
				log.info("机械设计删除文件" + mecName +"成功！");
			}
			if(sofName != null) {
				for (DesignSoftwareEntity designSof : allSof) {
					//20220823-thg
					if (getFileName3(designSof.getProgUrl()).equals(sofName) ) {
						designSoftwareMapping.deleteDesignSoftwareById(designSof.getId());
						deleteAllFile(docPath + sofName);
					}else {
						continue;
					}
				}
				log.info("软件设计删除文件" + sofName +"成功！");
			}
			
			return Constants.SUCCESSCODE;	
		}catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	
	
	/*
	 * 三个生产在数据库中存储的字段和文件名给前台列表
	 */
	public String outPurProDeleteWeb(String proname, HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			req.setAttribute("status", statusMapping.getStatusById(projectEntity.getStatusId()).getStatusName());
			List<ProductOutsourceEntity> allOut = productOutsourceMapping.getAllOut();
			List<ProductPurchaseEntity> allPur = productPurchaseMapping.getAllOut();
			List<ProductProcessEntity> allPro = productProcessMapping.getAllOut();
			List<ProductOutsourceEntity> thisProjectOut = new ArrayList<ProductOutsourceEntity>();
			List<ProductPurchaseEntity> thisProjectPur = new ArrayList<ProductPurchaseEntity>();
			List<ProductProcessEntity> thisProjectPro = new ArrayList<ProductProcessEntity>();
			
			for (ProductOutsourceEntity productOut : allOut) {
				if (productOut.getProjectName().equals(proname) ) {
					productOut.setFileUrl(getFileName2(productOut.getFileUrl()));
					thisProjectOut.add(productOut);
				}else {
					continue;
				}
			}
			for (ProductPurchaseEntity productPur : allPur) {
				if (productPur.getProjectName().equals(proname) ) {
					thisProjectPur.add(productPur);
				}else {
					continue;
				}
			}
			for (ProductProcessEntity productPro : allPro) {
				if (productPro.getProjectName().equals(proname) ) {
					thisProjectPro.add(productPro);
				}else {
					continue;
				}
			}
			if(thisProjectOut.isEmpty()) {
				req.setAttribute("thisProjectOut", null);
			}else {
			req.setAttribute("thisProjectOut", thisProjectOut);
			log.info("BackSysService/outPurProDeleteWeb, thisProjectOut:{}", JSON.toJSONString(thisProjectOut));
			}
			if(thisProjectPur.isEmpty()) {
				req.setAttribute("thisProjectPur", null);
			}else {
			req.setAttribute("thisProjectPur", thisProjectPur);
			log.info("BackSysService/outPurProDeleteWeb, thisProjectPur:{}", JSON.toJSONString(thisProjectPur));
			}
			if(thisProjectPro.isEmpty()) {
				req.setAttribute("thisProjectPro", null);
			}else {
			req.setAttribute("thisProjectPro", thisProjectPro);
			log.info("BackSysService/outPurProDeleteWeb, thisProjectPro:{}", JSON.toJSONString(thisProjectPro));
			}
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	/*
	 * 三个生产的删除
	 */
	public String outPurProDelete(String proname, HttpServletRequest req, 
			int outId, int purId, int proId) {
		try {
			List<ProductOutsourceEntity> allOut = productOutsourceMapping.getAllOut();
			String docPath = Constants.FILEPATH + Constants.PROJECT + proname;
			if(outId != 0) {
				for (ProductOutsourceEntity productOut : allOut) {
					if (productOut.getId() == outId) {
						//20220823-thg,逻辑错误，要去查已经删除的记录，这两条语句应该换一下顺序
						deleteAllFile(docPath + getFileName(productOutsourceMapping.getProductOutsourceByOutId(outId).getFileUrl()));
						productOutsourceMapping.deleteProductOutsourceById(outId);
					}else {
						continue;
					}
				}
				log.info("外协删除成功！");
			}
			if(purId != 0) {
				productPurchaseMapping.deleteProductPurchaseById(purId);
				log.info("采购删除成功！");
			}
			if(proId != 0) {
				productProcessMapping.deleteProductProcessById(proId);
				log.info("零件加工删除成功！");
			}
			
			return Constants.SUCCESSCODE;	
		}catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	/*
	 * 装配的删除
	 */
	public String assembleDelete(String proname, HttpServletRequest req, int assembleId) {
		try {
			if(assembleId != 0) {
				assemblingMapping.deleteAssemblingById(assembleId);
				log.info("装配删除成功！");
			}
			return Constants.SUCCESSCODE;	
		}catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	/**
	 * 流程管理
	 * @return
	 */
	/*
	 * 20220825-thg,从数据库查目前的流程
	 */
	public String designFieldWeb(HttpServletRequest req) {
		try {
			DesignMecFieldEntity designMecFieldEntity = backSysMapping.getDesignMecField(1);
			req.setAttribute("mecField", designMecFieldEntity);
			DesignEleFieldEntity designEleFieldEntity = backSysMapping.getDesignEleField(1);
			req.setAttribute("eleField", designEleFieldEntity);
			return Constants.SUCCESSCODE;
		}catch(Exception e) {
			log.info("backSysService/designFieldWeb:查询流程失败",e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 电气设计字段名更新
	 */
	public String designEleFieldUpdate(String proname, HttpServletRequest req, DesignEleFieldEntity designEleFieldEntity) {
		try {
			if(designEleFieldEntity != null) {
				backSysMapping.updateDesignEleField(designEleFieldEntity);
				log.info("电气设计字段修改成功！");
			}
			return Constants.SUCCESSCODE;	
		}catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	/*
	 * 机械设计字段名更新
	 */
	public String designMecFieldUpdate(String proname, HttpServletRequest req, DesignMecFieldEntity designMecFieldEntity) {
		try {
			if(designMecFieldEntity != null) {
				backSysMapping.updateDesignMecField(designMecFieldEntity);
				log.info("机械设计字段修改成功！");
			}
			return Constants.SUCCESSCODE;	
		}catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
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
		return split[split.length - 4]+"/"+split[split.length - 3];
	}
	//20220823-thg
	/**
	 * 拿到文件url的文件名
	 * 
	 * @param path
	 * @return
	 */
	private String getFileName3(String path) {
		String[] split = path.split("/");
		return split[split.length - 3]+"/"+split[split.length - 2];
	}
	/**
	 * 拿到文件url的文件名
	 * 
	 * @param path
	 * @return
	 */
	private String getFileName2(String path) {
		String[] split = path.split("/");
		return split[split.length - 2]+"/"+split[split.length - 1];
	}
	
	/**
	 * 删除文件夹
	 */
	public static boolean deleteAllFile(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
//			System.out.println("删除文件夹失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子文件夹
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子文件夹
			else if (files[i].isDirectory()) {
				flag = deleteAllFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
//			System.out.println("删除文件夹失败！");
			return false;
		}
		// 删除当前文件夹
		if (dirFile.delete()) {
//			System.out.println("删除文件夹" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 删除文件
	 * @param fileName
	 * @return
	 */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径只有单个文件
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
//                System.out.println("删除文件" + fileName + "成功！");
                return true;
            } else {
//                System.out.println("删除文件" + fileName + "失败！");
                return false;
            }
        } else {
//            System.out.println(fileName + "不存在！");
            return false;
        }
    }

}
