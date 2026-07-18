package com.vypeensoft.unblockme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;
import java.io.IOException;

public class GameView extends View {
    private GameEngine engine;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float cellSize;
    private Block selectedBlock = null;
    private float lastTouchX, lastTouchY;
    private OnGameListener listener;

    private Bitmap bmpTarget;
    private Bitmap bmpWoodH2;
    private Bitmap bmpWoodH3;
    private Bitmap bmpWoodV2;
    private Bitmap bmpWoodV3;

    public interface OnGameListener {
        void onMove();
        void onWin();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadBitmaps(context);
    }

    private void loadBitmaps(Context context) {
        try {
            bmpTarget = BitmapFactory.decodeStream(context.getAssets().open("tiles/wood/red_block_1.png"));
            bmpWoodH2 = BitmapFactory.decodeStream(context.getAssets().open("tiles/wood/wood_block_h2.png"));
            bmpWoodH3 = BitmapFactory.decodeStream(context.getAssets().open("tiles/wood/wood_block_h3.png"));
            bmpWoodV2 = BitmapFactory.decodeStream(context.getAssets().open("tiles/wood/wood_block_v2.png"));
            bmpWoodV3 = BitmapFactory.decodeStream(context.getAssets().open("tiles/wood/wood_block_v3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
        invalidate();
    }

    public void setOnGameListener(OnGameListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellSize = Math.min(w, h) / 6.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (engine == null) return;

        // Draw grid background
        paint.setColor(Color.parseColor("#E0E0E0"));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        paint.setColor(Color.WHITE);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                canvas.drawRect(i * cellSize + 2, j * cellSize + 2, (i + 1) * cellSize - 2, (j + 1) * cellSize - 2, paint);
            }
        }

        // Draw board border
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.parseColor("#8D6E63"));
        canvas.drawRect(0, 0, 6 * cellSize, 6 * cellSize, paint);
        paint.setStyle(Paint.Style.FILL);

        // Draw exit indicator
        paint.setColor(Color.RED);
        paint.setAlpha(80);
        canvas.drawRect(5 * cellSize, 2 * cellSize, 6 * cellSize, 3 * cellSize, paint);
        paint.setAlpha(255);

        // Draw blocks
        for (Block b : engine.getBlocks()) {
            float left = b.x * cellSize + 8;
            float top = b.y * cellSize + 8;
            float right = (b.isHorizontal ? b.x + b.length : b.x + 1) * cellSize - 8;
            float bottom = (b.isHorizontal ? b.y + 1 : b.y + b.length) * cellSize - 8;
            
            RectF rect = new RectF(left, top, right, bottom);

            Bitmap bmpToDraw = null;
            if (b.isTarget) {
                bmpToDraw = bmpTarget;
            } else if (b.isHorizontal) {
                bmpToDraw = (b.length == 3) ? bmpWoodH3 : bmpWoodH2;
            } else {
                bmpToDraw = (b.length == 3) ? bmpWoodV3 : bmpWoodV2;
            }

            if (bmpToDraw != null) {
                canvas.drawBitmap(bmpToDraw, null, rect, paint);
            } else {
                // Fallback
                paint.setColor(b.color);
                canvas.drawRoundRect(rect, 20, 20, paint);
            }

            // Draw highlight for selected
            if (b == selectedBlock) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(8);
                paint.setColor(Color.YELLOW);
                canvas.drawRoundRect(rect, 20, 20, paint);
                paint.setStyle(Paint.Style.FILL);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (engine == null) return false;
        
        // If game is already over and they try a new touch, ignore
        if (engine.isGameOver() && event.getAction() == MotionEvent.ACTION_DOWN) {
            return false;
        }

        float x = event.getX();
        float y = event.getY();

        int gridX = (int) (x / cellSize);
        int gridY = (int) (y / cellSize);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                selectedBlock = engine.getBlockAt(gridX, gridY);
                lastTouchX = x;
                lastTouchY = y;
                invalidate();
                return selectedBlock != null;

            case MotionEvent.ACTION_MOVE:
                if (selectedBlock != null && !engine.isGameOver()) {
                    float dx = x - lastTouchX;
                    float dy = y - lastTouchY;

                    if (selectedBlock.isHorizontal) {
                        if (Math.abs(dx) >= cellSize) {
                            int direction = dx > 0 ? 1 : -1;
                            if (engine.moveBlock(selectedBlock, selectedBlock.x + direction, selectedBlock.y)) {
                                lastTouchX += direction * cellSize;
                                if (listener != null) listener.onMove();
                                if (engine.isGameOver() && listener != null) {
                                    listener.onWin();
                                    selectedBlock = null;
                                }
                            }
                        }
                    } else {
                        if (Math.abs(dy) >= cellSize) {
                            int direction = dy > 0 ? 1 : -1;
                            if (engine.moveBlock(selectedBlock, selectedBlock.x, selectedBlock.y + direction)) {
                                lastTouchY += direction * cellSize;
                                if (listener != null) listener.onMove();
                                if (engine.isGameOver() && listener != null) {
                                    listener.onWin();
                                    selectedBlock = null;
                                }
                            }
                        }
                    }
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                selectedBlock = null;
                invalidate();
                break;
        }
        return true;
    }
}
