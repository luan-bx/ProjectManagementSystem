package com.shark.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Locale;

public class LocalMacUtil {


    /**
     * 本机MAC地址
     * @return
     */
    public static String getLocalMac() throws UnknownHostException {
        StringBuilder mac = new StringBuilder("");
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                //过滤其他网卡
                if (networkInterface.isVirtual()||networkInterface.isLoopback()||networkInterface.isPointToPoint() || !networkInterface.isUp()){
                    continue;
                }
                String displayName = networkInterface.getDisplayName();
                if (displayName.contains("VMware Virtual")){
                    continue;
                }
                //获取解析网关
                byte[] hardwareAddress =  networkInterface.getHardwareAddress();

                for(int i=0; i<hardwareAddress.length; i++) {
                    if(i!=0) {
                        mac.append("-");
                    }
                    //字节转换为整数
                    int temp = hardwareAddress[i]&0xff;
                    String str = Integer.toHexString(temp);
                    if(str.length()==1) {
                        mac.append("0").append(str);
                    }else {
                        mac.append(str);
                    }
                }
                break;
            }
        } catch (SocketException e) {
            e.printStackTrace();
            throw new UnknownHostException("获取本地Mac地址失败");
        }
        return String.valueOf(mac).toUpperCase(Locale.ROOT);
    }
}