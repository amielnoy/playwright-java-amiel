package utils.properties;

import BuildingBlocks.LoginBuildingBlock;
import base.BaseTests;
import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.DashBoardPage;
import pages.LoginPage;

import java.nio.file.Paths;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


public class LoginToStagingSleep extends BaseTests {

    BrowserContext currContext;
    String nonSecretPropertiesFullPath,SecretPropertiesFullPath;
    String currTestName;
    Page page;

    @BeforeClass
    public void setUp() {
        String loginUrl="";
        //Open a browser (supports Chromium (Chrome, Edge), Firefox, and Webkit (Safari))
        browser = Playwright
                .create()
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("./video")));

        //A single browser tab
        page = context.newPage();
        //C:\Users\amiel\IdeaProjects\playwright-java-amiel
        nonSecretPropertiesFullPath=System.getProperty("user.dir")
                +"\\src\\test\\resources\\enviornmentNonSecrets.properties";
        SecretPropertiesFullPath=System.getProperty("user.dir")
                +"\\src\\test\\resources\\enviornmentSecrets.properties";

        loginUrl= PropertiesOperations.getProperty("website_url",nonSecretPropertiesFullPath);
        page.navigate(loginUrl);
    }

    //TODO midiating layer
    //@Test
    public void loginToItamar() {
        LoginPage loginPage =  new LoginPage(page);
        String title = "Agile Testing";
        String expectedUsername=PropertiesOperations.getProperty("username",SecretPropertiesFullPath);
        String expectedPassword=PropertiesOperations.getProperty("password1",SecretPropertiesFullPath);

        String actualUsername=loginPage.setUserName(expectedUsername);
        String actualPassword=loginPage.setPassword(expectedPassword);

        assertEquals("Username set Failed",expectedUsername, actualUsername);
        assertEquals("Password set failed",expectedPassword,actualPassword);
        loginPage.clickLoginButton();
        //Verify Succesfull login
        DashBoardPage dashBoardPage=new DashBoardPage(page);
        String actualDashBoardUpperTitle=dashBoardPage.getTopDashBoardTitle();
        assertEquals("We failed getting the correct dashboard page"
                ,"We are happy to announce new features in CloudPAT",actualDashBoardUpperTitle);
    }

    @Test
    public void LoginToItamarUsingLoginBuildingBlock(){
        LoginBuildingBlock myLoginBuildingBlock=new LoginBuildingBlock(page);
        String expectedUsername=PropertiesOperations.getProperty("username",SecretPropertiesFullPath);
        String expectedPassword=PropertiesOperations.getProperty("password1",SecretPropertiesFullPath);

        String actualOutput=myLoginBuildingBlock.loginToItamar(expectedUsername,expectedPassword);

        assertEquals("Username set Failed",expectedUsername, actualOutput.split(",")[0]);
        assertEquals("Password set failed",expectedPassword,actualOutput.split(",")[1]);

        //Verify Succesfull login
        DashBoardPage dashBoardPage=new DashBoardPage(page);
        String actualDashBoardUpperTitle=dashBoardPage.getTopDashBoardTitle();
        assertEquals("We failed getting the correct dashboard page"
                ,"We are happy to announce new features in CloudPAT",actualDashBoardUpperTitle);

    }
//    @Test
//    public void negativeLoginToItamar(Method testMethod
//
//            }

    @AfterMethod
    public void tearDown(ITestResult testResult){
        if(testResult.isSuccess()==false)
            onTestFailure(testResult);
    }
}
