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

public class PackSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_select);

        GridView gridView = findViewById(R.id.packGridView);
        List<String> packs = LevelManager.getPacks();
        
        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() { return packs.size(); }
            @Override
            public Object getItem(int position) { return packs.get(position); }
            @Override
            public long getItemId(int position) { return position; }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_level, parent, false);
                }
                TextView textView = convertView.findViewById(R.id.levelText);
                String packName = packs.get(position);
                textView.setText(packName);
                
                convertView.setOnClickListener(v -> {
                    Intent intent = new Intent(PackSelectActivity.this, LevelSelectActivity.class);
                    intent.putExtra("PACK_NAME", packName);
                    startActivity(intent);
                });
                
                return convertView;
            }
        });
    }
}
