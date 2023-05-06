package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.microsoft.playwright.options.WaitForSelectorState.ATTACHED;


public class DashBoardPage {

    private Page page;

    private String locator_top_title = "We are happy to announce new features in CloudPAT";

    public DashBoardPage(Page page) {
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

    public String getTopDashBoardTitle() {
        Locator userTopDashboardtitle= page.getByText(locator_top_title);
        return userTopDashboardtitle.innerHTML();
    }
}