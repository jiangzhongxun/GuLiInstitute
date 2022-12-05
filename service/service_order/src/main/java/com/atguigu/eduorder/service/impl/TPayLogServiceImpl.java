package com.atguigu.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.entity.TPayLog;
import com.atguigu.eduorder.mapper.TPayLogMapper;
import com.atguigu.eduorder.service.TOrderService;
import com.atguigu.eduorder.service.TPayLogService;
import com.atguigu.eduorder.utils.HttpClient;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-02
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired
    private TOrderService orderService;

    /**
     * 生成微信支付二维码接口
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link String}>
     */
    @Override
    public Map createNative(String orderNo) {
        try {
            /* 1、根据订单号获取订单信息 */
            QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_no", orderNo);
            TOrder order = orderService.getOne(queryWrapper);
            /* 2、使用 map 设置生成二维码支付所需要的参数 */
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");   // 关联的公众号appid
            m.put("mch_id", "1558950191");      // 商户号
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle());  // 课程标题
            m.put("out_trade_no", orderNo);     // 订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");    // 支付地址
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");  // 支付类型
            /* 3、发送 httpClient 请求，传递参数 xml 格式，微信支付提供的固定地址 */
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            /* client设置 xml 格式的参数 */
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            /* 执行 post 请求发送 */
            client.post();
            /* 4、得到发送请求返回结果 */
            String xml = client.getContent();   // 返回的内容是 xml 格式的
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);// 把 xml 格式转换 map 集合，把 map 集合返回
            /* 最终返回数据的封装 */
            Map<String, Object> map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));   // 返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));     // 二维码地址
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
            //throw new GuliException(20001, "生成二维码失败！");
        }
    }

    /**
     * 查询支付状态
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link String}>
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            /* 1、封装参数 */
            Map<String, String> m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            /* 2、设置请求 */
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            /* 3、返回第三方的数据 */
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            /* 6、转成Map */
            /* 7、返回 */
            return resultMap;
        } catch (Exception e) {
            throw new GuliException(20001, "查询支付状态失败");
        }
    }

    /**
     * 更新订单状态
     * @param map map
     */
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        /* 获取订单号 */
        String orderNo = map.get("out_trade_no");
        /* 根据订单id查询订单信息 */
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        TOrder order = orderService.getOne(wrapper);
        //更新订单表状态
        if(order.getStatus().intValue() == 1) return;
        //controller已经判断state
        order.setStatus(1);
        orderService.updateById(order);
        /* 记录支付日志 */
        TPayLog payLog=new TPayLog();
        payLog.setOrderNo(order.getOrderNo());  //支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);   //支付类型
        payLog.setTotalFee(order.getTotalFee());    //总金额(分)
        payLog.setTradeState(map.get("trade_state"));   //支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);  //插入到支付日志表
    }

}
