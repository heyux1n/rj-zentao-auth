package com.youngyedu.rjzentaoauth.controller;

import com.youngyedu.rjzentaoauth.entity.User;
import com.youngyedu.rjzentaoauth.service.UserService;
import com.youngyedu.rjzentaoauth.service.dto.DeptUserNodeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: heyuxin
 * @Create: 2022-06-20
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping
    public User getUser() {
        return userService.getUser();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/list")
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/tree")
    public List<DeptUserNodeDto> getDeptUserTreeNodeList() {
        return userService.getDeptUserTreeNodeList();
    }

}
