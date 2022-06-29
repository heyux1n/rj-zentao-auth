package com.youngyedu.rjzentaoauth.service;

import com.youngyedu.rjzentaoauth.entity.User;
import com.youngyedu.rjzentaoauth.service.dto.DeptUserNodeDto;

import java.util.List;

/**
 * @Author: heyuxin
 * @Create: 2022-06-20
 * @Description:
 */
public interface UserService {

    /**
     * 获取用户列表
     * @return
     */
    List<User> getUserList();


    /**
     * 获取当前登录用户
     * @return
     */
    User getUser();


    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    User getUserById(Integer id);


    /**
     * 根据用户名获取用户信息
     * @param account
     * @return
     */
    User getUserByAccount(String account);

    /**
     * 获取部门用户树形节点列表
     * @return
     */
    List<DeptUserNodeDto> getDeptUserTreeNodeList();
}
