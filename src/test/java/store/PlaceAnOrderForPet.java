package store;

import baseTest.BaseTestForStore;
import dto.storeModel.StoreModel;

import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static utils.TestDataHelper.STATUS_CODE_OK;
import static utils.TestDataHelper.VALID_PET_ID;
import static utils.TestStoreBuilder.getAddNewStoreModel;

public class PlaceAnOrderForPet extends BaseTestForStore {

    @Test
    @DisplayName("Add new purchase to store. Positive case")
    @Story("Добавление новой покупки, позитивный сценарий")
    public void testAddANewPurchaseToTheStorePositive() {

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
                            .withFailMessage("Status code doesn't match")
                            .isEqualTo(STATUS_CODE_OK);
                    softAssertions
                            .assertThat(response.getPetId())
                            .withFailMessage("petID doesn't match")
                            .isEqualTo(request.getPetId());
                    softAssertions
                            .assertThat(response.getQuantity())
                            .withFailMessage("Quantity doesn't match")
                            .isEqualTo(request.getQuantity());
                    softAssertions
                            .assertThat(response.getStatus())
                            .withFailMessage("Status doesn't match")
                            .isEqualTo(request.getStatus());
                    softAssertions
                            .assertThat(response.isComplete())
                            .withFailMessage("Doesn't complete")
                            .isEqualTo(request.isComplete());

                }
        ));
    }
}