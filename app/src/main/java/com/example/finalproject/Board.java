package com.example.finalproject;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;


public class Board {

    public static Cell[] GenerateBoard(int size) {
        Cell[] board = new Cell[size];
        int[] check = new int[size];
        Random rand = new Random();
        int blank = rand.nextInt(size);
        int number = -1;
        Arrays.fill(check, 0);

        /*for (int i = 0; i < size; i++) {
            if (i == blank)
                board[i] = null;
            else {
                do {
                    number = rand.nextInt(size - 1);
                } while (check[number] == 1);
                check[number] = 1;
                board[i] = new Cell(String.valueOf(number));

            }
        }*/

        for (int i = 0; i <= size-3; i++) {
            board[i] = new Cell(String.valueOf(i));
        }
        board[size-2] = null;
        board[size-1] = new Cell(String.valueOf(size-2));
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

    public static int ScoreCalc(int row,int col,int move,int time){
        double score = (1/(Math.sqrt(time)+Math.sqrt(move)))*(row*col*100);
        return (int)score;
    }
}
