package boot;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.boot.Application;
import com.boot.controller.system.BaseController;
import com.boot.model.Menu;
import com.boot.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Calendar;
import java.util.List;

@SuppressWarnings("ALL")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class BaseTest extends BaseController {
    @Test
    public void test() throws Exception {
        String a = "2018-10-10";
        String b = "2018-10-10 10:10:10";
        String c = "2018-10-10 11:11";
        String a1 = "2018/10/10";
        String b2 = "2018/10/10 10:10:10";
        String c1 = "2018/10/10 11:11";
        DateTime parse = DateUtil.parse(a);
        Calendar calendar = DateUtil.calendar(parse);
        System.out.println(DateUtil.parse(a));
        System.out.println(DateUtil.parse(b));
        System.out.println(DateUtil.parse(c));
        System.out.println(DateUtil.parse(a1));
        System.out.println(DateUtil.parse(b2));
        System.out.println(DateUtil.parse(c1));
        String htmlNbsp = StrUtil.HTML_NBSP;
    }

    @Test
    public void test1() {
        Menu menu = sqlManager.single(Menu.class, 1);
    }


    @Test
    public void test2() {

        List<Role> all = sqlManager.all(Role.class);
    }


    @Test
    public void test3() {
        File desktopDir = FileSystemView.getFileSystemView()
                .getHomeDirectory();
        String desktopPath = desktopDir.getAbsolutePath();
        System.out.println(desktopPath);
    }

}
