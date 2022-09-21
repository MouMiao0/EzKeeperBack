package com.example.ezkeeper.realm;

import com.example.ezkeeper.mappers.UserTokenMapper;
import com.example.ezkeeper.mappers.UsersMapper;
import com.example.ezkeeper.model.UserToken;
import com.example.ezkeeper.model.Users;
import com.example.ezkeeper.service.UserTokenService;
import com.example.ezkeeper.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class DefaultRealm extends AuthorizingRealm {
    @Autowired(required = false)
    UserTokenMapper userTokenMapper;

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    UsersService usersService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //暂时不需要权限管理
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

//        用户名密码登陆
        UserToken userToken = userTokenMapper.getTokenByName(usernamePasswordToken.getUsername());

        if(userToken == null) return null;

        ByteSource salt = ByteSource.Util.bytes(userToken.getUserId().toString());
        return new SimpleAuthenticationInfo(
                userToken.getUser().getUserName(),
                userToken.getPw(),
                salt,
                "DefaultRealm");
    }
}
