package ru.terra.spending.dto;

import ru.terra.server.dto.CommonDTO;
import ru.terra.server.dto.UserDTO;
import ru.terra.spending.db.entity.Transaction;

public class TransactionDTO extends CommonDTO {
    public Integer id;
    public Long date;
    public Double value;
    public Integer type;
    public UserDTO user;

    public TransactionDTO(Transaction t) {
        id = t.getId();
        date = t.getTrDate().getTime();
        value = t.getValue();
        type = t.getTypeId().getId();
        user = new UserDTO(t.getUser());
    }

}
