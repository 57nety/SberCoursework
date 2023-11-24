package ru.template.example.documents.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.template.example.documents.controllers.dto.DocumentDto;
import ru.template.example.documents.dao.entity.Document;
import ru.template.example.documents.dao.entity.Status;
import ru.template.example.documents.dao.repository.DocumentsRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для {@link DocumentServiceImpl}
 */
class DocumentServiceImplTest2 {

    @Mock
    private DocumentsRepository documentsRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void update() {
        // Тест обновления документа
        DocumentDto documentDto = new DocumentDto();
        Document document = new Document();
        when(objectMapper.convertValue(documentDto, Document.class)).thenReturn(document);
        when(objectMapper.convertValue(document, DocumentDto.class)).thenReturn(documentDto);
        when(documentsRepository.save(document)).thenReturn(document);

        DocumentDto updatedDocument = documentService.update(documentDto);

        assertNotNull(updatedDocument);
        verify(documentsRepository).save(document);
    }

    @Test
    void delete() {
        // Тест удаления документа
        Long documentId = 1L;
        doNothing().when(documentsRepository).deleteById(documentId);

        assertDoesNotThrow(() -> documentService.delete(documentId));
        verify(documentsRepository).deleteById(documentId);
    }

    @Test
    void deleteAll() {
        // Тест удаления списка документов
        Set<Long> documentIds = Set.of(1L, 2L, 3L);
        doNothing().when(documentsRepository).deleteAllByIdIn(documentIds);

        assertDoesNotThrow(() -> documentService.deleteAll(documentIds));
        verify(documentsRepository).deleteAllByIdIn(documentIds);
    }

    @Test
    void findAll() {
        // Тест получения всех документов
        List<Document> documents = new ArrayList<>();
        when(documentsRepository.findAll()).thenReturn(documents);
        when(objectMapper.convertValue(any(), eq(DocumentDto.class))).thenReturn(new DocumentDto());

        List<DocumentDto> allDocuments = documentService.findAll();

        assertNotNull(allDocuments);
        verify(documentsRepository).findAll();
        verify(objectMapper, times(documents.size())).convertValue(any(), eq(DocumentDto.class));
    }

    @Test
    void get() {
        // Тест получения документа по идентификатору
        Long documentId = 1L;
        Document document = new Document();
        when(documentsRepository.findById(documentId)).thenReturn(Optional.of(document));
        when(objectMapper.convertValue(document, DocumentDto.class)).thenReturn(new DocumentDto());

        DocumentDto foundDocument = documentService.get(documentId);

        assertNotNull(foundDocument);
        verify(documentsRepository).findById(documentId);
        verify(objectMapper).convertValue(document, DocumentDto.class);
    }

    @Test
    void getNotFound() {
        // Тест получения документа по несуществующему идентификатору
        Long documentId = 1L;
        when(documentsRepository.findById(documentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> documentService.get(documentId));

        assertEquals("Document with id " + documentId + " not found", exception.getMessage());
        verify(documentsRepository).findById(documentId);
    }
}