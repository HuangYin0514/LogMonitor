package com.bigdata.bolt;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.bigdata.domian.Message;
import com.bigdata.utils.MonitorHandle;
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
        String line = input.getString(0);
        //对数据进行解析
        // appid   content
        //aid:1||msg:error java.lang.StackOverflowError
        Message message = MonitorHandle.parser(line);
        if (message == null) {
            return;
        }
        //判断规则，成功之后，将message对象发送的下游。
        //此时的message对象中包含：line,appId,ruleId,keyword
        if (MonitorHandle.trigger(message)) {
            collector.emit(new Values(message.getAppId(), message));
        }
        //定时更新规则信息 每十分钟更新一次规则库
        MonitorHandle.scheduleLoad();
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("appId", "message"));

    }
}
