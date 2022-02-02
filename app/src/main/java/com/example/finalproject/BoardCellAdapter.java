package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BoardCellAdapter extends RecyclerView.Adapter<BoardCellAdapter.ViewHolder> {
    private Cell[] board;
    private Context context;
    private int size;
    private int row;
    private int col;
    private BoardCellAdapter.BoardCellAdapterListener listener;
    private int MoveCount = 0;
    private Long Start;
    private Long End;

    public BoardCellAdapter(Context context) {
        Start =  System.currentTimeMillis();
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.preference_file_key), context.MODE_PRIVATE);
        row = Integer.parseInt(sharedPref.getString("board_row", "4"));
        col = Integer.parseInt(sharedPref.getString("board_col", "4"));
        size = row*col;
        board = Board.GenerateBoard(size);
        this.context = context;
        listener = (BoardCellAdapterListener)context;
        Board.ShowBoardStatus(board);
    }

    @NonNull
    @Override
    public BoardCellAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.fragment_frag_cell, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoardCellAdapter.ViewHolder holder, int position) {
        holder.fillData(position);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button BTN_Cell;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            BTN_Cell = itemView.findViewById(R.id.BTN_Cell);
        }

        public void fillData(int position){
            Cell cell = board[position];
            int number ;
            if(cell == null){
                BTN_Cell.setText("");
            }else {
                number = Integer.parseInt(cell.getNumber());
                BTN_Cell.setText(String.valueOf(number+1));
            }

            BTN_Cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int move;
                    if(cell == null)
                        return;
                    if((move = Board.checkMove(position,row,col,board)) == -1)
                        ToastMessage("Illegal move");
                    else{
                        MoveCount++;
                        board[move] = cell;
                        board[position] = null;
                        Board.ShowBoardStatus(board);
                        notifyDataSetChanged();
                        if (Board.checkBoard(board,size)){
                            End =  System.currentTimeMillis();
                            Log.i("Game status","Finish");
                            Long time = End-Start;
                            listener.GameFinished(time.intValue(),MoveCount);
                        }
                    }
                }
            });

        }
    }

    public interface BoardCellAdapterListener{
        void GameFinished(int time ,int move);
    }

    public void ToastMessage(CharSequence  MSG){
        Toast msg = Toast.makeText(context, MSG,Toast.LENGTH_SHORT);
        msg.show();
    }
}
