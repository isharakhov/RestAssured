package utils;

import com.github.javafaker.Faker;

public class TestDataHelper {

    /**
     * Валидный идентификатор питомца
     */
    public static final Long VALID_PET_ID = 0l;

    /**
     * Не валидный иденфикатор питомца с отлицательным ID
     */
    public static final Long NEGATIVE_NUMBER_PET_ID = -1l;

    /**
     * Не валидный иденфикатор питомца с отлицательным ID
     */
    public static final Long NEGATIVE_NUMBER_PURCHASE_ID = -55555l;

    /**
     * Не валидный идентификатор питомца
     */
    public static final Long NOT_VALID_PET_ID = -666l;

    /**
     * Валидный идентификатор категории
     */
    public static final int VALID_CATEGORY_ID = 0;

    /**
     * Валидный идентификатор тэга
     */
    public static final int VALID_TAG_ID = 0;

    /**
     * Валидное имя тэга
     */
    public static final String VALID_TAG_NAME = "tagOne";

    /**
     * Валидный статус питомца
     */
    public static final String VALID_STATUS = "available";

    /**
     * Статус код успешного результата запроса
     */
    public static final int STATUS_CODE_OK = 200;

    /**
     * Статус код не успешного результата запроса 500
     */
    public static final int STATUS_CODE_ERROR_500 = 500;

    /**
     * Статус код не успешного результата запроса 404
     */
    public static final int STATUS_CODE_ERROR_404 = 404;

    /**
     * Валидное количество покупки
     */
    public static final int VALID_QUANTITY_PURCHASE = 3;

    /**
     * Экземпляр класса Faker
     */
    private static final Faker faker = new Faker();

    /**
     * Регулярное выражениек для генерации случайного URL
     */
    private static final String REGEXP_PHOTO_URL = "http:\\\\[a-z]{5}";

    /**
     * Регулярное выражениек для генерации случайного не валидного статуса
     */
    private static final String REGEXP_STRING = "[a-z]{20}";

    /**
     * Валидная дата и время
     */
    public static final String DATE_AND_TIME_REGEXP = "(\\d{4})-(\\d{2})-(\\d{2})[T](\\d{2}):(\\d{2}):(\\d{2})[Z]";

//    public static final String DATE_AND_TIME_REGEXP = "((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)"
//            + "|(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))"
//            + "|(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))"
//            + "|(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))"
//            +"[T](0?[0-9]|1?[0-9]|2?[0-3]):(\\d{2}):(\\d{2})[Z])";

    /**
     * Валидный статус
     */
    public static final Boolean VALID_COMPLETE = true;

    /**
     * Метод получения случайного имени питомца
     *
     * @return случайное имя питомца
     */
    public static String getRandomPetName() {
        return faker.dog().name();
    }

    /**
     * Метод получения случайного имени категории
     *
     * @return случайное имя категории
     */
    public static String getRandomCategoryName() {
        return faker.dog().breed();
    }

    /**
     * Метод получения случайного URL
     *
     * @return случайное URL
     */
    public static String getRandomUrl() {
        return faker.regexify(REGEXP_PHOTO_URL);
    }

    /**
     * Метод получения случайного не валидного статуса
     *
     * @return случайный не валидный статус
     */
    public static String getRandomString() {
        return faker.regexify(REGEXP_STRING);
    }

    public static String getRandomDate() {
        return faker.regexify(DATE_AND_TIME_REGEXP);
    }

}