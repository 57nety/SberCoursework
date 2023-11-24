package ru.template.example.documents.controllers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.template.example.documents.controllers.dto.DocumentDto;
import ru.template.example.documents.controllers.dto.IdDto;
import ru.template.example.documents.controllers.dto.IdsDto;
import ru.template.example.documents.controllers.dto.StatusDto;
import ru.template.example.documents.dao.entity.Status;
import ru.template.example.documents.service.DocumentService;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Класс, тестирующий методы класса DocumentController
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {
    /**
     * Макет documentService
     */
    @Mock
    private DocumentService documentService;

    /**
     * Тестируемый класс, в который будет внедрён макет
     */
    @InjectMocks
    private DocumentController documentController;

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
     * Шаблон id документа со значением 1L
     */
    private static final IdDto ID_ONE_DTO = new IdDto(1L);

    /**
     * Шаблон id документа со значением 2L
     */
    private static final IdDto ID_TWO_DTO = new IdDto(2L);

    /**
     * Шаблон id документа со значением 3L
     */
    private static final IdDto ID_THREE_DTO = new IdDto(3L);

    /**
     * Количество раз, сколько должен быть выполнен метод
     */
    private static final int ONE_TIME = 1;

    @Test
    @DisplayName("Successful save documentDto")
    public void testSuccessfulSaveDocumentDto() {
        DocumentDto mockDocumentDto = DOCUMENT_DTO;
        when(documentService.save(any(DocumentDto.class))).thenReturn(mockDocumentDto);
        DocumentDto savedDto = documentController.save(mockDocumentDto);
        verify(documentService, times(ONE_TIME)).save(any(DocumentDto.class));

        Assertions.assertEquals(mockDocumentDto.getStatus(), savedDto.getStatus());
    }

    @Test
    @DisplayName("Successful find all documents")
    public void testSuccessfulFindAllDocuments() {
        List<DocumentDto> mockDocumentsDto = List.of(DOCUMENT_DTO, DOCUMENT_DTO, DOCUMENT_DTO);
        when(documentService.findAll()).thenReturn(mockDocumentsDto);
        List<DocumentDto> getDocumentsDto = documentController.get();
        verify(documentService, times(ONE_TIME)).findAll();

        Assertions.assertEquals(mockDocumentsDto, getDocumentsDto);
    }

    @Test
    @DisplayName("Successful send document")
    public void testSuccessfulSendDocument() {
        IdDto mockIdDto = ID_ONE_DTO;
        DocumentDto mockDocumentDto = DOCUMENT_DTO;
        mockDocumentDto.setStatus(StatusDto.fromStatus(Status.IN_PROCESS));
        when(documentService.get(mockIdDto.getId())).thenReturn(mockDocumentDto);
        when(documentService.update(mockDocumentDto)).thenReturn(mockDocumentDto);
        DocumentDto sendDocumentDto = documentController.send(mockIdDto);
        verify(documentService, times(ONE_TIME)).get(mockIdDto.getId());
        verify(documentService, times(ONE_TIME)).update(mockDocumentDto);

        Assertions.assertEquals(mockDocumentDto, sendDocumentDto);
    }

    @Test
    @DisplayName("Successful delete document by id")
    public void testSuccessfulDeleteDocumentById() {
        Long mockIdDocumentDto = ID_ONE_DTO.getId();
        documentController.delete(mockIdDocumentDto);

        verify(documentService, times(ONE_TIME)).delete(mockIdDocumentDto);
    }

    @Test
    @DisplayName("Successful delete all document by ids")
    public void testSuccessfulDeleteAllDocumentByIds() {
        Set<Long> mockIdsDtoIds = Set.of(ID_ONE_DTO.getId(), ID_TWO_DTO.getId(), ID_THREE_DTO.getId());
        IdsDto idsDto = new IdsDto(mockIdsDtoIds);
        documentController.deleteAll(idsDto);

        verify(documentService, times(ONE_TIME)).deleteAll(mockIdsDtoIds);
    }
}
