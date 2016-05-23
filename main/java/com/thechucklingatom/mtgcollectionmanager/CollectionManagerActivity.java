package com.thechucklingatom.mtgcollectionmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;

public class CollectionManagerActivity extends AppCompatActivity implements CardAndSetListViewFragment.Communicator,
                                                    DataTask.Observer{

    private boolean set = true;
    private DataTask dataTask;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataTask = new DataTask(this, new JsonReader(
                                        new InputStreamReader(getResources()
                                                .openRawResource(R.raw.all_sets_x))));

        dataTask.execute();

       /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        
        //add listview fragment
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CardAndSetListViewFragment())
                    .commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(int position) {
        if(set) {
            dataTask.createCardList(position);
            adapter.notifyDataSetChanged();
            set = false;
        }else{
            Intent intent = new Intent(this, CardView.class);
            intent.putExtra("card", dataTask.getCard(position));
            startActivity(intent);
        }
    }

    @Override
    public ArrayAdapter<String> getAdapter() {
        adapter = new ArrayAdapter<>(this, R.layout.card_and_set_list_view,
                R.id.textview_cardset, dataTask.getSetList());
        return adapter;
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if(set){
            Toast.makeText(this, "No further back", Toast.LENGTH_SHORT).show();
        }else{
            set = true;
            dataTask.createSetList();
            adapter.notifyDataSetChanged();
        }
        //super.onBackPressed();
    }

}
