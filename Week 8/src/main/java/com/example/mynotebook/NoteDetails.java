package com.example.mynotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mynotebook.Models.Note;

public class NoteDetails extends AppCompatActivity {

    private Note note;
    private EditText headline;
    private EditText body;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", 0);
        note = MainActivity.noteList.get(noteId);

        headline = findViewById(R.id.editText);
        body = findViewById(R.id.editText2);

        headline.setText(note.getTitle());
        body.setText(note.getBody());
    }

    /**
     * Edit the note object and headline and switches back to the MainActivity.
     * @param view
     *
     */
    public void saveNote(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        note.setTitle(headline.getText().toString());
        note.setBody(body.getText().toString());
        startActivity(intent);
    }
}
