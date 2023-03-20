package steps;

import dto.petModel.PetModel;
import dto.storeModel.StoreModel;
import io.restassured.specification.RequestSpecification;
import utils.ResponseWrapper;

import static io.restassured.RestAssured.given;

/**
 * Класс с реализацией всех Rest шагов
 */
public class Steps {

    /**
     * Экземпляр спецификации RestAssured
     */
    private final RequestSpecification requestSpecification;

    /**
     * Конструктор для создания экземпляра класса
     *
     * @param requestSpecification спецификация RestAssured
     */
    public Steps(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    /**
     * Часть URL для запросов по ID pet
     */
    private final String BY_ID_PATH = "/{id}";

    /**
     * Часть URL для запросов Place order STORE
     */
    private final String ORDER_PATH = "/order";

    /**
     * Часть URL для запросов поиска и удаления покупок STORE
     */
    private final String STORE_BY_ID_PATH = "/order/{orderId}";

    /**
     * Метод создания питомца
     *
     * @param request тело запроса
     * @return оболочка для работы с ответом
     */
    public ResponseWrapper createNewPetToStore(PetModel request) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .body(request)
                .post()
                .andReturn());
    }

    /**
     * Метод поиска питомцев по id
     *
     * @param id id
     */
    public ResponseWrapper getPetByID(Long id) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .get(BY_ID_PATH, id)
                .andReturn());
    }

    /**
     * Метод обновления существуещего питомца по id
     *
     * @param request тело запроса
     * @return оболочка для работы с ответом
     */
    public ResponseWrapper updateExistingPetByID(PetModel request) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .body(request)
                .put()
                .andReturn());
    }

    /**
     * Метод обновления существуещего питомца по id
     *
     * @param id id питомца
     * @return оболочка для работы с ответом
     */
    public ResponseWrapper deletePetById(Long id) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .delete(BY_ID_PATH, id)
                .andReturn());
    }

    /**
     * @param request
     * @return
     */
    public ResponseWrapper createNewPurchaseToStore(StoreModel request) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .body(request)
                .post(ORDER_PATH)
                .andReturn());
    }

    /**
     * @param orderId
     * @return
     */
    public ResponseWrapper getPurchaseByID(Long orderId) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .get(STORE_BY_ID_PATH, orderId)
                .andReturn());
    }

    /**
     * @param orderId
     * @return
     */
    public ResponseWrapper deletePurchaseById(Long orderId) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .delete(STORE_BY_ID_PATH, orderId)
                .andReturn());
    }
}