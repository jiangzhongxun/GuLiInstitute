package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author jiang zhongxun
 * @create 2022-11-18 15:35
 */
@Data
public class SubjectData {

    /**
     * 一级课程分类
     */
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    /**
     * 二级课程分类
     */
    @ExcelProperty(index = 1)
    private String twoSubjectName;

}
