package com.thechucklingatom.mtgcollectionmanager;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by thechucklingatom on 12/30/2015.
 * @author thechucklingatom
 */
public class CardAndSetListViewFragment extends Fragment {

    private ArrayAdapter cardAndSets;

    public CardAndSetListViewFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.card_and_set_list_view, container, false);
        return rootView;
    }

}
