package com.vypeensoft.unblockme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnPlay).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("LEVEL_INDEX", 0);
            startActivity(intent);
        });

        findViewById(R.id.btnSelectLevel).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LevelSelectActivity.class));
        });

        findViewById(R.id.btnAbout).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        });
    }
}
