import com.bigdata.domian.App;
import com.bigdata.domian.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA by User1071324110@qq.com
 *
 * @author 10713
 * @date 2018/7/10 10:34
 */

public class SpiltTest {

    @Test
    public void spiltTest() {
        String a = "falksjfd\\||aa";
        String b = "\\|\\|";
        String[] split = a.split(b);
        System.out.println(split[0]);
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void subTest() {
        String a = "error: Caused by: java.lang.NoClassDefFoundError: com/starit/gejie/dao/SysNameDao";
        System.out.println(a.substring(4));
    }

    @Test
    public void listTest() {

        List<App> appList = new ArrayList<App>();
        App app1 = new App();
        app1.setId(1);
        app1.setUserId("1,3,4");
        appList.add(app1);
        App app2 = new App();
        app2.setId(2);
        app2.setUserId("1,3,4");
        appList.add(app2);



        HashMap<String, List<User>> map = new HashMap<String, List<User>>();
        for (App app : appList) {
            String userIds = app.getUserId();
            List<User> userListInApp = map.get(app.getId());
           /* if (userListInApp == null) {
                userListInApp = new ArrayList<User>();
                map.put(app.getId() + "", userListInApp);
            }*/
            String[] userIdArr = userIds.split(",");
            for (String userId : userIdArr) {
                User user1 = new User();
                user1.setId(Integer.parseInt(userId));
                userListInApp.add(user1);
            }
            map.put(app.getId() + "", userListInApp);
        }
    }

    @Test
    public void testListMy() {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        map.put("1", null);

        List<String> strings = map.get("1");
//        strings = new ArrayList<String>();
//        map.put("1", strings);

        System.out.println(map);

    }
}
