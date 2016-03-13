
package foorumi;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Alue {
    
    private Integer id;
    private String nimi;
    private List<Keskustelu> keskustelut;

    public Alue(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
        this.keskustelut = new ArrayList<>();
    }
    
    public void lisaaKeskustelu(Keskustelu k) {
        this.keskustelut.add(k);
    }
    public List<Keskustelu> keskustelutListana() {
        return this.keskustelut;
    }
    public int viestienLkmAlueella() {
        int i = 0;
        for (Keskustelu k : this.keskustelut) {
            i = i + k.viestienLkm();
        }
        return i;
    }
    public String viimeisinViesti() {
        String a = "";
        
     
        return a;
    }
    

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    
    
}
