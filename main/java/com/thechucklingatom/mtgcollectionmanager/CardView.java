package com.thechucklingatom.mtgcollectionmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class CardView extends AppCompatActivity {

    CardImageFetcher imageFetcher = new CardImageFetcher(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent passingIntent = getIntent();

        Card displayedCard = (Card)passingIntent.getSerializableExtra("card");

        TextView textCardName = (TextView)findViewById(R.id.textCardName);
        TextView textManaCost = (TextView)findViewById(R.id.textManaCost);
        TextView textCardType = (TextView)findViewById(R.id.textCardType);
        TextView textCardText = (TextView)findViewById(R.id.textCardText);
        TextView textPowerAndToughness = (TextView)findViewById(R.id.textPowerAndToughness);

        if(displayedCard.getName() != null){
            textCardName.setText(displayedCard.getName());
        }else{
            textCardName.setText("");
        }

        if(displayedCard.getManaCost() != null){
            textManaCost.setText(displayedCard.getManaCost());
        }else{
            textManaCost.setText("");
        }

        if(displayedCard.getType() != null) {
            textCardType.setText(displayedCard.getType());
        }else{
            textCardType.setText("");
        }

        if(displayedCard.getText() != null){
            textCardText.setText(displayedCard.getText());
        }else{
            textCardText.setText("");
        }

        if(displayedCard.getPower() != null && displayedCard.getToughness() != null){
            String powerAndToughness =  displayedCard.getPower() +
                    " / " +
                    displayedCard.getToughness();
            textPowerAndToughness.setText(powerAndToughness);
        }else{
            textPowerAndToughness.setText("");
        }

        imageFetcher.execute(displayedCard);
    }
}
