package com.atguigu.edustatistics.service;

import com.atguigu.edustatistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-03
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 统计某一天的注册人数
     * @param day 日期
     */
    void registerCount(String day);

    /**
     * 图表显示，返回两部分数据，日期 JSON 数组，数量 JSON 数组
     * @param type  统计因子
     * @param begin 开始时间
     * @param end   结束时间
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getShowData(String type, String begin, String end);
}
