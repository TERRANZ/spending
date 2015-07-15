package ru.terra.spending.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ru.terra.spending.android.core.tasks.RecvTypesAsyncTask;
import ru.terra.spending.android.core.tasks.SyncTransactionsAsyncTask;
import ru.terra.spending.android.fragments.ListFragment;
import ru.terra.spending.android.fragments.MainFragment;


public class MainActivity extends ActionBarActivity {
    private Drawer drawer;
    private FrameLayout flMain;
    public static String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        flMain = (FrameLayout) findViewById(R.id.flMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .withTranslucentStatusBarShadow(true)
                .withCloseOnClick(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Потратить").withIdentifier(1),
                        new PrimaryDrawerItem().withName("Траты").withIdentifier(2),
                        new PrimaryDrawerItem().withName("Отчёты").withIdentifier(3),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Обновить").withIdentifier(4),
                        new PrimaryDrawerItem().withName("Выгрузить").withIdentifier(5),
                        new PrimaryDrawerItem().withName("Выход").withIdentifier(9)
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View view, float v) {

                    }
                }).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        if (l == 0) {
                            getSupportFragmentManager().beginTransaction().add(R.id.flMain, new MainFragment(), "main").commit();
                        } else if (l == 1) {
                            getSupportFragmentManager().beginTransaction().add(R.id.flMain, new ListFragment(), "list").commit();
                        } else if (l == 2) {
//                            getSupportFragmentManager().beginTransaction().replace(R.id.flMain, new MainFragment()).commit();
                        } else if (l == 3) {
//                            getSupportFragmentManager().beginTransaction().replace(R.id.flMain, new MainFragment()).commit();
                        } else if (l == 4) {
                            new RecvTypesAsyncTask(MainActivity.this).execute();
                            Toast.makeText(MainActivity.this, "Синхронизируем типы трат", Toast.LENGTH_SHORT).show();
                        } else if (l == 5) {
                            new SyncTransactionsAsyncTask(MainActivity.this).execute();
                            Toast.makeText(MainActivity.this, "Синхронизируем траты", Toast.LENGTH_SHORT).show();
                        }
                        drawer.closeDrawer();
                        return true;
                    }
                })
                .build();
        getSupportFragmentManager().beginTransaction().add(R.id.flMain, new MainFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    public void spended(View v) {
        ((MainFragment) getSupportFragmentManager().findFragmentById(R.id.flMain)).spended(v);
    }
}
