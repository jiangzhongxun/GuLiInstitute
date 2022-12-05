package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 定义常量类，读取配置文件内容
 * @author jiang zhongxun
 * @create 2022-11-17 10:05
 */

// 实现 Spring 的接口，当项目启动，Spring 加载之后，执行接口中的一个方法
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    /**
     * 地域节点
     */
    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;

    /**
     * 秘钥keyId
     */
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    /**
     * 秘钥keySecret
     */
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    /**
     * bucket名称
     */
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    /**
     * 定义公开静态常量
     */
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    /**
     * 用 afterPropertiesSet 来初始化配置信息，这个方法将在所有的属性被初始化后调用
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endPoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
