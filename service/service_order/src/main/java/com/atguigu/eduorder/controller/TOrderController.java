package com.atguigu.eduorder.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-02
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService orderService;

    /**
     * 创建订单, 返回订单号
     * @param courseId 课程id
     * @param request  请求
     * @return {@link R}
     */
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo =  orderService.createOrders(courseId, memberId);
        return R.ok().data("orderId", orderNo);
    }

    /**
     * 根据订单 ID 获取订单信息
     * @param orderId 订单id
     * @return {@link R}
     */
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderId);
        TOrder order = orderService.getOne(queryWrapper);
        return R.ok().data("item", order);
    }

    /**
     * 根据课程ID 和 用户ID 查询订单表中订单状态
     * @param courseId 课程id
     * @param memberId 会员id
     * @return boolean
     */
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,
                               @PathVariable("memberId") String memberId) {
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("status", 1);
        int count = orderService.count(queryWrapper);
        return count > 0;
    }

}

