package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-26
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 统计某天的注册人数
     * @param day 日期
     * @return {@link Integer}
     */
    Integer countRegisterDay(String day);
}
