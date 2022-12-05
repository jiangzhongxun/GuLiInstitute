package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-01
 */
public interface EduCommentService extends IService<EduComment> {

    /**
     * 添加评论
     * @param comment 评论
     * @param request 请求
     */
    EduComment addComent(EduComment comment, HttpServletRequest request);

    /**
     * 根据课程 ID 查询评论带分页
     * @param commentPage 评论分页
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getCommentPageById(Page<EduComment> commentPage, String courseId);
}
