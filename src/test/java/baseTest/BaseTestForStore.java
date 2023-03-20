package baseTest;

import config.BaseConfigForStore;
import dto.storeModel.StoreModel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import steps.Steps;
import utils.ResponseWrapper;

public class BaseTestForStore {

    /**
     * Экземпляр класса с телом запроса STORE
     */
    protected StoreModel request;

    /**
     * Экземпляр класса с телом ответа STORE
     */
    protected StoreModel response;

    /**
     * Экземплял класса с оболочкой ответа
     */
    protected ResponseWrapper responseWrapper;

    /**
     * Экземпляр интерфейса с конфигурацией
     */
    private final BaseConfigForStore config = ConfigFactory.create(BaseConfigForStore.class, System.getenv());

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
    protected final RequestSpecification requestSpecificationForStore = new RequestSpecBuilder()
            .setBaseUri(config.baseUrlStore())
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .log(LogDetail.ALL)
            .build();

    /**
     * Экземпляр класса с Rest шагами
     */
    protected final Steps steps = new Steps(requestSpecificationForStore);
}