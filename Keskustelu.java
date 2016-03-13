
package foorumi;
import java.util.*;

public class Keskustelu {
    
    private Integer id;
    private String otsikko;
    private Alue alue;
    private List<Viesti> viestit;

    public Keskustelu(Integer id, String otsikko) {
        this.id = id;
        this.otsikko = otsikko;
        this.viestit = new ArrayList<>();
    }

    public void setViesti(Viesti v) {
        this.viestit.add(v);
    }
    public List<Viesti> viestitListana() {
        return this.viestit;
    }
    public int viestienLkm(){
        return viestit.size();
    }
    
    public Alue getAlue() {
        return alue;
    }
    public void setAlue(Alue a) {
        this.alue = a;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtsikko() {
        return otsikko;
    }
    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }
    
    
    
    
    
    
    
    
}
