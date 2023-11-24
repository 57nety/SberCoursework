package ru.template.example.documents.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.template.example.documents.controllers.dto.DocumentDto;
import ru.template.example.documents.controllers.dto.StatusDto;
import ru.template.example.documents.dao.entity.Status;
import ru.template.example.documents.service.DocumentService;

import java.util.Random;

/**
 * Класс, представляющий Kafka Consumer для обработки документов.
 */
@RequiredArgsConstructor
@Component
public class KafkaConsumer {

    /**
     * Название темы Kafka, из которой происходит чтение сообщений.
     */
    private static final String TOPIC_NAME = "documents";

    /**
     * Идентификатор группы Kafka Consumer.
     */
    private static final String GROUP_ID = "documents-group";

    /**
     * Сервис для работы с документами.
     */
    private final DocumentService documentService;

    /**
     * Метод, прослушивающий Kafka тему и обрабатывающий поступающие документы.
     *
     * @param documentDto DTO (Data Transfer Object) представление документа.
     */
    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void consume(@Payload DocumentDto documentDto) {
        // Генерация случайного решения по принятию или отклонению документа.
        boolean isAccepted = new Random().nextBoolean();

        // Установка статуса документа в зависимости от случайного решения.
        if (isAccepted) {
            documentDto.setStatus(StatusDto.fromStatus(Status.ACCEPTED));
        } else {
            documentDto.setStatus(StatusDto.fromStatus(Status.DECLINED));
        }

        // Обновление статуса документа в сервисе документов.
        documentService.update(documentDto);
    }
}
