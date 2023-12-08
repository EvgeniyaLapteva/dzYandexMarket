package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class YandexLaptopBeforeSearch {

    private WebDriver driver;

    private WebDriverWait wait;

    private WebElement titleOfSection;

    private WebElement searchPriceFrom;

    private WebElement searchPriceTo;

    private WebElement producerHp;

    private WebElement producerLenovo;


    public WebDriver getWebDriver() {
        return driver;
    }

    public YandexLaptopBeforeSearch(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void checkIfItLaptopsSection(String title) {
        wait.until(visibilityOfElementLocated(By.xpath("//h1")));
        titleOfSection = driver.findElement(By.xpath("//h1"));
        Assertions.assertTrue(titleOfSection.getText().contains(title),
                "Раздел не содержит текста " + titleOfSection);
    }

    public void setPrices(String from, String to) {
        wait.until(visibilityOfElementLocated(By.xpath(
                "//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-min']//input[@type='text']")));
        wait.until(visibilityOfElementLocated(By.xpath(
                "//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-max']//input[@type='text']")));
        searchPriceFrom = driver.findElement(By.xpath(
                "//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-min']//input[@type='text']"));
        searchPriceFrom.click();
        searchPriceFrom.sendKeys(from);
        searchPriceTo = driver.findElement(By.xpath(
                "//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-max']//input[@type='text']"));
        searchPriceTo.click();
        searchPriceTo.sendKeys(to);
    }

    public void setProducer(String first, String second) {
        wait.withTimeout(Duration.ofSeconds(10));
        wait.until(visibilityOfElementLocated(By.xpath(
                "//div[contains(@data-zone-data,'" + first + "')]//label")));
        producerHp = driver.findElement(By.xpath(
                "//div[contains(@data-zone-data,'" + first + "')]//label"));
        producerHp.click();
        wait.until(visibilityOfElementLocated(By.xpath(
                "//div[contains(@data-zone-data,'" + second + "')]//label")));
        producerLenovo = driver.findElement(By.xpath(
                "//div[contains(@data-zone-data,'" + second + "')]//label"));
        producerLenovo.click();
    }
}