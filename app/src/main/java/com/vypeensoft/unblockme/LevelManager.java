package com.vypeensoft.unblockme;

import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    public static List<Level> getLevels() {
        List<Level> levels = new ArrayList<>();

        // Level 1
        Level l1 = new Level(1);
        l1.addBlock(new Block(0, 0, 2, 2, true, Color.RED, true)); // Target
        l1.addBlock(new Block(1, 2, 0, 3, false, Color.GRAY, false));
        l1.addBlock(new Block(2, 3, 1, 2, true, Color.LTGRAY, false));
        l1.addBlock(new Block(3, 5, 2, 3, false, Color.GRAY, false));
        l1.addBlock(new Block(4, 0, 4, 3, true, Color.LTGRAY, false));
        levels.add(l1);

        // Level 2
        Level l2 = new Level(2);
        l2.addBlock(new Block(0, 1, 2, 2, true, Color.RED, true));
        l2.addBlock(new Block(1, 0, 0, 2, true, Color.LTGRAY, false));
        l2.addBlock(new Block(2, 0, 1, 3, false, Color.GRAY, false));
        l2.addBlock(new Block(3, 3, 0, 3, false, Color.GRAY, false));
        l2.addBlock(new Block(4, 4, 1, 2, true, Color.LTGRAY, false));
        l2.addBlock(new Block(5, 5, 2, 3, false, Color.GRAY, false));
        levels.add(l2);

        // Level 3
        Level l3 = new Level(3);
        l3.addBlock(new Block(0, 0, 2, 2, true, Color.RED, true));
        l3.addBlock(new Block(1, 2, 2, 2, false, Color.LTGRAY, false));
        l3.addBlock(new Block(2, 3, 2, 2, true, Color.LTGRAY, false));
        l3.addBlock(new Block(3, 5, 0, 3, false, Color.GRAY, false));
        l3.addBlock(new Block(4, 0, 0, 3, true, Color.LTGRAY, false));
        l3.addBlock(new Block(5, 4, 4, 2, false, Color.LTGRAY, false));
        levels.add(l3);

        // Level 4
        Level l4 = new Level(4);
        l4.addBlock(new Block(0, 2, 2, 2, true, Color.RED, true));
        l4.addBlock(new Block(1, 0, 0, 2, false, Color.LTGRAY, false));
        l4.addBlock(new Block(2, 1, 0, 3, false, Color.GRAY, false));
        l4.addBlock(new Block(3, 2, 0, 2, true, Color.LTGRAY, false));
        l4.addBlock(new Block(4, 4, 0, 2, true, Color.LTGRAY, false));
        l4.addBlock(new Block(5, 4, 1, 2, false, Color.LTGRAY, false));
        l4.addBlock(new Block(6, 0, 3, 2, true, Color.LTGRAY, false));
        levels.add(l4);

        // Level 5
        Level l5 = new Level(5);
        l5.addBlock(new Block(0, 0, 2, 2, true, Color.RED, true));
        l5.addBlock(new Block(1, 2, 0, 3, false, Color.GRAY, false));
        l5.addBlock(new Block(2, 0, 0, 2, true, Color.LTGRAY, false));
        l5.addBlock(new Block(3, 3, 1, 2, true, Color.LTGRAY, false));
        l5.addBlock(new Block(4, 5, 0, 3, false, Color.GRAY, false));
        l5.addBlock(new Block(5, 0, 4, 3, true, Color.GRAY, false));
        levels.add(l5);

        // Level 6
        Level l6 = new Level(6);
        l6.addBlock(new Block(0, 1, 2, 2, true, Color.RED, true));
        l6.addBlock(new Block(1, 0, 0, 3, false, Color.GRAY, false));
        l6.addBlock(new Block(2, 3, 0, 3, false, Color.GRAY, false));
        l6.addBlock(new Block(3, 4, 0, 2, true, Color.LTGRAY, false));
        l6.addBlock(new Block(4, 4, 1, 2, false, Color.LTGRAY, false));
        l6.addBlock(new Block(5, 0, 4, 2, true, Color.LTGRAY, false));
        levels.add(l6);

        // Level 7
        Level l7 = new Level(7);
        l7.addBlock(new Block(0, 0, 2, 2, true, Color.RED, true));
        l7.addBlock(new Block(1, 2, 2, 2, false, Color.LTGRAY, false));
        l7.addBlock(new Block(2, 3, 2, 2, true, Color.LTGRAY, false));
        l7.addBlock(new Block(3, 5, 1, 3, false, Color.GRAY, false));
        l7.addBlock(new Block(4, 0, 0, 2, true, Color.LTGRAY, false));
        l7.addBlock(new Block(5, 4, 4, 2, true, Color.LTGRAY, false));
        levels.add(l7);

        // Level 8
        Level l8 = new Level(8);
        l8.addBlock(new Block(0, 2, 2, 2, true, Color.RED, true));
        l8.addBlock(new Block(1, 1, 0, 3, false, Color.GRAY, false));
        l8.addBlock(new Block(2, 4, 0, 3, false, Color.GRAY, false));
        l8.addBlock(new Block(3, 0, 3, 2, true, Color.LTGRAY, false));
        l8.addBlock(new Block(4, 3, 3, 2, false, Color.LTGRAY, false));
        l8.addBlock(new Block(5, 0, 5, 3, true, Color.GRAY, false));
        levels.add(l8);

        // Level 9
        Level l9 = new Level(9);
        l9.addBlock(new Block(0, 0, 2, 2, true, Color.RED, true));
        l9.addBlock(new Block(1, 2, 0, 2, false, Color.LTGRAY, false));
        l9.addBlock(new Block(2, 3, 0, 3, false, Color.GRAY, false));
        l9.addBlock(new Block(3, 4, 0, 2, true, Color.LTGRAY, false));
        l9.addBlock(new Block(4, 0, 4, 2, true, Color.LTGRAY, false));
        l9.addBlock(new Block(5, 2, 4, 2, false, Color.LTGRAY, false));
        levels.add(l9);

        // Level 10
        Level l10 = new Level(10);
        l10.addBlock(new Block(0, 1, 2, 2, true, Color.RED, true));
        l10.addBlock(new Block(1, 0, 0, 2, false, Color.LTGRAY, false));
        l10.addBlock(new Block(2, 3, 0, 3, false, Color.GRAY, false));
        l10.addBlock(new Block(3, 4, 2, 2, true, Color.LTGRAY, false));
        l10.addBlock(new Block(4, 4, 3, 2, false, Color.LTGRAY, false));
        l10.addBlock(new Block(5, 1, 5, 3, true, Color.GRAY, false));
        levels.add(l10);

        return levels;
    }
}
