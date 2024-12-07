package org.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    //Directory automatica del db
    //private static final String currentDir = System.getProperty("user.dir");
    //private static final String URL = "jdbc:sqlite:" + currentDir + "/" + "geostore.db";
    //Directory fissa del db
    private static final String URL = "jdbc:sqlite:C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/DB/geostore.db";

    //Metodo per ottenere la connessione al db
    public static Connection sqlConnect(){
        Connection connection = null;

        try{
            //Connessione al db stabilita
            Class.forName("org.sqlite.JDBC"); //driver sqlite
            connection = DriverManager.getConnection(URL); //creazione della connessione
        }
        catch(SQLException e){ //errore connessione
            Utility.msgInf("GEOSTORE", "Errore alla connessione al db: " + e.getMessage());
        } catch (ClassNotFoundException e) { //errore driver
            Utility.msgInf("GEOSTORE", "Driver non caricato al db: " + e.getMessage());
        }

        return connection;
    }
}
