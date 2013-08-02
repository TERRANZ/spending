package ru.terra.spending.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import ru.terra.spending.R;
import ru.terra.spending.core.network.WorkIsDoneListener;
import ru.terra.spending.core.tasks.LoginAsyncTask;

public class LoginActivity extends RoboActivity {
    @InjectView(R.id.editLogin)
    private EditText login;
    @InjectView(R.id.editPass)
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_login);
    }

    public void loginOk(View v) {
        new LoginAsyncTask(this, new WorkIsDoneListener() {

            @Override
            public void workIsDone(int action, String... val) {
                if (Boolean.parseBoolean(val[0])) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle("Ошибка");
                    alert.setMessage("Аутентификация завершилась неудачно. Проверьте, пожалуйста, имя учетной записи и пароль и повторите ввод.");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
            }
        }).execute(login.getText().toString(), password.getText().toString());
    }
}
