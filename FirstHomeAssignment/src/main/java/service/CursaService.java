package service;

import model.Cursa;
import repository.CursaDBRepository;

import java.util.ArrayList;

/**
 * Created by Xoxii on 18-Mar-17.
 */
public class CursaService implements IService<Integer, Cursa>
{
    private CursaDBRepository repocursa;

    public CursaService(CursaDBRepository c) { repocursa = c; }

    public void save(Cursa t) { repocursa.save(t); }

    public void delete(Integer id) { repocursa.delete(id); }

    public void update(Integer id, Cursa t) { repocursa.update(id, t); }

    public Integer size() { return repocursa.size(); }

    public Cursa findOne(Integer id) { return repocursa.findOne(id); }

    public ArrayList<Cursa> findAll() { return repocursa.findAll(); }
}
