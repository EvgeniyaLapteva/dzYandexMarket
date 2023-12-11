package ru.yandex.market;

import io.qameta.allure.Feature;
import pages.YandexLaptopAfterSearch;
import pages.YandexLaptopBeforeSearch;
import pages.YandexMarketFinalSearch;
import pages.YandexMarketStart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static steps.StepsAll.*;

import static helpers.Properties.testProperties;

public class YandexMarketTests extends BaseTests {

    @Feature("Тест Яндекс Маркета")
    @DisplayName("Тест Яндекс Маркета - раздел Ноутбуки")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#providerCheckingLaptops")
    public void testYandexMarket(String titleOfSection, String priceFrom, String priceTo, String firstProducer,
                                 String secondProducer, int count) throws InterruptedException {
        chromeDriver.get(testProperties.yandexMarketUrl());
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
        yandexLaptopAfterSearch.checkThatFirstPageContainsMoreThanExactElements(count);
        yandexLaptopAfterSearch.addTheRestResultsToFirstPage();
        System.out.println(yandexLaptopAfterSearch.getCollectResults().size());
        yandexLaptopAfterSearch.checkIfResultContainRightProducersAndPrices(firstProducer, secondProducer, priceFrom,
                priceTo);
        yandexLaptopAfterSearch.searchByFirstElement();
        YandexMarketFinalSearch finalSearch = new YandexMarketFinalSearch(chromeDriver);
        finalSearch.checkIfFirstPageContainsExactItem(
                yandexLaptopAfterSearch.getCollectResults().get(0).keySet().iterator().next());
    }

    @Feature("Тест Яндекс Маркета - StepsAll")
    @DisplayName("Тест Яндекс Маркета - раздел Ноутбуки")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#providerCheckingLaptops")
    public void testYandexMarketStepsAll(String titleOfSection, String priceFrom, String priceTo, String firstProducer,
                                 String secondProducer, int count) throws InterruptedException {
        openSite(testProperties.yandexMarketUrl(), chromeDriver);
        goToLaptopsPage();
        checkSection(titleOfSection);
        setPricesAndProducers(priceFrom, priceTo, firstProducer, secondProducer);
        checkIfElementsEnough(count);
        checkIfAllResultsMatchFilter(firstProducer, secondProducer, priceFrom, priceTo);
        searchItemAtFirstPage();
        checkIfItemExistOnFirstPage();
    }
}
