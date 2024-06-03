package laptopsandnotebooks;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import utilities.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LaptopsAndNotebooksTest extends Utility {
    String baseUrl = "http://tutorialsninja.com/demo/index.php";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    @Test
    public void verifyProductsPriceDisplayHighToLowSuccessfully() {
        mouseHoverAndClickOnElement(By.xpath("//a[normalize-space()='Laptops & Notebooks']"));
        selectMenu("Show AllLaptops & Notebooks");
        selectByVisibleTextFromDropDown(By.id("input-sort"), "Price (High > Low)");

        List<Double> actPriceList = new ArrayList<>();
        List<WebElement> listOfPrices = driver.findElements(By.xpath("//div[@class='product-layout product-grid col-lg-4 col-md-4 col-sm-6 col-xs-12']//div[1]//div[2]//div[1]//p//span[@class='price-tax']"));
        for (WebElement element : listOfPrices) {
            String price = element.getText().replaceAll("[E,x,T,a,x,:,$]", "").replace(",", "");
            Double priceValue = Double.parseDouble(price);
            actPriceList.add(priceValue);
        }

        List<Double> sortedList = new ArrayList<>(actPriceList);
        Collections.sort(sortedList, Collections.reverseOrder());

        Assert.assertEquals(actPriceList, sortedList);

        clickOnElement(By.xpath("//a[normalize-space()='Sony VAIO']"));       //select Sony VAIO
        clickOnElement(By.id("button-cart"));           //Click on add to cart

        String expMessage = "Success: You have added Sony VAIO to your shopping cart!";
        String actMessage = getTextFromElement(By.xpath("//div[@class='alert alert-success alert-dismissible']")).substring(0, 56);
        verifyText("Invalid success message", expMessage, actMessage);

        clickOnElement(By.xpath("//a[normalize-space()='shopping cart']"));         //click on shopping cart from success message

        String expCartHeading = "Shopping Cart";
        String actCartHeading = getTextFromElement(By.xpath("//h1[contains(text(),'Shopping Cart')]")).substring(0, 13);
        verifyText("Invalid Cart Heading", expCartHeading, actCartHeading);

        String actName = getTextFromElement(By.xpath("//div[@class='table-responsive']//tbody//td[@class='text-left']//a[text()='Sony VAIO']"));
        verifyText("Invalid name", "Sony VAIO", actName);

        driver.findElement(By.xpath("//input[@class='form-control']")).clear();
        sendTextToElement(By.xpath("//input[@class='form-control']"), "2");

        clickOnElement(By.xpath("//button[@type='submit']"));       //click on update button

        String expModifiedMessage = "Success: You have modified your shopping cart!";
        String actModifiedMessage = getTextFromElement(By.xpath("//div[@class='alert alert-success alert-dismissible']")).substring(0, 46);
        verifyText("Invalid success message", expModifiedMessage, actModifiedMessage);

        String expTotal = "$2,404.00";
        WebElement toRightOf = driver.findElement(RelativeLocator.with(By.xpath("//td[@class='text-right']")).toRightOf(By.xpath("//strong[normalize-space()='Total:']")));
        String actTotal = toRightOf.getText();
        verifyText("Invalid total", expTotal, actTotal);

        clickOnElement(By.xpath("//a[@class='btn btn-primary']"));      // click on checkout

        String expNewCust = "New Customer";
        String actNewCust = getTextFromElement(By.xpath("//h2[normalize-space()='New Customer']"));
        verifyText("Invalid success message", expNewCust, actNewCust);

        clickOnElement(By.xpath("//input[@value='guest']"));            //CLick on guest checkout radio
        clickOnElement(By.id("button-account"));            //Click on continue

        sendTextToElement(By.id("input-payment-firstname"),"Prime");
        sendTextToElement(By.id("input-payment-lastname"),"Testing");
        sendTextToElement(By.id("input-payment-email"),"ptesting1@gmail.com");
        sendTextToElement(By.id("input-payment-telephone"),"07723884629");
        sendTextToElement(By.id("input-payment-address-1"),"43, prime street");
        sendTextToElement(By.id("input-payment-city"),"solihull");
        sendTextToElement(By.id("input-payment-postcode"),"B93BGG");
        selectByVisibleTextFromDropDown(By.id("input-payment-country"),"United Kingdom");
        selectByValueFromDropDown(By.id("input-payment-zone"),"3516");

        clickOnElement(By.id("button-guest"));          //Click on continue
        sendTextToElement(By.xpath("//textarea[@name='comment']"),"Test text");
        clickOnElement(By.id("button-shipping-method"));          //Click on continue
        clickOnElement(By.xpath("//input[@name='agree']"));          //Click on continue
        clickOnElement(By.id("button-payment-method"));          //Click on continue
        clickOnElement(By.id("button-confirm"));          //Click on confirm

    }


    @After
    public void tearDown() {
        closeBrowser();
    }
}
