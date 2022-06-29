package com.youngyedu.rjzentaoauth.service;

import com.youngyedu.rjzentaoauth.entity.Dept;
import com.youngyedu.rjzentaoauth.entity.User;
import com.youngyedu.rjzentaoauth.service.dto.DeptUserNodeDto;

import java.util.List;

/**
 * @Author: heyuxin
 * @Create: 2022-06-20
 * @Description:
 */
public interface DeptService {

    /**
     * 根据部门id获取部门信息
     * @param id
     * @return
     */
    Dept getDeptById(Integer id);

    /**
     * 获取部门列表
     * @return
     */
    List<Dept> getDeptList();

    /**
     * 根据部门id部门及其子级列表
     * @param id
     * @return
     */
    List<Dept> getDeptChildById(String id);
}
