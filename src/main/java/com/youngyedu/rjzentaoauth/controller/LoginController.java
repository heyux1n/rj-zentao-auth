package com.youngyedu.rjzentaoauth.controller;

import com.youngyedu.rjzentaoauth.service.dto.AuthUserDto;
import com.youngyedu.rjzentaoauth.util.Md5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: heyuxin
 * @Create: 2022-06-21
 * @Description:
 */
@RestController
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestBody AuthUserDto authUser) {
        Subject subject = SecurityUtils.getSubject();
        String encryptPassword = Md5Util.encrypt(authUser.getPassword());
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(authUser.getAccount(), encryptPassword);
        try {
            subject.login(usernamePasswordToken);
            return subject.getSession().getId().toString();
        } catch (UnknownAccountException e) {
            throw new RuntimeException("用户不存在");
        } catch (DisabledAccountException e) {
            throw new RuntimeException("用户被禁用");
        } catch (IncorrectCredentialsException e) {
            throw new RuntimeException("用户或密码错误");
        }
    }

}