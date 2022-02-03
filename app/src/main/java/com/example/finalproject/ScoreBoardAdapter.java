package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ScoreBoardAdapter extends RecyclerView.Adapter<ScoreBoardAdapter.ViewHolder>{
    private Context context;
    private String FILENAME;
    private String[] Scores;
    private String ScoreEmpty;
    private ArrayList<Integer> array;
    private ArrayList<Integer> sortArray;

    public ScoreBoardAdapter(Context context) {
        String check;
        this.context = context;
        FILENAME = context.getResources().getString(R.string.score_save);
        ScoreEmpty = ReadFromFile();
        if(ScoreEmpty.equals(""))
            return;
        Scores = ScoreEmpty.split("\n");


        array = Calculate.ScoreByHigh(Scores);
        sortArray = Calculate.ScoreByHigh(Scores);
        Collections.sort(sortArray);
        Log.i("ScoreBoardAdapter", sortArray.toString());

    }

    @NonNull
    @Override
    public ScoreBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.table_row, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreBoardAdapter.ViewHolder holder, int position) {
        holder.fillData(position);
    }

    @Override
    public int getItemCount() {
        if(ScoreEmpty.equals(""))
            return 0;
        return Scores.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView TV_Boars_Size;
        private TextView TV_Move;
        private TextView TV_Time;
        private TextView TV_Score;
        private TextView TV_Difficulty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TV_Boars_Size= itemView.findViewById(R.id.TV_Board_Size);
            TV_Move = itemView.findViewById(R.id.TV_Moves);
            TV_Time = itemView.findViewById(R.id.TV_Time);
            TV_Score = itemView.findViewById(R.id.TV_Score);
            TV_Difficulty = itemView.findViewById(R.id.TV_Difficulty);
        }

        public void fillData(int position) {
            if(Scores.length == 0)
                return;
            int value = sortArray.get(sortArray.size()-1);
            int index = array.indexOf(value);
            sortArray.remove(sortArray.size()-1);


            Log.i("ScoreBoardAdapter", "sort array after remove"+ sortArray.toString());
            Log.i("ScoreBoardAdapter", "Value "+value);
            Log.i("ScoreBoardAdapter", "index "+index);

            String score[] = Scores[index].split(" ");
            int row = Integer.valueOf(score[0]);
            int col = Integer.valueOf(score[1]);
            int move = Integer.valueOf(score[2]);
            int time = Integer.valueOf(score[3]);
            CharSequence  difficulty = score[4];

            Log.i("ScoreBoardAdapter", "row "+ row);
            Log.i("ScoreBoardAdapter", "col "+col);
            Log.i("ScoreBoardAdapter", "move "+move);
            Log.i("ScoreBoardAdapter", "time "+time);
            Log.i("ScoreBoardAdapter", "difficulty "+difficulty);

            TV_Boars_Size.setText(score[0]+"X"+score[1]);
            TV_Move.setText(score[2]);
            TV_Time.setText(score[3]);
            TV_Score.setText(String.valueOf(value));
            TV_Difficulty.setText(difficulty);
        }
    }

    public String ReadFromFile(){
        try {
            FileInputStream fin = context.openFileInput( FILENAME );
            ByteBuffer bf = ByteBuffer.allocate(1000*10);
            int numberOfBytes = fin.read(bf.array());
            fin.close();
            if(numberOfBytes != -1) {
                String convert = new String(bf.array());
                convert = convert.substring(0, numberOfBytes);
                return convert;
            }
            return "";

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
