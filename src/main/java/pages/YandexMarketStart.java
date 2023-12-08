package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class YandexMarketStart {

    private WebDriver driver;

    private WebDriverWait wait;

    private WebElement catalogButton;

    private WebElement laptopsField;

    public YandexMarketStart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void goToCatalog() {
        wait.until(visibilityOfElementLocated(By.xpath("//div[@data-zone-name='catalog']//button")));
        catalogButton = driver.findElement(By.xpath("//div[@data-zone-name='catalog']//button"));
        catalogButton.click();
    }

    public void goToLaptopsPage() {
        wait.until(visibilityOfElementLocated(By.xpath("//span[text()='Ноутбуки и компьютеры']")));
        laptopsField = driver.findElement(By.xpath("//span[text()='Ноутбуки и компьютеры']"));
        WebElement field = driver.findElement(By.xpath("//a[text()='Ноутбуки']"));
        field.click();
    }
}
