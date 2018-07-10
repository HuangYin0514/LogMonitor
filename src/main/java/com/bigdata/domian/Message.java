package com.bigdata.domian;

import lombok.Data;

import java.io.Serializable;

/**
 * Describe: 请补充类描述
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/11/11.
 */
@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 7241117393416877510L;
    private String appId;//消息所属服务器编号
    private String line;//消息内容  aid:1||msg:error java.lang.StackOverflowError
    private String ruleId;//规则编号
    private String keyword;//规则中的关键词
    private int isEmail;//是否已发送邮件
    private int isPhone;//是否已发送短信
    private String appName;//应用的名称

}
