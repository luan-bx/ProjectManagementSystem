package com.shark.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;

import  com.alibaba.fastjson.JSON;
import cn.hutool.log.Log;

import java.util.*;
import com.aliyuncs.dysmsapi.model.v20170525.*;

public class SendSms {
	public static Map<String, String> sendMsgMap = new HashMap<>();
	
//	static {
//		 sendMsgMap.put("templateCode","SMS_254680258");
//		 sendMsgMap.put("AccessKeyId", "LTAI5tJdLN1eHL5Zp3N3E3iR");
//		 sendMsgMap.put("AccessKeySecret", "1Gvm6UPX1tBmTvqs3u9957iLwmzpjO");
//		 sendMsgMap.put("sign", "中拓科技");
//		}

    public static void SendMessageCode(String code, String phone) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", sendMsgMap.get("AccessKeyId"), sendMsgMap.get("AccessKeySecret"));
        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSignName(sendMsgMap.get("sign"));
        request.setTemplateCode(sendMsgMap.get("templateCode"));
        request.setPhoneNumbers(phone);
        request.setTemplateParam("{\"code\":\""+code+"\"}");

        try {
            SendSmsResponse response = client.getAcsResponse(request);
            String jsonStr = new Gson().toJson(response);
            System.out.println(jsonStr);
           
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }
    
    public static boolean SendMessageCode(String code, String phone, String accessKeyId, String accessKeySecret, String sign, String templateCode) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setSignName(sign);
        request.setTemplateCode(templateCode);
        request.setPhoneNumbers(phone);
        request.setTemplateParam("{\"code\":\""+code+"\"}");

        try {
            SendSmsResponse response = client.getAcsResponse(request);
            String jsonStr = new Gson().toJson(response);
            System.out.println(jsonStr);
            return jsonStr.indexOf("\"message\":\"OK\"")>0;
           
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return false;
    }

}
