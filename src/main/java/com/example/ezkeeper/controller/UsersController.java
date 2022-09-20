package com.example.ezkeeper.controller;


import com.example.ezkeeper.JSONResult;
import com.example.ezkeeper.model.UserToken;
import com.example.ezkeeper.model.Users;
import com.example.ezkeeper.service.UsersService;
import com.example.ezkeeper.type.LoginPlatformType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author MouMeo
 * @since 2022-09-17
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    /**
     * 注册
     * @param users 用户信息
     * @param pw 密码
     * @return  json信息
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public JSONResult register(Users users, @RequestParam(value = "pw",defaultValue = "") String pw){

        if(users.getUserName() == null || users.getUserName().length() <= 0) return JSONResult.failMsg("请输入用户名");

        if(usersService.lambdaQuery().eq(Users::getUserName,users.getUserName()).one()!=null) return JSONResult.failMsg("改用户名已被注册");

        if(pw.length() <= 0) return JSONResult.failMsg("请输入密码");

        //注册用户
        if(!usersService.register(users, pw)) return JSONResult.failMsg("注册失败,请稍后重试");

        //返回信息和Token
        return JSONResult.success(usersService.getToken(users,pw),"注册成功");
    }

    /**
     * 用户登陆
     * @param userName 用户名
     * @param pw 密码
     * @return json消息
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONResult login(String userName, String pw){
        Subject subject = SecurityUtils.getSubject();

        Users user = new Users();
        user.setUserName(userName);
        UserToken userToken = new UserToken();
        userToken.setPw(pw);
        userToken.setUser(user);

        //用户登陆
        try{
            subject.login(new UsernamePasswordToken(
                    userToken.getUser().getUserName(),
                    userToken.getPw()
            ));
        }catch (AuthenticationException e){
            return JSONResult.failMsg("用户名或密码输入错误");
        }

        //返回Token
        user.setId(usersService.lambdaQuery().eq(Users::getUserName,userName).one().getId());
        return JSONResult.success(usersService.getToken(user,pw),"登陆成功");
    }

    /**
     * 手机登陆
     * @param phone
     * @param code
     * @return
     */
    @RequestMapping(value = "/login_by_phone",method = RequestMethod.POST)
    public JSONResult loginByPhone(@RequestParam(value = "phone",defaultValue = "-1")int phone,
                                   @RequestParam(value = "code",defaultValue = "-1") int code)
    {
        if(phone == -1 //todo: 同时验证手机号
            ){
            return JSONResult.failMsg("请输入正确的手机号");
        }

        if(code == -1){
            //Todo: 如果没有附带验证码,发送验证码;
            return JSONResult.custom("0","已发送验证码",null);
        }

        //todo: 同时发送token
        return JSONResult.success("验证成功");
    }

    /**
     * 第三方登陆
     */
    @RequestMapping(value = "other_login",method = RequestMethod.POST)
    public JSONResult otherLogin(String token, int type){

        //todo: 据不同平台选择登陆
        String newToken = usersService.getToken(token,LoginPlatformType.values()[type]);
        if(newToken != null) return JSONResult.success(newToken,"登陆成功");
        return null;
    }

}

