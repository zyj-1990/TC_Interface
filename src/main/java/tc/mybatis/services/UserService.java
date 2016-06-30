package tc.mybatis.services;

/**
 * Created by zhaoyanji on 2016/6/30.
 */
import java.util.Map;

import tc.mybatis.domain.User;

public interface UserService {
    public int countAll();
    public void insertUser(User user);
    public void update_insert(Map map,User user);

}