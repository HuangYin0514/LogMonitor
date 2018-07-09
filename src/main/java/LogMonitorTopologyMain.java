import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import com.bigdata.bolt.FilterBolt;
import com.bigdata.spout.RandomSpout;
import com.bigdata.spout.StringScheme;
import org.apache.log4j.Logger;
import storm.kafka.ZkHosts;

/**
 * Created with IDEA by User1071324110@qq.com
 *
 * 日志监控系统驱动类
 * @author 10713
 * @date 2018/7/9 23:09
 */
public class LogMonitorTopologyMain {
    private static Logger logger = Logger.getLogger(LogMonitorTopologyMain.class);

    public static void main(String[] args) {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        ZkHosts zkHosts = new ZkHosts("mini2:2181,mini3:2181");
        topologyBuilder.setSpout("kafka-spout", new RandomSpout(new StringScheme()), 10);
        topologyBuilder.setBolt("filter-bolt", new FilterBolt(), 10).shuffleGrouping("kafka-spout");

        Config config = new Config();
        config.setDebug(true);
        //设置最多并发度，使之前在上述设置的并发度无用
        config.setMaxTaskParallelism(3);
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("word-count", config, topologyBuilder.createTopology());
        Utils.sleep(10000000);
        localCluster.shutdown();
    }
}
