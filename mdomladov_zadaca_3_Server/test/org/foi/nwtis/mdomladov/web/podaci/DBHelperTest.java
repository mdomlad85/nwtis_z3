/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.web.podaci;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.mdomladov.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mdomladov.konfiguracije.NemaKonfiguracije;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zeus
 */
public class DBHelperTest {
    
    private DBHelper helper;
    
    public DBHelperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() { 
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {           
        try {
            helper = new DBHelper("NWTiS.db.config_1.xml");
        } catch (NemaKonfiguracije | NeispravnaKonfiguracija ex) {
            Logger.getLogger(DBHelperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testMeteoInterval() {
        assertNotNull(helper.dajSveMeteoPodatkeZaUredjaj(2, 1494849847123L, 1495044610547L));
    }
    

    @Test
    public void testMeteoPronadi() {
        assertNotNull(helper.dajZadnjeMeteoPodatkeZaUredjaj(2));
    }
    

    @Test
    public void testMinimaxTemp() {
        assertNotNull(helper.dajMinMaxTempZaUredjaj(2, 1494849847123L, 1495044610547L));
    }
    

    @Test
    public void testMinimaxVlaga() {
        assertNotNull(helper.dajMinMaxVlagaZaUredjaj(2, 1494849847123L, 1495044610547L));
    }
    

    @Test
    public void testMinimaxTlak() {
        assertNotNull(helper.dajMinMaxTlakZaUredjaj(2, 1494849847123L, 1495044610547L));
    }
}
