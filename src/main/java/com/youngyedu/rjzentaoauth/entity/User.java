package com.youngyedu.rjzentaoauth.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: heyuxin
 * @Create: 2022-06-20
 * @Description: 禅道用户表
 */
@Data
@AllArgsConstructor
@TableName("zt_user")
public class User implements Serializable {
    @TableId(value = "id")
    private Integer id;
    private String account;
    private String password;
    private String realname;
    private String nickname;
    private String role;
    private Integer dept;
}
