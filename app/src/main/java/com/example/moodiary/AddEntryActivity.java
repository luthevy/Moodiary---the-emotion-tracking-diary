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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;


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
    private int chooseStatus[] = {0,0,0,0,0,0,0,0,0};
    private EditText notes;
    private Button btnAddPhoto;
    private ImageButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        btnSave = findViewById(R.id.btnSave);

        activity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity1,0);
            }
        });
        activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity2,1);
            }
        });
        activity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity3,2);
            }
        });
        activity4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity4,3);
            }
        });
        activity5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity5,4);
            }
        });
        activity6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity6,5);
            }
        });
        activity7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity7,6);
            }
        });
        activity8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity8,7);
            }
        });
        activity9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity(activity9,8);
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

    private void clickActivity(LinearLayout activity, int pos){
        RelativeLayout layout = (RelativeLayout) activity.getChildAt(0);
        ImageView circularShape = (ImageView) layout.getChildAt(0);
        ImageView activityImage = (ImageView) layout.getChildAt(1);
        if (chooseStatus[pos] == 0) {
            chooseStatus[pos] = 1;
            circularShape.setImageResource(R.drawable.circular_shape);
            ImageViewCompat.setImageTintList(activityImage, ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        }
        else if (chooseStatus[pos] == 1) {
            chooseStatus[pos] = 0;
            circularShape.setImageResource(R.drawable.circular_shape_none);
            ImageViewCompat.setImageTintList(activityImage, ColorStateList.valueOf(Color.parseColor("#32CD32")));
        }
    }
}

