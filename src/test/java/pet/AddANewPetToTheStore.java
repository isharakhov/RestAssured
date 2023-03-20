package pet;

import baseTest.BaseTestForPet;
import dto.petModel.PetModel;
import dto.response.MessageFromResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static utils.ExpectedObjectBuilder.getUnknownErrorResponse;
import static utils.TestDataHelper.*;
import static utils.TestPetBuilder.getAddNewPetModel;

/**
 * Тест сьют для Post pet
 */
@Epic("Pet controller")
@Feature("Add a new pet to the store")
public class AddANewPetToTheStore extends BaseTestForPet {

    @Test
    @DisplayName("Add new pet to store. Positive case")
    @Story("Добавление нового питомца, позитивный сценарий")
    public void testAddANewPetToTheStorePositive() {

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
                            .assertThat(response.getTags()).extracting("id")
                            .withFailMessage("Name of pet doesn't match")
                            .containsAll(request.getTags().stream().map(PetModel.CategoryAndTagsItem::getId).collect(Collectors.toList()));
                    softAssertions
                            .assertThat(response.getTags()).extracting("name")
                            .withFailMessage("Name for tags doesn't match")
                            .containsAll(request.getTags().stream().map(PetModel.CategoryAndTagsItem::getName).collect(Collectors.toList()));
                    softAssertions
                            .assertThat(response.getName())
                            .withFailMessage("Name of pet doesn't match")
                            .isEqualTo(request.getName());
                    softAssertions
                            .assertThat(response.getStatus())
                            .withFailMessage("Status doesn't match")
                            .isEqualTo(request.getStatus());
                    softAssertions
                            .assertThat(response.getId())
                            .withFailMessage("Id is null")
                            .isNotNull();
                    softAssertions
                            .assertThat(response.getPhotoUrls())
                            .withFailMessage("Photo URL doesn't match")
                            .isEqualTo(request.getPhotoUrls());
                    softAssertions
                            .assertThat(response.getCategory().getId())
                            .withFailMessage("Category id doesn't match")
                            .isEqualTo(request.getCategory().getId());
                    softAssertions
                            .assertThat(response.getCategory().getName())
                            .withFailMessage("Category name doesn't match")
                            .isEqualTo(request.getCategory().getName());
                }
        ));
    }

    @Test
    @DisplayName("Add new pet to store. Negative case with not valid ID")
    @Story("Добавление нового питомца с не валидным ID, негативный сценарий")
    public void testAddANewPetToTheStoreNegative() {

        step("Создание тело запроса с валидным ID", () -> {
            request = getAddNewPetModel(NOT_VALID_PET_ID);
        });

        step("Выполнение запроса POST /pet", () -> {
            responseWrapper = steps.createNewPetToStore(request);
        });

        step("Проверка результата", () -> {
            assertSoftly(
                    softAssertions -> {
                        softAssertions
                                .assertThat(responseWrapper.getStatusCode())
                                .withFailMessage("Status code doesn't match")
                                .isEqualTo(SC_OK);
                        softAssertions
                                .assertThat(responseWrapper.as(PetModel.class))
                                .withFailMessage("Body doesn't match")
                                .isNotNull();
                    }
            );
        });
    }
}