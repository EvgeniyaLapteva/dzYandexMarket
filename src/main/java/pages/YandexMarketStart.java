package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class YandexMarketStart {

    private WebDriver driver;

    private WebDriverWait wait;

    private String selectorCatalogButton = "//div[@data-zone-name='catalog']";

    private String selectorLaptopsSection = "//span[text()='Ноутбуки и компьютеры']";

    private String selectorLaptopChoice = "//a[text()='Ноутбуки']";

    private WebElement catalogButton;

    private WebElement laptopsField;

    public YandexMarketStart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void goToCatalog() {
        wait.until(visibilityOfElementLocated(By.xpath(selectorCatalogButton)));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selectorCatalogButton)));
        catalogButton = driver.findElement(By.xpath(selectorCatalogButton));
        catalogButton.click();
    }

    public void goToLaptopsPage() {
        wait.until(visibilityOfElementLocated(By.xpath(selectorLaptopsSection)));
        laptopsField = driver.findElement(By.xpath(selectorLaptopsSection));
        WebElement field = driver.findElement(By.xpath(selectorLaptopChoice));
        field.click();
    }
}
