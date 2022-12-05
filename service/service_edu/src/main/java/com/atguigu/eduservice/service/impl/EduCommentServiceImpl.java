package com.atguigu.eduservice.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.UcenterMemberVo;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.mapper.EduCommentMapper;
import com.atguigu.eduservice.service.EduCommentService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-01
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 添加评论
     * @param comment 评论
     * @param request 请求
     */
    @Override
    public EduComment addComent(EduComment comment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            throw new GuliException(20001, "请先登录");
        }
        comment.setMemberId(memberId);
        UcenterMemberVo infoUc = ucenterClient.getInfoUc(memberId);
        comment.setAvatar(infoUc.getAvatar());
        comment.setNickname(infoUc.getNickname());
        return comment;
    }

    /**
     * 根据课程 ID 查询评论带分页
     * @param commentPage 评论分页
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> getCommentPageById(Page<EduComment> commentPage, String courseId) {
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(commentPage, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentPage.getRecords());
        map.put("pages", commentPage.getPages());
        map.put("total", commentPage.getTotal());
        map.put("size", commentPage.getSize());
        map.put("current", commentPage.getCurrent());
        map.put("hasNext", commentPage.hasNext());
        map.put("hasPrevious", commentPage.hasPrevious());
        return map;
    }

}
