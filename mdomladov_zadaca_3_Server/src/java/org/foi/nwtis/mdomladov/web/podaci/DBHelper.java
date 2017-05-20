/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.web.podaci;

import org.foi.nwtis.mdomladov.core.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.mdomladov.konfiguracije.APP_Konfiguracija;
import org.foi.nwtis.mdomladov.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mdomladov.konfiguracije.NemaKonfiguracije;

/**
 *
 * Klasa za rad s bazom podataka
 *
 * @author Marko Domladovac
 */
public class DBHelper extends CoreHelper {

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    private static Integer uredjajId;

    public DBHelper(APP_Konfiguracija konfig) {
        super(konfig);
    }

    public DBHelper(String filePath) throws NemaKonfiguracije, NeispravnaKonfiguracija {
        super(filePath);
    }

    public boolean addUredjaj(String naziv, String adresa) {
        boolean retVal = false;
        GMKlijent k = new GMKlijent();
        Lokacija lok = k.getGeoLocation(adresa);
        
        if(lok != null){
            retVal = addUredjaj(new Uredjaj(0, naziv, lok));
        }
        return retVal;
    }

    public boolean addUredjaj(Uredjaj uredjaj) {
        boolean isValid = false;
        try {
            String insertQuery = getInsertIotQuery(uredjaj);
            openConnection();
            isValid = stmt.executeUpdate(insertQuery) == 1;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeDbResources();
        }
        return isValid;
    }

    protected boolean addMeteo(MeteoPodaci meteo, Uredjaj uredjaj) {
        boolean isValid = false;
        try {
            openConnection();
            isValid = stmt.executeUpdate(getInsertMeteoQuery(meteo, uredjaj)) == 1;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeDbResources();
        }
        return isValid;
    }

    public List<Uredjaj> getUredjaji() {
        List<Uredjaj> uredjaji = null;
        try {
            openConnection();
            uredjaji = fillUredjaji();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeDbResources();
        }
        return uredjaji;
    }

    private List<Uredjaj> fillUredjaji() throws SQLException {
        List<Uredjaj> uredjaji = new ArrayList<>();
        rs = stmt.executeQuery("SELECT id, naziv, longitude, latitude FROM uredaji");
        while (rs.next()) {
            String latitude = String.valueOf(rs.getFloat("latitude"));
            String longitude = String.valueOf(rs.getFloat("longitude"));
            Lokacija lokacija = new Lokacija(latitude, longitude);
            Uredjaj uredjaj = new Uredjaj(rs.getInt("id"),
                    rs.getString("naziv"), lokacija);
            uredjaji.add(uredjaj);
        }

        return uredjaji;
    }

    public List<MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(int id, long intervalOd, long intervalDo) {
        List<MeteoPodaci> meteoPodaci = null;
        try {
            openConnection();
            meteoPodaci = fillMeteoPodaci(id, intervalOd, intervalDo);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeDbResources();
        }
        return meteoPodaci;
    }

    public MeteoPodaci dajZadnjeMeteoPodatkeZaUredjaj(int id) {
        MeteoPodaci meteo = null;
        try {
            openConnection();
            meteo = pronadi(id);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeDbResources();
        }
        return meteo;
    }

    public Minimax dajMinMaxTempZaUredjaj(int id, long intervalOd, long intervalDo) {
        Minimax minimax = null;
        try {
            openConnection();
            minimax = dohvatiMinimaxZaInterval(id, intervalOd, intervalDo, "temp");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeDbResources();
        }
        return minimax;
    }

    public Minimax dajMinMaxVlagaZaUredjaj(int id, long intervalOd, long intervalDo) {
        Minimax minimax = null;
        try {
            openConnection();
            minimax = dohvatiMinimaxZaInterval(id, intervalOd, intervalDo, "vlaga");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeDbResources();
        }
        return minimax;
    }

    public Minimax dajMinMaxTlakZaUredjaj(int id, long intervalOd, long intervalDo) {
        Minimax minimax = null;
        try {
            openConnection();
            minimax = dohvatiMinimaxZaInterval(id, intervalOd, intervalDo, "tlak");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeDbResources();
        }
        return minimax;
    }

    private MeteoPodaci pronadi(int id) throws SQLException {
        MeteoPodaci meteo = null;
        rs = stmt.executeQuery(getPronadiZadnjiMeteoQuery(id));

        if (rs.next()) {
            meteo = fillMeteoObjekt();
        }

        return meteo;
    }

    private List<MeteoPodaci> fillMeteoPodaci(int id, long intervalOd, long intervalDo) throws SQLException {
        List<MeteoPodaci> meteoPodaci = new ArrayList<>();
        rs = stmt.executeQuery(getSelectMeteoQuery(id, intervalOd, intervalDo));
        while (rs.next()) {
            MeteoPodaci meteo = fillMeteoObjekt();
            meteoPodaci.add(meteo);
        }

        return meteoPodaci.size() > 0 ? meteoPodaci : null;
    }

    private MeteoPodaci fillMeteoObjekt() throws SQLException {
        MeteoPodaci meteo = new MeteoPodaci();
        meteo.setNaziv(rs.getString("naziv"));
        meteo.setLokacija(rs.getString("latitude"), rs.getString("longitude"));
        meteo.setWeatherIcon(rs.getString("vrijeme"));
        meteo.setWeatherValue(rs.getString("vrijemeOpis"));
        meteo.setTemperatureValue(rs.getFloat("temp"));
        meteo.setTemperatureMin(rs.getFloat("tempMin"));
        meteo.setTemperatureMax(rs.getFloat("tempMax"));
        meteo.setHumidityValue(rs.getFloat("vlaga"));
        meteo.setPressureValue(rs.getFloat("tlak"));
        meteo.setWindSpeedValue(rs.getFloat("vjetar"));
        meteo.setWindDirectionValue(rs.getFloat("vjetarSmjer"));
        meteo.setLastUpdate(new Date(rs.getTimestamp("preuzeto").getTime()));
        return meteo;
    }

    private Minimax dohvatiMinimaxZaInterval(int id, long intervalOd, long intervalDo, String polje) throws SQLException {
        Minimax minimax = null;
        rs = stmt.executeQuery(getPronadiMinimaxQuery(id, intervalOd, intervalDo, polje));

        if (rs.next()) {
            minimax = new Minimax(rs.getDouble(1), rs.getDouble(2));
        }

        return minimax;
    }

    private void openConnection() throws ClassNotFoundException, SQLException {
        Class.forName(konfiguracija.getDriverDatabase());
        if (conn == null) {
            conn = DriverManager
                    .getConnection(konfiguracija.getUrl(),
                            konfiguracija.getAdminUsername(),
                            konfiguracija.getAdminPassword());
        }

        if (stmt == null) {
            stmt = (Statement) conn.createStatement();
        }
    }

    private void closeDbResources() {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
                Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException ex) {
                Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException ex) {
                Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getZadnjiUredjajId() {

        try {
            openConnection();
            rs = stmt.executeQuery("SELECT MAX(id) FROM uredaji");
            uredjajId = rs.next() ? rs.getInt(1) : null;
            closeDbResources();
        } catch (ClassNotFoundException | SQLException ex) {
            uredjajId = null;
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return uredjajId;
    }

    private String getInsertIotQuery(Uredjaj uredjaj) {
        int id = getZadnjiUredjajId() + 1;
        StringBuilder query = new StringBuilder("INSERT INTO uredaji ");
        query.append("(id, naziv, latitude, longitude) VALUES (");
        query.append(id);
        query.append(", '");
        query.append(uredjaj.getNaziv());
        query.append("', ");
        query.append(uredjaj.getGeoloc().getLatitude());
        query.append(", ");
        query.append(uredjaj.getGeoloc().getLongitude());
        query.append("");
        query.append(")");

        return query.toString();
    }

    private String getInsertMeteoQuery(MeteoPodaci meteo, Uredjaj uredjaj) {
        Lokacija lokacija = uredjaj.getGeoloc();
        GMKlijent gmKlijent = new GMKlijent();
        String adresa = gmKlijent.getAdresaByLocation(lokacija);
        StringBuilder query = new StringBuilder("INSERT INTO meteo ");
        query.append("(id, adresaStanice, latitude, longitude, vrijeme, vrijemeopis, temp, tempMin, tempMax, vlaga, tlak, vjetar, vjetarSmjer) VALUES (");

        query.append(uredjaj.getId());
        query.append(", '");
        query.append(adresa);
        query.append("', ");
        query.append(lokacija.getLatitude());
        query.append(", ");
        query.append(lokacija.getLongitude());
        query.append(", '");
        query.append(meteo.getWeatherIcon());
        query.append("', '");
        query.append(meteo.getWeatherValue());
        query.append("', ");
        query.append(meteo.getTemperatureValue());
        query.append(", ");
        query.append(meteo.getTemperatureMin());
        query.append(", ");
        query.append(meteo.getTemperatureMax());
        query.append(", ");
        query.append(meteo.getHumidityValue());
        query.append(", ");
        query.append(meteo.getPressureValue());
        query.append(", ");
        query.append(meteo.getWindSpeedValue());
        query.append(", ");
        query.append(meteo.getWindDirectionValue());

        query.append("");
        query.append(")");

        return query.toString();
    }

    private String getSelectMeteoQuery(int id, long intervalOd, long intervalDo) {
        StringBuilder sb = getPronadiMeteoZaUredjajQuery(id);
        sb.append(dohvatiInterval(intervalOd, intervalDo));
        sb.append(" ORDER BY PREUZETO DESC");

        return sb.toString();
    }

    private String getPronadiZadnjiMeteoQuery(int id) {
        StringBuilder sb = getPronadiMeteoZaUredjajQuery(id);
        sb.append(" ORDER BY PREUZETO DESC");

        return sb.toString();
    }

    private StringBuilder getPronadiMeteoZaUredjajQuery(int id) {
        StringBuilder sb = new StringBuilder("SELECT u.NAZIV, m.* FROM NWTIS_I.METEO m join NWTIS_I.UREDAJI u on m.id=u.id where u.id =");
        sb.append(id);

        return sb;
    }

    private String getPronadiMinimaxQuery(int id, long intervalOd, long intervalDo, String polje) {
        StringBuilder sb = new StringBuilder("SELECT MAX(");
        sb.append(polje);
        sb.append("), MIN(");
        sb.append(polje);
        sb.append(") FROM METEO where id =");
        sb.append(id);
        sb.append(dohvatiInterval(intervalOd, intervalDo));

        return sb.toString();
    }

    private StringBuilder dohvatiInterval(long intervalOd, long intervalDo) {
        StringBuilder sb = new StringBuilder(" and preuzeto between '");
        sb.append(new Timestamp(intervalOd));
        sb.append("' and '");
        sb.append(new Timestamp(intervalDo));
        sb.append("'");

        return sb;
    }
}
