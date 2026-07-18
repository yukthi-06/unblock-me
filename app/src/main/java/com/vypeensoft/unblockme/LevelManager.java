package com.vypeensoft.unblockme;

import android.graphics.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class LevelManager {
    public static List<String> getPacks() {
        List<String> packs = new ArrayList<>();
        File baseDir = new File("/sdcard/Vypeensoft/Unblock_Me/game/levels/");
        if (baseDir.exists() && baseDir.isDirectory()) {
            File[] dirs = baseDir.listFiles(File::isDirectory);
            if (dirs != null) {
                for (File d : dirs) {
                    packs.add(d.getName());
                }
            }
        }
        return packs;
    }

    public static List<Level> getLevels() {
        return getLevels("beginner");
    }

    public static List<Level> getLevels(String difficulty) {
        List<Level> levels = new ArrayList<>();
        File dir = new File("/sdcard/Vypeensoft/Unblock_Me/game/levels/" + difficulty);
        if (!dir.exists() || !dir.isDirectory()) {
            return levels; // Returns empty list if dir doesn't exist
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files == null) return levels;

        for (File file : files) {
            try {
                InputStream is = new FileInputStream(file);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String jsonStr = new String(buffer, "UTF-8");

                JSONObject json = new JSONObject(jsonStr);
                int id = json.getInt("id");
                Level level = new Level(id);
                if (json.has("difficulty")) {
                    level.difficulty = json.getString("difficulty");
                }
                if (json.has("minimumMoves")) {
                    level.minimumMoves = json.getInt("minimumMoves");
                }

                JSONArray cars = json.getJSONArray("cars");
                for (int i = 0; i < cars.length(); i++) {
                    JSONObject car = cars.getJSONObject(i);
                    String carId = car.getString("id");
                    int x = car.getInt("x");
                    int y = car.getInt("y");
                    int length = car.getInt("length");
                    boolean horizontal = car.getBoolean("horizontal");
                    
                    boolean isTarget = carId.equals("X");
                    int color = isTarget ? Color.RED : getColorForId(carId);

                    level.addBlock(new Block(carId, x, y, length, horizontal, color, isTarget));
                }
                levels.add(level);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        Collections.sort(levels, new Comparator<Level>() {
            @Override
            public int compare(Level l1, Level l2) {
                return Integer.compare(l1.levelNumber, l2.levelNumber);
            }
        });

        return levels;
    }
    
    private static int getColorForId(String id) {
        if (id.equals("X")) return Color.RED;
        
        int hash = id.hashCode();
        int r = (hash & 0xFF0000) >> 16;
        int g = (hash & 0x00FF00) >> 8;
        int b = (hash & 0x0000FF);
        
        r = (r % 128) + 64;
        g = (g % 128) + 100; // a bit more green
        b = (b % 128) + 128; // a bit more blue
        
        return Color.rgb(r, g, b);
    }
}
