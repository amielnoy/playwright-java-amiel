package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.options.WaitForSelectorState.ATTACHED;
import static com.microsoft.playwright.options.WaitForSelectorState.DETACHED;


public class LoginPage extends BasePage {

    private Page page;

    private final String locator_username_edit = "input[type=\"text\"]";
    private final String locator_password_edit = "input[type=\"password\"]";
    private final String locator_login_button = "sign in";
    private final String locator_visibleBookTitles = "li:not(.ui-screen-hidden) h2";


    public LoginPage(Page page) {
        super(page);
        this.page = page;
    }


    public String setUserName(String username) {
        Locator userNameLocator= page.locator(locator_username_edit);
        userNameLocator.fill(username);
        //Log value
        var expectedState = new Page.WaitForSelectorOptions().setState(ATTACHED);
        page.waitForSelector(locator_username_edit, expectedState);
        return userNameLocator.inputValue();
    }

    public String setPassword(String password){
        Locator passwordLocator= page.locator(locator_password_edit);
        passwordLocator.fill(password);
        //Log value
        var expectedState = new Page.WaitForSelectorOptions().setState(ATTACHED);
        page.waitForSelector(locator_password_edit, expectedState);
        return passwordLocator.inputValue();
    }

    public void clickLoginButton(){
        Locator button_locator=page.getByText("sign in");
        button_locator.click();
    }
}