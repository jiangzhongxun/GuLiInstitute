package com.atguigu.eduorder.client;

import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author jiang zhongxun
 * @create 2022-12-02 14:59
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    /**
     * 根据用户ID 获取用户信息 ——— 订单相关
     * @param id id
     * @return {@link UcenterMemberOrder}
     */
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);

}
