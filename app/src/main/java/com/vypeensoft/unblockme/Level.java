package com.vypeensoft.unblockme;

import java.util.ArrayList;
import java.util.List;

public class Level {
    public int levelNumber;
    public String difficulty;
    public int minimumMoves;
    public List<Block> blocks;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        this.blocks = new ArrayList<>();
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }
    
    public List<Block> getBlocksCopy() {
        List<Block> copy = new ArrayList<>();
        for (Block b : blocks) {
            copy.add(b.copy());
        }
        return copy;
    }
}
