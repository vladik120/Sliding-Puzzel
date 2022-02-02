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


public class MenuFrag extends Fragment {

    Button Btn_Play;
    Button Btn_Score;
    Button Btn_Exit;
    MenuFragListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Btn_Play = ((Button)view.findViewById(R.id.BTN_Play));;
        Btn_Score = ((Button)view.findViewById(R.id.BTN_Score));;
        Btn_Exit = ((Button)view.findViewById(R.id.BTN_Exit));;

        Btn_Play.setOnClickListener(new Play());
        Btn_Exit.setOnClickListener(new Exit());

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            this.listener = (MenuFragListener)context;
        }catch(ClassCastException e){
            throw new ClassCastException("the class " +
                    context.getClass().getName() +
                    " must implements the interface 'MenuFragListener'");
        }
        super.onAttach(context);
    }

    private class Play implements View.OnClickListener {
        public void onClick(View arg0) {
            listener.setActionMenuFrag("Play");
        }
    }

    private class Exit implements View.OnClickListener {
        public void onClick(View arg0) {
            listener.setActionMenuFrag("Exit");
        }
    }

    public interface MenuFragListener{
        public void setActionMenuFrag(String BTN);
    }
}