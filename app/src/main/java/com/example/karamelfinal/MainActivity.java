package com.example.karamelfinal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {



    final Array arrSounds = new Array();//new object from class array

    //exit by 2 times
    private long backPressedTime;
    private Toast backToast;



    //soundpool code start
    SoundPool soundPool;
    AudioManager audioManager;
    // Maximum sound stream.
    private static final int MAX_STREAMS = 4;

    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;
    private boolean loaded;
    private int soundId_1;
    private int soundId_2;
    private int soundId_3;
    private float volume;
    int x=0;




    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        TextView tv = (TextView) findViewById(R.id.title);

        //alpha animation
        Animation a = AnimationUtils.loadAnimation(this, R.anim.alpha);
        tv.startAnimation(a);


        //full screen below
        Window w =getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        //counter for cat day
        //current date
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        char lastD = currentDate.charAt(currentDate.length()-1);//cut last digit
        lastD++;//last digit of year + one

        String inputString2 = "02-02-202"+lastD; //date + next year

        TextView HM = (TextView) findViewById(R.id.howMany) ;

        try {
            Date date1 = myFormat.parse(currentDate);
            Date date2 = myFormat.parse(inputString2);
            assert date2 != null;
            assert date1 != null;
            long diff = date2.getTime() - date1.getTime();
            HM.setText(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+ " ");

        } catch (ParseException e) {
            e.printStackTrace();
        }







        //Sound pool big code start

        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Current volume Index of particular stream type.
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);

        // Get the maximum volume index for a particular stream type.
        float maxVolumeIndex  = (float) audioManager.getStreamMaxVolume(streamType);

        // Volume (0 --> 1)
        this.volume = currentVolumeIndex / maxVolumeIndex;

        // Suggests an audio stream whose volume should be changed by
        // the hardware volume controls.
        this.setVolumeControlStream(streamType);

        // For Android SDK >= 21
        AudioAttributes audioAttrib = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        SoundPool.Builder builder= new SoundPool.Builder();
        builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

        this.soundPool = builder.build();

        // When Sound Pool load complete.
        this.soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> loaded = true);

        // Load sound file 1,2,3 into SoundPool.
        this.soundId_1 = this.soundPool.load(this,arrSounds.sounds[0],1);
        this.soundId_2 = this.soundPool.load(this,arrSounds.sounds[1],1);
        this.soundId_3 = this.soundPool.load(this,arrSounds.sounds[2],1);

        //Sound pool big code end

        ImageView logo = (ImageView) findViewById(R.id.logoCat);
//        make action on click
        logo.setOnClickListener(view -> {
            try{

                playSound(x);
                x++;
                if(x==3)
                    x=0;


                //animation
                logo.startAnimation(a);
            }catch(Exception ignored)   {
            }
        });

                Button buttG = (Button) findViewById(R.id.bGallery);
        buttG.setOnClickListener(view -> {

            try{
                Intent intent = new Intent(MainActivity.this, gallery.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }catch (Exception ignored){

            }
        });

        Button bGame = (Button) findViewById(R.id.bGame);
        bGame.setOnClickListener(view -> {

            try{
                Intent intent = new Intent(MainActivity.this, kGame.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in,R.anim.out);
                finish();
            }catch (Exception ignored){

            }

        });


    }

    // sounds start
    public void playSound(int x )  {
        if(x==0){
            try {
                if(loaded)  {
                    float leftVolumn = volume;
                    float rightVolumn = volume;
                    int streamId = this.soundPool.play(this.soundId_1,leftVolumn, rightVolumn, 1, 0, 1f);

                }

            } catch (Exception ignored) {

            }
        }
        else if(x==1){
            try {
                if(loaded)  {
                    float leftVolumn = volume;
                    float rightVolumn = volume;
                    int streamId = this.soundPool.play(this.soundId_2,leftVolumn, rightVolumn, 1, 0, 1f);

                }

            } catch (Exception ignored) {

            }

        }

        else{
            try {
                if(loaded)  {
                    float leftVolumn = volume;
                    float rightVolumn = volume;
                    int streamId = this.soundPool.play(this.soundId_3,leftVolumn, rightVolumn, 1, 0, 1f);
                }

            } catch (Exception ignored) {

            }
        }
    }

//sounds end




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

