package ru.terra.spending.android.core.network.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 17.04.14
 * Time: 15:51
 */
public class CommonDTO implements Serializable {
    public String errorMessage = "";
    public Integer errorCode = 0;
    public String status = "";
    public Long timestamp = new Date().getTime();
    public Integer id = -1;

}