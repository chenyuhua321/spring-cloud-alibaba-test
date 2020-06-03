package com.cyh.user.controller;

import com.cyh.common.domain.IResponse;
import com.cyh.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Chenyuhua
 * @date 2020/5/24 14:32
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册接口
     * @param email 邮箱地址
     * @param password 密码
     * @param code 验证码
     * @return true 成功注册 false注册失败
     */
    @GetMapping("/user/register/{email}/{password}/{code}")
    public IResponse register(@PathVariable("email") String email, @PathVariable("password") String password, @PathVariable("code") String code){
        return userService.register(email,password,code);
    }

    /**
     * 是否已注册
     * @param email 邮箱
     * @return true 已注册 false 未注册
     */
    @GetMapping("/user/isRegistered/{email}")
    public IResponse isRegistered(@PathVariable("email") String email){
        Boolean flag = userService.isRegistered(email);
        if(flag){
            return new IResponse(flag,"该邮箱已注册");
        }
        return new IResponse(flag,"该邮箱尚未注册");
    }

    /**
     * 登录
     * @param email 邮箱
     * @param password 密码
     * @return 成功返回true data返回token
     */
    @GetMapping("/user/login/{email}/{password}")
    public IResponse login(@PathVariable("email") String email, @PathVariable("password") String password){
        return userService.login(email,password);
    }

    @GetMapping("/user/info/{token}")
    public IResponse userInfo(@PathVariable("token") String token){
        return userService.userInfo(token);
    }


}
