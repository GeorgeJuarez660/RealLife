package org.models;

import org.services.DBConnection;
import org.utility.Utility;
import org.utility.disponibilitaCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DisponibilitaRepository implements disponibilitaCRUD {

    private HashMap<Integer, Disponibilita> disponibilita = new HashMap<>();

    @Override
    public void insertDisponibilitaWithDB(Integer id, Disponibilita d) {

    }

    @Override
    public HashMap<Integer, Disponibilita> getDisponibilitaWithDB() {
        String sql = "SELECT * FROM Disponibilita d";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        disponibilita = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Disponibilita d;

            while(rs.next()){
                d = new Disponibilita();
                d.setId(rs.getInt("id"));
                d.setCode(rs.getString("code"));

                disponibilita.put(d.getId(), d);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getDisponibilitaWithDB: " + e.getMessage());
        }

        return disponibilita;
    }

    @Override
    public Disponibilita getDisponibilitaWithDB(Integer id) {
        String sql = "SELECT * FROM Disponibilita d WHERE d.ID = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Disponibilita d = null;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                d = new Disponibilita();
                d.setId(rs.getInt("id"));
                d.setCode(rs.getString("code"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getMateriaWithDB: " + e.getMessage());
        }
        return d;
    }

    @Override
    public void updateDisponibilitaWithDB(Integer id, Disponibilita newD) {

    }

    @Override
    public void deleteDisponibilitaWithDB(Integer id) {

    }

    public String changeIntToStringDisponibilita(Integer id) {
        String sql = "SELECT d.code FROM Disponibilita d WHERE d.ID = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String nome = "";

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                nome = rs.getString("code");
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel changeIntToStringDisponibilita: " + e.getMessage());
        }
        return nome;
    }

    //metodi override per operazioni CRUD con database



}
