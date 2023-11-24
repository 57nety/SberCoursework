package ru.template.example.documents.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * Модель множества id документов
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdsDto {
    /**
     * Множество id документов
     */
    @NotEmpty(message = "Множество id не может быть пустым")
    private Set<Long> ids;

}
