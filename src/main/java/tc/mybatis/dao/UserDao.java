package tc.mybatis.dao;

/**
 * Created by zhaoyanji on 2016/6/30.
 */
import java.util.List;
import java.util.Map;

import tc.mybatis.domain.User;

public interface UserDao {
    public int countAll();
    public void insertUser(User user);
    public List<User> getAllUser();
    public User getById(String id);
    public void deleteUser(String id);
    public void updateUser(Map<String,Object> map);

}