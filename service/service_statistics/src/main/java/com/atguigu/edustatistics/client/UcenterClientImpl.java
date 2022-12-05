package com.atguigu.edustatistics.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @author jiang zhongxun
 * @create 2022-12-03 15:22
 */
@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public R countRegister(String day) {
        return R.error().message("获取注册人数统计失败！");
    }
}
