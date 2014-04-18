package ru.terra.spending.core.network.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 17.04.14
 * Time: 16:20
 */
public class ListTypeDTO extends CommonDTO {
    public List<TypeDTO> data = new ArrayList<TypeDTO>();
    private Integer size;

    public Integer getSize() {
        size = data.size();
        return size;
    }

    public void setData(List<TypeDTO> d) {
        this.data = d;
    }
}