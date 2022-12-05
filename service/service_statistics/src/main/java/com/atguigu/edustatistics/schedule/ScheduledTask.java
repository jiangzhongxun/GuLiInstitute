package com.atguigu.edustatistics.schedule;

import com.atguigu.edustatistics.service.StatisticsDailyService;
import com.atguigu.edustatistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务类，使用cron表达式
 * @author jiang zhongxun
 * @create 2022-12-03 17:08
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 测试
     * 每五秒执行一次
     */
    //@Scheduled(cron = "0/5 * * * * ?")
    //public void task1() {
    //    System.out.println("*********++++++++++++*****执行了");
    //}

    /**
     * 每天凌晨1点执行定时
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.registerCount(day);
    }

}
