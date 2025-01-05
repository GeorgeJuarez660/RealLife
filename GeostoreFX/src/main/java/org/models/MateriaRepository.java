package org.models;

import org.services.DBConnection;
import org.utility.Utility;
import org.utility.materieCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MateriaRepository implements materieCRUD {

    private HashMap<Integer, Materia> materie = new HashMap<>();

    //metodi override per operazioni CRUD con database

    @Override
    public int insertMateriaWithDB(Integer id, Materia m) {
        String sql = "INSERT INTO `materie`(`nome`) VALUES (?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, m.getNome());
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertMateriaWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public HashMap<Integer, Materia> getMaterieWithDB() {
        String sql = "SELECT * FROM Materie m";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        materie = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Materia mat;

            while(rs.next()){
                mat = new Materia();
                mat.setId(rs.getInt("id"));
                mat.setNome(rs.getString("nome"));

                materie.put(mat.getId(), mat);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getMaterieWithDB: " + e.getMessage());
        }

        return materie;
    }

    @Override
    public Materia getMateriaWithDB(Integer id) {
        String sql = "SELECT * FROM Materie m WHERE m.ID = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Materia mat = null;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                mat = new Materia();
                mat.setId(rs.getInt("id"));
                mat.setNome(rs.getString("nome"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getMateriaWithDB: " + e.getMessage());
        }
        return mat;
    }

    @Override
    public int updateMateriaWithDB(Integer id, Materia newM) {
        String sql = "UPDATE `materie` SET `nome` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, newM.getNome());
            preparedStatement.setInt(2, id);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateMateriaWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public int deleteMateriaWithDB(Integer id) {
        String sql = "DELETE FROM `materie` WHERE id = ? ";
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
            Utility.msgInf("GEOSTORE", "Errore nel deleteMateriaWithDB: " + e.getMessage());
        }

        return num;
    }

    public int checkDuplicatesMateria(Materia m) {
        String sql = "select count(*) as duplicates from materie m where nome = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, m.getNome());

            rs = preparedStatement.executeQuery();

            while(rs.next()){
                num = rs.getInt("duplicates");
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel checkDuplicatesMateria: " + e.getMessage());
        }

        return num;
    }

}
