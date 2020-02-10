package com.example.mynotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText noteText;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteText = findViewById(R.id.editText2);
    }

    public void saveNote(View view) {
        message = noteText.getText().toString();
    }

    public void viewNotes(View view) {
        Intent intent = new Intent(this, NoteList.class);
        intent.putExtra("noteText", message);
        startActivity(intent);
    }
}
