package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-19
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id 删除小节
     * @param courseId 课程id
     */
    void removeVideoByCourseId(String courseId);
}
