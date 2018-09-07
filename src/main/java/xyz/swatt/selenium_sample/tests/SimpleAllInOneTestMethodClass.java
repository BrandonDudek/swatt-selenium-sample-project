package xyz.swatt.selenium_sample.tests;

import org.testng.TestException;
import org.testng.annotations.Test;
import xyz.swatt.selenium.WebDriverWrapper;
import xyz.swatt.selenium_sample.pages.Google;
import xyz.swatt.selenium_sample.pages.SearchResult;

import java.util.List;

/**
 * This Class/Method is for Demo Purposes only.
 * Your code should not be organized in this way.
 * See {@link SearchTests} for Proper Implementation.
 */
@Deprecated
public class SimpleAllInOneTestMethodClass {

    @Test
    public void searchTest() {

        // Launch the Web Browser.
        Google google = new Google(WebDriverWrapper.ChromeBrowser.CHROME);

        // Go to the Google home page.
        google.goHome(); // Validation done inside method.

        // Perform Search.
        final String SEARCH_TERM = "Serenity";
        List<SearchResult> searchResults = google.search(SEARCH_TERM); // Validation that A search was done inside method.

        ///// Validate Search Results ///// (Done outside search method. Because it could take a while, and validation could be context dependant.)
        if(searchResults.isEmpty()) {
            throw new TestException("No Results Found!");
        }
        for(SearchResult searchResult : searchResults) {
            // Title.
            String title = searchResult.getTitle();
            if(title.toLowerCase().contains(SEARCH_TERM.toLowerCase())) {
                continue;
            }
            // Description.
            String description = searchResult.getDescription();
            if(description.toLowerCase().contains(SEARCH_TERM.toLowerCase())) {
                continue;
            }
            // Not Found.
            String url = searchResult.getUrl();
            throw new TestException("Search term was not found in Search Result!\n\tTerm: " + SEARCH_TERM + "\n\t:Title: " + title + "\n\tDescription: "
                    + description + "\n\tURL: " + url);
        }
    }
}
