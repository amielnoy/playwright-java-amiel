package utils.properties;

import com.microsoft.playwright.Page;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Recording {
    public static void takeScreenShot(String browserType,Page page){
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./ScreenShots/"  +browserType+".png")));
    }

    public static void setVideoName(String newName,Page page) throws IOException {
        Path videoFileSourcePath = Paths.get(String.valueOf(page.video().path()));
        Path videoFileTargetPath=Paths.get("./video/"+newName+".webm");
        Files.copy(videoFileSourcePath,videoFileTargetPath);
        FileUtils.forceDelete(videoFileSourcePath.toFile());
    }

}
