package com.example.ezkeeper.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Calendar;
import java.util.Map;

public class JWTUtil {
    //签名
    static String SIGNATURE = "token!qwerty123";

    /**
     * 生成token
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        //设置过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);//一个月
        builder.withExpiresAt(calendar.getTime());

        return builder.sign(Algorithm.HMAC256(SIGNATURE));
    }

    /**
     *  验证token
     */
    public static Boolean verify(String token){
        JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
        return true;
    }

    /**
     *  获取token的信息
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    /**
     * 从令牌获取用户id
     * @return 用户id
     */
    public static int getUserIdBySubject(){
        return Integer.parseInt(SecurityUtils.getSubject().getPrincipal().toString());
    }
}
