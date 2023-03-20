package dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * Класс для работы с message from сервер
 */
@Data
@Builder
public class MessageFromResponse {
	private final Integer code;
	private final String type;
	private final String message;
}
