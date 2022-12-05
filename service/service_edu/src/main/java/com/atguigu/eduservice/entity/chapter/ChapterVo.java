package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 章节VO（ 页面数据对象 ）
 * @author jiang zhongxun
 * @create 2022-11-19 21:48
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    /* 表示小节，一个章节下有多个小节 */
    private List<VideoVo> children = new ArrayList<>();

}
