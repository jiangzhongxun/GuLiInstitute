package com.atguigu.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author jiang zhongxun
 * @create 2022-12-03 12:38
 */
@Component
@FeignClient("service-order")
public interface OrdersClient {

    /**
     * 根据课程ID 和 用户ID 查询订单表中订单状态
     * @param courseId 课程id
     * @param memberId 会员id
     * @return boolean
     */
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId);

}
