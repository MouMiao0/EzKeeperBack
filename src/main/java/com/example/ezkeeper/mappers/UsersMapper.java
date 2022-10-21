package com.example.ezkeeper.mappers;

import com.example.ezkeeper.cache.MybatisRedisCache;
import com.example.ezkeeper.model.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface UsersMapper extends BaseMapper<Users> {

}
