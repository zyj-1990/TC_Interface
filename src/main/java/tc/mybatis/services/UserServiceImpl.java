package tc.mybatis.services;

/**
 * Created by zhaoyanji on 2016/6/30.
 */
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tc.mybatis.dao.UserDao;
import tc.mybatis.domain.User;
@Service("userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    public int countAll() {
        return this.userDao.countAll();
    }

    public void insertUser(User user) {
        this.userDao.insertUser(user);
        throw new RuntimeException("Error");
    }


    public void update_insert(Map map,User user) {
        this.userDao.updateUser(map);
        this.userDao.insertUser(user);
        throw new RuntimeException("Error");

    }

}