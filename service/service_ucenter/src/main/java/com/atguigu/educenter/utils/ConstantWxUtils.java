package com.atguigu.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信开放平台 常量类
 * @author jiang zhongxun
 * @create 2022-11-27 13:13
 */
@Component
public class ConstantWxUtils implements InitializingBean {

    /** 微信开放平台 appid */
    @Value("${wx.open.app_id}")
    private String appId;

    /** 微信开放平台 appsecret */
    @Value("${wx.open.app_secret}")
    private String appSecret;

    /** 微信开放平台 重定向url */
    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }

}
