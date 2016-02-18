package com.thechucklingatom.mtgcollectionmanager;

//import android.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by thechucklingatom on 12/30/2015.
 * @author thechucklingatom
 */
public class CardAndSetListViewFragment extends Fragment {

    public interface Communicator{
        public void onItemClicked(int position, String location);
    }

    private Communicator mCallback;

    public ArrayAdapter cardAndSets;

    public CardAndSetListViewFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        setHasOptionsMenu(true);
        DataTask dataTask = new DataTask(this.getContext());

        dataTask.execute();

        //array adapter
        cardAndSets = new ArrayAdapter<>(getActivity(), R.layout.card_and_set_list_view,
                R.id.textview_cardset, dataTask.getSetList());

        dataTask.adapter = cardAndSets;

        View rootView = inflater.inflate(R.layout.card_and_set_list_view, container, false);

        //get the listview and set the adapter
        ListView listView = (ListView) rootView.findViewById(R.id.listview_cardset);
        listView.setAdapter(cardAndSets);
        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_collection_manager, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            cardAndSets.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Activity activity;

        if(context instanceof Activity){
            activity = (Activity) context;
            try {
                mCallback = (Communicator) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }
    }

}


