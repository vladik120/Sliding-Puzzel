package com.example.finalproject;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;


public class Board {

    public static Cell[] GenerateBoard(int size, String difficulty) {
        Cell[] board = new Cell[size];
        int[] check = new int[size];
        Random rand = new Random();
        int blank = rand.nextInt(size);
        int numberRand =size;
        int count=0;
        int number = 0;
        int index = 0;
        int randomize=0;
        int t1,t2,t3;
        Cell temp1,temp2;
        String[] testArr= new String[size];
        for (int i =0; i<size;i++) {
            testArr[i]="-1";
        }
        String test="";
        Arrays.fill(check, 0);
        int dif ;
        switch (difficulty){
            case "Easy": //randomize two places in the board only
                for (int i = 0; i <= size-3; i++) {
                    board[i] = new Cell(String.valueOf(i));
                }
                board[size-2] = null;
                board[size-1] = new Cell(String.valueOf(size-2));
                t1 = rand.nextInt(size-1);
                temp1 = board[t1];
                board[t1]=board[size-1-t1];
                board[size-1-t1]=temp1;
                break;
            case "Normal": //randomize four places in the board
                for (int i = 0; i <= size-3; i++) {
                    board[i] = new Cell(String.valueOf(i));
                }
                board[size-2] = null;
                board[size-1] = new Cell(String.valueOf(size-2));
                t1 = rand.nextInt(size-1);
                temp1 = board[t1];
                board[t1]=board[size-1-t1];
                board[size-1-t1]=temp1;
                t2 = rand.nextInt(size-1);
                temp2 = board[t2];
                board[t2]=board[size-1-t2];
                board[size-1-t2]=temp2;
                break;
            case "Hard": //randomize all the board
                //make the numbers random on the board, and place null in the bottomright side of the grid.
                board[size-1]=null;
                for (int i=0; i<size-1; i++) {
                    boolean found = false;
                    int r=0;
                    do {
                        found=false;
                        r = rand.nextInt(size-1);
                        for (int j=0; j<size-1; j++) {
                            if (testArr[j]== String.valueOf(r)) {
                                found = true;
                                break;
                            }
                        }
                    }
                    while (found);
                    testArr[i] = String.valueOf(r);
                    board[i]=new Cell(testArr[i]);
                }
                break;
        }

        return board;
    }

    public static boolean checkBoard(Cell[] board, int size) {
        int check[] = new int[size];
        int number;
        Arrays.fill(check,0);
        for (int i = 0; i < size-1; i++) {
            if(board[i] != null){
                number = Integer.parseInt(board[i].getNumber());
                if (number != i)
                    return false;
            }else return false;
        }
        return true;
    }

    public static int checkMove(int position, int row, int col, Cell[] board) {
        int left = position - 1;
        int right = position + 1;
        int top = position + row;
        int bot = position - row;
        int rowpos = position / row;

        if (top <= row * col - 1)
            if (board[top] == null)
                return top;
        if (bot >= 0)
            if (board[bot] == null)
                return bot;

        if (left >= rowpos * row) {
            if (board[left] == null)
                return left;
        }
        if (right <= (rowpos + 1) * row) {
            if (board[right] == null)
                return right;
        }
        return -1;
    }

    public static void ShowBoardStatus(Cell[] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == null)
                Log.i("ShowBoardStatus - ", "position " + i + " is Null.");
            else {
                Log.i("ShowBoardStatus - ", "position " + i + " is " + board[i].getNumber());
            }
        }
        Log.i("ShowBoardStatus - ", "end.");

    }

}
