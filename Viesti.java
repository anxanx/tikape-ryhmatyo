package foorumi;

import java.sql.Timestamp;
import java.util.*;

public class Viesti {

    private Integer id;
    private String sisalto;
    private Date lahetetty;
    private Keskustelu keskustelu;
    private String nimimerkki;

    public Viesti(Integer id, String sisalto, String nimimerkki) {
        this.id = id;
        this.sisalto = sisalto;
        this.nimimerkki = nimimerkki;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public Keskustelu getKeskustelu() {
        return keskustelu;
    }

    public void setKeskustelu(Keskustelu keskustelu) {
        this.keskustelu = keskustelu;
    }

    public Date getLahetetty() {

        return lahetetty;
    }

    public void setLahetetty(Date lahetetty) {
        this.lahetetty = lahetetty;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

}
