/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.web.zrna;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.datatype.XMLGregorianCalendar;
import org.foi.nwtis.mdomladov.RESTKlijent;
import org.foi.nwtis.mdomladov.ws.klijenti.MeteoPodaci;
import org.foi.nwtis.mdomladov.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.mdomladov.ws.klijenti.Uredjaj;

/**
 * unositi pojedinačne IoT uređaje za koje će se preuzimati metorološki podaci.
 * Unese se naziv IoT uređaja i adresa. Postoje dva gumba "Upiši SOAP" i "Upiši
 * REST". Svaki od njih pokreće akciju koja poziva određenu operaciju web
 * servisa (SOAP i REST) iz prvog projekta. preuzeti IoT uređaje za koje se
 * prikupljaju meteorološki podaci u prvom projektu te se prikazuju u obliku
 * padajućeg izbornika s mogućim odabirom više elemenata. Postoje elementi za
 * unos od i do intervala. Ako se odabere samo jedan IoT uređaj tada se može
 * aktivirati gumb "Preuzmi SOAP" kojim se pokreće akcija koja će preuzeti sve
 * pohranjenje meteorološke podatke za odabrani IoT uređaj putem SOAP web
 * servisa iz prvog projekta. Preuzeti meteorološki podaci prikazuju se u obliku
 * tablice. Ako se odabere minimalno dva IoT uređaja tada se može aktivirati
 * gumb "Preuzmi REST" kojim se pokreće akcija koja će preuzeti putem REST web
 * servisa iz prvog projekta preuzimaju važaći meteorološki podaci za odabrane
 * IoT uređaje. Preuzeti meteorološki podaci prikazuju se u obliku tablice.
 *
 * @author Marko Domladovac
 */
@ManagedBean(name = "odabirUredjaja", eager = true)
@ViewScoped
public class OdabirUredjaja implements Serializable {

    private List<Uredjaj> uredjaji;

    private List<MeteoPodaci> meteoPodaci;

    private List<String> odabraniUredjajiId;

    private String naziv;

    private String adresa;

    private long intervalOd;

    private long intervalDo;

    private boolean restEnabled;

    private boolean soapEnabled;

    /**
     * Creates a new instance of OdabirUredjaja
     */
    public OdabirUredjaja() {
        uredjaji = MeteoWSKlijent.dajSveAdrese();
        intervalOd = System.currentTimeMillis() - 24 * 60 * 1000;
        intervalDo = System.currentTimeMillis();
        odabraniUredjajiId = new ArrayList();
    }

    /**
     *
     */
    public void dohvatiPodatke() {
        meteoPodaci = new ArrayList<>();
        if (soapEnabled) {
            meteoPodaci = MeteoWSKlijent.dajSveMeteoPodatkeZaUredjaj(Integer.parseInt(odabraniUredjajiId.get(0)), intervalOd, intervalDo);
        } else if (restEnabled) {
            for (String id : odabraniUredjajiId) {
                MeteoPodaci meteo = RESTKlijent.dajZadnjeMeteoPodatkeZaUredjaj(Integer.parseInt(id));
                if (meteo != null) {
                    meteoPodaci.add(meteo);
                }
            }
        }
    }

    public String convertXmlDate(XMLGregorianCalendar xmlDate) {
        if (xmlDate != null) {
            xmlDate.toGregorianCalendar().getTime();
            SimpleDateFormat dt1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

            return dt1.format(xmlDate.toGregorianCalendar().getTime());
        }
        return null;
    }

    public void provjeriOdabraneUredjaje() {
        soapEnabled = odabraniUredjajiId.size() == 1;
        restEnabled = odabraniUredjajiId.size() > 1;
    }

    /**
     *
     * @param isSoap
     */
    public void spremi(boolean isSoap) {
        if (naziv.isEmpty() || adresa.isEmpty()) {
            return;
        }
        if (isSoap) {
            MeteoWSKlijent.dodajUredjaj(naziv, adresa);
        } else {
            RESTKlijent.addUredjaj(naziv, adresa);
        }
        naziv = "";
        adresa = "";
        uredjaji = MeteoWSKlijent.dajSveAdrese();
    }

    /**
     *
     * @return
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     *
     * @param naziv
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     *
     * @return
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     *
     * @param adresa
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    /**
     *
     * @return
     */
    public long getIntervalOd() {
        return intervalOd;
    }

    /**
     *
     * @param intervalOd
     */
    public void setIntervalOd(long intervalOd) {
        this.intervalOd = intervalOd;
    }

    /**
     *
     * @return
     */
    public long getIntervalDo() {
        return intervalDo;
    }

    /**
     *
     * @param intervalDo
     */
    public void setIntervalDo(long intervalDo) {
        this.intervalDo = intervalDo;
    }

    /**
     *
     * @return
     */
    public List<Uredjaj> getUredjaji() {
        return uredjaji;
    }

    /**
     *
     * @param uredjaji
     */
    public void setUredjaji(List<Uredjaj> uredjaji) {
        this.uredjaji = uredjaji;
    }

    /**
     *
     * @return
     */
    public List<String> getOdabraniUredjajiId() {
        return odabraniUredjajiId;
    }

    /**
     *
     * @param odabraniUredjajiId
     */
    public void setOdabraniUredjajiId(List<String> odabraniUredjajiId) {
        this.odabraniUredjajiId = odabraniUredjajiId;
        provjeriOdabraneUredjaje();
        meteoPodaci = null;
    }

    /**
     *
     * @return
     */
    public List<MeteoPodaci> getMeteoPodaci() {
        return meteoPodaci;
    }

    /**
     *
     * @param meteoPodaci
     */
    public void setMeteoPodaci(List<MeteoPodaci> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }

    public boolean isRestEnabled() {
        return restEnabled;
    }

    public void setRestEnabled(boolean restEnabled) {
        this.restEnabled = restEnabled;
    }

    public boolean isSoapEnabled() {
        return soapEnabled;
    }

    public void setSoapEnabled(boolean soapEnabled) {
        this.soapEnabled = soapEnabled;
    }

}
