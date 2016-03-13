
package foorumi;

import java.sql.ResultSet;
import java.sql.SQLException;


public class KeskusteluCollector implements Collector<Keskustelu> {

    @Override
    public Keskustelu collect(ResultSet rs) throws SQLException {
    
        
        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        
        
        return new Keskustelu(id, otsikko);
        
    }
    
}
