package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class YandexMarketFinalSearch {

    private WebDriver driver;

    private WebDriverWait wait;

    private String selectorResults = "//div[@data-zone-name='catalogListPage']";

    private String selectorProducer = "//h3[@data-zone-name='title']//span";

    private String selectorPaginationButton = "//div[@data-auto='pagination-page']";

    private WebElement resultsOfSearch;

    private List<String> resultsFromFirstPage = new ArrayList<>();

    public YandexMarketFinalSearch(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

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
        for (String str : resultsFromFirstPage) {
            System.out.println(str);
        }

        Assertions.assertTrue(isFirstPageContainsItem, "После поиска по запросу " + item + " первая страница " +
                "не содержит искомых результатов");
    }
}
