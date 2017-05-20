/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.core;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import org.foi.nwtis.mdomladov.web.podaci.Lokacija;
import org.foi.nwtis.mdomladov.web.podaci.Uredjaj;

/**
 *
 * @author Zeus
 */
public class JsonHelper {

    private static JsonObjectBuilder jsonObj;

    public static <T> String createJsonArrayString(List<T> objekti) {
        JsonArrayBuilder objektiJson = Json.createArrayBuilder();

        objekti.forEach((t) -> {
            jsonObj = Json.createObjectBuilder();
            FillJsonObjekt(t);
            objektiJson.add(jsonObj);
        });

        return objektiJson.build().toString();
    }

    public static <T> String createJsonObjectString(T t) {
        jsonObj = Json.createObjectBuilder();
        FillJsonObjekt(t);

        return jsonObj.build().toString();
    }

    private static <T> void FillJsonObjekt(T t) {

        Class<?> c = t.getClass();
        Method[] ms = c.getDeclaredMethods();
        for (Method m : ms) {
            if (m.getName().startsWith("get")) {
                try {
                    Object val = m.invoke(t, null);
                    String name = String.format("%s%s", 
                            m.getName().substring(3,4).toLowerCase(), 
                            m.getName().substring(4));
                    if (val instanceof Integer) {
                        jsonObj.add(name, (int) val);
                    } else if (val instanceof Float) {
                        jsonObj.add(name, (float) val);
                    } else if (val instanceof Double) {
                        jsonObj.add(name, (double) val);
                    } else if (val instanceof Long) {
                        jsonObj.add(name, (long) val);
                    } else if (val instanceof String) {
                        jsonObj.add(name, val.toString());
                    } else if (val instanceof String) {
                        jsonObj.add(name, val.toString());
                    } else if (val instanceof Date) {
                        Date datum = (Date) val;
                        jsonObj.add(name, ((Date) val).getTime());
                    } else if (val instanceof Lokacija) {
                        Lokacija lokacija = (Lokacija) val;
                        JsonObjectBuilder jsonLokacijaObj = Json.createObjectBuilder();
                        jsonLokacijaObj.add("latitude", lokacija.getLatitude());
                        jsonLokacijaObj.add("longitude", lokacija.getLongitude());
                        jsonObj.add(m.getName().substring(3).toLowerCase(), jsonLokacijaObj);
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(JsonHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static Uredjaj parsirajUredjaj(String uredjajStr) {
        Uredjaj uredjaj = null;

        try {
            JsonReader reader = Json.createReader(new StringReader(uredjajStr));
            JsonObject jo = reader.readObject();
            uredjaj = new Uredjaj();
            uredjaj.setNaziv(jo.getString("naziv"));  
            Lokacija loc = new Lokacija();
            loc.setAdresa( jo.getJsonString("adresa").getString());
            uredjaj.setGeoloc(loc);
        } catch (Exception ex) {
            uredjaj = null;
        }

        return uredjaj;
    }

}
