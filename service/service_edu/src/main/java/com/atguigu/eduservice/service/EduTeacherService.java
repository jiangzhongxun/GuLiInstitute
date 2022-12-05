package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-10
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 查询前 4 条热门讲师
     * @return {@link List}<{@link EduTeacher}>
     */
    List<EduTeacher> getTeacherByLimit();

    /**
     * 分页查询讲师
     * @param pageTeacher pageTeacher
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
