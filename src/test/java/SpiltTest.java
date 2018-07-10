import org.junit.Test;

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
}
