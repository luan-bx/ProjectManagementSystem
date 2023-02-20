package com.shark.project.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.shark.project.entity.RequestEntity;
import com.shark.project.service.RequestService;
import com.shark.project.mapper.ProjectMapping;
import com.shark.project.mapper.RequestMapping;
import com.shark.util.Constants;
import com.shark.util.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RequestService {

	@Autowired
	private RequestMapping requestMapping;
	@Autowired
	private ProjectMapping projectMapping;

	/**
	 * 插入数据库
	 * 
	 * @param requestEntity
	 * @param file
	 * @param req
	 * @return 返回状态码
	 */
	public String insertRequest(RequestEntity requestEntity, List<MultipartFile> file, HttpServletRequest req) {
		if (requestEntity == null) {
			return Constants.FAILCODE;
//			没有请求信息传来，弹窗“请求失败，请重新请求”，返回请求页
		}
		//20220823-thg，加入项目名称唯一性验证
		try {
			if(projectMapping.getProjectByName(requestEntity.getName())!=null) {
				log.info("projectService/insertRequest:项目名称重复");
				return Constants.FAILCODE;
			}
		}
		catch(Exception e) {
			log.info("projectService/insertRequest:",e);
			return Constants.FAILCODE;
		}
		try {
			// 创建文件夹
			String docPath = Constants.FILEPATH + Constants.PROJECT + requestEntity.getName() + Constants.REQUEST;
			FileUtil.isDirExist(docPath); // qh 20220402 封装FileUtil
			// 存文件
			if (file != null) {
				for (MultipartFile f : file) {
					String filename = f.getOriginalFilename();
					if(filename.equals("")) {
						continue;
					}
					/**
					 * String path = docPath +filename; File filePath = new File(path);
					 * BufferedOutputStream out = new BufferedOutputStream(new
					 * FileOutputStream(filePath));// 保存文件到目录下
					 * out.write(f.getBytes());//在创建好的文件中写入f.getBytes() out.flush(); out.close();
					 * log.info("for循环：本地储存："+path);
					 **/
					if (f == file.get(0)) {
						String docPath1 = docPath + Constants.SOW;
						FileUtil.isDirExist(docPath1);
						String path1 = docPath1 + filename;
						File filePath = new File(path1);
						BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));// 保存文件到目录下
						out.write(f.getBytes());// 在创建好的文件中写入f.getBytes()
						out.flush();
						out.close();
						log.info("for循环：本地储存：" + path1);
						requestEntity.setSowUrl(path1);
						log.info("数据库中SowUrl储存" + requestEntity.getSowUrl());
					} else if (f == file.get(1)) {
						String docPath2 = docPath + Constants.QUOTATION;
						FileUtil.isDirExist(docPath2);
						String path2 = docPath2 + filename;
						File filePath = new File(path2);
						BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));// 保存文件到目录下
						out.write(f.getBytes());// 在创建好的文件中写入f.getBytes()
						out.flush();
						out.close();
						log.info("for循环：本地储存：" + path2);
						requestEntity.setQuotationUrl(path2);
						log.info("数据库中QuotationUrl储存" + requestEntity.getQuotationUrl());
					} else {
						String docPath3 = docPath + "designUrl/";
						FileUtil.isDirExist(docPath3);
						String path3 = docPath3 + filename;
						File filePath = new File(path3);
						BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));// 保存文件到目录下
						out.write(f.getBytes());// 在创建好的文件中写入f.getBytes()
						out.flush();
						out.close();
						log.info("for循环：本地储存：" + path3);
						requestEntity.setDesignUrl(path3);
						log.info("数据库中DesignUrl储存" + requestEntity.getDesignUrl());
					}
				}
			}
			//20220823-thg，这里也要加项目名验证
			if(requestMapping.getRequestByName(requestEntity.getName())!=null) {
				log.info("requestService/insertRequest:项目名称重复");
				return Constants.FAILCODE;
			}
			requestMapping.insert(requestEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			req.setAttribute(Constants.ERROR, "添加文件失败");
			return Constants.FAILCODE;
		}

	}
	/*
	 * 传送文件
	 */
	public void downloadFile(String proname, String fileName, HttpServletResponse resp) {
		try {
			RequestEntity requestEntity = requestMapping.getRequestByName(proname);
			//获取文件名
			String strUrl = null;
			
			if(fileName.equals("sowUrl")) {
				strUrl = requestEntity.getSowUrl();
				log.info(strUrl);
				download(strUrl,resp);
			}
			else if(fileName.equals("quotationUrl")) {
				strUrl = requestEntity.getQuotationUrl();
				download(strUrl,resp);
			}
			else {
				strUrl = requestEntity.getDesignUrl();
				download(strUrl,resp);
			}
	        log.info("下载成功");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void download(String strUrl, HttpServletResponse resp) {
		try {
		String filename = strUrl.substring(strUrl.lastIndexOf("/")+1);
		filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
	
		String path = strUrl;
		File file = new File(path);
		//如果文件不存在
		if(!file.exists()){
			log.info("下载文件不存在");
		}
		//解决下载文件时文件名乱码问题
	    byte[] fileNameBytes = filename.getBytes(StandardCharsets.UTF_8);
	    filename = new String(fileNameBytes, 0, fileNameBytes.length, StandardCharsets.ISO_8859_1);
	    resp.reset();
	    resp.setContentType("application/octet-stream");
	    resp.setCharacterEncoding("utf-8");
	    resp.setContentLength((int) file.length());
		//设置响应头，控制浏览器下载该文件
		resp.setHeader("content-disposition", "attachment;filename=" + filename);
		try{
		//读取要下载的文件，保存到文件输入流
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
		//创建输出流
		OutputStream os  = resp.getOutputStream();
		//缓存区
		byte[] buff = new byte[1024];
		int i = 0;
		//循环将输入流中的内容读取到缓冲区中
		while ((i = bis.read(buff)) != -1) {
	        os.write(buff, 0, i);
	        os.flush();
	    }
		//关闭
		bis.close();
		os.close();   
      
	    } catch (IOException e) {
	        log.error("{}",e);
	        log.info("下载失败");
	    }
	}catch (Exception e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
}
	
	
	/**
	 * 根据项目名称查询项目请求
	 * 
	 * @param name
	 * @return
	 */
	public String getRequestByName(String name) {
		if (name == null) {
			return Constants.FAILCODE;
		}
		try {
//			return String.valueOf(RequestMapping.getRequestByName(name)); // 这里不要用String.valueOf
			return JSON.toJSONString(requestMapping.getRequestByName(name));
		} catch (Exception e) {
			log.info("查询失败");
			return Constants.FAILCODE;
		}
	}

	/**
	 * 根据公司名称查询项目请求
	 * 
	 * @param companyName
	 * @return
	 */
	public String getRequestBycompanyName(String companyName) {
		if (companyName == null) {
			return Constants.FAILCODE;
		}
		try {
			return JSON.toJSONString(requestMapping.getRequestBycompanyName(companyName));
		} catch (Exception e) {
			log.info("查询失败");
			return Constants.FAILCODE;
		}
	}
}
