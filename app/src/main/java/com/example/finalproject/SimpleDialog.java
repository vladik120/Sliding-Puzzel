package com.example.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;

import androidx.fragment.app.DialogFragment;

public class SimpleDialog extends DialogFragment {
    public SimpleDialog() {
        // Empty constructor required for DialogFragment
    }

    public static SimpleDialog newInstance(String title,String text) {
        SimpleDialog frag = new SimpleDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("text", text);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        String text = getArguments().getString("text");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("                  "+title);
        alertDialogBuilder.setMessage("                    "+text);
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return alertDialogBuilder.create();
    }
}
