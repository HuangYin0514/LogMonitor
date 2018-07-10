package com.bigdata.utils;

import com.bigdata.domian.App;
import com.bigdata.domian.Message;
import com.bigdata.domian.Rule;
import com.bigdata.domian.User;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA by User1071324110@qq.com
 *
 * @author 10713
 * @date 2018/7/10 10:24
 */
public class MonitorHandle implements Serializable {

    private static final long serialVersionUID = -8789857575784112738L;
    private static Logger logger = Logger.getLogger(MonitorHandle.class);
    private static Map<String, List<Rule>> ruleMap;
    private static Map<String, List<User>> userMap;
    private static List<App> appList;
    private static List<User> userList;
    private static boolean reloaded = false;

    static{

    }

    public static Message parser(String line) {
        line.split("||");
        return null;
    }
}
