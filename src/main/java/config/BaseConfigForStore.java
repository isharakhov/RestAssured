package config;

import org.aeonbits.owner.Config;

/**
 * Интерфейс с основной конфигурацией проекта запросов Store
 */
@Config.Sources({"classpath:config.properties"})
public interface BaseConfigForStore extends Config {

    /**
     * Метод для возвращения значения параметра baseUrlForStore Store из config.properties
     *
     * @return базовый URL
     */
    String baseUrlStore();
}