package ru.template.example.documents.kafka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import ru.template.example.documents.controllers.dto.DocumentDto;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Тестовый класс для проверки функциональности {@link KafkaSender}.
 * Использует библиотеку Mockito для создания макета {@link KafkaTemplate}.
 */
@ExtendWith(MockitoExtension.class)
class KafkaSenderTest {

    /**
     * Макет шаблона Kafka для отправки сообщений.
     */
    @Mock
    private KafkaTemplate<String, DocumentDto> kafkaTemplate;

    /**
     * Инжектит макет {@code kafkaTemplate} в тестируемый объект {@code kafkaSender}.
     */
    @InjectMocks
    private KafkaSender kafkaSender;

    /**
     * Тест проверяет, что метод {@link KafkaSender#sendMessage(DocumentDto)} корректно отправляет сообщение в Kafka.
     */
    @Test
    void sendMessage_ShouldSendToKafkaTopic() {
        // Arrange
        DocumentDto documentDto = new DocumentDto(/* ваш конструктор DocumentDto */);

        // Act
        kafkaSender.sendMessage(documentDto);

        // Assert
        verify(kafkaTemplate, times(1)).send(eq("documents"), eq(documentDto));
    }
}