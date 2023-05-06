package BuildingBlocks;

import com.microsoft.playwright.Page;
import pages.DashBoardPage;
import pages.LoginPage;

public class LoginBuildingBlock {
    Page page;
    public LoginBuildingBlock(Page page){
        this.page=page;
    };

    public String loginToItamar(String userName, String password){
        LoginPage loginPage=new LoginPage(page);
        String actualOutput=loginPage.setUserName(userName);
        actualOutput=actualOutput+","+loginPage.setPassword(password);
        loginPage.clickLoginButton();
        return actualOutput;
    }
}
