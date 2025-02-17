package com.example.test_app_01;

import static com.example.test_app_01.signup.checkField;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.app.DatePickerDialog;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class roombooking extends AppCompatActivity {
    private EditText ETXT_Username;
    private EditText ETXT_Email;

    public EditText ETXT_Desc;

    private EditText ETXT_RoomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_roombooking);


        EditText ETXT_DateIn = findViewById(R.id.ETXT_DateIn);

        ETXT_DateIn.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
                // Formatting the selected date as DD/MM/YYYY
                String formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                ETXT_DateIn.setText(formattedDate);
            }, year, month, day);

            datePickerDialog.show();
        });

        EditText ETXT_DateOut = findViewById(R.id.ETXT_DateOut);

        ETXT_DateOut.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
                // Formatting the selected date as DD/MM/YYYY
                String formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                ETXT_DateOut.setText(formattedDate);
            }, year, month, day);

            datePickerDialog.show();
        });


        EditText ETXT_RoomID = findViewById(R.id.ETXT_RoomID);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String roomID = extras.getString("roomID");
            ETXT_RoomID.setText(roomID);
            ETXT_RoomID.setEnabled(false);

        }


        ETXT_Username = findViewById(R.id.ETXT_Username);
        ETXT_Email = findViewById(R.id.ETXT_Email);

        // Get Firebase Auth instance
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String username = user.getDisplayName(); // Get username
            String email = user.getEmail(); // Get email

            // If username is null, set a default text
            if (username == null || username.isEmpty()) {
                username = "No username found";
            }

            ETXT_Username.setText(username);
            ETXT_Email.setText(email);

            ETXT_Username.setEnabled(false);
            ETXT_Username.setFocusable(false);
            ETXT_Username.setCursorVisible(false);

            ETXT_Email.setEnabled(false);
            ETXT_Email.setFocusable(false);
            ETXT_Email.setCursorVisible(false);

        }

        // Handle window insets (UI padding for system bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        EditText ETXT_Phone = findViewById(R.id.ETXT_Phone);
        Button BTN_CBook = findViewById(R.id.BTN_CBook);
        EditText ETXT_FName = findViewById(R.id.ETXT_FName);
        EditText ETXT_Email = findViewById(R.id.ETXT_Email);
        EditText ETXT_Username = findViewById(R.id.ETXT_Username);

        BTN_CBook.setOnClickListener(view -> {
            // Get user input
            String FullName = ETXT_FName.getText().toString().trim();
            String CheckInDate = ETXT_DateIn.getText().toString().trim();
            String ContactNumber = ETXT_Phone.getText().toString().trim();
            String Email = ETXT_Email.getText().toString().trim();
            String UserName = ETXT_Username.getText().toString().trim();
            String RoomID = ETXT_RoomID.getText().toString().trim();
            String CheckOutDate = ETXT_DateOut.getText().toString().trim();

            if (!checkField(ETXT_Phone)) {
                return;
            }
            if (!checkField(ETXT_FName)) {
                return;
            }
            if (!checkField(ETXT_DateIn)) {
                return;
            }
            if (!checkField(ETXT_DateOut)) {
                return;
            }


            // Initialize Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Create a booking object
            db.collection("1001")
                    .whereEqualTo("RoomId", RoomID)  // Find room by RoomId
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot document : queryDocumentSnapshots) {
                                String docId = document.getId();  // Get document ID
                                String status = document.getString("Status"); // Get room status

                                if (status != null && status.equals("Available")) {
                                    // Step 2: Room is available, proceed with booking
                                    Map<String, Object> booking = new HashMap<>();
                                    booking.put("CheckInDate", CheckInDate);
                                    booking.put("FullName", FullName);
                                    booking.put("contactNumber", ContactNumber);
                                    booking.put("Email", Email);
                                    booking.put("ClientId", UserName);
                                    booking.put("RoomId", RoomID);
                                    booking.put("CheckOutDate", CheckOutDate);

                                    db.collection("Bookings").add(booking)
                                            .addOnSuccessListener(documentReference -> {
                                                // Step 3: Update room status to "Booked"
                                                db.collection("1001").document(docId)
                                                        .update("Status", "Booked", "BookedBy", UserName)
                                                        .addOnSuccessListener(aVoid -> {
                                                            Toast.makeText(view.getContext(), "Booking Successful!", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        })
                                                        .addOnFailureListener(e -> {
                                                            Toast.makeText(view.getContext(), "Error updating room status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        });
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(view.getContext(), "Error adding booking: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
                                } else {
                                    // Step 4: Room is already booked, show an error message
                                    Toast.makeText(view.getContext(), "This room is already booked. Please choose another room.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(view.getContext(), "Room not found in '1001' collection.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(view.getContext(), "Error checking room availability: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

        });
    }

}