package ru.nat.service;

import java.util.List;

public interface Service<Entity> {
    void createEntity(Entity entity);
    void updateEntity(Entity entity);
    void deleteEntity(Long id);
    List<Entity> getAllEntities();
    Entity getEntityById(Long id);
}
