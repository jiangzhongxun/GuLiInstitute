package com.atguigu.educenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.ConstantWxUtils;
import com.atguigu.educenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author jiang zhongxun
 * @create 2022-11-27 13:27
 */
@CrossOrigin
@Controller //注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * 回调( 获取扫描人信息，添加数据 )
     * @return {@link String}
     */
    @GetMapping("/callback")
    public String callback(String code, String state) {
        try {
            /* 1. 获取 code 值（临时票据，类似于验证码） */
            /* 2. 拿着 code 请求微信固定的地址，得到两个值，access_token 和 openid */
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token"
                    + "?appid=%s"
                    + "&secret=%s"
                    + "&code=%s"
                    + "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantWxUtils.WX_OPEN_APP_ID, ConstantWxUtils.WX_OPEN_APP_SECRET, code);
            /* 请求拼接好的地址，得到返回的两个值，access_token 和 openid；使用 httpClient 发送请求，得到返回结果 */
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            /* 从 accessTokenInfo 字符串中取出来两个值，access_token 和 openid；把 accessTokenInfo 字符串转换成 Map集合，根据 Map 中的 key 获取对应值 */
            Gson gson = new Gson();
            HashMap<String, Object> mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");
            /* 把扫码人的信息添加到数据库中，需要判断数据库中是否存在此信息 */
            UcenterMember member = memberService.getOpenIdMember(openid);
            if (member == null) {
                /* 3. 根据返回的两个值，access_token 和 openid 访问微信固定的资源服务器，获取用户信息 */
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" + "?access_token=%s" + "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                /* 发送请求 */
                String userInfo = HttpClientUtils.get(userInfoUrl);
                /* 获取返回的 userInfo 字符串中的扫码人信息 */
                HashMap<String, Object> userMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userMap.get("nickname");
                String headimgurl = (String) userMap.get("headimgurl");
                member = new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            /* 使用 JWT 根据 member 对象生成 token 字符串 */
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            /* 返回首页，通过路径传递 token 字符串 ( cookie 不能跨域，所以使用 URL 重写 ) */
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            throw new GuliException(20001, e.getMessage());
        }
    }

    /**
     * 生成微信扫描的二维码
     * @return {@link String}
     */
    @GetMapping("/login")
    public String getWxCode() {
        /* 固定地址，后面拼接参数 */
        //String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" + ConstantWxUtils.WX_OPEN_APP_ID + "&redirect_uri="
        //        + ConstantWxUtils.WX_OPEN_REDIRECT_URL + "&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        /* 微信开放平台授权 baseUrl, %s 相当于 ？代表占位符 */
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        /* 使用 urlEncoder 对链接进行编码处理 */
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (Exception e) {
            throw new GuliException(20001, e.getMessage());
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");  //一般情况下会使用一个随机数
        String state = "atguigu";   //为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        /* 设置 %s 的值 */
        String url = String.format(baseUrl, ConstantWxUtils.WX_OPEN_APP_ID, redirectUrl, state);
        /* 重定向到 请求微信地址 */
        return "redirect:" + url;
    }

}
