package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jiang zhongxun
 * @create 2022-11-21 16:25
 */
@Data
public class CourseQuery {

    /**
     * 课程名称，模糊查询
     */
    private String title;

    /**
     * 课程状态 Normal：已发布  Draft：未发布
     */
    private String status;

    /**
     * 查询开始时间;
     */
    private String begin;  //注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    /**
     * 查询结束时间
     */
    private String end;

}
