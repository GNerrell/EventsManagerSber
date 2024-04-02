package ru.brovkin.eventsmanagersber.exception;

/**
 * Класс исключения для обработки несуществующих данных в базе данных
 */
public class DataLackException extends RuntimeException{
    public DataLackException(String message) {
        super(message);
    }
}
