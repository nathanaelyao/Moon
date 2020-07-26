package com.polygonshark.moon.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaDrm;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.polygonshark.moon.MainActivity;
import com.polygonshark.moon.R;
import com.polygonshark.moon.cancel;
import com.polygonshark.moon.popUp;
import android.content.SharedPreferences.Editor;

import java.util.Locale;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Boolean playing = false;
    int time = 15;
    CountDownTimer countDownTimer;
    long countTime;

    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final Button button = (Button)root.findViewById(R.id.button2);
        final ImageView image = (ImageView)root.findViewById(R.id.imageView3);
        final SeekBar seekbar = (SeekBar) root.findViewById(R.id.seekBar);
        final TextView timeText = (TextView)root.findViewById(R.id.timeText);
        TextView coinText = (TextView)root.findViewById(R.id.coinText);





        seekbar.setMax(60);
        seekbar.setProgress(time);
        timeText.setText(time + ":00");


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                time = progress;
                timeText.setText(time + ":00");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playing) {
                    button.setText("Cancel");
                    image.setImageDrawable(getResources().getDrawable(R.drawable.ship));
                    seekbar.setEnabled(false);

                    countDownTimer = new CountDownTimer((time * 60000), 1000) {

                        public void onTick(long millisUntilFinished) {
                            long minutes = (millisUntilFinished/1000)/60;
                            long seconds = (millisUntilFinished/1000)%60;
                            String timeLeft = "";
                            timeLeft += minutes + "";
                            timeLeft += ":";
                            if (seconds<10){
                                timeLeft+= "0";
                            }
                            timeLeft += seconds + "";
                            timeText.setText(timeLeft);
                        }

                        public void onFinish() {
                            timeText.setText("done!");
                            Intent intent = new Intent(getContext(), popUp.class);
                            intent.putExtra("coins", time);
                            startActivity(intent);
                            SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
                            int newMoney = prefs.getInt("key", 0);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("key", newMoney);
                            editor.commit();
                        }
                    }.start();
                }
                else if (playing){
                    Intent intent = new Intent(getContext(), cancel.class);
                    startActivity(intent);
                    if (getActivity().getIntent().hasExtra("done")){
                        getActivity().finish();
                    }
                    else{
                        button.setText("Press here to take off!");
                        image.setImageDrawable(getResources().getDrawable(R.drawable.initialship));
                        seekbar.setEnabled(true);
                        countDownTimer.cancel();
                    }
                }
                playing = !playing;
            }
        });


        return root;



    }
}
