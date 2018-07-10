package com.bigdata.domian;

import lombok.Data;

import java.io.Serializable;

/**
 * Describe: 触发报警之后的记录
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/11/11.
 */
@Data
public class Record  implements Serializable {
    private static final long serialVersionUID = -448694989531619988L;
    private int id;//告警信息编号
    private int appId;//告警信息所属的应用
    private int ruleId;//告警信息所属的规则
    private int isEmail;//告警信息是否通过邮件告警
    private int isPhone;//告警信息是否通过短信告警
    private int isColse;//告警信息是否处理完毕
    private String line;//原始日志信息

}
