package ru.template.example.documents.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Модель id документа
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdDto {
    /**
     * Id документа
     */
    @NotNull(message = "id документа не может быть null")
    private Long id;
}
