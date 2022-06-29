package com.youngyedu.rjzentaoauth.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @Author: heyuxin
 * @Create: 2022-06-27
 * @Description:部门树形信息节点
 */
@Data
@AllArgsConstructor
public class DeptUserNodeDto {

    /**
     * 唯一标识
     */
    private String key;
    /**
     * 用户或部门id值
     */
    private Integer id;
    /**
     * 节点类型
     */
    private Type type;
    /**
     * 节点展示标题
     */
    private String title;
    /**
     * 子级节点
     */
    private List<DeptUserNodeDto> child;



    @AllArgsConstructor
    @Getter
    public enum Type {
        /**
         * 用户
         */
        USER(),
        /**
         * 部门
         */
        DEPT();
    }
}
