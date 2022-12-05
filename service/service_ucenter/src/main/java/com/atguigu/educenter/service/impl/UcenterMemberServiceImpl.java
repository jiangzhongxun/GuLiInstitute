package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 * @author jiangzhongxun
 * @since 2022-11-26
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 登录
     * @param member 成员
     * @return {@link String}
     */
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        /* 手机号、密码非空判断 */
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "用户名或密码不存在，登录失败！");
        }
        /* 判断用户是否正确 */
        LambdaQueryWrapper<UcenterMember> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UcenterMember::getMobile, mobile);
        UcenterMember mobileMember = baseMapper.selectOne(lambdaQueryWrapper);
        if (null == mobileMember) {
            throw new GuliException(20001, "用户名不存在，请先注册！");
        }
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001, "密码错误，请重新登录！");
        }
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(20001, "用户名已禁用！");
        }
        /* 登录成功，生成 token */
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    /**
     * 注册
     * @param registerVo 注册签证官
     */
    @Override
    public void register(RegisterVo registerVo) {
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        /* 注册信息非空判断 */
        if (StringUtils.isEmpty(nickname) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "注册失败，请正确填写注册信息！");
        }
        /* 验证码校验 */
        String mobileCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(mobileCode)) {
            throw new GuliException(20001, "验证码错误，请重新填写！");
        }
        /* 校验手机号是否注册过 */
        LambdaQueryWrapper<UcenterMember> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UcenterMember::getMobile, mobile);
        Integer selectCount = baseMapper.selectCount(lambdaQueryWrapper);
        if (selectCount > 0) {
            throw new GuliException(20001, "该手机号已注册过，请直接登录！");
        }
        /* 添加注册信息到数据库 */
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setAvatar("https://img2.baidu.com/it/u=2212383468,857153027&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        baseMapper.insert(ucenterMember);
    }

    /**
     * 查询 openid
     * @return {@link UcenterMember}
     */
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        LambdaQueryWrapper<UcenterMember> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UcenterMember::getOpenid, openid);
        UcenterMember member = baseMapper.selectOne(lambdaQueryWrapper);
        return member;
    }

    /**
     * 统计某天的注册人数
     * @param day 日期
     * @return {@link Integer}
     */
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }

}
