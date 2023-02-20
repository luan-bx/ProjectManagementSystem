package com.shark.base;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.shark.base.controller.AuthController;
import com.shark.base.util.BaseUtil;
import com.shark.users.entity.UserEntity;
import com.shark.users.mapper.UserMapping;
import com.shark.users.service.UserService;
import com.shark.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BaseController extends BaseUtil {

	@Autowired
	protected UserService UserService;
	@Autowired
	protected AuthController authController;
	@Autowired
	protected UserMapping userMapping;

	/**
	 * 系统入口
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String pms(HttpServletRequest req, HttpServletResponse response) {
		
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
		return authController.auth(userEntity, userName, req, response);
	}
}
