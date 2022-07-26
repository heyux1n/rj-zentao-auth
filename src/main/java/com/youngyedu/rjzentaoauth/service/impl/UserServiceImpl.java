package com.youngyedu.rjzentaoauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngyedu.rjzentaoauth.entity.Dept;
import com.youngyedu.rjzentaoauth.entity.User;
import com.youngyedu.rjzentaoauth.mapper.UserMapper;
import com.youngyedu.rjzentaoauth.service.DeptService;
import com.youngyedu.rjzentaoauth.service.UserService;
import com.youngyedu.rjzentaoauth.service.dto.DeptUserNodeDto;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: heyuxin
 * @Create: 2022-06-20
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private DeptService deptService;

    @Override
    public List<User> getUserList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", '0');
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public User getUser() {
        return this.getUserByAccount("heyuxin");
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUserByAccount(String account) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public List<DeptUserNodeDto> getDeptUserTreeNodeList() {
        List<Dept> deptList = deptService.getDeptChildById(null);
        List<User> userList = this.getUserList();

        Map<Integer, List<DeptUserNodeDto>> deptIdUserMap = new HashMap<>(16);
        for (User user : userList) {
            Integer deptId = user.getDept();
            String key = DeptUserNodeDto.Type.USER + "_" + user.getId();
            DeptUserNodeDto node = new DeptUserNodeDto(key, user.getId(), DeptUserNodeDto.Type.USER, user.getRealname(), null);
            List<DeptUserNodeDto> deptUserNodeDtoList = deptIdUserMap.get(deptId);
            if (CollectionUtils.isEmpty(deptUserNodeDtoList)) {
                deptUserNodeDtoList = new ArrayList<>();
            }
            deptUserNodeDtoList.add(node);
            deptIdUserMap.put(deptId, deptUserNodeDtoList);
        }

        List<DeptUserNodeDto> treeNodeList = new ArrayList<>();
        for (Dept dept : deptList) {
            Integer deptId = dept.getId();
            List<DeptUserNodeDto> childList = deptIdUserMap.get(deptId);
            //树形一级节点
            String key = DeptUserNodeDto.Type.DEPT + "_" + dept.getId();
            DeptUserNodeDto node = new DeptUserNodeDto(key, deptId, DeptUserNodeDto.Type.DEPT, dept.getName(), childList);
            treeNodeList.add(node);
        }
        return treeNodeList;
    }
}
