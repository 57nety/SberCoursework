package ru.template.example.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Класс, который перехватывает исключения, выброшенные в контроллерах
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * 400 Ошибка во время валидации
     */
    private static final String VALIDATION_ERROR = "Ошибка во время валидации";

    /**
     * 400 Не удалось пройти валидацию
     */
    private static final String VALIDATION_FAILED = "Валидация не пройдена";

    /**
     * 400 Не удалось прочитать HTTP-сообщение
     */
    private static final String HTTP_MESSAGE_IS_NOT_READABLE = "HTTP-сообщение не читается";

    /**
     * 500 Внутренняя ошибка сервера
     */
    private static final String INTERNAL_SERVER_ERROR_REASON = "Внутренняя ошибка сервера";

    /**
     * 400 Аргументы запроса не валидные
     *
     * @param ex      исключение
     * @param headers заголовки, которые будут записаны в ответ
     * @param status  выбранный статус ответа
     * @param request текущий запрос
     * @return объект, представляющий ответ на HTTP-запрос
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        logger.error(VALIDATION_ERROR, ex);

        List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        RestApiError restApiError = new RestApiError(VALIDATION_FAILED, errors);
        return handleExceptionInternal(ex, restApiError, headers, BAD_REQUEST, request);
    }

    /**
     * 400 Не удалось прочитать HTTP сообщение
     *
     * @param ex      исключение
     * @param headers заголовки, которые будут записаны в ответ
     * @param status  выбранный статус ответа
     * @param request текущий запрос
     * @return объект, представляющий ответ на HTTP-запрос
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        logger.error(HTTP_MESSAGE_IS_NOT_READABLE, ex);
        List<String> errors = List.of(ex.getRootCause() == null ? ex.getMessage() : ex.getRootCause().getMessage());
        RestApiError restApiError = new RestApiError(HTTP_MESSAGE_IS_NOT_READABLE, errors);
        return handleExceptionInternal(ex, restApiError, headers, BAD_REQUEST, request);
    }

    /**
     * 500 Все исключения, которые не были обработаны более конкретными методами
     *
     * @param ex      исключение
     * @param request текущий запрос
     * @return объект, представляющий ответ на HTTP-запрос
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<RestApiError> handleAll(final Exception ex, final WebRequest request) {
        logger.error(INTERNAL_SERVER_ERROR_REASON, ex);
        RestApiError restApiError = new RestApiError(INTERNAL_SERVER_ERROR_REASON, List.of(ex.getLocalizedMessage()));
        return new ResponseEntity<>(restApiError, new HttpHeaders(), INTERNAL_SERVER_ERROR);
    }

    /**
     * Класс позволяет представить информацию об ошибке, возникшей при HTTP запросе, в структурированном формате
     */
    @AllArgsConstructor
    @Getter
    @Setter
    public static class RestApiError {
        /**
         * Сообщение об ошибке, предоставляющее краткое описание того, что произошло
         */
        private String message;

        /**
         * Список строк, представляющих конкретные ошибки или детали ошибки
         */
        private List<String> errors;
    }
}
