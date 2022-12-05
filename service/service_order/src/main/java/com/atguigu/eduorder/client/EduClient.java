package com.atguigu.eduorder.client;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author jiang zhongxun
 * @create 2022-12-02 14:59
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    /**
     * 根据课程 ID 获取课程信息
     * @param id id
     * @return {@link CourseWebVoOrder}
     */
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);

}
