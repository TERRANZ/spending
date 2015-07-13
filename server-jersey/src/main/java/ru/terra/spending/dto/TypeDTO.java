package ru.terra.spending.dto;

import ru.terra.server.dto.CommonDTO;
import ru.terra.spending.db.entity.TrType;

public class TypeDTO extends CommonDTO {
    public Integer id;
    public String name;

    public TypeDTO(TrType tt) {
        this.id = tt.getId();
        this.name = tt.getName();
    }
}
