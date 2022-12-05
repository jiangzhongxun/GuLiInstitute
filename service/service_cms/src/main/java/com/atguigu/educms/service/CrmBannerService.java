package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-25
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 获取所有 banner
     * @return {@link List}<{@link CrmBanner}>
     */
    List<CrmBanner> getAllBanner();
}
