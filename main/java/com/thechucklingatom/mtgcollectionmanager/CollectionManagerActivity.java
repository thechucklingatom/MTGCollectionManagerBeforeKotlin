package com.thechucklingatom.mtgcollectionmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;

/**
 * @link #initialize(Context)
 */

public class CollectionManagerActivity
		extends AppCompatActivity
		implements CardAndSetListViewFragment.Communicator {
	private boolean set = true;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection_manager);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		//add listview fragment
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.container,
							new CardAndSetListViewFragment())
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
	}

	@Override
	public ArrayAdapter<String> getAdapter() {
		return adapter;
	}

	@Override
	public void onBackPressed() {
	}
}
