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
 * Gets the image for a magic card based on its multiverse Id and this link
 * http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=@multiverseId&type=card
 * Main use is to send it a {@link Activity} when created that has an imageView with the
 * id R.id.imageView. Then you call doInBackground. This fetches the image off the main
 * thread and returns the image to the onPostExecute method.
 *
 * @author thechucklingatom
 *
 */

public class CardImageFetcher extends AsyncTask<Card, Void, Drawable>{

    Activity fetchingActivity;

    public CardImageFetcher(Activity activity){
        fetchingActivity = activity;
    }

    /**
     * Returns an image of a card that will be displayed on the screen.
     * <p>
     * The {@link Card} argument should have a multiverse id. This multiverse id
     * is used to build the image url. As of right now Wizards of the Coast's
     * gatherer site uses this id as a unique identifier that helps clear up set
     * confusion for reprints, and allows for an easy way to pull a card image
     * from their site.
     *
     * @param cards cards that you want to display, currently only supports a single card.
     * @return the image at the url or null if unable to fetch. Returned to onPostExecute
     */
    @Override
    protected Drawable doInBackground(Card... cards) {
        try{
            String url = "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid="
                    + cards[0].getMultiverseID()
                    + "&type=card";
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

    /**
     * Takes the image the card fetcher grabbed and adds it into the image view.
     * If null nothing happens. If there is an image, it sets the returned image
     * as the image used (setImageDrawable) in R.id.imageView. It then sets it to match
     * the (x,y) of the imageView. It then sets its visibility to true.
     *
     * @param cardImage single image returned from doInBackground can be null
     */
    protected void onPostExecute(Drawable cardImage){
        Log.i("onPostExecute: ", "post execute");
        if(cardImage != null){
            Log.i("onPostExecute: ", "non null image");
            ImageView cardImageView = (ImageView)fetchingActivity.findViewById(R.id.imageView);
            cardImageView.setImageDrawable(cardImage);
            cardImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            cardImageView.setVisibility(View.VISIBLE);
        }
    }
}
