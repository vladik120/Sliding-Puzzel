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
import java.util.Arrays;

public class ScoreBoardAdapter extends RecyclerView.Adapter<ScoreBoardAdapter.ViewHolder>{
    private Context context;
    private String FILENAME;
    private String[] Scores;

    public ScoreBoardAdapter(Context context) {
        this.context = context;
        FILENAME = context.getResources().getString(R.string.score_save);
        Scores = ReadFromFile().split("\n");
        Log.i("ScoreBoardAdapter", Arrays.toString(Scores) + Scores.length);
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
        Log.i("onBindViewHolder","position "+position);
        holder.fillData(position);
    }

    @Override
    public int getItemCount() {
        return Scores.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView TV_Boars_Size;
        private TextView TV_Move;
        private TextView TV_Time;
        private TextView TV_Score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TV_Boars_Size= itemView.findViewById(R.id.TV_Board_Size);
            TV_Move = itemView.findViewById(R.id.TV_Moves);
            TV_Time = itemView.findViewById(R.id.TV_Time);
            TV_Score = itemView.findViewById(R.id.TV_Score);
        }

        public void fillData(int position) {
            Log.i("fillData","position "+position);
            String score[] = Scores[position].split(" ");
            int row = Integer.valueOf(score[0]);
            int col = Integer.valueOf(score[1]);
            int move = Integer.valueOf(score[2]);
            int time = Integer.valueOf(score[3]);
            int SC = Board.ScoreCalc(row,col,move,time);
            Log.i("fillData",position+Arrays.toString(score));
            TV_Boars_Size.setText(row+"X"+col);
            TV_Move.setText(move);
            TV_Time.setText(time);
            TV_Score.setText(SC);
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
