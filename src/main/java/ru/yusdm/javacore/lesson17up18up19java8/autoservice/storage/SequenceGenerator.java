package ru.yusdm.javacore.lesson17up18up19java8.autoservice.storage;

public final class SequenceGenerator {
    private static long value = 0;

    private SequenceGenerator() {

    }

    public static long getNextValue() {
        return ++value;
    }
}
