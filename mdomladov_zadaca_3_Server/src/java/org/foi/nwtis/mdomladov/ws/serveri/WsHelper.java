/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.ws.serveri;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.foi.nwtis.mdomladov.konfiguracije.APP_Konfiguracija;
import org.foi.nwtis.mdomladov.web.slusaci.SlusacAplikacije;

/**
 *
 * @author Zeus
 */
class WsHelper {
    
    protected APP_Konfiguracija konfiguracija;

    @Resource
    private WebServiceContext context;

    public WsHelper() {
        napuniKonfiguraciju();
    } 

    private void napuniKonfiguraciju() {
        if (konfiguracija == null) {
            ServletContext sc
                    = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);

            konfiguracija = (APP_Konfiguracija) sc.getAttribute(SlusacAplikacije.APP_KONFIG);
        }
    }
    
}
