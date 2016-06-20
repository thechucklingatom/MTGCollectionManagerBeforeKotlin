package com.thechucklingatom.mtgcollectionmanager;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Data Task is the task that fetches all the magic card data from MTGJson's
 * JSON file. Currently has the functionality to get the set names and the cards
 * in a specific set. Runs off the UI thread since the JSON file is so large it takes
 * about 10 seconds to get the data. It has an interface to notify the calling activity
 * once the data is done filling. Looking at ways to improve performance.
 * <p/>
 * Created by thechucklingatom on 2/17/2016.
 *
 * @author thechucklingatom
 */

public class DataTask extends AsyncTask<Integer, Integer, List<String>> {
	private List<String> setNameList;
	private List<Set> sets;
	private List<Card> cards;
	private JsonReader jsonReader;
	private Observer observer;

	/**
	 * Callback interface. The calling activity extends this so that this object has a way
	 * to communicate with the calling activity.
	 */
	public interface Observer {
		void notifyDataSetChanged();
	}

	/**
	 * Constructor. Data Task is the task that fetches all the magic card data from MTGJson's
	 * JSON file.
	 *
	 * @param mContext   Context to notify when the tasks are done. It is an {@link Observer}
	 *                   to make sure that it has a notify method.
	 * @param jsonReader A {@link JsonReader} that has the file already added to it. This
	 *                   allows for a file to be a resource and controlled by the calling
	 *                   activity.
	 */
	public DataTask(Observer mContext, JsonReader jsonReader) {
		setNameList = new ArrayList<>();
		sets = new ArrayList<>();
		observer = mContext;
		this.jsonReader = jsonReader;
	}

	/**
	 * Clears the {@link List} and adds all the set names to it. Clears initially because the
	 * current implementation uses the same {@link List} for the Cards as well.
	 */
	protected void createSetList() {
		setNameList.clear();
		for (int i = 0; i < sets.size(); i++) {
			setNameList.add(sets.get(i).getName());
		}
	}

	/**
	 * Returns a {@link List} that contains either set names or card names for the set.
	 *
	 * @return setNameList, a {@link List} that contains either the set names or card
	 * names in a set.
	 */
	public List<String> getSetList() {
		return setNameList;
	}

	/**
	 * Does a similar thing to the createCardList. Takes in an int to which is the position
	 * of the set in the original set list. It then takes that position and gets each
	 * of the cards in that set. It then places the name into the original list for auto
	 * update.
	 *
	 * @param position The Position of the set in the original list. Used to figure out
	 *                 what set the cards are being requested from. Returns cards sorted
	 *                 alphabetically.
	 */
	protected void createCardList(int position) {
		cards = sets.get(position).getCards();
		Collections.sort(cards, new Comparator<Card>() {
			@Override
			public int compare(Card card1, Card card2) {
				return card1.getName().compareTo(card2.getName());
			}
		});
		setNameList.clear();
		for (int i = 0; i < cards.size(); i++) {
			setNameList.add(cards.get(i).getName());
		}
	}

	/**
	 * Returns the {@link Card} at the position in the card list that was created by
	 * createCardList.
	 *
	 * @param position The position of the {@link Card} from the created card list.
	 * @return {@link Card} at the position.
	 */
	protected Card getCard(int position) {
		return cards.get(position);
	}

	/**
	 * Currently a duplicate of getSetList.
	 * Returns a {@link List} that contains either set names or card names for the set.
	 *
	 * @return setNameList, a {@link List} that contains either the set names or card
	 */
	public List<String> getCardList() {
		return setNameList;
	}

	/**
	 * Parses the JSON object using {@link Gson}. Fills a list that contains a bunch of
	 * {@link Set} objects.
	 *
	 * @param params Unused
	 * @return The list created from getSetList.
	 */
	@Override
	protected List<String> doInBackground(Integer... params) {
		if (params.length == 0) {
			Gson gson = new GsonBuilder().create();

			//for checking token
			JsonToken jsonToken;

			try {
				jsonReader.beginObject();
				jsonReader.nextName();

				while (jsonReader.hasNext() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
					Set set = gson.fromJson(jsonReader, Set.class);
					sets.add(set);
					jsonToken = jsonReader.peek();
					if (jsonToken == JsonToken.NAME) {
						jsonReader.nextName();
					} else if (jsonToken != JsonToken.END_DOCUMENT) {
						jsonReader.endObject();
						jsonReader.skipValue();
						jsonToken = jsonReader.peek();
						if (jsonToken != JsonToken.END_DOCUMENT) {
							jsonReader.nextName();
						}
					}
				}

				Log.i("Filling data:", "success!");
			} catch (IOException ex) {
				Log.e("Filling data:", "Error reading from JSON");
			}
			createSetList();
			return getSetList();
		}
		return getSetList();
	}

	/**
	 * Takes the returned list, calls the super first and then notifies the
	 * {@link Observer} that the data set has changed.
	 *
	 * @param strings The set list returned by doInBackground.
	 */
	@Override
	protected void onPostExecute(List<String> strings) {
		super.onPostExecute(strings);
		observer.notifyDataSetChanged();
	}
}
