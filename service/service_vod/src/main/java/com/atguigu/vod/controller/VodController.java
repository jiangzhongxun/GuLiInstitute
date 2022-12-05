package com.atguigu.vod.controller;

import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author jiang zhongxun
 * @create 2022-11-23 20:28
 */
@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频到阿里云
     * @param file 文件
     * @return {@link R}
     */
    @PostMapping("/uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file) {
        // 返回上传视频的 ID 值
        String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId", videoId).message("视频上传成功");
    }

    /**
     * 根据视频ID 删除视频
     * @param id 视频id
     * @return {@link R}
     */
    @DeleteMapping("/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        vodService.deleteVideo(id);
        return R.ok();
    }

    /**
     * 批处理删除
     * @return {@link R}
     */
    @DeleteMapping("/delete-batch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList) {
        vodService.removeMoreAliyVideo(videoList);
        return R.ok();
    }

    /**
     * 获取视频播放凭证
     * @param id id
     * @return {@link R}
     */
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        GetVideoPlayAuthResponse response = vodService.getPlayAuth(id);
        String playAuth = response.getPlayAuth();
        return R.ok().data("playAuth", playAuth);
    }

}
