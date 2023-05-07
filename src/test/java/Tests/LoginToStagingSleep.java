package utils.properties;

import BuildingBlocks.LoginBuildingBlock;
import Tests.BaseTest;
import com.microsoft.playwright.*;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashBoardPage;
import pages.LoginPage;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.AssertJUnit.assertEquals;


public class LoginToStagingSleep extends BaseTest {

    //BrowserContext currContext;
    String loginUrl="";
    String nonSecretPropertiesFullPath,SecretPropertiesFullPath;
    //String currTestName;


    @BeforeMethod
    public void setUp() {

        //Open a browser (supports Chromium (Chrome, Edge), Firefox, and Webkit (Safari))
        browser = Playwright
                .create()
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false));
                context = browser.newContext(new Browser.NewContextOptions()
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



    //@Test

    @Test
    public void LoginToItamar(){
        LoginBuildingBlock myLoginBuildingBlock=new LoginBuildingBlock(page);
        String expectedUsername=PropertiesOperations.getProperty("username",SecretPropertiesFullPath);
        String expectedPassword=PropertiesOperations.getProperty("password1",SecretPropertiesFullPath);

        String expectedWebUrlAfterSuccesfulLogin=PropertiesOperations.getProperty("main_url"
                ,nonSecretPropertiesFullPath);
        String actualOutput=myLoginBuildingBlock.loginToItamar(expectedUsername,expectedPassword
                ,expectedWebUrlAfterSuccesfulLogin);

        assertEquals("Username set Failed",expectedUsername, actualOutput.split(",")[0]);
        assertEquals("Password set failed",expectedPassword,actualOutput.split(",")[1]);


        //Verify Succesfull login
        //First verify correct URL has been reached!
        assertEquals("Failed to navigate to the main web page expected after successfull login",
                expectedWebUrlAfterSuccesfulLogin,this.getCurrentUrl());
        //Second check the unique text on the greeting screen!
        DashBoardPage dashBoardPage=new DashBoardPage(page);
        String actualDashBoardUpperTitle=dashBoardPage.getTopDashBoardTitle();
        assertEquals("We failed getting the correct dashboard page"
                ,"We are happy to announce new features in CloudPAT",actualDashBoardUpperTitle);

    }


    @Issue("TAP-0001")
    @TmsLink("STORY-111")
    @Story("POSITIVE FLOW")
    @Owner("Kamil Nowocin")
    @Severity(SeverityLevel.CRITICAL)
    @Description("As a user I can log into itamar using username & password")
    @Test(description = "As a user I can log into itamar using username & password",
            priority = 0)
    public void LoginToItamarUsingPageObject(){
        LoginPage loginPage=new LoginPage(page);
        String expectedUsername=PropertiesOperations.getProperty("username",SecretPropertiesFullPath);
        String expectedPassword=PropertiesOperations.getProperty("password1",SecretPropertiesFullPath);

        String expectedWebUrlAfterSuccesfulLogin=PropertiesOperations.getProperty("main_url"
                ,nonSecretPropertiesFullPath);
        String actualUsername=loginPage.setUserName(expectedUsername);
        String actualPassword=loginPage.setPassword(expectedPassword);

        assertEquals("Username set Failed",expectedUsername, actualUsername);
        assertEquals("Password set failed",expectedPassword,actualPassword);
        loginPage.clickLoginButton();

        //Second check the unique text on the greeting screen!
        DashBoardPage dashBoardPage=new DashBoardPage(page);
        String actualDashBoardUpperTitle=dashBoardPage.getTopDashBoardTitle();
        assertEquals("We failed getting the correct dashboard page"
                ,"We are happy to announce new features in CloudPAT",actualDashBoardUpperTitle);


        assertThat(page).hasURL(expectedWebUrlAfterSuccesfulLogin);

        //Verify Succesfull login
        //First verify correct URL has been reached!
        assertEquals("Failed to navigate to the main web page expected after successfull login",
                expectedWebUrlAfterSuccesfulLogin,this.getCurrentUrl());

    }
    @Test
    public void negativeLoginToItamar() throws InterruptedException {
        LoginBuildingBlock myLoginBuildingBlock=new LoginBuildingBlock(page);
        String expectedUsername=PropertiesOperations.getProperty("username",SecretPropertiesFullPath);
        String expectedPassword=PropertiesOperations.getProperty("password1",SecretPropertiesFullPath);
        expectedUsername=expectedUsername+"error";
        String expectedWebUrlAfterSuccesfulLogin=PropertiesOperations.getProperty("main_url"
                ,nonSecretPropertiesFullPath);

        String actualOutput=myLoginBuildingBlock.loginToItamar(expectedUsername,expectedPassword
                ,expectedWebUrlAfterSuccesfulLogin);

        assertEquals("Username set Failed",expectedUsername, actualOutput.split(",")[0]);
        assertEquals("Password set failed",expectedPassword,actualOutput.split(",")[1]);

        //Verify Succesfull login
        //First verify correct URL has been reached!
        //assertEquals("Failed to navigate to the main web page expected after successfull login",
        //        expectedWebUrlAfterSuccesfulLogin,this.getCurrentUrl());
        //Verify Succesfull login

        DashBoardPage dashBoardPage=new DashBoardPage(page);
        //this.page=dashBoardPage.getPage();
        String actualDashBoardUpperTitle=dashBoardPage.getTopDashBoardTitle();
        assertEquals("We failed getting the correct dashboard page"
                ,"We are happy to announce new features in CloudPAT",actualDashBoardUpperTitle);
    }


}
