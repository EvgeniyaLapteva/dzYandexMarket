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

    public void scrollToTheEndOfPage() {
        wait.until(visibilityOfElementLocated(By.xpath(selectorPaginationButton)));
        wait.until(visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Показать ещё')]")));
        WebElement lastElement =
                driver.findElement(By.xpath("//span[contains(text(), 'Показать ещё')]"));
        int y = lastElement.getLocation().getY();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0,"+y+")");
    }

    public void putItemsToCollectResults() throws InterruptedException {
         // wait.until(visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Показать ещё')]")));
//        WebElement lastElement =
//                driver.findElement(By.xpath("//span[contains(text(), 'Показать ещё')]"));
//        int y = lastElement.getLocation().getY();
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        js.executeScript("window.scrollTo(0,"+y+")");
//
        wait.until(visibilityOfElementLocated(By.xpath(selectorPaginationButton)));
        Thread.sleep(5000);
        wait.until(visibilityOfElementLocated(By.xpath(selectorResults)));
        wait.until(visibilityOfElementLocated(By.xpath(selectorProducer)));
        wait.until(visibilityOfElementLocated(By.xpath(selectorPrice)));

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
            wait.until(visibilityOfElementLocated(By.xpath(selectorPaginationButton)));
            WebElement lastElement =
                    driver.findElement(By.xpath(selectorPaginationButton));
            int y = lastElement.getLocation().getY();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0," + y + ")");
            putItemsToCollectResults();
        }
    }

    public void checkIfResultContainRightProducers(String first, String second, String from, String to) {
        for (Map<String, String> map : collectResults) {
            String productName = map.keySet().iterator().next();
            String priceString = map.get(productName).replaceAll("[^0-9]", "");
            int price = Integer.parseInt(priceString);

            if (!(productName.contains(first) || productName.contains(second)) || price < Integer.parseInt(from)
                    || price > Integer.parseInt(to)) {
                exceptedItems.add(map);
            }
        }
        for (Map<String, String> invalidElement : exceptedItems) {
            System.out.println(invalidElement);
        }
    }
}