package pages;

import com.microsoft.playwright.Page;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.options.WaitForSelectorState.ATTACHED;
import static com.microsoft.playwright.options.WaitForSelectorState.DETACHED;


public class LoginPage {

    private Page page;

    private String locator_username_edit = "#searchBar";
    private String locator_password_edit = "li.ui-screen-hidden";
    private String locator_login_button = "li:not(.ui-screen-hidden)";
    private String locator_visibleBookTitles = "li:not(.ui-screen-hidden) h2";


    public LoginPage(Page page) {
        this.page = page;
    }

    public void takeScreenShot(String browserType){
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./ScreenShots/"  +browserType+".png")));
    }

    public void setVideoName(String newName) throws IOException {
        Path videoFileSourcePath = Paths.get(String.valueOf(page.video().path()));
        Path videoFileTargetPath=Paths.get("./video/"+newName+".webm");
        Files.copy(videoFileSourcePath,videoFileTargetPath);
        FileUtils.forceDelete(videoFileSourcePath.toFile());
    }

    public void setUserName(String username) {
        //clearSearchBar();
        page.fill(locator_username_edit, username);
        //Log value
        var expectedState = new Page.WaitForSelectorOptions().setState(ATTACHED);
        page.waitForSelector(locator_hiddenBooks, expectedState);
    }

    public void clearSearchBar() {
        page.fill(locator_searchBar, "");

        var expectedState = new Page.WaitForSelectorOptions().setState(DETACHED);
        page.waitForSelector(locator_hiddenBooks, expectedState);
    }

    public int getNumberOfVisibleBooks() {
        return page.querySelectorAll(locator_visibleBooks).size();
    }

    public List<String> getVisibleBooks() {
        return page.querySelectorAll(locator_visibleBookTitles)
                .stream()
                .map(e -> e.innerText())
                .collect(Collectors.toList());
    }
}