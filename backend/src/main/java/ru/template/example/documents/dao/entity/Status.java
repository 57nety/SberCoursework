package ru.template.example.documents.dao.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление, представляющее статус документа.
 */
@AllArgsConstructor
@Getter
public enum Status {

    /**
     * Новый статус документа.
     */
    NEW("NEW", "Новый"),

    /**
     * Статус документа "В обработке".
     */
    IN_PROCESS("IN_PROCESS", "В обработке"),

    /**
     * Принятый статус документа.
     */
    ACCEPTED("ACCEPTED", "Принят"),

    /**
     * Отклонённый статус документа.
     */
    DECLINED("DECLINED", "Отклонён");

    /**
     * Код статуса.
     */
    private final String code;

    /**
     * Наименование статуса на русском языке.
     */
    private final String name;

    /**
     * Создает экземпляр статуса на основе его кода.
     *
     * @param code Код статуса.
     * @return Экземпляр статуса.
     * @throws IllegalArgumentException Если переданный код не соответствует ни одному из статусов.
     */
    @JsonCreator
    public static Status fromCode(String code) {
        for (Status status : Status.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Status code: " + code);
    }

    /**
     * Возвращает код статуса в виде строки.
     *
     * @return Код статуса.
     */
    @JsonValue
    @Override
    public String toString() {
        return code;
    }
}