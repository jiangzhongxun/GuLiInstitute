package com.atguigu.eduservice.client;

import com.atguigu.commonutils.vo.UcenterMemberVo;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

/**
 * @author jiang zhongxun
 * @create 2022-12-01 15:36
 */
@Component
public class UcenterClientImpl implements UcenterClient {

    @Override
    public UcenterMemberVo getInfoUc(String id) {
        throw new GuliException(20001, "获取用户信息失败");
    }

}
