package com.example.moodiary;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Database {
    private final DatabaseReference databaseReference;
    public Database(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Entry.class.getSimpleName());
    }

    public Task<Void> add(Entry ent){
        return databaseReference.push().setValue(ent);
    }
}
