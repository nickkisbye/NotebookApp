package com.example.mynotebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mynotebook.Models.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    public static List<Note> noteList = new ArrayList<>();
    private ArrayAdapter<Note> adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchNotes();
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
     * Fetches the notes from Firebase.
     */
    public void fetchNotes() {
        db.collection("notes").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            noteList.clear();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                Note note = new Note();
                                note.setId(documentSnapshot.getId());
                                note.setTitle(documentSnapshot.get("Title").toString());
                                note.setBody(documentSnapshot.get("Body").toString());
                                noteList.add(note);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.w("Error", task.getException());
                        }
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
     * Creates a new Note object. and adds it to FireBase.
     * @param view
     */
    public void addNote(View view) {
        DocumentReference docRef = db.collection("notes").document();
        Map<String,String> map = new HashMap<>();
        map.put("Title", "New note");
        map.put("Body", "New body");
        docRef.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("all", "added successfully");
            }
        });
        fetchNotes();
    }

}
