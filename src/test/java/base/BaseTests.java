package base;

import com.microsoft.playwright.*;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.time.LocalTime.now;

public class BaseTests implements ITestListener  {

    protected Browser browser;
    protected SearchPage searchPage;



    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("***** Error "+result.getName()+" test has failed *****");
        String methodName=result.getName().trim();
        ITestContext context = result.getTestContext();
        if(result.isSuccess()==false) {
            searchPage.takeScreenShot(methodName + "-" + LocalDateTime.now().toString().replace(":","_") + "_failed");
            try {
                searchPage.setVideoName(methodName + "-" +  LocalDateTime.now().toString().replace(":","_") + "_failed");
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
