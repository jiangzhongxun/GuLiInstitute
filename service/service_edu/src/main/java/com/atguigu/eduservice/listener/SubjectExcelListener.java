package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 读取 Excel 文件内容监听器
 * @author jiang zhongxun
 * @create 2022-11-18 15:44
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    /* 因为 SubjectExcelListener 不能交给 Spring 进行管理，需要自己 new，因此不能注入其他对象，也不能通过 mapper 操作数据库 */
    /* 通过添加有参和无参构造 */
    public EduSubjectService subjectService;

    public SubjectExcelListener() {}

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * 一行一行读取 Excel 中的内容
     * @param subjectData     课程数据
     * @param analysisContext 分析上下文
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }
        // 一行一行读取，每次会读取到两个值：一级分类、二级分类
        // 判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null) {  // 没有相同的一级分类，进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }
        // 获取一级分类的 ID 值
        String parentId = existOneSubject.getId();
        // 判断二级分类是否重复
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), parentId);
        if (existTwoSubject == null) {  // 没有相同的二级分类，进行添加
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            existTwoSubject.setParentId(parentId);
            subjectService.save(existTwoSubject);
        }
    }

    /**
     * 判断一级分类不能重复添加
     * @param subjectService 课程服务
     * @param oneSubjectName 一级课程名称
     * @return {@link EduSubject}
     */
    private EduSubject existOneSubject(EduSubjectService subjectService, String oneSubjectName) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", oneSubjectName);
        queryWrapper.eq("parent_id", "0");
        return subjectService.getOne(queryWrapper);
    }

    /**
     * 判断二级分类不能重复添加
     * @param subjectService 课程服务
     * @param twoSubjectName 二级课程名称
     * @param parentId       父id
     * @return {@link EduSubject}
     */
    private EduSubject existTwoSubject(EduSubjectService subjectService, String twoSubjectName, String parentId) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", twoSubjectName);
        queryWrapper.eq("parent_id", parentId);
        return subjectService.getOne(queryWrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

}
