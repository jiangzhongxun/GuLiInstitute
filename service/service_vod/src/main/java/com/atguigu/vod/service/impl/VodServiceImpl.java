package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author jiang zhongxun
 * @create 2022-11-23 20:31
 */
@Service
public class VodServiceImpl implements VodService {

    /**
     * 上传视频到阿里云
     * 使用 流式上传接口
     * @param file 文件
     * @return {@link String}
     */
    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
            /* accessKeyId、accessKeySecret：ID 和秘钥 */
            /* fileName：上传文件的原始名称 */
            String fileName = file.getOriginalFilename();
            /* title：上传之后显示的名称，可以和 fileName 相同 */
            //String title = fileName != null ? fileName.substring(0, fileName.lastIndexOf(".")) : null;
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            /* inputStream：上传文件输入流 */
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,
                                                                    ConstantVodUtils.ACCESS_KEY_SECRET,
                                                                    title, fileName, inputStream);
            /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印 */
            //request.setShowWaterMark(true);
            /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
            //request.setCallback("http://callback.sample.com");
            /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
            //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
            /* 视频分类ID(可选) */
            //request.setCateId(0);
            /* 视频标签,多个用逗号分隔(可选) */
            //request.setTags("标签1,标签2");
            /* 视频描述(可选) */
            //request.setDescription("视频描述");
            /* 封面图片(可选) */
            //request.setCoverURL("http://cover.sample.com/sample.jpg");
            /* 模板组ID(可选) */
            //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
            /* 工作流ID(可选) */
            //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
            /* 存储区域(可选) */
            //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
            /* 开启默认上传进度回调 */
            // request.setPrintProgress(true);
            /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
            // request.setProgressListener(new PutObjectProgressListener());
            /* 设置应用ID*/
            //request.setAppId("app-1000000");
            /* 点播服务接入点 */
            //request.setApiRegionId("cn-shanghai");
            /* ECS部署区域*/
            // request.setEcsRegionId("cn-shanghai");
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            // System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                // System.out.print("VideoId=" + response.getVideoId() + "\n");
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                //System.out.print("VideoId=" + response.getVideoId() + "\n");
                //System.out.print("ErrorCode=" + response.getCode() + "\n");
                //System.out.print("ErrorMessage=" + response.getMessage() + "\n");
                videoId = response.getVideoId();
                //String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                //if(StringUtils.isEmpty(videoId)){
                //    throw new GuliException(20001, errorMessage);
                //}
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            //throw new GuliException(20001, "guli vod 服务上传失败");
            return null;
        }
    }

    /**
     * 根据视频ID 删除视频
     * @param id id
     */
    @Override
    public void deleteVideo(String id) {
        try {
            /* 初始化对象 */
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            /* 创建删除视频 request 对象 */
            DeleteVideoRequest request = new DeleteVideoRequest();
            /* 向 request 对象设置视频 ID */
            request.setVideoIds(id);
            /* 调用初始化对象的方法实现删除 */
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "视频删除失败");
        }
    }

    /**
     * 删除多个视频
     * @param videoList 视频列表
     */
    @Override
    public void removeMoreAliyVideo(List<String> videoList) {
        try {
            /* 初始化对象 */
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            /* 创建删除视频 request 对象 */
            DeleteVideoRequest request = new DeleteVideoRequest();
            /* videoList 值转换成 1, 2, 3 的形式 */
            String videoIds = StringUtils.join(videoList.toArray(), ",");
            /* 向 request 对象设置视频 ID */
            request.setVideoIds(videoIds);
            /* 调用初始化对象的方法实现删除 */
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "视频删除失败");
        }
    }

    /**
     * 获取视频播放凭证
     * @param id id
     * @return {@link GetVideoPlayAuthResponse}
     */
    @Override
    public GetVideoPlayAuthResponse getPlayAuth(String id) {
        try {
            /* 创建初始化对象 */
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            /* 创建获取视频播放凭证 request 和 response */
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            /* 向 request 对象里设置视频 ID */
            request.setVideoId(id);
            /* 通过初始化对象里面的方法，传递 request，获取数据 */
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            return response;
        } catch (ClientException e) {
            throw new GuliException(20001, "视频播放凭证获取失败！");
        }
    }

}
