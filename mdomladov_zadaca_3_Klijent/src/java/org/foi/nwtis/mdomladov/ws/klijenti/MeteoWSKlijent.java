/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.ws.klijenti;

/**
 *
 * Pozivi metoda SOAP web servisa 
 * 
 * @author Marko Domladovac
 */
public class MeteoWSKlijent {

    /**
     *
     * @param naziv
     * @param adresa
     */
    public static void dodajUredjaj(java.lang.String naziv, java.lang.String adresa) {
        org.foi.nwtis.mdomladov.ws.klijenti.GeoMeteoWS_Service service = new org.foi.nwtis.mdomladov.ws.klijenti.GeoMeteoWS_Service();
        org.foi.nwtis.mdomladov.ws.klijenti.GeoMeteoWS port = service.getGeoMeteoWSPort();
        port.dodajUredjaj(naziv, adresa);
    }

    /**
     *
     * @return
     */
    public static java.util.List<org.foi.nwtis.mdomladov.ws.klijenti.Uredjaj> dajSveAdrese() {
        org.foi.nwtis.mdomladov.ws.klijenti.GeoMeteoWS_Service service = new org.foi.nwtis.mdomladov.ws.klijenti.GeoMeteoWS_Service();
        org.foi.nwtis.mdomladov.ws.klijenti.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveUredjaje();
    }   

    /**
     *
     * @param uredjajId
     * @param intervalOd
     * @param intervalDo
     * @return
     */
    public static java.util.List<org.foi.nwtis.mdomladov.ws.klijenti.MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(int uredjajId, long intervalOd, long intervalDo) {
        org.foi.nwtis.mdomladov.ws.klijenti.GeoMeteoWS_Service service = new org.foi.nwtis.mdomladov.ws.klijenti.GeoMeteoWS_Service();
        org.foi.nwtis.mdomladov.ws.klijenti.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveMeteoPodatkeZaUredjaj(uredjajId, intervalOd, intervalDo);
    }
    
    
    
}
