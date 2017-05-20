/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.dretve;

import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.mdomladov.konfiguracije.APP_Konfiguracija;
import org.foi.nwtis.mdomladov.web.podaci.DBHelper;
import org.foi.nwtis.mdomladov.web.podaci.Lokacija;
import org.foi.nwtis.mdomladov.web.podaci.MeteoPodaci;
import org.foi.nwtis.mdomladov.web.podaci.OWMKlijent;
import org.foi.nwtis.mdomladov.web.podaci.Uredjaj;

/**
 *
 * @author Zeus
 */
public class PreuzmiMeteoPodatke extends DBHelper {

    private final int interval;

    private long startTime;

    public PreuzmiMeteoPodatke(APP_Konfiguracija konfig) {
        super(konfig);
        interval = konfiguracija.getTimeSecThread() * 1000;
    }

    @Override
    public void run() {

        while (!this.isInterrupted()) {
            try {
                startTime = System.currentTimeMillis();

                List<Uredjaj> uredjaji = getUredjaji();
                for (Uredjaj uredjaj : uredjaji) {
                    OWMKlijent owm = new OWMKlijent(konfiguracija.getOWApiKey());
                    Lokacija lokacija = uredjaj.getGeoloc();
                    MeteoPodaci meteo = owm.getRealTimeWeather(
                            lokacija.getLatitude(),
                            lokacija.getLongitude());
                    addMeteo(meteo, uredjaj);
                }

                sleep(getTrajanjeSpavanja());
            } catch (InterruptedException ex) {
                Logger.getLogger(PreuzmiMeteoPodatke.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        Logger.getLogger(PreuzmiMeteoPodatke.class.getName()).log(Level.INFO,
                "ObradaPoruka je započela s radom", this);
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void interrupt() {
        Logger.getLogger(PreuzmiMeteoPodatke.class.getName()).log(Level.INFO,
                "ObradaPoruka je zavrsila s radom", this);
        super.interrupt();
    }

    /**
     *
     * @return
     */
    protected long getTrajanjeSpavanja() {
        int i = 1;
        long spavanje = 0;
        do {
            spavanje = i++ * interval - (System.currentTimeMillis() - startTime) / 1000;
        } while (spavanje < 0);

        return spavanje;
    }

}
