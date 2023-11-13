package com.example.note_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> listNoteItems = new ArrayList<>();
    static ArrayAdapter<String> adapter;
    ListView lvNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvNotes = findViewById(R.id.lvNotes);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNoteItems);
        this.lvNotes.setAdapter(adapter);

        lvNotes.setOnItemLongClickListener((adapterView, view, i, l) -> {
            final int itemToDelete =i;
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this note?")
                    .setPositiveButton("Yes", (dialogInterface, i1) -> {
                        listNoteItems.remove(itemToDelete);
                        adapter.notifyDataSetChanged();

                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.NOTES_FILE, Context.MODE_PRIVATE );
                        HashSet<String> set = new HashSet<>(MainActivity.listNoteItems);
                        sharedPreferences.edit().putStringSet(Constants.NOTES_ARRAY_KEY, set).apply();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_options_menu, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Current
        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);

        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);

        if(savedSet != null) {
            listNoteItems.clear();
            listNoteItems.addAll(savedSet);
            adapter.notifyDataSetChanged();
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_note:
                Intent i = new Intent(this, com.example.note_app.AddNoteActivity.class);
                startActivity(i);
                return true;
            case R.id.remove_note:
                Intent r = new Intent(this, com.example.note_app.DeleteNoteActivity.class);
                startActivity(r);
                return true;
            case R.id.update_note:
                Toast.makeText(getApplicationContext(), R.string.msg_updated_clicked, Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


}