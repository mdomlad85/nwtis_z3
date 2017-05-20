/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.mdomladov.konfiguracije.APP_Konfiguracija;
import org.foi.nwtis.mdomladov.web.podaci.DBHelper;
import org.foi.nwtis.mdomladov.web.podaci.GMKlijent;
import org.foi.nwtis.mdomladov.web.podaci.Lokacija;
import org.foi.nwtis.mdomladov.web.podaci.MeteoPodaci;
import org.foi.nwtis.mdomladov.web.podaci.OWMKlijent;
import org.foi.nwtis.mdomladov.web.podaci.Uredjaj;
import org.foi.nwtis.mdomladov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author Zeus
 */
public class DodajUredjaj extends HttpServlet {

    private HttpServletRequest httpReq;

    private APP_Konfiguracija konfiguracija;

    private Akcija trenutnaAkcija;

    private DBHelper db;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        httpReq = request;
        inicijaliziraj();

        switch (trenutnaAkcija) {
            case DOHVATI:
                dohvatiKoordinate();
                break;
            case SPREMI:
                spremiUredaj();
                break;
            case PRIKAZI:
                dohvatiPrognozu();
                break;
            case KONTROLER:
            default:
                ocistiVrijednosti();
                break;
        }

        RequestDispatcher rd = this.getServletContext()
                .getRequestDispatcher("/index.jsp");
        rd.forward(httpReq, response);

    }

    private void dohvatiKoordinate() {
        ServletContext sc = httpReq.getServletContext();
        String naziv = httpReq.getParameter("naziv");
        String adresa = httpReq.getParameter("adresa");
        GMKlijent gm = new GMKlijent();
        Lokacija lokacija = gm.getGeoLocation(adresa);
        sc.setAttribute("lokacija", lokacija);
        sc.setAttribute("naziv", naziv);
        sc.setAttribute("adresa", adresa);
    }

    private void spremiUredaj() {
        ServletContext sc = httpReq.getServletContext();
        Uredjaj uredjaj = new Uredjaj();
        uredjaj.setNaziv(sc.getAttribute("naziv").toString());
        uredjaj.setGeoloc((Lokacija) sc.getAttribute("lokacija"));        

        if (db.addUredjaj(uredjaj)) {
            setPoruke("Uređaj je spremljen", null);
        } else {
            setPoruke(null, "Uređaj nije spremljen");
        }
    }

    private void dohvatiPrognozu() {
        ServletContext sc = httpReq.getServletContext();
        Lokacija lokacija = (Lokacija) sc.getAttribute("lokacija");
        OWMKlijent owm = new OWMKlijent(konfiguracija.getOWApiKey());
        MeteoPodaci meteo = owm.getRealTimeWeather(lokacija.getLatitude(),
                lokacija.getLongitude());

        sc.setAttribute("meteo", meteo);
    }

    private void inicijaliziraj() {
        String path = httpReq.getServletPath();
        trenutnaAkcija = Akcija.KONTROLER;
        if (akcije.containsKey(path)) {
            trenutnaAkcija = akcije.get(path);
        }

        konfiguracija = (APP_Konfiguracija) httpReq.getServletContext()
                .getAttribute(SlusacAplikacije.APP_KONFIG);

        db = new DBHelper(konfiguracija);

        setPoruke(null, null);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private static HashMap<String, DodajUredjaj.Akcija> akcije;

    static {
        akcije = new HashMap<>();
        akcije.put("/DodajUredjaj", DodajUredjaj.Akcija.KONTROLER);
        akcije.put("/DohvatiGeolokaciju", DodajUredjaj.Akcija.DOHVATI);
        akcije.put("/SpremiLokaciju", DodajUredjaj.Akcija.SPREMI);
        akcije.put("/DohvatiMeteo", DodajUredjaj.Akcija.PRIKAZI);

    }

    private void setPoruke(String spremljen, String nijeSpremljen) {
        ServletContext sc = httpReq.getServletContext();
        sc.setAttribute("neuspjesnaPoruka", nijeSpremljen);
        sc.setAttribute("uspjesnaPoruka", spremljen);
    }

    private void ocistiVrijednosti() {
        ServletContext sc = httpReq.getServletContext();
        setPoruke(null, null);
        sc.setAttribute("naziv", null);
        sc.setAttribute("adresa", null);
        sc.setAttribute("lokacija", null);
    }

    public enum Akcija {
        KONTROLER,
        DOHVATI,
        SPREMI,
        PRIKAZI
    }

}
