package utils;

import dto.response.MessageFromResponse;

import static utils.TestDataHelper.STATUS_CODE_ERROR_404;
import static utils.TestDataHelper.STATUS_CODE_OK;

/**
 * Вспомагательный класс для формирования ожидаемых результатов
 */
public class ExpectedObjectBuilder {

    /**
     * Код неизвестной ошибки
     */
    private static final int UNKNOWN_CODE = 500;

    /**
     * Код ошибки 1
     */
    public static final int CODE_ONE = 1;

    /**
     * Тип неизвестной ошибки
     */
    private static final String UNKNOWN_TYPE = "unknown";

    /**
     * Тип ошибки с кодом 1
     */
    private static final String TYPE_ONE = "error";

    /**
     * Сообщение неизвестной ошибки
     */
    private static final String UNKNOWN_MESSAGE = "something bad happened";

    private static final String MESSAGE_PET_NOT_FOUND = "Pet not found";


    /**
     * Метод формирования ожидаемого результата неизвестой ошибки
     *
     * @return тело ошибки
     */
    public static MessageFromResponse getUnknownErrorResponse() {
        return MessageFromResponse.builder()
                .code(UNKNOWN_CODE)
                .type(UNKNOWN_TYPE)
                .message(UNKNOWN_MESSAGE)
                .build();
    }

    public static MessageFromResponse getErrorResponseForPetNotFound() {
        return MessageFromResponse.builder()
                .code(CODE_ONE)
                .type(TYPE_ONE)
                .message(MESSAGE_PET_NOT_FOUND)
                .build();
    }

    public static MessageFromResponse getMessageOKThenDeletePet() {
        return MessageFromResponse.builder()
                .code(STATUS_CODE_OK)
                .type(UNKNOWN_TYPE)
                .message("/{pet id}")
                .build();
    }

    public static MessageFromResponse getMessage404() {
        return MessageFromResponse.builder()
                .code(STATUS_CODE_ERROR_404)
                .type(UNKNOWN_TYPE)
                .message("Order not Found")
                .build();
    }

}
