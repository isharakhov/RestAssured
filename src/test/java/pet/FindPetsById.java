package pet;

import baseTest.BaseTestForPet;
import dto.petModel.PetModel;
import dto.response.MessageFromResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static utils.ExpectedObjectBuilder.getErrorResponseForPetNotFound;
import static utils.TestDataHelper.*;
import static utils.TestPetBuilder.getAddNewPetModel;

/**
 * Тест сьют метода GET /pet/findByID
 */
@Epic("Pet контроллер")
@Feature("Find pets by ID")
public class FindPetsById extends BaseTestForPet {

    private Long petID;

    /**
     * Создаем нового pet
     */
    @BeforeEach
    void setUp() {

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
    }

    @Test
    @DisplayName("Find pets by id. Positive case")
    @Story("Поиск питомцев по id, позитивный сценарий")
    public void testFindPetsByIdPositive() {

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
    @DisplayName("Find pets by id. Negative case, if id is not number")
    @Story("Поиск питомцев с несуществующим id, негативный сценарий")
    public void testFindPetsByIdIfIdIsNegative() {

        step("Выполнение запроса GET by id /pet", () -> {
            responseWrapper = steps.getPetByID(NEGATIVE_NUMBER_PET_ID);
        });

        step("Проверка результатов", () -> {
            MessageFromResponse error = responseWrapper.as(MessageFromResponse.class);
            assertSoftly(
                    softAssertions -> {
                        softAssertions
                                .assertThat(responseWrapper.getStatusCode())
                                .withFailMessage("Status code doesn't match")
                                .isEqualTo(STATUS_CODE_ERROR_404);
                        softAssertions
                                .assertThat(responseWrapper.as(MessageFromResponse.class))
                                .withFailMessage("Response body is empty")
                                .isEqualTo(getErrorResponseForPetNotFound());
                    });
        });
    }
}