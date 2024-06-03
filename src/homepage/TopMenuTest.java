package homepage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import utilities.Utility;

public class TopMenuTest extends Utility {
    String baseUrl = "http://tutorialsninja.com/demo/index.php";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    @Test
    public void verifyUserShouldNavigateToDesktopsPageSuccessfully(){
        mouseHoverAndClickOnElement(By.xpath("//a[normalize-space()='Desktops']"));
        selectMenu("Show AllDesktops");
        String actDesktopHeading = getTextFromElement(By.xpath("//h2[normalize-space()='Desktops']"));
        verifyText("Invalid desktop heading","Desktops", actDesktopHeading);
    }

    @Test
    public void verifyUserShouldNavigateToLaptopsAndNotebooksPageSuccessfully(){
        mouseHoverAndClickOnElement(By.linkText("Laptops & Notebooks"));
        selectMenu("Show AllLaptops & Notebooks");
        String actDesktopHeading = getTextFromElement(By.xpath("//h2[normalize-space()='Laptops & Notebooks']"));
        verifyText("Invalid Laptops & Notebooks heading","Laptops & Notebooks", actDesktopHeading);
    }

    @Test
    public void verifyUserShouldNavigateToComponentsPageSuccessfully(){
        mouseHoverAndClickOnElement(By.linkText("Components"));
        selectMenu("Show AllComponents");
        String actDesktopHeading = getTextFromElement(By.xpath("//h2[normalize-space()='Components']"));
        verifyText("Invalid Components heading","Components", actDesktopHeading);
    }



    @After
    public void tearDown() {
        closeBrowser();
    }
}
