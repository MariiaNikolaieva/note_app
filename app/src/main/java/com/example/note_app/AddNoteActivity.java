package com.example.note_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AddNoteActivity extends AppCompatActivity {

    EditText edNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);

        this.edNote = findViewById(R.id.edNote);
    }


    public void onBtnSaveAndCloseClick(View view) {
        String noteToAdd = this.edNote.getText().toString();
        if(noteToAdd.length() != 0) {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);


            //Current
            SharedPreferences sharedPref = this.getSharedPreferences(com.example.note_app.Constants.NOTES_FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            Set<String> savedSet = sharedPref.getStringSet(com.example.note_app.Constants.NOTES_ARRAY_KEY, null);
            Set<String> newSet = new HashSet<>();
            if (savedSet != null) {
                newSet.addAll(savedSet);
            }
            newSet.add(noteToAdd);

            editor.putString(com.example.note_app.Constants.NOTE_KEY, noteToAdd);
            editor.putString(com.example.note_app.Constants.NOTE_KEY_DATE, formattedDate);
            editor.putStringSet(com.example.note_app.Constants.NOTES_ARRAY_KEY, newSet);
            editor.apply();
        }
        else{
            Toast.makeText(getApplicationContext(), "NO TEXT TO SAVE", Toast.LENGTH_LONG).show();
        }

        finish();
    }
}