package ru.sbp.gazii.podelki.generics.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
//TODO

/**
 * Реализовать шину событий с типобезопасной подпиской
 */
public class EventBus {

    private final Map<Class<?>, HandlerGroup<?>> handlers = new ConcurrentHashMap<>();

    private static class HandlerGroup<T> {
        List<Consumer<T>> consumers = new ArrayList<>();
        List<Function<T, ?>> functions = new ArrayList<>();
    }

    <T> void subscribe(Class<T> eventType, Consumer<T> handler) {
        getHandlerGroup(eventType).consumers.add(handler);
    }

    private <T> HandlerGroup getHandlerGroup(Class<T> eventType) {
        return handlers.computeIfAbsent(eventType, k -> new HandlerGroup<>());
    }

    private <T> HandlerGroup<T> getHandlerGroupSafe(Class<?> eventType) {
        return (HandlerGroup<T>) handlers.get(eventType);
    }

    <T> void publish(T event) {
        HandlerGroup<T> handlerGroup = getHandlerGroupSafe(event.getClass());
        if (handlerGroup != null) {
            // Обрабатываем consumers
            for (Consumer<T> handler : handlerGroup.consumers) {
                handler.accept(event);
            }
            // Обрабатываем functions (игнорируем результат)
            for (Function<T, ?> handler : handlerGroup.functions) {
                handler.apply(event);
            }
        }
    }

    <T> void unsubscribe(Class<T> eventType, Consumer<T> handler) {
        HandlerGroup<T> handlerGroup = getHandlerGroup(eventType);
        List<Consumer<T>> eventHandler = handlerGroup.consumers;
        if (eventHandler != null) {
            eventHandler.remove(handler);
        } else {
            if (eventHandler.isEmpty()) {
                handlers.remove(eventType);
            }
        }
    }

    <T, R> void subscribeWithReply(Class<T> eventType, Function<T, R> handler) {
        getHandlerGroup(eventType).consumers.add(handler);
    }
}