package pages;

import helpers.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Евгения Лаптева
 * С помощью этого класса мы, находясь на странице Яндекс маркета, в разделе Ноутбуки, задаем необходимые параметры для
 * поиска элементов
 */
public class YandexLaptopBeforeSearch {

    /**
     * Это поле определяет драйвер, которым мы будем пользоваться в этом классе
     */
    private WebDriver driver;

    /**
     *Это поле для задания условий, выполнения которых мы будем ждать перед выполнением дальнейших действий
     */
    private WebDriverWait wait;

    /**
     * Это поле хранит селектор для заголовка раздела
     */
    private String selectorOfTitle = "//h1";

    /**
     * Это поле хранит селектор для поля минимальной цены
     */
    private String selectorPriceFrom =
            "//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-min']//input[@type='text']";

    /**
     * Это поле хранит селектор для поля максимальной цены
     */
    private String selectorPriceTo =
            "//div[@data-auto='filter-range-glprice']//span[@data-auto='filter-range-max']//input[@type='text']";

    /**
     * Это поле хранит вебэлемент для поля, содержащего заголовок раздела Ноутбуки
     */
    private WebElement titleOfSection;

    /**
     * Это поле хранит вебэлемент поля, предназначенного для вставки минимальной цены
     */
    private WebElement searchPriceFrom;

    /**
     * Это поле хранит вебэлемент поля, предназначенного для вставки максимальной цены
     */
    private WebElement searchPriceTo;

    /**
     * Это поле хранит вебэлемент, с помощью которого мы выбираем первого производителя
     */
    private WebElement producerHp;

    /**
     * Это поле хранит вебэлемент, с помощью которого мы выбираем второго производителя
     */
    private WebElement producerLenovo;

    public YandexLaptopBeforeSearch(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    /**
     * С помощью этого метода мы проверяем, соответствует ли название раздела, в котором мы находимся переданному
     * проверочному слову
     * @param title - название раздела, в котором мы ожидаем, что находимся
     */
    public void checkIfItLaptopsSection(String title) {
        wait.until(visibilityOfElementLocated(By.xpath(selectorOfTitle)));
        titleOfSection = driver.findElement(By.xpath(selectorOfTitle));
        Assertions.assertTrue(titleOfSection.getText().contains(title),
                "Раздел не содержит текста " + titleOfSection.getText());
    }

    /**
     * Метод, с помощью которого мы задаем минимальное и максимальное значения цены для фильтрации
     * @param from минимальное значение цены
     * @param to максимальное значение цены
     */
    public void setPrices(String from, String to) {
        wait.until(visibilityOfElementLocated(By.xpath(selectorPriceFrom)));
        wait.until(visibilityOfElementLocated(By.xpath(selectorPriceTo)));
        searchPriceFrom = driver.findElement(By.xpath(selectorPriceFrom));
        searchPriceFrom.click();
        searchPriceFrom.sendKeys(from);
        searchPriceTo = driver.findElement(By.xpath(selectorPriceTo));
        searchPriceTo.click();
        searchPriceTo.sendKeys(to);
    }

    /**
     * Метод, с помощью которго мы задаем первого и второго производителя для фильтрации
     * @param first наименование первого производителя
     * @param second наименование второго производителя
     */
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
