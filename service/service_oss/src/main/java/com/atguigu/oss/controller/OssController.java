package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jiang zhongxun
 * @create 2022-11-17 10:26
 */
@Api(description="讲师头像上传")
@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin    // 解决跨域问题
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 上传头像文件
     * @param file 文件
     * @return {@link R}
     */
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        // 获取上传文件 MultipartFile，返回上传到 OSS 的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }

}
