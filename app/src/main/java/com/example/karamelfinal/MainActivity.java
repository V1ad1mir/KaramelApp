package com.example.karamelfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {


    private final int[] sounds = {R.raw.karamel_girgur,R.raw.karamel_muau,R.raw.karamel_mya_click};

    //exit by 2 times
    private long backPressedTime;
    private Toast backToast;



    //soundpool code start
    SoundPool soundPool;
    private AudioManager audioManager;
    // Maximum sound stream.
    private static final int MAX_STREAMS = 5;

    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;
    private boolean loaded;
    private int soundId_1;
    private int soundId_2;
    private int soundId_3;
    private float volume;
    int x=0;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        TextView tv = (TextView) findViewById(R.id.title);

        //alpha animation
        Animation a = AnimationUtils.loadAnimation(this, R.anim.alpha);
        tv.startAnimation(a);



        //counter for cat day

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputString2 = "02-02-2023";
        TextView HM = (TextView) findViewById(R.id.howMany) ;

        try {
            Date date1 = myFormat.parse(currentDate);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            HM.setText(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+ " ");

        } catch (ParseException e) {
            e.printStackTrace();
        }







        //Sound pool big code start

        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Current volumn Index of particular stream type.
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);

        // Get the maximum volume index for a particular stream type.
        float maxVolumeIndex  = (float) audioManager.getStreamMaxVolume(streamType);

        // Volumn (0 --> 1)
        this.volume = currentVolumeIndex / maxVolumeIndex;

        // Suggests an audio stream whose volume should be changed by
        // the hardware volume controls.
        this.setVolumeControlStream(streamType);

        // For Android SDK >= 21
        if (Build.VERSION.SDK_INT >= 21 ) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // for Android SDK < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // When Sound Pool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        // Load sound file 1,2,3 into SoundPool.
        this.soundId_1 = this.soundPool.load(this, sounds[0],1);
        this.soundId_2 = this.soundPool.load(this, sounds[1],1);
        this.soundId_3 = this.soundPool.load(this, sounds[2],1);

        //Sound pool big code end

        ImageView logo = (ImageView) findViewById(R.id.logoCat);
//        make action on click
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(x==0){
                        playSound1();
                        x=1;
                    }
                    else if (x==1){
                        playSound2();
                        x=2;
                    }
                    else{
                        playSound3();
                        x=0;
                    }
                    //animation
                    logo.startAnimation(a);
                }catch(Exception ignored)   {
                }
            }
        });

                Button buttG = (Button) findViewById(R.id.bGallery);
        buttG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(MainActivity.this, gallery.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in,R.anim.out);
                    finish();
                }catch (Exception ignored){

                }
            }
        });

        Button bGame = (Button) findViewById(R.id.bGame);
        bGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(MainActivity.this, kGame.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in,R.anim.out);
                    finish();
                }catch (Exception ignored){

                }

            }
        });


    }

    // sounds start
    public void playSound1( )  {
        if(loaded)  {
            float leftVolumn = volume;
            float rightVolumn = volume;
            int streamId = this.soundPool.play(this.soundId_1,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playSound2( )  {
        if(loaded)  {
            float leftVolumn = volume;
            float rightVolumn = volume;
            int streamId = this.soundPool.play(this.soundId_2,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }
    public void playSound3( )  {
        if(loaded)  {
            float leftVolumn = volume;
            float rightVolumn = volume;
            int streamId = this.soundPool.play(this.soundId_3,leftVolumn, rightVolumn, 1, 0, 1f);

        }
        //sounds end





        //full screen below

        Window w =getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }





    //System back button below

    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000> System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else{
            backToast = Toast.makeText(getBaseContext(), "Press back again to Exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime=System.currentTimeMillis();
    }
}

