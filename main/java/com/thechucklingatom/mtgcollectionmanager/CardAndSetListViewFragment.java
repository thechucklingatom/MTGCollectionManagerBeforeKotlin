package com.thechucklingatom.mtgcollectionmanager;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

/**
 * Created by thechucklingatom on 12/30/2015.
 *
 * @author thechucklingatom
 */
public class CardAndSetListViewFragment extends Fragment {

	public interface Communicator {
		void onItemClicked(int position);

		ArrayAdapter<String> getAdapter();
	}

	private Communicator mCallback;

	public ArrayAdapter cardAndSets;

	public CardAndSetListViewFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		setHasOptionsMenu(true);

		//array adapter
		cardAndSets = mCallback.getAdapter();

		View rootView = inflater.inflate(R.layout.card_and_set_list_view, container, false);

		//get the listview and set the adapter
		ListView listView = (ListView) rootView.findViewById(R.id.listview_cardset);
		listView.setAdapter(cardAndSets);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.i("onItemClick: ", "Item clicked " + position);
				mCallback.onItemClicked(position);
			}
		});
		return rootView;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_collection_manager, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getActivity()
				.getSystemService(Context.SEARCH_SERVICE);

		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(
						getActivity()
								.getComponentName()));
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
	public void onAttach(Context context) {
		super.onAttach(context);

		Activity activity;

		if (context instanceof Activity) {
			activity = (Activity) context;
			try {
				mCallback = (Communicator) activity;
			} catch (ClassCastException e) {
				throw new ClassCastException(activity.toString() +
						" must implement OnHeadlineSelectedListener");
			}
		}
	}

}


