package com.bigdata.bolt;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import org.apache.log4j.Logger;

import java.util.Map;


/**
 * Created with IDEA by User1071324110@qq.com
 *
 * @author 10713
 * @date 2018/7/9 23:47
 */
public class FilterBolt extends BaseBasicBolt {
    private static final long serialVersionUID = 2704922669352741932L;
    private static Logger logger = Logger.getLogger(FilterBolt.class);

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        super.prepare(stormConf, context);
    }

    public void execute(Tuple input, BasicOutputCollector collector) {
        String line = input.getString(1);
        System.out.println("============================================================");
        System.out.println(line);
        System.out.println("============================================================");
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
