package utils;

import dto.storeModel.StoreModel;

import static utils.TestDataHelper.*;

public class TestStoreBuilder {

    /**
     * Метод для формирования тела запроса создания покупки
     *
     * @param id идентификатор покупки
     * @return тело запроса
     */
    public static StoreModel getAddNewStoreModel(Long id) {
        return StoreModel.builder()
                .id(id)
                .petId(VALID_PET_ID)
                .quantity(VALID_QUANTITY_PURCHASE)
                .shipDate(getRandomDate())
                .status(getRandomString())
                .complete(VALID_COMPLETE)
                .build();
    }
}
