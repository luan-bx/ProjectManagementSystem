package com.shark.project.service.design;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.shark.project.entity.ProjectEntity;
import com.shark.project.entity.design.DesignElectricalEntity;
import com.shark.project.entity.design.DesignMechancisEntity;
import com.shark.project.entity.design.DesignRelationEntity;
import com.shark.project.mapper.ProjectMapping;
import com.shark.project.mapper.SignMapping;
import com.shark.project.mapper.design.DesignElectricalMapping;
import com.shark.project.mapper.design.DesignRelationMapping;
import com.shark.util.Constants;
import com.shark.util.FileUtil;
import com.shark.util.DownLoadUtil;
import com.shark.util.ZipCompressorUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DesignElectricalService {

	@Autowired
	private DesignElectricalMapping designElectricalMapping;
	@Autowired
	private ProjectMapping projectMapping;
	@Autowired
	private DesignRelationMapping designRelationMapping;
	@Autowired
	private SignMapping signMapping;

	public String insertDesignElectrical(DesignElectricalEntity designElectricalEntity, String proname,
			List<MultipartFile> file, HttpServletRequest req) {
		if (designElectricalEntity == null) {
			return Constants.FAILCODE;
//			没有请求信息传来，弹窗“请求失败，请重新请求”，返回请求页
		}
		try {
			//获取当前时间
			Calendar calendar = Calendar.getInstance();
			String nowTime = (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(calendar.getTime()));
			// 创建文件夹
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			String poId = signMapping.getSignById(projectEntity.getProSignId()).getPoId();
			String docPath = Constants.FILEPATH + Constants.PROJECT + proname + "/design/" + poId + "-electrical_design/"+nowTime+"/";
			// 存文件
			for (MultipartFile f : file) {
				String filename = f.getOriginalFilename();
				if (f == file.get(0)) {
					String docPath1 = docPath + "bomUrl/";
					FileUtil.isDirExist(docPath1);
					String path1 = docPath1 + filename;
					File filePath = new File(path1);
					BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));// 保存文件到目录下
					out.write(f.getBytes());// 在创建好的文件中写入f.getBytes()
					out.flush();
					out.close();
					log.info("for循环：本地储存：" + path1);

					designElectricalEntity.setBomUrl(path1);
					log.info("数据库中BomUrl储存" + designElectricalEntity.getBomUrl());
				} else if (f == file.get(1)) {
					String docPath2 = docPath + "graphUrl/";
					File localPath = new File(docPath2);
					if (!localPath.exists()) { // 获得文件目录，判断目录是否存在，不存在就新建一个
						localPath.mkdirs();
					}
					String path2 = docPath2 + filename;
					File filePath = new File(path2);
					BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));// 保存文件到目录下
					out.write(f.getBytes());// 在创建好的文件中写入f.getBytes()
					out.flush();
					out.close();
					log.info("for循环：本地储存：" + path2);

					designElectricalEntity.setGraphUrl(path2);
					log.info("数据库中GraphUrl储存" + designElectricalEntity.getGraphUrl());
				} else {
					String docPath3 = docPath + "listUrl/";
					File localPath = new File(docPath3);
					if (!localPath.exists()) { // 获得文件目录，判断目录是否存在，不存在就新建一个
						localPath.mkdirs();
					}
					String path3 = docPath3 + filename;
					File filePath = new File(path3);
					BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));// 保存文件到目录下
					out.write(f.getBytes());// 在创建好的文件中写入f.getBytes()
					out.flush();
					out.close();
					log.info("for循环：本地储存：" + path3);

					designElectricalEntity.setListUrl(path3);
					log.info("数据库中ListUrl储存" + designElectricalEntity.getListUrl());
				}
			}

			designElectricalMapping.insert(designElectricalEntity);
			return Constants.SUCCESSCODE;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			req.setAttribute("error", "添加文件失败");
			return Constants.FAILCODE;
		}
	}

	public String getDesignElectricalByBomUrl(String bomUrl) {
		if (bomUrl == null) {
			return Constants.FAILCODE;
		}
		try {
			return JSON.toJSONString(designElectricalMapping.getDesignElectricalByBomUrl(bomUrl));
		} catch (Exception e) {
			log.info("查询失败", e);
			return Constants.FAILCODE;
		}
	}
	
	/*
	 * 下载文件
	 */
	public void downloadFile(String proname, String fileName, HttpServletResponse resp) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			String poId = signMapping.getSignById(projectEntity.getProSignId()).getPoId();
			String name=Constants.FILEPATH + Constants.PROJECT + proname + "/design/" + poId + "-electrical_design"+".zip";
			ZipCompressorUtil zc = new ZipCompressorUtil(name);
			List<String> list=new ArrayList<>();
			list.add(Constants.FILEPATH + Constants.PROJECT + proname + "/design/" + poId + "-electrical_design");
			String[] strings = new String[list.size()];
			list.toArray(strings);
			File file=null;
			try {
			   zc.compress(strings);
			   file=new File(name);
			   DownLoadUtil.downloadZip(file,resp);
			} catch (IOException e) {
			   e.printStackTrace();
			}finally {
			   if(file!=null){
			      file.delete();
			   }
			}
		
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
