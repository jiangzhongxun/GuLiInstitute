package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-19
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    /* 注入 VodClient */
    @Autowired
    private VodClient vodClient;

    /**
     * 添加小节
     * @param eduVideo 小节信息
     * @return {@link R}
     */
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节
     * @param id id
     * @return {@link R}
     */
    @DeleteMapping("/{id}")
    public R deleteVideo(@PathVariable String id) {
        /* 根据小节 ID 获取视频 ID ，然后删除 */
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        /* 判断小节中是否有视频 */
        if (!StringUtils.isEmpty(videoSourceId)) {
            /* 根据视频 ID，远程调用实现视频删除 */
            vodClient.removeAlyVideo(videoSourceId);
        }
        /* 删除小节 */
        videoService.removeById(id);
        return R.ok();
    }

    /**
     * 根据ID查询小节信息
     * @param id id
     * @return {@link R}
     */
    @GetMapping("/getVideo/{id}")
    public R getVideo(@PathVariable String id) {
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video", eduVideo);
    }

    /**
     * 更新小节信息
     * @param eduVideo 小节信息
     * @return {@link R}
     */
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

}

