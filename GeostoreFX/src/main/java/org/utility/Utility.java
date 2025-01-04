package org.utility;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import org.models.Cliente;
import org.models.Utente;
import org.services.LoadPage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Scanner;

public class Utility {

    static Scanner input = new Scanner(System.in);


    public static void msgInf(String owner, String text) //metodo di messaggio info
    {
        System.out.println(owner + ": " + text);
    }

    public static int insertInt(String value){
        System.out.println(value);
        int num = 0;
        boolean flag;

        do{ //controlla ripetutamente se è un numero
            flag = false;
            try{
                num = Integer.parseInt(value);
            }catch(NumberFormatException err){
                msgInf("GEOSTORE", "devi inserire un valore numerico");
                flag = true;
            }
            catch (Exception err){
                msgInf("GEOSTORE", "errore");
                flag = true;
            }
        }while(flag);

        return num;

    }

    public static BigDecimal insertBigDecimal(String value){
        System.out.println("Portafoglio iniziale: " + value);
        BigDecimal num = new BigDecimal(0);
        boolean flag;

        do{ //controlla ripetutamente se è un numero
            flag = false;
            try{
                num = new BigDecimal(value);
            }catch(NumberFormatException err){
                msgInf("GEOSTORE", "devi inserire un valore decimale");
                flag = true;
            }
            catch (Exception err){
                msgInf("GEOSTORE", "errore");
                flag = true;
            }
        }while(flag);

        return num;

    }

    public static String insertString(String value){
        System.out.println(value);
        String word = value;

        return word;

    }

    public static String formatValueBigDecimal(BigDecimal value){
        String formattedValue = "";

        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.ITALIAN);
        DecimalFormat df = new DecimalFormat("###,##0.##", dfs);
        formattedValue = df.format(value);

        return formattedValue;
    }

    public static BigDecimal formatValueString(String value){
        BigDecimal formattedValue = new BigDecimal(0);

        value = value.replace(",", ".");
        formattedValue = new BigDecimal(value);

        return formattedValue;
    }

    public static void sendResponseLogin(Integer num, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", "ACCESSO APPROVATO");
        }
        else{
            LoadPage.answerScene("negative", "ACCESSO FALLITO");
        }

        if(num>0){
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user);
            });
            delay.play();
        }
        else{
            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.getFullScene("prepage");
            });
            delay.play();
        }

    }

    public static void sendResponseRegister(Integer num){
        if(num > 0){
            LoadPage.answerScene("positive", "REGISTRATO CON SUCCESSO");
        }
        else{
            LoadPage.answerScene("negative", "REGISTRAZIONE FALLITA");
        }

        //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            // Dopo 2 secondi, carica la terza scena
            LoadPage.getFullScene("prepage");
        });
        delay.play();
    }

    public static void sendResponse(Integer num, String dynamicEvent, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", dynamicEvent + " CON SUCCESSO");
        }
        else{
            LoadPage.answerScene("negative", dynamicEvent + " FALLITA");
        }

        //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            // Dopo 2 secondi, carica la terza scena
            LoadPage.goesToMenu(user);
        });
        delay.play();
    }

    public static void sendResponseDeletedProducts(Integer num, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", "PRODOTTO ELIMINATO CON SUCCESSO");

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.answerScene("info", "LA RICORDIAMO CHE SONO STATI EFFETTUATI DEI RIMBORSI AGLI UTENTI CHE AVEVANO ORDINATO QUESTO PRODOTTO");
            });
            delay.play();

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay2 = new PauseTransition(Duration.seconds(9));
            delay2.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "ELIMINAZIONE PRODOTTO FALLITA");

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user);
            });
            delay.play();
        }
    }

    public static void sendResponseDeletedOrders(Integer num, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", "ORDINE ELIMINATO CON SUCCESSO");

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.answerScene("info", "LA RICORDIAMO CHE È STATO EFFETTUATO DEL RIMBORSO ALL'UTENTE CHE AVEVA ORDINATO QUESTO PRODOTTO");
            });
            delay.play();

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay2 = new PauseTransition(Duration.seconds(9));
            delay2.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "ELIMINAZIONE ORDINE FALLITA");

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user);
            });
            delay.play();
        }
    }

    public static void sendResponseOrderedProducts(Integer num, String response, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", response);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.answerScene("info", "LA RICORDIAMO CHE IL PAGAMENTO È STATO EFFETTUATO CON SUCCESSO");
            });
            delay.play();

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay2 = new PauseTransition(Duration.seconds(9));
            delay2.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", response);

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(6));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user);
            });
            delay.play();
        }
    }

    public static void sendResponseDeletedCategories(Integer num, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", "CATEGORIA ELIMINATA CON SUCCESSO");

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.answerScene("info", "LA RICORDIAMO CHE I PRODOTTI APPARTENENTI ALLA PRECEDENTE CATEGORIA SONO SPOSTATI SU N/A");
            });
            delay.play();

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay2 = new PauseTransition(Duration.seconds(9));
            delay2.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "ELIMINAZIONE CATEGORIA FALLITA");

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user);
            });
            delay.play();
        }
    }

    public static void sendResponseDeletedMaterials(Integer num, Cliente user){
        if(num > 0){
            LoadPage.answerScene("positive", "MATERIA ELIMINATA CON SUCCESSO");

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.answerScene("info", "LA RICORDIAMO CHE I PRODOTTI APPARTENENTI ALLA PRECEDENTE MATERIA SONO SPOSTATI SU N/A");
            });
            delay.play();

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay2 = new PauseTransition(Duration.seconds(9));
            delay2.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user);
            });
            delay2.play();
        }
        else{
            LoadPage.answerScene("negative", "ELIMINAZIONE MATERIA FALLITA");

            //PauseTransition serve per ritardare il caricamento della nuova scena, permettendo di mostrare temporaneamente la precedente (s-1)
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                // Dopo 2 secondi, carica la terza scena
                LoadPage.goesToMenu(user);
            });
            delay.play();
        }
    }

}
