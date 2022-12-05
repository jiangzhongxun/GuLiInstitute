package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.UcenterMemberVo;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-01
 */
@RestController
@RequestMapping("/eduservice/comment")
//@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    /**
     * 添加评论
     * @param comment 评论
     * @param request 请求
     * @return {@link R}
     */
    @PostMapping("/auth/saveComment")
    public R saveComment(@RequestBody EduComment comment, HttpServletRequest request) {
        EduComment eduComment = commentService.addComent(comment, request);
        commentService.save(eduComment);
        return R.ok();
    }

    /**
     * 根据课程 ID 查询评论带分页
     * @param page     当前页
     * @param limit    每页数
     * @param courseId 课程id
     * @return {@link R}
     */
    @GetMapping("/getCommentPage/{page}/{limit}")
    public R getCommentPage(@PathVariable("page") long page, @PathVariable("limit") long limit, String courseId) {
        Page<EduComment> commentPage = new Page<>(page, limit);
        Map<String, Object> map = commentService.getCommentPageById(commentPage, courseId);
        /* 返回分页所有数据 */
        return R.ok().data(map);
    }

}

