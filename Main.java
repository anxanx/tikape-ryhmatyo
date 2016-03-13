package foorumi;

import java.util.*;
import java.sql.*;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) throws Exception {

        Database database = new Database("jdbc:sqlite:foorumi.db");
        AlueDao alueDao = new AlueDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database, alueDao);
        ViestiDao viestiDao = new ViestiDao(database, keskusteluDao);
        
//        Tulostaa kaikki viestit: 
        List<Viesti> viestit = viestiDao.findAll();
        int i = 0;
        for (Viesti v : viestit) {
            System.out.println("viestin id: " + v.getId()
                    + "; sisältää: " + v.getSisalto());
            i++;
        }
        System.out.println(i);

        System.out.println("viestien_lkm_keskustelussa " + keskusteluDao.viestienLkm(3));
        System.out.println("viestien_lkm_alueella:" + alueDao.viestienLkm(3));

        int r = 0;
        for (Viesti viesti : keskusteluDao.findOne(1).viestitListana()) {
            r++;
        }
        System.out.println(r);
        
        
//        alueet:
        get("/alueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        ArrayList<String> nimet = new ArrayList<>();

        get("/alueet", (req, res) -> {
            String alueet = "";
            for (String nimi : nimet) {
                alueet += nimi + "<br/>";
            }
            return "<form method=\"POST\" action=\"/alueet\">\n"
                    + "Nimi:<br/>\n"
                    + "<input type=\"text\" name=\"nimi\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Luo uusi alue\"/>\n"
                    + "</form>";
        });

        post("/alueet", (req, res) -> {
            String nimi = req.queryParams("nimi");
            alueDao.addAlue(nimi);
            nimet.add(nimi);

            return "Lisätty alue: " + nimi;
        });

        get("/alue/1", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelut", alueDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

//        viestit:
        get("/viestit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.findAll());

            return new ModelAndView(map, "index3");
        }, new ThymeleafTemplateEngine());

        ArrayList<Viesti> sisallot = new ArrayList<>();

        get("/viestit", (req, res) -> {
            String viesteja = "";
            for (Viesti sisalto : sisallot) {
                viesteja += sisalto + "<br/>";
            }
            return "<form method=\"POST\" action=\"/viestit\">\n"
                    + "Viesti:<br/>\n"
                    + "<input type=\"text\" name=\"sisalto\"/><br/>\n"
                    + "<input type=\"text\" name=\"nimimerkki\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Luo uusi viesti\"/>\n"
                    + "</form>";
        });

        post("/viestit", (req, res) -> {

            String sisalto = req.queryParams("sisalto");
            String nimimerkki = req.queryParams("nimimerkki");
            viestiDao.addViesti(sisalto, nimimerkki);


            return "Lisätty viesti: " + sisalto;
        });

//        keskustelut:
        get("/keskustelut", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("keskustelut", keskusteluDao.findAll());

            return new ModelAndView(map, "index2");
        }, new ThymeleafTemplateEngine());

        ArrayList<String> otsikot = new ArrayList<>();

        get("/alueet", (req, res) -> {
            String keskustelut = "";
            for (String otsikko : otsikot) {
                keskustelut += otsikko + "<br/>";
            }
            return "<form method=\"POST\" action=\"/keskustelut\">\n"
                    + "Otsikko:<br/>\n"
                    + "<input type=\"text\" name=\"nimi\"/><br/>\n"
                    + "<input type=\"submit\" value=\"Luo uusi keskustelu\"/>\n"
                    + "</form>";
        });

        post("/keskustelut", (req, res) -> {
            String otsikko = req.queryParams("nimi");
            keskusteluDao.addKeskustelu(otsikko);
            otsikot.add(otsikko);

            return "Lisätty keskustelu: " + otsikko;
        });


//                List<Viesti> viestit = viestiDao.findAll();
//                
//                for (Viesti v : viestit) {
//                    System.out.println(
//     //                       "Keskustelu: " + v.getKeskustelu().getOtsikko()
//   //                         + "; kayttaja: " + v.getKayttaja().getNimimerkki()
//                             "; viestin id: " + v.getId() 
//                            + "; sisÃƒÂ¤ltÃƒÂ¶: " + v.getSisalto());
//                }
        //SEURAAVA TOIMII!!!
        //        List<Keskustelu> keskustelut = keskusteluDao.findAll();
        //        for (Keskustelu k : keskustelut) {
        //            System.out.println("id: " + k.getId() + "; otsikko: " + k.getOtsikko() + "; alue: " + k.getAlue().getNimi());
        //        }
        //Keskustelu k = keskusteluDao.findOne(7);
        //System.out.println("id: " + k.getId() + "; otsikko: " + k.getOtsikko() + "; alue: " + k.getAlue().getNimi());
        //        Connection connection = DriverManager.getConnection("jdbc:sqlite:foorumi.db");
        //        Statement statement = connection.createStatement();
        //        ResultSet rs = statement.executeQuery("SELECT * FROM Alue");
        //    
        //        while (rs.next()) {
        //            
        //            Integer id = rs.getInt("id");
        //            String nimi = rs.getString("nimi");
        //            
        //            System.out.println("Alueen id: " + id + ", nimi: " + nimi);
        //            
        //        }
        //        
        //        rs.close();
        //        statement.close();
        //        connection.close();
    }

}
