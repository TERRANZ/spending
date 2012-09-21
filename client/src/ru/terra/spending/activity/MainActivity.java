package ru.terra.spending.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.activity.RoboActivity;
import ru.terra.spending.R;
import ru.terra.spending.activity.components.TransactionsListCursorAdapter;
import ru.terra.spending.activity.components.TypesSpinnerAdapter;
import ru.terra.spending.core.DateHelper;
import ru.terra.spending.core.ProjectModule;
import ru.terra.spending.core.db.entity.TransactionDBEntity;
import ru.terra.spending.core.db.entity.TypeDBEntity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.viewpagerindicator.TitlePageIndicator;

public class MainActivity extends RoboActivity
{
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

	private class TimeSetListenerImpl implements TimePickerDialog.OnTimeSetListener
	{

		private Dialog dialog;
		private TextView textView;
		private TimePickerDialog.OnTimeSetListener onTimeSetListener;

		private TimeSetListenerImpl(TextView textView)
		{
			this.textView = textView;
		}

		public void setDialog(Dialog dialog, TimePickerDialog.OnTimeSetListener onTimeSetListener)
		{
			this.onTimeSetListener = onTimeSetListener;
			this.dialog = dialog;
		}

		public void onTimeSet(TimePicker timePicker, int hours, int minutes)
		{
			Date currDate = new Date((Long) textView.getTag());
			currDate = DateHelper.getDate(currDate.getYear(), currDate.getMonth(), currDate.getDay(), hours, minutes);
			String time = tf.format(currDate);
			textView.setText(time);
			textView.setTag(currDate.getTime());
			if (onTimeSetListener != null)
			{
				onTimeSetListener.onTimeSet(timePicker, hours, minutes);
			}
			dialog.dismiss();
		}
	}

	private class MainAcvitiyAdapter extends PagerAdapter
	{
		@Override
		public int getCount()
		{
			return pages.size();
		}

		@Override
		public Object instantiateItem(View collection, int position)
		{
			View v = pages.get(position);
			((ViewPager) collection).addView(v, 0);
			return v;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == (View) arg1;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return titles[position];
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_main);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		pm = new ProjectModule(this);
		if (prefs.getBoolean("first_start", true))
			firstStart();
		titles = new String[] { "Тратим", "Потратили", "Отчёт" };
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View main, list, report;
		main = inflater.inflate(R.layout.f_main, null);
		etDate = (EditText) main.findViewById(R.id.et_date);
		Date d = new Date();
		etDate.setTag(d.getTime());
		etDate.setText(pm.provideTimeFormat().format(d));
		types = (Spinner) main.findViewById(R.id.sp_spending_type);
		etMoney = (EditText) main.findViewById(R.id.et_spending_money);
		list = inflater.inflate(R.layout.f_main_list, null);
		lvMainListTransactions = (ListView) list.findViewById(R.id.lv_main_list_transactions);
		report = inflater.inflate(R.layout.f_main_report, null);
		pages.add(main);
		pages.add(list);
		pages.add(report);
		vp = (ViewPager) findViewById(R.id.vp_main_activity);
		MainAcvitiyAdapter adapter = new MainAcvitiyAdapter();
		vp.setAdapter(adapter);
		TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.tpi_main_activity);
		titleIndicator.setViewPager(vp);
		Cursor ctr = getContentResolver().query(TransactionDBEntity.CONTENT_URI, null, null, null, TransactionDBEntity.DATE + " desc");
		lvMainListTransactions.setAdapter(new TransactionsListCursorAdapter(this, ctr));
		Cursor ctype = getContentResolver().query(TypeDBEntity.CONTENT_URI, null, null, null, null);
		types.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				stype = (Long) arg1.getTag();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		types.setAdapter(new TypesSpinnerAdapter(this, ctype));
		tf = pm.provideTimeFormat();

		etDate.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Calendar c = Calendar.getInstance();
				int hour = 0;
				int minute = 0;
				try
				{
					Object o = tf.parse(etDate.getText().toString());
					if (o != null)
					{
						c.setTime((Date) o);
						hour = c.get(Calendar.HOUR_OF_DAY);
						minute = c.get(Calendar.MINUTE);
					}
				} catch (ParseException e)
				{
					Log.d(getClass().getSimpleName(), "cant parse date = " + c, e);
				}
				final Dialog dialog;
				TimeSetListenerImpl listener = new TimeSetListenerImpl(etDate);

				dialog = new TimePickerDialog(MainActivity.this, listener, hour, minute, true);
				listener.setDialog(dialog, null);

				dialog.show();
			}
		});
	}

	private void firstStart()
	{
		Editor e = prefs.edit();
		e.putBoolean("first_start", false);
		e.commit();
		//TODO: replace by server-side configuration
		ContentValues[] cvs = new ContentValues[5];
		ContentValues cv0 = new ContentValues();
		cv0.put(TypeDBEntity.NAME, "Обед");
		cvs[0] = cv0;
		ContentValues cv1 = new ContentValues();
		cv1.put(TypeDBEntity.NAME, "Бензин");
		cvs[1] = cv1;
		ContentValues cv2 = new ContentValues();
		cv2.put(TypeDBEntity.NAME, "Домой");
		cvs[2] = cv2;
		ContentValues cv3 = new ContentValues();
		cv3.put(TypeDBEntity.NAME, "Ништяки");
		cvs[3] = cv3;
		ContentValues cv4 = new ContentValues();
		cv4.put(TypeDBEntity.NAME, "Проезд");
		cvs[4] = cv4;
		ContentResolver cr = getContentResolver();
		cr.bulkInsert(TypeDBEntity.CONTENT_URI, cvs);
	}

	public void spended(View v)
	{
		ContentValues cv = new ContentValues();
		cv.put(TransactionDBEntity.MONEY, Double.parseDouble(etMoney.getText().toString()));
		cv.put(TransactionDBEntity.DATE, (Long) etDate.getTag());
		cv.put(TransactionDBEntity.TYPE, stype);
		getContentResolver().insert(TransactionDBEntity.CONTENT_URI, cv);
		etMoney.getText().clear();		
	}
}
