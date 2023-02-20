package com.shark.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shark.project.entity.StatusEntity;
import com.shark.project.mapper.StatusMapping;
import com.shark.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatusService {

	@Autowired
	private StatusMapping statusMapping;
	
	public String insertstatus(StatusEntity statusEntity) {
		if(statusEntity == null) {
			return Constants.FAILCODE;
//			没有请求信息传来，弹窗“请求失败，请重新请求”，返回请求页
		}
		try {
			
			statusMapping.insert(statusEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("插入项目状态失败");
			return Constants.FAILCODE;
		}
	}	
	
	/**
	 * 根据状态名称查询Id
	 * @param statusName
	 * @return
	 */
	public int getIdByStatusName(String statusName) {
		return statusMapping.getIdByStatusName(statusName);
	}
	
	public String passupdateStatus(String passcurstatusName) {
		if(passcurstatusName == null) {
			return Constants.FAILCODE;
		}
		try {		
			for (int i = 0; i < Constants.statuss.length; i++) {
				if (Constants.statuss[i]==passcurstatusName) {
					int index = i ;
					String passnewStatus = Constants.statuss[index + 1];
					statusMapping.updateStatusName(passcurstatusName, passnewStatus);
					}
			}
			log.info("修改成功");
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("修改失败");
			return Constants.FAILCODE;
		}
	}
	
	public String noPassupdateStatus(String noPasscurstatusName) {
		if(noPasscurstatusName == null) {
			return Constants.FAILCODE;
		}
		try {		
			for (int i = 0; i < Constants.statuss.length; i++) {
				if (Constants.statuss[i]==noPasscurstatusName) {
					int index = i ;
					String passnewStatus = Constants.statuss[index - 1];
					statusMapping.updateStatusName(noPasscurstatusName, passnewStatus);
					}
			}
			log.info("修改成功");
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			log.info("修改失败");
			return Constants.FAILCODE;
		}
	}
	
	/**
	 * 返回所有状态信息
	 * @return
	 */
	public List<StatusEntity> getAllStatus(){
		return statusMapping.getAllStatus();
	}
	
}
