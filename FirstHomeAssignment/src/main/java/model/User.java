package model;

import repository.HasID;

/**
 * Created by Xoxii on 28-Mar-17.
 */
public class User implements HasID<String> {
    private String username;
    private String password;

    public User(String u, String c){ username=u; password=c; }

    public String getId(){ return username; }
    public void setId(String id){ username = id; }

    public String getPassword() { return password; }
    public void setPassword(String p){ password = p; }
}
