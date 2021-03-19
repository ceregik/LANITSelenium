
import io.qameta.allure.Features;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class test{

    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverSteps steps;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(800, TimeUnit.MILLISECONDS);
        steps = new WebDriverSteps(driver,wait);
    }


    @After
    public void stop(){
        driver.quit();
        driver = null;
    }

    @Test
    public void AvitoCatalog(){

        steps.openAvito();

        steps.selectCategory();

        steps.find();

        steps.city();

        steps.Vladivostok();

        steps.checkBox();

        steps.delivery();

        steps.priceChange();

        steps.writePrices();

    }

}

