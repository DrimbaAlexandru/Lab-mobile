package model;

/**
 * Created by Xoxii on 10-Mar-17.
 */
public class Cursa {
    private Integer IDCursa;
    private Integer Capacitate_Generala;
    private Integer Nr_Participanti;

    public Cursa(Integer i, Integer c, Integer n){ IDCursa =i; Capacitate_Generala = c; Nr_Participanti = n; }

    public void setIDCursa(Integer id) { IDCursa = id;}

    public Integer getIDCursa() { return IDCursa; }

    public void setCapacitate_Generala(Integer cap) { Capacitate_Generala = cap; }

    public Integer getCapacitate_Generala() { return Capacitate_Generala; }

    public void setNr_Participanti(Integer nr) { Nr_Participanti = nr; }

    public Integer getNr_Participanti() { return Nr_Participanti; }
}
