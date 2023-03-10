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
	 * ??????????????????
	 * 
	 * @return
	 */
	public List<ProjectEntity> getAll() {
		return projectMapping.getAll();
	}

	/**
	 * 
	 * @param projectId ?????????project??????id
	 * @param changeto  ???????????????status_id
	 */
	public void changeStatus(int projectId, int changeto) {

	}

	// ??????????????????1
	public ProjectDto basicInformation(String proname) {
		ProjectDto projectDto = projectDtoMapping.getDtoByName(proname);
		return projectDto;
	}

	// ??????????????????2
	public SignEntity IdInformation(String proname) {
		ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
		SignEntity signEntity = signMapping.getSignById(projectEntity.getProSignId());
		return signEntity;
	}

	/**
	 * ????????????
	 */
	// 2.???????????????
	// ??????requestentity??????
	// ??????project?????????????????????name???status_id???request_id
	public String requestSubmit(RequestEntity requestEntity, HttpServletRequest req) {
		/*
		 * qh 20220402 ??????
		 */
		try {
			ProjectEntity projectEntity = new ProjectEntity();
			projectEntity.setName(requestEntity.getName());
			projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[1]));
			// ???????????????requestId
			String requestByName = requestService.getRequestByName(requestEntity.getName());
			if (requestByName.equals(Constants.FAILCODE)) {
				return Constants.FAILCODE;
			}
			RequestEntity request = JSON.parseObject(requestByName, RequestEntity.class);
			projectEntity.setRequestId(request.getId());
	
			projectMapping.insert(projectEntity); // ??????try catch??????????????????????????????????????????
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}

	/**
	 * ????????????
	 */
	// 1.?????????????????????review????????????
	public String getDescription(String proname) {
		RequestEntity requestEntity = requestMapping.getRequestByName(proname);
		String description = requestEntity.getDescription();
		return description;
	}

	// 2.???????????????
	public String reviewSubmit(String submitPass, String proname, HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			if (submitPass.equals("yes")) {
				projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[2]));
			} else {
				projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[6]));
			}
			projectMapping.update(projectEntity); // ??????try catch??????????????????????????????????????????
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}

	// 3.?????????
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
				log.info("????????????",e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * ????????????
	 */
	// 2.???????????????
	public String signSubmit(SignEntity signEntity, String proname, HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[3]));
			// ???????????????sign??????Id
			String signByPoId = signService.getSignByPoId(signEntity.getPoId());
			if (signByPoId.equals(Constants.FAILCODE)) {
				return Constants.FAILCODE;
			}
			SignEntity sign = JSON.parseObject(signByPoId, SignEntity.class);
			projectEntity.setProSignId(sign.getId());
			projectMapping.update(projectEntity); // ??????try catch??????????????????????????????????????????
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}
	// 3.?????????
	public String sigDetail(String proname, HttpServletRequest req) {
		try {
			// qh 20220414 ????????????????????????????????????????????????????????????????????????????????????
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			String signByName = JSON.toJSONString(signMapping.getSignById(projectEntity.getProSignId()));
			if (signByName.equals(Constants.FAILCODE)) {
				return Constants.FAILCODE;
			}
			return signByName;
			
		} catch (Exception e) {
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}
	/**
	 * ????????????
	 */
	/**
	 * ??????????????????
	 */
	// 2.???????????????
	public String designSubmit(DesignEntity designEntity, String proname, HttpServletRequest req) {
		try {
			DesignRelationEntity designRelationEntity = new DesignRelationEntity();
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			// ???????????????designId
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
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * ??????????????????????????????
	 */
	public String reDesignSubmit(String mecEndDate, String eleEndDate, String sofEndDate, String poId) {
		try {
				designMapping.updateEndDate(mecEndDate, eleEndDate, sofEndDate, poId);
			log.info("ProjectService.reDesignSubmit:????????????????????????????????????");
			return Constants.SUCCESS;
		}
		catch(Exception e) {
			log.info("ProjectService.reDesignSubmit:????????????????????????????????????",e);
			return Constants.FAILCODE;
		}
	}
	// 3.?????????
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
				log.info("????????????", e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * ????????????
	 */
	// 2.???????????????
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
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}

	//20220826-thg,????????????????????????
	public String getEleFieldEntity(HttpServletRequest req) {
		try {
			DesignEleFieldEntity eleField = projectMapping.getEleFieldEntity();
			log.info("projectService/getEleFieldEntity:??????????????????????????????");
			req.setAttribute("eleFieldEntity", eleField);
			return Constants.SUCCESSCODE;
		}catch (Exception e) {
			// TODO: handle exception
			log.info("projectService/getEleFieldEntity:??????????????????????????????",e);
			return Constants.FAILCODE;
		}
	}
	// 3.?????????
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
			//???????????????????????????thisProjectElec???????????????????????????????????????
			if(thisProjectElec.isEmpty()) {
				log.info("????????????");
				req.setAttribute("thisProjectElec", null);
			}else {
				req.setAttribute("thisProjectElec", thisProjectElec);
				log.info("ProjectService/designEleDetail, thisProjectElec:{}", JSON.toJSONString(thisProjectElec));
			}
			return Constants.SUCCESSCODE;
			
		} catch (Exception e) {
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}

	/**
	 * ????????????
	 */
	// 2.???????????????
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
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
		
		
	}
	
	//20220826-thg,????????????????????????
	public String getMecFieldEntity(HttpServletRequest req) {
		try {
			DesignMecFieldEntity mecField = projectMapping.getMecFieldEntity();
			log.info("projectService/getmecFieldEntity:??????????????????????????????");
			req.setAttribute("mecFieldEntity", mecField);
			return Constants.SUCCESSCODE;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.info("projectService/getMecFieldEntity:??????????????????????????????",e);
			return Constants.FAILCODE;
		}
	}
	
	// 3.?????????
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
					log.info("????????????");
					req.setAttribute("thisProjectMech", null);
				}else {
					req.setAttribute("thisProjectMech", thisProjectMech);
					log.info("ProjectService/designMecDetail, thisProjectMech:{}", JSON.toJSONString(thisProjectMech));
				}
				return Constants.SUCCESSCODE;
				
			} catch (Exception e) {
				log.info("????????????", e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * ????????????
	 */
	// 2.???????????????
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
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}

	// 3.?????????
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
				log.info("????????????");
				req.setAttribute("thisProjectSoft", null);
			}else {
				req.setAttribute("thisProjectSoft", thisProjectSoft);
				log.info("ProjectService/softwareDesignDetail, thisProjectSoft:{}", JSON.toJSONString(thisProjectSoft));
			}
			return Constants.SUCCESSCODE;
			
		} catch (Exception e) {
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}
	// ???????????????????????????
	public String designPassSubmit(String passSubmit, String proname, HttpServletRequest req) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			if (passSubmit.equals("yes")) {
				projectEntity.setStatusId(statusService.getIdByStatusName(Constants.statuss[4]));
				
				// ???pro_design??????3???id
				String designById = designService.getDesignById(projectEntity.getProDesignId());
				DesignEntity designEntity = JSON.parseObject(designById, DesignEntity.class);
				// ?????????????????????
				ProductEntity productEntity = new ProductEntity();
				productEntity.setPoId(designEntity.getPoId());
				productEntity.setSignId(designEntity.getSignId());
				productEntity.setProId(designEntity.getProId());
				// qh 20220408 ???productEntity??????????????????int,????????????0???????????????
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
			log.info("??????????????????", e);
			return Constants.FAILCODE;
		}
	}

	// ???????????????????????????
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
			log.info("??????????????????", e);
			return Constants.FAILCODE;
		}
	}
	// ???????????????????????????
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
				log.info("??????????????????", e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * ????????????
	 */

	/*
	 * 1. ??????????????????????????????????????????????????????pro_product???
	 */
	/**
	 * ????????????
	 */
	// 2.???????????????
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
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}

	// 3.?????????
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
				log.info("????????????");
				req.setAttribute("thisProjectOut", null);
			}else {
			req.setAttribute("thisProjectOut", thisProjectOut);
			log.info("ProjectService/productOutDetail, thisProjectOut:{}", JSON.toJSONString(thisProjectOut));
			}
			return Constants.SUCCESSCODE;
			
			
		} catch (Exception e) {
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}

	/**
	 * ??????????????????
	 */
	// 2.???????????????
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
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}
	// 3.?????????
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
					log.info("????????????");
					req.setAttribute("thisProjectPro", null);
				}else {
					req.setAttribute("thisProjectPro", thisProjectPro);
					log.info("ProjectService/productProDetail, thisProjectPro:{}", JSON.toJSONString(thisProjectPro));
				}
				return Constants.SUCCESSCODE;
				
			} catch (Exception e) {
				log.info("????????????", e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * ????????????
	 */
	// 2.???????????????
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
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}
	// 3.?????????
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
					log.info("????????????");
					req.setAttribute("thisProjectPur", null);
				}else {
					req.setAttribute("thisProjectPur", thisProjectPur);
					log.info("ProjectService/productPurDetail, thisProjectPur:{}", JSON.toJSONString(thisProjectPur));
				}
				return Constants.SUCCESSCODE;
				
			} catch (Exception e) {
				log.info("????????????", e);
				return Constants.FAILCODE;
			}
		}
	/**
	 * ????????????
	 */

	// 2.???????????????
	public String assemblingSubmit(AssemblingEntity assemblingEntity, String proname, HttpServletRequest req) {
		try{
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);

			//20220824-thg,??????????????????????????????id???proname??????id
			AssemblingEntity pr = assemblingMapping.getAssembleByPoId(assemblingEntity.getPoId());
			if(pr==null) {
				log.info("projectService/assemblingService:??????????????????????????????????????????");
				return Constants.FAILCODE;
			}
			int id=pr.getId();
			projectEntity.setProAssemblingId(id);
			projectMapping.update(projectEntity);
			
			return Constants.SUCCESS;
		} catch (Exception e) {
			log.info("????????????", e);
			return Constants.FAILCODE;
		}
	}

	// 3.?????????
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
				log.info("????????????");
				req.setAttribute("thisProjectAss", null);
			}else {
				req.setAttribute("thisProjectAss", thisProjectAss);
				log.info("ProjectService/AssemblingDetail, thisProjectAss:{}", JSON.toJSONString(thisProjectAss));
			}
			return Constants.SUCCESSCODE;
			
			} catch (Exception e) {
				log.info("????????????", e);
				return Constants.FAILCODE;
			}
		}
	
	
	public AssemblingEntity assembleIsSubmitted(String poId) {
		  return assemblingMapping.getAssembleByPoId(poId);
		 }
	
	/*
	 * ??????????????????????????????
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
	 * ????????????url????????????
	 * 
	 * @param path
	 * @return
	 */
	private String getFileName(String path) {
		String[] split = path.split("/");
		return split[split.length - 1];
	}
}
