package ru.terra.spending.android.core.network.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 19.05.14
 * Time: 13:27
 */
public class TransactionListDTO extends CommonDTO {
    public List<TransactionDTO> data = new ArrayList<TransactionDTO>();
    public Integer size;

    public List<TransactionDTO> getData() {
        return data;
    }

    public void setData(List<TransactionDTO> data) {
        this.data = data;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
