package com.bigdata.domian;

import lombok.Data;

import java.io.Serializable;

/**
 * Describe: 用户信息
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/11/11.
 */
@Data
public class User  implements Serializable {
    private static final long serialVersionUID = -3904760809415605618L;
    private int id;//用户编号
    private String name;//用户名称
    private String mobile;//用户手机
    private String email;//用户邮箱
    private int isValid;//用户是否可用

}
