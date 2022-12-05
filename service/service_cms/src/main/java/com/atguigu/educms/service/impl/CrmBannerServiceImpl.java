package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author jiangzhongxun
 * @since 2022-11-25
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 获取所有 banner
     * @return {@link List}<{@link CrmBanner}>
     */
    /*
      1、@Cacheable：根据方法对其返回结果进行缓存，下次请求时，若缓存存在，则直接读取缓存数据返回；若缓存不存在，则执行方法，并把返回的结果存入缓存中。一般用在查询方法上。
            value：缓存名，必填，它指定了你的缓存存放在哪块命名空间；
            cacheNames：与 value 差不多，二选一即可；
            key：可选属性，可以使用 SpEL 标签自定义缓存的key；
      2、@CachePut：使用该注解标志的方法，每次都会执行，并将结果存入指定的缓存中。其他方法可以直接从响应的缓存中读取缓存数据，而不需要再去查询数据库。一般用在新增方法上。
            value：缓存名，必填，它指定了你的缓存存放在哪块命名空间；
            cacheNames：与 value 差不多，二选一即可；
            key：可选属性，可以使用 SpEL 标签自定义缓存的key；
      3、@CacheEvict：使用该注解标志的方法，会清空指定的缓存。一般用在更新或者删除方法上。
            value：缓存名，必填，它指定了你的缓存存放在哪块命名空间；
            cacheNames：与 value 差不多，二选一即可；
            key：可选属性，可以使用 SpEL 标签自定义缓存的key；
            allEntries：是否清空所有缓存，默认为 false。如果指定为 true，则方法调用后将立即清空所有的缓存；
            beforeInvocation：是否在方法执行前就清空，默认为 false。如果指定为 true，则在方法执行前就会清空缓存；
     */
    @Override
    @Cacheable(key = "'selectIndexList'", value = "banner")
    public List<CrmBanner> getAllBanner() {
        /* 根据 ID 进行降序排序，显示排列之后的前两条数据 */
        QueryWrapper<CrmBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        /* last 方法，拼接 SQL 语句 */
        queryWrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(queryWrapper);
        return list;
    }

}
