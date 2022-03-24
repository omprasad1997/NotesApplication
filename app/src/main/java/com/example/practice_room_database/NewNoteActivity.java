package com.example.practice_room_database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewNoteActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();
    public static final String NOTE_ADDED = "new_note";

    private EditText edit_new_note;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        edit_new_note = findViewById(R.id.etNewNote);
        btn_save = findViewById(R.id.bAdd);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();

                if (TextUtils.isEmpty(edit_new_note.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);
                } else {
                        String note = edit_new_note.getText().toString();
                        resultIntent.putExtra(NOTE_ADDED,note);
                        setResult(RESULT_OK,resultIntent);
                }

                finish();
            }
        });
    }
}