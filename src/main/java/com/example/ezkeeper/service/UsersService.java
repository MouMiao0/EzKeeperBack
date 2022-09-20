package com.example.ezkeeper.service;

import com.example.ezkeeper.model.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ezkeeper.type.LoginPlatformType;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
public interface UsersService extends IService<Users> {

    /**
     * 注册
     * @param user 用户信息
     * @param pw 密码
     * @return 是否成功
     */
    Boolean register(Users user, String pw);

    /**
     * 用户密码登陆
     * @param user
     * @return
     */
    String getToken(Users user, String pw);

    /**
     * 手机验证码登陆
     * @param phone
     * @param code
     * @return
     */
    String getToken(int phone, int code);

    /**
     * 第三方登陆
     * @param token
     * @return
     */
    String getToken(String token, LoginPlatformType platformType);
}
