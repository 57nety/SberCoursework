package ru.template.example.documents.dao.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.template.example.documents.controllers.dto.StatusDto;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Сущность документа, хранящегося в БД
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documents")
public class Document {

    /**
     * Уникальный идентификатор документа в БД.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип документа.
     */
    @Column(nullable = false)
    private String type;

    /**
     * Наименование организации, создавшей документ.
     */
    @Column(nullable = false)
    private String organization;

    /**
     * Дата и время создания документа.
     */
    @JsonFormat(pattern = "HH:mm dd.MM.yyyy")
    @DateTimeFormat(pattern = "HH:mm dd.MM.yyyy")
    @Column(nullable = false)
    private LocalDateTime date;

    /**
     * Описание документа.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Статус документа. Принимает значения из перечисления Status.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Пациент, к которому относится документ.
     */
    @Column(nullable = false)
    private String patient;

    /**
     * Конструктор для создания объекта Document.
     *
     * @param id           Уникальный идентификатор документа.
     * @param type         Тип документа.
     * @param organization Наименование организации.
     * @param date         Дата и время создания документа.
     * @param description  Описание документа.
     * @param statusDto    DTO объект статуса документа.
     * @param patient      Пациент, к которому относится документ.
     */
    @JsonCreator
    public Document(
            @JsonProperty("id") Long id,
            @JsonProperty("type") String type,
            @JsonProperty("organization") String organization,
            @JsonProperty("date") LocalDateTime date,
            @JsonProperty("description") String description,
            @JsonProperty("status") StatusDto statusDto,
            @JsonProperty("patient") String patient) {

        this.id = id;
        this.type = type;
        this.organization = organization;
        this.date = date;
        this.description = description;
        this.patient = patient;
        if (statusDto != null) {
            // Извлекаем код из StatusDto и используем его для создания объекта Status
            this.status = Status.fromCode(statusDto.getCode());
        } else {
            this.status = null; // Или устанавливайте значение по умолчанию, как вам удобно
        }
    }
}


