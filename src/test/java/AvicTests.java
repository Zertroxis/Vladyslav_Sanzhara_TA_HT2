import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;


public class AvicTests {

    private static final String BUTTON_lINK_HOMEPAGE_SAMSUNG_SHORTCUT = "//div[@class = 'partner-box height']/a[@href = '/brand-samsung']";

    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void checkShortcutToSamsungGoodsFromHomePage() {
        driver.findElement(By.xpath(BUTTON_lINK_HOMEPAGE_SAMSUNG_SHORTCUT)).click();
        new WebDriverWait(driver, 15).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id = 'Смартфоны'][@class]")).getText().contains("Samsung"));

    }

    @Test(priority = 2)
    public void checkElementsOfSamsungGoodsFromShortcut() {
        driver.findElement(By.xpath(BUTTON_lINK_HOMEPAGE_SAMSUNG_SHORTCUT)).click();
        new WebDriverWait(driver, 15).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class ='container-main']/article[@class ='brand__item']"));
        Assert.assertEquals(elementList.size(), 34);
    }

    @Test(priority = 3)
    public void checkTwoElementsWacomAndIpadAddedInCart() {
        driver.findElement(xpath("//input[@id='input_search']")).sendKeys("wacom");
        driver.findElement(xpath("//button[@class='button-reset search-btn']")).click();
        new WebDriverWait(driver, 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(xpath("//a[@class='prod-cart__buy'][contains(@data-ecomm-cart," +
                "'Монитор-планшет Wacom Cintiq 16 FHD (DTK-1660)')]")).click();
        WebDriverWait waitForWacom = new WebDriverWait(driver, 15);
        waitForWacom.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        driver.findElement(xpath("//div[@class='btns-cart-holder']//a[contains(@class,'btn--orange')]")).click();
        new WebDriverWait(driver, 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(xpath("//input[@id='input_search']")).clear();
        driver.findElement(xpath("//input[@id='input_search']")).sendKeys("ipad");
        driver.findElement(xpath("//button[@class='button-reset search-btn']")).click();
        new WebDriverWait(driver, 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(xpath("//a[@class='prod-cart__buy'][contains(@data-ecomm-cart," +
                "'Apple iPad Air 2019 Wi-Fi 256GB Gold (MUUT2)')]")).click();
        WebDriverWait waitForIPad2021Pro = new WebDriverWait(driver, 15);
        waitForIPad2021Pro.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        driver.findElement(xpath("//div[@class='btns-cart-holder']//a[contains(@class,'btn--orange')]")).click();
        driver.findElement(xpath("//div[@class='header-bottom__cart active-cart flex-wrap middle-xs js-btn-open']")).click();
        List<WebElement> elementList = driver.findElements(By.xpath("//div[@class = 'item'][@data-productkey][@data-cart-id]"));
        Assert.assertEquals(elementList.size(), 2);
    }

    @Test(priority = 4)
    public void checkToCartCameraSonyA7(){
        driver.findElement(By.xpath("//span[@class='sidebar-item']")).click();
        driver.findElement(xpath("//ul[contains(@class,'sidebar-list')]//a[contains(@href, 'https://avic.ua/foto-video')]")).click();
        new WebDriverWait(driver, 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(By.xpath("//img[@src][@alt = 'Фотоаппараты']")).click();
        new WebDriverWait(driver, 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(By.xpath("//label[@for='fltr-proizvoditel-sony']")).click();
        new WebDriverWait(driver, 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(xpath("//div[@class='prod-cart__descr' and contains(text(), 'Фотоаппарат Sony Alpha 7M2 + объектив 28-70 KIT (ILCE7M2KB.CEC)')]")).click();
        new WebDriverWait(driver, 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(xpath("//div[@class='about-prod__center']//a[@class='main-btn main-btn--green main-btn--large'][contains(@data-ecomm-cart, 'Фотоаппарат Sony Alpha')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        Assert.assertTrue(driver.findElement(By.xpath("//span[@class='name']")).getText().contains("Фотоаппарат Sony Alpha 7M2 + объектив 28-70 KIT (ILCE7M2KB.CEC)"));
    }


    @AfterMethod
    public void tearDown() {
        driver.close();
    }

}
