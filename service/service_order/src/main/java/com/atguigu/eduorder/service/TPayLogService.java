package com.atguigu.eduorder.service;

import com.atguigu.eduorder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-02
 */
public interface TPayLogService extends IService<TPayLog> {

    /**
     * 生成微信支付二维码接口
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map createNative(String orderNo);

    /**
     * 查询支付状态
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link String}>
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 更新订单状态
     * @param map map
     */
    void updateOrderStatus(Map<String, String> map);
}
