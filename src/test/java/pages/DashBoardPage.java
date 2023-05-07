package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.microsoft.playwright.options.WaitForSelectorState.ATTACHED;


public class DashBoardPage extends BasePage{

    private Page page;
    private static final String PAGE_TITLE="Itamar Medical";
    private String locator_top_title = "We are happy to announce new features in CloudPAT";

    public DashBoardPage(Page page) {
        super(page);
        this.page = page;
    }

    public static String getPageTitle(){
        return PAGE_TITLE;
    }
    public String getTopDashBoardTitle() {
        Locator userTopDashboardtitle= page.getByText(locator_top_title);
        assertThat(page).hasTitle(PAGE_TITLE);
        return userTopDashboardtitle.innerHTML();
    }
}