package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-26
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录
     * @param member 成员
     * @return {@link String}
     */
    String login(UcenterMember member);

    /**
     * 注册
     * @param registerVo 注册签证官
     */
    void register(RegisterVo registerVo);

    /**
     * 查询 openid
     * @return {@link UcenterMember}
     */
    UcenterMember getOpenIdMember(String openid);

    /**
     * 统计某天的注册人数
     * @param day 日期
     * @return {@link Integer}
     */
    Integer countRegisterDay(String day);
}
