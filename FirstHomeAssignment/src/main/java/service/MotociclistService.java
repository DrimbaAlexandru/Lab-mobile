package service;

import model.Motociclist;
import repository.MotociclistDBRepository;

import java.util.ArrayList;

/**
 * Created by Xoxii on 18-Mar-17.
 */
public class MotociclistService implements IService<Integer, Motociclist> {
    private MotociclistDBRepository repomoto;

    public MotociclistService(MotociclistDBRepository r){ repomoto = r; }

    public void save(Motociclist motociclist){repomoto.save(motociclist);}

    public void delete(Integer id){repomoto.delete(id);}

    public void update(Integer id, Motociclist motociclist){repomoto.update(id, motociclist);}

    public Integer size(){ return repomoto.size(); }

    public Motociclist findOne(Integer integer) {return repomoto.findOne(integer);}

    public ArrayList<Motociclist> findAll(){return repomoto.findAll();}
}
