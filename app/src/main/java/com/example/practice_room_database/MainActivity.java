package com.example.practice_room_database;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NoteListAdapter.OnDeleteClickListener {

    private  String TAG = this.getClass().getSimpleName();
    private  static int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public  static int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private NoteViewModel noteViewModel;
    private Button data_input_btn;
    private RecyclerView recyclerView;
    private NoteListAdapter noteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data_input_btn = findViewById(R.id.input_btn);
        recyclerView = findViewById(R.id.recycler_view);

        noteListAdapter = new NoteListAdapter(this,this);
        recyclerView.setAdapter(noteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data_input_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NewNoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getmAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteListAdapter.setNotes(notes);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){

            //Code to insert note
            final String note_id = UUID.randomUUID().toString();
            Note note = new Note(note_id, data.getStringExtra(NewNoteActivity.NOTE_ADDED));
            noteViewModel.insert(note);

            Toast.makeText(getApplicationContext(),R.string.saved,Toast.LENGTH_LONG).show();
        } else if(requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            //code to update note

            Note note = new Note(data.getStringExtra(EditNoteActivity.NOTE_ID),
                    data.getStringExtra(EditNoteActivity.UPDATE_NOTE));
            noteViewModel.update(note);

            Toast.makeText(getApplicationContext(),R.string.updated,Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),R.string.not_saved,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void OnDeleteClickListener(Note myNote) {
        //Code for Delete operation
        noteViewModel.delete(myNote);
    }
}