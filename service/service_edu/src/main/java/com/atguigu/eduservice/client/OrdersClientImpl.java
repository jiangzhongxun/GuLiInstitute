package com.atguigu.eduservice.client;

import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

/**
 * @author jiang zhongxun
 * @create 2022-12-03 12:39
 */
@Component
public class OrdersClientImpl implements OrdersClient {

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        throw new GuliException(20001, "获取订单支付信息失败");
    }

}
