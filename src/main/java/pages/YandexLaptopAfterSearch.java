package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class YandexLaptopAfterSearch {

    private WebDriver driver;

    private WebDriverWait wait;

    private String selectorResults = "//div[@data-zone-name='catalogListPage']";

    private String selectorProducer = "//h3[@data-zone-name='title']//span";

    private String selectorPrice = "//div[@data-zone-name='price']//h3";

    private String selectorNextButton = "//span[contains(text(), 'Вперёд')]";

    private String selectorShowMore = "//span[contains(text(), 'Показать ещё')]";

    private String selectorPaginationButton = "//div[@data-auto='pagination-page']";

    private String selectorSearchField = "//input[@id='header-search']";

    private String selectorSearchButton = "//button[@data-auto='search-button']";

    private WebElement resultsOfSearch;

    private List<Map<String, String>> collectResults = new ArrayList<>();

    private List<Map<String, String>> exceptedItems = new ArrayList<>();

    public YandexLaptopAfterSearch(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public List<Map<String, String>> getCollectResults() {
        return collectResults;
    }

    public void scrollToTheEndOfFirstPage() {
        wait.until(visibilityOfElementLocated(By.xpath(selectorPaginationButton)));
        wait.until(visibilityOfElementLocated(By.xpath(selectorShowMore)));
        WebElement lastElement =
                driver.findElement(By.xpath(selectorShowMore));
        int y = lastElement.getLocation().getY();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0,"+y+")");
    }

    public void putItemsToCollectResults() {

        wait.until(visibilityOfElementLocated(By.xpath(selectorResults)));
        wait.until(visibilityOfElementLocated(By.xpath(selectorProducer)));
        wait.until(visibilityOfElementLocated(By.xpath(selectorPrice)));
        wait.until(visibilityOfElementLocated(By.xpath(selectorPaginationButton)));

        resultsOfSearch = driver.findElement(By.xpath(selectorResults));

        List<WebElement> producers = resultsOfSearch.findElements(By.xpath(selectorProducer));
        List<WebElement> prices = resultsOfSearch.findElements(By.xpath(selectorPrice));

        for (int i = 0; i < producers.size(); i++) {
            Map<String, String> laptops = new HashMap<>();
            laptops.put(producers.get(i).getText(), prices.get(i).getText());
            collectResults.add(laptops);
        }
    }

    public void checkThatFirstPageContainsMoreThan12Elements() {
        Assertions.assertTrue(getCollectResults().size() > 12, "The result of the search contains of " +
                getCollectResults().size() + " elements. It is less than 12");
    }

    public void addTheRestResultsToFirstPage() throws InterruptedException {
        while (driver.findElements(By.xpath(selectorNextButton)).size() != 0) {
            driver.findElement(By.xpath(selectorNextButton)).click();
            scrollToThePointOnThePage(selectorPaginationButton);
            Thread.sleep(3000);
            putItemsToCollectResults();
        }
    }

    public void checkIfResultContainRightProducersAndPrices(String first, String second, String from, String to) {
        for (Map<String, String> map : collectResults) {
            String productName = map.keySet().iterator().next();
            String priceString = map.get(productName).replaceAll("[^0-9]", "");
            int price = Integer.parseInt(priceString);

            if (!(productName.toLowerCase().contains(first.toLowerCase())
                    || productName.toLowerCase().contains(second.toLowerCase())) || price < Integer.parseInt(from)
                    || price > Integer.parseInt(to)) {
                exceptedItems.add(map);
            }
        }
        if (exceptedItems.size() != 0) {
            System.out.println("Не все предложения соответствуют фильтру: ");
            for (Map<String, String> invalidElement : exceptedItems) {
                System.out.println(invalidElement);
            }
        }
    }

    public void searchByFirstElement() {
        wait.until(visibilityOfElementLocated(By.xpath(selectorSearchField)));
        scrollToThePointOnThePage(selectorSearchField);
        WebElement searchButton = driver.findElement(By.xpath(selectorSearchField));
        searchButton.click();
        searchButton.sendKeys(getNameOfFirstItem());
        wait.until(visibilityOfElementLocated(By.xpath(selectorSearchButton)));
        driver.findElement(By.xpath(selectorSearchButton)).click();

    }
    public void searchResultsByNameOfFirstItem() {
        System.out.println(getNameOfFirstItem());
    }

    private String getNameOfFirstItem() {
        return collectResults.get(0).keySet().iterator().next();
    }

    private void scrollToThePointOnThePage(String point) {
        wait.until(visibilityOfElementLocated(By.xpath(point)));
        WebElement lastElement =
                driver.findElement(By.xpath(point));
        int y = lastElement.getLocation().getY();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0," + y + ")");
    }

}
