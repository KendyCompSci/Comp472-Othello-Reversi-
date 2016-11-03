/**
 * Created by Kendy on 2016-10-29.
 */

import java.awt.*;
import java.util.*;

public class Othello {

    private Square othelloBoard[][] = new Square[8][8];
    private int blackCircles, whiteCircles;
    private char player;
    private int evaluation;
    private Move lastMove;
    private char previous = 'W';
    private char now = 'B';
    public Runtime runtime;
    public int depth;

    public Othello(int x, int y, int w, int h, int depth){
        int tempX = x;
        for(int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                othelloBoard[j][i] = new Square(x, y, w, h, i, j);
                x = x + w;
            }
            y = y + h;
            x = tempX;
            runtime = Runtime.getRuntime();
        }
        lastMove = new Move();
        setRules('B');
        this.depth = depth;
        runtime.gc();

            }
        }
    }

}
