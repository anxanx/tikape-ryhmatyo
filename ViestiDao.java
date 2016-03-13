package foorumi;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class ViestiDao implements Dao<Viesti, String> {

    private Database database;
    private Dao<Keskustelu, Integer> keskusteluDao;
//    private Dao<Kayttaja, Integer> kayttajaDao;

    public ViestiDao(Database database) {
        this.database = database;
//       this.keskusteluDao = keskusteluDao;
//        this.kayttajaDao = kayttajaDao;

    }

    public ViestiDao(Database database, Dao<Keskustelu, Integer> keskusteluDao) {
        this.database = database;
        this.keskusteluDao = keskusteluDao;
//        this.kayttajaDao = kayttajaDao;

    }

    @Override
    public Viesti findOne(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Integer id = rs.getInt("id");
        String sisalto = rs.getString("sisalto");
        String nimimerkki = rs.getString("kayttaja");
        Timestamp lahetetty = rs.getTimestamp("lahetetty");

        Viesti viesti = new Viesti(id, sisalto, nimimerkki);

        Integer keskustelu_id = rs.getInt("keskustelu_id");

//        try {
//            String id = rs.getString("id");
//            String sisalto = rs.getString("sisalto");
//
//            String s = rs.getString("lahetetty");
//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date lahetetty = format.parse(s);
//
//            Viesti viesti = new Viesti(id, sisalto);
//            Integer keskustelu_id = rs.getInt("keskustelu_id");
//            Integer kayttaja_id = rs.getInt("kayttaja_id");
        rs.close();
        stmt.close();
        connection.close();

        viesti.setKeskustelu(this.keskusteluDao.findOne(keskustelu_id));

        return viesti;
//            viesti.setKayttaja(kayttajaDao.findOne(kayttaja_id));

//    }
//    catch (Exception e
//
//    
//        ) {
//            System.out.println("Parse ei onnistunut");
//        return null;
//    }
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");
        ResultSet rs = stmt.executeQuery();

        Map<String, List<Viesti>> viestienKeskustelut = new HashMap<>();
        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String sisalto = rs.getString("sisalto");
            String nimimerkki = rs.getString("kayttaja");
//            Timestamp lahetetty = rs.getTimestamp("lahetetty");
//              String s = rs.getString("lahetetty");
//                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date lahetetty = format.parse(s);

            Viesti v = new Viesti(id, sisalto, nimimerkki);
            viestit.add(v);
            String keskustelu = rs.getString("keskustelu_id");

            if (!viestienKeskustelut.containsKey(keskustelu)) {
                viestienKeskustelut.put(keskustelu, new ArrayList<>());
            }
            viestienKeskustelut.get(keskustelu).add(v);

//            } catch (Exception e) {
//                System.out.println("Parse ei onnistunut!");
//                return null;
//            }
        }

        rs.close();
        stmt.close();
        connection.close();

        for (Keskustelu keskustelu : this.keskusteluDao.findAll()) {
            if (!viestienKeskustelut.containsKey(keskustelu)) {
                continue;
            }
            for (Viesti viesti : viestienKeskustelut.get(keskustelu.getId())) {
                viesti.setKeskustelu(keskustelu);
            }
        }
        return viestit;

    }

    @Override
    public void delete(String key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viesti> findAllIn(Collection<String> keys) throws SQLException {
        if (keys.isEmpty()) {
            return new ArrayList<>();
        }

        StringBuilder muuttujat = new StringBuilder("?");
        for (int i = 1; i < keys.size(); i++) {
            muuttujat.append(", ?");
        }

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id IN (" + muuttujat + ")");
        int laskuri = 1;
        for (String key : keys) {
            stmt.setObject(laskuri, key);
            laskuri++;
        }

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {

            try {
                Integer id = rs.getInt("id");
                String sisalto = rs.getString("sisalto");
                String nimimerkki = rs.getString("kayttaja");

                
                String s = rs.getString("lahetetty");
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date lahetetty = format.parse(s);
                

                Viesti v = new Viesti(id, sisalto, nimimerkki);
                viestit.add(v);

            } catch (Exception e) {
                System.out.println("Parse ei onnistunut!");
                return null;
            }

        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
    
     public void addViesti(String sisalto, String nimimerkki) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti(sisalto, kayttaja, keskustelu_id) "
                + "VALUES(?,?,1)");
        stmt.setObject(1, sisalto);
        stmt.setObject(2, nimimerkki);
        stmt.execute();
    }

}
