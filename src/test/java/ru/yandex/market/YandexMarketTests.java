package ru.yandex.market;

import io.qameta.allure.Feature;
import pages.YandexLaptopAfterSearch;
import pages.YandexLaptopBeforeSearch;
import pages.YandexMarketStart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class YandexMarketTests extends BaseTests {

    @Feature("Тест Яндекс Маркета")
    @DisplayName("Тест Яндекс Маркета - раздел Ноутбуки")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#providerCheckingLaptops")
    public void testYandexMarket(String titleOfSection, String priceFrom, String priceTo, String firstProducer,
                                 String secondProducer) throws InterruptedException {
        chromeDriver.get("https://market.yandex.ru/");
        YandexMarketStart yandexMarketStart = new YandexMarketStart(chromeDriver);
        yandexMarketStart.goToCatalog();
        yandexMarketStart.goToLaptopsPage();
        YandexLaptopBeforeSearch yandexLaptopBeforeSearch = new YandexLaptopBeforeSearch(chromeDriver);
        yandexLaptopBeforeSearch.checkIfItLaptopsSection(titleOfSection);
        yandexLaptopBeforeSearch.setPrices(priceFrom, priceTo);
        yandexLaptopBeforeSearch.setProducer(firstProducer, secondProducer);
        YandexLaptopAfterSearch yandexLaptopAfterSearch = new YandexLaptopAfterSearch(chromeDriver);
        yandexLaptopAfterSearch.scrollToTheEndOfFirstPage();
        yandexLaptopAfterSearch.putItemsToCollectResults();
        System.out.println(yandexLaptopAfterSearch.getCollectResults().size());
        System.out.println(yandexLaptopAfterSearch.getCollectResults());
        yandexLaptopAfterSearch.checkThatFirstPageContainsMoreThan12Elements();
        yandexLaptopAfterSearch.addTheRestResultsToFirstPage();
        System.out.println(yandexLaptopAfterSearch.getCollectResults().size());
        yandexLaptopAfterSearch.checkIfResultContainRightProducersAndPrices(firstProducer, secondProducer, priceFrom,
                priceTo);
        yandexLaptopAfterSearch.searchResultsByNameOfFirstItem();
        yandexLaptopAfterSearch.searchByFirstElement();
    }
}
