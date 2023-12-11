package pages;

import helpers.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Евгения Лаптева
 * С помощью этого класса мы проверяем, есть ли в результатах поиска товара по конкретному наименованию правильная
 * выдача
 */
public class YandexMarketFinalSearch {

    /**
     * Это поле определяет драйвер, которым мы будем пользоваться в этом классе
     */
    private WebDriver driver;

    /**
     *Это поле для задания условий, выполнения которых мы будем ждать перед выполнением дальнейших действий
     */
    private WebDriverWait wait;

    /**
     * Это поле - селектор для поля, хранящего результаты поиска
     */
    private String selectorResults = "//div[@data-zone-name='catalogListPage']";

    /**
     * Это поле - селектор для поля наименований товаров
     */
    private String selectorProducer = "//h3[@data-zone-name='title']//span";

    /**
     * Это поле - селектор для кнопок пагинации
     */
    private String selectorPaginationButton = "//div[@data-auto='pagination-page']";

    /**
     * Это поле предназначено для хранения вебэлемента с результатами поиска
     */
    private WebElement resultsOfSearch;

    /**
     * Это поле предназначено для хранения наименований товаров, полученных в  результате поиска по конкретному названию
     */
    private List<String> resultsFromFirstPage = new ArrayList<>();

    public YandexMarketFinalSearch(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    /**
     * Этот метод принимает на вход наименование товара, переходит к поисковому полю, вставляет в него данное наименование,
     * осуществляет поиск по нему, а затем проверяет, есть ли в полученных результатах нужный результат
     * @param item определенное наименование товара
     */
    public void checkIfFirstPageContainsExactItem(String item) {
        wait.until(visibilityOfElementLocated(By.xpath(selectorResults)));
        wait.until(visibilityOfAllElementsLocatedBy(By.xpath(selectorProducer)));
        wait.until(visibilityOfAllElementsLocatedBy(By.xpath(selectorPaginationButton)));

        resultsOfSearch = driver.findElement(By.xpath(selectorResults));

        List<WebElement> producers = resultsOfSearch.findElements(By.xpath(selectorProducer));

        for (WebElement producer : producers) {
            resultsFromFirstPage.add(producer.getText());
        }

        boolean isFirstPageContainsItem = resultsFromFirstPage.contains(item);

        Assertions.assertTrue(isFirstPageContainsItem, "После поиска по запросу " + item + " первая страница " +
                "не содержит искомых результатов");
    }
}
