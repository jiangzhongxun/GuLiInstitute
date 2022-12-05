package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-02
 */
public interface TOrderService extends IService<TOrder> {

    /**
     * 创建订单, 返回订单号
     * @param courseId 课程id
     * @param memberId 成员身份
     * @return {@link String}
     */
    String createOrders(String courseId, String memberId);
}
