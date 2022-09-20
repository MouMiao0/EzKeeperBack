package com.example.ezkeeper.service.impl;

import com.example.ezkeeper.model.UserToken;
import com.example.ezkeeper.mappers.UserTokenMapper;
import com.example.ezkeeper.service.UserTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 密码表 服务实现类
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-18
 */
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {

}
