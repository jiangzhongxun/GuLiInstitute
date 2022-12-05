package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author jiang zhongxun
 * @create 2022-11-26 13:34
 */
@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送短信
     * @param param 参数
     * @param phone 电话
     * @return boolean
     */
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)) return false;
        DefaultProfile profile = DefaultProfile.getProfile("default",
                "LTAI5tFdVXa73bqWgvDmpeJu", "SobfTIHAM9C44eqSIRxzI1QDRvApSx");
        DefaultAcsClient client = new DefaultAcsClient(profile);
        /* 设置相关固定的参数 */
        CommonRequest request = new CommonRequest();
        /* 发送类型 */
        request.setMethod(MethodType.POST);
        /* 域名 */
        request.setDomain("dysmsapi.aliyuncs.com");
        /* 版本 */
        request.setVersion("2017-05-25");
        /* 发送方法 */
        request.setAction("SendSms");
        /* 设置发送相关的参数 */
        /* 手机号 */
        request.putQueryParameter("PhoneNumbers", phone);
        /* 签名名称 */
        request.putQueryParameter("SignName", "阿里云短信测试");
        /* 模板 CODE */
        request.putQueryParameter("TemplateCode", "SMS_154950909");
        /* 模板参数（随机生成的验证码）转换成 JSON 数据 */
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
        try {
            /* 最终发送 */
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
