package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.YandexLaptopAfterSearch;
import pages.YandexLaptopBeforeSearch;
import pages.YandexMarketFinalSearch;
import pages.YandexMarketStart;

public class StepsAll {

    private static WebDriver driver;

    private static WebDriverWait wait;

    private static YandexLaptopBeforeSearch yandexLaptopBeforeSearch;

    private static YandexLaptopAfterSearch yandexLaptopAfterSearch;

    @Step("Переходим на {url}")
    public static void openSite(String url, WebDriver currentDriver){
        driver = currentDriver;
        driver.get(url);
        wait = new WebDriverWait(driver,15);
    }

    @Step("Переходим в раздел Ноутбуки")
    public static void goToLaptopsPage() {
        YandexMarketStart yandexMarketStart = new YandexMarketStart(driver);
        yandexMarketStart.goToCatalog();
        yandexMarketStart.goToLaptopsPage();
    }

    @Step("Проверяем, что мы в разделе {titleOfSection}")
    public static void checkSection(String titleOfSection) {
        yandexLaptopBeforeSearch = new YandexLaptopBeforeSearch(driver);
        yandexLaptopBeforeSearch.checkIfItLaptopsSection(titleOfSection);
    }

    @Step("Задаем параметры цены и производителя")
    public static void setPricesAndProducers(String priceFrom, String priceTo, String firstProducer,
                                           String secondProducer) {
        yandexLaptopBeforeSearch.setPrices(priceFrom, priceTo);
        yandexLaptopBeforeSearch.setProducer(firstProducer, secondProducer);
    }

    @Step("Проверяем, что на первой странице более {count} элементов")
    public static void checkIfElementsEnough(int count) {
        yandexLaptopAfterSearch = new YandexLaptopAfterSearch(driver);
        yandexLaptopAfterSearch.scrollToTheEndOfFirstPage();
        yandexLaptopAfterSearch.putItemsToCollectResults();
        yandexLaptopAfterSearch.checkThatFirstPageContainsMoreThanExactElements(count);
    }

    @Step("Проверяем, что предложения на всех страницах соответствуют фильтру")
    public static void checkIfAllResultsMatchFilter(String firstProducer, String secondProducer, String priceFrom,
                                                    String priceTo) throws InterruptedException {
        yandexLaptopAfterSearch.addTheRestResultsToFirstPage();
        yandexLaptopAfterSearch.checkIfResultContainRightProducersAndPrices(firstProducer, secondProducer, priceFrom,
                priceTo);
    }

    @Step("Ищем первый элемент в выдаче на первой странице и по нему задаем новый поисковой запрос")
    public static void searchItemAtFirstPage() {
        yandexLaptopAfterSearch.searchByFirstElement();
    }

    @Step("Проверяем, что в результатах поиска на первой странице есть искомый товар")
    public static void checkIfItemExistOnFirstPage() {
        YandexMarketFinalSearch finalSearch = new YandexMarketFinalSearch(driver);
        finalSearch.checkIfFirstPageContainsExactItem(
                yandexLaptopAfterSearch.getCollectResults().get(0).keySet().iterator().next());
    }
}
