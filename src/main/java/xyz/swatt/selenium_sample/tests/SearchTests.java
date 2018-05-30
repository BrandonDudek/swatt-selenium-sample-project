package xyz.swatt.selenium_sample.tests;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.TestException;
import org.testng.annotations.*;
import xyz.swatt.asserts.ArgumentChecks;
import xyz.swatt.selenium_sample.Globals;
import xyz.swatt.selenium_sample.pages.Google;
import xyz.swatt.selenium_sample.pages.SearchResult;

import java.util.*;

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
    //////////////////// Before ////////////////////
    /**
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    @BeforeClass
    public void beforeClass() {

    	LOGGER.info("beforeClass() [START]");

    	//------------------------ Pre-Checks ----------------------------------

    	//------------------------ CONSTANTS -----------------------------------

    	//------------------------ Variables -----------------------------------

    	//------------------------ Code ----------------------------------------
        google = new Google(BROWSER_TYPE); // Validation done inside method.

    	LOGGER.debug("beforeClass() [END]");
    }

    /**
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    @BeforeMethod
    public void beforeMethod() {

        LOGGER.info("beforeMethod() [START]");

        //------------------------ Pre-Checks ----------------------------------

        //------------------------ CONSTANTS -----------------------------------

        //------------------------ Variables -----------------------------------

        //------------------------ Code ----------------------------------------
        google.goHome(); // Validation done inside method.

        LOGGER.debug("beforeMethod() [END]");
    }

    //////////////////// Data Providers ////////////////////
    /**
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    @DataProvider
    public Object[][] searchTestData() {
        return new Object[][] {
                {"Letters", "Serenity"},
                {"Numbers", "123"},
                {"Multiple Words", "Science Fiction"},
                {"Quotes", "\"Young Justice\""}
                // etc.
        };
    }

    //////////////////// Tests ////////////////////
    /**
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    @Test(dataProvider = "searchTestData")
    public void searchTestWithEnter(String _testName, String _searchTerm) {

    	LOGGER.info("searchTestWithEnter(_testName: {}, _searchTerm: {}) [START]", _testName, _searchTerm);

    	//------------------------ Pre-Checks ----------------------------------

    	//------------------------ CONSTANTS -----------------------------------

    	//------------------------ Variables -----------------------------------
        List<SearchResult> searchResults;

    	//------------------------ Code ----------------------------------------
        searchResults = google.search(_searchTerm);

        ////////// Validate //////////
        validateSearchResults(_searchTerm, searchResults);

    	LOGGER.debug("searchTestWithEnter(_testName: {}, _searchTerm: {}) [END]", _testName, _searchTerm);
    }

    /**
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    @Test(dataProvider = "searchTestData")
    public void searchTestWithNormalSearchButton(String _testName, String _searchTerm) {

        LOGGER.info("searchTestWithNormalSearchButton(_testName: {}, _searchTerm: {}) [START]", _testName, _searchTerm);

        //------------------------ Pre-Checks ----------------------------------

        //------------------------ CONSTANTS -----------------------------------

        //------------------------ Variables -----------------------------------
        List<SearchResult> searchResults;

        //------------------------ Code ----------------------------------------
        Set<String> suggestions = google.setSearchInput(_searchTerm + Keys.ESCAPE); // Sending Escape to close Search Suggestions.
        searchResults = google.clickGoogleSearchButton();

        LOGGER.debug(suggestions);

        ////////// Validate //////////
        validateSearchResults(_searchTerm, searchResults);

        LOGGER.debug("searchTestWithNormalSearchButton(_testName: {}, _searchTerm: {}) [END]", _testName, _searchTerm);
    }

    /**
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    @Test(dataProvider = "searchTestData")
    public void searchTestWithSuggestionSearchButton(String _testName, String _searchTerm) {

        LOGGER.info("searchTestWithSuggestionSearchButton(_testName: {}, _searchTerm: {}) [START]", _testName, _searchTerm);

        //------------------------ Pre-Checks ----------------------------------

        //------------------------ CONSTANTS -----------------------------------

        //------------------------ Variables -----------------------------------
        List<SearchResult> searchResults;

        //------------------------ Code ----------------------------------------
        Set<String> suggestions = google.setSearchInput(_searchTerm);
        searchResults = google.clickGoogleSearchButton();

        LOGGER.debug(suggestions);

        ////////// Validate //////////
        validateSearchResults(_searchTerm, searchResults);

        LOGGER.debug("searchTestWithSuggestionSearchButton(_testName: {}, _searchTerm: {}) [END]", _testName, _searchTerm);
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

    //////////////////// Helper ////////////////////
    /**
     * Will validate that all of the Search Results contain the Search Term(s).
     *
     * @author Brandon Dudek (<a href="github.com/BrandonDudek">BrandonDudek</a>)
     */
    public void validateSearchResults(String _searchString, Collection<SearchResult> _searchResults) {

    	LOGGER.info("validateSearchResults(_searchString: {}, _searchResults: ({})) [START]", _searchString,
                (_searchResults == null ? "NULL" : _searchResults.size()));

    	//------------------------ Pre-Checks ----------------------------------
        ArgumentChecks.notNull(_searchString, "Search String");
        ArgumentChecks.notNull(_searchResults, "Search Results");

    	//------------------------ CONSTANTS -----------------------------------

    	//------------------------ Variables -----------------------------------

    	//------------------------ Code ----------------------------------------
        if(_searchResults.isEmpty()) {
            throw new TestException("No Results Found!");
        }

        List<String> terms;
        boolean isOneQuoteTerm = _searchString.startsWith("\"") && _searchString.endsWith("\"") && StringUtils.countMatches(_searchString, "\"") == 2;
        if(isOneQuoteTerm) {
            terms = Collections.singletonList(_searchString.substring(1, _searchString.length() - 1));
        }
        else {
            terms = Arrays.asList(_searchString.split("\\s+"));
        }
        for(String searchTerm : terms) {

            searchTerm = searchTerm.toLowerCase();

            for(SearchResult searchResult : _searchResults) {

                String title = searchResult.getTitle();
                if(title.toLowerCase().contains(searchTerm)) {
                    continue;
                }

                String description = searchResult.getDescription();
                if(description.toLowerCase().contains(searchTerm)) {
                    continue;
                }

                ///// Not Found /////
                String url = searchResult.getUrl();
                throw new TestException("Search term was not found in Search Result!\n\tTerm: " + searchTerm + "\n\t:Title: " + title + "\n\tDescription: "
                        + description + "\n\tURL: " + url);
            }
        }

    	LOGGER.debug("validateSearchResults(_searchString: {}, _searchResults: ({})) [END]", _searchString, _searchResults.size());
    }

    //========================= Classes ========================================
}
