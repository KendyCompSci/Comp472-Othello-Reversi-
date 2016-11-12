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
    char now = 'B';
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

	public Othello(Othello o) {
        for(int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                othelloBoard[j][i] = o.othelloBoard[j][i];
            }
            runtime = o.runtime;
        }
        lastMove = o.lastMove;
        blackCircles = o.getBlackcircles();
        whiteCircles = o.getWhitecircles();
        evaluation = o.evaluation;
        player = o.getPlayer();
        previous = o.previous;
        setRules(o.now);
        this.depth = o.depth;
        runtime.gc();
	}

	private void setRules(char c) {
		// TODO Auto-generated method stub
		
	}

	public void setBlackcircles() {
		// TODO Auto-generated method stub
		
	}

	public void setWhitecircles() {
		// TODO Auto-generated method stub
		
	}

	public int getBlackcircles() {
		// TODO Auto-generated method stub
		return blackCircles;
	}

	public int getWhitecircles() {
		// TODO Auto-generated method stub
		return whiteCircles;
	}

	public char getPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Square getSquare(int gety, int getx) {
		return othelloBoard[gety][getx];
	}

	public void makeMove(int gety, int getx, char player2) {
		// TODO Auto-generated method stub
		
	}

	public void setPlayer() {
		// TODO Auto-generated method stub
		
	}

	public boolean isTerminal() {
		// TODO Auto-generated method stub
		return false;
	}

	public Point getLastMove() {
		// TODO Auto-generated method stub
		return null;
	}

	public int evaluate() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ArrayList<Othello> getChildren(char c) {
		// TODO Auto-generated method stub
		return null;
	}

}
