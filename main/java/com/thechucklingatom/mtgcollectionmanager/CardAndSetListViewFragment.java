package com.thechucklingatom.mtgcollectionmanager;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        //dummy data for test
        String[] testData = {
                "Test item1",
                "Test item2",
                "Test item3",
                "Test item4",
                "Test item5",
                "Test item6",
                "Test item7",
                "Test item8",
                "Test item9",
                "Test item10",
                "Test item11",
        };

        List<String> testList = new ArrayList<>(Arrays.asList(testData));

        //array adapter
        cardAndSets = new ArrayAdapter<>(getActivity(), R.layout.card_and_set_list_view,
                R.id.textview_cardset, testList);

        View rootView = inflater.inflate(R.layout.card_and_set_list_view, container, false);

        //get the listview and set the adapter
        ListView listView = (ListView) rootView.findViewById(R.id.listview_cardset);
        listView.setAdapter(cardAndSets);

        return rootView;
    }

}
