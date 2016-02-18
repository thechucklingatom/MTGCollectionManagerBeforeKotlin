package com.thechucklingatom.mtgcollectionmanager;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This object is holding all the data for my fragment.
 *
 * Created by thechucklingatom on 2/17/2016.
 * @author thechucklingatom
 * @
 */
public class DataTest {
    private List<String> setNameList;
    private List<Set> sets;

    public DataTest(JsonReader jsonReader){
        setNameList = new ArrayList<>();
        sets = new ArrayList<>();
        fillData(jsonReader);
        createSetList();
    }

    private void fillData(JsonReader jsonReader){
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

            Log.i("Fill Data: ", "success!");
        }catch(IOException ex){
            Log.e("fillData", "Error reading from JSON");
        }
    }

    private void createSetList(){
        for(int i = 0; i < sets.size(); i++){
            setNameList.add(sets.get(i).getName());
        }
    }

    public List<String> getSetList(){
        return setNameList;
    }
}
