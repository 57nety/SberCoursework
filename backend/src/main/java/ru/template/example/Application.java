package ru.template.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс приложения
 */
@SpringBootApplication(scanBasePackages = {
        "ru.template.example.*"
})
public class Application {

    /**
     * Точка входа для выполнения приложения
     *
     * @param args массив переданных аргументов
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
