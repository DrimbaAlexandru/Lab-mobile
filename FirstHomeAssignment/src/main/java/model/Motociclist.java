package model;

/**
 * Created by Xoxii on 10-Mar-17.
 */
public class Motociclist {
    private Integer idmotociclist;
    private String numemotociclist;
    private Integer capacitate_motor;
    private String echipa;

    public Motociclist(Integer id, String n, Integer c, String e)
    {
        this.idmotociclist = id;
        numemotociclist = n;
        capacitate_motor = c;
        echipa = e;
    }

    public void setIdmotociclist(Integer idmotociclist){this.idmotociclist = idmotociclist;}

    public Integer getIdmotociclist(){return this.idmotociclist;}

    public void setNumemotociclist(String numemotociclist){this.numemotociclist = numemotociclist;}

    public String getNumemotociclist(){return this.numemotociclist;}

    public void setCapacitate_motor(Integer capacitate_motor){this.capacitate_motor = capacitate_motor;}

    public Integer getCapacitate_motor(){return this.capacitate_motor;}

    public void setEchipa(String echipa){this.echipa = echipa;}

    public String getEchipa(){return this.echipa;}
}
