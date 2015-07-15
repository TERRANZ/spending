package ru.terra.spending.android.fragments;


import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.terra.spending.android.R;
import ru.terra.spending.android.activity.components.TransactionsListCursorAdapter;
import ru.terra.spending.android.core.db.entity.TransactionDBEntity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends android.support.v4.app.ListFragment {
    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_main_list, container, false);
        Cursor ctr = getActivity().getContentResolver().query(TransactionDBEntity.CONTENT_URI, null, null, null, TransactionDBEntity.DATE + " desc");
        setListAdapter(new TransactionsListCursorAdapter(getActivity(), ctr));
//        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                selectedItemId = ((TransactionsListCursorAdapter.ViewHolder) view.getTag()).id;
//                startActionMode(mActionModeCallback);
//                view.setSelected(true);
//                return true;
//            }
//        });
        return v;
    }
}
