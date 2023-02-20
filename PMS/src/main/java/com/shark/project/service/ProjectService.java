package com.shark.project.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shark.backendSystem.entity.DesignEleFieldEntity;
import com.shark.backendSystem.entity.DesignMecFieldEntity;
import com.shark.base.dto.ProjectDto;
import com.shark.base.mapper.ProjectDtoMapping;
import com.shark.project.entity.AssemblingEntity;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.RequestEntity;
import com.shark.project.entity.SignEntity;
import com.shark.project.entity.design.DesignElectricalEntity;
import com.shark.project.entity.design.DesignEntity;
import com.shark.project.entity.design.DesignMechancisEntity;
import com.shark.project.entity.design.DesignRelationEntity;
import com.shark.project.entity.design.DesignSoftwareEntity;
import com.shark.project.entity.product.ProductEntity;
import com.shark.project.entity.product.ProductOutsourceEntity;
import com.shark.project.entity.product.ProductProcessEntity;
import com.shark.project.entity.product.ProductPurchaseEntity;
import com.shark.project.entity.product.SeveralSubmissions.OutSevSubEntity;
import com.shark.project.mapper.AssemblingMapping;
import com.shark.project.mapper.ProjectMapping;
import com.shark.project.mapper.RequestMapping;
import com.shark.project.mapper.SignMapping;
import com.shark.project.mapper.StatusMapping;
import com.shark.project.mapper.design.DesignElectricalMapping;
import com.shark.project.mapper.design.DesignMapping;
import com.shark.project.mapper.design.DesignMechancisMapping;
import com.shark.project.mapper.design.DesignRelationMapping;
import com.shark.project.mapper.design.DesignSoftwareMapping;
//import com.shark.project.mapper.design.SeveraSubmissions.EleSevSubMapping;
//import com.shark.project.mapper.design.SeveraSubmissions.MecSevSubMapping;
//import com.shark.project.mapper.design.SeveraSubmissions.SofSevSubMapping;
import com.shark.project.mapper.product.ProductMapping;
import com.shark.project.mapper.product.ProductOutsourceMapping;
import com.shark.project.mapper.product.ProductProcessMapping;
import com.shark.project.mapper.product.ProductPurchaseMapping;
//import com.shark.project.mapper.product.SeveraSubmissions.OutSevSubMapping;
//import com.shark.project.mapper.product.SeveraSubmissions.ProSevSubMapping;
//import com.shark.project.mapper.product.SeveraSubmissions.PurSevSubMapping;
import com.shark.project.service.design.DesignElectricalService;
import com.shark.project.service.design.DesignMechancisService;
import com.shark.project.service.design.DesignService;
import com.shark.project.service.design.DesignSoftwareService;
//import com.shark.project.service.design.SeveralSubmissions.EleSevSubService;
//import com.shark.project.service.design.SeveralSubmissions.MecSevSubService;
//import com.shark.project.service.design.SeveralSubmissions.SofSevSubService;
import com.shark.project.service.product.ProductOutsourceService;
import com.shark.project.service.product.ProductProcessService;
import com.shark.project.service.product.ProductPurchaseService;
import com.shark.project.service.product.ProductService;
//import com.shark.project.service.product.SeveralSubmissions.OutSevSubService;
//import com.shark.project.service.product.SeveralSubmissions.ProSevSubService;
//import com.shark.project.service.product.SeveralSubmissions.PurSevSubService;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProjectService {

	@Autowired
	private ProjectMapping projectMapping;
	@Autowired
	private ProjectDtoMapping projectDtoMapping;
	@Autowired
	private StatusService statusService;
	@Autowired
	private RequestService requestService;
	@Autowired
	private SignService signService;
	@Autowired
	private SignMapping signMapping;
	@Autowired
	private RequestMapping requestMapping;
	@Autowired
	private DesignRelationMapping designRelationMapping;
	@Autowired
	private ProductMapping productMapping;
	@Autowired
	private DesignService designService;
	@Autowired
	private DesignElectricalService designElectricalService;
	@Autowired
	private DesignMechancisService designMechancisService;
	@Autowired
	private DesignSoftwareService designSoftwareService;
	@Autowired
	private ProductOutsourceService productOutsourceService;
	@Autowired
	private ProductProcessService productProcessService;
	@Autowired
	private ProductPurchaseService productPurchaseService;
	@Autowired
	private AssemblingService assemblingService;
	@Autowired
	private ProductService productService;
	@Autowired
	private DesignMechancisMapping designMechancisMapping;
	@Autowired
	private DesignMapping designMapping;
	@Autowired
	private DesignElectricalMapping designElectricalMapping;
	@Autowired
	private DesignSoftwareMapping designSoftwareMapping;
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

	/**
	 * 返回所有项目
	 * 
	 * @return
	 */
	public List<ProjectEntity> getAll() {
		return projectMapping.getAll();
	}

	/**
	 * 
	 * @param projectId 变更的project表的id
	 * @param changeto  需要变成的status_id
	 */
	public void changeStatus(int projectId, int changeto) {

	}

	// 首页点击按钮1
	public ProjectDto basicInformation(String proname) {
		ProjectDto projectDto = projectDtoMapping.getDtoByName(proname);
		return projectDto;
	}

	// 首页点击按钮2
	public SignEntity IdInformation(String proname) {
		ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
		SignEntity signEntity = signMapping.getSignById(projectEntity.getProSignId());
		return signEntity;
	}

	/**
	 * 新建项目
	 */
	// 2.调用方法：
	// 插入requestentity记录
	// 插入project记录，里面存放name、status_id、request_id
	public String requestSubmit(RequestEntity requestEntity, HttpServletRequest req) {
		/*
		 * qh 20220402 改动
		 */
		try {
			ProjectEntity projectEntity = new ProjectEntity();
			projectEntity.setName(requestEntity.getName());
			projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[1]));
			// 从数据库取requestId
			String requestByName = requestService.getRequestByName(requestEntity.getName());
			if (requestByName.equals(Constants.FAILCODE)) {
				return Constants.FAILCODE;
			}
			RequestEntity request = JSON.parseObject(requestByName, RequestEntity.class);
			projectEntity.setRequestId(request.getId());
	
			projectMapping.insert(projectEntity); // 很多try catch先不做，后面进行全局异常捕捉
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("提交失败", e);
			return Constants.FAILCODE;
		}
	}

	/**
	 * 项目评审
	 */
	// 1.首页点击按钮，review特殊字段
	public String getDescription(String proname) {
		RequestEntity requestEntity = requestMapping.getRequestByName(proname);
		String description = requestEntity.getDescription();
		return description;
	}

	// 2.调用方法：
	public String reviewSubmit(String submitPass, String proname, HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			if (submitPass.equals("yes")) {
				projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[2]));
			} else {
				projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[6]));
			}
			projectMapping.update(projectEntity); // 很多try catch先不做，后面进行全局异常捕捉
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("提交失败", e);
			return Constants.FAILCODE;
		}
	}

	// 3.详情：
		public String revDetail(String proname, HttpServletRequest req) {
			try {		
				String requestByName = JSON.toJSONString(requestMapping.getRequestByName(proname));
				if (requestByName.equals(Constants.FAILCODE)) {
					return Constants.FAILCODE;
				}
				log.info(requestByName);
				return requestByName;
			} 
			catch (Exception e) {
				log.info("查询失败",e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * 项目签订
	 */
	// 2.调用方法：
	public String signSubmit(SignEntity signEntity, String proname, HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[3]));
			// 从数据库取sign表的Id
			String signByPoId = signService.getSignByPoId(signEntity.getPoId());
			if (signByPoId.equals(Constants.FAILCODE)) {
				return Constants.FAILCODE;
			}
			SignEntity sign = JSON.parseObject(signByPoId, SignEntity.class);
			projectEntity.setProSignId(sign.getId());
			projectMapping.update(projectEntity); // 很多try catch先不做，后面进行全局异常捕捉
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("提交失败", e);
			return Constants.FAILCODE;
		}
	}
	// 3.详情：
	public String sigDetail(String proname, HttpServletRequest req) {
		try {
			// qh 20220414 这地方详情的查询，改为对新建审核文件对查询（和评审一样）
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			String signByName = JSON.toJSONString(signMapping.getSignById(projectEntity.getProSignId()));
			if (signByName.equals(Constants.FAILCODE)) {
				return Constants.FAILCODE;
			}
			return signByName;
			
		} catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	/**
	 * 项目设计
	 */
	/**
	 * 一、基本信息
	 */
	// 2.调用方法：
	public String designSubmit(DesignEntity designEntity, String proname, HttpServletRequest req) {
		try {
			DesignRelationEntity designRelationEntity = new DesignRelationEntity();
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			// 从数据库取designId
			String designByName = designService.getDesignByPoId(designEntity.getPoId());
			if (designByName.equals(Constants.FAILCODE)) {
				return Constants.FAILCODE;
			}
			DesignEntity design = JSON.parseObject(designByName, DesignEntity.class);
			designRelationEntity.setDesiId(design.getId());
			designRelationMapping.insert(designRelationEntity);
			projectEntity.setProDesignId(design.getId());
	
			projectMapping.update(projectEntity);
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("提交失败", e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 提交设计实际结束时间
	 */
	public String reDesignSubmit(String mecEndDate, String eleEndDate, String sofEndDate, String poId) {
		try {
				designMapping.updateEndDate(mecEndDate, eleEndDate, sofEndDate, poId);
			log.info("ProjectService.reDesignSubmit:插入设计实际结束时间成功");
			return Constants.SUCCESS;
		}
		catch(Exception e) {
			log.info("ProjectService.reDesignSubmit:插入设计实际结束时间失败",e);
			return Constants.FAILCODE;
		}
	}
	// 3.详情：
		public String desDetail(String proname, HttpServletRequest req) {
			try {
				ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
				String designByName = JSON.toJSONString(designMapping.getDesignById(projectEntity.getProDesignId()));
				if (designByName.equals(Constants.FAILCODE)) {
					return Constants.FAILCODE;
				}
				req.setAttribute("status", statusMapping.getStatusById(projectEntity.getStatusId()).getStatusName());
				return designByName;
				
			} catch (Exception e) {
				log.info("查询失败", e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * 二、电器
	 */
	// 2.调用方法：
	public String designElectricalSubmit(DesignElectricalEntity designElectricalEntity, String proname,
			HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			DesignRelationEntity designRelationEntity = designRelationMapping.getDesignRelationByDesiId(projectEntity.getProDesignId());

			if(designRelationEntity.getElecId() == null) {
				designRelationEntity.setElecId(proname);
				designRelationMapping.update(designRelationEntity);
			}
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("提交失败", e);
			return Constants.FAILCODE;
		}
	}

	//20220826-thg,查询电气设计流程
	public String getEleFieldEntity(HttpServletRequest req) {
		try {
			DesignEleFieldEntity eleField = projectMapping.getEleFieldEntity();
			log.info("projectService/getEleFieldEntity:电气设计流程查询成功");
			req.setAttribute("eleFieldEntity", eleField);
			return Constants.SUCCESSCODE;
		}catch (Exception e) {
			// TODO: handle exception
			log.info("projectService/getEleFieldEntity:电气设计流程查询失败",e);
			return Constants.FAILCODE;
		}
	}
	// 3.详情：
	public String designEleDetail(String proname, HttpServletRequest req) {
		try {

			List<DesignElectricalEntity> AllOut = designElectricalMapping.getAllOut();
			List<DesignElectricalEntity> thisProjectElec = new ArrayList<DesignElectricalEntity>();
			
			for (DesignElectricalEntity designElec : AllOut) {
				if (designElec.getProjectName().equals(proname) ) {
					designElec.setBomUrl(getFileName(designElec.getBomUrl()));
					designElec.setGraphUrl(getFileName(designElec.getGraphUrl()));
					designElec.setListUrl(getFileName(designElec.getListUrl()));
					thisProjectElec.add(designElec);
				}else {
					continue;
				}
			}
			//如果为空，不发送“thisProjectElec”到前端。前端显示未查询到
			if(thisProjectElec.isEmpty()) {
				log.info("没有记录");
				req.setAttribute("thisProjectElec", null);
			}else {
				req.setAttribute("thisProjectElec", thisProjectElec);
				log.info("ProjectService/designEleDetail, thisProjectElec:{}", JSON.toJSONString(thisProjectElec));
			}
			return Constants.SUCCESSCODE;
			
		} catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}

	/**
	 * 三、机械
	 */
	// 2.调用方法：
	public String designMechancisSubmit(DesignMechancisEntity designMechancisEntity, String proname,
			HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			DesignRelationEntity designRelationEntity = designRelationMapping.getDesignRelationByDesiId(projectEntity.getProDesignId());

			if(designRelationEntity.getMechId()==null ) {
				designRelationEntity.setMechId(proname);
				designRelationMapping.update(designRelationEntity);
			}
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("提交失败", e);
			return Constants.FAILCODE;
		}
		
		
	}
	
	//20220826-thg,查询机械设计流程
	public String getMecFieldEntity(HttpServletRequest req) {
		try {
			DesignMecFieldEntity mecField = projectMapping.getMecFieldEntity();
			log.info("projectService/getmecFieldEntity:机械设计流程查询成功");
			req.setAttribute("mecFieldEntity", mecField);
			return Constants.SUCCESSCODE;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.info("projectService/getMecFieldEntity:机械设计流程查询失败",e);
			return Constants.FAILCODE;
		}
	}
	
	// 3.详情：
		public String designMecDetail(String proname, HttpServletRequest req) {
			try {

				List<DesignMechancisEntity> AllOut = designMechancisMapping.getAllOut();
				List<DesignMechancisEntity> thisProjectMech = new ArrayList<DesignMechancisEntity>();
				
				for (DesignMechancisEntity designMech : AllOut) {
					if (designMech.getProjectName().equals(proname) ) {
						designMech.setBomUrl(getFileName(designMech.getBomUrl()));
						designMech.setCompUrl(getFileName(designMech.getCompUrl()));
						designMech.setGasUrl(getFileName(designMech.getGasUrl()));
						designMech.setProfUrl(getFileName(designMech.getProfUrl()));
						designMech.setThreeDUrl(getFileName(designMech.getThreeDUrl()));
						designMech.setTwoDUrl(getFileName(designMech.getTwoDUrl()));
						designMech.setVulDrawUrl(getFileName(designMech.getVulDrawUrl()));
						designMech.setVulListUrl(getFileName(designMech.getVulListUrl()));
						
						thisProjectMech.add(designMech);
					}else {
						continue;
					}
				}
				if(thisProjectMech.isEmpty()) {
					log.info("没有记录");
					req.setAttribute("thisProjectMech", null);
				}else {
					req.setAttribute("thisProjectMech", thisProjectMech);
					log.info("ProjectService/designMecDetail, thisProjectMech:{}", JSON.toJSONString(thisProjectMech));
				}
				return Constants.SUCCESSCODE;
				
			} catch (Exception e) {
				log.info("查询失败", e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * 四、软件
	 */
	// 2.调用方法：
	public String designSoftwareSubmit(DesignSoftwareEntity designSoftwareEntity, String proname,
			HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			DesignRelationEntity designRelationEntity = designRelationMapping.getDesignRelationByDesiId(projectEntity.getProDesignId());

			if(designRelationEntity.getSoftId() == null) {
				designRelationEntity.setSoftId(proname);
				designRelationMapping.update(designRelationEntity);
				
			}
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("提交失败", e);
			return Constants.FAILCODE;
		}
	}

	// 3.详情：
	public String softwareDesignDetail(String proname, HttpServletRequest req) {
		try {

			List<DesignSoftwareEntity> AllOut = designSoftwareMapping.getAllOut();
			List<DesignSoftwareEntity> thisProjectSoft = new ArrayList<DesignSoftwareEntity>();
			
			for (DesignSoftwareEntity designSoft : AllOut) {
				if (designSoft.getProjectName().equals(proname) ) {
					designSoft.setProgUrl(getFileName(designSoft.getProgUrl()));
					thisProjectSoft.add(designSoft);
				}else {
					continue;
				}
			}
			if(thisProjectSoft.isEmpty()) {
				log.info("没有记录");
				req.setAttribute("thisProjectSoft", null);
			}else {
				req.setAttribute("thisProjectSoft", thisProjectSoft);
				log.info("ProjectService/softwareDesignDetail, thisProjectSoft:{}", JSON.toJSONString(thisProjectSoft));
			}
			return Constants.SUCCESSCODE;
			
		} catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	// 状态：设计进入生产
	public String designPassSubmit(String passSubmit, String proname, HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			if (passSubmit.equals("yes")) {
				projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[4]));
				
				// 从pro_design表取3个id
				String designById = designService.getDesignById(projectEntity.getProDesignId());
				DesignEntity designEntity = JSON.parseObject(designById, DesignEntity.class);
				// 插入生产关联表
				ProductEntity productEntity = new ProductEntity();
				productEntity.setPoId(designEntity.getPoId());
				productEntity.setSignId(designEntity.getSignId());
				productEntity.setProId(designEntity.getProId());
				// qh 20220408 ，productEntity其他字段，为int,默认值为0，不会报错
				productMapping.insert(productEntity);
				String productByPoId = productService.getProductByPoId(productEntity.getPoId());
				if (productByPoId.equals(Constants.FAILCODE)) {
					return Constants.FAILCODE;
				}
				ProductEntity product = JSON.parseObject(productByPoId, ProductEntity.class);
				projectEntity.setProProductId(product.getId());
				
			} else {
				projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[3]));
			}
		
			projectMapping.update(projectEntity);
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("进入生产失败", e);
			return Constants.FAILCODE;
		}
	}

	// 状态：生产进入装配
	public String productPassSubmit(String passSubmit, String proname, HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			if (passSubmit.equals("yes")) {
				projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[5]));
			} else {
				projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[4]));
			}
			projectMapping.update(projectEntity);
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("进入生产失败", e);
			return Constants.FAILCODE;
		}
	}
	// 状态：装配进入完成
		public String assemblePassSubmit(String passSubmit, String proname, HttpServletRequest req) {
			try {
				ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
				if (passSubmit.equals("yes")) {
					projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[7]));
				} else {
					projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[5]));
				}
				projectMapping.update(projectEntity);
				return Constants.SUCCESS;
			} catch (Exception e) {
				log.info("进入生产失败", e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * 项目生产
	 */

	/*
	 * 1. “点击生产”，到项目生产状态，就插入pro_product表
	 */
	/**
	 * 一、外协
	 */
	// 2.调用方法：
	public String productOutsourceSubmit(ProductOutsourceEntity productOutsourceEntity, String proname,
			HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			ProductEntity productEntity = productMapping.getProductByProductId(projectEntity.getProProductId());
			if(productEntity.getOutId() == null) {
				productEntity.setOutId(proname);
				productMapping.update(productEntity);
				
			}
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}

	// 3.详情：
	public String productOutDetail(String proname, HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			req.setAttribute("status", statusMapping.getStatusById(projectEntity.getStatusId()).getStatusName());
			List<ProductOutsourceEntity> AllOut = productOutsourceMapping.getAllOut();
			List<ProductOutsourceEntity> thisProjectOut = new ArrayList<ProductOutsourceEntity>();
			
			for (ProductOutsourceEntity productOut : AllOut) {
				if (productOut.getProjectName().equals(proname) ) {
					productOut.setFileUrl(getFileName(productOut.getFileUrl()));
					thisProjectOut.add(productOut);
				}else {
					continue;
				}
			}
			if(thisProjectOut.isEmpty()) {
				log.info("没有记录");
				req.setAttribute("thisProjectOut", null);
			}else {
			req.setAttribute("thisProjectOut", thisProjectOut);
			log.info("ProjectService/productOutDetail, thisProjectOut:{}", JSON.toJSONString(thisProjectOut));
			}
			return Constants.SUCCESSCODE;
			
			
		} catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}

	/**
	 * 二、零件加工
	 */
	// 2.调用方法：
	public String productProcessSubmit(ProductProcessEntity productProcessEntity, String proname,
			HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			ProductEntity productEntity = productMapping.getProductByProductId(projectEntity.getProProductId());
			if(productEntity.getProcessId() == null) {
				productEntity.setProcessId(proname);
				productMapping.update(productEntity);
				
			}
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	// 3.详情：
		public String productProDetail(String proname, HttpServletRequest req) {
			try {

				List<ProductProcessEntity> AllOut = productProcessMapping.getAllOut();
				List<ProductProcessEntity> thisProjectPro = new ArrayList<ProductProcessEntity>();
				
				for (ProductProcessEntity productPro : AllOut) {
					if (productPro.getProjectName().equals(proname) ) {
						thisProjectPro.add(productPro);
					}else {
						continue;
					}
				}
				if(thisProjectPro.isEmpty()) {
					log.info("没有记录");
					req.setAttribute("thisProjectPro", null);
				}else {
					req.setAttribute("thisProjectPro", thisProjectPro);
					log.info("ProjectService/productProDetail, thisProjectPro:{}", JSON.toJSONString(thisProjectPro));
				}
				return Constants.SUCCESSCODE;
				
			} catch (Exception e) {
				log.info("查询失败", e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * 三、采购
	 */
	// 2.调用方法：
	public String productPurchaseSubmit(ProductPurchaseEntity productPurchaseEntity, String proname,
			HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			ProductEntity productEntity = productMapping.getProductByProductId(projectEntity.getProProductId());

			if(productEntity.getPurId() == null) {
				productEntity.setPurId(proname);
				productMapping.update(productEntity);
				
			}
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	// 3.详情：
		public String productPurDetail(String proname, HttpServletRequest req) {
			try {

				List<ProductPurchaseEntity> AllOut = productPurchaseMapping.getAllOut();
				List<ProductPurchaseEntity> thisProjectPur = new ArrayList<ProductPurchaseEntity>();
				
				for (ProductPurchaseEntity productPur : AllOut) {
					if (productPur.getProjectName().equals(proname) ) {
						thisProjectPur.add(productPur);
					}else {
						continue;
					}
				}
				if(thisProjectPur.isEmpty()) {
					log.info("没有记录");
					req.setAttribute("thisProjectPur", null);
				}else {
					req.setAttribute("thisProjectPur", thisProjectPur);
					log.info("ProjectService/productPurDetail, thisProjectPur:{}", JSON.toJSONString(thisProjectPur));
				}
				return Constants.SUCCESSCODE;
				
			} catch (Exception e) {
				log.info("查询失败", e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * 项目装配
	 */

	// 2.调用方法：
	public String assemblingSubmit(AssemblingEntity assemblingEntity, String proname, HttpServletRequest req) {
		try{
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);

			//20220824-thg,把插入项目总表的装配id从proname改成id
			AssemblingEntity pr = assemblingMapping.getAssembleByPoId(assemblingEntity.getPoId());
			if(pr==null) {
				log.info("projectService/assemblingService:装配失败，无法查询到装配记录");
				return Constants.FAILCODE;
			}
			int id=pr.getId();
			projectEntity.setProAssemblingId(id);
			projectMapping.update(projectEntity);
			
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("装配失败", e);
			return Constants.FAILCODE;
		}
	}

	// 3.详情：
	public String assembleDetail(String proname, HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);

			req.setAttribute("status", statusMapping.getStatusById(projectEntity.getStatusId()).getStatusName());
		
			List<AssemblingEntity> AllOut = assemblingMapping.getAllOut();
			List<AssemblingEntity> thisProjectAss = new ArrayList<AssemblingEntity>();
			
			for (AssemblingEntity productAss : AllOut) {
				if (productAss.getProjectName().equals(proname) ) {
					thisProjectAss.add(productAss);
				}else {
					continue;
				}
			}
			if(thisProjectAss.isEmpty()) {
				log.info("没有记录");
				req.setAttribute("thisProjectAss", null);
			}else {
				req.setAttribute("thisProjectAss", thisProjectAss);
				log.info("ProjectService/AssemblingDetail, thisProjectAss:{}", JSON.toJSONString(thisProjectAss));
			}
			return Constants.SUCCESSCODE;
			
			} catch (Exception e) {
				log.info("查询失败", e);
				return Constants.FAILCODE;
			}
		}
	
	
	public AssemblingEntity assembleIsSubmitted(String poId) {
		  return assemblingMapping.getAssembleByPoId(poId);
		 }
	
	/*
	 * 提交装配实际完成时间
	 */
	public String reAssembingSubmit(String poId, String meEndDate, String elEndDate, String soEndDate, String checkEndDate) {
		try {
			assemblingMapping.reAssembingSubmit(meEndDate, elEndDate, soEndDate, checkEndDate, poId);
			return Constants.SUCCESS;
		}
		catch(Exception e) {
			log.info("projectService/reAssembingSubmit:",e);
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
		return split[split.length - 1];
	}
}
