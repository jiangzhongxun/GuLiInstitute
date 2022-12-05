package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author jiang zhongxun
 * @create 2022-11-27 18:29
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    /**
     * 分页查询讲师
     * @param page  当前页
     * @param limit 没页数
     * @return {@link R}
     */
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(pageTeacher);
        /* 返回分页所有数据 */
        return R.ok().data(map);
    }

    /**
     * 根据讲师 ID 查询讲师详情
     * @param teacherId 老师id
     * @return {@link R}
     */
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        /* 1、根据讲师 ID 查询讲师基本信息 */
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        /* 2、根据讲师 ID 查询讲师所讲的课程信息 */
        LambdaQueryWrapper<EduCourse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(EduCourse::getTeacherId, teacherId);
        lambdaQueryWrapper.orderByDesc(EduCourse::getGmtModified);
        List<EduCourse> courseList = courseService.list(lambdaQueryWrapper);
        return R.ok().data("eduTeacher", eduTeacher).data("courseList", courseList);
    }

}
