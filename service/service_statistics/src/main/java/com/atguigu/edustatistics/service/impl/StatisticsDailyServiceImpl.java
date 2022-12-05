package com.atguigu.edustatistics.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.edustatistics.client.UcenterClient;
import com.atguigu.edustatistics.entity.StatisticsDaily;
import com.atguigu.edustatistics.mapper.StatisticsDailyMapper;
import com.atguigu.edustatistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-03
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 统计某一天的注册人数
     * @param day 日期
     */
    @Override
    public void registerCount(String day) {
        /* 删除已存在的统计对象 */
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);
        /* 远程调用得到注册人数 */
        R registerR = ucenterClient.countRegister(day);
        Integer registerNum = (Integer) registerR.getData().get("countRegister");
        /* 获取统计信息 */
        Integer loginNum = RandomUtils.nextInt(10, 20);       //TODO
        Integer videoViewNum = RandomUtils.nextInt(10, 20);   //TODO
        Integer courseNum = RandomUtils.nextInt(10, 20);      //TODO
        /* 把获取到的数据添加到数据库统计数据表中 */
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setCourseNum(courseNum);
        statisticsDaily.setRegisterNum(registerNum);
        statisticsDaily.setLoginNum(loginNum);
        statisticsDaily.setVideoViewNum(videoViewNum);
        statisticsDaily.setDateCalculated(day);
        baseMapper.insert(statisticsDaily);
    }

    /**
     * 图表显示，返回两部分数据，日期 JSON 数组，数量 JSON 数组
     * @param type  统计因子
     * @param begin 开始时间
     * @param end   结束时间
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated", begin, end);
        queryWrapper.orderByAsc("date_calculated");
        queryWrapper.select("date_calculated", type);
        List<StatisticsDaily> dailyList = baseMapper.selectList(queryWrapper);
        /* 返回 日期 和 日期对应数量，前端展示要求数组 JSON 结构，对应后端 List 集合 */
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        /* 遍历查询所有数据 List 集合，进行封装 */
        for (int i = 0; i < dailyList.size(); i++) {
            StatisticsDaily daily = dailyList.get(i);
            /* 封装日期 list 集合 */
            date_calculatedList.add(daily.getDateCalculated());
            /* 封装对应数量 */
            switch (type) {
                case "login_num":
                    numList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        /* 把封装之后的两个 List 放到 map 集合，进行返回 */
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", date_calculatedList);
        map.put("numList", numList);
        return map;
    }

}
