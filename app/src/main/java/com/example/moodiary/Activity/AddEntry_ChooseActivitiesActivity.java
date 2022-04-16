package com.example.moodiary.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moodiary.Database;
import com.example.moodiary.Entry;
import com.example.moodiary.EntryActivitiesAdapter;
import com.example.moodiary.EntryActivity;
import com.example.moodiary.MoodInfo;
import com.example.moodiary.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class AddEntry_ChooseActivitiesActivity extends AppCompatActivity {
    private String[]  activityDefault_type      = {
            "cleaning", "cook", "date", "drawing",
            "eat", "family", "festival", "friend",
            "game", "gift", "music", "party",
            "reading", "relax", "shopping", "sleep",
            "sport", "study", "swim", "tv",
            "walk", "work"
    };
    private Integer[] activityDefault_thumbnail = {
            R.drawable.activity_cleaning, R.drawable.activity_cook, R.drawable.activity_date, R.drawable.activity_drawing,
            R.drawable.activity_eat, R.drawable.activity_family, R.drawable.activity_festival, R.drawable.activity_friend,
            R.drawable.activity_game, R.drawable.activity_gift, R.drawable.activity_music, R.drawable.activity_party,
            R.drawable.activity_reading, R.drawable.activity_relax, R.drawable.activity_shopping, R.drawable.activity_sleep,
            R.drawable.activity_sport, R.drawable.activity_study, R.drawable.activity_swim, R.drawable.activity_tv,
            R.drawable.activity_walk, R.drawable.activity_work
    };
    private boolean[]     chooseStatus              = new boolean[activityDefault_type.length];

    private GridView    activitiesGridView;
    private EditText    notes;
    private Button      btnAddPhoto;
    private ImageButton btnSave;
    private ImageView   btnMoodBack, chosenMood;
    private Uri    selectedImage;
    private int[]  currentMood;
    private String dayOfMood, timeOfMood;
    String linkimg = "", listAct = "";
    int completeupload = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry_choose_activities);

        activitiesGridView = findViewById(R.id.activitiesGridView);
        notes              = findViewById(R.id.edtxt_Notes);
        btnAddPhoto        = findViewById(R.id.btnAddPhoto);
        btnSave            = findViewById(R.id.btnSave);
        btnMoodBack        = findViewById(R.id.btnBack2);
        chosenMood         = findViewById(R.id.chosenMood);

        //-----------------------Show all activities-------------------------
        ArrayList<EntryActivity> activityArrayList = new ArrayList<EntryActivity>();
        for (int i = 0; i < activityDefault_type.length; i++) {
            activityArrayList.add(new EntryActivity(activityDefault_type[i], activityDefault_thumbnail[i]));
        }
        EntryActivitiesAdapter adapter = new EntryActivitiesAdapter(this, activityArrayList, chooseStatus);
        activitiesGridView.setAdapter(adapter);

        btnMoodBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddEntry_ChooseMoodsActivity.class));
            }
        });

        currentMood = getIntent().getIntArrayExtra("chosenMood");
        dayOfMood   = getIntent().getStringExtra("dayOfMood").toString();
        timeOfMood  = getIntent().getStringExtra("timeOfMood").toString();

        chosenMood.setImageResource(MoodInfo.moods_thumbnail[currentMood[0]][currentMood[1]]);

        btnAddPhoto.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database dtb = new Database();

                for (int i = 0; i < chooseStatus.length; i++) {
                    if (chooseStatus[i])
                        listAct += i + 1 + " ";
                }

                Entry newEntry = new Entry(
                        listAct,
                        notes.getText().toString(),
                        dayOfMood,
                        timeOfMood,
                        MoodInfo.moods_type[currentMood[0]][currentMood[1]]);

                // get date of entry to name for image
                String imgName = newEntry.getDateOfMood().replace("[ :/]", "");

                //UPLOAD IMAGE
                if (selectedImage != null) {
                    StorageReference fileReference = FirebaseStorage.getInstance().getReference("images").child(imgName + "." + getFileExtension(selectedImage));
                    fileReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    newEntry.setImgLink(uri.toString());
                                    dtb.add(newEntry).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(AddEntry_ChooseActivitiesActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull @NotNull Exception e) {
                                            Toast.makeText(AddEntry_ChooseActivitiesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else {
                    dtb.add(newEntry).addOnSuccessListener(unused -> {
                        Toast.makeText(AddEntry_ChooseActivitiesActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class));
                    }).addOnFailureListener(e -> {
                        Toast.makeText(AddEntry_ChooseActivitiesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            btnAddPhoto.setText("Edit Photo");

            //show or change selected image
            selectedImage = data.getData();
            ((ImageView) findViewById(R.id.selectedImage)).setImageURI(selectedImage);
        }
    }

//    private void clickActivity(LinearLayout activity, int pos) {
//        RelativeLayout layout        = (RelativeLayout) activity.getChildAt(0);
//        ImageView      circularShape = (ImageView) layout.getChildAt(0);
//        ImageView      activityImage = (ImageView) layout.getChildAt(1);
//        if (chooseStatus[pos] == 0) {
//            chooseStatus[pos] = 1;
//            circularShape.setImageResource(R.drawable.circular_shape);
//            ImageViewCompat.setImageTintList(activityImage, ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
//        } else if (chooseStatus[pos] == 1) {
//            chooseStatus[pos] = 0;
//            circularShape.setImageResource(R.drawable.circular_shape_none);
//            ImageViewCompat.setImageTintList(activityImage, ColorStateList.valueOf(Color.parseColor("#32CD32")));
//        }
//    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR   = getContentResolver();
        MimeTypeMap     mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}


