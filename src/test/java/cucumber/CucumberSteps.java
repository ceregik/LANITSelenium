package cucumber;

import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CucumberSteps{

    private static WebDriver driver;
    private static WebDriverWait wait;

    @ParameterType(".*")
    public CategoryItem categoryItem(String type){
        return CategoryItem.valueOf(type);
    }

    @ParameterType(".*")
    public PriceOrder priceOrder(String price){
        return PriceOrder.valueOf(price);
    }

    @Пусть("^открыт ресурс авито$")
    public static void Open(){
        System.setProperty("webdriver.chrome.driver", "src\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(800, TimeUnit.MILLISECONDS);
        driver.get("https://www.avito.ru/");
    }

    @И("в выпадающем списке категорий выбрана {categoryItem}")
    public static void selevtOrg(CategoryItem categoryItem){
        WebElement selectElem = driver.findElement(By.cssSelector("select[data-marker='search-form/category']"));
        Select select = new Select(selectElem);
        select.selectByValue(categoryItem.value);
    }

    @И("^в поле поиска введено значение ([^\\\"]*)$")
    public static void SearchSendKeys(String word){
        driver.findElement(By.cssSelector("input[data-marker='search-form/suggest']")).sendKeys(word);
    }

    @Тогда("^кликнуть по выпадающему списку региона$")
    public static void Region(){
        driver.findElement(By.cssSelector("div[data-marker='search-form/region']")).click();
    }

    @Тогда("^в поле регион введено значение ([^\\\"]*)$")
    public static void chouseRegion(String city){
        driver.findElement(By.cssSelector("input[data-marker='popup-location/region/input']")).sendKeys(city);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'"+ city +"')]")));
        driver.findElement(By.xpath("//strong[contains(text(),'" + city + "')]")).click();
        driver.findElement(By.cssSelector("button[data-marker='popup-location/save-button']")).click();
    }

    @И("^нажата кнопка показать объявления$")
    public static void clickButtonSearch(){
        driver.findElement(By.cssSelector("button[data-marker='search-form/submit-button']")).click();

    }

    @Тогда("^активирован чекбокс только с доставкой$")
    public static void mail(){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("scroll(0, 250);");

        driver.findElement(By.cssSelector("span[data-marker='delivery-filter/text']")).click();
        driver.findElement(By.cssSelector("button[data-marker='search-filters/submit-button']")).click();
    }
    @И("в выпадающем списке сортировка выбрано значение {priceOrder}")
    public static void expensive(PriceOrder priceOrder){
        Select select = new Select(driver.findElement(By.xpath("//option[contains(text(),'По умолчанию')] /..")));
        select.selectByValue(priceOrder.value);
    }
    @И("^в консоль выведено значение названия и цены (\\d+) первых товаров$")
    public static void prices(int howMany){
        List<WebElement> price = driver.findElements(By.xpath(("//span[contains(text(),'₽')] /..")));
        for(int i = 0;i<howMany;i++){
            System.out.println(price.get(i).getText());

        }
        driver.quit();
        driver = null;
    }


}
