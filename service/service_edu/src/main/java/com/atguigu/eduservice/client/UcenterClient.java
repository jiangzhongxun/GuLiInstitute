package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author jiang zhongxun
 * @create 2022-12-01 15:36
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterClientImpl.class)
public interface UcenterClient {

    /**
     * 根据token字符串获取用户信息
     * 根据用户id获取用户信息
     * @param id id
     * @return {@link R}
     */
    @PostMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMemberVo getInfoUc(@PathVariable("id") String id);

}
