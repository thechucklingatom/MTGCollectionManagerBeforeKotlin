package com.thechucklingatom.mtgcollectionmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This object is holding all the data for my fragment.
 *
 * Created by thechucklingatom on 2/17/2016.
 * @author thechucklingatom
 * @
 */
public class DataTask extends AsyncTask<Integer, Integer, List<String>> {
    private List<String> setNameList;
    private List<Set> sets;
    private List<Card> cards;
    JsonReader jsonReader;
    Observer context;

    public interface Observer{
        void notifyDataSetChanged();
    }

    public DataTask(Context mContext, JsonReader jsonReader){
        setNameList = new ArrayList<>();
        sets = new ArrayList<>();
        context = (CollectionManagerActivity)mContext;
        this.jsonReader = jsonReader;
    }

    protected void createSetList(){
        setNameList.clear();
        for(int i = 0; i < sets.size(); i++){
            setNameList.add(sets.get(i).getName());
        }
    }

    public List<String> getSetList(){
        return setNameList;
    }

    protected void createCardList(int position){
        cards = sets.get(position).getCards();
        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return card1.getName().compareTo(card2.getName());
            }
        });
        setNameList.clear();
        for(int i = 0; i < cards.size(); i++){
            setNameList.add(cards.get(i).getName());
        }
    }

    protected Card getCard(int position){
        return cards.get(position);
    }

    public List<String> getCardList(){
        return setNameList;
    }

    @Override
    protected List<String> doInBackground(Integer... params) {
        if(params.length == 0){

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
            }catch(IOException ex){
                Log.e("Filling data:", "Error reading from JSON");
            }
            createSetList();
            return getSetList();
        }

        return getSetList();
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
        context.notifyDataSetChanged();
    }
}
