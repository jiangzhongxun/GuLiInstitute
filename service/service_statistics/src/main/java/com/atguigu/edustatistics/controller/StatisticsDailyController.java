package com.atguigu.edustatistics.controller;

import com.atguigu.commonutils.R;
import com.atguigu.edustatistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-03
 */
@RestController
@RequestMapping("/edustatistics/sta")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 统计某一天的注册人数
     * @param day 日期
     * @return {@link R}
     */
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable("day") String day) {
        statisticsDailyService.registerCount(day);
        return R.ok();
    }

    /**
     * 图表显示，返回两部分数据，日期 JSON 数组，数量 JSON 数组
     * @param type  统计因子
     * @param begin 开始时间
     * @param end   结束时间
     * @return {@link R}
     */
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable("type") String type,
                      @PathVariable("begin") String begin,
                      @PathVariable("end") String end) {
        Map<String, Object> map = statisticsDailyService.getShowData(type, begin, end);
        return R.ok().data(map);
    }

}

