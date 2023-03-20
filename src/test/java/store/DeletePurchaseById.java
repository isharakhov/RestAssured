package store;

import baseTest.BaseTestForStore;
import dto.response.MessageFromResponse;
import dto.storeModel.StoreModel;

import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static utils.TestDataHelper.*;
import static utils.TestStoreBuilder.getAddNewStoreModel;

public class DeletePurchaseById extends BaseTestForStore {

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

        step("Выполнение запроса GET by id /pet", () -> {
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
    @DisplayName("Delete Purchase by ID. Positive case")
    @Story("Удаление покупки по id. Позитивный кейс")
    public void deletePurchasePositive() {

        step("Выполнение запроса delete by id /store", () -> {
            responseWrapper = steps.deletePurchaseById(orderId);
        });

        step("Проверка результатов", () -> assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(responseWrapper.getStatusCode())
                            .withFailMessage("Status code doesn't match")
                            .isEqualTo(SC_OK);
                    softAssertions
                            .assertThat(responseWrapper.as(MessageFromResponse.class))
                            .withFailMessage("Response body doesn't match")
                            .isNotNull();
                }));
    }

    @Test
    @DisplayName("Delete Purchase by ID. Negative case")
    @Story("Удаление покупки по id. Негативный кейс")
    public void deletePurchaseNegative() {

        step("Выполнение запроса delete by id /store", () -> {
            responseWrapper = steps.deletePurchaseById(NEGATIVE_NUMBER_PURCHASE_ID);
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