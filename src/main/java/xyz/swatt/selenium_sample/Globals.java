package xyz.swatt.selenium_sample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import xyz.swatt.selenium.WebDriverWrapper;

import java.util.*;

/**
 * This class holds all of the "Global" variables/methods for the entire project.
 * <p>
 *     It could be considered similar to a config file in a dev project.
 * </p>
 */
public class Globals {

    //========================= Static Enums ===================================

    //========================= STATIC CONSTANTS ===============================
    private static final Logger LOGGER = LogManager.getLogger(Globals.class);

    public static final Set<Object> BROWSERS_TO_TEST = new HashSet<Object>() {

        private static final long serialVersionUID = 1897224021993318921L;
        {
            add(WebDriverWrapper.ChromeBrowser.CHROME);
            add(WebDriverWrapper.FirefoxBrowser.FIREFOX);
        }
    };

    //========================= Static Variables ===============================

    //========================= Static Constructor =============================
    static {
    }

    //========================= Static Methods =================================
    /**
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    @DataProvider
    public Iterator<Object[]> browsersToTest() {

    	LOGGER.info("() [START]");

    	//------------------------ Pre-Checks ----------------------------------

    	//------------------------ CONSTANTS -----------------------------------

    	//------------------------ Variables -----------------------------------
        List<Object[]> toRet = new ArrayList<>(BROWSERS_TO_TEST.size());

    	//------------------------ Code ----------------------------------------
        for(Object object : BROWSERS_TO_TEST) {
            toRet.add(new Object[] {object});
        }

    	LOGGER.debug("() [END]");

        return toRet.iterator();
    }

    //========================= CONSTANTS ======================================

    //========================= Variables ======================================

    //========================= Constructors ===================================

    //========================= Methods ========================================

    //========================= Classes ========================================
}
