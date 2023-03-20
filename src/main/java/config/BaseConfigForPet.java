package config;

import org.aeonbits.owner.Config;

/**
 * Интерфейс с основной конфигурацией проекта запросов PET
 */
@Config.Sources({"classpath:config.properties"})
public interface BaseConfigForPet extends Config {

    /**
     * Метод для возвращения значения параметра baseUrlForPet PET из config.properties
     *
     * @return базовый URL
     */
    String baseUrlPet();
}