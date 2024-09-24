package src.main.java.model;

import src.main.java.utility.DBConnection;
import src.main.java.utility.Utility;
import src.main.java.utility.ordiniCRUD;

import java.sql.*;
import java.util.HashMap;

public class OrdineRepository implements ordiniCRUD {

    private HashMap<Integer, Ordine> ordini = new HashMap<>();

    @Override
    public int insertOrdineWithDB(Integer id, Ordine o) {
        String sql = "INSERT INTO `ordini`(`utente_id`, `prodotto_id`,`data_ordine`,`quantita`,`prezzo_unitario`,`stato_id`) VALUES (?, ?, ?, ?, ?, ?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setInt(1, o.getUtente().getId());
            preparedStatement.setInt(2, o.getProdotto().getId());
            preparedStatement.setString(3, String.valueOf(o.getData_ordine()));
            preparedStatement.setInt(4, o.getQuantita());
            preparedStatement.setBigDecimal(5, o.getPrezzo_unitario());
            preparedStatement.setInt(6, o.getStato().getId());
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertOrdineWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public HashMap<Integer, Ordine> getOrdiniWithDB() {
        String sql = "SELECT o.id, u.nome AS nome_utente, u.cognome AS cognome_utente, og.nome AS nome_prodotto, o.data_ordine, o.quantita, o.prezzo_unitario, o.stato_id FROM ordini o JOIN utenti u ON(o.utente_id =u.id )\n" +
                "JOIN prodotti og ON(o.prodotto_id =og.id ) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ordini = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Ordine ord;

            while(rs.next()){
                ord = new Ordine();
                ord.setId(rs.getInt("id"));
                Utente utente = new Utente();
                utente.setNome(rs.getString("nome_utente"));
                utente.setCognome(rs.getString("cognome_utente"));
                ord.setUtente(utente);
                Prodotto prodotto = new Prodotto();
                prodotto.setNome(rs.getString("nome_prodotto"));
                ord.setProdotto(prodotto);
                ord.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ord.setQuantita(rs.getInt("quantita"));
                ord.setPrezzo_unitario(rs.getBigDecimal("prezzo_unitario"));
                Stato stato = new Stato();
                stato.setId(rs.getInt("stato_id"));
                ord.setStato(stato);

                ordini.put(ord.getId(), ord);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdiniWithDB: " + e.getMessage());
        }

        return ordini;
    }

    public HashMap<Integer, Ordine> getOrdiniByUserWithDB(Integer idUtente) {
        String sql = "SELECT o.id, u.nome AS nome_utente, u.cognome AS cognome_utente, og.nome AS nome_prodotto, o.data_ordine, o.quantita, o.prezzo_unitario, o.stato_id FROM ordini o JOIN utenti u ON(o.utente_id =u.id )\n" +
                "JOIN prodotti og ON(o.prodotto_id =og.id )\n" +
                "WHERE o.utente_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        ordini = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idUtente);
            rs = preparedStatement.executeQuery();
            Ordine ord;

            while(rs.next()){
                ord = new Ordine();
                ord.setId(rs.getInt("id"));
                Utente utente = new Utente();
                utente.setNome(rs.getString("nome_utente"));
                utente.setCognome(rs.getString("cognome_utente"));
                ord.setUtente(utente);
                Prodotto prodotto = new Prodotto();
                prodotto.setNome(rs.getString("nome_prodotto"));
                ord.setProdotto(prodotto);
                ord.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ord.setQuantita(rs.getInt("quantita"));
                ord.setPrezzo_unitario(rs.getBigDecimal("prezzo_unitario"));
                Stato stato = new Stato();
                stato.setId(rs.getInt("stato_id"));
                ord.setStato(stato);

                ordini.put(ord.getId(), ord);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdiniByUserWithDB: " + e.getMessage());
        }

        return ordini;
    }

    public Ordine getOrdineByUserWithDB(Integer idUtente, Integer idOrdine) {
        String sql = "SELECT o.id, u.nome AS nome_utente, u.cognome AS cognome_utente, og.nome AS nome_prodotto, o.data_ordine, o.quantita, o.prezzo_unitario, o.stato_id FROM ordini o JOIN utenti u ON(o.utente_id =u.id )\n" +
                "JOIN prodotti og ON(o.prodotto_id =og.id )\n" +
                "WHERE o.utente_id = ? AND o.id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Ordine ordine = new Ordine();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idUtente);
            preparedStatement.setInt(2, idOrdine);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                ordine.setId(rs.getInt("id"));
                Utente utente = new Utente();
                utente.setNome(rs.getString("nome_utente"));
                utente.setCognome(rs.getString("cognome_utente"));
                ordine.setUtente(utente);
                Prodotto prodotto = new Prodotto();
                prodotto.setNome(rs.getString("nome_prodotto"));
                ordine.setProdotto(prodotto);
                ordine.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ordine.setQuantita(rs.getInt("quantita"));
                ordine.setPrezzo_unitario(rs.getBigDecimal("prezzo_unitario"));
                Stato stato = new Stato();
                stato.setId(rs.getInt("stato_id"));
                ordine.setStato(stato);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdiniByUserWithDB: " + e.getMessage());
        }

        return ordine;
    }

    @Override
    public Ordine getOrdineWithDB(Integer id) {
        String sql = "SELECT o.id, u.nome AS nome_utente, u.cognome AS cognome_utente, og.nome AS nome_prodotto, o.data_ordine, o.quantita, o.prezzo_unitario, o.stato_id FROM ordini o JOIN utenti u ON(o.utente_id =u.id )\n" +
                "JOIN prodotti og ON(o.prodotto_id =og.id )\n" +
                "WHERE o.id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Ordine ordine = new Ordine();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                ordine.setId(rs.getInt("id"));
                Utente utente = new Utente();
                utente.setNome(rs.getString("nome_utente"));
                utente.setCognome(rs.getString("cognome_utente"));
                ordine.setUtente(utente);
                Prodotto prodotto = new Prodotto();
                prodotto.setNome(rs.getString("nome_prodotto"));
                ordine.setProdotto(prodotto);
                ordine.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ordine.setQuantita(rs.getInt("quantita"));
                ordine.setPrezzo_unitario(rs.getBigDecimal("prezzo_unitario"));
                Stato stato = new Stato();
                stato.setId(rs.getInt("stato_id"));
                ordine.setStato(stato);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdineWithDB: " + e.getMessage());
        }

        return ordine;
    }

    @Override
    public int updateOrdineWithDB(Integer id, Ordine newO) {
        String sql = "UPDATE `ordini` SET `stato_id` = ?, `quantita` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setInt(1, newO.getStato().getId());
            preparedStatement.setInt(2, newO.getQuantita());
            preparedStatement.setInt(3, id);
            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateOrdineWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public void deleteOrdineWithDB(Integer id) {
        String sql = "DELETE FROM `ordini` WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel deleteOrdineWithDB: " + e.getMessage());
        }
    }

    public Ordine getOrdineTotGiorWithDB(Utente u, String data) {
        String sql = "SELECT u.nome as nome_utente, u.cognome as cognome_utente, o.data_ordine, sum(o.quantita*o.prezzo_unitario) AS tot_ord_gior FROM ordini o JOIN utenti u ON(o.utente_id =u.id )\n" +
                "JOIN prodotti p ON(o.prodotto_id =p.id )\n" +
                "WHERE u.id = ? AND o.data_ordine LIKE ? AND o.stato_id = 2\n" +
                "GROUP BY o.data_ordine ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Ordine ordine = new Ordine();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, u.getId());
            preparedStatement.setString(2, data + "%");
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Utente utente = new Utente();
                utente.setNome(rs.getString("nome_utente"));
                utente.setCognome(rs.getString("cognome_utente"));
                ordine.setUtente(utente);
                ordine.setData_ordine(Timestamp.valueOf(rs.getString("data_ordine")));
                ordine.setPrezzo_unitario(rs.getBigDecimal("tot_ord_gior"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getOrdineTotGiorWithDB: " + e.getMessage());
        }

        return ordine;
    }

    //metodi override per operazioni CRUD




}
