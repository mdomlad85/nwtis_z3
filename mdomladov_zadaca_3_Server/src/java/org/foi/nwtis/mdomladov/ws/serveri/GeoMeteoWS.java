/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.ws.serveri;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.Oneway;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.foi.nwtis.mdomladov.konfiguracije.APP_Konfiguracija;
import org.foi.nwtis.mdomladov.web.podaci.DBHelper;
import org.foi.nwtis.mdomladov.web.podaci.MeteoPodaci;
import org.foi.nwtis.mdomladov.web.podaci.Minimax;
import org.foi.nwtis.mdomladov.web.podaci.Uredjaj;
import org.foi.nwtis.mdomladov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author Zeus
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {

    @Resource
    private WebServiceContext context;  
    
    private DBHelper getDb(){
        ServletContext sc
                    = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        APP_Konfiguracija  konfiguracija = (APP_Konfiguracija) sc.getAttribute(SlusacAplikacije.APP_KONFIG);
        return  new DBHelper(konfiguracija);
    }  

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "dajSveUredjaje")
    public List<Uredjaj> dajSveUredjaje() {         
        return getDb().getUredjaji();
    }

    /**
     * Web service operation
     * @param naziv
     * @param adresa
     */
    @WebMethod(operationName = "dodajUredjaj")
    @Oneway
    public void dodajUredjaj(@WebParam(name = "uredjaj") String naziv, String adresa) {
        if(getDb().addUredjaj(naziv, adresa)){
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.INFO, null, 
                    String.format("Uređaj %s je uspješno pohranjen.", naziv));
        } else {            
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.INFO, null, 
                    String.format("Došlo je do pogreške. Uređaj %s nije spremljen!", naziv));
        }
    }

    /**
     * Web service operation
     * @param uredjajId
     * @param intervalOd
     * @param intervalDo
     * @return 
     */
    @WebMethod(operationName = "dajSveMeteoPodatkeZaUredjaj")
    public List<MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(@WebParam(name = "uredjajId") 
            int uredjajId, @WebParam(name = "intervalOd") 
                    long intervalOd, @WebParam(name = "intervalDo") 
                            long intervalDo) {
        
        return getDb().dajSveMeteoPodatkeZaUredjaj(uredjajId, intervalOd, intervalDo);
    }

    /**
     * Web service operation
     * @param uredjajId - identifikator uredjaja
     * @return 
     */
    @WebMethod(operationName = "dajZadnjeMeteoPodatkeZaUredjaj")
    public MeteoPodaci dajZadnjeMeteoPodatkeZaUredjaj(@WebParam(name = "id") int uredjajId) {
        
       return getDb().dajZadnjeMeteoPodatkeZaUredjaj(uredjajId);
    }

    /**
     * Web service operation
     * @param uredjajId
     * @param intervalOd
     * @param intervalDo
     * @return 
     */
    @WebMethod(operationName = "dajMinMaxTempZaUredjaj")
    public Minimax dajMinMaxTempZaUredjaj(@WebParam(name = "id") int uredjajId, 
            @WebParam(name = "intervalOd") long intervalOd, 
            @WebParam(name = "intervalDo") long intervalDo) {
        //TODO write your implementation code here:
         return getDb().dajMinMaxTempZaUredjaj(uredjajId, intervalOd, intervalDo);
    }

    /**
     * Web service operation
     * @param uredjajId
     * @param intervalOd
     * @param intervalDo
     * @return 
     */
    @WebMethod(operationName = "dajMinMaxVlagaZaUredjaj")
    public Minimax dajMinMaxVlagaZaUredjaj(@WebParam(name = "id") int uredjajId, 
            @WebParam(name = "intervalOd") long intervalOd, 
            @WebParam(name = "intervalDo") long intervalDo) {
        //TODO write your implementation code here:
         return getDb().dajMinMaxVlagaZaUredjaj(uredjajId, intervalOd, intervalDo);
    }

    /**
     * Web service operation
     * @param uredjajId
     * @param intervalOd
     * @param intervalDo
     * @return 
     */
    @WebMethod(operationName = "dajMinMaxTlakZaUredjaj")
    public Minimax dajMinMaxTlakZaUredjaj(@WebParam(name = "id") int uredjajId, 
            @WebParam(name = "intervalOd") long intervalOd, 
            @WebParam(name = "intervalDo") long intervalDo) {
        //TODO write your implementation code here:
         return getDb().dajMinMaxTlakZaUredjaj(uredjajId, intervalOd, intervalDo);
    }
}
