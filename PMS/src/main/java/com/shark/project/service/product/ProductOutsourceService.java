package com.shark.project.service.product;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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
import com.shark.project.entity.product.ProductEntity;
import com.shark.project.entity.product.ProductOutsourceEntity;
import com.shark.project.mapper.ProjectMapping;
import com.shark.project.mapper.SignMapping;
import com.shark.project.mapper.product.ProductMapping;
import com.shark.project.mapper.product.ProductOutsourceMapping;
import com.shark.util.Constants;
import com.shark.util.DownLoadUtil;
import com.shark.util.ZipCompressorUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductOutsourceService {

	@Autowired
	private ProductOutsourceMapping productOutsourceMapping;
	@Autowired
	private ProjectMapping projectMapping;
	@Autowired
	private ProductMapping productMapping;
	@Autowired
	private SignMapping signMapping;
	
	public String insertProductOutsource(ProductOutsourceEntity productOutsourceEntity,String proname, List<MultipartFile> file,
			HttpServletRequest req) {	
		// 判断参数有没有效
		if(productOutsourceEntity == null) {
			return Constants.FAILCODE;
		}	
		try {
			//获取当前时间
			Calendar calendar = Calendar.getInstance();
			String nowTime = (new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(calendar.getTime()));
			// 创建文件夹
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			String poId = signMapping.getSignById(projectEntity.getProSignId()).getPoId();
			String docPath = Constants.FILEPATH + Constants.PROJECT + proname + "/product/" + poId + "-outsource/"+nowTime+"/";

			File localPath = new File(docPath);
			if (!localPath.exists()) {  // 获得文件目录，判断目录是否存在，不存在就新建一个
				localPath.mkdirs();
            }
			//存文件
			for (MultipartFile f : file) {
				String filename = f.getOriginalFilename();
				String path = docPath +filename;
				File filePath = new File(path);			
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));// 保存文件到目录下
				out.write(f.getBytes());//在创建好的文件中写入f.getBytes()
				out.flush();
				out.close();
				productOutsourceEntity.setFileUrl(path);
				log.info("数据库中FileUrl储存"+productOutsourceEntity.getFileUrl());
			}

			productOutsourceMapping.insert(productOutsourceEntity);
			return Constants.SUCCESSCODE;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			req.setAttribute("error", "添加文件失败");
			return Constants.FAILCODE;
		}
	}	
	
	public String getProductOutsourceByFileUrl(String fileUrl) {
		if(fileUrl == null) {
			return Constants.FAILCODE;    
		}
		try {		
			return JSON.toJSONString(productOutsourceMapping.getProductOutsourceByFileUrl(fileUrl));
		} catch (Exception e) {
			log.info("查询失败",e);
			return Constants.FAILCODE;
		}
	}
	
	
	/*
	 * 下载文件夹
	 */
	public void downloadFile(String proname, String fileName, HttpServletResponse resp) {
		try {
			ProjectEntity projectEntity = projectMapping.getProjectByName(proname);
			String poId = signMapping.getSignById(projectEntity.getProSignId()).getPoId();
			String name=Constants.FILEPATH + Constants.PROJECT + proname + "/product/" + poId + "-outsource"+".zip";
			ZipCompressorUtil zc = new ZipCompressorUtil(name);
			List<String> list=new ArrayList<>();
			list.add(Constants.FILEPATH + Constants.PROJECT + proname + "/product/" + poId + "-outsource");
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
