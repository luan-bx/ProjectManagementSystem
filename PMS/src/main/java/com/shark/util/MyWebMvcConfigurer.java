package com.shark.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.shark.interceptor.Interceptor;

@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurationSupport{
	/**
	* 配置静态访问资源
	* @param registry
	*/
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
		registry.addResourceHandler("/template/**").addResourceLocations("classpath:/template/");
        registry.addResourceHandler("/iconPath/**").addResourceLocations("file:"+ Constants.FILEPATH + "users/");
    }
	/**
	 * 添加拦截器
	 */
	
	  @Override protected void addInterceptors(InterceptorRegistry registry) { 
		  //TODO 自动生成的方法存根
	   InterceptorRegistration registration =
	   registry.addInterceptor(new Interceptor());
	   registration.addPathPatterns("/**");
	   registration.excludePathPatterns("/getPhoneCode","/getEmailCode","/msgConfig","/sendMsgConfig","/css/**","/js/**","/assets/**","/fonts/**","/img/**","/template/**","/iconPath/**"); }
	 
}
