package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-18
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 获取上传的文件，读取文件中的内容, 用来添加课程分类
     * @param file 上传的课程科目文件
     */
    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    /**
     * 课程分类列表 ( 树形结构 )
     * @return {@link List}<{@link OneSubject}>
     */
    List<OneSubject> getAllOneTwoSubject();

}
