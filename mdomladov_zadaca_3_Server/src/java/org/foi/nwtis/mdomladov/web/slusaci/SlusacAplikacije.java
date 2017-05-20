/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.web.slusaci;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.mdomladov.dretve.PreuzmiMeteoPodatke;
import org.foi.nwtis.mdomladov.konfiguracije.APP_Konfiguracija;
import org.foi.nwtis.mdomladov.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mdomladov.konfiguracije.NemaKonfiguracije;

/**
 * Slušač aplikacije starta pozadinsku dretvu 
 * i stavlja konfiguraciju u aplikacijski kontekst
 *
 * @author Zeus
 */
@WebListener
public class SlusacAplikacije implements ServletContextListener {

    /**
     *
     */
    public static final String APP_KONFIG = "APP_Konfiguracija";
    
    private PreuzmiMeteoPodatke dretva;
    
    /**
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            
            ServletContext sc = sce.getServletContext();
            
            String filePath = sc.getRealPath("WEB-INF")
                    + File.separator + sc.getInitParameter("konfiguracija");

            APP_Konfiguracija bpKonfig = new APP_Konfiguracija(filePath);
            sc.setAttribute(APP_KONFIG, bpKonfig);
            
            dretva = new PreuzmiMeteoPodatke(bpKonfig);
            dretva.start();
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dretva.interrupt();
    }
}
