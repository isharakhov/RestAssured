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
import static utils.ExpectedObjectBuilder.getMessage404;
import static utils.ExpectedObjectBuilder.getMessageOKThenDeletePet;
import static utils.TestDataHelper.*;
import static utils.TestPetBuilder.getAddNewPetModel;

public class DeletePet extends BaseTestForPet {

    private Long petID;

    /**
     * Создаем нового pet
     */
    @BeforeEach
    void setUpBeforeFindByIdPet() {
        step("Создание тело запроса с валидным ID", () -> {
            request = getAddNewPetModel(VALID_PET_ID);
        });

        step("Выполнение запроса POST /pet", () -> {
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
    @DisplayName("Delete Pet by ID. Positive case")
    @Story("Удаление питомца по id. Позитивный кейс")
    public void deletePetPositive() {

        step("Выполнение запроса delete by id /pet", () -> {
            responseWrapper = steps.deletePetById(petID);
        });

        step("Проверка результатов", () -> assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(responseWrapper.getStatusCode())
                            .withFailMessage("Status code doesn't match")
                            .isEqualTo(SC_OK);
                    softAssertions
                            .assertThat(responseWrapper.as(MessageFromResponse.class))
                            .withFailMessage("Response body is empty")
                            .isNotNull();
                }));
    }

    @Test
    @DisplayName("Delete Pet by ID. Negative case")
    @Story("Удаление питомца по id. Негативный кейс")
    public void deletePetNegative() {

        step("Выполнение запроса delete by id /pet", () -> {
            responseWrapper = steps.deletePetById(NEGATIVE_NUMBER_PET_ID);
        });

        step("Проверка результатов", () -> assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(responseWrapper.getStatusCode())
                            .withFailMessage("Status code doesn't match")
                            .isEqualTo(STATUS_CODE_ERROR_404);
                }));
    }
}