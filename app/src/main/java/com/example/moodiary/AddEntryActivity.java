package com.example.moodiary;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;


public class AddEntryActivity extends AppCompatActivity {
    //    private int activityIds[] = {
//            R.id.activity1,
//            R.id.activity2,
//            R.id.activity3,
//            R.id.activity4,
//            R.id.activity5,
//            R.id.activity6,
//            R.id.activity7,
//            R.id.activity8,
//            R.id.activity9};
    private int         chooseStatus[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private EditText    notes;
    private Button      btnAddPhoto;
    private ImageButton btnSave;
    private ImageView   btnMoodBack, chosenMood;
    private Uri    selectedImage;
    private int[]  currentMood;
    private String dayOfMood, timeOfMood;
    String linkimg = "", listAct;
    int completeupload = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addentry);

        LinearLayout activity1 = findViewById(R.id.activity1);
        LinearLayout activity2 = findViewById(R.id.activity2);
        LinearLayout activity3 = findViewById(R.id.activity3);
        LinearLayout activity4 = findViewById(R.id.activity4);
        LinearLayout activity5 = findViewById(R.id.activity5);
        LinearLayout activity6 = findViewById(R.id.activity6);
        LinearLayout activity7 = findViewById(R.id.activity7);
        LinearLayout activity8 = findViewById(R.id.activity8);
        LinearLayout activity9 = findViewById(R.id.activity9);
        LinearLayout edit_new  = findViewById(R.id.edit_new);

        notes       = findViewById(R.id.edtxt_Notes);
        btnAddPhoto = findViewById(R.id.btnAddPhoto);
        btnSave     = findViewById(R.id.btnSave);
        btnMoodBack = findViewById(R.id.btnBack2);
        chosenMood  = findViewById(R.id.chosenMood);

        btnMoodBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InitiateMood.class));

            }
        });

        currentMood = getIntent().getIntArrayExtra("chosenMood");
        dayOfMood   = getIntent().getStringExtra("dayOfMood").toString();
        timeOfMood  = getIntent().getStringExtra("timeOfMood").toString();

        chosenMood.setImageResource(MoodInfor.moods_thumbnail[currentMood[0]][currentMood[1]]);

//        if(currentMood.equals("Amazing"))
//            chosenMood.setImageResource(R.drawable.mood_amazing);
//        if(currentMood.equals("Happy"))
//            chosenMood.setImageResource(R.drawable.mood_happy);
//        if(currentMood.equals("Ok"))
//            chosenMood.setImageResource(R.drawable.mood_ok);
//        if(currentMood.equals("Sad"))
//            chosenMood.setImageResource(R.drawable.mood_sad);
//        if(currentMood.equals("Awful"))
//            chosenMood.setImageResource(R.drawable.mood_awful);

        activity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity1, 0);
            }
        });
        activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity2, 1);
            }
        });
        activity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity3, 2);
            }
        });
        activity4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity4, 3);
            }
        });
        activity5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity5, 4);
            }
        });
        activity6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity6, 5);
            }
        });
        activity7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity7, 6);
            }
        });
        activity8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity8, 7);
            }
        });
        activity9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity9, 8);
            }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEntryToDatabase dtb = new AddEntryToDatabase();
                listAct = "";
                for (int i = 0; i < chooseStatus.length; i++) {
                    if (chooseStatus[i] == 1)
                        listAct += i + 1 + " ";
                }

                Entry newEntry = new Entry(listAct, notes.getText().toString(), dayOfMood, timeOfMood, MoodInfor.moods_type[currentMood[0]][currentMood[1]]);

                // get date of entry to name for image
                String imgName = newEntry.getDateOfMood();
                imgName = imgName.replace("/", "");
                imgName = imgName.replace(":", "");
                imgName = imgName.replace(" ", "");

                //UPLOAD IMAGE
                if (selectedImage != null) {
                    StorageReference fileReference = FirebaseStorage.getInstance().getReference("images").child(imgName + "." + getFileExtension(selectedImage));
                    fileReference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    linkimg = uri.toString();
                                    Entry newEntry1 = new Entry(listAct, notes.getText().toString(), dayOfMood, timeOfMood, MoodInfor.moods_type[currentMood[0]][currentMood[1]], linkimg);
                                    //UPLOAD DATA OF ENTRY
                                    dtb.add(newEntry1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(AddEntryActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class));

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull @NotNull Exception e) {
                                            Toast.makeText(AddEntryActivity.this, "Add Unsuccess", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else {
                    //UPLOAD DATA OF ENTRY
                    dtb.add(newEntry).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddEntryActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(AddEntryActivity.this, "Add Unsuccess", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            //hide the button after choosing an image
            btnAddPhoto.setVisibility(View.GONE);

            //show selected image
            selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.selectedImage);
            imageView.setImageURI(selectedImage);
        }
    }

    private void clickActivity(LinearLayout activity, int pos) {
        RelativeLayout layout        = (RelativeLayout) activity.getChildAt(0);
        ImageView      circularShape = (ImageView) layout.getChildAt(0);
        ImageView      activityImage = (ImageView) layout.getChildAt(1);
        if (chooseStatus[pos] == 0) {
            chooseStatus[pos] = 1;
            circularShape.setImageResource(R.drawable.circular_shape);
            ImageViewCompat.setImageTintList(activityImage, ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        } else if (chooseStatus[pos] == 1) {
            chooseStatus[pos] = 0;
            circularShape.setImageResource(R.drawable.circular_shape_none);
            ImageViewCompat.setImageTintList(activityImage, ColorStateList.valueOf(Color.parseColor("#32CD32")));
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR   = getContentResolver();
        MimeTypeMap     mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}


