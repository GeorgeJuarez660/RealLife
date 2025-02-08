package org.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Translater {

    public static String chosenLanguage = "it";


    public static void setLanguage(String language){
        chosenLanguage = language;
    }

    public static String getLanguage(){
        return chosenLanguage;
    }

    //prepara il link da eseguire
    public static String translate(String text, String fromLanguage, String toLanguage) throws IOException {
        String urlStr = "https://api.mymemory.translated.net/get?q=" + URLEncoder.encode(text, "UTF-8") +
                "&langpair=" + fromLanguage + "|" + toLanguage;

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine; //ottiene la risposta in json
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        String json = response.toString();
        int start = json.indexOf("\"translatedText\":\"") + 18;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    public static String detectLanguage(String text) throws IOException {
        String urlStr = "https://api.mymemory.translated.net/get?q=" + URLEncoder.encode(text, "UTF-8") +
                "&langpair=auto|en";  // "auto" rileva la lingua e "en" serve per la traduzione fittizia

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;

        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        String json = response.toString(); //ottiene la risposta in json
        int start = json.indexOf("\"source\":\"") + 10;
        int end = json.indexOf("\"", start);

        if (start != -1 && end != -1) {
            return json.substring(start, end);
        } else {
            return "Unknown";
        }
    }
}
