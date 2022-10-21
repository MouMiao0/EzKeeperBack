package com.example.ezkeeper.mappers;

import com.example.ezkeeper.cache.MybatisRedisCache;
import com.example.ezkeeper.model.UserToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 密码表 Mapper 接口
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-18
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface UserTokenMapper extends BaseMapper<UserToken> {
    UserToken getTokenByName(@Param("userName") String userName);
}
