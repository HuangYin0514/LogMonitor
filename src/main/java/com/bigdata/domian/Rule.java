package com.bigdata.domian;

import lombok.Data;

import java.io.Serializable;

/**
 * Created with IDEA by User1071324110@qq.com
 *
 * @author 10713
 * @date 2018/7/10 10:26
 */
@Data
public class Rule implements Serializable {
    private static final long serialVersionUID = -5426252008335259743L;
    private int id;//规则编号
    private String name;//规则名称
    private String keyword;//规则过滤的关键字
    private int isValid;//规则是否可用
    private int appId;//规则所属的应用
}
