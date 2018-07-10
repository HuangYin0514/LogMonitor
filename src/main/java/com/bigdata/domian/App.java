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
public class App  implements Serializable {
    private static final long serialVersionUID = 5851904527889102588L;
    private int id;//应用编号
    private String name;//应用名称
    private int isOnline;//应用是否在线
    private int typeId;//应用所属类别
    private String userId;//应用的负责人，多个用户用逗号分开

}
