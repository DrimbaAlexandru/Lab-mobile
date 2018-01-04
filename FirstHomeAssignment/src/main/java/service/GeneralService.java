package service;

import model.Cursa;
import model.Motociclist;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Xoxii on 18-Mar-17.
 */
public class GeneralService extends Observable
{
    private CursaService cs;
    private MotociclistService ms;

    public GeneralService(CursaService cs, MotociclistService ms) { this.cs = cs; this.ms = ms; }


    public void schimbare()
    {
        setChanged();
        notifyObservers();
    }

    public void saveCursa(Cursa c) { cs.save(c); schimbare(); }
    public void saveMotociclist(Motociclist m) { ms.save(m); schimbare(); }

    public void deleteCursa(Integer id) { cs.delete(id); schimbare(); }
    public void deleteMotociclist(Integer id){ ms.delete(id); schimbare(); }

    public void updateCursa(Integer id, Cursa c) { cs.update(id, c); schimbare(); }
    public void updateMotociclist(Integer id, Motociclist m) { ms.update(id, m); schimbare(); }

    public Integer sizeCursa() { return cs.size(); }
    public Integer sizeMotociclist() { return ms.size(); }

    public Cursa findOneCursa(Integer id) { return cs.findOne(id); }
    public Motociclist findOneMotociclist(Integer id) { return ms.findOne(id); }

    public ArrayList<Cursa> findAllCursa() { return cs.findAll(); }
    public ArrayList<Motociclist> findAllMotociclist() { return ms.findAll(); }
}
