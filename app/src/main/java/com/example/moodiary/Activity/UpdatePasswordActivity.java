package com.example.moodiary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodiary.R;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText currentPassword, newPassword, newPasswordRetype;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        currentPassword   = findViewById(R.id.currentPassword);
        newPassword       = findViewById(R.id.newPassword);
        newPasswordRetype = findViewById(R.id.newPasswordRetype);
        btnUpdate         = findViewById(R.id.btnChange);

        btnUpdate.setOnClickListener(view -> {
            String cp = currentPassword.getText().toString(),
                    np = newPassword.getText().toString(),
                    npr = newPasswordRetype.getText().toString();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), cp);

            user.reauthenticate(credential)
                    .addOnSuccessListener(task -> {
                        if (np.isEmpty() || npr.isEmpty() || cp.isEmpty())
                            Toast.makeText(getApplicationContext(), "All fields must not be left empty", Toast.LENGTH_SHORT).show();
                        else if (!npr.equals(np))
                            Toast.makeText(getApplicationContext(), "Retyped password does not match", Toast.LENGTH_SHORT).show();
                        else
                            user.updatePassword(np)
                                    .addOnSuccessListener(task1 -> {
                                            Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), ShowEntriesActivity.class));
                                            finish();
                                    })
                                    .addOnFailureListener(task1 -> Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show());

                    })
                    .addOnFailureListener(runnable ->
                            Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show());
        });
    }
}