package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

/**
 * 小节VO （ 页面数据对象 ）
 * @author jiang zhongxun
 * @create 2022-11-19 21:50
 */
@Data
public class VideoVo {

    private String id;

    private String title;

    private String videoSourceId;   // 视频 ID

}
