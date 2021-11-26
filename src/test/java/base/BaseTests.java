package base;

import com.microsoft.playwright.*;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.SearchPage;

public class BaseTests {

    protected Browser browser;
    protected SearchPage searchPage;


    @AfterClass
    public void tearDown() {
        browser.close();
    }
}
