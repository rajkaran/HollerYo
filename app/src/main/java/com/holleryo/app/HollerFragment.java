package com.holleryo.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * HollerFragment
 * List all the hollers
 */

public class HollerFragment extends ListFragment {

    private RelatedHollerAdapter relatedHollerAdapter;


    public HollerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        relatedHollerAdapter = new RelatedHollerAdapter(getActivity());

        setListAdapter(relatedHollerAdapter);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_holler, container, false);

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
