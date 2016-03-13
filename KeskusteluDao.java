package foorumi;

import java.sql.*;
import java.util.*;

public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database database;
    private Dao<Alue, Integer> alueDao;
//    private Dao<Viesti, String> viestiDao;

    public KeskusteluDao(Database database, Dao<Alue, Integer> alueDao) {
        this.database = database;
        this.alueDao = alueDao;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");

        Keskustelu keskustelu = new Keskustelu(id, otsikko);

        Integer alue = rs.getInt("alue_id");

        rs.close();
        stmt.close();
        connection.close();

        keskustelu.setAlue(this.alueDao.findOne(alue));

        return keskustelu;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu");
        ResultSet rs = stmt.executeQuery();

        Map<String, List<Keskustelu>> keskusteluidenAlueet = new HashMap<>();

        List<Keskustelu> keskustelut = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");

            Keskustelu k = new Keskustelu(id, otsikko);
            keskustelut.add(k);

            String alue_id = rs.getString("alue_id");

            if (!keskusteluidenAlueet.containsKey(alue_id)) {
                keskusteluidenAlueet.put(alue_id, new ArrayList<>());
            }
            keskusteluidenAlueet.get(alue_id).add(k);
        }

        rs.close();
        stmt.close();
        connection.close();

        for (Alue alue : this.alueDao.findAll()) {
            if (!keskusteluidenAlueet.containsKey(alue.getId())) {
                continue;
            }
            for (Keskustelu keskustelu : keskusteluidenAlueet.get(alue.getId())) {
                keskustelu.setAlue(alue);
            }
        }

        return keskustelut;
//        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu");
//        ResultSet rs = stmt.executeQuery();
//        
//        Map<String, List<Keskustelu>> keskustelujenViestit = new HashMap<>();
//        
//        List<Keskustelu> keskustelut = new ArrayList<>();
//        
//        while (rs.next()) {
//            
//            //haetaan yksittäisen keskustelun tiedot ja
//            //lisätään se keskustelut-listaan
//            Integer id = rs.getInt("id");
//            String otsikko = rs.getString("otsikko");
//            Keskustelu keskustelu = new Keskustelu(id, otsikko);
//            keskustelut.add(keskustelu);
//            
//         
//            String viesti = rs.getString("viesti");
//            
//        
//            if (!keskustelujenViestit.containsKey(viesti)) {
//                keskustelujenViestit.put(viesti, new ArrayList<>());
//            }
//            
//            keskustelujenViestit.get(viesti).add(keskustelu);
//            
//        }
//        
//        rs.close();
//        stmt.close();
//        connection.close();
//      
//        for (Viesti v : this.viestiDao.findAllIn(keskustelujenViestit.keySet())) {
//            
//            for (Keskustelu k : keskustelujenViestit.get(v.getId())) {
//                k.setViesti(v);
//            }
//        }
//        
//        return keskustelut;
//        
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Keskustelu> findAllIn(Collection<Integer> keys) throws SQLException {
        if (keys.isEmpty()) {
            return new ArrayList<>();
        }

        StringBuilder muuttujat = new StringBuilder("?");
        for (int i = 1; i < keys.size(); i++) {
            muuttujat.append(", ?");
        }

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id IN (" + muuttujat + ")");
        int laskuri = 1;
        for (Integer key : keys) {
            stmt.setObject(laskuri, key);
            laskuri++;
        }

        ResultSet rs = stmt.executeQuery();

        List<Keskustelu> keskustelut = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");

            keskustelut.add(new Keskustelu(id, otsikko));
        }

        return keskustelut;
    }

    public int viestienLkm(Integer key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * "
                + "FROM Viesti, Keskustelu WHERE Viesti.keskustelu_id = Keskustelu.id "
                + "AND Keskustelu.id = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        int i = 0;
        while (rs.next()) {
            i++;
        }
//        System.out.println("lkm " + i);
        return i;
    }

    public void addKeskustelu(String otsikko) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Keskustelu(otsikko, alue_id) "
                + "VALUES(?, 1)");
        stmt.setObject(1, otsikko);
        
        stmt.execute();
    }

}
