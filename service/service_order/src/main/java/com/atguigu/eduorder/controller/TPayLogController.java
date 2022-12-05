package com.atguigu.eduorder.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduorder.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-02
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class TPayLogController {

    @Autowired
    private TPayLogService payLogService;

    /**
     * 生成微信支付二维码接口
     * @param orderNo 订单号
     * @return {@link R}
     */
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable("orderNo") String orderNo) {
        /* 返回信息，包含支付二维码地址，还有其他需要的信息 */
        Map map = payLogService.createNative(orderNo);
        System.out.println("返回二维码 map 集合 = " + map);
        return R.ok().data(map);
    }

    /**
     * 查询支付状态
     * @param orderNo 订单号
     * @return {@link R}
     */
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable("orderNo") String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("查询订单状态 map 集合 = " + map);
        if (map == null) {
            return R.error().message("支付出错了！");
        }
        if ("SUCCESS".equals(map.get("trade_state"))) {
            /* 更改订单状态: 添加记录到支付表，更新订单表订单状态 */
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功！");
        }
        return R.ok().code(25000).message("支付中。。。");
    }

}

