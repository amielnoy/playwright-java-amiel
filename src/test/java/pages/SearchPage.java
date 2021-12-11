package pages;
import org.apache.commons.io.FileUtils;
import com.microsoft.playwright.Page;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.options.WaitForSelectorState.ATTACHED;
import static com.microsoft.playwright.options.WaitForSelectorState.DETACHED;


public class SearchPage {

    private Page page;

    private String locator_searchBar = "#searchBar";
    private String locator_hiddenBooks = "li.ui-screen-hidden";
    private String locator_visibleBooks = "li:not(.ui-screen-hidden)";
    private String locator_visibleBookTitles = "li:not(.ui-screen-hidden) h2";


    public SearchPage(Page page) {
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

    public void search(String query) {
        clearSearchBar();
        page.fill(locator_searchBar, query);

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