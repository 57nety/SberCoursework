package ru.template.example.documents.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.template.example.documents.controllers.dto.DocumentDto;

/**
 * Класс, отвечающий за отправку сообщений в Apache Kafka для обработки документов.
 */
@RequiredArgsConstructor
@Component
public class KafkaSender {

    /**
     * Шаблон Kafka для отправки сообщений.
     */
    private final KafkaTemplate<String, DocumentDto> kafkaTemplate;

    /**
     * Имя темы Kafka, на которую отправляются документы.
     */
    private static final String TOPIC_NAME = "documents";

    /**
     * Метод для отправки сообщения с данными документа в Kafka.
     *
     * @param documentDto DTO (Data Transfer Object) представление документа.
     */
    public void sendMessage(DocumentDto documentDto) {
        kafkaTemplate.send(TOPIC_NAME, documentDto);
    }
}