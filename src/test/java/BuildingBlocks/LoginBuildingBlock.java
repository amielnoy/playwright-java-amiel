package BuildingBlocks;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import pages.DashBoardPage;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginBuildingBlock {
    Page page;
    public LoginBuildingBlock(Page page){
        this.page=page;
    };
    @Step("Setting user={userName}Password={password}")
    public String loginToItamar(String userName, String password,String expectedUrl){
        LoginPage loginPage=new LoginPage(page);
        String actualOutput=loginPage.setUserName(userName);
        actualOutput=actualOutput+","+loginPage.setPassword(password);
        loginPage.clickLoginButton();
        assertThat(page).hasURL(expectedUrl);
        return actualOutput;
    }
}
