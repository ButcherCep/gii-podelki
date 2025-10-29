package ru.sbp.gazii.podelki.generics.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Создать универсальный репозиторий для работы с сущностями, имеющими ID
 */
public class GenericRepository<T extends Entity<I>, I> {

    private Map<I, T> storage = new ConcurrentHashMap<>();

    T save(T entity) {
        if (entity != null) {
            storage.put(entity.getId(), entity);
            return entity;
        }
        return null;
    }

    Optional<T> findById(I id) {
        return Optional.ofNullable(storage.get(id));
    }

    List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    void deleteById(I id) {
        storage.remove(id);
    }

    <S extends T> List<S> findBy(Specification<S> spec, Class<S> type) {
        return storage.values().stream()
                .filter(type::isInstance) // ← проверка типа
                .map(type::cast) // ← безопасное приведение
                .filter(spec::test)
                .collect(Collectors.toList());
    }

    public interface Specification<T> {
        boolean test(T entity);
    }
}


