/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.foi.nwtis.mdomladov.ws.klijenti.MeteoPodaci;

/**
 *
 * REST klijent
 * Spajanje s rest klijentom (prva aplikacija na Tomcatu)
 * 
 * @author Marko Domladovac
 */
public class RESTKlijent {

    /**
     *
     * @param naziv
     * @param adresa
     * @return
     */
    public static boolean addUredjaj(String naziv, String adresa) {
        try {
            JsonObjectBuilder inputJsonObj = Json.createObjectBuilder();
            inputJsonObj.add("adresa", adresa);
            inputJsonObj.add("naziv", naziv);

            String url = RESTHelper.getGM_BASE_URI() + RESTHelper.getMETEO_RESOURCE();

            RESTHelper.sendPostRequest(url, inputJsonObj.build().toString());

            return true;
        } catch (Exception ex) {
            Logger.getLogger(RESTKlijent.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     *
     * @param odabraniUredjajId
     * @return
     */
    public static MeteoPodaci dajZadnjeMeteoPodatkeZaUredjaj(int odabraniUredjajId) {
        MeteoPodaci meteo = null;
        try {
            meteo = new MeteoPodaci();
            Client client = ClientBuilder.newClient();
            WebTarget webResource = client.target(RESTHelper.getGM_BASE_URI())
                    .path(RESTHelper.getMETEO_RESOURCE())
                    .path(String.valueOf(odabraniUredjajId));

            String odgovor = webResource.request(MediaType.APPLICATION_JSON).get(String.class);

            JsonReader reader = Json.createReader(new StringReader(odgovor));

            JsonObject jo = reader.readObject();

            meteo.setNaziv(jo.getJsonString("naziv").getString());
            meteo.setHumidityValue((float) jo.getJsonNumber("humidityValue").doubleValue());
            meteo.setWeatherValue(jo.getJsonString("weatherValue").getString());
            meteo.setWeatherIcon(jo.getJsonString("weatherIcon").getString());
            meteo.setPressureValue((float) jo.getJsonNumber("pressureValue").doubleValue());
            meteo.setTemperatureValue((float) jo.getJsonNumber("temperatureValue").doubleValue());
            meteo.setTemperatureMax((float) jo.getJsonNumber("temperatureMax").doubleValue());
            meteo.setTemperatureMin((float) jo.getJsonNumber("temperatureMin").doubleValue());
            meteo.setWindSpeedValue((float) jo.getJsonNumber("windSpeedValue").doubleValue());
            meteo.setWindDirectionValue((float) jo.getJsonNumber("windDirectionValue").doubleValue());
            GregorianCalendar c = new GregorianCalendar();
            long time = jo.getJsonNumber("lastUpdate").longValue();
            c.setTime(new Date(time));
            XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            meteo.setLastUpdate(date);

        } catch (Exception ex) {
            Logger.getLogger(RESTKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meteo;
    }
}
