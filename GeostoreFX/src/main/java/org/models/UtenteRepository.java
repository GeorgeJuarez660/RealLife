package org.models;

import org.utility.DBConnection;
import org.utility.Utility;
import org.utility.utentiCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UtenteRepository implements utentiCRUD {

    private HashMap<Integer, Utente> utenti = new HashMap<>();

    @Override
    public int insertUtenteWithDB(Integer id, Utente u) {
        String sql = "INSERT INTO `utenti`(`nome`, `cognome`, `sesso`, `data_nascita`, `email`, `password`, `telefono`, `indirizzo`, `portafoglio`, `codice_admin`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, u.getNome());
            preparedStatement.setString(2, u.getCognome());
            preparedStatement.setString(3, u.getSesso());
            preparedStatement.setDate(4, u.getDataNascita());
            preparedStatement.setString(7, u.getTelefono());
            preparedStatement.setString(8, u.getIndirizzo());

            if(u instanceof Amministratore){
                Amministratore a = (Amministratore) u;
                preparedStatement.setString(5, a.getEmail());
                preparedStatement.setString(6, a.getPassword());
                preparedStatement.setBigDecimal(9, a.getPortafoglio());
                preparedStatement.setString(10, a.getCodeAdmin());
            }
            else if(u instanceof Cliente){
                Cliente c = (Cliente) u;
                preparedStatement.setString(5, c.getEmail());
                preparedStatement.setString(6, c.getPassword());
                preparedStatement.setBigDecimal(9, c.getPortafoglio());
                preparedStatement.setString(10, null);
            }

            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertUtenteWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public HashMap<Integer, Utente> getUtentiWithDB() {
        String sql = "select * from utenti u ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        utenti = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Utente foundUt, ut;

            while(rs.next()){
                String codeAdmin = rs.getString("codice_admin");

                if(codeAdmin != null) {
                    foundUt = new Amministratore();
                }
                else {
                    foundUt = new Cliente();
                }

                if(foundUt instanceof Amministratore){
                    Amministratore foundAm = (Amministratore) foundUt;
                    foundAm.setId(rs.getInt("id"));
                    foundAm.setNome(rs.getString("nome"));
                    foundAm.setCognome(rs.getString("cognome"));
                    foundAm.setSesso(rs.getString("sesso"));
                    foundAm.setDataNascita(rs.getDate("data_nascita"));
                    foundAm.setEmail(rs.getString("email"));
                    foundAm.setPassword(rs.getString("password"));
                    foundAm.setIndirizzo(rs.getString("indirizzo"));
                    foundAm.setTelefono(rs.getString("telefono"));
                    foundAm.setCodeAdmin(rs.getString("codice_admin"));
                    foundAm.setPortafoglio(rs.getBigDecimal("portafoglio"));
                    ut = foundAm;
                }
                else{
                    Cliente foundCl = (Cliente) foundUt;
                    foundCl.setId(rs.getInt("id"));
                    foundCl.setNome(rs.getString("nome"));
                    foundCl.setCognome(rs.getString("cognome"));
                    foundCl.setSesso(rs.getString("sesso"));
                    foundCl.setDataNascita(rs.getDate("data_nascita"));
                    foundCl.setEmail(rs.getString("email"));
                    foundCl.setPassword(rs.getString("password"));
                    foundCl.setIndirizzo(rs.getString("indirizzo"));
                    foundCl.setTelefono(rs.getString("telefono"));
                    foundCl.setPortafoglio(rs.getBigDecimal("portafoglio"));
                    ut = foundCl;
                }

                utenti.put(ut.getId(), ut);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getUtentiWithDB: " + e.getMessage());
        }

        return utenti;
    }

    @Override
    public Utente getUtenteWithDB(Integer id) {
        String sql = "select * from utenti u where u.id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Utente foundUtente = null, utente = null;
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                String codeAdmin = rs.getString("codice_admin");

                if(codeAdmin != null) {
                    foundUtente = new Amministratore();
                }
                else {
                    foundUtente = new Cliente();
                }

                if(foundUtente instanceof Amministratore){
                    Amministratore foundAdmin = (Amministratore) foundUtente;
                    foundAdmin.setId(rs.getInt("id"));
                    foundAdmin.setNome(rs.getString("nome"));
                    foundAdmin.setCognome(rs.getString("cognome"));
                    foundAdmin.setSesso(rs.getString("sesso"));
                    foundAdmin.setDataNascita(rs.getDate("data_nascita"));
                    foundAdmin.setEmail(rs.getString("email"));
                    foundAdmin.setPassword(rs.getString("password"));
                    foundAdmin.setIndirizzo(rs.getString("indirizzo"));
                    foundAdmin.setTelefono(rs.getString("telefono"));
                    foundAdmin.setCodeAdmin(codeAdmin);
                    foundAdmin.setPortafoglio(rs.getBigDecimal("portafoglio"));
                    utente = foundAdmin;
                }
                else{
                    Cliente foundCliente = (Cliente) foundUtente;
                    foundCliente.setId(rs.getInt("id"));
                    foundCliente.setNome(rs.getString("nome"));
                    foundCliente.setCognome(rs.getString("cognome"));
                    foundCliente.setSesso(rs.getString("sesso"));
                    foundCliente.setDataNascita(rs.getDate("data_nascita"));
                    foundCliente.setEmail(rs.getString("email"));
                    foundCliente.setPassword(rs.getString("password"));
                    foundCliente.setIndirizzo(rs.getString("indirizzo"));
                    foundCliente.setTelefono(rs.getString("telefono"));
                    foundCliente.setPortafoglio(rs.getBigDecimal("portafoglio"));
                    utente = foundCliente;
                }
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getUtenteWithDB: " + e.getMessage());
        }

        return utente;
    }

    public Cliente checkCliente(String email, String password) {
        String sql = "";
        boolean pwdEmpty = false;

        if(password != null){
            sql = "select * from utenti u where u.email = ? and u.password = ? ";
        }
        else{
            sql = "select * from utenti u where u.email = ? and u.password is null ";
            pwdEmpty = true;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Cliente foundCliente = new Cliente();
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            if(!pwdEmpty){
                preparedStatement.setString(2, password);
            }
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                foundCliente.setId(rs.getInt("id"));
                foundCliente.setNome(rs.getString("nome"));
                foundCliente.setCognome(rs.getString("cognome"));
                foundCliente.setSesso(rs.getString("sesso"));
                foundCliente.setDataNascita(rs.getDate("data_nascita"));
                foundCliente.setEmail(rs.getString("email"));
                foundCliente.setPassword(rs.getString("password"));
                foundCliente.setIndirizzo(rs.getString("indirizzo"));
                foundCliente.setTelefono(rs.getString("telefono"));
                foundCliente.setPortafoglio(rs.getBigDecimal("portafoglio"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel checkCliente: " + e.getMessage());
        }

        return foundCliente;
    }

    public Amministratore checkAdmin(String email, String password, String codeAdmin) {
        String sql = "";
        boolean pwdEmpty = false, codeEmpty = false;

        if(password != null && codeAdmin != null){
            sql = "select * from utenti u where u.email = ? and u.password = ? and u.codice_admin = ? ";
        }
        else if(password != null && codeAdmin == null){
            sql = "select * from utenti u where u.email = ? and u.password = ? and u.codice_admin is null ";
            codeEmpty = true;
        }
        else if(password == null && codeAdmin != null){
            sql = "select * from utenti u where u.email = ? and u.password is null and u.codice_admin = ? ";
            pwdEmpty = true;
        }
        else {
            sql = "select * from utenti u where u.email = ? and u.password is null and u.codice_admin is null ";
            pwdEmpty = true;
            codeEmpty = true;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Amministratore foundAdmin = new Amministratore();
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            if(!pwdEmpty && !codeEmpty){
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, codeAdmin);
            }
            else if(!pwdEmpty && codeEmpty){
                preparedStatement.setString(2, password);
            }
            else if(pwdEmpty && !codeEmpty){
                preparedStatement.setString(2, codeAdmin);
            }
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                foundAdmin.setId(rs.getInt("id"));
                foundAdmin.setNome(rs.getString("nome"));
                foundAdmin.setCognome(rs.getString("cognome"));
                foundAdmin.setSesso(rs.getString("sesso"));
                foundAdmin.setDataNascita(rs.getDate("data_nascita"));
                foundAdmin.setEmail(rs.getString("email"));
                foundAdmin.setPassword(rs.getString("password"));
                foundAdmin.setIndirizzo(rs.getString("indirizzo"));
                foundAdmin.setTelefono(rs.getString("telefono"));
                foundAdmin.setCodeAdmin(rs.getString("codice_admin"));
                foundAdmin.setPortafoglio(rs.getBigDecimal("portafoglio"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel checkAdmin: " + e.getMessage());
        }

        return foundAdmin;
    }

    @Override
    public int updateUtenteWithDB(Integer id, Utente newU) {
        String sql = "UPDATE `utenti` SET `nome` = ?, `cognome` = ?, `sesso` = ?, `data_nascita` = ?, `email` = ?, `password` = ?, `telefono` = ?, `indirizzo` = ?, `codice_admin` = ?, `portafoglio` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, newU.getNome());
            preparedStatement.setString(2, newU.getCognome());
            preparedStatement.setString(3, newU.getSesso());
            preparedStatement.setDate(4, newU.getDataNascita());
            preparedStatement.setString(7, newU.getTelefono());
            preparedStatement.setString(8, newU.getIndirizzo());

            if(newU instanceof Amministratore){
                Amministratore newA = (Amministratore) newU;
                preparedStatement.setString(5, newA.getEmail());
                preparedStatement.setString(6, newA.getPassword());
                preparedStatement.setString(9, newA.getCodeAdmin());
                preparedStatement.setBigDecimal(10, newA.getPortafoglio());
            }
            else if(newU instanceof Cliente){
                Cliente newC = (Cliente) newU;
                preparedStatement.setString(5, newC.getEmail());
                preparedStatement.setString(6, newC.getPassword());
                preparedStatement.setString(9, null);
                preparedStatement.setBigDecimal(10, newC.getPortafoglio());
            }

            preparedStatement.setInt(11, id);

            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateUtenteWithDB: " + e.getMessage());
        }

        return num;
    }

    public int updateWalletUtente(Integer id, Utente newU) {
        String sql = "UPDATE `utenti` SET `portafoglio` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;


            if(newU instanceof Amministratore){
                Amministratore newA = (Amministratore) newU;
                preparedStatement.setBigDecimal(1, newA.getPortafoglio());
            }
            else if(newU instanceof Cliente){
                Cliente newC = (Cliente) newU;
                preparedStatement.setBigDecimal(1, newC.getPortafoglio());
            }

            preparedStatement.setInt(2, id);

            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateWalletUtente: " + e.getMessage());
        }

        return num;
    }

    @Override
    public int deleteUtenteWithDB(Integer id) {
        String sql = "DELETE FROM `utenti` WHERE id = ? ";
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
            Utility.msgInf("GEOSTORE", "Errore nel deleteUtenteWithDB: " + e.getMessage());
        }

        return num;
    }

    //metodi override per operazioni CRUD




}
