import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class test {

    private WebDriver driver;
    private WebDriverWait wait;
    private final By searchForm =  By.cssSelector("select[data-marker='search-form/category']");
    private final String URL =  "https://www.avito.ru/";
    private final By searchFormSuggest =  By.cssSelector("input[data-marker='search-form/suggest']");
    private final By searchFormRegion =  By.cssSelector("div[data-marker='search-form/region']");
    private final By popupLocation =  By.cssSelector("input[data-marker='popup-location/region/input']");
    private final By searchCity =  By.xpath("//strong[contains(text(),'Владивосток')]");
    private final By popupLocationSaveButton =  By.cssSelector("button[data-marker='popup-location/save-button']");
    private final By popupLocationSubmitButton =  By.cssSelector("button[data-marker='search-form/submit-button']");
    private final By deliveryFilter =  By.cssSelector("label[data-marker='delivery-filter']");
    private final By searchFiltersSubmitButton = By.xpath("//button[@data-marker='search-filters/submit-button']");
    private final By option =  By.xpath("//option[contains(text(),'По умолчанию')] /..");
    private final By priceSelector =  By.xpath("//span[contains(text(),'₽')] /..");


    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "src\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(800, TimeUnit.MILLISECONDS);
    }

    @Test
    public void AvitoCatalog() {
        System.out.println();
        driver.get(URL);

        System.out.println("Открыть ресурс по адресу https://www.avito.ru/");
        WebElement selectElem = driver.findElement(searchForm);
        Select select = new Select(selectElem);
        select.selectByValue("99");

        System.out.println("Выбрать в выпадающем списке “категория”  значение оргтехника и расходники");
        driver.findElement(searchFormSuggest).sendKeys("Принтер");

        System.out.println("В поле поиск по объявлению ввести значение “Принтер”");
        driver.findElement(searchFormRegion).click();

        System.out.println("Нажать на поле город");
        driver.findElement(popupLocation).sendKeys("Владивосток");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchCity)).click();

        System.out.println("Заполнить значением “Владивосток” поле город  в открывшемся окне и кликнуть по первому предложенному варианту. Нажать на кнопку “Показать объявления”");
        driver.findElement(popupLocationSaveButton).click();
        driver.findElement(popupLocationSubmitButton).click();

        System.out.println("Активировать чекбокс и нажать кнопку “Показать объявления”");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, document.documentElement.clientHeight));");
        driver.findElement(deliveryFilter).click();
        driver.findElement(searchFiltersSubmitButton).click();

        System.out.println("В выпадающем списке фильтрации выбрать фильтрацию по убыванию цены.");
        select = new Select(driver.findElement(option));
        select.selectByValue("2");

        System.out.println("Вывести в консоль название и стоимость 3х самых дорогих принтеров");
       List<WebElement> price = driver.findElements(priceSelector);
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

