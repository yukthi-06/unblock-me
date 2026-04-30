package com.vypeensoft.unblockme;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        
        TextView tvAbout = findViewById(R.id.tvAbout);
        tvAbout.setText("Unblock Me Clone\nBuilt with Android Studio and Java\n\nObjective: Move the red block to the exit on the right.");
    }
}
