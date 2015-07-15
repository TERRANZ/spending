package ru.terra.spending.android.provider;

import android.app.Activity;
import android.util.Log;
import com.google.gson.Gson;
import ru.terra.spending.android.core.constants.Constants;
import ru.terra.spending.android.core.constants.URLConstants;
import ru.terra.spending.android.core.network.JsonAbstractProvider;
import ru.terra.spending.android.core.network.dto.LoginDTO;
import ru.terra.spending.android.util.SettingsUtil;

public class LoginProvider extends JsonAbstractProvider {
    private final static String TAG = "LoginProvider";

    public LoginProvider(Activity c) {
        super(c);
    }

    public boolean login(String user, String pass) {
        String json = httpReqHelper.runSimpleJsonRequest(URLConstants.Login.LOGIN + URLConstants.Login.LOGIN_DO_LOGIN_JSON + "?user=" + user + "&pass=" + pass);
        Log.i("LoginProvider", json);
        LoginDTO dto = new Gson().fromJson(json, LoginDTO.class);
        if (dto.logged) {
            SettingsUtil.saveSetting(cntxActivity, Constants.CONFIG_SESSION, dto.session);
            return true;
        } else
            return false;
    }
}
