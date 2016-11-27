/**
 * Created by Kendy and Mathieu from (2016-10-29)-(2016-11-28)
 *
 * This class describe a move in the board.
 * We need to keep the moves as well as the states.
 */
public class Move {

    //Attributes of move class
    private int x;
    private int y;
    private int value;

    //Constructor
    public Move(){
        //initializing every component to -1 because x and y are not defined when we run the game.
        x = -1;
        y = -1;
        value = 0;
    }

    public Move(int x, int y)
    {
        //assigning the move value to each x and y.
        this.x = x;
        this.y = y;
        this.value = -1;
    }

    public Move(int value)
    {
        //assigning the move value to value
        this.x = -1;
        this.y = -1;
        this.value = value;
    }

    public Move(int x, int y, int value)
    {

        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
