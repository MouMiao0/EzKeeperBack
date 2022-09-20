package com.example.ezkeeper.realm;

import com.example.ezkeeper.Util.JWTUtil;
import com.example.ezkeeper.model.Users;
import com.example.ezkeeper.service.UsersService;
import com.example.ezkeeper.token.ShiroToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenRealm extends AuthorizingRealm {
    @Autowired
    UsersService usersService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ShiroToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String jwt = authenticationToken.getPrincipal().toString();

        if(jwt == null){
            return null;
        }

        if(!JWTUtil.verify(jwt)) return null;

        int userId = Integer.parseInt(JWTUtil.getToken(jwt).getClaim("userId").asString());
        String userName = JWTUtil.getToken(jwt).getClaim("userName").asString();
        Users user = usersService.getById(userId);
        if(user == null || !user.getUserName().equals(userName)) return null;

        return new SimpleAuthenticationInfo(userId,jwt,"TokenRealm");
    }
}
