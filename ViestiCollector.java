package foorumi;

import java.sql.*;
import java.text.*;
import java.util.Date;

public class ViestiCollector implements Collector<Viesti> {

    @Override
    public Viesti collect(ResultSet rs) throws SQLException {

//        try {

            Integer id = rs.getInt("id");
            String sisalto = rs.getString("sisalto");
            String nimimerkki = rs.getString("kayttaja");

//            String s = rs.getString("lahetetty");
//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date lahetetty = format.parse(s);

            Timestamp t = rs.getTimestamp("lahetetty");
            Date lahetetty = new Date(t.getTime());
           
            Viesti v = new Viesti(id, sisalto, nimimerkki);
            
            v.setLahetetty(lahetetty);
            
            return v;

//        } catch (ParseException e) {
//            System.out.println("Parse ei onnistunut.");
//            return null;
//        }

        //VANHA:
        //muutetaan Timestamp-muodosta Date-muotoon
        //EI TOIMI!!!
        //Error parsing timestamp (seuraava rivi aiheuttaa error-viestin)
        //Timestamp t = rs.getTimestamp("lahetetty");
        //Date lahetetty = new Date(t.getTime());
        // * 60*60*2*1000
        //testi:
//        try {
//            String s = rs.getString("lahetetty");
//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date lahetetty = format.parse(s);
//
//            Integer kayttaja_id = rs.getInt("kayttaja_id");
//            Integer keskustelu_id = rs.getInt("keskustelu_id");
//
//            return new Viesti(id, sisalto, lahetetty, kayttaja_id, keskustelu_id);
//
//        } catch (ParseException e) {
//            System.out.println("parse ei onnistu");
//            return null;
//        }
        //toimii, myös databasella, jossa timestamp
        //timestamp voidaan siis lukea myös string-muodossa rs-oliolta
        //nyt ongelmaksi muodostuu ajan muuttaminen oikeaan muotoon (+2h)
    }

}
