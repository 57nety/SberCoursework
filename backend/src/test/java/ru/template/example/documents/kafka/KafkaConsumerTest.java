package ru.template.example.documents.kafka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.template.example.documents.controllers.dto.DocumentDto;
import ru.template.example.documents.service.DocumentService;

import java.util.Random;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Test
    void consume_ShouldProcessDocumentAndCallDocumentService() {
        // Arrange
        DocumentDto documentDto = new DocumentDto(/* ваш конструктор DocumentDto */);

        // Act
        kafkaConsumer.consume(documentDto);

        // Assert
        verify(documentService, times(1)).update(documentDto);
    }
}