package foorumi;

import java.sql.*;
import java.util.*;

public class AlueDao implements Dao<Alue, Integer> {

    private Database database;

    public AlueDao(Database database) {
        this.database = database;
    }

    @Override
    public Alue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE id = ?");

        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();

        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Alue alue = new Alue(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return alue;
    }

    @Override
    public List<Alue> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue");
        ResultSet rs = stmt.executeQuery();

//        Map<String, List<Alue>> alueidenKeskustelut = new HashMap<>();
        List<Alue> alueet = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            Alue a = new Alue(id, nimi);
            alueet.add(a);

//            String keskustelu = rs.getString("keskustelu");
//            
//            if (!alueidenKeskustelut.containsKey(keskustelu)) {
//                alueidenKeskustelut.put(keskustelu, new ArrayList<>());
//            }
//            alueidenKeskustelut.get(keskustelu).add(a);
        }

        rs.close();
        stmt.close();
        connection.close();

        return alueet;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }

    @Override
    public List<Alue> findAllIn(Collection<Integer> keys) throws SQLException {

        if (keys.isEmpty()) {
            return new ArrayList<>();
        }

        StringBuilder muuttujat = new StringBuilder("?");
        for (int i = 1; i < keys.size(); i++) {
            muuttujat.append(", ?");
        }

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Alue WHERE id IN (" + muuttujat + ")");
        int laskuri = 1;
        for (Integer key : keys) {
            stmt.setObject(laskuri, key);
            laskuri++;
        }

        ResultSet rs = stmt.executeQuery();
        List<Alue> alueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            alueet.add(new Alue(id, nimi));
        }

        return alueet;
    }

    public int viestienLkm(Integer key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * "
                + "FROM Viesti, Keskustelu, Alue WHERE Viesti.keskustelu_id = Keskustelu.id "
                + "AND Keskustelu.alue_id = Alue.id AND Alue.id = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        int i = 0;
        while (rs.next()) {
            i++;
        }
//        System.out.println("lkm " + i);
        return i;
    }

    public void addAlue(String nimi) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Alue(nimi) "
                + "VALUES(?)");
        stmt.setObject(1, nimi);
        
        stmt.execute();
    }
}
