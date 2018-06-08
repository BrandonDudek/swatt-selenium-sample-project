package xyz.swatt.selenium_sample.pages;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Quotes;
import xyz.swatt.asserts.ArgumentChecks;
import xyz.swatt.exceptions.WebPageException;
import xyz.swatt.selenium.WebDriverWrapper;
import xyz.swatt.selenium.WebElementWrapper;
import xyz.swatt.string.StringHelper;

import java.util.*;

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

    private static final By SEARCH_INPUT_SELECTOR = By.id("lst-ib");

    //========================= Static Variables ===============================

    //========================= Static Constructor =============================
    static {
    }

    //========================= Static Methods =================================

    //========================= CONSTANTS ======================================
    private final WebDriverWrapper DRIVER;

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
            DRIVER = new WebDriverWrapper((BrowserVersion) _browserType);
        }
        else if(_browserType instanceof WebDriverWrapper.ChromeBrowser) {
            DRIVER = new WebDriverWrapper((WebDriverWrapper.ChromeBrowser) _browserType);
        }
        else if(_browserType instanceof WebDriverWrapper.FirefoxBrowser) {
            DRIVER = new WebDriverWrapper((WebDriverWrapper.FirefoxBrowser) _browserType);
        }
        else if(_browserType instanceof WebDriverWrapper.IEBrowser) {
            DRIVER = new WebDriverWrapper((WebDriverWrapper.IEBrowser) _browserType);
        }
        else {
            throw new IllegalArgumentException("The given Browser Type (" + _browserType + ") is unknown!");
        }

        goHome();

        LOGGER.debug("Google(_browserType: {}) [END]", _browserType);
    }

    //========================= Methods ========================================
    /**
     * Will clear the Search input and validate that it was cleared.
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public void clearSearchInput() {

    	LOGGER.info("clearSearchInput() [START]");

    	//------------------------ Pre-Checks ----------------------------------

    	//------------------------ CONSTANTS -----------------------------------

    	//------------------------ Variables -----------------------------------
        WebElementWrapper searchInput = DRIVER.getWebElementWrapper(SEARCH_INPUT_SELECTOR, true,
                "Could not find Search input!");

    	//------------------------ Code ----------------------------------------
        searchInput.clearInput();

        ////////// Validate //////////
        String value = getSearchInputValue();
        if(!value.isEmpty()) {
            throw new WebPageException("Expected Search input to be cleared but found a value of, " + Quotes.escape(value) + "!");
        }

    	LOGGER.debug("clearSearchInput() [END]");
    }

    /**
     * Will click on the "Google Search" button and wait for the results to load.
     * <p>
     *     <b>Note:</b> It will click the normal one, or the Search Suggestions dropdown one.
     *     (Depending on which is available.)
     * </p>
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public List<SearchResult> clickGoogleSearchButton() {

        LOGGER.info("clickGoogleSearchButton() [START]");

        //------------------------ Pre-Checks ----------------------------------

        //------------------------ CONSTANTS -----------------------------------

        //------------------------ Variables -----------------------------------
        WebElementWrapper googleSearchButton;

        List<SearchResult> toRet;

        //------------------------ Code ----------------------------------------
        // First try Google Search button is Related Searches dropdown.
        googleSearchButton = DRIVER.getWebElementWrapper(By.cssSelector("#sbtc input[value='Google Search']"), true, 0);
        if(googleSearchButton == null) {
            googleSearchButton = DRIVER.getWebElementWrapper(By.cssSelector("#tsf input[aria-label='Google Search']"), true,
                    "Cannot find the \"Google Search\" button!");
        }

        googleSearchButton.click(true);

        toRet = getSearchResultsObject();

        LOGGER.debug("clickGoogleSearchButton() - ({}) - [END]", toRet.size());

        return toRet;
    }

    /**
     * Will get the value of the Search input field.
     * <p>
     *     <b>Note:</b> If the input is blank, then an Empty String is returned.
     * </p>
     *
     * @return What value is in the Search input.
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public String getSearchInputValue() {

    	LOGGER.info("getSearchInputValue() [START]");

    	//------------------------ Pre-Checks ----------------------------------

    	//------------------------ CONSTANTS -----------------------------------

    	//------------------------ Variables -----------------------------------
        String toRet;
        WebElementWrapper searchInput = DRIVER.getWebElementWrapper(SEARCH_INPUT_SELECTOR, true,
                "Could not find Search input!");

    	//------------------------ Code ----------------------------------------
    	toRet = searchInput.getValue();

    	if(toRet == null) {
    	    toRet = "";
        }

    	LOGGER.debug("getSearchInputValue() - {} - [END]", toRet);

    	return toRet;
    }

    /**
     * Will go to the Google Home Page.
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public void goHome() {

    	LOGGER.info("goHome() [START]");

    	//------------------------ Pre-Checks ----------------------------------

    	//------------------------ CONSTANTS -----------------------------------

    	//------------------------ Variables -----------------------------------

    	//------------------------ Code ----------------------------------------
        DRIVER.goToUrl(BASE_URL);

        ////////// Validate //////////
        if(!DRIVER.getPageTitle().trim().equalsIgnoreCase("Google")) {
            throw new WebPageException("Google failed to load!");
        }

    	LOGGER.debug("goHome() [END]");
    }

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

    /**
     * Will add a search string to the search box, validate it was set, send the enter key to execute the search, and wait for the results to load..
     * <p>
     *     <b>Note:</b> The Search input will be cleared first.
     *     (see: {@link #clearSearchInput()}.)
     * </p>
     * <p>
     *     <b>Note:</b> If the Search String is empty, then an empty search will be performed.
     *     (Should do nothing.)
     * </p>
     *
     * @throws IllegalArgumentException If the given Search String is {@code null}.
     * <p>
     *     If the given Search String contains the Enter key.
     * </p>
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public List<SearchResult> search(String _searchString) {

        LOGGER.info("search(_searchString: {}) [START]", _searchString);

        //------------------------ Pre-Checks ----------------------------------
        ArgumentChecks.notNull(_searchString, "Search String");

        //------------------------ CONSTANTS -----------------------------------

        //------------------------ Variables -----------------------------------
        WebElementWrapper searchInput = DRIVER.getWebElementWrapper(SEARCH_INPUT_SELECTOR, true,
                "Could not find Search input!");

        List<SearchResult> toRet;

        //------------------------ Code ----------------------------------------
        setSearchInput(_searchString); // Validation done in method.

        searchInput.sendKeys(Keys.ENTER);

        ////////// Wait //////////
        DRIVER.waitForPageLoad();

        toRet = getSearchResultsObject();

        LOGGER.debug("search(_searchString: {}) - ({}) - [END]", _searchString, toRet.size());

        return toRet;
    }

    /**
     * Will add a search string to the search box and validate it was set but will <b>not</b> click on the search button.
     * <p>
     *     <b>Note:</b> The Search input will be cleared first.
     *     (see: {@link #clearSearchInput()}.)
     * </p>
     * <p>
     *     <b>Note:</b> If the Search String is empty, then the input will just be cleared.
     *     (You could also just call {@link #clearSearchInput()}.)
     * </p>
     *
     * @throws IllegalArgumentException If the given Search String is {@code null}.
     * <p>
     *     If the given Search String contains the Enter key.
     * </p>
     *
     * @return A List of Search common search terms that Google suggests.
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public Set<String> setSearchInput(String _searchString) {

    	LOGGER.info("setSearchInput(_searchString: {}) [START]", _searchString);

    	//------------------------ Pre-Checks ----------------------------------
        ArgumentChecks.notNull(_searchString, "Search String");
        if(_searchString.contains(Keys.ENTER.toString())) {
            throw new IllegalArgumentException("Given Search String cannot contain the Enter key!");
        }

    	//------------------------ CONSTANTS -----------------------------------

    	//------------------------ Variables -----------------------------------
        WebElementWrapper searchInput = DRIVER.getWebElementWrapper(SEARCH_INPUT_SELECTOR, true,
                "Could not find Search input!");

        Set<String> toRet;

    	//------------------------ Code ----------------------------------------
        clearSearchInput();

        if(!_searchString.isEmpty()) {

            searchInput.sendKeys(_searchString);

            ///// Validate /////
            String expectedValue = StringHelper.replace(_searchString, "", StringHelper.CharacterPosition.ANYWHERE, false,
                    StringHelper.CharacterSet.CONTROL_NON_PRINT); // Remove Non-Print Characters.
            String newValue = getSearchInputValue();
            if(!newValue.equals(expectedValue)) {
                throw new WebPageException("Failed to set Search input's value!\n\tExpected:  " + expectedValue + "\n\tBut Found: " + newValue);
            }
        }

        ////////// Get Search Suggestions //////////
        try { // Needs some time for the Search Suggestions to populate. (Cannot wait for existence, because they change with every keystroke.)
            Thread.sleep(200);
        }
        catch(InterruptedException e) {/*Do Nothing*/}
        List<WebElementWrapper> searchSuggestionElements = DRIVER.getWebElementWrappers(By.cssSelector("#sbtc li .sbqs_c"), true);
        toRet = new LinkedHashSet<>(searchSuggestionElements.size()); // LinkedHashSet to preserve order.
        for(WebElementWrapper searchSuggestionElement : searchSuggestionElements) {

            String searchSuggestion = searchSuggestionElement.getValue();
            toRet.add(searchSuggestion);
        }

    	LOGGER.debug("setSearchInput(_searchString: {}) - ({}) - [END]", _searchString, toRet.size());

        return toRet;
    }

    //////////////////// Helper Methods ////////////////////
    /**
     * Will find the Search Results Object and save it as an object variable.
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    private List<SearchResult> getSearchResultsObject() {

        LOGGER.debug("getSearchResultsObject() [START]");

        //------------------------ Pre-Checks ----------------------------------

        //------------------------ CONSTANTS -----------------------------------

        //------------------------ Variables -----------------------------------
        List<SearchResult> toRet;

        //------------------------ Code ----------------------------------------
        List<WebElementWrapper> searchResultsElements = DRIVER.getWebElementWrappers(
                By.cssSelector("#rso > .bkWMgd > *:not(.kno-kp):not(.mnr-c):not(.g-blk) .rc"), WebDriverWrapper.DEFAULT_MAX_PAGE_LOAD_TIME_S, true);

        toRet = new ArrayList<>(searchResultsElements.size());

        for(WebElementWrapper searchResultElement : searchResultsElements) {
            toRet.add(new SearchResult(this, searchResultElement));
        }

        LOGGER.trace("getSearchResultsObject() - ({}) - [END]", toRet.size());

        return toRet;
    }

    //========================= Classes ========================================
}
