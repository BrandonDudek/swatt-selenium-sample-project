package xyz.swatt.selenium_sample.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Quotes;
import xyz.swatt.asserts.ArgumentChecks;
import xyz.swatt.selenium.WebElementWrapper;

/**
 * Represents one Search Result in the results list.
 */
public class SearchResult {

    //========================= Static Enums ===================================

    //========================= STATIC CONSTANTS ===============================
    private static final Logger LOGGER = LogManager.getLogger(SearchResult.class);

    //========================= Static Variables ===============================

    //========================= Static Constructor =============================
    static {
    }

    //========================= Static Methods =================================

    //========================= CONSTANTS ======================================
    private final Google GOOGLE; // Page classes always need a reference to a parent or base page class.
    private final WebElementWrapper ROOT;

    //========================= Variables ======================================
    String description, title, url;

    //========================= Constructors ===================================
    /**
     * Will initialize this object around a Web Element.
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public SearchResult(Google _google, WebElementWrapper _root) {

        super();

        LOGGER.info("SearchResult(Google, _root: {}) [START]", _root == null ? "(NULL)" : _root.toString());

        //------------------------ Pre-Checks ----------------------------------
        ArgumentChecks.notNull(_google, "Google Class");

        ArgumentChecks.notNull(_root, "Root Element");

        //-------------------------CONSTANTS------------------------------------

        //-------------------------Variables------------------------------------

        //-------------------------Code-----------------------------------------
        GOOGLE = _google;
        ROOT = _root;

        LOGGER.debug("SearchResult(Google, _root: {}) [END]", _root);
    }

    //========================= Methods ========================================
    /**
     * @return The Title of this Search Result.
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public String getDescription() {

        LOGGER.info("getDescription() [START]");

        //------------------------ Pre-Checks ----------------------------------

        //------------------------ CONSTANTS -----------------------------------

        //------------------------ Variables -----------------------------------

        //------------------------ Code ----------------------------------------
        if(description == null) {

            WebElementWrapper element = ROOT.getDescendant(By.cssSelector(".s .st"), true, "Cannot find Description!");
            description = element.getValue();
        }

        LOGGER.debug("getDescription() - {} - [END]", Quotes.escape(description));

        return description;
    }

    /**
     * @return The Title of this Search Result.
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public String getTitle() {

    	LOGGER.info("getTitle() [START]");

    	//------------------------ Pre-Checks ----------------------------------

    	//------------------------ CONSTANTS -----------------------------------

    	//------------------------ Variables -----------------------------------

    	//------------------------ Code ----------------------------------------
    	if(title == null) {

    	    WebElementWrapper titleElement = ROOT.getDescendant(By.tagName("h3"), true, "Cannot find Search Result title!");
    	    title = titleElement.getValue();
        }

    	LOGGER.debug("getTitle() - {} - [END]", Quotes.escape(title));

    	return title;
    }
    /**
     * @return The Title of this Search Result.
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public String getUrl() {

        LOGGER.info("getUrl() [START]");

        //------------------------ Pre-Checks ----------------------------------

        //------------------------ CONSTANTS -----------------------------------

        //------------------------ Variables -----------------------------------

        //------------------------ Code ----------------------------------------
        if(url == null) {

            WebElementWrapper element = ROOT.getDescendant(By.cssSelector(".s .f cite"), true, "Cannot find URL!");
            url = element.getValue();
        }

        LOGGER.debug("getUrl() - {} - [END]", Quotes.escape(url));

        return url;
    }

    //========================= Classes ========================================
}
