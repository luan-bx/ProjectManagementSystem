package com.shark.project.service.design;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shark.base.dto.ProjectDto;
import com.shark.project.entity.PaymentTermEntity;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.design.DesignEntity;
import com.shark.project.entity.design.EleEngineerEntity;
import com.shark.project.entity.design.MecEngineerEntity;
import com.shark.project.entity.design.SofEngineerEntity;
import com.shark.project.mapper.ProjectMapping;
import com.shark.project.mapper.design.DesignMapping;
import com.shark.project.mapper.design.StaffListMapping;
import com.shark.project.service.SignService;
import com.shark.users.entity.UserEntity;
import com.shark.users.mapper.UserMapping;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class StaffListService {
	
	@Autowired
	protected ProjectMapping projectMapping;
	@Autowired
	protected StaffListMapping staffListMapping;
	@Autowired
	protected UserMapping userMapping;
	@Autowired
	protected DesignMapping designMapping;
	
	/**
	 * 返回全部 MecEngineer选项
	 * 
	 * @return
	 */
	public List<String> getMecEngineer() {
		List<String> mecEngineer = new ArrayList<String>();
		List<UserEntity> userAll = userMapping.getAll();
		for (UserEntity user : userAll) {
			if(user.getPostId() == 6) {
				mecEngineer.add(user.getUserName());
			}else{
				continue;
			}
		}
		log.info("StaffListService, mecEngineer:{}", JSON.toJSON(mecEngineer));		
		return mecEngineer;
	}
	/**
	 * 返回全部 EleEngineer选项
	 * 
	 * @return
	 */
	public List<String> getEleEngineer() {
		List<String> eleEngineer = new ArrayList<String>();
		List<UserEntity> userAll = userMapping.getAll();
		for (UserEntity user : userAll) {
			if(user.getPostId() == 7) {
				eleEngineer.add(user.getUserName());
			}else{
				continue;
			}
		}
		log.info("StaffListService, eleEngineer:{}", JSON.toJSON(eleEngineer));		
		return eleEngineer;
	}
	/**
	 * 返回全部 SofEngineer选项
	 * 
	 * @return
	 */
	public List<String> getSofEngineer() {
		List<String> sofEngineer = new ArrayList<String>();
		List<UserEntity> userAll = userMapping.getAll();
		for (UserEntity user : userAll) {
			if(user.getPostId() == 8) {
				sofEngineer.add(user.getUserName());
			}else{
				continue;
			}
		}
		log.info("StaffListService, sofEngineer:{}", JSON.toJSON(sofEngineer));		
		return sofEngineer;
	}
	
	
	
	/**
	 * 返回有任务 MecEngineer选项
	 * 
	 * @return
	 */
	public List<String> getMecEngineerNoBusy() {
		List<String> mecEngineer = new ArrayList<String>();
		List<String> mecEngineerBusy = new ArrayList<String>();
		List<UserEntity> userAll = userMapping.getAll();//可以不用查全部项目，可以试试直接查人
		for (UserEntity user : userAll) {
			if (user.getPostId() == 6) {
				mecEngineer.add(user.getUserName());
				List<DesignEntity> designMec = designMapping.getDesignByMecEngineer(user.getUserName());// 他可能查到好几个相关项目，应该用list
				if (designMec == null) {
					continue;
				}
				//每个人的所有项目查一遍
				for (DesignEntity design : designMec) {
					String start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(design.getMecStartDate());
					String finish = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(design.getMecPredictEndDate());
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						//获取当前时间
						Calendar calendar1 = Calendar.getInstance();
						long nowDate = calendar1.getTime().getTime(); // Date.getTime() 获得毫秒型日期

						long startDate = sdf.parse(start).getTime();
						long finishDate = sdf.parse(finish).getTime();
						// 计算间隔多少天，则除以毫秒到天的转换公式
						long betweenDate1 = (finishDate - nowDate) / (1000 * 60 * 60 * 24);
						long betweenDate2 = (nowDate - startDate) / (1000 * 60 * 60 * 24);

						log.info("机械工程师："+design.getMecEngineer()+"／开始日期" + start + "／结束日期" + finish+"／还有" + betweenDate1+"天结束"+"／已经开始" + betweenDate2+"天");
						
					   //如果已经开始且超过15天才能结束，则视为正在忙的员工
						if(betweenDate1 > 15 ) {
							mecEngineerBusy.add(user.getUserName());
						}
						
					} catch (Exception e) {
						// TODO: handle exceptions
					}		
				}
			} else {
				continue;
			}
		}    
		//所有工程师减去忙碌的工程师
		mecEngineer.removeAll(mecEngineerBusy);
		log.info("StaffListService, mecEngineerNoBusy:{}", JSON.toJSON(mecEngineer));		
		return mecEngineer;
	}
	
	
	/**
	 * 返回EleEngineer选项
	 * 
	 * @return
	 */
	public List<String> getEleEngineerNoBusy() {
		List<String> eleEngineer = new ArrayList<String>();
		List<String> eleEngineerBusy = new ArrayList<String>();
		List<UserEntity> userAll = userMapping.getAll();
		for (UserEntity user : userAll) {
			if(user.getPostId() == 7) {
				eleEngineer.add(user.getUserName());
				List<DesignEntity> designEle = designMapping.getDesignByEleEngineer(user.getUserName());// 他可能查到好几个相关项目，应该用list
				if (designEle == null) {
					continue;
				}
				for (DesignEntity design : designEle) {
					String start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(design.getEleStartDate());
					String finish = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(design.getElePredictEndDate());
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						//获取当前时间
						Calendar calendar1 = Calendar.getInstance();
						long nowDate = calendar1.getTime().getTime(); // Date.getTime() 获得毫秒型日期

						long startDate = sdf.parse(start).getTime();
						long finishDate = sdf.parse(finish).getTime();
						// 计算间隔多少天，则除以毫秒到天的转换公式
						long betweenDate1 = (finishDate - nowDate) / (1000 * 60 * 60 * 24);
						long betweenDate2 = (nowDate - startDate) / (1000 * 60 * 60 * 24);

						log.info("电气工程师："+design.getEleEngineer()+"／开始日期" + start + "／结束日期" + finish+"／还有" + betweenDate1+"天结束"+"／已经开始" + betweenDate2+"天");
					
					   //如果已经开始且超过15天才能结束，则视为正在忙的员工
						if(betweenDate1 > 15) {
							eleEngineerBusy.add(user.getUserName());
						}
						
					} catch (Exception e) {
						// TODO: handle exceptions
					}		
				}
			}else{
				continue;
			}
		}
		//所有工程师减去忙碌的工程师
		eleEngineer.removeAll(eleEngineerBusy);
		log.info("StaffListService, eleEngineerNoBusy:{}", JSON.toJSON(eleEngineer));		
		return eleEngineer;
	}
	/**
	 * 返回SofEngineer选项
	 * 
	 * @return
	 */
	public List<String> getSofEngineerNoBusy() {
		List<String> sofEngineer = new ArrayList<String>();
		List<String> sofEngineerBusy = new ArrayList<String>();
		List<UserEntity> userAll = userMapping.getAll();
		for (UserEntity user : userAll) {
			if(user.getPostId() == 8) {
				sofEngineer.add(user.getUserName());
				List<DesignEntity> designSof = designMapping.getDesignBySofEngineer(user.getUserName());// 他可能查到好几个相关项目，应该用list
				if (designSof == null) {
					continue;
				}
				for (DesignEntity design : designSof) {
					String start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(design.getSofStartDate());
					String finish = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(design.getSofPredictEndDate());
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						//获取当前时间
						Calendar calendar1 = Calendar.getInstance();
						long nowDate = calendar1.getTime().getTime(); // Date.getTime() 获得毫秒型日期

						long startDate = sdf.parse(start).getTime();
						long finishDate = sdf.parse(finish).getTime();
						// 计算间隔多少天，则除以毫秒到天的转换公式
						long betweenDate1 = (finishDate - nowDate) / (1000 * 60 * 60 * 24);
						long betweenDate2 = (nowDate - startDate) / (1000 * 60 * 60 * 24);

						log.info("软件工程师："+design.getSofEngineer()+"／开始日期" + start + "／结束日期" + finish+"／还有" + betweenDate1+"天结束"+"／已经开始" + betweenDate2+"天");
						
					   //如果已经开始且超过15天才能结束，则视为正在忙的员工
						if(betweenDate1 > 15 ) {
							sofEngineerBusy.add(user.getUserName());
						}
						
					} catch (Exception e) {
						// TODO: handle exceptions
					}		
				}
			}else{
				continue;
			}
		}
		//所有工程师减去忙碌的工程师
		sofEngineer.removeAll(sofEngineerBusy);
		log.info("StaffListService, sofEngineerNoBusy:{}", JSON.toJSON(sofEngineer));		
		return sofEngineer;
	}
	


}
