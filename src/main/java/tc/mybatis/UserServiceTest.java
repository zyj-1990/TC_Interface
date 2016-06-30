package tc.mybatis;

/**
 * Created by zhaoyanji on 2016/6/30.
 */

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tc.mybatis.domain.User;
import tc.mybatis.services.UserService;
import org.testng.annotations.Test;

public class UserServiceTest {

    @Test
    public void userServiceTest() {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userService");
        User user = new User();
        user.setId("003");
        user.setName("樱木花道");

        Map map = new HashMap();
        map.put("id", "001");
        map.put("name", "方舟子");
        try {
            System.out.println(userService.countAll());
            //userService.update_insert(map, user);
            //	 userService.insertUser(user);//
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
