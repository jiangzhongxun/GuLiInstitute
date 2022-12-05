package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-19
 */
@Api(description="课程管理")
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 通过id删除课程
     * @param courseId 课程id
     * @return {@link R}
     */
    @DeleteMapping("/{courseId}")
    public R deleteCourseById(@PathVariable String courseId) {
        boolean result = courseService.removeCourseById(courseId);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除课程失败！");
        }
    }

    /**
     * 查询全部课程列表
     * @return {@link R}
     */
    @GetMapping
    public R getCourseList() {
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list", list);
    }

    /**
     * 条件查询带分页
     * @param current     当前页
     * @param limit       每页显示数
     * @param courseQuery 查询数据对象
     * @return {@link R}
     */
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current,
                                 @PathVariable long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> coursePage = new Page<>(current, limit);
        // 调用方法实现条件查询分页
        courseService.getPageCourseCondition(coursePage, courseQuery);
        long total = coursePage.getTotal();
        List<EduCourse> records = coursePage.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 添加课程基本信息
     * @param courseInfoVo 课程基本信息
     * @return {@link R}
     */
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        if (!StringUtils.isEmpty(id)) {
            return R.ok().data("courseId", id);
        } else {
            return R.error().message("此课程已存在");
        }
    }

    /**
     * 获取课程基本信息
     * @param courseId 课程id
     * @return {@link R}
     */
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    /**
     * 更新课程基本信息
     * @param courseInfoVo 课程基本信息
     * @return {@link R}
     */
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    /**
     * 根据课程ID 查询课程确认信息
     * @param id id
     * @return {@link R}
     */
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    /**
     * 发布课程 ( 修改课程的状态为：Normal )
     * @param id id
     * @return {@link R}
     */
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        boolean b = courseService.publishCourse(id);
        if (b) {
            return R.ok();
        } else {
            throw new GuliException(20001, "课程发布失败！");
        }
    }

}

