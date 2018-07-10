package com.bigdata.utils;

import com.bigdata.dao.LogMonitorDao;
import com.bigdata.domian.App;
import com.bigdata.domian.Message;
import com.bigdata.domian.Rule;
import com.bigdata.domian.User;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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

    static {
        load();
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
    /**
     * 加载数据模型，主要是用户列表、应用管理表、组合规则模型、组合用户模型。
     */
    public static synchronized void load() {
        if (userList == null) {
            userList = loadUserList();
        }
        if (appList == null) {
            appList = loadAppList();
        }
        if (ruleMap == null) {
            ruleMap = loadRuleMap();
        }
        if (userMap == null) {
            userMap = loadUserMap();
        }
    }

    /**
     * 访问数据库获取所有有效的app列表
     *
     * @return
     */
    private static List<App> loadAppList() {
        return new LogMonitorDao().getAppList();
    }

    /**
     * 访问数据库获取所有有效用户的列表
     *
     * @return
     */
    private static List<User> loadUserList() {
        return new LogMonitorDao().getUserList();
    }

    /**
     * 封装应用与用户对应的map
     *
     * @return
     */
    private static Map<String, List<User>> loadUserMap() {
        //以应用的appId为key，以应用的所有负责人的userList对象为value。
        //HashMap<String, List<User>>
        HashMap<String, List<User>> map = new HashMap<String, List<User>>();
        for (App app : appList) {
            String userIds = app.getUserId();
            List<User> userListInApp = map.get(app.getId());
            //从map中取出的list的没有地址的，为null 所以不能增加元素
            //遇到这种情况的时候需要给对应的appId的list分配地址空间
            if (userListInApp == null) {
                userListInApp = new ArrayList<User>();
                map.put(app.getId() + "", userListInApp);
            }
            String[] userIdArr = userIds.split(",");
            for (String userId : userIdArr) {
                userListInApp.add(queryUserById(userId));
            }
            map.put(app.getId() + "", userListInApp);
        }
        return map;
    }

    private static User queryUserById(String userId) {
        for (User user : userList) {
            if (user.getId() == Integer.parseInt(userId)) {
                return user;
            }
        }
        return null;
    }

    /**
     * 封装应用与规则的map
     *
     * @return
     */
    private static Map<String,List<Rule>> loadRuleMap() {
        Map<String, List<Rule>> map = new HashMap<String, List<Rule>>();
        LogMonitorDao logMonitorDao = new LogMonitorDao();
        List<Rule> ruleList = logMonitorDao.getRuleList();
        //将代表rule的list转化成一个map，转化的逻辑是，
        // 从rule.getAppId作为map的key，然后将rule对象作为value传入map
        //Map<appId,ruleList>  一个appid的规则信息，保存在一个list中。
        for (Rule rule : ruleList) {
            List<Rule> ruleListByAppId = map.get(rule.getAppId() + "");
            if (ruleListByAppId == null) {
                ruleListByAppId = new ArrayList<Rule>();
                map.put(rule.getAppId() + "", ruleListByAppId);
            }
            ruleListByAppId.add(rule);
            map.put(rule.getAppId() + "", ruleList);
        }
        return map;
    }

}
