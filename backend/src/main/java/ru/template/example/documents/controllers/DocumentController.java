package ru.template.example.documents.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.template.example.documents.controllers.dto.DocumentDto;
import ru.template.example.documents.controllers.dto.IdDto;
import ru.template.example.documents.controllers.dto.IdsDto;
import ru.template.example.documents.controllers.dto.StatusDto;
import ru.template.example.documents.dao.entity.Status;
import ru.template.example.documents.kafka.KafkaSender;
import ru.template.example.documents.service.DocumentService;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер, принимающий запросы от клиента
 */
@RequiredArgsConstructor
@Validated
@RequestMapping("/documents")
@RestController
public class DocumentController {
    /**
     * Сервис, реализующий логику, в зависимости от запроса клиента
     */
    private final DocumentService service;

    /**
     *  Producer, который отправляет сообщения в kafka
     */
    private final KafkaSender kafkaSender;

    /**
     * Метод обрабатывает POST запрос на сохранение документа по пути /documents
     *
     * @param documentDto модель документа, который надо сохранить
     * @return модель сохранённого документа
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDto save(@RequestBody @Valid DocumentDto documentDto) {
        return service.save(documentDto);
    }

    /**
     * Метод обрабатывает GET запросы по пути /documents
     *
     * @return список моделей документов
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DocumentDto> get() {
        return service.findAll();
    }

    /**
     * Метод обрабатывает POST запросы по пути /documents/send
     *
     * @param id документа, который надо отправить
     * @return модель, отправленного документа
     */
    @PostMapping(
            path = "send",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DocumentDto send(@RequestBody @Valid IdDto id) {
        DocumentDto documentDto = service.get(id.getId());
        kafkaSender.sendMessage(documentDto);
        documentDto.setStatus(StatusDto.fromStatus(Status.IN_PROCESS));
        return service.update(documentDto);
    }

    /**
     * Метод обрабатывает DELETE запросы по пути /document/{id}
     *
     * @param id документа, который надо удалить
     */
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    /**
     * Метод обрабатывает DELETE запросы по пути /document
     *
     * @param idsDto модель множества id тех документов, которые надо удалить
     */
    @DeleteMapping
    public void deleteAll(@RequestBody @Valid IdsDto idsDto) {
        service.deleteAll(idsDto.getIds());
    }
}
