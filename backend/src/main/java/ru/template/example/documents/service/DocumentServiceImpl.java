package ru.template.example.documents.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.template.example.documents.controllers.dto.DocumentDto;
import ru.template.example.documents.controllers.dto.StatusDto;
import ru.template.example.documents.dao.entity.Document;
import ru.template.example.documents.dao.entity.Status;
import ru.template.example.documents.dao.repository.DocumentsRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервис по взаимодействию с документами
 */
@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {
    /**
     * Репозиторий для взаимодействия с БД
     */
    private final DocumentsRepository documentsRepository;

    /**
     * Mapper для отображения dto на entity, и entity на dto
     */
    private final ObjectMapper objectMapper;

    /**
     * Сохранить документ в БД
     *
     * @param documentDto документ
     * @return
     */
    @Transactional
    public DocumentDto save(DocumentDto documentDto) {
        Document document = objectMapper.convertValue(documentDto, Document.class);
        document.setDate(LocalDateTime.now());
        document.setStatus(Status.NEW);
        documentsRepository.save(document);
        return objectMapper.convertValue(document, DocumentDto.class);
    }

    /**
     * Обновить документ в БД
     *
     * @param documentDto документ
     * @return обновлённый документ
     */
    @Transactional
    public DocumentDto update(DocumentDto documentDto) {
        Document document = objectMapper.convertValue(documentDto, Document.class);
        documentsRepository.save(document);
        return objectMapper.convertValue(document, DocumentDto.class);
    }

    /**
     * Удалить документ из БД
     *
     * @param id идентификатор документа
     */
    @Transactional
    public void delete(Long id) {
        documentsRepository.deleteById(id);
    }

    /**
     * Удалить список документов из БД
     *
     * @param ids идентификаторы документов
     */
    @Transactional
    public void deleteAll(Set<Long> ids) {
        documentsRepository.deleteAllByIdIn(ids);
    }

    /**
     * Получить все документы из БД
     *
     * @return список всех документов
     */
    @Transactional
    public List<DocumentDto> findAll() {
        List<Document> documents = documentsRepository.findAll();
        return documents.stream()
                .map(document -> objectMapper.convertValue(document, DocumentDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Получить документ по идентификатору
     *
     * @param id идентификатор
     * @return документ
     */
    @Transactional
    public DocumentDto get(Long id) {
        Optional<Document> document = documentsRepository.findById(id);
        if (document.isEmpty()) {
            throw new EntityNotFoundException("Document with id " + id + " not found");
        }
        return objectMapper.convertValue(document.get(), DocumentDto.class);
    }
}
