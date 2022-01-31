package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements MenuFrag.MenuFragListener,DifficultyFrag.DifficultyFragListener,ExitDialog.ExitDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MenuFrag frag = (MenuFrag)getSupportFragmentManager().findFragmentByTag("MENUFRAG");
        FragmentContainerView fragBContainer = findViewById(R.id.MainFragCon);
        if (frag != null) {
            getSupportFragmentManager().beginTransaction()
                    .show(frag)
                    .commit();
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.MainFragCon, MenuFrag.class,null, "MENUFRAG")
                    //	.addToBackStack(null)
                    .commit();
        }
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void setActionMenuFrag(String BTN) {
        DifficultyFrag frag;
        switch (BTN){
            case "Play":
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.MainFragCon, DifficultyFrag.class, null,"DifficultyFrag")
                        .addToBackStack("BBB")
                        .commit();
                getSupportFragmentManager().executePendingTransactions();
                frag = (DifficultyFrag)getSupportFragmentManager().findFragmentByTag("DifficultyFrag");
                break;
            case "Exit":
                showDialog();
                break;
            case "Score":
                break;

        }

    }

    @Override
    public void setActionDifficultyFrag(String BTN) {
        MenuFrag frag;
        switch (BTN){
            case "Easy":
                break;
            case "Normal":
                break;
            case "Hard":
                break;
            case "Back":
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.MainFragCon, MenuFrag.class, null,"MENUFRAG")
                        .addToBackStack("BBB")
                        .commit();
                getSupportFragmentManager().executePendingTransactions();
                frag = (MenuFrag)getSupportFragmentManager().findFragmentByTag("MENUFRAG");
                break;

        }
    }

    void showDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ExitDialog exitDialog = ExitDialog.newInstance("Closing the application");
        exitDialog.show(fm, "fragment_alert");
    }

    @Override
    public void onExitClick() {
        finish();
        System.exit(0);
    }
}