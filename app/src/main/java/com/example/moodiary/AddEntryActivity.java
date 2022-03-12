package com.example.moodiary;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;


public class AddEntryActivity extends AppCompatActivity {
    private String[] chosenActivities;
    private EditText notes;
    private Button btnAddPhoto;

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
        LinearLayout edit_new = findViewById(R.id.edit_new);
        notes = findViewById(R.id.edtxt_Notes);
        btnAddPhoto = findViewById(R.id.btnAddPhoto);

        activity1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_BUTTON_PRESS){
                    RelativeLayout layout = (RelativeLayout) activity1.getChildAt(0);
                    ImageView circularShape = (ImageView) layout.getChildAt(0);
                    ImageView activityImage = (ImageView) layout.getChildAt(1);

                    circularShape.setBackgroundResource(R.drawable.circular_shape);
                    ImageViewCompat.setImageTintList(activityImage, ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                }
                return true;
            }
        });
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            //hide the button after choosing an image
            btnAddPhoto.setVisibility(View.GONE);

            //show selected image
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.selectedImage);
            imageView.setImageURI(selectedImage);
        }

    }
}


