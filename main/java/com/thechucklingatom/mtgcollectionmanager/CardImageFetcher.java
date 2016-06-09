package com.thechucklingatom.mtgcollectionmanager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Gets the image for a magic card based on its multiverse Id and this link http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=@multiverseId&type=card
 *
 * @author thechucklingatom
 *
 */

public class CardImageFetcher extends AsyncTask<Card, Void, Drawable>{

    Activity fetchingActivity;

    public CardImageFetcher(Activity activity){
        fetchingActivity = activity;
    }

    @Override
    protected Drawable doInBackground(Card... cards) {
        try{
            String url = "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" +
                    cards[0].getMultiverseid() +
                    "&type=card";
            InputStream inputStream = (InputStream) new URL(url).getContent();
            Log.i("doInBackground: ", "inputStream gotten");
            return Drawable.createFromStream(inputStream, "src name");
        } catch (MalformedURLException e) {
            Log.e("fetchImage: ", "Error with URL", e.fillInStackTrace());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("fetchImage: ", "Error reading image", e.fillInStackTrace());
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Drawable cardImage){
        Log.i("onPostExecute: ", "post execute");
        if(cardImage != null){
            Log.i("onPostExecute: ", "non null image");
            ImageView imageCard = (ImageView)fetchingActivity.findViewById(R.id.imageView);
            imageCard.setImageDrawable(cardImage);
            imageCard.setScaleType(ImageView.ScaleType.FIT_XY);
            imageCard.setVisibility(View.VISIBLE);
        }
    }
}
