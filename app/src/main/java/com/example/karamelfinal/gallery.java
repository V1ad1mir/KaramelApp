package com.example.karamelfinal;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class gallery extends AppCompatActivity {

    ImageView iw;
    Button butt;
    int i=0;

    final Array arr = new Array();//new object from class array



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galery);

        iw = (ImageView) findViewById(R.id.image2);

        butt = (Button) findViewById(R.id.b1);




        iw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iw.animate()

                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) { }{
                                iw.animate().alpha(0).setDuration(180);
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                //this 1 line for change picture
                                iw.setImageResource(arr.images[i]);
                                iw.animate().alpha(1).setDuration(90);


                            }
                            @Override
                            public void onAnimationCancel(Animator animator) { }
                            @Override
                            public void onAnimationRepeat(Animator animator) { }
                        });

                i++;
                if(i==7)
                    i=0;
            }

        });
       // button click
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iw.animate()

                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) { }{
                               iw.animate().alpha(0).setDuration(180);
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                //this 1 line for change picture
                                iw.setImageResource(arr.images[i]);
                                iw.animate().alpha(1).setDuration(90);


                            }
                            @Override
                            public void onAnimationCancel(Animator animator) { }
                            @Override
                            public void onAnimationRepeat(Animator animator) { }
                        });

                i++;
                if(i==7)
                    i=0;
            }
        });
        Button button_back = (Button) findViewById(R.id.buttonBack);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(gallery.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in,R.anim.out);finish();
                }catch (Exception e){

                }
            }
        });
    }

    //back system button
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent(gallery.this, MainActivity.class);
            startActivity(intent);finish();
        }catch (Exception e){

        }
    }
}
