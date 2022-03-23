package com.example.karamelfinal;

import static com.example.karamelfinal.R.*;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class kGame extends AppCompatActivity {

    //variable for pictures
    int imgLeftTop;
    int imgRightTop;
    int imgLeftDown;
    int imgRightDown;
    final Array arr = new Array();//new object from class array
    final Random random = new Random();//generate random number
    private int score = 0;

    public int life_pl = 3;

    Toast toast = null;
    Dialog dialog;

    //soundpool code start
    SoundPool soundPool;
    private AudioManager audioManager;
    // Maximum sound stream.
    private static final int MAX_STREAMS = 3;

    // Stream type.
    private static final int streamType = AudioManager.STREAM_MUSIC;

    private boolean loaded;
    private float volume;
    private int soundWinner;
    private int soundLoser;

    String user_score;

    private static final String TEXTVIEW_STATE_KEY = "TEXTVIEW_STATE_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.about_k);

        user_score=""+0;

        TextView text_score = findViewById(id.score);

        //restore String
        if (savedInstanceState != null && savedInstanceState.containsKey(TEXTVIEW_STATE_KEY))
            user_score = savedInstanceState.getString(TEXTVIEW_STATE_KEY);

        text_score.setText(user_score);


        //casting string to int
        score= Integer.parseInt(user_score);





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

        // Load sound file 1,2 into SoundPool.
        this.soundWinner = this.soundPool.load(this, raw.karamel_girgur,1);
        this.soundLoser = this.soundPool.load(this, raw.catyowl,1);

        //alpha animation
        Animation a = AnimationUtils.loadAnimation(this, R.anim.alpha);


//button back
        Button button_back = (Button) findViewById(id.buttonBack);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(kGame.this, MainActivity.class);
                    //animation start
                    startActivity(intent);overridePendingTransition(anim.in, anim.out);
                    //animation end
                    finish();
                }catch (Exception e){

                }
            }
        });


        imgLeftTop = random.nextInt( 10); //generate random from 0 to 9
        imgRightTop = random.nextInt( 10); //generate random from 0 to 9
        while(imgLeftTop == imgRightTop){
            imgRightTop = random.nextInt(10);
        }
        imgLeftDown = random.nextInt( 10); //generate random from 0 to 9
        while(imgLeftDown==imgLeftTop || imgRightDown==imgRightTop){
            imgRightTop = random.nextInt(10);
        }
        imgRightDown = random.nextInt( 10); //generate random from 0 to 9
        while(imgRightDown==imgLeftTop || imgRightDown==imgRightTop || imgRightDown==imgLeftDown){
            imgRightDown = random.nextInt(10);
        }
        //to make new objects from activity
        ImageView imageGame=(ImageView) findViewById(id.imageGame);
        ImageView imageGame2=(ImageView) findViewById(id.imageGame2);
        ImageView imageGame3=(ImageView) findViewById(id.imageGame3);
        ImageView imageGame4=(ImageView) findViewById(id.imageGame4);
        //to take path for image
        imageGame.setImageResource(arr.images1[imgLeftTop]);
        imageGame2.setImageResource(arr.images1[imgRightTop]);
        imageGame3.setImageResource(arr.images1[imgLeftDown]);
        imageGame4.setImageResource(arr.images1[imgRightDown]);


        toast = Toast.makeText(getApplicationContext(),"It's not karamel!", Toast.LENGTH_SHORT);



        //click for left top image
        imageGame.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //if touch pic start
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    toast.cancel();
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //if leave finger start
                    if(imgLeftTop>6){
                        text_score.setText(score_user());
                        text_score.startAnimation(a);


                    }
                    else{
                        toast.show();
                        user_life();
                    }
                    if(imgRightTop>6 || imgLeftDown>6 || imgRightDown>6){
                        imgLeftTop = random.nextInt(10);

                    }
                    else{
                        imgLeftTop = (random.nextInt(3)+7);
                        imgRightTop = random.nextInt(10);
                        imageGame2.setImageResource(arr.images1[imgRightTop]);
                    }


                    imageGame.setImageResource(arr.images1[imgLeftTop]);

                }
                return true;
            }
        });
        //click for right top image
        imageGame2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //if touch pic start
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    toast.cancel();

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //if leave finger start
                    if(imgRightTop>6){
                        text_score.setText(score_user());
                        text_score.startAnimation(a);

                    }

                    else{
                        toast.show();
                        user_life();
                    }
                    if(imgLeftTop>6 || imgLeftDown>6 || imgRightDown>6){
                        imgRightTop = random.nextInt(10);

                    }
                    else{
                        imgRightTop = (random.nextInt(3)+7);
                        imgLeftTop = random.nextInt(10);
                        imageGame.setImageResource(arr.images1[imgLeftTop]);
                    }

                    imageGame2.setImageResource(arr.images1[imgRightTop]);


                }
                return true;
            }
        });
        //click for right top image
        imageGame3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                //if touch pic start
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    toast.cancel();

                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //if leave finger start
                    if(imgLeftDown>6){
                        text_score.setText(score_user());
                        text_score.startAnimation(a);

                    }
                    else{
                        toast.show();
                        user_life();
                    }

                    if(imgLeftTop>6 || imgRightTop>6 || imgRightDown>6){

                        imgLeftDown = random.nextInt(10);
                    }
                    else{
                        imgLeftDown = (random.nextInt(3)+7);
                        imgRightDown = random.nextInt(10);
                        imageGame4.setImageResource(arr.images1[imgRightDown]);
                    }

                    imageGame3.setImageResource(arr.images1[imgLeftDown]);


                }
                return true;
            }
        });
        //click for right top image
        imageGame4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                //if touch pic start
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    toast.cancel();


                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    //if leave finger start
                    if(imgRightDown>6){
                        text_score.setText(score_user());
                        text_score.startAnimation(a);


                    }
                    else{
                        toast.show();
                        user_life();
                    }

                    if(imgLeftTop>6 || imgRightTop>6 || imgLeftDown>6){
                        imgRightDown = random.nextInt(10);

                    }
                     else{
                        imgRightDown = (random.nextInt(3)+7);
                            imgLeftDown = random.nextInt(10);
                        imageGame3.setImageResource(arr.images1[imgLeftDown]);
                    }

                    imageGame4.setImageResource(arr.images1[imgRightDown]);

                }
                return true;
            }
        });



    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // получаем ссылку на текстовую метку
        TextView myTextView = (TextView)findViewById(id.score);
        // Сохраняем его состояние
        outState.putString(TEXTVIEW_STATE_KEY, myTextView.getText().toString());

    }






    //life for user
    public void user_life(){
        ImageView imageLife = (ImageView) findViewById(id.ImageLife);
        life_pl--;

        if(life_pl==2){
            imageLife.setImageResource(drawable.life_2);
        }
        else if(life_pl==1){
            imageLife.setImageResource(drawable.life_1);
        }
        else{
            imageLife.setImageResource(drawable.life_0);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Title");
            alertDialog.setMessage("You lose....back to main screen and start again");
            alertDialog.setCancelable(false);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    try{
                        dialog.cancel();
                        Intent intent=new Intent(kGame.this, MainActivity.class);//make intent
                        startActivity(intent);

                        finish();//close class
                    }catch (Exception e){
                    }
                }
            });

            alertDialog.setIcon(drawable.cat_n);
            playLoser();
            alertDialog.show();
        }

    }


    //score for user
    public String score_user(){
        score++;
        if(score==10){
            playWinner();
            //open new window alert in start
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//hide title for window
            dialog.setContentView(layout.preview);//path for window
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//transpert color for window
            //button to close alert window - start
            Button buttonClose =  dialog.findViewById(id.button_close);
            buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //action for button - start
                    try {
                        Intent intent=new Intent(kGame.this, MainActivity.class);//make intent
                        startActivity(intent);
                        finish();//close class
                    }catch (Exception e){
                        //nothing - place for error
                    }
                    dialog.dismiss();//close alert window
                }
            });
            //button close - end code

            dialog.setCancelable(false);//cancel back button for close window;
            dialog.show();
            //end code for alert window
        }


        return (""+score);

    }

    // sounds start
    public void playWinner( )  {
        if(loaded)  {
            float leftVolumn = volume;
            float rightVolumn = volume;
            this.soundPool.play(this.soundWinner,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    public void playLoser( )  {
        if(loaded)  {
            float leftVolumn = volume;
            float rightVolumn = volume;
            this.soundPool.play(this.soundLoser,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    /* back system button */
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(kGame.this, MainActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){

        }
    }




}