package Lesson55.src.practice;

/**
 * Интерфейс с методом, принимающим один аргумент и возвращающим булево значение (аналог Predicate):
 */
@FunctionalInterface
interface Condition<T> {
    boolean test(T t);
}
