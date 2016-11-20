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
    public char now = 'B';
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
    public Othello (Othello ot) {
        Runtime runtime = Runtime.getRuntime();
        for (int i =  0; i < 8; i++) {
            for (int j = 0; j < 8; j++ ) {
             Vector<Point> v = new Vector<Point>();
                this.othelloBoard[i][j] = new Square(ot.getSquare(i, j).getX(),ot.getSquare(i, j).getY(), ot.getSquare(i, j).getW(), ot.getSquare(i, j).getH(), i, j);
                this.othelloBoard[i][j].setAdd_circle(ot.getSquare(i, j).getAdd_circle());
                this.othelloBoard[i][j].setCircle(ot.getSquare(i, j).getCircle());
                this.othelloBoard[i][j].setLabel(ot.getSquare(i, j).getLabel());
                for(int k = 0; k < ot.getSquare(i, j).getChangesSize(); k++) {
                    v.add(ot.getSquare(i, j).getChange(k));
                    this.othelloBoard[i][j].addChange(v);
                }
            }
        }
        this.lastMove = new Move(ot.getLastMove().getX(),ot.getLastMove().getY(), ot.getLastMove().getValue());
        runtime.gc(); //garbage collector
    }

    public Square getSquare(int i, int j) {
        return othelloBoard[i][j];
    }

    //Give us the Black circle count
     public void setBlackCircles() {
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (othelloBoard[i][j].getCircle() == 'B')
                    counter++;
            }
        }
        blackCircles = counter;
    }
    //Give us the white circle count
    public void setWhiteCircles() {
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (othelloBoard[i][j].getCircle() == 'W')
                    counter++;
            }
        }
        whiteCircles = counter;
    }

    public int getBlackCircles() {
        return this.blackCircles;
    }

    public int getWhiteCircles() {
        return this.whiteCircles;
    }

    public ArrayList<Othello> getChildren(char player) {
        ArrayList<Othello> children = new ArrayList<Othello>();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (this.othelloBoard[y][x].getAdd_circle()) {
                    Othello child = new Othello(this); //This Othello instance
                    child.makeMove(y, x, player);
                    child.lastMove = new Move(y, x);
                    if (player == 'B') {
                        child.setRules('W');
                    } else {
                        child.setRules('B');
                    }
                    children.add(child);
                }
            }
        }
        return children;
    }

    public void setRules(char player) { //Player or computer
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(othelloBoard[i][j].getCircle() == 'N')) {
                    othelloBoard[i][j].setAdd_circle(false); // If there is a cycle can not put another
                } else {
                    othelloBoard[i][j].setAdd_circle(lookVertically(new Point(i, j), player) | lookHorizontally(new Point(i, j), player) | lookDiagonally(new Point(i, j), player)); //binary or
                }
            }

        }

    }

    private boolean lookHorizontally(Point square1, char player) {
        return (lookRight(square1, player) | lookLeft(square1, player));
    }

    private boolean lookRight(Point square1, char player){
        int x = (int) square1.getX();
        int y = (int) square1.getY();
        int next = y + 1;       //square to the right
        Vector<Point> v = new Vector<Point>(); //to store the change
        boolean add = false;
        if (y == 7) {
            return false;
        }
        if (player == 'B') { //If its Black move
            if ((next <= 7) && (othelloBoard[x][next].getCircle() == 'B')) {
                return false;
            }
            if ((next <= 7) && (othelloBoard[x][next].getCircle() == 'W')) {
                v.add(new Point(x, next));
                for (int i = (++next); i <= 7; i++) {
                    if (add) {
                        break;
                    }
                    if (othelloBoard[x][i].getCircle() == 'W') {
                        v.add(new Point(x, i));
                    } else if (othelloBoard[x][i].getCircle() == 'B') {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        if (player == 'W') { //If its white move
            if ((next <= 7) && (othelloBoard[next][y].getCircle() == 'W')) {
                return false;
            }
            if ((next <= 7) && (othelloBoard[x][next].getCircle() == 'B')) {
                v.add(new Point(x, next));
                for (int i = (++next); i <= 7; i++) {
                    if (add) {
                        break;
                    }
                    if (othelloBoard[x][i].getCircle() == 'B') {
                        v.add(new Point(x, i));
                    } else if (othelloBoard[x][i].getCircle() == 'W') {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean lookLeft(Point square1, char player) {
        int x = (int) square1.getX();
        int y = (int) square1.getY();
        int previous = y - 1;        //square to the right
        Vector<Point> v = new Vector<Point>(); //to store the change
        boolean add = false;
        if (y == 0) {
            return false;
        }
        if (player == 'B') {
            if ((previous >= 0) && (othelloBoard[x][previous].getCircle() == 'B')) {
                return false;
            }
            if ((previous >= 0) && (othelloBoard[x][previous].getCircle() == 'W')) {
                v.add(new Point(x, previous));
                for (int i = (--previous); i >= 0; i--) {
                    if (add) {
                        break;
                    }
                    if (othelloBoard[x][i].getCircle() == 'W') {
                        v.add(new Point(x, i));
                    } else if (othelloBoard[x][i].getCircle() == 'B') {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        if (player == 'W') {
            if ((previous >= 0) && (othelloBoard[x][previous].getCircle() == 'W')) {
                return false;
            }
            if ((previous >= 0) && (othelloBoard[x][previous].getCircle() == 'B')) {
                v.add(new Point(x, previous));
                for (int i = (--previous); i >= 0; i--) {
                    if (add) {
                        break;
                    }
                    if (othelloBoard[x][i].getCircle() == 'B') {
                        v.add(new Point(x, i));
                    } else if (othelloBoard[x][i].getCircle() == 'W') {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean lookVertically(Point square1, char player) {
        return (lookDown(square1, player)) | (lookUp(square1, player));
    }

    private boolean lookDown(Point square1, char player) {
        int x = (int) square1.getX();
        int y = (int) square1.getY();
        int next = x + 1;       //square below
        Vector<Point> v = new Vector<Point>(); //vector to store the point
        boolean add = false;
        if (x == 7) {
            return false;
        }
        if (player == 'B') {
            if ((next <= 7) && (othelloBoard[next][y].getCircle() == 'B')) {
                return false;
            }
            if ((next <= 7) && (othelloBoard[next][y].getCircle() == 'W')) {
                v.add(new Point(next, y));
                for (int i = (++next); i <= 7; i++) {
                    if (add) {
                        break;
                    }
                    if (othelloBoard[i][y].getCircle() == 'W') {
                        v.add(new Point(i, y));
                    } else if (othelloBoard[i][y].getCircle() == 'B') {
                        add = true;
                    } else {
                        return false;
                    }

                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            }
            return false;

        }
        if (player == 'W') {
            if ((next <= 7) && (othelloBoard[next][y].getCircle() == 'W')) {
                return false;
            }
            if ((next <= 7) && (othelloBoard[next][y].getCircle() == 'B')) {
                v.add(new Point(next, y));
                for (int i = (++next); i <= 7; i++) {
                    if (add) {
                        break;
                    }
                    if (othelloBoard[i][y].getCircle() == 'B') {
                        v.add(new Point(i, y));
                    } else if (othelloBoard[i][y].getCircle() == 'W') {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }

        }
        return false;
    }

    private boolean lookUp(Point square1, char player) {
        int x = (int) square1.getX();
        int y = (int) square1.getY();
        int previous = x - 1;    //square above
        Vector<Point> v = new Vector<Point>(); //vector to store the point
        boolean add = false;
        if (x == 0) {
            return false;
        }
        if (player == 'B') {
            if ((previous <= 7) && (othelloBoard[previous][y].getCircle() == 'B')) {
                return false;
            }
            if ((previous >= 0) && (othelloBoard[previous][y].getCircle() == 'W')) {
                v.add(new Point(previous, y));
                for (int i = (--previous); i >= 0; i--) {
                    if (add) {
                        break;
                    }
                    if (othelloBoard[i][y].getCircle() == 'W') {
                        v.add(new Point(i, y));
                    } else if (othelloBoard[i][y].getCircle() == 'B') {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        if (player == 'W') {
            if ((previous <= 7) && (othelloBoard[previous][y].getCircle() == 'W')) {
                return false;
            }
            if ((previous >= 0) && (othelloBoard[previous][y].getCircle() == 'B')) {
                v.add(new Point(previous, y));
                for (int i = (--previous); i >= 0; i--) {
                    if (add) {
                        break;
                    }
                    if (othelloBoard[i][y].getCircle() == 'B') {
                        v.add(new Point(i, y));
                    } else if (othelloBoard[i][y].getCircle() == 'W') {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean lookDiagonally(Point square1, char player) {
        return (lookDiagonalUpLeft(square1, player) | lookDiagonalDownLeft(square1, player)| lookDiagonalUpRight(square1, player) | lookDiagonalDownRight(square1, player));
    }

    private boolean lookDiagonalDownLeft(Point square1, char player) {
        int x = (int) square1.getX();
        int y = (int) square1.getY();
        int nextX = x + 1;       //Square at DownLeft
        int nextY = y + 1;
        Vector<Point> v = new Vector<Point>(); //Store changes
        boolean add = false;
        if (x == 7) {
            return false;
        }
        if (y == 7) {
            return false;
        }
        if (player == 'B') {
            if ((nextX <= 7) && (nextY <= 7) && (othelloBoard[nextX][nextY].getCircle() == 'B')) {
                return false;
            }
            if ((nextX <= 7) && (nextY <= 7) && (othelloBoard[nextX][nextY].getCircle() == 'W')) {
                v.add(new Point(nextX, nextY));
                for (int i = (++nextX); i <= 7; i++) {
                    if (add) {
                        break;
                    }
                    nextY++;
                    if ((nextY <= 7) && (othelloBoard[i][nextY].getCircle() == 'W')) {
                        v.add(new Point(i, nextY));
                    } else if ((nextY <= 7) && (othelloBoard[i][nextY].getCircle() == 'B')) {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        if (player == 'W') {
            if ((nextX <= 7) && (nextY <= 7) && (othelloBoard[nextX][nextY].getCircle() == 'W')) {
                return false;
            }
            if ((nextX <= 7) && (nextY <= 7) && (othelloBoard[nextX][nextY].getCircle() == 'B')) {
                v.add(new Point(nextX, nextY));
                for (int i = (++nextX); i <= 7; i++) {
                    if (add) {
                        break;
                    }
                    nextY++;
                    if ((nextY <= 7) && (othelloBoard[i][nextY].getCircle() == 'B')) {
                        v.add(new Point(i, nextY));
                    } else if ((nextY <= 7) && (othelloBoard[i][nextY].getCircle() == 'W')) {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean lookDiagonalUpLeft(Point square1, char player) {
        int x = (int) square1.getX();
        int y = (int) square1.getY();
        int previousX = x - 1;       //UpLeft
        int previousY = y - 1;
        Vector<Point> v = new Vector<Point>(); //Store the changes
        boolean add = false;
        if (x == 0) {
            return false;
        }
        if (y == 0) {
            return false;
        }
        if (player == 'B') {
            if ((previousX >= 0) && (previousY >= 0) && (othelloBoard[previousX][previousY].getCircle() == 'B')) {
                return false;
            }
            if ((previousX >= 0) && (previousY >= 0) && (othelloBoard[previousX][previousY].getCircle() == 'W')) {
                v.add(new Point(previousX, previousY));
                for (int i = (--previousX); i >= 0; i--) {
                    if (add) {
                        break;
                    }
                    previousY--;
                    if ((previousY >= 0) && (othelloBoard[i][previousY].getCircle() == 'W')) {
                        v.add(new Point(i, previousY));
                    } else if ((previousY >= 0) && (othelloBoard[i][previousY].getCircle() == 'B')) {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        if (player == 'W') {
            if ((previousX >= 0) && (previousY >= 0) && (othelloBoard[previousX][previousY].getCircle() == 'W')) {
                return false;
            }
            if ((previousX >= 0) && (previousY >= 0) && (othelloBoard[previousX][previousY].getCircle() == 'B')) {
                v.add(new Point(previousX, previousY));
                for (int i = (--previousX); i >= 0; i--) {
                    if (add) {
                        break;
                    }
                    previousY--;
                    if ((previousY >= 0) && (othelloBoard[i][previousY].getCircle() == 'B')) {
                        v.add(new Point(i, previousY));
                    } else if ((previousY >= 0) && (othelloBoard[i][previousY].getCircle() == 'W')) {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean lookDiagonalDownRight(Point square1, char player) {
        int x = (int) square1.getX();
        int y = (int) square1.getY();
        int nextX = x + 1;       //DownRight
        int nextY = y - 1;
        Vector<Point> v = new Vector<Point>(); //Store change
        boolean add = false;
        if (x == 7) {
            return false;
        }
        if (y == 0) {
            return false;
        }
        if (player == 'B') {
            if ((nextX <= 7) && (nextY >= 0) && (othelloBoard[nextX][nextY].getCircle() == 'B')) {
                return false;
            }
            if ((nextX <= 7) && (nextY >= 0) && (othelloBoard[nextX][nextY].getCircle() == 'W')) {
                v.add(new Point(nextX, nextY));
                for (int i = (++nextX); i <= 7; i++) {
                    if (add) {
                        break;
                    }
                    nextY--;
                    if ((nextY >= 0) && (othelloBoard[i][nextY].getCircle() == 'W')) {
                        v.add(new Point(i, nextY));
                    } else if ((nextY >= 0) && (othelloBoard[i][nextY].getCircle() == 'B')) {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        if (player == 'W') {
            if ((nextX <= 7) && (nextY >= 0) && (othelloBoard[nextX][nextY].getCircle() == 'W')) {
                return false;
            }
            if ((nextX <= 7) && (nextY >= 0) && (othelloBoard[nextX][nextY].getCircle() == 'B')) {
                v.add(new Point(nextX, nextY));
                for (int i = (++nextX); i <= 7; i++) {
                    if (add) {
                        break;
                    }
                    nextY--;
                    if ((nextY >= 0) && (othelloBoard[i][nextY].getCircle() == 'B')) {
                        v.add(new Point(i, nextY));
                    } else if ((nextY >= 0) && (othelloBoard[i][nextY].getCircle() == 'W')) {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }

        }
        return false;
    }

    private boolean lookDiagonalUpRight(Point square1, char player) {
        int x = (int) square1.getX();
        int y = (int) square1.getY();
        int previousX = x - 1;       //Upright
        int previousY = y + 1;
        Vector<Point> v = new Vector<Point>(); //To store change
        boolean add = false;
        if (x == 0) {
            return false;
        }
        if (y == 7) {
            return false;
        }
        if (player == 'B') {
            if ((previousX >= 0) && (previousY <= 7) && (othelloBoard[previousX][previousY].getCircle() == 'B')) {
                return false;
            }
            if ((previousX >= 0) && (previousY <= 7) && (othelloBoard[previousX][previousY].getCircle() == 'W')) {
                v.add(new Point(previousX, previousY));
                for (int i = (--previousX); i >= 0; i--) {
                    if (add) {
                        break;
                    }
                    previousY++;
                    if ((previousY <= 7) && (othelloBoard[i][previousY].getCircle() == 'W')) {
                        v.add(new Point(i, previousY));
                    } else if ((previousY <= 7) && (othelloBoard[i][previousY].getCircle() == 'B')) {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        if (player == 'W') {
            if ((previousX >= 0) && (previousY <= 7) && (othelloBoard[previousX][previousY].getCircle() == 'W')) {
                return false;
            }
            if ((previousX >= 0) && (previousY <= 7) && (othelloBoard[previousX][previousY].getCircle() == 'B')) {
                v.add(new Point(previousX, previousY));
                for (int i = (--previousX); i >= 0; i--) {
                    if (add) {
                        break;
                    }
                    previousY++;
                    if ((previousY <= 7) && (othelloBoard[i][previousY].getCircle() == 'B')) {
                        v.add(new Point(i, previousY));
                    } else if ((previousY <= 7) && (othelloBoard[i][previousY].getCircle() == 'W')) {
                        add = true;
                    } else {
                        return false;
                    }
                }
                if (add) {
                    othelloBoard[x][y].addChange(v);
                    return true;
                }
                return false;
            } else {
                return false;
            }
        }
        return false;
    }

    public void makeMove(int x, int y, char player) {
        if (player == 'B') {
            this.othelloBoard[x][y].setCircle('B');
            this.lastMove.setX(y);
            this.lastMove.setY(x);
            for (int i = 0; i < this.othelloBoard[x][y].getChangesSize(); i++) {
                this.othelloBoard[(int) this.othelloBoard[x][y].getChange(i).getX()][(int) this.othelloBoard[x][y].getChange(i).getY()].setCircle('B');
            }
            setRules('W');
        } else if (player == 'W') {
            this.othelloBoard[x][y].setCircle('W');
            for (int i = 0; i < othelloBoard[x][y].getChangesSize(); i++) {
                this.othelloBoard[(int) othelloBoard[x][y].getChange(i).getX()][(int) othelloBoard[x][y].getChange(i).getY()].setCircle('W');
            }

            setRules('B');
        }

    }
    //Get the player
    public char getPlayer(){
        return now;
    }
    //Set the player
    public void setPlayer() {
        char help;
        setRules(previous);
        if (previous == now) {
            if (isTerminal()) {
                now = ' ';
            } else {
                help = now;
                now = previous;
                previous = help;
            }
        } else if (isTerminal()) {
            previous = now;
        } else {
            help = now;
            now = previous;
            previous = help;
        }
        player = now;
        if (now == 'W') {
            state state1 = new state(depth);
            Move m = state1.alpha_beta(this);
            setRules('W');
            this.makeMove(m.getX(), m.getY(), 'W');
            state1 = null;
            runtime.gc();
            setRules('B');
            setPlayer();

        }

    }

    private int getMovesNumber() {
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (othelloBoard[i][j].getAdd_circle()) {
                    counter++;
                }
            }
        }
        return counter;

    }

    public boolean isTerminal() {
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if((othelloBoard[i][j].getCircle() == 'B') || (this.othelloBoard[i][j].getCircle() == 'W')) {
                    counter++;
                }
            }
        }
        if (counter == 64) {//Number of Maximum tiles
            return true;
        } else {
            return false;
        }

    }

    public int evaluate() {
        this.setBlackCircles();
        this.setWhiteCircles();

        int eval1 = this.getBlackCircles() - this.getWhiteCircles();

        int eval2 = 0;
        int eval3 = 0;
        int sth = 0;
        boolean f = true;
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                if (this.getSquare(j, i).getCircle() == 'S') {
                    if (getPlayer() == 'B') {
                        eval2 = -10;
                    } else {
                        eval2 = 10;
                    }
                } else if (this.getSquare(j, i).getLabel() == 'X') {
                    if (getPlayer() == 'B') {
                        eval2 = 10;
                    } else {
                        eval2 = -10;
                    }
                } else if (this.getSquare(j, i).getLabel() == 'C') {
                    if (getPlayer() == 'B') {
                        eval2 = 7;
                    } else {
                        eval2 = -7;
                    }
                } else if (this.getSquare(j, i).getLabel() == 'B') {
                    if (getPlayer() == 'B') {
                        eval2 = -5;
                    } else {
                        eval2 = 5;
                    }
                } else if (this.getSquare(j, i).getLabel() == 'A') {
                    if (getPlayer() == 'B') {
                        eval2 = 7;
                    } else {
                        eval2 = -7;
                    }
                }
                if ((i > 0)) {
                    if (this.getSquare(j, i - 1).getCircle() == 'B') {
                        eval3 = -10;
                    }
                } else if ((i < 7)) {
                    if (this.getSquare(j, i + 1).getCircle() == 'B') {
                        eval3 = -10;
                    }
                }
                if (f) {
                    if ((j > 0)) {
                        if (this.getSquare(j - 1, sth).getCircle() == 'B') {
                            eval3 = -10;
                        }
                    } else if ((j < 7)) {
                        if (this.getSquare(j + 1, sth).getCircle() == 'B') {
                            eval3 = -10;
                        }
                    }
                    f = false;
                }

                f = true;


            }
        }

        return (10 * eval1 / 100 + 40 * eval2 / 100 + 50 * eval3 / 100);

    }

    public void setEvaluation(int e){
        this.evaluation = e;
    }

    public int getEvaluation(){
        return this.evaluation;
    }

    public ArrayList<Point> getMoves(){
        ArrayList<Point> p = new ArrayList<Point>();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(this.othelloBoard[i][j].getAdd_circle()) {
                    p.add(new Point(i, j));
                }
            }
        }
        return p;
    }

    //return the player last move
    public Move getLastMove(){
        return this.lastMove;
    }

}