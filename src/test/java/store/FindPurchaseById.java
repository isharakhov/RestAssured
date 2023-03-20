package store;

import baseTest.BaseTestForStore;

import dto.response.MessageFromResponse;
import dto.storeModel.StoreModel;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static utils.TestDataHelper.*;
import static utils.TestStoreBuilder.getAddNewStoreModel;

/**
 * Тест сьют метода GET /store/orderID
 */
@Epic("Store контроллер")
@Feature("Find purchase by ID")
public class FindPurchaseById extends BaseTestForStore {

    private Long orderId;

    @BeforeEach
    void setUp() {

        step("Создание тело запроса с валидным ID", () -> {
            request = getAddNewStoreModel(VALID_PET_ID);
        });

        step("Выполнение запроса POST /store", () -> {
            responseWrapper = steps.createNewPurchaseToStore(request);
            response = responseWrapper.as(StoreModel.class);
        });

        step("Проверка результатов", () -> assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(responseWrapper.getStatusCode())
                            .withFailMessage("Name of pet doesn't match")
                            .isEqualTo(STATUS_CODE_OK);
                }
        ));

        step("Запоминаем orderId, созданного purchase", () -> {
            orderId = response.getId();
        });
    }

    @Test
    @DisplayName("Find purchase by id. Positive case")
    @Story("Поиск покупки по id, позитивный сценарий")
    public void testFindPetsByIdPositive() {

        step("Выполнение запроса GET by id /purchase", () -> {
            responseWrapper = steps.getPurchaseByID(orderId);
        });

        step("Проверка результатов", () -> assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(responseWrapper.getStatusCode())
                            .withFailMessage("Status code doesn't match")
                            .isEqualTo(SC_OK);
                    softAssertions
                            .assertThat(responseWrapper.as(StoreModel.class))
                            .withFailMessage("Response body is empty")
                            .isNotNull();
                }));
    }

    @Test
    @DisplayName("Find purchase by id. Negative case")
    @Story("Поиск покупки по id, позитивный сценарий")
    public void testFindPetsByIdNegative() {

        step("Выполнение запроса GET by id /purchase", () -> {
            responseWrapper = steps.getPurchaseByID(NEGATIVE_NUMBER_PURCHASE_ID);
        });

        step("Проверка результатов", () -> assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(responseWrapper.getStatusCode())
                            .withFailMessage("Status code doesn't match")
                            .isEqualTo(STATUS_CODE_ERROR_404);
                    softAssertions
                            .assertThat(responseWrapper.as(MessageFromResponse.class))
                            .withFailMessage("Response body is empty")
                            .isNotNull();
                }));
    }
}