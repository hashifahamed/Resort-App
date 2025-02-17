package com.example.test_app_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    private EditText etxt_Email, etxt_Password, txt_Username;
    private TextView txt_btnLogin;
    private Button btn_Register;

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        etxt_Email = findViewById(R.id.ETXT_Email);
        etxt_Password = findViewById(R.id.ETXT_Password);
        txt_Username = findViewById(R.id.ETXT_Username);
        btn_Register = findViewById(R.id.BTN_Register);
        txt_btnLogin = findViewById(R.id.TXT_btnLogin);

        // Initialize Firebase instances
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        // Navigate to login screen
        txt_btnLogin.setOnClickListener(view -> {
            Intent moveLogin = new Intent(getApplicationContext(), login.class);
            startActivity(moveLogin);
        });

        // Register button click event
        btn_Register.setOnClickListener(view -> {
            String emailVal = etxt_Email.getText().toString().trim();
            String passVal = etxt_Password.getText().toString().trim();
            String nameVal = txt_Username.getText().toString().trim();

            // Validate input fields
            if (!checkField(etxt_Email) || !checkField(txt_Username) || !checkField(etxt_Password)) {
                return;
            }

            // Create user in Firebase Authentication
            fAuth.createUserWithEmailAndPassword(emailVal, passVal)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser user = fAuth.getCurrentUser();
                        if (user != null) {
                            // Update Firebase Authentication profile with username
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nameVal) // ✅ Set username in Firebase Authentication
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(profileTask -> {
                                        if (profileTask.isSuccessful()) {
                                           //Toast.makeText(signup.this, "", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            // Store user details in Firestore
                            DocumentReference df = fStore.collection("Users_Details").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("EmailAddress", emailVal);
                            userInfo.put("Password", passVal);
                            userInfo.put("Username", nameVal);  //  Save username in Firestore
                            userInfo.put("isAdmin", "0");

                            df.set(userInfo)
                                    .addOnSuccessListener(aVoid -> Toast.makeText(signup.this, "Account Created!", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(signup.this, "Failed to Save User Info", Toast.LENGTH_SHORT).show());

                            // Redirect to login screen
                            Intent moveLogin = new Intent(getApplicationContext(), login.class);
                            startActivity(moveLogin);
                            finish();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(signup.this, "Failed to create an Account", Toast.LENGTH_SHORT).show());
        });
    }

    // Method to validate input fields

        public static boolean checkField(EditText field) {
            if (field.getText().toString().trim().isEmpty()) {
                field.setError("Required Field");
                return false;
            }
            return true;
        }

}
