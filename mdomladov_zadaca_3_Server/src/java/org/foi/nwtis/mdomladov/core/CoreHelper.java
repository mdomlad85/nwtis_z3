/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.core;

import javax.servlet.ServletContext;
import org.foi.nwtis.mdomladov.konfiguracije.APP_Konfiguracija;
import org.foi.nwtis.mdomladov.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mdomladov.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.mdomladov.web.slusaci.SlusacAplikacije;

/**
 * Osnovne stvari koje koriste svi helperi idu u ovu klase
 *
 * @author Marko Domladovac
 */
public class CoreHelper extends Thread {

    /**
     *
     * konfiguracija
     */
    protected static APP_Konfiguracija konfiguracija;
    
    /**
     *
     * Konstruktor koji prima objekt konfiguracije
     * @param konfig
     */
    public CoreHelper(APP_Konfiguracija konfig) {
        konfiguracija = konfig;
    }

    /**
     *
     * Konstruktor kojemu se predaje putanja od konfiguracije
     *
     * @param filePath
     * @throws NemaKonfiguracije
     * @throws NeispravnaKonfiguracija
     */
    public CoreHelper(String filePath) throws NemaKonfiguracije, NeispravnaKonfiguracija {
        konfiguracija = new APP_Konfiguracija(filePath);
    }
}
