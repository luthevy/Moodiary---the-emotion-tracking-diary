package com.example.moodiary;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class AddEntryToDatabase {
    private DatabaseReference databaseReference;

    public AddEntryToDatabase(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Entry.class.getSimpleName());
    }

    public Task<Void> add(Entry ent){
        return databaseReference.push().setValue(ent);
    }
}
