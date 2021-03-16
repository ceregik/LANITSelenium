package cucumber;

import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CucumberSteps{

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final By searchForm =  By.cssSelector("select[data-marker='search-form/category']");
    private static final String URL =  "https://www.avito.ru/";
    private static final By searchFormSuggest =  By.cssSelector("input[data-marker='search-form/suggest']");
    private static final By searchFormRegion =  By.cssSelector("div[data-marker='search-form/region']");
    private static final By popupLocation =  By.cssSelector("input[data-marker='popup-location/region/input']");
    private static final By searchCity =  By.xpath("//strong[contains(text(),'Владивосток')]");
    private static final By popupLocationSaveButton =  By.cssSelector("button[data-marker='popup-location/save-button']");
    private static final By popupLocationSubmitButton =  By.cssSelector("button[data-marker='search-form/submit-button']");
    private static final By deliveryFilter =  By.cssSelector("label[data-marker='delivery-filter']");
    private static final By searchFiltersSubmitButton = By.xpath("//button[@data-marker='search-filters/submit-button']");
    private static final By option =  By.xpath("//option[contains(text(),'По умолчанию')] /..");
    private static final By priceSelector =  By.xpath("//span[contains(text(),'₽')] /..");


    @ParameterType(".*")
    public CategoryItem categoryItem(String type){
        return CategoryItem.valueOf(type);
    }

    @ParameterType(".*")
    public PriceOrder priceOrder(String price){
        return PriceOrder.valueOf(price);
    }

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "src\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(800, TimeUnit.MILLISECONDS);
    }

    @Пусть("^открыт ресурс авито$")
    public static void Open(){
        driver.get(URL);
    }

    @И("в выпадающем списке категорий выбрана {categoryItem}")
    public static void selevtOrg(CategoryItem categoryItem){
        WebElement selectElem = driver.findElement(searchForm);
        Select select = new Select(selectElem);
        select.selectByValue(categoryItem.value);
    }

    @И("^в поле поиска введено значение ([^\\\"]*)$")
    public static void SearchSendKeys(String word){
        driver.findElement(searchFormSuggest).sendKeys(word);
    }

    @Тогда("^кликнуть по выпадающему списку региона$")
    public static void Region(){
        driver.findElement(searchFormRegion).click();
    }

    @Тогда("^в поле регион введено значение ([^\\\"]*)$")
    public static void chouseRegion(String city){
        driver.findElement(popupLocation).sendKeys(city);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'"+ city +"')]"))).click();
        driver.findElement(popupLocationSaveButton).click();
    }

    @И("^нажата кнопка показать объявления$")
    public static void clickButtonSearch(){
        driver.findElement(popupLocationSubmitButton).click();

    }

    @Тогда("^активирован чекбокс только с доставкой$")
    public static void mail(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, document.documentElement.clientHeight));");

        driver.findElement(deliveryFilter).click();
        driver.findElement(searchFiltersSubmitButton).click();
    }
    @И("в выпадающем списке сортировка выбрано значение {priceOrder}")
    public static void expensive(PriceOrder priceOrder){
        Select select = new Select(driver.findElement(option));
        select.selectByValue(priceOrder.value);
    }
    @И("^в консоль выведено значение названия и цены (\\d+) первых товаров$")
    public static void prices(int howMany){
        List<WebElement> price = driver.findElements(priceSelector);
        for(int i = 0;i<howMany;i++){
            System.out.println(price.get(i).getText());

        }
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }


}
