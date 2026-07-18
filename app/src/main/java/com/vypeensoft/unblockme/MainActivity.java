package com.vypeensoft.unblockme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        findViewById(R.id.btnPlay).setOnClickListener(v -> {
            String lastPack = getSharedPreferences("GAME_PROGRESS", MODE_PRIVATE).getString("LAST_PACK", null);
            if (lastPack == null) {
                java.util.List<String> packs = LevelManager.getPacks();
                if (!packs.isEmpty()) {
                    lastPack = packs.get(0);
                } else {
                    lastPack = "beginner";
                }
            }
            int lastCompleted = getSharedPreferences("GAME_PROGRESS", MODE_PRIVATE).getInt("LAST_COMPLETED_" + lastPack, -1);
            
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("PACK_NAME", lastPack);
            intent.putExtra("LEVEL_INDEX", lastCompleted + 1);
            startActivity(intent);
        });

        findViewById(R.id.btnSelectLevel).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PackSelectActivity.class));
        });

        findViewById(R.id.btnAbout).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        });
    }
}
