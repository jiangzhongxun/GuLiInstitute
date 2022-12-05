package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduorder.client.EduClient;
import com.atguigu.eduorder.client.UcenterClient;
import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.mapper.TOrderMapper;
import com.atguigu.eduorder.service.TOrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-12-02
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 创建订单, 返回订单号
     * @param courseId 课程id
     * @param memberId 会员 ID
     * @return {@link String}
     */
    @Override
    public String createOrders(String courseId, String memberId) {
        /* 通过远程调用，根据用户ID获取用户信息 */
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        /* 通过远程调用，根据课程ID获取课程信息 */
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        TOrder tOrder = new TOrder();
        tOrder.setOrderNo(OrderNoUtil.getOrderNo());
        tOrder.setCourseId(courseId);
        tOrder.setCourseTitle(courseInfoOrder.getTitle());
        tOrder.setCourseCover(courseInfoOrder.getCover());
        tOrder.setTeacherName(courseInfoOrder.getTeacherName());
        tOrder.setTotalFee(courseInfoOrder.getPrice());
        tOrder.setMemberId(memberId);
        tOrder.setMobile(userInfoOrder.getMobile());
        tOrder.setNickname(userInfoOrder.getNickname());
        tOrder.setStatus(0);
        tOrder.setPayType(1);
        int insert = baseMapper.insert(tOrder);
        if (insert != 1) {
            throw new GuliException(20001, "订单创建失败");
        }
        return tOrder.getOrderNo();
    }

}
