package com.bigdata.utils;

import com.bigdata.domian.App;
import com.bigdata.domian.Message;
import com.bigdata.domian.Rule;
import com.bigdata.domian.User;
import org.apache.commons.lang.StringUtils;
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
        String[] messageArr = line.split("\\|\\|");
        if (messageArr.length != 2) {
            return null;
        }

        if (StringUtils.isBlank(messageArr[0]) || StringUtils.isBlank(messageArr[1])) {
            return null;
        }
        //检验当前日志所属的appid是否是经过授权的。
        if (appIdisValid(messageArr[0].trim().substring(4))) {
            Message message = new Message();
            message.setAppId(messageArr[0].trim().substring(4));
            message.setLine(messageArr[1].substring(4));
            return message;
        }
        return null;
    }

    /**
     * 验证appid是否经过授权
     */
    private static boolean appIdisValid(String appId) {
        try {
            for (App app : appList) {
                if (app.getId() == Integer.parseInt(appId)) {
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
}
