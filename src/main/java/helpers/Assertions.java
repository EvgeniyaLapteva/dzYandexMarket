package helpers;

import io.qameta.allure.Step;

/**
 * @author Евгения Лаптева
 * С помощью этого класса мы переопределяем ассерты
 */
public class Assertions {

    @Step("Проверяем что нет ошибки: {message}")
    public static void assertTrue(boolean condition, String message) {
        org.junit.jupiter.api.Assertions.assertTrue(condition, message);
    }
}
