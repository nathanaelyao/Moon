package com.polygonshark.moon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class popUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        getWindow().setLayout((int)( metrics.widthPixels * 0.7), (int) (metrics.heightPixels * 0.4));

        Button rewardBtn = (Button)findViewById(R.id.rewardBtn);
        rewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button okBtn = (Button)findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView coinText = (TextView)findViewById(R.id.coinText);

        if(getIntent().hasExtra("coins")){
            int coins = getIntent().getIntExtra("coins" , 0);
            coinText.setText("$"+coins);
        }
    }
}
