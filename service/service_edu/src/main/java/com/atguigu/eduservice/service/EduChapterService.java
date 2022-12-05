package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-19
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 课程大纲列表，根据课程 ID 进行查询
     * @param courseId 课程id
     * @return {@link List}<{@link ChapterVo}>
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 删除章节信息
     *  @param chapterId 章节id
     * @return boolean
     */
    boolean deleteChapter(String chapterId);

    /**
     * 根据课程id 删除章节
     * @param courseId 课程id
     */
    void removeChapterByCourseId(String courseId);
}
