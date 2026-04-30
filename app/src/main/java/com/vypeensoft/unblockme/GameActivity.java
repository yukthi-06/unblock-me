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
    private List<Level> levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);
        tvMoves = findViewById(R.id.tvMoves);
        tvLevel = findViewById(R.id.tvLevel);
        Button btnReset = findViewById(R.id.btnReset);

        levels = LevelManager.getLevels();
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
                .putInt("LAST_COMPLETED", currentLevelIndex)
                .apply();

        new AlertDialog.Builder(this)
                .setTitle("Level Completed!")
                .setMessage("Congratulations! You solved level " + levels.get(currentLevelIndex).levelNumber + " in " + engine.getMoves() + " moves.")
                .setPositiveButton("Next Level", (dialog, which) -> {
                    if (currentLevelIndex + 1 < levels.size()) {
                        loadLevel(currentLevelIndex + 1);
                    } else {
                        finish();
                    }
                })
                .setNegativeButton("Menu", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }
}
