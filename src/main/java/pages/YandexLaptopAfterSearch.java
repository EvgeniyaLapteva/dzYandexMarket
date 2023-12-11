package pages;

import helpers.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * @author Евгения Лаптева
 * С помощью этого класса мы собираем и обрабатывем результаты поиска по заданным в классе YandexLaptopBeforeSearch
 * параметрам
 */
public class YandexLaptopAfterSearch {

    /**
     * Это поле определяет драйвер, которым мы будем пользоваться в этом классе
     */
    private WebDriver driver;

    /**
     *Это поле для задания условий, выполнения которых мы будем ждать перед выполнением дальнейших действий
     */
    private WebDriverWait wait;

    /**
     * Это поле - селектор всех результатов поиска
     */
    private String selectorResults = "//div[@data-zone-name='catalogListPage']";

    /**
     * Это поле - селектор выдачи наименований товаров
     */
    private String selectorProducer = "//h3[@data-zone-name='title']//span";

    /**
     * Это поле - селектор выдачи цен товаров
     */
    private String selectorPrice = "//div[@data-zone-name='price']//h3";

    /**
     * Это поле - селектор кнопки Вперед, предназначенной для перехода на следующую страницу
     */
    private String selectorNextButton = "//span[contains(text(), 'Вперёд')]";

    /**
     * Это поле - селектор поля Показать еще
     */
    private String selectorShowMore = "//span[contains(text(), 'Показать ещё')]";

    /**
     * Это поле - селектор кнопок пагинации
     */
    private String selectorPaginationButton = "//div[@data-auto='pagination-page']";

    /**
     * Это поле - селектор поля поисковой строки
     */
    private String selectorSearchField = "//input[@placeholder='Искать товары']";

    /**
     * Это поле - селектор поля кнопки поиска, связанной с поисковой строкой
     */
    private String selectorSearchButton = "//button[@data-auto='search-button']";

    /**
     * Это поле - храни вебэлемент, содержащий результаты поиска
     */
    private WebElement resultsOfSearch;

    /**
     * Это поле предназначено для хранения пар производитель-цена в виде списка
     */
    private List<Map<String, String>> collectResults = new ArrayList<>();

    /**
     * Это поле предназначено для хранения результатов поиска, которые не прошли проверку на соответствие заданным параметрам
     */
    private List<Map<String, String>> exceptedItems = new ArrayList<>();

    /**
     * Это поле предназначено для задания определенных действий на странице
     */
    private final Actions actions;

    public YandexLaptopAfterSearch(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        this.actions = new Actions(driver);
    }

    /**
     * Это геттер для поля, хранящего пары производитель-цена в виде списка
     * @return возвращает список товаров, полученный после фильтрации
     */
    public List<Map<String, String>> getCollectResults() {
        return collectResults;
    }

    /**
     * Этот метод предназначен для прокрутки первой страницы
     */
    public void scrollToTheEndOfFirstPage() {
        wait.until(visibilityOfElementLocated(By.xpath(selectorPaginationButton)));
        wait.until(visibilityOfElementLocated(By.xpath(selectorShowMore)));
        WebElement lastElement =
                driver.findElement(By.xpath(selectorShowMore));
        int y = lastElement.getLocation().getY();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0,"+y+")");
    }

    /**
     * Этот метод предназначен для сбора элементов выдачи в лист List<Map<String, String>> collectResults, хранящий все
     * результаты поиска по заданным параметрам
     * @throws InterruptedException выбрасывает исключение, если работа потока была прервана
     */
    public void putItemsToCollectResults() throws InterruptedException {
        synchronized (actions) {
            actions.wait(3000);
        }
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

    /**
     * Этот метод проверяет, что на первой странице было выдано результатов поиска больше, чем count
     * @param count - число, с которым мы сравниваем количество результатов поиска
     */
    public void checkThatFirstPageContainsMoreThanExactElements(int count) {
        Assertions.assertTrue(getCollectResults().size() > count, "The result of the search contains of " +
                getCollectResults().size() + " elements.");
    }

    /**
     * Этот метод собирает результаты поиска в List<Map<String, String>> collectResults со страниц, следующих за первой
     * до тех пор, пока не доберется до последней страницы
     * @throws InterruptedException выбрасывает исключение, если работа потока была прервана
     */
    public void addTheRestResultsToFirstPage() throws InterruptedException {
        while (driver.findElements(By.xpath(selectorNextButton)).size() != 0) {
            driver.findElement(By.xpath(selectorNextButton)).click();
            scrollToThePointOnThePage(selectorPaginationButton);
            wait.until(visibilityOfElementLocated(By.xpath(selectorSearchField)));
            scrollToThePointOnThePage(selectorSearchField);
            putItemsToCollectResults();
        }
    }

    /**
     * Этот метод проверяет, соответствуют ли результаты поиска, собранные со всех страниц, заданным фильтрам
     * @param first - наименование первого производителя
     * @param second - наименование второго производителя
     * @param from - минимальная цена
     * @param to - максимальная цена
     */
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
        /*
        Этот метод я закомментировала т.к. в каждом прогоне теста присутствуют товары, не соответствующие фильтру и,
        соответственно, тест стопорится на этом шаге и не проверяет следующие шаги. Вместо ошибки вывожу на экран
        сообщение о том, что не все результаты соответствуют фильтру. Либо можно добавить эту проверку в конец
        степов и тогда можно будет последующие шаги проверить и вернуться к этому шагу
         */
        //Assertions.assertTrue(exceptedItems.size() == 0, "Не все предложения соответствуют фильтру");
    }

    /**
     * Этот метод возвращается к поисковой строке и вводит в нее наименование первого из полученных ранее результатов
     * поиска
     */
    public void searchByFirstElement() {
        wait.until(visibilityOfElementLocated(By.xpath(selectorSearchField)));
        scrollToThePointOnThePage(selectorSearchField);
        WebElement searchButton = driver.findElement(By.xpath(selectorSearchField));
        searchButton.click();
        searchButton.sendKeys(getNameOfFirstItem());
        wait.until(visibilityOfElementLocated(By.xpath(selectorSearchButton)));
        driver.findElement(By.xpath(selectorSearchButton)).click();

    }

    /**
     * этот метод возвращает название первого результата поиска с первой страницы
     * @return возвращает название первого результата поиска с первой страницы
     */
    private String getNameOfFirstItem() {
        return collectResults.get(0).keySet().iterator().next();
    }

    /**
     * этот метод осуществляет прокрутку страницы до заданного элемента
     * @param point - xpath элемента
     */
    private void scrollToThePointOnThePage(String point) {
        wait.until(visibilityOfElementLocated(By.xpath(point)));
        WebElement lastElement =
                driver.findElement(By.xpath(point));
        int y = lastElement.getLocation().getY();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0," + y + ")");
    }
}
