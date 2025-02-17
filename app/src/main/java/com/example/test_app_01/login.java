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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {

    EditText etxt_Email , etxt_Password;
    TextView txt_btnRegister;
    Button btn_Login ;

    Boolean isValid = false;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etxt_Email = findViewById(R.id.ETXT_Email);
        etxt_Password = findViewById(R.id.ETXT_Password);
        txt_btnRegister = findViewById(R.id.TXT_btnRegister);
        btn_Login = findViewById(R.id.BTN_Login);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        txt_btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent moveRegister = new Intent(getApplicationContext(), signup.class); // Going to next page we are using the word INTENT
                startActivity(moveRegister);

            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValid = true;
                checkFiled(etxt_Email);
                checkFiled(etxt_Password);

                String emailVal = etxt_Email.getText().toString();
                String passVal = etxt_Password.getText().toString();

                if (isValid){

                    fAuth.signInWithEmailAndPassword(emailVal ,passVal).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();

                            checkUserAccess(authResult.getUser().getUid());



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }

        });
    }

    public boolean checkFiled(EditText ex){

        if(ex.getText().toString().isEmpty()){
            ex.setError("Required Field");
            isValid = false;

        } else {
            isValid = true ;
        }
        return isValid;
    }


    public void checkUserAccess(String uid) {
        DocumentReference df = fStore.collection("Users_Details").document(uid);

        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                // Get the value of the "isAdmin" field
                String isAdmin = documentSnapshot.getString("isAdmin");

                // Check if the value of "isAdmin" is "1" (admin) or "0" (regular user)
                if (isAdmin != null) {
                    if (isAdmin.equals("1")) {
                        // If the user is an admin, navigate to Admin Dashboard
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();  // Close the login activity
                    } else if (isAdmin.equals("0")) {
                        // If the user is a regular user, navigate to User Dashboard
                        startActivity(new Intent(getApplicationContext(), Userdashboard.class));
                        finish();  // Close the login activity
                    }
                } else {
                    // If isAdmin is not found in the document, handle this case (maybe show an error message)
                    Toast.makeText(login.this, "Error: isAdmin not found", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle failure if getting the document fails
                Toast.makeText(login.this, "Error getting user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}