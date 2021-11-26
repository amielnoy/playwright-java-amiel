package search;
import com.microsoft.playwright.*;
import base.BaseTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.SearchPage;


import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


public class SearchTests2 extends BaseTests {
    @BeforeClass
    public void setUp() {

        //Open a browser (supports Chromium (Chrome, Edge), Firefox, and Webkit (Safari))
        browser = Playwright
                .create()
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false));

        //A single browser tab
        Page page = browser.newPage();
        page.navigate("https://automationbookstore.dev/");
        searchPage = new SearchPage(page);
    }
    @Test
    public void searchForExactTitle() {
        String title = "Agile Testing";
        searchPage.search(title);
        assertEquals("Number of visible books",searchPage.getNumberOfVisibleBooks(), 1 );
        assertTrue("Title of visible book",searchPage.getVisibleBooks().contains(title));
    }

    @Test
    public void searchForPartialTitle() {
        searchPage.search("Test");

        List<String> expectedBooks = List.of(
                "Test Automation in the Real World",
                "Experiences of Test Automation",
                "Agile Testing",
                "How Google Tests Software",
                "Java For Testers"
        );

        assertEquals("Number of visible books",searchPage.getNumberOfVisibleBooks(), expectedBooks.size());
        assertEquals("Titles of visible books",searchPage.getVisibleBooks(), expectedBooks);
    }


}
