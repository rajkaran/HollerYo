package com.holleryo.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyHollerFragment extends ListFragment {

    private CurrentUsersHollerAdapter currentUsersHollerAdapter;


    public MyHollerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentUsersHollerAdapter = new CurrentUsersHollerAdapter(getActivity());
        setListAdapter(currentUsersHollerAdapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Holler h = (Holler) l.getItemAtPosition(position);

        Intent hollerIntent = new Intent(v.getContext(), HollerViewActivity.class);
        hollerIntent.putExtra("objectId", h.getObjectId());
        hollerIntent.putExtra("facebookId", h.getString("facebookId"));
        startActivity(hollerIntent);
    }


}
