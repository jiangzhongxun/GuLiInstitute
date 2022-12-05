package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器  管理员使用
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-25
 */
@RestController
@RequestMapping("/educms/banneradmin")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 分页查询 banner
     * @param page  当前页数
     * @param limit 每页条数
     * @return {@link R}
     */
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        bannerService.page(bannerPage, null);
        return R.ok().data("items", bannerPage.getRecords()).data("total", bannerPage.getTotal());
    }

    /**
     * 添加 banner
     * @param crmBanner banner
     * @return {@link R}
     */
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    /**
     * 修改 banner
     * @param crmBanner banner
     * @return {@link R}
     */
    @PostMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.updateById(crmBanner);
        return R.ok();
    }

    /**
     * 删除 banner
     * @param id id
     * @return {@link R}
     */
    @DeleteMapping("/removeBanner/{id}")
    public R deleteBanner(@PathVariable String id) {
        boolean b = bannerService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            throw new GuliException(20001, "删除 banner 失败！");
        }
    }

    /**
     * 根据 ID 查询 banner
     * @param id id
     * @return {@link R}
     */
    @GetMapping("/getBanner/{id}")
    public R getBanner(@PathVariable String id) {
        CrmBanner crmBanner = bannerService.getById(id);
        return R.ok().data("banner", crmBanner);
    }

}

