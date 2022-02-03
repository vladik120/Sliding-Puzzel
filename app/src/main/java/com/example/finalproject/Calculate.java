package com.example.finalproject;

import android.util.Log;

import java.util.ArrayList;

public class Calculate {

    public static int ScoreCalc(int row,int col,int move,int time){
        double score = (1/(Math.sqrt(time)+Math.sqrt(move)))*(row*col*100);
        return (int)score;
    }

    public static ArrayList<Integer> ScoreByHigh(String[] Score){
        String[] data;
        ArrayList<Integer> arrlist = new ArrayList<Integer>(Score.length);
        Log.i("ScoreByHigh",Score.toString());
        int array[] = new int[Score.length];
        int row ;
        int col ;
        int move ;
        int time ;
        int SC ;
        for (int i = 0; i < Score.length; i++) {
            data = Score[i].split(" ");
            row = Integer.valueOf(data[0]);
            col = Integer.valueOf(data[1]);
            move = Integer.valueOf(data[2]);
            time = Integer.valueOf(data[3]);
            SC = Calculate.ScoreCalc(row,col,move,time);
            Log.i("ScoreByHigh","row "+ row+" col "+col+" move "+move+" time "+time+" score"+SC);
            arrlist.add(i,SC);
        }
        Log.i("ScoreByHigh",arrlist.toString());
        return arrlist;
    }
}
