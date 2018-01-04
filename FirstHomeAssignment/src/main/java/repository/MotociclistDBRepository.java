package repository;

import model.Motociclist;
import utils.JdbcUtils;
import model.Motociclist;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Xoxii on 12-Mar-17.
 */
public class MotociclistDBRepository implements IRepository<Integer, Motociclist> {
    private JdbcUtils dbUtils;

    public MotociclistDBRepository(Properties props){
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Integer size() {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select count(*) as [SIZE] from Motociclist")) {
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
    public void save(Motociclist entity) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into Motociclist values (?,?,?,?)")){
            preStmt.setInt(1,entity.getIdmotociclist());
            preStmt.setString(2,entity.getNumemotociclist());
            preStmt.setInt(3,entity.getCapacitate_motor());
            preStmt.setString(4,entity.getEchipa());
            int result = preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Motociclist where idmotociclist=?")){
            preStmt.setInt(1, integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Motociclist entity) {
        //To do
    }

    @Override
    public Motociclist findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Motociclist where idmotociclist=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("idmotociclist");
                    String desc = result.getString("numemotociclist");
                    int elems = result.getInt("capacitate_motor");
                    String order = result.getString("echipa");
                    Motociclist task = new Motociclist(id, desc, elems, order);
                    return task;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public ArrayList<Motociclist> findAll() {
        Connection con=dbUtils.getConnection();
        ArrayList<Motociclist> tasks=new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Motociclist")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("idmotociclist");
                    String desc = result.getString("numemotociclist");
                    int elems = result.getInt("capacitate_motor");
                    String echipa = result.getString("echipa");
                    Motociclist moto = new Motociclist(id, desc, elems, echipa);
                    tasks.add(moto);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return tasks;
    }



}
