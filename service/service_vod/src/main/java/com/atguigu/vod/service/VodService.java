package com.atguigu.vod.service;

import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author jiang zhongxun
 * @create 2022-11-23 20:31
 */
public interface VodService {

    /**
     * 上传视频到阿里云
     * @param file 文件
     * @return {@link String}
     */
    String uploadVideoAly(MultipartFile file);

    /**
     * 根据视频ID 删除视频
     * @param id id
     */
    void deleteVideo(String id);

    /**
     * 删除多个视频
     * @param videoList 视频列表
     */
    void removeMoreAliyVideo(List<String> videoList);

    /**
     * 获取视频播放凭证
     * @param id id
     * @return {@link GetVideoPlayAuthResponse}
     */
    GetVideoPlayAuthResponse getPlayAuth(String id);
}
