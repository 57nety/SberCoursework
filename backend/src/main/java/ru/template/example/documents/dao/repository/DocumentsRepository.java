package ru.template.example.documents.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.template.example.documents.dao.entity.Document;

import java.util.Set;

/**
 * Репозиторий для взаимодействия с БД
 */
public interface DocumentsRepository extends JpaRepository<Document, Long> {
    /**
     * Удалить все документы, у которых id соответствует переданным
     *
     * @param ids документов на удаление
     */
    void deleteAllByIdIn(Set<Long> ids);
}
