package com.example.moodiary;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Database {
    private final DatabaseReference ref;

    public Database(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        ref = db.getReference(Entry.class.getSimpleName());
    }

    public Task<Void> add(Entry ent){
        return ref.push().setValue(ent);
    }

//    public Task<Void> delete(Entry ent){
//
//    }
}
