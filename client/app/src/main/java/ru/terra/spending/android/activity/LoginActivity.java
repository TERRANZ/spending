package ru.terra.spending.android.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import ru.terra.spending.android.MainActivity;
import ru.terra.spending.android.R;
import ru.terra.spending.android.core.network.WorkIsDoneListener;
import ru.terra.spending.android.core.tasks.LoginAsyncTask;

public class LoginActivity extends RoboActionBarActivity {
    @InjectView(R.id.edtLogin)
    private MaterialEditText edtLogin;
    @InjectView(R.id.edtPass)
    private MaterialEditText edtPass;
    @InjectView(R.id.progressBar)
    private View progressBar;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_login);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        edtLogin.setText(sharedPreferences.getString("login", ""));
        edtPass.setText(sharedPreferences.getString("pass", ""));
    }

    private void hide() {
        edtLogin.setVisibility(View.INVISIBLE);
        edtPass.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        findViewById(R.id.btnLogin).setVisibility(View.INVISIBLE);
    }

    private void show() {
        edtLogin.setVisibility(View.VISIBLE);
        edtPass.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    public void login(View view) {
//
        final String login = edtLogin.getText().toString();
        final String pass = edtPass.getText().toString();
        new LoginAsyncTask(this, new WorkIsDoneListener() {

            @Override
            public void workIsDone(int action, String... val) {
                if (Boolean.parseBoolean(val[0])) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", login);
                    editor.putString("pass", pass);
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle("Ошибка");
                    alert.setMessage("Аутентификация завершилась неудачно. Проверьте, пожалуйста, имя учетной записи и пароль и повторите ввод.");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
            }
        }).execute(login, pass);
    }
}
