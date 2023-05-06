package Tests.base;

import com.microsoft.playwright.*;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import pages.BasePage;
import pages.DashBoardPage;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.time.LocalTime.now;

public class BaseTests implements ITestListener  {

    protected Browser browser;
    protected Page page;


    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("***** Error "+result.getName()+" test has failed *****");
        String methodName=result.getName().trim();
        ITestContext context = result.getTestContext();
        BasePage basePage=new BasePage(page);
        if(result.isSuccess()==false) {
            basePage.takeScreenShot(methodName + "-" + LocalDateTime.now().toString().replace(":","_") + "_failed");
            try {
                basePage.setVideoName(methodName + "-" +  LocalDateTime.now().toString().replace(":","_") + "_failed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    public void tearDown() {
        browser.close();
    }
}
