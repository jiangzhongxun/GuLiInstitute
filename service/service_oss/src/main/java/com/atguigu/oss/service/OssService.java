package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author jiang zhongxun
 * @create 2022-11-17 10:25
 */
public interface OssService {

    /**
     * 上传头像到 oss
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file);

}
