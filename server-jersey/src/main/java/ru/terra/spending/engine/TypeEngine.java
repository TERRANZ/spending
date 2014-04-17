package ru.terra.spending.engine;

import ru.terra.server.engine.AbstractEngine;
import ru.terra.spending.db.controller.TrTypeJpaController;
import ru.terra.spending.db.entity.TrType;
import ru.terra.spending.dto.TypeDTO;

/**
 * Date: 16.04.14
 * Time: 15:42
 */
public class TypeEngine extends AbstractEngine<TrType, TypeDTO> {
    public TypeEngine() {
        super(new TrTypeJpaController());
    }

    @Override
    public TypeDTO getDto(Integer id) {
        return entityToDto(getBean(id));
    }

    @Override
    public void dtoToEntity(TypeDTO dto, TrType trType) {
        trType.setName(dto.name);
    }

    @Override
    public TypeDTO entityToDto(TrType trType) {
        return new TypeDTO(trType);
    }
}
