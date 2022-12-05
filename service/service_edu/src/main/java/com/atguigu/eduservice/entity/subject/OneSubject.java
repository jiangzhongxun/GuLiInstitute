package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类要返回的数据
 * @author jiang zhongxun
 * @create 2022-11-18 22:35
 */
@Data
public class OneSubject {

    private String id;

    private String title;

    /* 一个一级分类中有多个二级分类 */
    private List<TwoSubject> children = new ArrayList<>();

}
