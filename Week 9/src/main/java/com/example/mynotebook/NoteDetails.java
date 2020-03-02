package com.example.mynotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mynotebook.Models.Note;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NoteDetails extends AppCompatActivity {

    private Note note;
    private EditText headline;
    private EditText body;
    private int noteId;
    private String documentId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        documentId = note.getId();
    }

    /**
     * Edit the note object and headline, updates it in firebase and switches back to the MainActivity.
     * @param view
     *
     */
    public void saveNote(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        DocumentReference docRef = db.collection("notes").document(documentId);
        Map<String,String> map = new HashMap<>();

        note.setTitle(headline.getText().toString());
        note.setBody(body.getText().toString());

        map.put("Title", note.getTitle());
        map.put("Body", note.getBody());

        docRef.set(map);
        startActivity(intent);
    }

    /**
     * Deletes the note from firebase and redirects to the frontpage.
     * @param view
     */
    public void deleteNote(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        DocumentReference docRef = db.collection("notes").document(documentId);
        docRef.delete();
        startActivity(intent);
    }
}
