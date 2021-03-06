package search;
import com.microsoft.playwright.*;
import base.BaseTests;


import org.junit.jupiter.api.TestInstance;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.SearchPage;


import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


public class SearchTests1 extends BaseTests {

    BrowserContext currContext;
    String currTestName;
    Page page;

    @BeforeClass
    public void setUp() {

        //Open a browser (supports Chromium (Chrome, Edge), Firefox, and Webkit (Safari))
        browser = Playwright
                .create()
                .firefox()
                .launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("./video")));

        //A single browser tab
        page = context.newPage();
        page.navigate("https://automationbookstore.dev/");
    }

    @Test
    public void searchForExactTitle() {

        searchPage = new SearchPage(page);
        String title = "Agile Testing";
        searchPage.search(title);

        assertEquals("Number of visible books",searchPage.getNumberOfVisibleBooks(), 1);
        assertTrue("Title of visible book",searchPage.getVisibleBooks().contains(title));

    }

    @Test
    public void searchForPartialTitle() {
        searchPage = new SearchPage(page);
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

    @Test
    public void searchForPartialTitle2(Method testMethod
            ,ITestContext testContext) {
        searchPage = new SearchPage(page);
        searchPage.search("Java");

        List<String> expectedBooks = List.of(
                "The cucumber for java book"
                ,"Advanced selenium in Java"
                ,"Java For Testers"
        );

        assertEquals("Number of visible books",searchPage.getNumberOfVisibleBooks(), expectedBooks.size());
        assertEquals("Titles of visible books",searchPage.getVisibleBooks().stream().sorted().collect(Collectors.toList())
                , expectedBooks.stream().sorted().collect(Collectors.toList()));
    }
    @AfterMethod
    public void tearDown(ITestResult testResult){
        if(testResult.isSuccess()==false)
            onTestFailure(testResult);
    }
}
