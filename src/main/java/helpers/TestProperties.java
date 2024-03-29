package helpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:src/test/resources/test.properties"
})
public interface TestProperties extends Config {

    @Config.Key("yandexmarket.url")
    String yandexMarketUrl();
}
