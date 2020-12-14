package com.RandoDam.rando.ActivityClass;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.RandoDam.rando.Fragments.Hamburger_fragment;
import com.RandoDam.rando.R;

public class MainActivity extends AppCompatActivity {

    ImageView iv_foret;
    ImageView iv_randonneur;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    /**Handler to handle the splash screen **/
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        iv_foret = (ImageView)findViewById(R.id.iv_france);
        iv_randonneur = (ImageView)findViewById(R.id.iv_randonneur);

        iv_randonneur.animate().translationYBy(-150).setDuration(5000);
        iv_randonneur.animate().translationXBy(+150).setDuration(5000);
        iv_randonneur.animate().scaleX(0.01f).setDuration(5000);
        iv_randonneur.animate().scaleY(0.01f).setDuration(5000);

        /** To connect to the fisrt page of the appli after splash screen**/
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Hamburger_fragment.class));
            }
        },1000);
        }
}