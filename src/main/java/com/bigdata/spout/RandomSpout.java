package com.bigdata.spout;

import backtype.storm.spout.Scheme;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created with IDEA by User1071324110@qq.com
 *
 * @author 10713
 * @date 2018/7/9 23:15
 */
public class RandomSpout extends BaseRichSpout {

    private static final long serialVersionUID = -6405249193989943211L;
    public static final String ERROR_AT_COM_STARIT_GEJIE_UTIL_TRANS_BL_GET_SYS_NAMES_BY_TYPE_TRANS_JAVA_220 = "1||error at com.starit.gejie.Util.Trans.BL_getSysNamesByType(Trans.java:220)";
    private SpoutOutputCollector collector;
    private TopologyContext context;
    private List list;
    private final Scheme scheme;

    public RandomSpout(Scheme scheme) {
        this.scheme = scheme;
    }

    //初始化信息
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.context = context;
        this.collector = collector;
        list = new ArrayList();
        list.add("aid:1||error: Caused by: java.lang.NoClassDefFoundError: com/starit/gejie/dao/SysNameDao");
        list.add("aid:2||java.sql.SQLException: You have an error in your SQL syntax;");
        list.add("aid:1||error Unable to connect to any of the specified MySQL hosts.");
        list.add("aid:1||error:Servlet.service() for servlet action threw exception java.lang.NullPointerException");
        list.add("aid:2||error:java.lang.NoClassDefFoundError: org/coffeesweet/test01/Test01");
    }

    /**
     * 发送信息
     */
    public void nextTuple() {
        final Random random = new Random();
        String msg = list.get(random.nextInt(5)).toString();
        this.collector.emit(this.scheme.deserialize(msg.getBytes()));
//        this.collector.emit(new Values(msg));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(this.scheme.getOutputFields());
//        declarer.declare(new Fields("word"));
    }
}
