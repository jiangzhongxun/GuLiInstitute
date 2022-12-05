package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.commonutils.vo.UcenterMemberVo;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-26
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 统计某天的注册人数
     * @param day 日期
     * @return {@link R}
     */
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day) {
        Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister", count);
    }

    /**
     * 登录
     * @param member 成员
     * @return {@link R}
     */
    @PostMapping("/login")
    public R loginUser(@RequestBody UcenterMember member) {
        /* 返回 token 值，使用 JWT 生成 */
        String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    /**
     * 注册用户
     * @param registerVo 注册签证官
     * @return {@link R}
     */
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据 token 获取用户信息
     * @param request 请求
     * @return {@link R}
     */
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        /* 调用 JWT 工具类方法，根据 request 对象获取头信息，返回用户 ID */
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    /**
     * 根据token字符串获取用户信息
     * 根据用户id获取用户信息
     * @param id id
     * @return {@link R}
     */
    @PostMapping("/getInfoUc/{id}")
    public UcenterMemberVo getInfoUc(@PathVariable("id") String id) {
        UcenterMember ucenterMember = memberService.getById(id);
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(ucenterMember, ucenterMemberVo);
        return ucenterMemberVo;
    }

    /**
     * 根据用户ID 获取用户信息 ——— 订单相关
     * @param id id
     * @return {@link UcenterMemberOrder}
     */
    @PostMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id) {
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder memberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, memberOrder);
        return memberOrder;
    }

}

