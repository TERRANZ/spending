package ru.terra.spending.android.fragments;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.terra.spending.android.R;
import ru.terra.spending.android.activity.components.TypesSpinnerAdapter;
import ru.terra.spending.android.core.ProjectModule;
import ru.terra.spending.android.core.db.entity.TransactionDBEntity;
import ru.terra.spending.android.core.db.entity.TypeDBEntity;
import ru.terra.spending.android.core.tasks.RecvTypesAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private SharedPreferences prefs;
    private ViewPager vp;
    private List<View> pages = new ArrayList<View>();
    private String[] titles = null;
    private ListView lvMainListTransactions;
    private EditText etDate, etMoney;
    private Spinner types;
    private Long stype = 1L;
    private ProjectModule pm;
    private DateFormat tf;
    private Long selectedItemId;
    private TextView tvDay;
    private TextView tvMonth;

    private class TimeSetListenerImpl implements TimePickerDialog.OnTimeSetListener {

        private Dialog dialog;
        private TextView textView;
        private TimePickerDialog.OnTimeSetListener onTimeSetListener;

        private TimeSetListenerImpl(TextView textView) {
            this.textView = textView;
        }

        public void setDialog(Dialog dialog, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
            this.onTimeSetListener = onTimeSetListener;
            this.dialog = dialog;
        }

        public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date((Long) textView.getTag()));
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, minutes);
            String time = tf.format(calendar.getTime());
            textView.setText(time);
            textView.setTag(calendar.getTime().getTime());
            if (onTimeSetListener != null) {
                onTimeSetListener.onTimeSet(timePicker, hours, minutes);
            }
            dialog.dismiss();
        }
    }

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main = inflater.inflate(R.layout.f_main, container, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        pm = new ProjectModule(getActivity());
        if (prefs.getBoolean("first_start", true))
            firstStart();

        etDate = (EditText) main.findViewById(R.id.et_date);
        Date d = new Date();
        etDate.setTag(d.getTime());
        etDate.setText(pm.provideTimeFormat().format(d));
        types = (Spinner) main.findViewById(R.id.sp_spending_type);
        etMoney = (EditText) main.findViewById(R.id.et_spending_money);
        Cursor ctype = getActivity().getContentResolver().query(TypeDBEntity.CONTENT_URI, null, null, null, null);
        types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                stype = (Long) arg1.getTag();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        types.setAdapter(new TypesSpinnerAdapter(getActivity(), ctype));
        tf = pm.provideTimeFormat();

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hour = 0;
                int minute = 0;
                try {
                    Object o = tf.parse(etDate.getText().toString());
                    if (o != null) {
                        c.setTime((Date) o);
                        hour = c.get(Calendar.HOUR_OF_DAY);
                        minute = c.get(Calendar.MINUTE);
                    }
                } catch (ParseException e) {
                    Log.d("Edit date", "cant parse date = " + c, e);
                }
                final Dialog dialog;
                TimeSetListenerImpl listener = new TimeSetListenerImpl(etDate);

                dialog = new TimePickerDialog(getActivity(), listener, hour, minute, true);
                listener.setDialog(dialog, null);

                dialog.show();
            }
        });

        return main;
    }

    private void firstStart() {
        SharedPreferences.Editor e = prefs.edit();
        e.putBoolean("first_start", false);
        e.commit();
        new RecvTypesAsyncTask(getActivity()).execute();
        Toast.makeText(getActivity(), "Синхронизируем типы трат", Toast.LENGTH_SHORT).show();
    }

    public EditText getEtMoney() {
        return etMoney;
    }

    public EditText getEtDate() {
        return etDate;
    }

    public Long getStype() {
        return stype;
    }

    public void spended(View v) {
        ContentValues cv = new ContentValues();
        cv.put(TransactionDBEntity.MONEY, Double.parseDouble(etMoney.getText().toString()));
        cv.put(TransactionDBEntity.DATE, (Long) etDate.getTag());
        cv.put(TransactionDBEntity.TYPE, stype);
        getActivity().getContentResolver().insert(TransactionDBEntity.CONTENT_URI, cv);
        etMoney.getText().clear();
//        updateReport();
    }
}
