package org.models;

import org.services.DBConnection;
import org.utility.Utility;
import org.utility.codiciCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CodiceRepository implements codiciCRUD {

    private HashMap<Integer, Codice> codici = new HashMap<>();

    @Override
    public int insertCodiceWithDB(Integer id, Codice c) {
        String sql = "INSERT INTO `admin_codes`(`codice`, `descrizione`) VALUES (?,?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, c.getCodice());
            preparedStatement.setString(2, c.getDescrizione());
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertCodiceWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public HashMap<Integer, Codice> getCodiciWithDB() {
        String sql = "SELECT * FROM admin_codes ac";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        codici = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Codice c;

            while(rs.next()){
                c = new Codice();
                c.setId(rs.getInt("id"));
                c.setCodice(rs.getString("codice"));
                c.setDescrizione(rs.getString("descrizione"));

                codici.put(c.getId(), c);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getCodiciWithDB: " + e.getMessage());
        }

        return codici;
    }

    @Override
    public Codice getCodiceWithDB(Integer id) {
        String sql = "SELECT * FROM admin_codes ac WHERE ac.ID = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Codice c = null;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                c = new Codice();
                c.setId(rs.getInt("id"));
                c.setCodice(rs.getString("codice"));
                c.setDescrizione(rs.getString("descrizione"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getCodiceWithDB: " + e.getMessage());
        }
        return c;
    }

    public HashMap<Integer, Codice> getCodiceByKeyword(String keyword) {
        String sql = "SELECT * FROM admin_codes ac WHERE ac.codice LIKE ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        codici = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            keyword = "%" + keyword + "%";
            preparedStatement.setString(1, keyword);
            rs = preparedStatement.executeQuery();
            Codice c;

            while(rs.next()){
                c = new Codice();
                c.setId(rs.getInt("id"));
                c.setCodice(rs.getString("codice"));
                c.setDescrizione(rs.getString("descrizione"));

                codici.put(c.getId(), c);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getCodiceByKeyword: " + e.getMessage());
        }

        return codici;
    }

    @Override
    public int updateCodiceWithDB(Integer id, Codice newC) {
        String sql = "UPDATE `admin_codes` SET `codice` = ?, `descrizione` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, newC.getCodice());
            preparedStatement.setString(2, newC.getDescrizione());
            preparedStatement.setInt(3, id);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateCodiceWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public int deleteCodiceWithDB(Integer id) {
        String sql = "DELETE FROM `admin_codes` WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setInt(1, id);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel deleteCodiceWithDB: " + e.getMessage());
        }

        return num;
    }

    public int checkDuplicatesCodice(Codice c) {
        String sql = "select count(*) as duplicates from admin_codes ac where codice = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, c.getCodice());

            rs = preparedStatement.executeQuery();

            while(rs.next()){
                num = rs.getInt("duplicates");
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel checkDuplicatesCodice: " + e.getMessage());
        }

        return num;
    }

}
