package pages;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class BasePage {

    private Page page;
    //public static org.apache.logging.log4j.Logger log = new Logger.(this.cla);

    public BasePage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void takeScreenShot(String browserType) throws FileNotFoundException {
        try {
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./ScreenShots/" + browserType + ".png")));
            String imageFullPath = System.getProperty("user.dir") + "\\ScreenShots\\" + browserType + ".png";
            //Allure.addAttachment("Allure Screenshot", "image/png", new FileInputStream(imageFullPath).toString());
            Allure.addAttachment("Allure Screenshot",new FileInputStream(imageFullPath));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setVideoName(String newName, BrowserContext context, boolean isFail) throws IOException {
        Path videoFileSourcePath = Paths.get(String.valueOf(page.video().path()));

        Path videoFileTargetPath = Paths.get(System.getProperty("user.dir") + "/video/" + newName + ".webm");
        //page.video().saveAs(videoFileTargetPath);
        context.close();
        if (isFail)
            Files.copy(videoFileSourcePath, videoFileTargetPath);
        if (videoFileTargetPath.toFile().exists()) {
            Allure.addAttachment("Test Failure video", "video/mp4", new FileInputStream(videoFileTargetPath.toFile()), ".mp4");
            //Allure.addAttachment("Failure Video","video/mp4", videoFileTargetPath.toFile().getAbsolutePath());
            FileUtils.forceDelete(videoFileSourcePath.toFile());
        }

    }
}