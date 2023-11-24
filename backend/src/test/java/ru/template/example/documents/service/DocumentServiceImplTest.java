package ru.template.example.documents.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.template.example.documents.controllers.dto.DocumentDto;
import ru.template.example.documents.controllers.dto.StatusDto;
import ru.template.example.documents.dao.entity.Status;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс, тестирующий методы класса DocumentServiceImpl
 */
@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = DocumentServiceImplTest.Initializer.class)
public class DocumentServiceImplTest {
    /**
     * Контейнер PostgreSQL используется в тестах, как временная БД
     */
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testDB")
            .withUsername("postgres")
            .withPassword("postgres");

    /**
     * Класс настраивает контекст приложения до его фактического запуска
     */
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        /**
         *  Настройка параметров подключения к БД
         * @param applicationContext приложение для настройки
         */
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresContainer.getUsername(),
                    "spring.datasource.password=" + postgresContainer.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }

    /**
     * Шаблон документа
     */
    private static final DocumentDto DOCUMENT_DTO = DocumentDto.builder()
            .patient("Пациент")
            .type("Тип")
            .organization("Организация")
            .description("Описание")
            .build();

    /**
     * Сервис для реализации логики взаимодействия с документами
     */
    private final DocumentService documentService;

    /**
     * Конструктор инициализирует поле documentService
     * @param documentService бин, инициализирующий поле
     */
    @Autowired
    public DocumentServiceImplTest(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Test
    @DisplayName("Successful save document")
    public void testSuccessfulSaveDocument() {
        DocumentDto savedDocument = documentService.save(DOCUMENT_DTO);
        Long idSavedDocument = savedDocument.getId();

        Assertions.assertEquals(savedDocument, documentService.get(idSavedDocument));
    }

    @Test
    @DisplayName("Successful update document")
    public void testSuccessfulUpdateDocument() {
        DocumentDto savedDocument = documentService.save(DOCUMENT_DTO);
        savedDocument.setStatus(StatusDto.fromStatus(Status.ACCEPTED));
        documentService.update(savedDocument);
        StatusDto statusSavedDocument = documentService.get(savedDocument.getId()).getStatus();

        Assertions.assertEquals(StatusDto.fromStatus(Status.ACCEPTED), statusSavedDocument);
    }

    @Test
    @DisplayName("Successful delete document")
    public void testSuccessfulDeleteDocument() {
        DocumentDto savedDocument = documentService.save(DOCUMENT_DTO);
        documentService.delete(savedDocument.getId());
        Long idDeletedDocument = savedDocument.getId();

        Assertions.assertThrows(EntityNotFoundException.class, () -> documentService.get(idDeletedDocument));
    }

    @Test
    @DisplayName("Successful delete all documents")
    public void testSuccessfulDeleteAllDocuments() {
        DocumentDto documentDto1 = DOCUMENT_DTO;
        DocumentDto documentDto2 = DOCUMENT_DTO;
        DocumentDto documentDto3 = DOCUMENT_DTO;

        documentDto1 = documentService.save(documentDto1);
        documentDto2 = documentService.save(documentDto2);
        documentDto3 = documentService.save(documentDto3);

        Set<Long> ids = new HashSet<>();
        ids.add(documentDto1.getId());
        ids.add(documentDto2.getId());
        ids.add(documentDto3.getId());

        documentService.deleteAll(ids);
        Long idDeletedDocument = documentDto3.getId();

        Assertions.assertThrows(EntityNotFoundException.class, () -> documentService.get(idDeletedDocument));
    }

    @Test
    @DisplayName("Successful find all documents")
    public void testSuccessFindAllDocuments() {
        DocumentDto documentDto1 = DOCUMENT_DTO;
        DocumentDto documentDto2 = DOCUMENT_DTO;
        DocumentDto documentDto3 = DOCUMENT_DTO;

        documentDto1 = documentService.save(documentDto1);
        documentDto2 = documentService.save(documentDto2);
        documentDto3 = documentService.save(documentDto3);

        List<DocumentDto> savedDocumentsDto = List.of(documentDto1, documentDto2, documentDto3);
        List<DocumentDto> findDocumentsDto = documentService.findAll();

        Assertions.assertEquals(savedDocumentsDto, findDocumentsDto);
    }

    @Test
    @DisplayName("Successful get document by id")
    public void testSuccessGetDocumentById() {
        DocumentDto savedDocumentsDto = documentService.save(DOCUMENT_DTO);
        Long idSavedDocument = savedDocumentsDto.getId();
        Assertions.assertEquals(savedDocumentsDto, documentService.get(idSavedDocument));
    }
}
