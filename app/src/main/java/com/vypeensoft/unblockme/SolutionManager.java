package com.vypeensoft.unblockme;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SolutionManager {

    public static class SolutionMove {
        public String car;
        public int distance;

        public SolutionMove(String car, int distance) {
            this.car = car;
            this.distance = distance;
        }
    }

    public static File getSolutionFile(String pack, int levelNumber) {
        String fileName = "level_" + levelNumber + "_solution.json";
        File file = new File("/sdcard/Vypeensoft/Unblock_Me/game/solutions/" + pack + "/" + fileName);
        if (file.exists()) {
            return file;
        }
        file = new File("/sdcard/Vypeensoft/Unblock_Me/game/solutions/" + fileName);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public static boolean hasSolution(String pack, int levelNumber) {
        return getSolutionFile(pack, levelNumber) != null;
    }

    public static List<SolutionMove> loadSolution(String pack, int levelNumber) {
        File file = getSolutionFile(pack, levelNumber);
        if (file == null) return null;

        List<SolutionMove> moves = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonStr = new String(buffer, "UTF-8");

            JSONObject json = new JSONObject(jsonStr);
            JSONArray solutionArray = json.getJSONArray("solution");
            for (int i = 0; i < solutionArray.length(); i++) {
                JSONObject moveJson = solutionArray.getJSONObject(i);
                String car = moveJson.getString("car");
                int distance = moveJson.getInt("distance");
                moves.add(new SolutionMove(car, distance));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return moves;
    }

    public static void saveSolution(String pack, int levelNumber, List<SolutionMove> moves, long timeTakenMillis) {
        String fileName = "level_" + levelNumber + "_solution.json";
        File dir = new File("/sdcard/Vypeensoft/Unblock_Me/game/solutions/" + pack);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        try {
            JSONObject root = new JSONObject();
            root.put("timeTaken", timeTakenMillis);
            
            JSONArray solutionArray = new JSONArray();
            for (SolutionMove move : moves) {
                JSONObject moveJson = new JSONObject();
                moveJson.put("car", move.car);
                moveJson.put("distance", move.distance);
                solutionArray.put(moveJson);
            }
            root.put("solution", solutionArray);
            
            java.io.FileWriter writer = new java.io.FileWriter(file);
            writer.write(root.toString(2)); // format with 2 space indent
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
