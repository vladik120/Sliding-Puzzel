package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DifficultyFrag extends Fragment {
    DifficultyFragListener listener;
    Button Btn_Easy;
    Button Btn_Normal;
    Button Btn_Hard;
    Button Btn_Back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_difficulty, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Btn_Easy = ((Button)view.findViewById(R.id.BTN_Easy));
        Btn_Normal = ((Button)view.findViewById(R.id.BTN_Normal));
        Btn_Hard = ((Button)view.findViewById(R.id.BTN_Hard));
        Btn_Back = ((Button)view.findViewById(R.id.BTN_Back));

        Btn_Back.setOnClickListener(new Back());

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            this.listener = (DifficultyFragListener)context;
        }catch(ClassCastException e){
            throw new ClassCastException("the class " +
                    context.getClass().getName() +
                    " must implements the interface 'DifficultyFragListener'");
        }
        super.onAttach(context);
    }

    private class Back implements View.OnClickListener {
        public void onClick(View arg0) {
            listener.setActionDifficultyFrag("Back");
        }
    }

    public interface DifficultyFragListener{
        public void setActionDifficultyFrag(String BTN);
    }
}