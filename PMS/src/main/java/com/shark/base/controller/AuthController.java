package com.shark.base.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.shark.backendSystem.entity.CompanyNameEntity;
import com.shark.backendSystem.mapper.BackSysMapping;
import com.shark.base.dto.ProjectDto;
import com.shark.base.util.BaseUtil;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.RequestEntity;
import com.shark.project.entity.design.DesignEntity;
import com.shark.project.mapper.design.DesignMapping;
import com.shark.users.controller.UserController;
import com.shark.users.entity.UserEntity;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthController extends BaseUtil{

	
	@Autowired
	protected DesignMapping designMapping;
	@Autowired
	protected BackSysMapping backSysMapping;
	/*
	 * 根据登录信息，分析权限
	 */
	public String auth(UserEntity userEntity, String userName, HttpServletRequest req, HttpServletResponse response) {
		//1. isAdmin -> 管理员权限判断(高级管理员)
		int postId = userEntity.getPostId();
		String iconPath = userEntity.getIcon();
		Cookie cookie = new Cookie("iconPath",iconPath);
		response.addCookie(cookie);
		//传递公司名字
		int companyNameid = 1;
		CompanyNameEntity companyNameEntity = backSysMapping.getCompanyNameById(companyNameid);
		response.addCookie(new Cookie("companyName1",companyNameEntity.getCompanyName1()));
		response.addCookie(new Cookie("companyName2",companyNameEntity.getCompanyName2()));
		req.setAttribute(Constants.COMPANYNAMEENTITY, companyNameEntity);	
//		if(userEntity.isAdmin()) {
		if(postId == 1) {
			req.setAttribute(Constants.POSTID, postId);
			return toIndex(req, response);
		}else {
			if(postId == 2) {
				// 商务人员-新建项目（看到已完成新建的项目）
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					if(project.getStatusId() > 1) {
					finished.add(projectDtoMapping.getDtoById(project.getId()));
					}else {
						continue;
					}
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, requestFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}else if(postId == 3) {
				// 商务人员-评审项目
				List<ProjectDto> pending = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[1]);
				for (int id : projectIdListBystatus) {
					pending.add(projectDtoMapping.getDtoById(id));
				}
				req.setAttribute(Constants.PENDING, pending);
				log.info("AuthController/auth, review:{}", JSON.toJSON(pending));
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					if(project.getStatusId() > 2) {
					finished.add(projectDtoMapping.getDtoById(project.getId()));
					}else {
						continue;
					}
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, reviewFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else if(postId == 4) {
				//商务人员-签订项目
				List<ProjectDto> pending = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[2]);
				for (int id : projectIdListBystatus) {
					pending.add(projectDtoMapping.getDtoById(id));
				}
				req.setAttribute(Constants.PENDING, pending);
				log.info("AuthController/auth, sign:{}", JSON.toJSON(pending));
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					if(project.getStatusId() > 3 && project.getStatusId() != 7) {
					finished.add(projectDtoMapping.getDtoById(project.getId()));
					}else {
						continue;
					}
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, signFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else if(postId == 5) {
				//项目设计-总设计师
				List<ProjectDto> processing = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[3]);
				for (int id : projectIdListBystatus) {
					processing.add(projectDtoMapping.getDtoById(id));
				}
				req.setAttribute(Constants.PROCESSING, processing);
				log.info("AuthController/auth, design:{}", JSON.toJSON(processing));
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					if(project.getStatusId() > 4) {
					finished.add(projectDtoMapping.getDtoById(project.getId()));
					}else {
						continue;
					}
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, designFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else if(postId == 6) {
				//项目设计-机械设计师
				List<ProjectDto> processing = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[3]);
				for (int id : projectIdListBystatus) {
					ProjectEntity projectEntity =  projectMapping.getProjectById(id);
					DesignEntity designEntity = designMapping.getDesignById(projectEntity.getProDesignId());
					if(designEntity == null) {
						continue;
					}else if(designEntity.getMecEngineer().equals(userName)) {
						processing.add(projectDtoMapping.getDtoById(id));
					}			
				}
				req.setAttribute(Constants.PROCESSING, processing);
				log.info("AuthController/auth, design:{}", JSON.toJSON(processing));
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					DesignEntity designEntity = designMapping.getDesignById(project.getProDesignId());
					if(designEntity == null) {
						continue;
					}else if(designEntity.getMecEngineer().equals(userName)) {
						if(project.getStatusId() > 4) {
							finished.add(projectDtoMapping.getDtoById(project.getId()));
						}else {
							continue;
						}
					}	
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, designFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else if(postId == 7) {
				//项目设计-电气设计师
				List<ProjectDto> processing = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[3]);
				for (int id : projectIdListBystatus) {
					ProjectEntity projectEntity =  projectMapping.getProjectById(id);
					DesignEntity designEntity = designMapping.getDesignById(projectEntity.getProDesignId());
					if(designEntity == null) {
						continue;
					}else if(designEntity.getEleEngineer().equals(userName)) {
						processing.add(projectDtoMapping.getDtoById(id));
					}
				}
				req.setAttribute(Constants.PROCESSING, processing);
				log.info("AuthController/auth, design:{}", JSON.toJSON(processing));
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					DesignEntity designEntity = designMapping.getDesignById(project.getProDesignId());
					if(designEntity == null) {
						continue;
					}else if(designEntity.getEleEngineer().equals(userName)) {
						if(project.getStatusId() > 4) {
							finished.add(projectDtoMapping.getDtoById(project.getId()));
						}else {
							continue;
						}
					}	
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, designFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else if(postId == 8) {
				//项目设计-软件设计师
				List<ProjectDto> processing = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[3]);
				for (int id : projectIdListBystatus) {
					ProjectEntity projectEntity =  projectMapping.getProjectById(id);
					DesignEntity designEntity = designMapping.getDesignById(projectEntity.getProDesignId());
					if(designEntity == null) {
						continue;
					}else if(designEntity.getSofEngineer().equals(userName)) {
						processing.add(projectDtoMapping.getDtoById(id));
					}	
				}
				req.setAttribute(Constants.PROCESSING, processing);
				log.info("AuthController/auth, design:{}", JSON.toJSON(processing));
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					DesignEntity designEntity = designMapping.getDesignById(project.getProDesignId());
					if(designEntity == null) {
						continue;
					}else if(designEntity.getSofEngineer().equals(userName)) {
						if(project.getStatusId() > 4) {
							finished.add(projectDtoMapping.getDtoById(project.getId()));
						}else {
							continue;
						}
					}	
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, designFinished:{}", JSON.toJSON(finished));
				
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else if(postId == 9) {
				//项目设计-审核人员
				List<ProjectDto> processing = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[3]);
				for (int id : projectIdListBystatus) {
					processing.add(projectDtoMapping.getDtoById(id));
				}
				req.setAttribute(Constants.PROCESSING, processing);
				log.info("AuthController/auth, design:{}", JSON.toJSON(processing));
				
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					if(project.getStatusId() > 4 && project.getStatusId() != 7) {
					finished.add(projectDtoMapping.getDtoById(project.getId()));
					}else {
						continue;
					}
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, designFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else if(postId == 10) {
				//项目生产-添加外协的人员
				List<ProjectDto> processing = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[4]);
				for (int id : projectIdListBystatus) {
					processing.add(projectDtoMapping.getDtoById(id));
				}
				req.setAttribute(Constants.PROCESSING, processing);
				log.info("AuthController/auth, product:{}", JSON.toJSON(processing));
				
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					if(project.getStatusId() > 5 && project.getStatusId() != 7) {
					finished.add(projectDtoMapping.getDtoById(project.getId()));
					}else {
						continue;
					}
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, productFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else if(postId == 11) {
				//项目生产-采购的人员
				List<ProjectDto> processing = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[4]);
				for (int id : projectIdListBystatus) {
					processing.add(projectDtoMapping.getDtoById(id));
				}
				req.setAttribute(Constants.PROCESSING, processing);
				log.info("AuthController/auth, product:{}", JSON.toJSON(processing));
				
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					if(project.getStatusId() > 5 && project.getStatusId() != 7) {
					finished.add(projectDtoMapping.getDtoById(project.getId()));
					}else {
						continue;
					}
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, productFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else if(postId == 12) {
				//项目生产-加工的人员
				List<ProjectDto> processing = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[4]);
				for (int id : projectIdListBystatus) {
					processing.add(projectDtoMapping.getDtoById(id));
				}
				req.setAttribute(Constants.PROCESSING, processing);
				log.info("AuthController/auth, product:{}", JSON.toJSON(processing));
				
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					if(project.getStatusId() > 5 && project.getStatusId() != 7) {
					finished.add(projectDtoMapping.getDtoById(project.getId()));
					}else {
						continue;
					}
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, productFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else if(postId == 13) {
				//项目生产-审核人员
				List<ProjectDto> processing = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[4]);
				for (int id : projectIdListBystatus) {
					processing.add(projectDtoMapping.getDtoById(id));
				}
				req.setAttribute(Constants.PROCESSING, processing);
				log.info("AuthController/auth, product:{}", JSON.toJSON(processing));
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					if(project.getStatusId() > 5 && project.getStatusId() != 7) {
					finished.add(projectDtoMapping.getDtoById(project.getId()));
					}else {
						continue;
					}
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, productFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else if(postId == 14){
				//项目装配人员
				List<ProjectDto> processing = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[5]);
				for (int id : projectIdListBystatus) {
					processing.add(projectDtoMapping.getDtoById(id));
				}
				req.setAttribute(Constants.PROCESSING, processing);
				log.info("AuthController/auth, assemble:{}", JSON.toJSON(processing));
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					if(project.getStatusId() > 6 && project.getStatusId() != 7) {
					finished.add(projectDtoMapping.getDtoById(project.getId()));
					}else {
						continue;
					}
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, assembleFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}	
			else if(postId == 15){
				//项目装配-审核人员
				List<ProjectDto> processing = new ArrayList<ProjectDto>();
				List<Integer> projectIdListBystatus = projectMapping.getProjectListBystatus(Constants.statuss[5]);
				for (int id : projectIdListBystatus) {
					processing.add(projectDtoMapping.getDtoById(id));
				}
				req.setAttribute(Constants.PROCESSING, processing);
				log.info("AuthController/auth, assemble:{}", JSON.toJSON(processing));
				
				//该人员已完成的项目
				List<ProjectDto> finished = new ArrayList<ProjectDto>();
				List<ProjectEntity> allProject = projectMapping.getAll();
				for (ProjectEntity project : allProject) {
					if(project.getStatusId() == 8) {
					finished.add(projectDtoMapping.getDtoById(project.getId()));
					}else {
						continue;
					}
				}
				req.setAttribute("finished", finished);
				log.info("AuthController/auth, assembleFinished:{}", JSON.toJSON(finished));
				
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.INDEX;
			}
			else {
				req.setAttribute(Constants.POSTID, postId);	
				return Constants.WAIT;
			}
		}
	}
}
