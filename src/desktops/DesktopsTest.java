package desktops;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utilities.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DesktopsTest extends Utility {

    String baseUrl = "http://tutorialsninja.com/demo/index.php";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }


    @Test
    public void verifyProductArrangeInAlphaBeticalOrder(){
        mouseHoverAndClickOnElement(By.xpath("//a[normalize-space()='Desktops']"));
        selectMenu("Show AllDesktops");
        selectByVisibleTextFromDropDown(By.id("input-sort"),"Name (Z - A)");

        List<String> actProductList=new ArrayList<>();
        List<WebElement> listOfProducts= driver.findElements(By.xpath("//div[@class='product-layout product-grid col-lg-4 col-md-4 col-sm-6 col-xs-12']/div[1]/div[2]/div[1]/h4"));
        for(WebElement element : listOfProducts){
            actProductList.add(element.getText());
        }

        List<String> sortedList = new ArrayList<>(actProductList);

        Collections.sort(sortedList, Collections.reverseOrder(String::compareToIgnoreCase));
        Assert.assertEquals(actProductList, sortedList);
    }

    @Test
    public void verifyProductAddedToShoppingCartSuccessFully() throws InterruptedException {
        mouseHoverAndClickOnElement(By.xpath("//a[normalize-space()='Desktops']"));
        selectMenu("Show AllDesktops");
        selectByVisibleTextFromDropDown(By.id("input-sort"),"Name (Z - A)");

        clickOnElement(By.xpath("//a[normalize-space()='HP LP3065']"));         //click on HP LP3065

        String actHpHeading = getTextFromElement(By.xpath("//h1[normalize-space()='HP LP3065']"));
        verifyText("Invalid product heading", "HP LP3065", actHpHeading);

        String year = "2023", month = "November", date = "27";
        clickOnElement(By.xpath("//div[@class='input-group date']//button[@type='button']"));
        while (true) {
            String monthYear = driver.findElement(By.xpath("//div[@class='datepicker-days']//th[@class='picker-switch']")).getText(); //of Date and year element title in calender
            String[] a = monthYear.split(" "); //Splitting month and year
            String mon = a[0];
            String yer = a[1];
            if (mon.equalsIgnoreCase(month) && yer.equalsIgnoreCase(year)) {
                break;
            } else {
                clickOnElement(By.xpath("//div[@class='datepicker-days']//th[@class='next']"));
            }
        }

        //Select date
        List<WebElement> allDates =driver.findElements(By.xpath("//div[@class='datepicker-days']//tbody//tr//td"));
        for(WebElement dt:allDates){
            if(dt.getText().equals(date)){
                dt.click();
                break;
            }
        }

        driver.findElement(By.id("input-quantity")).clear();
        sendTextToElement(By.id("input-quantity"), "1");
        Thread.sleep(1000);
        clickOnElement(By.id("button-cart"));           //click on add to cart

        String expMessage = "Success: You have added HP LP3065 to your shopping cart!";
        String actMessage = getTextFromElement(By.xpath("//div[@class='alert alert-success alert-dismissible']")).substring(0,56);
        verifyText("Invalid success message", expMessage, actMessage);

        clickOnElement(By.xpath("//a[normalize-space()='shopping cart']"));         //click on shopping cart from success message

        String expCartHeading = "Shopping Cart";
        String actCartHeading = getTextFromElement(By.xpath("//h1[contains(text(),'Shopping Cart')]")).substring(0,13);
        verifyText("Invalid Cart Heading", expCartHeading, actCartHeading);

        String actName = getTextFromElement(By.xpath("//body[1]/div[2]/div[1]/div[1]/form[1]/div[1]/table[1]/tbody[1]/tr[1]/td[2]/a[1]"));
//        String actName = getTextFromElement(By.xpath("//a[contains(text(),'HP LP3065')])[2]"));
        String actDeliveryDate = getTextFromElement(By.xpath("//small[normalize-space()='Delivery Date:"+year+"-11-"+date+"']"));
        String actModel = getTextFromElement(By.xpath("//td[normalize-space()='Product 21']"));
        String actTotal = getTextFromElement(By.xpath("//body[1]/div[2]/div[1]/div[1]/form[1]/div[1]/table[1]/tbody[1]/tr[1]/td[6]"));

        verifyText("Invalid name", "HP LP3065", actName);
        verifyText("Invalid Delivery Date", "Delivery Date:"+year+"-11-"+date, actDeliveryDate);
        verifyText("Invalid Model", "Product 21", actModel);
        verifyText("Invalid Total", "£122.00", actTotal);

    }

    @After
    public void tearDown() {
        closeBrowser();
    }
}
