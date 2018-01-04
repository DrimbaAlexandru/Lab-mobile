package service;

import model.User;
import repository.UserDBRepository;

import java.util.ArrayList;

/**
 * Created by Xoxii on 29-Mar-17.
 */
public class UserService implements IService<String, User> {
    UserDBRepository urep;

    public UserService(UserDBRepository urep){ this.urep = urep; }

    public void save(User user){ urep.save(user); }
    public void delete(String username) { urep.delete(username); }
    public Integer size() { return urep.size(); }
    public User findOne(String username) { return urep.findOne(username); }
    public ArrayList<User> findAll(){ return urep.findAll(); }
    public void update(String username, User user){}
}
