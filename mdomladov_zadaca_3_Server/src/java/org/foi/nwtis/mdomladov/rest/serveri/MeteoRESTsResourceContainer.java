/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.rest.serveri;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.foi.nwtis.mdomladov.core.JsonHelper;
import org.foi.nwtis.mdomladov.konfiguracije.APP_Konfiguracija;
import org.foi.nwtis.mdomladov.web.podaci.DBHelper;
import org.foi.nwtis.mdomladov.web.podaci.Uredjaj;
import org.foi.nwtis.mdomladov.web.slusaci.SlusacAplikacije;

/**
 * REST Web Service
 *
 * @author Zeus
 */
@Path("/meteoREST")
public class MeteoRESTsResourceContainer {

    @Context
    private UriInfo context;

    private DBHelper db;

    /**
     * Creates a new instance of MeteoRESTsResourceContainer
     *
     * @param sc
     */
    public MeteoRESTsResourceContainer(@Context ServletContext sc) {
        APP_Konfiguracija konfiguracija = (APP_Konfiguracija) sc.getAttribute(SlusacAplikacije.APP_KONFIG);
        db = new DBHelper(konfiguracija);
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.mdomladov.rest.serveri.MeteoRESTsResourceContainer
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return JsonHelper.createJsonArrayString(db.getUredjaji());
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.mdomladov.rest.serveri.MeteoRESTsResourceContainer
     *
     * @param id
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("id") int id) {
        return JsonHelper.createJsonObjectString(db.dajZadnjeMeteoPodatkeZaUredjaj(id));
    }

    /**
     * POST method for creating an instance of MeteoRESTResource
     *
     * @param uredjajStr
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String uredjajStr) {
        boolean responseOk = false;
        Uredjaj uredjaj = JsonHelper.parsirajUredjaj(uredjajStr);
        
        if(uredjaj != null){
            if(db.addUredjaj(uredjaj.getNaziv(), uredjaj.getGeoloc().getAdresa())){
                responseOk = true;
                uredjaj.setId(db.getZadnjiUredjajId());
            }
        }        
        
        return responseOk 
                ? Response.ok(uredjaj).build()
                : Response.status(Response.Status.BAD_REQUEST).build();
    }
}
