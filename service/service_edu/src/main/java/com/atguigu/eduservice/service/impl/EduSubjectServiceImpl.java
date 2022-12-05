package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-18
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 获取上传的文件，读取文件中的内容, 用来添加课程分类
     * @param file 上传的课程科目文件
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 课程分类列表 ( 树形结构 )
     * @return {@link List}<{@link OneSubject}>
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 创建 list 集合，用于存储最终封装的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        // 1、查询所有一级分类
        QueryWrapper<EduSubject> queryWrapperOne = new QueryWrapper<>();
        queryWrapperOne.eq("parent_id", "0");
        queryWrapperOne.orderByAsc("sort", "id");
        //List<EduSubject> oneSubjectList = this.list(queryWrapperOne);
        List<EduSubject> oneSubjectList = baseMapper.selectList(queryWrapperOne);
        // 2、查询所有二级分类
        QueryWrapper<EduSubject> queryWrapperTwo = new QueryWrapper<>();
        queryWrapperTwo.ne("parent_id", "0");
        queryWrapperTwo.orderByAsc("sort", "id");
        //List<EduSubject> twoSubjectList = this.list(queryWrapperTwo);
        List<EduSubject> twoSubjectList = baseMapper.selectList(queryWrapperTwo);
        // 3、封装一级分类;
        /*
        * 遍历查询出来的所有一级分类 list集合 List<EduSubject> oneSubjectList
        * 得到每一个一级分类对象，获取每个一级分类对象值
        * 并封装到要求的 list集合里面 List<OneSubject> finalSubjectList
        * */
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);
            // 获取 eduSubject 的值，放到 OneSubject 对象里面；多个 OneSubject 放到 finalSubjectList 里面
            OneSubject oneSubject = new OneSubject();
            //oneSubject.setId(eduSubject.getId());
            //oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject, oneSubject);
            finalSubjectList.add(oneSubject);

            // 4、封装二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject tSubject = twoSubjectList.get(m);
                // 判断二级分类的父 ID 与一级分类的 ID 是否一样
                if (tSubject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }

            // 把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }

}
