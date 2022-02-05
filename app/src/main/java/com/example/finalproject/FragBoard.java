package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FragBoard extends Fragment  {
    FragBoard.FragBoardListener listener;
    private String difficulty;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        difficulty = this.getArguments().getString("difficulty");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        ((Button)view.findViewById(R.id.BTN_Back_board)).setOnClickListener(new Back());
        SharedPreferences sharedPref = view.getContext().getSharedPreferences(
                view.getContext().getResources().getString(R.string.preference_file_key), view.getContext().MODE_PRIVATE);

        int row = Integer.parseInt(sharedPref.getString("board_row","4"));
        // Lookup the recyclerview in activity layout
        RecyclerView rvBoard = (RecyclerView) view.findViewById(R.id.RV_Board_Game);

        // Create adapter passing in the sample user data
        BoardCellAdapter adapter = new BoardCellAdapter(view.getContext(),difficulty);
        // Attach the adapter to the recyclerview to populate items
        rvBoard.setAdapter(adapter);
        // Set layout manager to position the items
        rvBoard.setLayoutManager(new GridLayoutManager(view.getContext(), row));

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            this.listener = (FragBoardListener)context;
        }catch(ClassCastException e){
            throw new ClassCastException("the class " +
                    context.getClass().getName() +
                    " must implements the interface 'DifficultyFragListener'");
        }
        super.onAttach(context);
    }

    private class Back implements View.OnClickListener {
        public void onClick(View arg0) {
            listener.setBackFromGame();
        }
    }

    public interface FragBoardListener{
        public void setBackFromGame();
    }
}