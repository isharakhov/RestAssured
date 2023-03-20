package pet;

import baseTest.BaseTestForPet;
import dto.petModel.PetModel;
import dto.response.MessageFromResponse;

import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static utils.ExpectedObjectBuilder.getUnknownErrorResponse;
import static utils.TestDataHelper.*;
import static utils.TestPetBuilder.getAddNewPetModel;

public class UpdateAnExistingPet extends BaseTestForPet {

    private Long petID;

    /**
     * Создаем нового pet
     */
    @BeforeEach
    void setUp() {
        step("Создание тело запроса с валидным ID", () -> {
            request = getAddNewPetModel(VALID_PET_ID);
        });

        step("Выполнение запроса PUT /pet", () -> {
            responseWrapper = steps.createNewPetToStore(request);
            response = responseWrapper.as(PetModel.class);
        });

        step("Проверка результатов", () -> assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(responseWrapper.getStatusCode())
                            .withFailMessage("Status cod doesn't match")
                            .isEqualTo(STATUS_CODE_OK);
                    softAssertions
                            .assertThat(responseWrapper.as(PetModel.class))
                            .withFailMessage("Response body doesn't match")
                            .isEqualTo(request);
                }
        ));

        step("Запоминаем petId, созданного pet", () -> {
            petID = response.getId();
        });

        step("Выполнение запроса GET by id /pet", () -> {
            responseWrapper = steps.getPetByID(petID);
        });

        step("Проверка результатов", () -> assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(responseWrapper.getStatusCode())
                            .withFailMessage("Status code doesn't match")
                            .isEqualTo(SC_OK);
                    softAssertions
                            .assertThat(responseWrapper.as(PetModel.class))
                            .withFailMessage("Response body is empty")
                            .isNotNull();
                }));
    }

    @Test
    @DisplayName("Update an existing pet. Positive case")
    @Story("Обновление существующего питомца, позитивный сценарий")
    public void testUpdateAnExistingPetPositive() {

        step("Создание тело запроса с валидным ID", () -> {
            request = getAddNewPetModel(petID);
        });

        step("Выполнение запроса PUT /pet", () -> {
            responseWrapper = steps.updateExistingPetByID(request);
        });

        step("Проверка результатов", () -> assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(responseWrapper.getStatusCode())
                            .withFailMessage("Status cod doesn't match")
                            .isEqualTo(STATUS_CODE_OK);
                    softAssertions
                            .assertThat(responseWrapper.as(PetModel.class))
                            .withFailMessage("Response body doesn't match")
                            .isNotNull();
                }
        ));
    }

    @Test
    @DisplayName("Update an existing pet. Negative case")
    @Story("Обновление существующего питомца, негативный сценарий")
    public void testUpdateAnExistingPetNegative() {

        step("Создание тело запроса с валидным ID", () -> {
            request = getAddNewPetModel(NOT_VALID_PET_ID);
        });

        step("Выполнение запроса PUT /pet", () -> {
            responseWrapper = steps.updateExistingPetByID(request);
        });

        step("Проверка результатов", () -> assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(responseWrapper.getStatusCode())
                            .withFailMessage("Status cod doesn't match")
                            .isEqualTo(SC_OK);
                    softAssertions
                            .assertThat(responseWrapper.as(PetModel.class))
                            .withFailMessage("Response body doesn't match")
                            .isNotNull();
                }
        ));
    }
}