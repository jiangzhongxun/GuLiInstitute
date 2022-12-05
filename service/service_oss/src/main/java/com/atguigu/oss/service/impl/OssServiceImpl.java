package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author jiang zhongxun
 * @create 2022-11-17 10:26
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传头像到 oss
     * @param file
     * @return
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // 通过定义的工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建 OSSClient 实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            // 获取文件名称
            String fileName = file.getOriginalFilename();
            // 在文件名称里添加随机唯一值，保证多次上传的文件名不重复，否则文件名重复的文件会被覆盖
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;
            // 把文件按照日期分类 如：2022/11/17/a.jpg
            // 获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            // 拼接文件路径
            fileName = datePath + "/" + fileName;

            // 调用 oss 方法实现上传 ( 参数1：bucket名称，参数2：上传到 oss 文件路径和文件名称，参数3：上传文件输入流 )
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭 ossClient
            ossClient.shutdown();

            // 返回上传之后的文件路径  https://bucket名称.地域节点/文件名称
            // https://guli-file-jzx.oss-cn-beijing.aliyuncs.com/default.jpg
            return "https://" + bucketName + "." + endpoint + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
