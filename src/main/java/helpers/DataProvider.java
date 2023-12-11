package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * @author Евгения Лаптева
 * С помощью этого класса мы предоставляем данные для параметризированных тестов
 */
public class DataProvider {

    /**
     * С помощью этого метода мы предоставляем данные для теста Яндекс маркета раздела ноутбуки
     * @return возвращает стрим из переданных аргументов
     */
    public static Stream<Arguments> providerCheckingLaptops() {
        return Stream.of(
                Arguments.of("Ноутбуки", "10000", "30000", "HP", "Lenovo", 12));
    }
}
