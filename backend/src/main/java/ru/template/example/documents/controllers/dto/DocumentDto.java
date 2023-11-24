package ru.template.example.documents.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Модель документа, отправляемая пользователю
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {
    /**
     * Номер
     */
    private Long id;

    /**
     * Вид документа
     */
    @NotNull(message = "Вид документа не может быть null")
    @NotBlank(message = "Вид документа не может быть пустым")
    @Size(min = 1, max = 255, message = "Вид документа быть от 1 до 255 символов")
    private String type;

    /**
     * Организация
     */
    @NotNull(message = "Название организации не может быть null")
    @NotBlank(message = "Название организации не может быть пустым")
    @Size(min = 1, max = 255, message = "Название организации должно быть от 1 до 255 символов")
    private String organization;

    /**
     * Описание
     */
    @NotNull(message = "Описание не может быть null")
    @NotBlank(message = "Описание не может быть пустым")
    @Size(min = 1, max = 255, message = "Описание должно быть от 1 до 255 символов")
    private String description;

    /**
     * Пациент
     */
    @NotNull(message = "ФИО пациента не может быть null")
    @NotBlank(message = "ФИО пациента не может быть пустым")
    @Size(min = 1, max = 255, message = "ФИО пациента должно быть от 1 до 255 символов")
    private String patient;

    /**
     * Дата документа
     */
    @JsonFormat(pattern = "HH:mm dd.MM.yyyy")
    private LocalDateTime date;

    /**
     * Статус
     */
    private StatusDto status;
}
