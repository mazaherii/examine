package com.ytu.examine.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ytu.examine.app.auth.Authentication;

public class MainActivity extends AppCompatActivity {
    public FloatingActionButton fabMenu1;
    public FloatingActionButton fabMenu2;
    public FloatingActionButton fabMenu3;
    public FloatingActionButton fabMenu4;
    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private RelativeLayout logout_container;
    private  SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabMenu1 = findViewById(R.id.soruEkle);
        fabMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, AddQuestionActivity.class);
                startActivity(intent1);
            }
        });

        fabMenu2 = findViewById(R.id.sorular);
        fabMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, QuestionListingActivity.class);
                startActivity(intent2);
            }
        });

        fabMenu3 = findViewById(R.id.sinavEkle);
        fabMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, AddExamActivity.class);
                startActivity(intent3);
            }
        });

        fabMenu4 = findViewById(R.id.sinavAyarlar);
        fabMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this, EditExamActivity.class);
                startActivity(intent4);
            }
        });


        initToolbar();

        logout_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putBoolean("ISLOGGEDIN",false).apply();
                startLogin();
                finish();

            }
        });
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        logout_container = findViewById(R.id.logout_c);
        setSupportActionBar(toolbar);
        setSystemBarColor(this, R.color.colorPrimary);

        sp = getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
        String name = sp.getString("NAME", null);
        String username = sp.getString("USERNAME", null);
        if (username != null){
            toolbar.setSubtitle("@" + username);
        }
        if (name != null) {
            Toast.makeText(this, "Hoşgeldiniz " + name, Toast.LENGTH_LONG).show();
            toolbar.setTitle("Hoşgeldiniz sayın " + name);
        }
    }

    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }
    private void startLogin(){
        Authentication.start(this);
    }
}
