package com.thechucklingatom.mtgcollectionmanager;

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
 * http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=@multiverseId&type=card.
 * Main use is to send it an {@link ImageView} that you want the card image to appear in.
 * Then you call doInBackground. This fetches the image off the UI thread and returns the
 * image to the onPostExecute method.
 *
 * @author thechucklingatom
 *
 */

public class CardImageFetcher extends AsyncTask<Card, Void, Drawable>{

    ImageView displayView;

    /**
     * Constructor for CardImageFetcher. All it needs is the {@link ImageView} you want the
     * card image to be displayed inside.
     * @param imageView The {@link ImageView} you want the card image displayed inside.
     */
    public CardImageFetcher(ImageView imageView){
        displayView = imageView;
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
                    + cards[0].getMultiverseid()
                    + "&type=card";
            Log.i("CardImageFetcher: ", "doInBackground: MultiverseId = "
                    + cards[0].getMultiverseid());
            InputStream inputStream = (InputStream) new URL(url).getContent();
            Log.i("CardImageFetcher: ", "doInBackground: inputStream gotten");
            return Drawable.createFromStream(inputStream, "src name");
        } catch (MalformedURLException e) {
            Log.e("CardImageFetcher: ", "fetchImage: Error with URL", e.fillInStackTrace());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("CardImageFetcher: ", "fetchImage: Error reading image", e.fillInStackTrace());
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
        Log.i("CardImageFetcher: ", "onPostExecute");
        if(cardImage != null){
            Log.i("CardImageFetcher: ", "onPostExecute: non null image");
            if(displayView != null) {
                displayView.setImageDrawable(cardImage);
                displayView.setScaleType(ImageView.ScaleType.FIT_XY);
                displayView.setVisibility(View.VISIBLE);
            }else{
                Log.e("CardImageFetcher: ", "onPostExecute: null view provided");
            }
        }
    }
}
