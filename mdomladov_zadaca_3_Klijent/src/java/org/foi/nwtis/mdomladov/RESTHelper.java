/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * Pomoćna klasa za manipulaciju s REST-om
 * 
 * @author Marko Domladovac
 */
public class RESTHelper {

    private static final String GM_BASE_URI = "http://localhost:29000/mdomladov_zadaca_3_1/webresources/";

    private static final String METEO_RESOURCE = "meteoREST";

    /**
     *
     */
    public RESTHelper() {
    }

    /**
     *
     * @return
     */
    public static String getGM_BASE_URI() {
        return GM_BASE_URI;
    }

    /**
     *
     * @return
     */
    public static String getMETEO_RESOURCE() {
        return METEO_RESOURCE;
    }

    /**
     *
     * @param requestUrl
     * @param payload
     * @return
     */
    public static String sendPostRequest(String requestUrl, String payload) {
        StringBuilder jsonString;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8")) {
                writer.write(payload);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                jsonString = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    jsonString.append(line);
                }
            }
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return jsonString.toString();
    }
}
