package com.youngyedu.rjzentaoauth.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: heyuxin
 * @Create: 2022-06-20
 * @Description: 禅道部门表
 */
@Data
@AllArgsConstructor
@TableName("zt_dept")
public class Dept implements Serializable {
    @TableId(value = "id")
    private Integer id;
    private String name;
    private String parent;
    private String path;
    private String grade;
}
