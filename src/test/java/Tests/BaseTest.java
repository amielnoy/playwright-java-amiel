package Tests;

import com.microsoft.playwright.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import pages.BasePage;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static java.time.LocalTime.now;

public class BaseTest implements ITestListener  {

    protected Browser browser;
    protected Page page;
    protected Path newVideoPath;
    protected BrowserContext context;

    private static final Logger logger = LogManager.getLogger(BasePage.class.getName());
    protected String getCurrentUrl(){
        return page.url();
    }
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("***** Error "+result.getName()+" test has failed *****");
        String methodName=result.getName().trim();
        ITestContext context = result.getTestContext();
        BasePage basePage=new BasePage(page);

        if(result.isSuccess()==false) {
            try {
                logger.debug("Test="+ methodName +" Failed adding snapshot to ALLURE REPORT");
                basePage.takeScreenShot(methodName + "-" + LocalDateTime.now().toString().replace(":","_") + "_failed");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                basePage.setVideoName(methodName + "-" +  LocalDateTime.now().toString().replace(":","_") + "_failed"
                        ,this.context,true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                logger.debug("Test="+ methodName +" Failed adding VIDEO to ALLURE REPORT");
                basePage.setVideoName("",this.context,false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @AfterMethod
    public void tearDown(ITestResult testResult){
            onTestFailure(testResult);
    }
}
