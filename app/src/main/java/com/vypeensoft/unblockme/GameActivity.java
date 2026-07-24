package com.vypeensoft.unblockme;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import android.view.View;

public class GameActivity extends AppCompatActivity implements GameView.OnGameListener {
    private GameView gameView;
    private GameEngine engine;
    private TextView tvMoves, tvLevel, tvTime;
    private long startTime;
    private boolean timerStarted;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = SystemClock.uptimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            if (tvTime != null) {
                tvTime.setText(String.format("Time: %02d:%02d", minutes, seconds));
            }
            timerHandler.postDelayed(this, 500);
        }
    };
    private int currentLevelIndex;
    private String currentPack;
    private List<Level> levels;
    private Button btnSolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);
        tvMoves = findViewById(R.id.tvMoves);
        tvTime = findViewById(R.id.tvTime);
        tvLevel = findViewById(R.id.tvLevel);
        Button btnReset = findViewById(R.id.btnReset);
        Button btnUndo = findViewById(R.id.btnUndo);
        btnSolution = findViewById(R.id.btnSolution);
        TextView btnPrev = findViewById(R.id.btnPrev);
        TextView btnNext = findViewById(R.id.btnNext);

        currentPack = getIntent().getStringExtra("PACK_NAME");
        if (currentPack == null) currentPack = "beginner";

        levels = LevelManager.getLevels(currentPack);
        currentLevelIndex = getIntent().getIntExtra("LEVEL_INDEX", 0);

        loadLevel(currentLevelIndex);

        btnReset.setOnClickListener(v -> loadLevel(currentLevelIndex));
        btnUndo.setOnClickListener(v -> {
            if (engine != null && engine.undoMove()) {
                updateMoves();
                gameView.invalidate();
            }
        });
        btnSolution.setOnClickListener(v -> {
            Level currentLevel = levels.get(currentLevelIndex);
            List<SolutionManager.SolutionMove> moves = SolutionManager.loadSolution(currentPack, currentLevel.levelNumber);
            if (moves != null && !moves.isEmpty()) {
                // Reset board first before playing solution
                loadLevel(currentLevelIndex);
                gameView.playSolution(moves);
            }
        });
        
        btnPrev.setOnClickListener(v -> {
            if (currentLevelIndex > 0) {
                loadLevel(currentLevelIndex - 1);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentLevelIndex < levels.size() - 1) {
                loadLevel(currentLevelIndex + 1);
            }
        });

        gameView.setOnGameListener(this);
    }

    private void stopTimer() {
        timerStarted = false;
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void loadLevel(int index) {
        stopTimer();
        if (tvTime != null) tvTime.setText("Time: 00:00");
        currentLevelIndex = index;
        Level level = levels.get(index);
        
        if (SolutionManager.hasSolution(currentPack, level.levelNumber)) {
            btnSolution.setVisibility(View.VISIBLE);
        } else {
            btnSolution.setVisibility(View.GONE);
        }

        TextView btnPrev = findViewById(R.id.btnPrev);
        TextView btnNext = findViewById(R.id.btnNext);
        btnPrev.setVisibility(index > 0 ? View.VISIBLE : View.INVISIBLE);
        btnNext.setVisibility(index < levels.size() - 1 ? View.VISIBLE : View.INVISIBLE);

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
        if (!timerStarted) {
            timerStarted = true;
            startTime = SystemClock.uptimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
        }
        updateMoves();
    }

    @Override
    public void onWin() {
        long timeTakenMillis = timerStarted ? (SystemClock.uptimeMillis() - startTime) : 0;
        stopTimer();
        // Save progress
        getSharedPreferences("GAME_PROGRESS", MODE_PRIVATE)
                .edit()
                .putInt("LAST_COMPLETED_" + currentPack, currentLevelIndex)
                .apply();

        // Save solution
        Level currentLevel = levels.get(currentLevelIndex);
        List<SolutionManager.SolutionMove> solutionPath = engine.getSolutionPath();
        SolutionManager.saveSolution(currentPack, currentLevel.levelNumber, solutionPath, timeTakenMillis);

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
