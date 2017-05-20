/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.mdomladov.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.mdomladov.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.mdomladov.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.mdomladov.web.podaci.DBHelper;
import org.foi.nwtis.mdomladov.web.podaci.DBHelperTest;
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
public class JsonHelperTest {
    
    private DBHelper helper;
    
    public JsonHelperTest() {
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

    /**
     * Test of createJsonArrayString method, of class JsonHelper.
     */
    @Test
    public void testCreateJsonArrayString() {
        System.out.println("createJsonArrayString");
        String expResult = "";
        String result = JsonHelper.createJsonArrayString(helper.getUredjaji());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
