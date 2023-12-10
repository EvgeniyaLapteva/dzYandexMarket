package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataProvider {

    public static Stream<Arguments> providerCheckingLaptops() {
        return Stream.of(
                Arguments.of("Ноутбуки", "10000", "30000", "HP", "Lenovo", 12)//,
               // Arguments.of("Ноутбуки", "Lenovo")
        );
    }
}
