package com.vypeensoft.unblockme;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class LevelSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        GridView gridView = findViewById(R.id.levelGridView);
        List<Level> levels = LevelManager.getLevels();
        
        int lastCompleted = getSharedPreferences("GAME_PROGRESS", MODE_PRIVATE).getInt("LAST_COMPLETED", -1);

        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() { return levels.size(); }
            @Override
            public Object getItem(int position) { return levels.get(position); }
            @Override
            public long getItemId(int position) { return position; }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_level, parent, false);
                }
                TextView textView = convertView.findViewById(R.id.levelText);
                textView.setText(String.valueOf(levels.get(position).levelNumber));
                
                if (position <= lastCompleted + 1) {
                    convertView.setAlpha(1.0f);
                    convertView.setEnabled(true);
                    if (position <= lastCompleted) {
                        convertView.setBackgroundColor(android.graphics.Color.parseColor("#C8E6C9")); // Completed color
                    }
                } else {
                    convertView.setAlpha(0.5f);
                    // Optionally disable selection of locked levels
                    // convertView.setEnabled(false); 
                }

                convertView.setOnClickListener(v -> {
                    Intent intent = new Intent(LevelSelectActivity.this, GameActivity.class);
                    intent.putExtra("LEVEL_INDEX", position);
                    startActivity(intent);
                });
                
                return convertView;
            }
        });
    }
}
