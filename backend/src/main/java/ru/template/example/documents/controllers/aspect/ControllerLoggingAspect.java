package ru.template.example.documents.controllers.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Класс, реализующий логирование вызванных контроллеров
 */
@Aspect
@Component
public class ControllerLoggingAspect {
    /**
     * Логгер, который выводит информацию
     */
    private final Logger logger = LoggerFactory.getLogger(ControllerLoggingAspect.class);

    /**
     * Метод, который вызывается перед работой каждого контроллера
     *
     * @param joinPoint точка выполнения программы
     */
    @Before("execution(* ru.template.example.documents.controllers.*.*(..))")
    public void logControllerMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();

        logger.info("Class: \"{}\" Method: \"{}\" Arguments: \"{}\"", className, methodName, args);
    }
}