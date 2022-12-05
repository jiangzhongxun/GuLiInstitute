package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jiang zhongxun
 * @create 2022-11-25 14:28
 */
@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询前 8 条热门课程
     * 查询前 4 条热门讲师
     * @return {@link R}
     */
    @GetMapping("/index")
    public R index() {
        List<EduCourse> courseList =  courseService.getCourseByLimit();
        List<EduTeacher> teacherList = teacherService.getTeacherByLimit();
        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }

}
