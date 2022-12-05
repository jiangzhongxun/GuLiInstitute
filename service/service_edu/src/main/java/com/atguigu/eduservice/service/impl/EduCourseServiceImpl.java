package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.constant.CourseConstant;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-19
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;

    /**
     * 添加课程基本信息
     * @param courseInfoVo 课程基本信息
     * @return {@link String}
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 1、向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        eduCourse.setStatus(CourseConstant.COURSE_DRAFT);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new GuliException(20001, "添加课程基本信息失败！");
        }
        String cid = eduCourse.getId();
        // 2、向课程简介表添加简介信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        boolean save = courseDescriptionService.save(eduCourseDescription);
        if (!save) {
            throw new GuliException(20001, "课程简介信息保存失败");
        }
        return cid;
    }

    /**
     * 获取课程基本信息
     * @param courseId 课程id
     * @return {@link CourseInfoVo}
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 1、查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        // 2、查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    /**
     * 更新课程基本信息
     * @param courseInfoVo 课程基本信息
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 1、修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliException(20001, "修改课程基本信息失败");
        }
        // 2、修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean b = courseDescriptionService.updateById(eduCourseDescription);
        if (!b) {
            throw new GuliException(20001, "修改课程简介失败");
        }
    }

    /**
     * 根据课程ID 查询课程确认信息
     * @param id id
     * @return {@link CoursePublishVo}
     */
    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        //CoursePublishVo publishCourseInfo = courseMapper.getPublishCourseInfo(id);
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        if (null != publishCourseInfo) {
            return publishCourseInfo;
        } else {
            throw new GuliException(20001, "课程确认信息查询失败！");
        }
    }

    /**
     * 发布课程 ( 修改课程的状态为：Normal )
     * @param id id
     * @return {@link Boolean}
     */
    @Override
    public boolean publishCourse(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus(CourseConstant.COURSE_NORMAL);
        int count = baseMapper.updateById(eduCourse);
        return count > 0;
    }

    /**
     * 条件查询带分页
     * @param page     page
     * @param courseQuery 查询数据对象
     */
    @Override
    public void getPageCourseCondition(Page<EduCourse> page, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        // 判断查询条件是否为空，不为空则通过该条件查询
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status", status);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_modified", end);
        }
        // 根据创建时间降序排序
        queryWrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 通过id删除课程
     * @param courseId 课程id
     * @return boolean
     */
    @Override
    public boolean removeCourseById(String courseId) {
        // 1、根据课程ID 删除小节
        videoService.removeVideoByCourseId(courseId);
        // 2、根据课程ID 删除章节
        chapterService.removeChapterByCourseId(courseId);
        // 3、根据课程ID 删除课程描述
        courseDescriptionService.removeById(courseId);
        // 4、根据课程ID 删除课程本身
        int delete = baseMapper.deleteById(courseId);
        return delete > 0;
    }

    /**
     * 查询前 8 条热门课程
     * @return {@link List}<{@link EduCourse}>
     */
    @Override
    @Cacheable(value = "course", key = "'courseList'")
    public List<EduCourse> getCourseByLimit() {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 8");
        List<EduCourse> courseList = baseMapper.selectList(queryWrapper);
        return courseList;
    }

    /**
     * 条件查询带分页查询课程
     * @param coursePage    课程页面
     * @param courseFrontVo 课程前签证官
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getTitle())) {
            queryWrapper.eq("title", courseFrontVo.getTitle());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getTeacherId())) {
            queryWrapper.eq("teacher_id", courseFrontVo.getTeacherId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            queryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        baseMapper.selectPage(coursePage, queryWrapper);
        long current = coursePage.getCurrent();
        long size = coursePage.getSize();
        long pages = coursePage.getPages();
        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();
        boolean hasPrevious = coursePage.hasPrevious();
        boolean hasNext = coursePage.hasNext();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current",current);
        map.put("size", size);
        map.put("pages", pages);
        map.put("total", total);
        map.put("hasPrevious", hasPrevious);
        map.put("hasNext", hasNext);
        return map;
    }

    /**
     * 根据课程id 查询课程相关信息
     * @param courseId 课程id
     * @return {@link CourseWebVo}
     */
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

}
