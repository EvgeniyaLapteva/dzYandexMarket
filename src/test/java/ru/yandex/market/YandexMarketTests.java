package ru.yandex.market;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static steps.StepsAll.*;

import static helpers.Properties.testProperties;

/**
 * @author Евгения Лаптева
 * С помощью этого класса мы тестируем Яндекс маркет
 */
public class YandexMarketTests extends BaseTests {

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
