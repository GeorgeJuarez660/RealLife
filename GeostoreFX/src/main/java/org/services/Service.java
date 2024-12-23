package org.services;

import org.models.*;
import org.utility.Utility;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Service {

    ProdottoRepository pr = new ProdottoRepository();
    UtenteRepository ur = new UtenteRepository();
    NewsRepository nr = new NewsRepository();
    CategoriaRepository car = new CategoriaRepository();
    MateriaRepository mr = new MateriaRepository();
    OrdineRepository odr = new OrdineRepository();
    DisponibilitaRepository dr = new DisponibilitaRepository();
    StatusRepository sr = new StatusRepository();


    public Utente ottieniProfiloUtente(Integer idUtente){
        return ur.getUtenteWithDB(idUtente);
    }

    public Map<Integer, Utente> ottieniUtente(Integer idUtente){
        Utente utente = ur.getUtenteWithDB(idUtente);
        Map<Integer, Utente> onlyUtente = new HashMap<>();
        onlyUtente.put(utente.getId(), utente);
        return onlyUtente;
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
        int num = ur.insertUtenteWithDB(user.getId(), user);
        Utility.sendResponseRegister(num);
    }

    public void creazioneUtente(Cliente user, Cliente userID){
        int num = 0;

        num = ur.insertUtenteWithDB(null, user);

        if(num > 0){
            Utility.sendResponse(num, "UTENTE CREATO", userID);
        }
        else{
            Utility.sendResponse(num, "CREAZIONE UTENTE", userID);
        }
    }

    public void modificaUtente(Utente u, Cliente userID){
        int num = ur.updateUtenteWithDB(u.getId(), u);

        if(num > 0){
            Utility.sendResponse(num, "UTENTE MODIFICATO", userID);
        }
        else{
            Utility.sendResponse(num, "MODIFICA UTENTE", userID);
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

    /*public void creazioneProdotto(){
        Prodotto p = new Prodotto();
        boolean flagInsert;
        view.maskInsertProdotto(p);

        Categoria cat = car.getCategoriaWithDB(p.getCategoria().getId());
        Materia m = mr.getMateriaWithDB(p.getMateria().getId());
        Disponibilita d = dr.getDisponibilitaWithDB(p.getDisponibilita().getId());

        if(cat != null && m != null && d != null && cat.getNome() != null && m.getNome() != null && d.getCode() != null){
            p.setCategoria(cat);
            p.setMateria(m);
            p.setDisponibilita(d);

            do{
                String question = Utility.insertString("Vuoi procedere? (s/n)");
                if(question.equalsIgnoreCase("s")) {
                    int num = pr.insertProdottoWithDB(p.getId(), p);

                    if(num > 0){
                        Utility.msgInf("GEOSTORE", "Nuovo prodotto aggiunto\n");
                    }
                    else{
                        Utility.msgInf("GEOSTORE", "Prodotto non aggiunto\n");
                    }

                    flagInsert = false;
                }else if(question.equalsIgnoreCase("n")){
                    Utility.msgInf("GEOSTORE", "Operazione annullata\n");
                    flagInsert = false;
                }
                else{
                    Utility.msgInf("GEOSTORE", "Rileggi la domanda\n");
                    flagInsert = true;
                }
            }while(flagInsert);


        }
        else{
            Utility.msgInf("GEOSTORE", "Categoria e/o Materia inesistenti\n");
        }
    }*/

    /*public void modificaProdotto(){
        view.printProdotti(pr.getProdottiWithDB());
        Prodotto p = pr.getProdottoWithDB(Utility.insertInt("Inserisci l'id prodotto"));

        if(p != null && p.getNome() != null){
            Utility.msgInf("GEOSTORE", "Prodotto trovato\n");

            Prodotto pNew = view.maskUpdateProdotto(p, new Prodotto());

            Categoria cat = car.getCategoriaWithDB(pNew.getCategoria().getId());
            Materia m = mr.getMateriaWithDB(pNew.getMateria().getId());
            Disponibilita d = dr.getDisponibilitaWithDB(pNew.getDisponibilita().getId());

            if(cat != null && m != null && d != null && cat.getNome() != null && m.getNome() != null && d.getCode() != null){
                pNew.setCategoria(cat);
                pNew.setMateria(m);
                pNew.setDisponibilita(d);
                int num = pr.updateProdottoWithDB(pNew.getId(), pNew);

                if(num > 0){
                    Utility.msgInf("GEOSTORE", "Prodotto aggiornato\n");
                }
                else{
                    Utility.msgInf("GEOSTORE", "Prodotto non aggiornato\n");
                }
            }
            else{
                Utility.msgInf("GEOSTORE", "Categoria e/o Materia inesistenti\n");
            }

        }
        else{
            Utility.msgInf("GEOSTORE", "Prodotto non trovato\n");
        }
    }*/

    /*public void eliminazioneProdotto(){
        view.printProdotti(pr.getProdottiWithDB());
        Prodotto p = pr.getProdottoWithDB(Utility.insertInt("Inserisci l'id prodotto"));

        if(p != null && p.getNome() != null){
            Utility.msgInf("GEOSTORE", "Prodotto trovato\n");
            if(Utility.insertString("Sei sicuro di voler eliminare questo prodotto?").equalsIgnoreCase("s")){

                HashMap<Integer, Ordine> ordini = odr.getOrdiniByProductWithDB(p.getId());

                for(Ordine ordine : ordini.values()){
                    //il prodotto è più cruciale rispetto all'ordine, motivo per cui vengono rimborsati anche se gli ordini sono in stato divrso da RIFIUTATO
                    if(ordine.getStato().getId() != 3 && ordine.getStato().getId() != 5){
                        refundAfterDeleteOrder(ordine, ordine.getUtente());
                    }
                    else{
                        Utility.msgInf("GEOSTORE", "L'ordine è già stato rifiutato oppure consegnato\n");
                    }
                }

                int num = odr.deleteOrdineAfterDeleteProduct(p.getId());

                if(num > 0){
                    Utility.msgInf("GEOSTORE", "Ordini eliminati\n");
                }
                else{
                    Utility.msgInf("GEOSTORE", "Ordini non eliminati\n");
                }

                num = 0;
                num = pr.deleteProdottoWithDB(p.getId());

                if(num > 0){
                    Utility.msgInf("GEOSTORE", "Prodotto eliminato\n");
                }
                else{
                    Utility.msgInf("GEOSTORE", "Prodotto non eliminato\n");
                }

            }
            else{
                Utility.msgInf("GEOSTORE", "Operazione annullata\n");
            }
        }
        else{
            Utility.msgInf("GEOSTORE", "Prodotto non trovato\n");
        }
    }*/

    /*public void ordinazioneProdotto(Utente u){
        Ordine o;
        view.printProdotti(pr.getProdottiDispWithDB());
        o = new Ordine();
        view.maskInsertOrdine(o, u);

        boolean flagInsert;
        do{
            String question = Utility.insertString("Vuoi procedere? (s/n)");
            if(question.equalsIgnoreCase("s")) {

                boolean canOrder = checkAmountOrderAndSufficientWallet(o, u);

                if(canOrder) {
                    int num = odr.insertOrdineWithDB(null, o);
                    if(num > 0){
                        Utility.msgInf("GEOSTORE", "Ordine effettuato\n");
                    }
                    else{
                        Utility.msgInf("GEOSTORE", "Ordine non effettuato\n");
                    }
                }

                flagInsert = false;
            }else if(question.equalsIgnoreCase("n")){
                Utility.msgInf("GEOSTORE", "Operazione annullata\n");
                flagInsert = false;
            }
            else{
                Utility.msgInf("GEOSTORE", "Rileggi la domanda\n");
                flagInsert = true;
            }
        }while(flagInsert);
    }*/

    /*public void modificaOrdine(){
        view.printOrdini(odr.getOrdiniWithDB());
        Ordine o = odr.getOrdineWithDB(Utility.insertInt("Inserisci l'id ordine"));

        if(o != null && o.getProdotto() != null && o.getProdotto().getNome() != null){
            Utility.msgInf("GEOSTORE", "Ordine trovato\n");

            Ordine oNew = view.maskUpdateOrdine(o, new Ordine());
            //TODO: inserire il check
            Stato s = sr.getStatoWithDB(oNew.getStato().getId());

            if(s != null && s.getCode() != null){
                Utente u = ur.getUtenteWithDB(o.getUtente().getId());
                checkOrderChanged(o, oNew, u);

                changeStatusProdottoAfterOrder(o, oNew);

                int num = odr.updateOrdineWithDB(oNew.getId(), oNew);

                if(num > 0){
                    Utility.msgInf("GEOSTORE", "Ordine aggiornato\n");
                }
                else{
                    Utility.msgInf("GEOSTORE", "Ordine non aggiornato\n");
                }
            }
        }
        else{
            Utility.msgInf("GEOSTORE", "Ordine non trovato\n");
        }
    }*/

    /*private static boolean checkAmountOrderAndSufficientWallet(Ordine o, Utente u){
        boolean canOrder = true;
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
                        Utility.msgInf("GEOSTORE", "Pagamento riuscito\n");
                    }
                    else{
                        Utility.msgInf("GEOSTORE", "Pagamento non riuscito\n");
                    }

                }
                else{
                    Utility.msgInf("GEOSTORE", "Non hai abbastanza denaro\n");
                    canOrder = false;
                }
            }
            else{
                Utility.msgInf("GEOSTORE", "La quantità ordinata supera quella disponibile\n");
                canOrder = false;
            }
        }
        else{
            Utility.msgInf("GEOSTORE", "L'oggetto ordinato non è disponibile oppure è inesistente\n");
            canOrder = false;
        }
        return canOrder;
    }*/

    /*private static void checkOrderChanged(Ordine oOld, Ordine oNew, Utente u){
        UtenteRepository ur = new UtenteRepository();
        BigDecimal pagamentoNuovo = new BigDecimal(0);
        BigDecimal pagamentoVecchio = new BigDecimal(0);
        BigDecimal pagamentoDecisivo = new BigDecimal(0);
        BigDecimal denaro;
        String choose = "";

        if(!Objects.equals(oOld.getQuantita(), oNew.getQuantita())){
            Utility.msgInf("GEOSTORE", "è stata modificata la quantità ordinata\n");

            if(oOld.getQuantita() < oNew.getQuantita()) {
                Utility.msgInf("GEOSTORE", "Procediamo con il pagamento del denaro richiesto\n");

                pagamentoNuovo = oNew.getPrezzo_unitario().multiply(BigDecimal.valueOf(oNew.getQuantita()));
                pagamentoVecchio = oOld.getPrezzo_unitario().multiply(BigDecimal.valueOf(oOld.getQuantita()));

                pagamentoDecisivo = pagamentoNuovo.subtract(pagamentoVecchio);
                denaro = new BigDecimal(0);

                choose = "S";
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
                    }
                    else{
                        Utility.msgInf("GEOSTORE", "Pagamento non riuscito\n");
                    }
                }
                else{
                    Utility.msgInf("GEOSTORE", "Denaro insufficiente\n");
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
                }
                else{
                    Utility.msgInf("GEOSTORE", "Rimborso non riuscito\n");
                }
            }
        }
    }*/

    /*public void changeStatusProdottoAfterOrder(Ordine oOld, Ordine oNew){
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
    }*/

    /*public void eliminazioneOrdine(Utente u, boolean onlyOwnUser){
        Ordine o;
        if(onlyOwnUser){
            view.printOrdini(odr.getOrdiniByUserWithDB(u.getId()));
            o = odr.getOrdineByUserWithDB(u.getId(), Utility.insertInt("Inserisci l'id ordine"));

        }
        else{
            view.printOrdini(odr.getOrdiniWithDB());
            o = odr.getOrdineWithDB(Utility.insertInt("Inserisci l'id ordine"));
        }

        if(o != null && o.getProdotto() != null && o.getProdotto().getNome() != null){
            Utility.msgInf("GEOSTORE", "Ordine trovato\n");
            if(Utility.insertString("Sei sicuro di voler eliminare quest'ordine?").equalsIgnoreCase("s")){

                if(o.getStato().getId() == 1 || (!onlyOwnUser && (o.getStato().getId() != 3 && o.getStato().getId() != 5))){
                    //solo l'ordine con stato ELABORAZIONE si può effettuare il rimborso
                    Utente uOrd = o.getUtente();
                    refundAfterDeleteOrder(o, uOrd);

                    int num = odr.deleteOrdineWithDB(o.getId());
                    if(num > 0){
                        Utility.msgInf("GEOSTORE", "Ordine eliminato\n");
                    }
                    else{
                        Utility.msgInf("GEOSTORE", "Ordine non eliminato\n");
                    }
                }
                else{
                    Utility.msgInf("GEOSTORE", "Non puoi eliminare l'ordine perchè lo stato è in " + o.getStato().getCode());
                }

            }
            else{
                Utility.msgInf("GEOSTORE", "Operazione annullata\n");
            }
        }
        else{
            Utility.msgInf("GEOSTORE", "Ordine non trovato\n");
        }
    }*/

    /*public void creazioneCategoria(){
        Categoria cat = new Categoria();
        view.maskInsertCategoria(cat);
        boolean flagInsert;
        do{
            String question = Utility.insertString("Vuoi procedere? (s/n)");
            if(question.equalsIgnoreCase("s")) {
                int num = car.insertCategoriaWithDB(cat.getId(), cat);

                if (num > 0) {
                    Utility.msgInf("GEOSTORE", "Nuova categoria aggiunta\n");
                } else {
                    Utility.msgInf("GEOSTORE", "Categoria non aggiunta\n");
                }

                flagInsert = false;
            }else if(question.equalsIgnoreCase("n")){
                Utility.msgInf("GEOSTORE", "Operazione annullata\n");
                flagInsert = false;
            }
            else{
                Utility.msgInf("GEOSTORE", "Rileggi la domanda\n");
                flagInsert = true;
            }
        }while(flagInsert);

    }*/

    /*public void modificaCategoria(){
        view.printCategorie(car.getCategorie());
        Categoria cat = car.getCategoriaWithDB(Utility.insertInt("Inserisci l'id categoria"));

        if(cat != null && cat.getNome() != null){
            Utility.msgInf("GEOSTORE", "Categoria trovata\n");

            Categoria cNew = view.maskUpdateCategoria(cat, new Categoria());

            int num = car.updateCategoriaWithDB(cNew.getId(), cNew);

            if(num > 0){
                Utility.msgInf("GEOSTORE", "Categoria aggiornata\n");
            }
            else{
                Utility.msgInf("GEOSTORE", "Categoria non aggiornata\n");
            }

        }
        else{
            Utility.msgInf("GEOSTORE", "Categoria non trovata\n");
        }
    }*/

    /*public void eliminazioneCategoria(){
        view.printCategorie(car.getCategorieWithDB());
        Categoria cat = car.getCategoriaWithDB(Utility.insertInt("Inserisci l'id categoria"));

        if(cat != null && cat.getNome() != null){
            Utility.msgInf("GEOSTORE", "Categoria trovata\n");
            if(Utility.insertString("Sei sicuro di voler eliminare questa categoria?").equalsIgnoreCase("s")){

                int num = pr.updateIdAfterDeleteCategory(0, cat.getId());
                if(num > 0){
                    Utility.msgInf("GEOSTORE", "Prodotti aggiornati\n");
                }
                else{
                    Utility.msgInf("GEOSTORE", "Prodotti non aggiornati\n");
                }

                num = 0;
                num = car.deleteCategoriaWithDB(cat.getId());
                if(num > 0){
                    Utility.msgInf("GEOSTORE", "Categoria eliminata\n");
                }
                else{
                    Utility.msgInf("GEOSTORE", "Categoria non eliminata\n");
                }
            }
            else{
                Utility.msgInf("GEOSTORE", "Operazione annullata\n");
            }
        }
        else{
            Utility.msgInf("GEOSTORE", "Categoria non trovata\n");
        }
    }*/

    /*private void refundAfterDeleteOrder(Ordine o, Utente u){
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
    }*/

    /*public void prodottiViaCategoria(){
        view.printCategorie(car.getCategorieWithDB());
        Categoria cat = new Categoria();
        view.maskObjViaCat(cat);
        view.printProdotti(pr.getProdottiViaCategoriaWithDB(cat.getId()));
    }*/

    /*public void creazioneMateria(){
        Materia m = new Materia();
        view.maskInsertMateria(m);
        boolean flagInsert;
        do{
            String question = Utility.insertString("Vuoi procedere? (s/n)");
            if(question.equalsIgnoreCase("s")) {
                int num = mr.insertMateriaWithDB(m.getId(), m);


                if (num > 0) {
                    Utility.msgInf("GEOSTORE", "Nuova materia aggiunta\n");
                } else {
                    Utility.msgInf("GEOSTORE", "Materia non aggiunta\n");
                }

                flagInsert = false;
            }else if(question.equalsIgnoreCase("n")){
                Utility.msgInf("GEOSTORE", "Operazione annullata\n");
                flagInsert = false;
            }
            else{
                Utility.msgInf("GEOSTORE", "Rileggi la domanda\n");
                flagInsert = true;
            }
        }while(flagInsert);
    }*/

    /*public void modificaMateria(){
        view.printMaterie(mr.getMaterieWithDB());
        Materia m = mr.getMateriaWithDB(Utility.insertInt("Inserisci l'id materia"));

        if(m != null && m.getNome() != null){
            Utility.msgInf("GEOSTORE", "Materia trovata\n");

            Materia mNew = view.maskUpdateMateria(m, new Materia());

            int num = mr.updateMateriaWithDB(mNew.getId(), mNew);

            if(num > 0){
                Utility.msgInf("GEOSTORE", "Materia aggiornata\n");
            }
            else{
                Utility.msgInf("GEOSTORE", "Materia non aggiornata\n");
            }

        }
        else{
            Utility.msgInf("GEOSTORE", "Materia non trovata\n");
        }
    }*/

    /*public void eliminazioneMateria(){
        view.printMaterie(mr.getMaterieWithDB());
        Materia m = mr.getMateriaWithDB(Utility.insertInt("Inserisci l'id materia"));

        if(m != null && m.getNome() != null){
            Utility.msgInf("GEOSTORE", "Materia trovata\n");
            if(Utility.insertString("Sei sicuro di voler eliminare questa materia?").equalsIgnoreCase("s")){

                int num = pr.updateIdAfterDeleteMaterial(0, m.getId());
                if(num > 0){
                    Utility.msgInf("GEOSTORE", "Prodotti aggiornati\n");
                }
                else{
                    Utility.msgInf("GEOSTORE", "Prodotti non aggiornati\n");
                }

                num = 0;
                num = mr.deleteMateriaWithDB(m.getId());
                if(num > 0){
                    Utility.msgInf("GEOSTORE", "Materia eliminata\n");
                }
                else{
                    Utility.msgInf("GEOSTORE", "Materia non eliminata\n");
                }
            }
            else{
                Utility.msgInf("GEOSTORE", "Operazione annullata\n");
            }
        }
        else{
            Utility.msgInf("GEOSTORE", "Materia non trovata\n");
        }
    }*/

    /*public void prodottiViaMateria(){
        view.printMaterie(mr.getMaterieWithDB());
        Materia m = new Materia();
        view.maskObjViaMat(m);
        view.printProdotti(pr.getProdottiViaMateriaWithDB(m.getId()));
    }*/

    /*public void ordiniEffettuati(Utente u, boolean youCanChoose){
        int choose = 0;
        if(youCanChoose){
            choose = Utility.insertInt("1 - tuoi ordini, 2 - di un'altra persona, 3 - generale");
        }

        if(choose == 1 || !youCanChoose){
            Utility.msgInf("GEOSTORE", "Tuoi ordini\n");
            view.printOrdini(odr.getOrdiniByUserWithDB(u.getId()));
        }
        else if(choose == 2){
            Utility.msgInf("GEOSTORE", "Ordini di qualcun'altro\n");

            view.printUtenti(ur.getUtentiWithDB());
            Integer anotherUser = Utility.insertInt("Inserisci l'id di un'altra persona");
            view.printOrdini(odr.getOrdiniByUserWithDB(anotherUser));
        }
        else if(choose == 3){
            Utility.msgInf("GEOSTORE", "Ordini in generale\n");

            view.printOrdini(odr.getOrdiniWithDB());
        }
        else{
            Utility.msgInf("GEOSTORE", "Non so cosa hai inserito\n");
        }
    }*/

    /*public void ordiniTotaliGiornalieri(Utente u){
        String chooseDate = Utility.insertString("Inserisci la data in formato yyyy-mm-dd");
        view.printOrdiniTotGior(odr.getOrdineTotGiorWithDB(u, chooseDate));
    }*/
}
