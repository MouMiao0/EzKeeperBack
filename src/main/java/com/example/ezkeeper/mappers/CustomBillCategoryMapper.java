package com.example.ezkeeper.mappers;

import com.example.ezkeeper.cache.MybatisRedisCache;
import com.example.ezkeeper.model.CustomBillCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 用户自定账单分类表
 Mapper 接口
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface CustomBillCategoryMapper extends BaseMapper<CustomBillCategory> {

}
