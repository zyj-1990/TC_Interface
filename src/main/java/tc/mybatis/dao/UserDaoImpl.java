package tc.mybatis.dao;

/**
 * Created by zhaoyanji on 2016/6/30.
 */
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tc.mybatis.dao.UserDao;
import tc.mybatis.domain.User;
import tc.mybatis.mapper.UserMapper;

@Service("UserDao")
public class UserDaoImpl implements UserDao{
    @Autowired
    private UserMapper userMapper;

    public int countAll() {
        return  this.userMapper.countAll();
    }

    public void insertUser(User user) {
        this.userMapper.insertUser(user);


    }

    public List<User> getAllUser() {
        return this.userMapper.getAllUser();
    }

    public User getById(String id) {
        return this.userMapper.getById(id);
    }

    public void deleteUser(String id) {
        this.userMapper.deleteUser(id);

    }

    public void updateUser(Map<String, Object> map) {
        this.userMapper.updateUser(map);
    }

}
