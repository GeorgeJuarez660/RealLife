package org.models;

import org.services.DBConnection;
import org.utility.Utility;
import org.utility.crud.prodottiCRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ProdottoRepository implements prodottiCRUD {

    private HashMap<Integer, Prodotto> prodotti = new HashMap<>();

    @Override
    public int insertProdottoWithDB(Integer id, Prodotto p) {
        String sql = "INSERT INTO `prodotti`(`nome`,`prezzo`,`disponibilita`,`categoria`,`materia`,`quantita_disp`) VALUES (?, ?, ?, ?, ?, ?) ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, p.getNome());
            preparedStatement.setBigDecimal(2, p.getPrezzo());
            preparedStatement.setInt(3, p.getDisponibilita().getId());
            preparedStatement.setInt(4, p.getCategoria().getId());
            preparedStatement.setInt(5, p.getMateria().getId());
            preparedStatement.setInt(6, p.getQuantita_disp());

            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel insertProdottoWithDB: " + e.getMessage());
        }

        return num;
    }

    @Override
    public HashMap<Integer, Prodotto> getProdottiWithDB() {
        String sql = "select c.id as cat_id, c.nome as cat_nome, d.id as disp_id, d.code as disp_code, m.id as mat_id, m.nome as mat_nome, o.id, o.nome, o.prezzo, o.disponibilita, o.categoria, o.materia, o.quantita_disp \n" +
                "from prodotti o join categorie c on(o.categoria=c.id)\n" +
                "join materie m on(o.materia=m.id)\n" +
                "join disponibilita d on(o.disponibilita=d.id)\n";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        prodotti = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Prodotto prodotto;

            while(rs.next()){
                prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                Disponibilita disponibilita = new Disponibilita();
                disponibilita.setId(rs.getInt("disp_id"));
                disponibilita.setCode(rs.getString("disp_code"));
                prodotto.setDisponibilita(disponibilita);
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("cat_id"));
                categoria.setNome(rs.getString("cat_nome"));
                prodotto.setCategoria(categoria);
                Materia materia = new Materia();
                materia.setId(rs.getInt("mat_id"));
                materia.setNome(rs.getString("mat_nome"));
                prodotto.setMateria(materia);
                prodotto.setQuantita_disp(rs.getInt("quantita_disp"));

                prodotti.put(prodotto.getId(), prodotto);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getProdottiWithDB: " + e.getMessage());
        }

        return prodotti;
    }

    public HashMap<Integer, Prodotto> getProdottiDispWithDB() {
        String sql = "select c.id as cat_id, c.nome as cat_nome, d.id as disp_id, d.code as disp_code, m.id as mat_id, m.nome as mat_nome, o.id, o.nome, o.prezzo, o.disponibilita, o.categoria, o.materia, o.quantita_disp \n" +
                "from prodotti o join categorie c on(o.categoria=c.id)\n" +
                "join materie m on(o.materia=m.id)\n" +
                "join disponibilita d on(o.disponibilita=d.id)\n"+
                "where o.disponibilita = 1 OR o.disponibilita = 3";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        prodotti = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            Prodotto prodotto;

            while(rs.next()){
                prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                Disponibilita disponibilita = new Disponibilita();
                disponibilita.setId(rs.getInt("disp_id"));
                disponibilita.setCode(rs.getString("disp_code"));
                prodotto.setDisponibilita(disponibilita);
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("cat_id"));
                categoria.setNome(rs.getString("cat_nome"));
                prodotto.setCategoria(categoria);
                Materia materia = new Materia();
                materia.setId(rs.getInt("mat_id"));
                materia.setNome(rs.getString("mat_nome"));
                prodotto.setMateria(materia);
                prodotto.setQuantita_disp(rs.getInt("quantita_disp"));

                prodotti.put(prodotto.getId(), prodotto);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getProdottiDispWithDB: " + e.getMessage());
        }

        return prodotti;
    }

    public HashMap<Integer, Prodotto> getProdottiViaCategoriaWithDB(Integer idCat) {
        String sql = "select c.id as cat_id, c.nome as cat_nome, d.id as disp_id, d.code as disp_code, m.id as mat_id, m.nome as mat_nome, o.id, o.nome, o.prezzo, o.disponibilita, o.categoria, o.materia, o.quantita_disp \n" +
                "from prodotti o join categorie c on(o.categoria=c.id)\n" +
                "join materie m on(o.materia=m.id)\n" +
                "join disponibilita d on(o.disponibilita=d.id)\n" +
                "where o.categoria = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        prodotti = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idCat);
            rs = preparedStatement.executeQuery();
            Prodotto prodotto;

            while(rs.next()){
                prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                Disponibilita disponibilita = new Disponibilita();
                disponibilita.setId(rs.getInt("disp_id"));
                disponibilita.setCode(rs.getString("disp_code"));
                prodotto.setDisponibilita(disponibilita);
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("cat_id"));
                categoria.setNome(rs.getString("cat_nome"));
                prodotto.setCategoria(categoria);
                Materia materia = new Materia();
                materia.setId(rs.getInt("mat_id"));
                materia.setNome(rs.getString("mat_nome"));
                prodotto.setMateria(materia);
                prodotto.setQuantita_disp(rs.getInt("quantita_disp"));

                prodotti.put(prodotto.getId(), prodotto);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getProdottiViaCategoriaWithDB: " + e.getMessage());
        }

        return prodotti;
    }

    public HashMap<Integer, Prodotto> getProdottiViaCategoriaByKeywordWithDB(Integer idCat, String keyword) {
        String sql = "select c.id as cat_id, c.nome as cat_nome, d.id as disp_id, d.code as disp_code, m.id as mat_id, m.nome as mat_nome, o.id, o.nome, o.prezzo, o.disponibilita, o.categoria, o.materia, o.quantita_disp \n" +
                "from prodotti o join categorie c on(o.categoria=c.id)\n" +
                "join materie m on(o.materia=m.id)\n" +
                "join disponibilita d on(o.disponibilita=d.id)\n" +
                "where o.categoria = ? and o.nome LIKE ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        prodotti = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idCat);
            keyword = "%" + keyword + "%";
            preparedStatement.setString(2, keyword);
            rs = preparedStatement.executeQuery();
            Prodotto prodotto;

            while(rs.next()){
                prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                Disponibilita disponibilita = new Disponibilita();
                disponibilita.setId(rs.getInt("disp_id"));
                disponibilita.setCode(rs.getString("disp_code"));
                prodotto.setDisponibilita(disponibilita);
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("cat_id"));
                categoria.setNome(rs.getString("cat_nome"));
                prodotto.setCategoria(categoria);
                Materia materia = new Materia();
                materia.setId(rs.getInt("mat_id"));
                materia.setNome(rs.getString("mat_nome"));
                prodotto.setMateria(materia);
                prodotto.setQuantita_disp(rs.getInt("quantita_disp"));

                prodotti.put(prodotto.getId(), prodotto);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getProdottiViaCategoriaWithDB: " + e.getMessage());
        }

        return prodotti;
    }

    public HashMap<Integer, Prodotto> getProdottiViaMateriaWithDB(Integer idMat) {
        String sql = "select c.id as cat_id, c.nome as cat_nome, d.id as disp_id, d.code as disp_code, m.id as mat_id, m.nome as mat_nome, o.id, o.nome, o.prezzo, o.disponibilita, o.categoria, o.materia, o.quantita_disp \n" +
                "from prodotti o join categorie c on(o.categoria=c.id)\n" +
                "join materie m on(o.materia=m.id)\n" +
                "join disponibilita d on(o.disponibilita=d.id)\n" +
                "where o.materia = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        prodotti = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMat);
            rs = preparedStatement.executeQuery();
            Prodotto prodotto;

            while(rs.next()){
                prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                Disponibilita disponibilita = new Disponibilita();
                disponibilita.setId(rs.getInt("disp_id"));
                disponibilita.setCode(rs.getString("disp_code"));
                prodotto.setDisponibilita(disponibilita);
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("cat_id"));
                categoria.setNome(rs.getString("cat_nome"));
                prodotto.setCategoria(categoria);
                Materia materia = new Materia();
                materia.setId(rs.getInt("mat_id"));
                materia.setNome(rs.getString("mat_nome"));
                prodotto.setMateria(materia);
                prodotto.setQuantita_disp(rs.getInt("quantita_disp"));

                prodotti.put(prodotto.getId(), prodotto);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getProdottiViaMateriaWithDB: " + e.getMessage());
        }

        return prodotti;
    }

    public HashMap<Integer, Prodotto> getProdottiViaMateriaByKeywordWithDB(Integer idMat, String keyword) {
        String sql = "select c.id as cat_id, c.nome as cat_nome, d.id as disp_id, d.code as disp_code, m.id as mat_id, m.nome as mat_nome, o.id, o.nome, o.prezzo, o.disponibilita, o.categoria, o.materia, o.quantita_disp \n" +
                "from prodotti o join categorie c on(o.categoria=c.id)\n" +
                "join materie m on(o.materia=m.id)\n" +
                "join disponibilita d on(o.disponibilita=d.id)\n" +
                "where o.materia = ? and o.nome LIKE ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        prodotti = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMat);
            keyword = "%" + keyword + "%";
            preparedStatement.setString(2, keyword);
            rs = preparedStatement.executeQuery();
            Prodotto prodotto;

            while(rs.next()){
                prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                Disponibilita disponibilita = new Disponibilita();
                disponibilita.setId(rs.getInt("disp_id"));
                disponibilita.setCode(rs.getString("disp_code"));
                prodotto.setDisponibilita(disponibilita);
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("cat_id"));
                categoria.setNome(rs.getString("cat_nome"));
                prodotto.setCategoria(categoria);
                Materia materia = new Materia();
                materia.setId(rs.getInt("mat_id"));
                materia.setNome(rs.getString("mat_nome"));
                prodotto.setMateria(materia);
                prodotto.setQuantita_disp(rs.getInt("quantita_disp"));

                prodotti.put(prodotto.getId(), prodotto);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getProdottiViaMateriaWithDB: " + e.getMessage());
        }

        return prodotti;
    }

    @Override
    public Prodotto getProdottoWithDB(Integer id) {
        String sql = "select c.id as cat_id, c.nome as cat_nome, d.id as disp_id, d.code as disp_code, m.id as mat_id, m.nome as mat_nome, o.id, o.nome, o.prezzo, o.disponibilita, o.categoria, o.materia, o.quantita_disp \n" +
                "from prodotti o join categorie c on(o.categoria=c.id)\n" +
                "join materie m on(o.materia=m.id)\n" +
                "join disponibilita d on(o.disponibilita=d.id)\n" +
                "where o.id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Prodotto prodotto = new Prodotto();
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                Disponibilita disponibilita = new Disponibilita();
                disponibilita.setId(rs.getInt("disp_id"));
                disponibilita.setCode(rs.getString("disp_code"));
                prodotto.setDisponibilita(disponibilita);
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("cat_id"));
                categoria.setNome(rs.getString("cat_nome"));
                prodotto.setCategoria(categoria);
                Materia materia = new Materia();
                materia.setId(rs.getInt("mat_id"));
                materia.setNome(rs.getString("mat_nome"));
                prodotto.setMateria(materia);
                prodotto.setQuantita_disp(rs.getInt("quantita_disp"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getProdottoWithDB: " + e.getMessage());
        }

        return prodotto;
    }

    public HashMap<Integer, Prodotto> getProdottoByKeywordWithDB(String keyword) {
        String sql = "select c.id as cat_id, c.nome as cat_nome, d.id as disp_id, d.code as disp_code, m.id as mat_id, m.nome as mat_nome, o.id, o.nome, o.prezzo, o.disponibilita, o.categoria, o.materia, o.quantita_disp \n" +
                "from prodotti o join categorie c on(o.categoria=c.id)\n" +
                "join materie m on(o.materia=m.id)\n" +
                "join disponibilita d on(o.disponibilita=d.id)\n" +
                "where o.nome LIKE ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        prodotti = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            keyword = "%" + keyword + "%";
            preparedStatement.setString(1, keyword);
            rs = preparedStatement.executeQuery();
            Prodotto prodotto;

            while(rs.next()){
                prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                Disponibilita disponibilita = new Disponibilita();
                disponibilita.setId(rs.getInt("disp_id"));
                disponibilita.setCode(rs.getString("disp_code"));
                prodotto.setDisponibilita(disponibilita);
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("cat_id"));
                categoria.setNome(rs.getString("cat_nome"));
                prodotto.setCategoria(categoria);
                Materia materia = new Materia();
                materia.setId(rs.getInt("mat_id"));
                materia.setNome(rs.getString("mat_nome"));
                prodotto.setMateria(materia);
                prodotto.setQuantita_disp(rs.getInt("quantita_disp"));

                prodotti.put(prodotto.getId(), prodotto);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getProdottoByKeywordWithDB: " + e.getMessage());
        }

        return prodotti;
    }

    public Prodotto getProdottoDispWithDB(Integer id) {
        String sql = "select c.id as cat_id, c.nome as cat_nome, d.id as disp_id, d.code as disp_code, m.id as mat_id, m.nome as mat_nome, o.id, o.nome, o.prezzo, o.disponibilita, o.categoria, o.materia, o.quantita_disp \n" +
                "from prodotti o join categorie c on(o.categoria=c.id)\n" +
                "join materie m on(o.materia=m.id)\n" +
                "join disponibilita d on(o.disponibilita=d.id)\n" +
                "where o.id = ? AND (o.disponibilita = 1 OR o.disponibilita = 3)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Prodotto prodotto = new Prodotto();
        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while(rs.next()){
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                Disponibilita disponibilita = new Disponibilita();
                disponibilita.setId(rs.getInt("disp_id"));
                disponibilita.setCode(rs.getString("disp_code"));
                prodotto.setDisponibilita(disponibilita);
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("cat_id"));
                categoria.setNome(rs.getString("cat_nome"));
                prodotto.setCategoria(categoria);
                Materia materia = new Materia();
                materia.setId(rs.getInt("mat_id"));
                materia.setNome(rs.getString("mat_nome"));
                prodotto.setMateria(materia);
                prodotto.setQuantita_disp(rs.getInt("quantita_disp"));
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getProdottoDispWithDB: " + e.getMessage());
        }

        return prodotto;
    }

    public HashMap<Integer, Prodotto> getProdottoDispByKeywordWithDB(String keyword) {
        String sql = "select c.id as cat_id, c.nome as cat_nome, d.id as disp_id, d.code as disp_code, m.id as mat_id, m.nome as mat_nome, o.id, o.nome, o.prezzo, o.disponibilita, o.categoria, o.materia, o.quantita_disp \n" +
                "from prodotti o join categorie c on(o.categoria=c.id)\n" +
                "join materie m on(o.materia=m.id)\n" +
                "join disponibilita d on(o.disponibilita=d.id)\n" +
                "where o.nome LIKE ? AND (o.disponibilita = 1 OR o.disponibilita = 3)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        prodotti = new HashMap<>();

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            keyword = "%" + keyword + "%";
            preparedStatement.setString(1, keyword);
            rs = preparedStatement.executeQuery();
            Prodotto prodotto;

            while(rs.next()){
                prodotto = new Prodotto();
                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setPrezzo(rs.getBigDecimal("prezzo"));
                Disponibilita disponibilita = new Disponibilita();
                disponibilita.setId(rs.getInt("disp_id"));
                disponibilita.setCode(rs.getString("disp_code"));
                prodotto.setDisponibilita(disponibilita);
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("cat_id"));
                categoria.setNome(rs.getString("cat_nome"));
                prodotto.setCategoria(categoria);
                Materia materia = new Materia();
                materia.setId(rs.getInt("mat_id"));
                materia.setNome(rs.getString("mat_nome"));
                prodotto.setMateria(materia);
                prodotto.setQuantita_disp(rs.getInt("quantita_disp"));

                prodotti.put(prodotto.getId(), prodotto);
            }
            //chiudi la connessione
            rs.close();
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel getProdottoDispByKeywordWithDB: " + e.getMessage());
        }

        return prodotti;
    }

    @Override
    public int updateProdottoWithDB(Integer id, Prodotto newP) {
        String sql = "UPDATE `prodotti` SET `nome` = ?, `prezzo` = ?, `disponibilita` = ?, `categoria` = ?, `materia` = ?, `quantita_disp` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setString(1, newP.getNome());
            preparedStatement.setBigDecimal(2, newP.getPrezzo());
            preparedStatement.setInt(3, newP.getDisponibilita().getId());
            preparedStatement.setInt(4, newP.getCategoria().getId());
            preparedStatement.setInt(5, newP.getMateria().getId());
            preparedStatement.setInt(6, newP.getQuantita_disp());

            preparedStatement.setInt(7, id);

            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateProdottoWithDB: " + e.getMessage());
        }

        return num;
    }

    public int updateProdottoAfterAccOrdineWithDB(Integer id, Integer disp, Integer quantitaDisp) {
        String sql = "UPDATE `prodotti` SET `disponibilita` = ?, `quantita_disp` = ? WHERE id = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setInt(1, disp);
            preparedStatement.setInt(2, quantitaDisp);

            preparedStatement.setInt(3, id);

            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateProdottoAfterAccOrdineWithDB: " + e.getMessage());
        }

        return num;
    }

    public int updateIdAfterDeleteCategory(Integer idNew, Integer idOld) {
        String sql = "UPDATE `prodotti` SET `categoria` = ? WHERE categoria = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setInt(1, idNew);
            preparedStatement.setInt(2, idOld);

            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateIdAfterDeleteCategory: " + e.getMessage());
        }

        return num;
    }

    public int updateIdAfterDeleteMaterial(Integer idNew, Integer idOld) {
        String sql = "UPDATE `prodotti` SET `materia` = ? WHERE materia = ? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int num = 0;

        try{
            //Connessione al db
            connection = DBConnection.sqlConnect();
            preparedStatement = connection.prepareStatement(sql);
            //int num = 0;

            preparedStatement.setInt(1, idNew);
            preparedStatement.setInt(2, idOld);

            num = preparedStatement.executeUpdate();
            //chiudi la connessione
            preparedStatement.close();
            connection.close();
        }catch(SQLException e){
            Utility.msgInf("GEOSTORE", "Errore nel updateIdAfterDeleteMaterial: " + e.getMessage());
        }

        return num;
    }

    @Override
    public int deleteProdottoWithDB(Integer id) {
        String sql = "DELETE FROM `prodotti` WHERE id = ? ";
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
            Utility.msgInf("GEOSTORE", "Errore nel deleteProdottoWithDB: " + e.getMessage());
        }

        return num;
    }




}
