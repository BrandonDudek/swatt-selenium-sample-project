package xyz.swatt.selenium_sample.pages;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.swatt.exceptions.WebPageException;
import xyz.swatt.selenium.WebDriverWrapper;

/**
 * This is the starting point for the Google Pages API.
 *
 * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
 */
public class Google {

    //========================= Static Enums ===================================

    //========================= STATIC CONSTANTS ===============================
    private static final Logger LOGGER = LogManager.getLogger(Google.class);
    private static final String BASE_URL = "https://www.google.com";

    //========================= Static Variables ===============================

    //========================= Static Constructor =============================
    static {
    }

    //========================= Static Methods =================================

    //========================= CONSTANTS ======================================
    private WebDriverWrapper DRIVER;

    //========================= Variables ======================================

    //========================= Constructors ===================================
    /**
     * Will load the Web Browser, go to the Google homepage, and validate that we are there.
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public Google(Object _browserType) {

        super();

        LOGGER.info("Google(_browserType: {}) [START]", _browserType);

        //------------------------ Pre-Checks ----------------------------------

        //-------------------------CONSTANTS------------------------------------

        //-------------------------Variables------------------------------------

        //-------------------------Code-----------------------------------------
        if(_browserType instanceof BrowserVersion) {
            DRIVER = new WebDriverWrapper((BrowserVersion)_browserType);
        }
        else if(_browserType instanceof WebDriverWrapper.ChromeBrowser) {
            DRIVER = new WebDriverWrapper((WebDriverWrapper.ChromeBrowser)_browserType);
        }
        else if(_browserType instanceof WebDriverWrapper.FirefoxBrowser) {
            DRIVER = new WebDriverWrapper((WebDriverWrapper.FirefoxBrowser)_browserType);
        }
        else {
            throw new IllegalArgumentException("The given Browser Type (" + _browserType + ") is unknown!");
        }

        DRIVER.goToUrl(BASE_URL);

        if(!DRIVER.getPageTitle().trim().equalsIgnoreCase("Google")) {
            throw new WebPageException("Google failed to load!");
        }

        LOGGER.debug("Google(_browserType: {}) [END]", _browserType);
    }

    //========================= Methods ========================================
    /**
     * Will close and quit the browser.
     * <p>
     *     <b>Note:</b> Any further calls made to this object will fail.
     * </p>
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public void quit() {

        LOGGER.info("quit() [START]");

        //------------------------ Pre-Checks ----------------------------------

        //------------------------ CONSTANTS -----------------------------------

        //------------------------ Variables -----------------------------------

        //------------------------ Code ----------------------------------------
        DRIVER.quit();

        LOGGER.debug("quit() [END]");
    }

    //========================= Classes ========================================
}
