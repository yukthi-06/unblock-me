package com.vypeensoft.unblockme;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class GameActivity extends AppCompatActivity implements GameView.OnGameListener {
    private GameView gameView;
    private GameEngine engine;
    private TextView tvMoves, tvLevel;
    private int currentLevelIndex;
    private String currentPack;
    private List<Level> levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);
        tvMoves = findViewById(R.id.tvMoves);
        tvLevel = findViewById(R.id.tvLevel);
        Button btnReset = findViewById(R.id.btnReset);

        currentPack = getIntent().getStringExtra("PACK_NAME");
        if (currentPack == null) currentPack = "beginner";

        levels = LevelManager.getLevels(currentPack);
        currentLevelIndex = getIntent().getIntExtra("LEVEL_INDEX", 0);

        loadLevel(currentLevelIndex);

        btnReset.setOnClickListener(v -> loadLevel(currentLevelIndex));
        gameView.setOnGameListener(this);
    }

    private void loadLevel(int index) {
        currentLevelIndex = index;
        Level level = levels.get(index);
        engine = new GameEngine(level.getBlocksCopy());
        gameView.setEngine(engine);
        tvLevel.setText("Level: " + level.levelNumber);
        updateMoves();
    }

    private void updateMoves() {
        tvMoves.setText("Moves: " + engine.getMoves());
    }

    @Override
    public void onMove() {
        updateMoves();
    }

    @Override
    public void onWin() {
        // Save progress
        getSharedPreferences("GAME_PROGRESS", MODE_PRIVATE)
                .edit()
                .putInt("LAST_COMPLETED_" + currentPack, currentLevelIndex)
                .apply();

        String msg = "Solved level " + levels.get(currentLevelIndex).levelNumber + " in " + engine.getMoves() + " moves!";
        android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_SHORT).show();

        if (currentLevelIndex + 1 < levels.size()) {
            loadLevel(currentLevelIndex + 1);
        } else {
            android.widget.Toast.makeText(this, "Pack Completed!", android.widget.Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
