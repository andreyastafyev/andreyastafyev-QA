import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
public class Test11 {
private WebDriver driver;
private String baseUrl;
@BeforeClass(alwaysRun = true)
public void setUp() throws Exception {
//System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
//driver = new ChromeDriver();
System.setProperty("webdriver.chrome.driver", "C:/Users/a-ast/work/git/andreyastafyev-QA/Homework3/src/test/chromedriver.exe");
 driver = new ChromeDriver();
driver.get("http://www.google.com/");
baseUrl = "https://sandbox.cardpay.com/MI/cardpayment2.html?orderXml=PE9SREVSIFdBTExFVF9JRD0nODI5OScgT1JERVJfTlVNQkVSPSc0NTgyMTEnIEFNT1VOVD0nMjkxLjg2JyBDVVJSRU5DWT0nRVVSJyAgRU1BSUw9J2N1c3RvbWVyQGV4YW1wbGUuY29tJz4KPEFERFJFU1MgQ09VTlRSWT0nVVNBJyBTVEFURT0nTlknIFpJUD0nMTAwMDEnIENJVFk9J05ZJyBTVFJFRVQ9JzY3NyBTVFJFRVQnIFBIT05FPSc4NzY5OTA5MCcgVFlQRT0nQklMTElORycvPgo8L09SREVSPg==&sha512=998150a2b27484b776a1628bfe7505a9cb430f276dfa35b14315c1c8f03381a90490f6608f0dcff789273e05926cd782e1bb941418a9673f43c47595aa7b8b0d";
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
driver.manage().window().fullscreen();}
@Test
public void testCardNumber() {
driver.get(baseUrl);
WebElement cardNumber = driver.findElement(By.id("input-card-number"));
WebElement cardHolder = driver.findElement(By.id("input-card-holder"));
WebElement cardExpiresMonth = driver.findElement(By.id("card-expires-month"));
WebElement cardYear = driver.findElement(By.id("card-expires-year"));
WebElement cardCVC = driver.findElement(By.id("input-card-cvc"));
cardNumber.clear();
cardNumber.sendKeys("4000 0000 0000 0036");
cardHolder.clear();
cardHolder.sendKeys("ASD ASD");
cardExpiresMonth.click();
new Select(cardExpiresMonth).selectByVisibleText("02");
cardYear.click();
new Select(cardYear).selectByVisibleText("2023");
cardCVC.clear();
cardCVC.sendKeys("111");
driver.findElement(By.id("action-submit")).click();
/*driver.get("https://sandbox.cardpay.com/sandbox-emulator/acspage/cap?RID=136&VAA=A");
driver.get("https://sandbox.cardpay.com/MI/cardpayment2.html?bank_id=sandbox");*/
assertEquals(driver.findElement(By.xpath("//div[@id='payment-item-status']/div[2]")).getText(), "Confirmed");
}
@Test
public void checkCardValidator() {
driver.get(baseUrl);
WebElement cardNumber = driver.findElement(By.id("input-card-number"));
WebElement cardHolder = driver.findElement(By.id("input-card-holder"));
cardNumber.clear();
cardNumber.sendKeys("0000 0000 0000 0000");
cardHolder.click();
assertEquals(driver.findElement(By.xpath("//*[@id=\"card-number-field\"]/div/label")).getText(), "Card number is not valid");
}
// Наведение мышки
       @Test
public void mouse() throws InterruptedException, IOException {
driver.get(baseUrl);
WebElement cvcHint = driver.findElement(By.id("cvc-hint"));
//Навели мышь на элемент
Actions actions = new Actions(driver);
actions.moveToElement(cvcHint).click().build().perform();
driver.wait(3000);
//assertEquals(driver.findElement(By.xpath("//*[@class='opened']")).getScreenshotAs("screenshot.jpg"), "Card number is not valid");
}

    public void checkCVCValidator() {
        driver.get(baseUrl);
        WebElement cardNumber = driver.findElement(By.id("input-card-number"));
        WebElement cardHolder = driver.findElement(By.id("input-card-holder"));
        cardNumber.clear();
        cardNumber.sendKeys("0000 0000 0000 0000");
        cardHolder.click();
        assertEquals(driver.findElement(By.xpath("//*[@id=\"card-number-field\"]/div/label")).getText(), "Card number is not valid");
    }
//Негативный тест с неправильными данными
        @Test
public void declinedStatus() {
driver.get(baseUrl);
WebElement cardNumber = driver.findElement(By.id("input-card-number"));
WebElement cardHolder = driver.findElement(By.id("input-card-holder"));
WebElement cardExpiresMonth = driver.findElement(By.id("card-expires-month"));
WebElement cardYear = driver.findElement(By.id("card-expires-year"));
WebElement cardCVC = driver.findElement(By.id("input-card-cvc"));
        cardNumber.clear();
        cardNumber.sendKeys("555555555555444");
        cardHolder.clear();
        cardHolder.sendKeys("Semen 1");
        cardExpiresMonth.click();
        new Select(cardExpiresMonth).selectByVisibleText("03");
        cardYear.click();
        new Select(cardYear).selectByVisibleText("2024");
        cardCVC.clear();
        cardCVC.sendKeys("113");
        driver.findElement(By.id("action-submit")).click();
        //Проверка введенных неправильных данных (номер карты и имя на карте)
        assertEquals(driver.findElement(By.xpath("//label[text()='Card number is not valid']")).getText(), "Card number is not valid");
        assertEquals(driver.findElement(By.xpath("//label[text()='Cardholder name is not valid']")).getText(), "Cardholder name is not valid");
    }
    @Test  //провека мастеркард
    public void Mastercard() {
        driver.get(baseUrl);
        WebElement cardNumber = driver.findElement(By.id("input-card-number"));
        WebElement cardHolder = driver.findElement(By.id("input-card-holder"));
        WebElement cardExpiresMonth = driver.findElement(By.id("card-expires-month"));
        WebElement cardYear = driver.findElement(By.id("card-expires-year"));
        WebElement cardCVC = driver.findElement(By.id("input-card-cvc"));
        cardNumber.clear();
        cardNumber.sendKeys("5555555555554477");
        cardHolder.clear();
        cardHolder.sendKeys("Ivan Petrov");
        cardExpiresMonth.click();
        new Select(cardExpiresMonth).selectByVisibleText("05");
        cardYear.click();
        new Select(cardYear).selectByVisibleText("2025");
        cardCVC.clear();
        cardCVC.sendKeys("100");
        driver.findElement(By.id("action-submit")).click();
        //Проверка введенных неправильных данных (номер карты и имя на карте)
        assertEquals(driver.findElement(By.xpath("//div[@id='payment-item-status']/div[2]")).getText(), "Declined by issuing bank");
        assertEquals(driver.findElement(By.xpath("//div[@id='payment-item-cardtype']/div[2]")).getText(), "MASTERCARD");
    }
    @Test  //провека 3D Secured
    public void Securedeclinemastercad() {
        driver.get(baseUrl);
        WebElement cardNumber = driver.findElement(By.id("input-card-number"));
        WebElement cardHolder = driver.findElement(By.id("input-card-holder"));
        WebElement cardExpiresMonth = driver.findElement(By.id("card-expires-month"));
        WebElement cardYear = driver.findElement(By.id("card-expires-year"));
        WebElement cardCVC = driver.findElement(By.id("input-card-cvc"));
        cardNumber.clear();
        cardNumber.sendKeys("5555555555554444");
        cardHolder.clear();
        cardHolder.sendKeys("Ivan Petrov");
        cardExpiresMonth.click();
        new Select(cardExpiresMonth).selectByVisibleText("07");
        cardYear.click();
        new Select(cardYear).selectByVisibleText("2027");
        cardCVC.clear();
        cardCVC.sendKeys("102");
        driver.findElement(By.id("action-submit")).click();
        driver.findElement(By.xpath("//button[@id='failure']")).click();

        assertEquals(driver.findElement(By.xpath("//div[@id='payment-item-status']/div[2]")).getText(), "Declined by issuing bank");
        assertEquals(driver.findElement(By.xpath("//div[@id='payment-item-cardtype']/div[2]")).getText(), "MASTERCARD");
    }
    @Test  //проверка 3д безоасность визы
    public void Securevisa3d() {
        driver.get(baseUrl);
        WebElement cardNumber = driver.findElement(By.id("input-card-number"));
        WebElement cardHolder = driver.findElement(By.id("input-card-holder"));
        WebElement cardExpiresMonth = driver.findElement(By.id("card-expires-month"));
        WebElement cardYear = driver.findElement(By.id("card-expires-year"));
        WebElement cardCVC = driver.findElement(By.id("input-card-cvc"));
        cardNumber.clear();
        cardNumber.sendKeys("4000000000000002");
        cardHolder.clear();
        cardHolder.sendKeys("Ivan Petrov");
        cardExpiresMonth.click();
        new Select(cardExpiresMonth).selectByVisibleText("07");
        cardYear.click();
        new Select(cardYear).selectByVisibleText("2027");
        cardCVC.clear();
        cardCVC.sendKeys("102");
        driver.findElement(By.id("action-submit")).click();
        driver.findElement(By.xpath("//button[@id='success']")).click();

        assertEquals(driver.findElement(By.xpath("//div[@id='payment-status-title']/span")).getText(), "Success");
        assertEquals(driver.findElement(By.xpath("//div[@id='payment-item-cardtype']/div[2]")).getText(), "VISA");
    }
    @Test  //проверка ФИО с первой страницы оплаты и на следющей
    public void SecureFIO() {
        driver.get(baseUrl);
        WebElement cardNumber = driver.findElement(By.id("input-card-number"));
        WebElement cardHolder = driver.findElement(By.id("input-card-holder"));
        WebElement cardExpiresMonth = driver.findElement(By.id("card-expires-month"));
        WebElement cardYear = driver.findElement(By.id("card-expires-year"));
        WebElement cardCVC = driver.findElement(By.id("input-card-cvc"));
        cardNumber.clear();
        cardNumber.sendKeys("4000000000000051");
        cardHolder.clear();
        cardHolder.sendKeys("IVAN PETROV");
        cardExpiresMonth.click();
        new Select(cardExpiresMonth).selectByVisibleText("07");
        cardYear.click();
        new Select(cardYear).selectByVisibleText("2027");
        cardCVC.clear();
        cardCVC.sendKeys("102");
        driver.findElement(By.id("action-submit")).click();


      assertEquals(driver.findElement(By.xpath("//div[@id='payment-item-cardholder']/div[2]")).getText(), "IVAN PETROV");
      assertEquals(driver.findElement(By.xpath("//div[@id='payment-item-ordernumber']/div[2]")).getText(), "458211");
      assertEquals(driver.findElement(By.xpath("//div[@id='payment-status-title']/span")).getText(), "Info");
    }
    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }
}