package com.bigdata.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.bigdata.domian.Record;
import com.bigdata.utils.MonitorHandle;
import org.apache.log4j.Logger;


/**
 * Created with IDEA by User1071324110@qq.com
 *
 * @author 10713
 * @date 2018/7/10 21:19
 */
public class SaveMessage2MySql extends BaseBasicBolt {

    private static final long serialVersionUID = 8747467484069747254L;
    private static Logger logger = Logger.getLogger(SaveMessage2MySql.class);


    public void execute(Tuple input, BasicOutputCollector collector) {
        Record record = (Record) input.getValueByField("record");
        System.out.println(record);
        MonitorHandle.save(record);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
