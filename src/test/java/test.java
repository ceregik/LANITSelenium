import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class test {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(800, TimeUnit.MILLISECONDS);
    }

    @Test
    public void AvitoCatalog(){
        driver.get("https://www.avito.ru/");
        WebElement selectElem = driver.findElement(By.cssSelector("select[data-marker='search-form/category']"));
        Select select = new Select(selectElem);
        select.selectByValue("99");
        driver.findElement(By.cssSelector("input[data-marker='search-form/suggest']")).sendKeys("Принтер");
        driver.findElement(By.cssSelector("div[data-marker='search-form/region']")).click();
        driver.findElement(By.cssSelector("input[data-marker='popup-location/region/input']")).sendKeys("Владивосток");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'Владивосток')]")));
        driver.findElement(By.xpath("//strong[contains(text(),'Владивосток')]")).click();

        driver.findElement(By.cssSelector("button[data-marker='popup-location/save-button']")).click();
        driver.findElement(By.cssSelector("button[data-marker='search-form/submit-button']")).click();

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("scroll(0, 250);");

        driver.findElement(By.cssSelector("span[data-marker='delivery-filter/text']")).click();
        driver.findElement(By.cssSelector("button[data-marker='search-filters/submit-button']")).click();

        select = new Select(driver.findElement(By.xpath("//option[contains(text(),'По умолчанию')] /..")));
        select.selectByValue("2");
       List<WebElement> price = driver.findElements(By.xpath(("//span[contains(text(),'₽')] /..")));
       for(int i = 0;i<3;i++){
           System.out.println(price.get(i).getText());
       }

    }

    @After

    public void stop(){
        driver.quit();
        driver = null;
    }
}

