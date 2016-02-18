package com.thechucklingatom.mtgcollectionmanager;

//import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.InputStreamReader;
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
        Gson gson;
        List<Set> sets = new ArrayList<>();

        try{
            DataTest dataTest = new DataTest(new JsonReader(new InputStreamReader(getResources()
                    .openRawResource(R.raw.all_sets_x), "UTF-8")));

            //array adapter
            cardAndSets = new ArrayAdapter<>(getActivity(), R.layout.card_and_set_list_view,
                    R.id.textview_cardset, dataTest.getSetList());

            View rootView = inflater.inflate(R.layout.card_and_set_list_view, container, false);

            //get the listview and set the adapter
            ListView listView = (ListView) rootView.findViewById(R.id.listview_cardset);
            listView.setAdapter(cardAndSets);
            return rootView;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
