package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Евгения Лаптева
 * С помощью этого класса мы, находясь на главной странице Яндекс маркета, открываем каталог и переходим в раздел Ноутбуки
 */
public class YandexMarketStart {

    /**
     * Это поле определяет драйвер, которым мы будем пользоваться в этом классе
     */
    private WebDriver driver;

    /**
     *Это поле для задания условий, выполнения которых мы будем ждать перед выполнением дальнейших действий
     */
    private WebDriverWait wait;

    /**
     * Это поле - селектор для кнопки "Каталог"
     */
    private String selectorCatalogButton = "//div[@data-zone-name='catalog']";

    /**
     * это поле - селектор для выбора раздела Ноутбуки и компьютеры
     */
    private String selectorLaptopsSection = "//span[text()='Ноутбуки и компьютеры']";

    /**
     * Это поле - селектор для выбора раздела Ноутбуки
     */
    private String selectorLaptopChoice = "//a[text()='Ноутбуки']";

    /**
     * Это поле хранит вебэлемент - кнопка Каталог
     */
    private WebElement catalogButton;

    /**
     * Это поле хранит вебэлемент поля Ноутбуки
     */
    private WebElement laptopsField;

    public YandexMarketStart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    /**
     * С помощью этого метода мы дожидаемся видимости кнопки Каталог и затем жмем на нее
     */
    public void goToCatalog() {
        wait.until(visibilityOfElementLocated(By.xpath(selectorCatalogButton)));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selectorCatalogButton)));
        catalogButton = driver.findElement(By.xpath(selectorCatalogButton));
        catalogButton.click();
    }

    /**
     * С помощью этого метода мы дожидаемся видимости раздела Ноутбуки и переходим в него
     */
    public void goToLaptopsPage() {
        wait.until(visibilityOfElementLocated(By.xpath(selectorLaptopsSection)));
        laptopsField = driver.findElement(By.xpath(selectorLaptopsSection));
        WebElement field = driver.findElement(By.xpath(selectorLaptopChoice));
        field.click();
    }
}
