package com.example.ezkeeper.service.impl;

import com.example.ezkeeper.Util.JWTUtil;
import com.example.ezkeeper.mappers.UserTokenMapper;
import com.example.ezkeeper.model.UserToken;
import com.example.ezkeeper.model.Users;
import com.example.ezkeeper.mappers.UsersMapper;
import com.example.ezkeeper.service.UserTokenService;
import com.example.ezkeeper.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ezkeeper.type.LoginPlatformType;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@EnableRabbit
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    UserTokenService userTokenService;

    private String algorithmName = "MD5";


    private int iterationCount = 123;

    @Autowired(required = false)
    UserTokenMapper userTokenMapper;

    @Override
    public Users getByUserName(String userName) {
        return baseMapper.getByUserName(userName);
    }

    @Transactional
    @Override
    public Boolean register(Users user, String pw){

        if(baseMapper.insert(user) != 1) return false;

        UserToken userToken = new UserToken();
        userToken.setUserId(user.getId());

        ByteSource salt = ByteSource.Util.bytes(user.getId().toString());
        String hashPW = new SimpleHash(algorithmName,
                pw,
                salt,
                iterationCount).toHex();
        userToken.setPw(hashPW);

        return userTokenMapper.insert(userToken) == 1;
    }

    @Override
    public String getToken(Users user) {
        Users theUser = lambdaQuery().eq(Users::getUserName,user.getUserName()).one();
        if(theUser == null ){
            return null;
        }
        Map<String, String> tokenInfo = new LinkedHashMap<>();
        tokenInfo.put("userId",user.getId().toString());
        tokenInfo.put("userName",user.getUserName());
        return JWTUtil.getToken(tokenInfo);
    }

    @Override
    public String getToken(int phone, int code) {
        return null;
    }

    @Override
    public String getToken(String token, LoginPlatformType platformType) {
        return null;
    }
}
