package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-19
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 根据课程ID 获取发布课程信息
     * @param courseId 课程id
     * @return {@link CoursePublishVo}
     */
    CoursePublishVo getPublishCourseInfo(String courseId);

    /**
     * 根据课程id 查询课程相关信息
     * @param courseId 课程id
     * @return {@link CourseWebVo}
     */
    CourseWebVo getBaseCourseInfo(String courseId);

}
