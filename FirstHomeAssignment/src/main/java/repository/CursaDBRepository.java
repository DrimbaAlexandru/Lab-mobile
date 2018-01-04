package repository;

import model.Cursa;

/**
 * Created by Xoxii on 10-Mar-17.
 */

import utils.JdbcUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Xoxii on 12-Mar-17.
 */
public class CursaDBRepository implements IRepository<Integer, Cursa> {
    private JdbcUtils dbUtils;

    public CursaDBRepository(Properties props){
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Integer size() {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select count(*) as [SIZE] from Cursa")) {
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
    public void save(Cursa entity) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into Cursa values (?,?,?)")){
            preStmt.setInt(1,entity.getIDCursa());
            preStmt.setInt(2,entity.getCapacitate_Generala());
            preStmt.setInt(3,entity.getNr_Participanti());
            int result = preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from Cursa where idcursa=?")){
            preStmt.setInt(1, integer);
            int result = preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Cursa entity) {
        //To do
    }

    @Override
    public Cursa findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Cursa where idcursa=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("idcursa");
                    int elems = result.getInt("capacitate_generala");
                    int order = result.getInt("nr_participanti");
                    Cursa task = new Cursa(id, elems, order);
                    return task;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public ArrayList<Cursa> findAll() {
        Connection con = dbUtils.getConnection();
        ArrayList<Cursa> tasks=new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Cursa")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("idcursa");
                    int elems = result.getInt("capacitate_generala");
                    int order = result.getInt("nr_participanti");
                    Cursa conc = new Cursa(id, elems, order);
                    System.out.println(conc);
                    tasks.add(conc);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return tasks;
    }



}
