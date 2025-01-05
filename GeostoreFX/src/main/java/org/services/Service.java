package org.services;

import org.models.*;
import org.utility.Utility;


import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Service {

    ProdottoRepository pr = new ProdottoRepository();
    UtenteRepository ur = new UtenteRepository();
    NewsRepository nr = new NewsRepository();
    CategoriaRepository cr = new CategoriaRepository();
    MateriaRepository mr = new MateriaRepository();
    OrdineRepository or = new OrdineRepository();
    DisponibilitaRepository dr = new DisponibilitaRepository();
    StatusRepository sr = new StatusRepository();


    public Utente ottieniProfiloUtente(Integer idUtente){
        return ur.getUtenteWithDB(idUtente);
    }

    public Map<Integer, Utente> ottieniUtenteByKeyword(String keyword){
        return ur.getUtentiByKeywordWithDB(keyword);
    }

    public Map<Integer, Utente> elencoUtenti(){
        return ur.getUtentiWithDB();
    }

    public void loginUtente(Cliente user){
        int num = 0;
        if(user instanceof Amministratore){
            Amministratore admin = (Amministratore) user;
            admin = ur.checkAdmin(admin.getEmail(), admin.getPassword(), admin.getCodeAdmin());

            if(admin.getEmail() != null && admin.getCodeAdmin() != null){
                num = 1;
            }
            user = admin;
        }
        else{
            user = ur.checkCliente(user.getEmail(), user.getPassword());

            if(user.getEmail() != null){
                num = 1;
            }
        }

        Utility.sendResponseLogin(num, user);
    }

    public void registerUtente(Cliente user){
        int num = 0;
        num = ur.checkDuplicatesUtente(user);

        if(num == 0){
            num = ur.insertUtenteWithDB(user.getId(), user);
            Utility.sendResponseRegister(num);
        }
        else{
            Utility.sendResponseRegister(0);
        }

    }

    public void creazioneUtente(Cliente user, Cliente userID){
        int num = 0;

        num = ur.checkDuplicatesUtente(user);

        if(num == 0){
            if(Utility.getAge(user.getDataNascita())){
                num = ur.insertUtenteWithDB(null, user);

                if(num > 0){
                    Utility.sendResponse(num, "UTENTE CREATO", userID);
                }
                else{
                    Utility.sendResponse(num, "CREAZIONE UTENTE", userID);
                }
            }
            else{
                Utility.sendResponse(0, "L'UTENTE DEVE AVERE ALMENO 13 ANNI. CREAZIONE UTENTE", userID);
            }
        }
        else{
            Utility.sendResponse(0, "L'UTENTE GIÀ ESISTE. CREAZIONE UTENTE", userID);
        }

    }

    public void modificaUtente(Utente u, Cliente userID){
        int num = 0;

        num = ur.checkDuplicatesUtente(u);

        if(num == 0){
            if(Utility.getAge(u.getDataNascita())){
                num = ur.updateUtenteWithDB(u.getId(), u);

                if(num > 0){
                    Utility.sendResponse(num, "UTENTE MODIFICATO", userID);
                }
                else{
                    Utility.sendResponse(num, "MODIFICA UTENTE", userID);
                }
            }
            else{
                Utility.sendResponse(0, "L'UTENTE DEVE AVERE ALMENO 13 ANNI. MODIFICA UTENTE", userID);
            }
        }
        else{
            Utility.sendResponse(0, "L'UTENTE GIÀ ESISTE. MODIFICA UTENTE", userID);
        }

    }

    public void eliminazioneUtente(String IDkey, Cliente userID){
        int num = ur.deleteUtenteWithDB(Integer.parseInt(IDkey));

        if(num > 0){
            Utility.sendResponse(num, "UTENTE ELIMINATO", userID);
        }
        else{
            Utility.sendResponse(num, "ELIMINAZIONE UTENTE", userID);
        }
    }

    public ArrayList<News> elencoNotizie(){
        return nr.getNotizieWithDB();
    }

    public News ottieniNotiziaByID(String IDNews){
        return nr.getNotiziaWithByIdWithDB(IDNews);
    }

    public ArrayList<News> ottieniNotizieByKeyword(String keyword){
        return nr.getNotizieByKeywordWithDB(keyword);
    }

    public void creazioneNotizia(News notizia, Cliente user){
        notizia.setUtente(user);
        int num = 0;

        num = nr.insertNotizieWithDB(notizia.getDataPub(), notizia.getDataMod(), notizia.getTesto(), notizia.getUtente().getId());

        if(num > 0){
            Utility.sendResponse(num, "NEWS CREATO", user);
        }
        else{
            Utility.sendResponse(num, "CREAZIONE NEWS", user);
        }
    }

    public void creazioneNotiziaSenzaRisposta(News notizia){
        int num = 0;

        nr.insertNotizieWithDB(notizia.getDataPub(), notizia.getDataMod(), notizia.getTesto(), notizia.getUtente().getId());
    }

    public void modificaNotizia(News notizia, Cliente user){
        notizia.setUtente(user);
        int num = 0;

        num = nr.updateNotizieWithDB(notizia.getId(), notizia.getDataMod(), notizia.getTesto(), notizia.getUtente().getId());

        if(num > 0){
            Utility.sendResponse(num, "NEWS MODIFICATO", user);
        }
        else{
            Utility.sendResponse(num, "MODIFICA NEWS", user);
        }
    }

    public void eliminazioneNotizia(String IDKey, Cliente user){
        int num = 0;

        num = nr.deleteNotizieWithDB(Integer.parseInt(IDKey));

        if(num > 0){
            Utility.sendResponse(num, "NEWS ELIMINATO", user);
        }
        else{
            Utility.sendResponse(num, "ELIMINAZIONE NEWS", user);
        }
    }

    public Map<Integer, Prodotto> elencoProdotti(){
        return pr.getProdottiWithDB();
    }

    public Map<Integer, Prodotto> elencoProdottiDisponibili(){
        return pr.getProdottiDispWithDB();
    }

    public Map<Integer, Prodotto> ottieniProdottoByKeyword(String keyword){
        return pr.getProdottoByKeywordWithDB(keyword);
    }

    public Prodotto ottieniProdotto(Integer idProdotto){
        return pr.getProdottoWithDB(idProdotto);
    }

    public Map<Integer, Prodotto> ottieniProdottoDisponibileByKeyword(String keyword){
        return pr.getProdottoDispByKeywordWithDB(keyword);
    }

    public void creazioneProdotto(Prodotto product, Cliente user){
        int num = 0;

        num = pr.insertProdottoWithDB(product.getId(), product);

        if(num > 0){
            //notizia per la creazione prodotto
            News notiziaCreazione = new News();
            notiziaCreazione.setUtente(user);
            notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setTesto("È stato pubblicato un nuovo prodotto: " + product.getNome() + " a soli " + Utility.formatValueBigDecimal(product.getPrezzo()) + " C. " + product.getDisponibilita().getCode() + " su GeoStore");
            this.creazioneNotiziaSenzaRisposta(notiziaCreazione);

            Utility.sendResponse(num, "PRODOTTO CREATO", user);
        }
        else{
            Utility.sendResponse(num, "CREAZIONE PRODOTTO", user);
        }
    }

    public void modificaProdotto(Prodotto product, Cliente user){
        int num = 0;
        num = pr.updateProdottoWithDB(product.getId(), product);

        if(num > 0){
            Utility.sendResponse(num, "PRODOTTO MODIFICATO", user);
        }
        else{
            Utility.sendResponse(num, "MODIFICA PRODOTTO", user);
        }
    }

    public void eliminazioneProdotto(String IDkey, Cliente user){
        Prodotto product = pr.getProdottoWithDB(Integer.parseInt(IDkey));
        HashMap<Integer, Ordine> ordini = or.getOrdiniByProductWithDB(Integer.parseInt(IDkey));

        for(Ordine ordine : ordini.values()){
            //il prodotto è più cruciale rispetto all'ordine, motivo per cui vengono rimborsati anche se gli ordini sono in stato divrso da RIFIUTATO
            if(ordine.getStato().getId() != 3 && ordine.getStato().getId() != 5){
                refundAfterDeleteOrder(ordine, ordine.getUtente());
            }
            else{
                Utility.msgInf("GEOSTORE", "L'ordine è già stato rifiutato oppure consegnato\n");
            }
        }

        int num = or.deleteOrdineAfterDeleteProduct(Integer.parseInt(IDkey));

        if(num > 0){
            Utility.msgInf("GEOSTORE", "Ordini eliminati\n");
        }
        else{
            Utility.msgInf("GEOSTORE", "Ordini non eliminati\n");
        }

        num = 0;
        num = pr.deleteProdottoWithDB(Integer.parseInt(IDkey));

        if(num > 0){
            //notizia per l'eliminazione prodotto
            News notiziaCreazione = new News();
            notiziaCreazione.setUtente(user);
            notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setTesto("È stato rimosso il prodotto: " + product.getNome() + ". Sono stati effettuati i rimborsi agli utenti che avevano ordinato questo prodotto");
            this.creazioneNotiziaSenzaRisposta(notiziaCreazione);

            Utility.sendResponseDeletedProducts(num, user);
        }
        else{
            Utility.sendResponseDeletedProducts(num, user);
        }

    }

    public Map<Integer, Ordine> elencoOrdini(){
        return or.getOrdiniWithDB();
    }

    public Map<Integer, Ordine> elencoPropriOrdini(Integer idUtente){
        return or.getOrdiniByUserWithDB(idUtente);
    }

    public Map<Integer, Ordine> elencoOrdiniByEmail(String email){
        return or.getOrdiniByEmailWithDB(email);
    }

    public Map<Integer, Ordine> elencoPropriOrdiniByKeyword(Integer idUtente, String keyword){
        return or.getOrdiniByUserAndKeywordWithDB(idUtente, keyword);
    }

    public Ordine ottieniOrdine(Integer idOrdine){
        return or.getOrdineWithDB(idOrdine);
    }

    public void ordinazioneProdotto(Ordine o, Cliente user){
        String canOrder = checkAmountOrderAndSufficientWallet(o, user);
        char firstchar = canOrder.charAt(0);
        String response = canOrder.substring(4);
        if(firstchar == 'T'){
            int num = or.insertOrdineWithDB(null, o);
            if(num > 0){
                Utility.sendResponseOrderedProducts(num, response, user);
            }
            else{
                Utility.sendResponseOrderedProducts(num, response, user);
            }
        }
        else{
            Utility.sendResponseOrderedProducts(0, response, user);
        }
    }

    public void modificaOrdine(Ordine order, Cliente user){

        Stato s = sr.getStatoWithDB(order.getStato().getId());

        if(s != null && s.getCode() != null){
            Ordine orderOld = or.getOrdineWithDB(order.getId());
            Utente u = ur.getUtenteWithDB(orderOld.getUtente().getId());
            String responseCheckOrder;

            responseCheckOrder = checkOrderChanged(orderOld, order, u);
            char firstchar = responseCheckOrder.charAt(0);
            String response = responseCheckOrder.substring(4);
            if(firstchar == 'T'){
                changeStatusProdottoAfterOrder(orderOld, order);

                int num = or.updateOrdineWithDB(order.getId(), order);

                if(num > 0){
                    Utility.sendResponse(num, response, user);
                }
                else{
                    Utility.sendResponse(num, response, user);
                }
            }
            else{
                Utility.sendResponse(0, response, user);
            }
        }
    }

    private static String checkAmountOrderAndSufficientWallet(Ordine o, Utente u){
        String canOrder = "";
        ProdottoRepository pr = new ProdottoRepository();
        UtenteRepository ur = new UtenteRepository();

        Prodotto p = pr.getProdottoDispWithDB(o.getProdotto().getId());
        if(p != null && p.getNome() != null){
            Utility.msgInf("GEOSTORE", "Il prodotto è disponibile\n");
            o.setProdotto(p);

            if(o.getQuantita() <= p.getQuantita_disp()){
                o.setPrezzo_unitario(p.getPrezzo());

                BigDecimal pagamento = o.getPrezzo_unitario().multiply(BigDecimal.valueOf(o.getQuantita()));
                BigDecimal denaro = new BigDecimal(0);

                if(u instanceof Amministratore){
                    Amministratore aDenaro = (Amministratore) u;
                    denaro = aDenaro.getPortafoglio();
                }
                else{
                    Cliente cDenaro = (Cliente) u;
                    denaro = cDenaro.getPortafoglio();
                }

                if(pagamento.compareTo(denaro) <= 0){

                    denaro = denaro.subtract(pagamento);

                    if(u instanceof Amministratore){
                        Amministratore aDenaro = (Amministratore) u;
                        aDenaro.setPortafoglio(denaro);
                        u = aDenaro;
                    }
                    else{
                        Cliente cDenaro = (Cliente) u;
                        cDenaro.setPortafoglio(denaro);
                        u = cDenaro;
                    }

                    int num = ur.updateWalletUtente(u.getId(), u);

                    if(num > 0){
                        Utility.msgInf("GEOSTORE", "T - Pagamento riuscito\n");
                        canOrder = "T - Pagamento riuscito".toUpperCase();
                    }
                    else{
                        Utility.msgInf("GEOSTORE", "F - Pagamento non riuscito\n");
                        canOrder = "F - Pagamento non riuscito".toUpperCase();
                    }

                }
                else{
                    Utility.msgInf("GEOSTORE", "F - Non hai abbastanza denaro\n");
                    canOrder = "F - Non hai abbastanza denaro".toUpperCase();
                }
            }
            else{
                Utility.msgInf("GEOSTORE", "F - La quantità ordinata supera quella disponibile\n");
                canOrder = "F - La quantità ordinata supera quella disponibile".toUpperCase();
            }
        }
        else{
            Utility.msgInf("GEOSTORE", "F - L'oggetto ordinato non è disponibile oppure è inesistente\n");
            canOrder = "F - L'oggetto ordinato non è disponibile oppure è inesistente".toUpperCase();
        }
        return canOrder;
    }

    private String checkOrderChanged(Ordine oOld, Ordine oNew, Utente u){
        UtenteRepository ur = new UtenteRepository();
        BigDecimal pagamentoNuovo = new BigDecimal(0);
        BigDecimal pagamentoVecchio = new BigDecimal(0);
        BigDecimal pagamentoDecisivo = new BigDecimal(0);
        BigDecimal denaro;
        String choose = "", response = "";

        if(!Objects.equals(oOld.getQuantita(), oNew.getQuantita())){
            Utility.msgInf("GEOSTORE", "è stata modificata la quantità ordinata\n");

            if(oOld.getQuantita() < oNew.getQuantita()) {

                Prodotto p = pr.getProdottoDispWithDB(oNew.getProdotto().getId());
                if(oNew.getQuantita() > p.getQuantita_disp()){
                    choose = "N";
                }
                else{
                    Utility.msgInf("GEOSTORE", "Procediamo con il pagamento del denaro richiesto\n");

                    pagamentoNuovo = oNew.getPrezzo_unitario().multiply(BigDecimal.valueOf(oNew.getQuantita()));
                    pagamentoVecchio = oOld.getPrezzo_unitario().multiply(BigDecimal.valueOf(oOld.getQuantita()));

                    pagamentoDecisivo = pagamentoNuovo.subtract(pagamentoVecchio);
                    denaro = new BigDecimal(0);

                    choose = "S";
                }
            }
            else if(oOld.getQuantita() > oNew.getQuantita()) {
                Utility.msgInf("GEOSTORE", "Procediamo con il rimborso del denaro in eccesso\n");

                pagamentoNuovo = oNew.getPrezzo_unitario().multiply(BigDecimal.valueOf(oNew.getQuantita()));
                pagamentoVecchio = oOld.getPrezzo_unitario().multiply(BigDecimal.valueOf(oOld.getQuantita()));

                pagamentoDecisivo = pagamentoVecchio.subtract(pagamentoNuovo);
                denaro = new BigDecimal(0);

                choose = "A";
            }

            if (u instanceof Amministratore) {
                Amministratore aDenaro = (Amministratore) u;
                denaro = aDenaro.getPortafoglio();
            } else {
                Cliente cDenaro = (Cliente) u;
                denaro = cDenaro.getPortafoglio();
            }



            if(choose.equals("S")){
                if (pagamentoDecisivo.compareTo(denaro) <= 0) {

                    denaro = denaro.subtract(pagamentoDecisivo);

                    if (u instanceof Amministratore) {
                        Amministratore aDenaro = (Amministratore) u;
                        aDenaro.setPortafoglio(denaro);
                        u = aDenaro;
                    } else {
                        Cliente cDenaro = (Cliente) u;
                        cDenaro.setPortafoglio(denaro);
                        u = cDenaro;
                    }

                    int num = ur.updateWalletUtente(u.getId(), u);

                    if(num > 0){
                        Utility.msgInf("GEOSTORE", "Pagamento riuscito\n");
                        response = "T - Pagamento riuscito. Ordine modificato".toUpperCase();
                    }
                    else{
                        Utility.msgInf("GEOSTORE", "Pagamento non riuscito\n");
                        response = "F - Pagamento non riuscito. Modifica ordine".toUpperCase();
                    }
                }
                else{
                    Utility.msgInf("GEOSTORE", "Denaro insufficiente\n");
                    response = "F - Denaro insufficiente. Modifica ordine".toUpperCase();
                }
            }
            else if(choose.equals("A")){
                denaro = denaro.add(pagamentoDecisivo);

                if (u instanceof Amministratore) {
                    Amministratore aDenaro = (Amministratore) u;
                    aDenaro.setPortafoglio(denaro);
                    u = aDenaro;
                } else {
                    Cliente cDenaro = (Cliente) u;
                    cDenaro.setPortafoglio(denaro);
                    u = cDenaro;
                }

                int num = ur.updateWalletUtente(u.getId(), u);

                if(num > 0){
                    Utility.msgInf("GEOSTORE", "Rimborso riuscito\n");
                    response = "T - Rimborso riuscito. Ordine modificato".toUpperCase();
                }
                else{
                    Utility.msgInf("GEOSTORE", "Rimborso non riuscito\n");
                    response = "F - Rimborso non riuscito. Modifica ordine".toUpperCase();
                }
            }
            else if(choose.equals("N")){
                response = "F - La quantita ordinata supera quella disponibile. Modifica ordine".toUpperCase();
            }
        }
        else{
            response = "T - Nessun cambiamento. Ordine modificato".toUpperCase();
        }
        return response;
    }

    public void changeStatusProdottoAfterOrder(Ordine oOld, Ordine oNew){
        OrdineRepository or = new OrdineRepository();
        if(oOld.getStato().getId() == 1 && oNew.getStato().getId() == 2){
            ProdottoRepository pr = new ProdottoRepository();

            Integer subQuantita = oOld.getProdotto().getQuantita_disp() - oNew.getQuantita();
            Integer newDisp;

            if(subQuantita == 0){
                newDisp = 4;
            }
            else if(subQuantita >= 1 && subQuantita <=3){
                newDisp = 3;
            }
            else{
                newDisp = 1;
            }

            int num = pr.updateProdottoAfterAccOrdineWithDB(oOld.getProdotto().getId(), newDisp, subQuantita);

            if(num > 0){
                Utility.msgInf("GEOSTORE", "Quantità e/o disponibilità aggiornati\n");
            }
            else{
                Utility.msgInf("GEOSTORE", "Quantità e/o disponibilità non aggiornati\n");
            }
        }
        else if(oOld.getStato().getId() == 1 && oNew.getStato().getId() == 3){
            Utente u = oOld.getUtente();

            refundAfterDeleteOrder(oOld, u);
        }
    }

    public void eliminazioneOrdine(String IDkey, Cliente user){
        Ordine order = or.getOrdineWithDB(Integer.parseInt(IDkey));

        if(order.getStato().getId() == 1) {
            //solo l'ordine con stato ELABORAZIONE si può effettuare il rimborso
            Utente uOrd = order.getUtente();
            refundAfterDeleteOrder(order, uOrd);

            int num = or.deleteOrdineWithDB(order.getId());
            if (num > 0) {
                Utility.sendResponseDeletedOrders(num, user);
            } else {
                Utility.sendResponseDeletedOrders(num, user);
            }
        }
        else{
            Utility.sendResponse(0, "Non puoi eliminare l'ordine perchè lo stato è in " + order.getStato().getCode() + ". Eliminazione ordine".toUpperCase(), user);
        }
    }

    public void creazioneCategoria(Categoria category, Cliente user){
        int num = 0;

        num = cr.checkDuplicatesCategoria(category);

        if(num == 0){

            num = cr.insertCategoriaWithDB(category.getId(), category);

            if(num > 0){
                //notizia per la creazione categoria
                News notiziaCreazione = new News();
                notiziaCreazione.setUtente(user);
                notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
                notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
                notiziaCreazione.setTesto("È stata allestita una nuova categoria: " + category.getNome() + ". Presto i prodotti di questa categoria saranno disponibili su Geostore");
                this.creazioneNotiziaSenzaRisposta(notiziaCreazione);

                Utility.sendResponse(num, "CATEGORIA AGGIUNTA", user);
            }
            else{
                Utility.sendResponse(num, "CREAZIONE CATEGORIA", user);
            }
        }
        else{
            Utility.sendResponse(0, "LA CATEGORIA GIÀ ESISTE. CREAZIONE CATEGORIA", user);
        }


    }

    public void modificaCategoria(Categoria category, Cliente user){
        int num = 0;

        num = cr.checkDuplicatesCategoria(category);

        if(num == 0){
            num = cr.updateCategoriaWithDB(category.getId(), category);

            if(num > 0){
                Utility.sendResponse(num, "CATEGORIA MODIFICATA", user);
            }
            else{
                Utility.sendResponse(num, "MODIFICA CATEGORIA", user);
            }
        }
        else{
            Utility.sendResponse(0, "LA CATEGORIA GIÀ ESISTE. MODIFICA CATEGORIA", user);
        }

    }

    public void eliminazioneCategoria(String IDKey, Cliente user){
        Categoria category = cr.getCategoriaWithDB(Integer.parseInt(IDKey));

        int num = pr.updateIdAfterDeleteCategory(0, category.getId());
        if(num > 0){
            Utility.msgInf("GEOSTORE", "Prodotti aggiornati\n");
        }
        else{
            Utility.msgInf("GEOSTORE", "Prodotti non aggiornati\n");
        }

        num = cr.deleteCategoriaWithDB(category.getId());

        if(num > 0){
            //notizia per l'eliminazione categoria
            News notiziaCreazione = new News();
            notiziaCreazione.setUtente(user);
            notiziaCreazione.setDataPub(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setDataMod(Date.valueOf(LocalDate.now()));
            notiziaCreazione.setTesto("È stata dismessa la categoria: " + category.getNome() + ". I prodotti appartenenti a questa categoria sono stati spostati in N/A");
            this.creazioneNotiziaSenzaRisposta(notiziaCreazione);

            Utility.sendResponseDeletedCategories(num, user);
        }
        else{
            Utility.sendResponseDeletedCategories(num, user);
        }
    }

    public HashMap<Integer, Categoria> ottieniCategorie() {
        return cr.getCategorieWithDB();
    }

    public Categoria ottieniCategoria(Integer idCategoria) {
        return cr.getCategoriaWithDB(idCategoria);
    }

    public void refundAfterDeleteOrder(Ordine o, Utente u){
        BigDecimal pagamento = o.getPrezzo_unitario().multiply(BigDecimal.valueOf(o.getQuantita()));

        if(u instanceof Amministratore){
            Amministratore aDenaro = (Amministratore) u;
            aDenaro.setPortafoglio(aDenaro.getPortafoglio().add(pagamento));
            u = aDenaro;
        }
        else{
            Cliente cDenaro = (Cliente) u;
            cDenaro.setPortafoglio(cDenaro.getPortafoglio().add(pagamento));
            u = cDenaro;
        }

        int num = ur.updateWalletUtente(u.getId(), u);

        if(num > 0){
            Utility.msgInf("GEOSTORE", "Denaro rimborsato\n");
        }
        else{
            Utility.msgInf("GEOSTORE", "Denaro non rimborsato\n");
        }
    }

    public Ordine ordiniTotaliGiornalieri(Utente u, String chooseDate){
        return or.getOrdineTotGiorWithDB(u, chooseDate);
    }

    public Map<Integer, Prodotto> prodottiViaCategoria(String IDCategoryKey){
        return pr.getProdottiViaCategoriaWithDB(Integer.parseInt(IDCategoryKey));
    }

    public Map<Integer, Prodotto> prodottiViaCategoriaByKeyword(String IDCategoryKey, String keyword){
        return pr.getProdottiViaCategoriaByKeywordWithDB(Integer.parseInt(IDCategoryKey), keyword);
    }

    public void creazioneMateria(Materia material, Cliente user){
        int num = 0;

        num = mr.checkDuplicatesMateria(material);

        if(num == 0){
            num = mr.insertMateriaWithDB(material.getId(), material);

            if(num > 0){
                Utility.sendResponse(num, "MATERIA AGGIUNTA", user);
            }
            else{
                Utility.sendResponse(num, "CREAZIONE MATERIA", user);
            }
        }
        else{
            Utility.sendResponse(0, "LA MATERIA GIÀ ESISTE. CREAZIONE MATERIA", user);
        }

    }

    public void modificaMateria(Materia material, Cliente user){
        int num = 0;

        num = mr.checkDuplicatesMateria(material);

        if(num == 0){
            num = mr.updateMateriaWithDB(material.getId(), material);

            if(num > 0){
                Utility.sendResponse(num, "MATERIA MODIFICATA", user);
            }
            else{
                Utility.sendResponse(num, "MODIFICA MATERIA", user);
            }
        }
        else{
            Utility.sendResponse(0, "LA MATERIA GIÀ ESISTE. MODIFICA MATERIA", user);
        }

    }

    public void eliminazioneMateria(String IDKey, Cliente user){
        Materia material = mr.getMateriaWithDB(Integer.parseInt(IDKey));

        int num = pr.updateIdAfterDeleteMaterial(0, material.getId());
        if(num > 0){
            Utility.msgInf("GEOSTORE", "Prodotti aggiornati\n");
        }
        else{
            Utility.msgInf("GEOSTORE", "Prodotti non aggiornati\n");
        }

        num = mr.deleteMateriaWithDB(material.getId());

        if(num > 0){
            Utility.sendResponseDeletedMaterials(num, user);
        }
        else{
            Utility.sendResponseDeletedMaterials(num, user);
        }
    }

    public HashMap<Integer, Materia> ottieniMaterie(){
        return mr.getMaterieWithDB();
    }

    public Materia ottieniMateria(Integer idMateria){
        return mr.getMateriaWithDB(idMateria);
    }

    public HashMap<Integer, Prodotto> prodottiViaMateria(String IDMaterialKey){
        return pr.getProdottiViaMateriaWithDB(Integer.parseInt(IDMaterialKey));
    }

    public Map<Integer, Prodotto> prodottiViaMateriaByKeyword(String IDMaterialKey, String keyword){
        return pr.getProdottiViaMateriaByKeywordWithDB(Integer.parseInt(IDMaterialKey), keyword);
    }

    public HashMap<Integer, Disponibilita> ottieniDisponibilita(){
        return dr.getDisponibilitaWithDB();
    }

    public HashMap<Integer, Stato> ottieniStato(){
        return sr.getStatusWithDB();
    }
}
