
package foorumi;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlueCollector implements Collector<Alue> {

    @Override
    public Alue collect(ResultSet rs) throws SQLException {
        
        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        
        return new Alue(id, nimi);
    }
    
}
