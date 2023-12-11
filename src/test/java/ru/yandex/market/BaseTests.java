package ru.yandex.market;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * @author Евгения Лаптева
 * С помощью этого класса мы задаем базовое поведение тестов
 */
public class BaseTests {

    /**
     * Это поле определяет вебдрайвер, которым мы будем пользоваться в тестах
     */
    protected WebDriver chromeDriver;

    /**
     * Этот метод задает параметры использования вебдрайвера перед каждым тестом
     */
    @BeforeEach
    public void before(){
        System.setProperty("webdriver.chrome.driver",System.getenv("CHROME_DRIVER"));
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
    }

    /**
     * Этот метод закрывает вебдрайвер  после каждого теста
     */
    @AfterEach
    public void after(){
        chromeDriver.quit();
    }
}
