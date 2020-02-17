package com.example.mynotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mynotebook.Models.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    public static List<Note> noteList = new ArrayList<>();
    private ArrayAdapter<Note> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteList);
        listView.setAdapter(adapter);

        /**
         * Activates when an item in the ListView is clicked. the onItemClick will provide us with the index of the item (int i),
         * so I parse the integer to the getNoteDetails method.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getNoteDetails(i);
            }
        });
    }

    /**
     * This method runs a new intend and switches the screen to the NoteDetails Activity.
     * intent.putExtra provides the intend object with the id of the item, which is used to reference the Note object
     * in the noteList.
     * @param i
     */
    public void getNoteDetails(int i) {
        Intent intent = new Intent(this, NoteDetails.class);
        intent.putExtra("noteId", i);
        startActivity(intent);
    }

    /**
     * Creates a new Note object.
     * @param view
     */
    public void addNote(View view) {
        Note note = new Note();

        note.setId(noteList.size() + 1);
        note.setTitle("Headline " + (noteList.size() + 1));
        note.setBody("");

        noteList.add(note);
        adapter.notifyDataSetChanged();
    }

}
