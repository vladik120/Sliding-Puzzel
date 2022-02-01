package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MenuFrag.MenuFragListener,DifficultyFrag.DifficultyFragListener,ExitDialog.ExitDialogListener {
    static boolean PreferenceOpen = false;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.defult_setings,menu);
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.MI_defultSetings:
                if(!MainActivity.PreferenceOpen) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, new MySettingsFragment())
                            .addToBackStack(null)
                            .commit();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
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

    public static class MySettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
        private final int MinSize = 3;
        private final int MaxSize = 16;


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            MainActivity.PreferenceOpen = true;
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceManager.getDefaultSharedPreferences(this.getContext()).registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            view.setBackgroundColor(getResources().getColor(android.R.color.white));
            UpdateBoardSizeText(this.getPreferenceManager().getSharedPreferences());
            return view;
        }



        @Override
        public void onStop() {
            MainActivity.PreferenceOpen = false;
            super.onStop();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            String sizeString = sharedPreferences.getString(key,"a");
            EditTextPreference editTextKey = (EditTextPreference)findPreference(key);
            int size;
            try {
                size = Integer.parseInt(sizeString);
                if (size>=MaxSize || size<MinSize){
                    ToastMessage("the "+key+" not right");
                    sharedPreferences.edit().putString(key,"4").commit();
                    editTextKey.setText("4");
                }

            }catch (NumberFormatException e){
                ToastMessage("the "+key+" is not a number");
                sharedPreferences.edit().putString(key,"4").commit();
                editTextKey.setText("4");
            }
            UpdateBoardSizeText(sharedPreferences);
        }

        public void UpdateBoardSizeText(SharedPreferences sharedPreferences){
            String row = sharedPreferences.getString("board_row","4");
            String col = sharedPreferences.getString("board_col","4");
            Preference BoardSize = findPreference("Borad_size");
            BoardSize.setTitle("Board size is "+row+"X"+col);

        }



        public void ToastMessage(CharSequence  MSG){
            Toast msg = Toast.makeText(this.getContext(),MSG,Toast.LENGTH_SHORT);
            msg.show();
        }
    }
}