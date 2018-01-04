package repository;

import model.Motociclist;
import model.User;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Xoxii on 28-Mar-17.
 */
public class UserDBRepository implements IRepository<String, User> {
    private JdbcUtils dbUtils;

    public UserDBRepository(Properties dbUtils) { this.dbUtils = new JdbcUtils(dbUtils); }

    @Override
    public Integer size() {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select count(*) as [SIZE] from User")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(User entity) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into User values (?,?)")){
            preStmt.setString(1,entity.getId());
            preStmt.setString(2,entity.getPassword());
            int result = preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void delete(String integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from User where username=?")){
            preStmt.setString(1, integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(String integer, User entity) {
        //To do
    }

    @Override
    public User findOne(String integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from User where username=?")){
            preStmt.setString(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String id = result.getString("username");
                    String desc = result.getString("password");
                    User task = new User(id, desc);
                    return task;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public ArrayList<User> findAll() {
        Connection con = dbUtils.getConnection();
        ArrayList<User> tasks = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from User")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    String desc = result.getString("username");
                    String echipa = result.getString("password");
                    User moto = new User(desc, echipa);
                    tasks.add(moto);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return tasks;
    }

}
