package com.example.ezkeeper.mappers;

import com.example.ezkeeper.cache.MybatisRedisCache;
import com.example.ezkeeper.model.BillCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 默认账目分类
 Mapper 接口
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface BillCategoryMapper extends BaseMapper<BillCategory> {

}
