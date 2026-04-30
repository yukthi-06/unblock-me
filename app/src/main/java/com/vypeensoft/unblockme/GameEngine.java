package com.vypeensoft.unblockme;

import java.util.List;

public class GameEngine {
    private List<Block> blocks;
    private int moves;
    private static final int GRID_SIZE = 6;
    private boolean isGameOver = false;

    public GameEngine(List<Block> blocks) {
        this.blocks = blocks;
        this.moves = 0;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public int getMoves() {
        return moves;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Block getBlockAt(int gridX, int gridY) {
        for (Block b : blocks) {
            if (b.contains(gridX, gridY)) return b;
        }
        return null;
    }

    public boolean canMoveTo(Block block, int newX, int newY) {
        if (newX < 0 || newY < 0) return false;
        if (block.isHorizontal) {
            if (newX + block.length > GRID_SIZE) return false;
            if (newY != block.y) return false;
        } else {
            if (newY + block.length > GRID_SIZE) return false;
            if (newX != block.x) return false;
        }

        // Check collision with other blocks
        for (Block other : blocks) {
            if (other == block) continue;
            if (block.isHorizontal) {
                for (int i = 0; i < block.length; i++) {
                    if (other.contains(newX + i, newY)) return false;
                }
            } else {
                for (int i = 0; i < block.length; i++) {
                    if (other.contains(newX, newY + i)) return false;
                }
            }
        }
        return true;
    }

    public boolean moveBlock(Block block, int newX, int newY) {
        if (block.x == newX && block.y == newY) return false;
        if (canMoveTo(block, newX, newY)) {
            block.x = newX;
            block.y = newY;
            moves++;
            checkWin();
            return true;
        }
        return false;
    }

    private void checkWin() {
        for (Block b : blocks) {
            if (b.isTarget) {
                if (b.x + b.length == GRID_SIZE) {
                    isGameOver = true;
                }
                break;
            }
        }
    }
}
