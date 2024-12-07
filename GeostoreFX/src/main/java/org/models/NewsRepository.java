package org.models;

import org.utility.DBConnection;
import org.utility.Utility;
import org.utility.newsCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class NewsRepository implements newsCRUD {

    private HashMap<Integer, News> notizie = new HashMap<>();

    @Override
    public int insertNotizieWithDB(String testo, Integer idAdmin) {
        String sql = "INSERT INTO `news`(`testo`, `admin_id`) VALUES (?, ?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, testo);
            preparedStatement.setInt(2, idAdmin);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertNotizieWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public HashMap<Integer, News> getNotizieWithDB() {
        return null;
    }

    @Override
    public News getNotiziaWithDB(String keyword) {
        String sql = "SELECT * FROM News n WHERE n.TESTO LIKE ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        News news = null;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            keyword = "%" + keyword;
            preparedStatement.setString(1, keyword);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                news = new News();
                news.setId(rs.getInt("id"));
                news.setTesto(rs.getString("testo"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getNotiziaWithDB: " + e.getMessage());
        }
        return news;
    }

    @Override
    public int updateNotizieWithDB(Integer id, String newTesto, Integer newIdAdmin) {
        String sql = "UPDATE `news` SET `testo` = ?, `admin_id` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, newTesto);
            preparedStatement.setInt(2, newIdAdmin);
            preparedStatement.setInt(3, id);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateNotizieWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public int deleteNotizieWithDB(Integer id) {
        String sql = "DELETE FROM `news` WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel deleteNotizieWithDB: " + e.getMessage());
        }

        return num;
    }

}
