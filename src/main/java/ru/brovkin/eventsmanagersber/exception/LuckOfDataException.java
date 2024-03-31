package ru.brovkin.eventsmanagersber.exception;

/**
 * Класс исключения для обработки несуществующих данных в базе данных
 */
public class LuckOfDataException extends RuntimeException{
    public LuckOfDataException(String message) {
        super(message);
    }
}
