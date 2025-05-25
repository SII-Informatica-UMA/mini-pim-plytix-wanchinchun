package com.uma.wanchinchun.mappers;

public interface IMapper<Entity, EntityDTO> {
    EntityDTO mapToDTO(Entity entity);
    Entity mapToEntity(EntityDTO dto);
    void updateEntity(Entity toUpdate, Entity source);
    void updateEntityFromDTO(Entity toUpdate, EntityDTO source);
}
