package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-19
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseInfoVo 课程基本信息
     * @return {@link String}
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 获取课程基本信息
     * @param courseId 课程id
     * @return {@link CourseInfoVo}
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 更新课程基本信息
     * @param courseInfoVo 课程基本信息
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程ID 查询课程确认信息
     * @param id id
     * @return {@link CoursePublishVo}
     */
    CoursePublishVo getPublishCourseInfo(String id);

    /**
     * 发布课程
     * @param id id
     * @return {@link Boolean}
     */
    boolean publishCourse(String id);

    /**
     * 条件查询带分页
     * @param page     page
     * @param courseQuery 查询数据对象
     */
    void getPageCourseCondition(Page<EduCourse> page, CourseQuery courseQuery);

    /**
     * 通过id删除课程
     * @param courseId 课程id
     * @return boolean
     */
    boolean removeCourseById(String courseId);

    /**
     * 查询前 8 条热门课程
     * @return {@link List}<{@link EduCourse}>
     */
    List<EduCourse> getCourseByLimit();

    /**
     * 条件查询带分页查询课程
     * @param coursePage    课程页面
     * @param courseFrontVo 课程前签证官
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    /**
     * 根据课程id 查询课程相关信息
     * @param courseId 课程id
     * @return {@link CourseWebVo}
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
