package baseTest;

import config.BaseConfigForPet;
import dto.petModel.PetModel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import steps.Steps;
import utils.ResponseWrapper;

/**
 * Базовый тестовый класс с общими настройками PET
 */
public class BaseTestForPet {

    /**
     * Экземпляр класса с телом запроса
     */
    protected PetModel request;

    /**
     * Экземпляр класса с телом ответа
     */
    protected PetModel response;

    /**
     * Экземплял класса с оболочкой ответа
     */
    protected ResponseWrapper responseWrapper;

    /**
     * Экземпляр интерфейса с конфигурацией
     */
    private final BaseConfigForPet config = ConfigFactory.create(BaseConfigForPet.class, System.getenv());

    /**
     * Метод для получения спецификации RestAssured
     * <p>
     * baseUrl - базовый URL
     * contentType - параметр в header со значением JSON
     * accept - параметр в header со значением JSON
     * filter - создает фильтр для allure
     * log - логирование всех деталей
     *
     * @return спецификация
     */
    protected final RequestSpecification requestSpecificationForPet = new RequestSpecBuilder()
            .setBaseUri(config.baseUrlPet())
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .addFilter(new RequestLoggingFilter(LogDetail.ALL))
            .build();

    /**
     * Экземпляр класса с Rest шагами
     */
    protected final Steps steps = new Steps(requestSpecificationForPet);
}