package com.bigdata.spout;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.List;

/**
 * Created with IDEA by User1071324110@qq.com
 *
 * @author 10713
 * @date 2018/7/9 23:39
 */
public class StringScheme implements Scheme {

    private static final long serialVersionUID = 5915328710727479829L;

    public List<Object> deserialize(byte[] ser) {
        try {
            return new Values(new String(ser));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Fields getOutputFields() {
        return new Fields("line");
    }
}
