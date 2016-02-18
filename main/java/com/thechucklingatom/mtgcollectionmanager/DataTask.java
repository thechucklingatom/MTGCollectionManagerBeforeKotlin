package com.thechucklingatom.mtgcollectionmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This object is holding all the data for my fragment.
 *
 * Created by thechucklingatom on 2/17/2016.
 * @author thechucklingatom
 * @
 */
public class DataTask extends AsyncTask<String, Integer, List<String>> {
    ArrayAdapter<String> adapter;
    private List<String> setNameList;
    private List<Set> sets;
    Context context;

    public DataTask(Context mContext){
        setNameList = new ArrayList<>();
        sets = new ArrayList<>();
        context = mContext;
    }

    private void createSetList(){
        for(int i = 0; i < sets.size(); i++){
            setNameList.add(sets.get(i).getName());
        }
    }

    public List<String> getSetList(){
        return setNameList;
    }

    @Override
    protected List<String> doInBackground(String... params) {
        if(params.length == 0){
            JsonReader jsonReader = new JsonReader(new InputStreamReader(
                                                            context.getResources()
                                                        .openRawResource(R.raw.all_sets_x)));

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
        }else if(params.length == 1){
            //todo make it pull card data based on the list item selected.
        }

        return getSetList();
    }
}
