package com.bigdata.bolt;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.FailedException;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.bigdata.domian.Message;
import com.bigdata.domian.Record;
import com.bigdata.utils.MonitorHandle;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

/**
 * Created with IDEA by User1071324110@qq.com
 *
 * 将触发信息保存到mysql数据库中
 * BaseRichBolt 需要手动调ack方法，BaseBasicBolt由storm框架自动调ack方法
 *
 * @author 10713
 * @date 2018/7/10 20:55
 */
public class PrepareRecordBolt extends BaseBasicBolt {
    private static final long serialVersionUID = -7734718032830882552L;
    private static Logger logger = Logger.getLogger(PrepareRecordBolt.class);
    private JedisPool pool;
    private Jedis jedis;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        //change "maxActive" -> "maxTotal" and "maxWait" -> "maxWaitMillis" in all examples
        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(5);
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setMaxTotal(1000 * 100);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(30);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        /**
         *如果你遇到 java.net.SocketTimeoutException: Read timed out exception的异常信息
         *请尝试在构造JedisPool的时候设置自己的超时值. JedisPool默认的超时时间是2秒(单位毫秒)
         */
        pool = new JedisPool(config, "192.168.25.181", 6379);
        jedis = pool.getResource();
        super.prepare(stormConf, context);
    }

    public void execute(Tuple input, BasicOutputCollector collector) {
        Message message = (Message) input.getValueByField("message");
        String appId = input.getStringByField("appId");
        //将触发规则的信息进行通知-------------如何处理重复通知，相当于一个异常通知了很多遍？
        // appid，ruleid，line，keyword
        //设计思路：appid+ruleid组成唯一的key 保存到的redis中
        String key = appId + "_" + message.getRuleId();
        boolean tag = jedis.exists(key);
        if (tag) {
            return;
        }
        //保存key到redis
        //设置redis的key的过期时间 10分钟
        jedis.setex(key, 60 * 10, "");
        MonitorHandle.notifly(appId, message);
        Record record = new Record();
        try {
            BeanUtils.copyProperties(message, record);
            collector.emit(new Values(record));
        } catch (BeansException e) {
            throw new FailedException("告警模块失败");
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("record"));
    }
}
