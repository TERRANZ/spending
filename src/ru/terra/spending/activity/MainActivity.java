package ru.terra.spending.activity;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import ru.terra.spending.R;
import ru.terra.spending.activity.components.TransactionsListCursorAdapter;
import ru.terra.spending.core.db.entity.Transaction;
import ru.terra.spending.core.db.entity.TransactionDBEntity;
import ru.terra.spending.core.db.entity.TypeDBEntity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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
		if (prefs.getBoolean("first_start", true))
			firstStart();
		titles = new String[] { "Тратим", "Потратили", "Отчёт" };
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View main, list, report;
		main = inflater.inflate(R.layout.f_main, null);
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
		Cursor c = getContentResolver().query(TransactionDBEntity.CONTENT_URI, null, null, null, null);
		lvMainListTransactions.setAdapter(new TransactionsListCursorAdapter(this, c));
	}

	private void firstStart()
	{
		Editor e = prefs.edit();
		e.putBoolean("first_start", false);
		e.commit();
		ContentValues[] cvs = new ContentValues[4];
		ContentValues cv0 = new ContentValues();
		cv0.put(TypeDBEntity.NAME, "Обед");
		cvs[0] = cv0;
		ContentValues cv1 = new ContentValues();
		cv1.put(TypeDBEntity.NAME, "Бензин");
		cvs[1] = cv1;
		ContentValues cv2 = new ContentValues();
		cv2.put(TypeDBEntity.NAME, "Домой");
		cvs[2] = cv2;
		ContentValues cv4 = new ContentValues();
		cv4.put(TypeDBEntity.NAME, "Ништяки");
		cvs[3] = cv4;
		ContentResolver cr = getContentResolver();
		cr.bulkInsert(TypeDBEntity.CONTENT_URI, cvs);
	}

	public void spended(View v)
	{
		Transaction t = new Transaction();
	}
}
