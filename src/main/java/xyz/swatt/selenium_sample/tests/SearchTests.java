package xyz.swatt.selenium_sample.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import xyz.swatt.selenium_sample.Globals;
import xyz.swatt.selenium_sample.pages.Google;

/**
 *
 */
public class SearchTests {

    //========================= Static Enums ===================================

    //========================= STATIC CONSTANTS ===============================
    private static final Logger LOGGER = LogManager.getLogger(SearchTests.class);

    //========================= Static Variables ===============================

    //========================= Static Constructor =============================
    static {
    }

    //========================= Static Methods =================================

    //========================= CONSTANTS ======================================
    private Object BROWSER_TYPE;

    //========================= Variables ======================================
    private Google google;

    //========================= Constructors ===================================
    /**
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    @Factory(dataProviderClass = Globals.class, dataProvider = "browsersToTest")
    public SearchTests(Object _browserType) {

        super();

        LOGGER.info("SearchTests(_browserType: {}) [START]", _browserType);

        //------------------------ Pre-Checks ----------------------------------

        //-------------------------CONSTANTS------------------------------------

        //-------------------------Variables------------------------------------

        //-------------------------Code-----------------------------------------
        BROWSER_TYPE = _browserType;

        // Note: You do not do work in the constructor;
        // because when multithreading, all test objects are constructed prior to executing @Before/@Test/After methods.

        LOGGER.debug("SearchTests(_browserType: {}) [END]", _browserType);
    }

    //========================= Methods ========================================
    /**
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    @Test
    public void load() {

    	LOGGER.info("load() [START]");

    	//------------------------ Pre-Checks ----------------------------------

    	//------------------------ CONSTANTS -----------------------------------

    	//------------------------ Variables -----------------------------------

    	//------------------------ Code ----------------------------------------
        google = new Google(BROWSER_TYPE); // Validation done inside method.

    	LOGGER.debug("load() [END]");
    }

    //////////////////// After ////////////////////
    /**
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    @AfterClass
    public void afterClass() {

    	LOGGER.info("afterClass() [START]");

    	//------------------------ Pre-Checks ----------------------------------

    	//------------------------ CONSTANTS -----------------------------------

    	//------------------------ Variables -----------------------------------

    	//------------------------ Code ----------------------------------------
    	google.quit();

    	LOGGER.debug("afterClass() [END]");
    }

    //========================= Classes ========================================
}
