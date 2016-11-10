/**
 * Created by Kendy on 2016-10-29.
 * AI class that provide min max, alpha beta agent
 */


import java.awt.Point;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class state
{       //Attributes
        private final int MAX = Integer.MAX_VALUE;
        private final int MIN = Integer.MIN_VALUE;
        private int maxDepth;
        Runtime runtime;

        //Constructor
        public state(int maximumDepth)
        {
            this.maxDepth = maximumDepth;
            runtime = Runtime.getRuntime();
            //call garbage collector to clean memory
            runtime.gc();
        }

         public Move alpha_beta(Othello o)
        {
            Move m = max_Value(new Othello(o), MIN, MAX, 0);
            runtime.gc();
            return m;
        }
    //will return the MAX value for the minmax agent
    private Move max_Value(Othello o,int a,int b,int depth)
    {
        if((o.isTerminal())||(depth==maxDepth))
        {
            Move lastMove=new Move((int)o.getLastMove().getX(),(int)o.getLastMove().getY(),o.evaluate());
            runtime.gc();
            return lastMove;
        }
        ArrayList<Othello> children =o.getChildren('W');
        Move maxMove=new Move(MIN);
        for(Othello child : children)
        {
            Move move=min_Value(child,a,b,depth+1);
            if(move.getValue()>=maxMove.getValue())
            {
                if(move.getValue()>=b){return move;}
                maxMove.setX((int)child.getLastMove().getX());
                maxMove.setY((int)child.getLastMove().getY());
                maxMove.setValue(move.getValue());
                child=null;
                runtime.gc();
            }
            a=Math.max(a,move.getValue());
        }
        return maxMove;

    }

    //will return the MIN value for the minmax agent
    private Move min_Value(Othello o,int a,int b,int depth)
    {
        if((o.isTerminal())||(depth==maxDepth))
        {
            Move lastMove=new Move((int)o.getLastMove().getX(),(int)o.getLastMove().getY(),o.evaluate());
            runtime.gc();
            return lastMove;
        }
        ArrayList<Othello> children =o.getChildren('B');
        Move minMove=new Move(MAX);
        for(Othello child : children)
        {
            Move move=max_Value(child,a,b,depth+1);
            if(move.getValue()<=minMove.getValue())
            {
                if(move.getValue()<=a) {return move;}
                minMove.setX((int)child.getLastMove().getX());
                minMove.setY((int)child.getLastMove().getY());
                minMove.setValue(move.getValue());
                child=null;
                runtime.gc();
            }
            b=Math.min(b,move.getValue());
        }
        return minMove;

    }


    }
