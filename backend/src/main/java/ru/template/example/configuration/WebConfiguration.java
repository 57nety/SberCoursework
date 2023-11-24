package ru.template.example.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Класс, который представляет конфигурацию для обработки CORS
 */
@Configuration
@ConditionalOnProperty(value = "cors.allow", havingValue = "true", matchIfMissing = false)
public class WebConfiguration implements WebMvcConfigurer {
    /**
     * Метод разрешает: запросы от любого источника, любые HTTP-методы, любые заголовки, и отправку куки в запросах
     *
     * @param registry объект для настройки CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedHeaders("*").allowCredentials(true);
    }
}
