package myaccounts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import utilities.Utility;

public class MyAccountsTest extends Utility {
    String baseUrl = "http://tutorialsninja.com/demo/index.php";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    public void selectMyAccountOptions(String option) {
        clickOnElement(By.xpath("//a[normalize-space()='" + option + "']"));
    }

    @Test
    public void verifyUserShouldNavigateToRegisterPageSuccessfully() {
        clickOnElement(By.xpath("//span[normalize-space()='My Account']"));
        selectMyAccountOptions("Register");
        String expRegHeading = "Register Account";
        String actRegHeading = getTextFromElement(By.xpath("//h1[normalize-space()='Register Account']"));
        verifyText("Invalid Heading", expRegHeading, actRegHeading);
    }

    @Test
    public void verifyUserShouldNavigateToLoginPageSuccessfully() {
        clickOnElement(By.xpath("//span[normalize-space()='My Account']"));
        selectMyAccountOptions("Login");
        String expRegHeading = "Returning Customer";
        String actRegHeading = getTextFromElement(By.xpath("//h2[normalize-space()='Returning Customer']"));
        verifyText("Invalid Heading", expRegHeading, actRegHeading);
    }

    @Test
    public void verifyThatUserRegisterAccountSuccessfully() {
        clickOnElement(By.xpath("//span[normalize-space()='My Account']"));
        selectMyAccountOptions("Register");

        String userName = "" + (int) (Math.random() * Integer.MAX_VALUE);            //Create random username
        String emailID = "User" + userName + "@example.com";
        sendTextToElement(By.id("input-email"),emailID);

        sendTextToElement(By.id("input-firstname"), "Prime");
        sendTextToElement(By.id("input-lastname"), "testing");
        sendTextToElement(By.id("input-email"), "Prime1@gmail.com");
        sendTextToElement(By.id("input-telephone"), "07745489933");
        sendTextToElement(By.id("input-password"), "pt12345");
        sendTextToElement(By.id("input-confirm"), "pt12345");
        clickOnElement(By.xpath("//label[normalize-space()='Yes']"));
        clickOnElement(By.xpath("//input[@name='agree']"));
        clickOnElement(By.xpath("//input[@value='Continue']"));

        String expRegHeading = "Your Account Has Been Created!";
        String actRegHeading = getTextFromElement(By.xpath("//h1[normalize-space()='Your Account Has Been Created!']"));
        verifyText("Invalid Heading", expRegHeading, actRegHeading);

        clickOnElement(By.xpath("//a[normalize-space()='Continue']"));
        clickOnElement(By.xpath("//span[normalize-space()='My Account']"));
        clickOnElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Logout']"));

        String expLogOutHeading = "Account Logout";
        String actLogOutHeading = getTextFromElement(By.xpath("//div[@id='content']//h1"));
        verifyText("Invalid Heading", expLogOutHeading, actLogOutHeading);

        clickOnElement(By.xpath("//a[normalize-space()='Continue']"));
    }

    @Test
    public void verifyThatUserShouldLoginAndLogoutSuccessfully() {
        clickOnElement(By.xpath("//span[normalize-space()='My Account']"));
        selectMyAccountOptions("Login");

        sendTextToElement(By.id("input-email"), "Prime1@gmail.com");
        sendTextToElement(By.id("input-password"), "pt12345");
        clickOnElement(By.xpath("//input[@value='Login']"));

        String expHeading = "My Account";
        String actHeading = getTextFromElement(By.xpath("//div[@id='content']//h2[1]"));
        verifyText("Invalid Heading", expHeading, actHeading);

        clickOnElement(By.xpath("//span[normalize-space()='My Account']"));
        clickOnElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Logout']"));

        String expLogOutHeading = "Account Logout";
        String actLogOutHeading = getTextFromElement(By.xpath("//div[@id='content']//h1"));
        verifyText("Invalid Heading", expLogOutHeading, actLogOutHeading);

        clickOnElement(By.xpath("//a[normalize-space()='Continue']"));
    }

    @After
    public void tearDown() {
        closeBrowser();
    }
}
