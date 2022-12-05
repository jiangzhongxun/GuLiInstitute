package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-19
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    /**
     * 课程大纲列表，根据课程 ID 进行查询
     * @param courseId 课程id
     * @return {@link List}<{@link ChapterVo}>
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        // 1、根据课程 ID 查出课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);
        // 2、根据课程 ID 查出课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
        // 创建 List集合，用于最终数据封装
        List<ChapterVo> finalList = new ArrayList<>();
        // 3、遍历查询章节 List集合进行封装
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            finalList.add(chapterVo);
            // 创建集合，用于封装章节中的小节
            List<VideoVo> videoList = new ArrayList<>();
            // 4、遍历查询小节 List集合进行封装
            for (EduVideo eduVideo : eduVideoList) {
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoList.add(videoVo);
                }
            }
            // 把封装之后的小节信息，加入到章节信息中
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    /**
     * 删除章节信息
     * @param chapterId 章节id
     * @return boolean
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        // 根据 章节ID 判断该章节下是否有小节 ----- 查小节表；若章节下有小节则不能删除
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        int count = videoService.count(queryWrapper);
        if (count > 0) {
            throw new GuliException(20001, "该章节下有小节,不能直接删除！");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    /**
     * 根据课程id 删除章节
     * @param courseId 课程id
     */
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        baseMapper.delete(queryWrapper);
    }

}
