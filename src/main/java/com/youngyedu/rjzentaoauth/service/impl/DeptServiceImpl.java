package com.youngyedu.rjzentaoauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.StringUtils;
import com.youngyedu.rjzentaoauth.entity.Dept;
import com.youngyedu.rjzentaoauth.mapper.DeptMapper;
import com.youngyedu.rjzentaoauth.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: heyuxin
 * @Create: 2022-06-27
 * @Description:
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptMapper deptMapper;

    @Override
    public Dept getDeptById(Integer id) {
        return deptMapper.selectById(id);
    }

    @Override
    public List<Dept> getDeptList() {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        return deptMapper.selectList(queryWrapper);
    }

    @Override
    public List<Dept> getDeptChildById(String id) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        //path中包含当前部门id及父级部门id
        if(!StringUtils.isNullOrEmpty(id)) {
            queryWrapper.like("path", id);
        }
        return deptMapper.selectList(queryWrapper);
    }
}
