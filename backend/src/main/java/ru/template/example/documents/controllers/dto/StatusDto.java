package ru.template.example.documents.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.template.example.documents.dao.entity.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto {
    private String code;
    private String name;

    public StatusDto(String code) {
        Status status = Status.valueOf(code);
        this.code = status.getCode();
        this.name = status.getName();
    }

    public static StatusDto fromStatus(Status status) {
        return new StatusDto(status.getCode(), status.getName());
    }
}
