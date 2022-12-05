package com.atguigu.msmservice.service;

import java.util.Map;

/**
 * @author jiang zhongxun
 * @create 2022-11-26 13:34
 */
public interface MsmService {

    /**
     * 发送短信
     * @param param 参数
     * @param phone 电话
     * @return boolean
     */
    boolean send(Map<String, Object> param, String phone);
}
