
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import java.util.List;

public class WebDriverSteps {

    private final By searchForm =  By.cssSelector("select[data-marker='search-form/category']");
 //   private final String URL =  "https://www.avito.ru/";
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

    private WebDriver driver;
    private WebDriverWait wait;

    public WebDriverSteps(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("open Avito")
    public void openAvito() {
        driver.get("https://www.avito.ru/");
        RunnerTest.captureScreenshot1(driver);
    }

    @Step("Выбрать в выпадающем списке “категория”  значение оргтехника и расходники")
    public void selectCategory() {
        WebElement selectElem = driver.findElement(searchForm);
        Select select = new Select(selectElem);
        select.selectByValue("99");
        System.out.println("Выбрать в выпадающем списке “категория”  значение оргтехника и расходники");
    }

    @Step("В поле поиск по объявлению ввести значение “Принтер”")
    public void find() {
        driver.findElement(searchFormSuggest).sendKeys("Принтер");
        System.out.println("В поле поиск по объявлению ввести значение “Принтер”");
    }

    @Step("Нажать на поле город")
    public void city() {
        driver.findElement(searchFormRegion).click();
        System.out.println("Нажать на поле город");
    }

    @Step("Заполнить значением “Владивосток” поле город  в открывшемся окне и кликнуть по первому предложенному варианту. Нажать на кнопку “Показать объявления”")
    public void Vladivostok() {

        driver.findElement(popupLocation).sendKeys("Владивосток");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchCity)).click();
        System.out.println("Заполнить значением “Владивосток” поле город  в открывшемся окне и кликнуть по первому предложенному варианту. Нажать на кнопку “Показать объявления”");
    }

    @Step("Активировать чекбокс и нажать кнопку “Показать объявления”")
    public void checkBox() {
        driver.findElement(popupLocationSaveButton).click();
        driver.findElement(popupLocationSubmitButton).click();
        System.out.println("Активировать чекбокс и нажать кнопку “Показать объявления”");
    }

    @Step("Активировать чекбокс и нажать кнопку “Показать объявления”")
    public void delivery() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, document.documentElement.clientHeight));");
        driver.findElement(deliveryFilter).click();
        driver.findElement(searchFiltersSubmitButton).click();
    }

    @Step("В выпадающем списке фильтрации выбрать фильтрацию по убыванию цены.")
    public void priceChange() {
        System.out.println("В выпадающем списке фильтрации выбрать фильтрацию по убыванию цены.");
        Select select = new Select(driver.findElement(option));
        select.selectByValue("2");
    }

    @Step("Вывести в консоль название и стоимость 3х самых дорогих принтеров")
    public void writePrices() {
        System.out.println("Вывести в консоль название и стоимость 3х самых дорогих принтеров");
        List<WebElement> price = driver.findElements(priceSelector);
        for (int i = 0; i < 3; i++) {
            System.out.println(price.get(i).getText());
        }

    }

    @Attachment(value="Screenshot", type="image/png")
    private static Screenshot captureScreenshot(WebDriver driver)
    {
        return new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(driver);
    }

}
