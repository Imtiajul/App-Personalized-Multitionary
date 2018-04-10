package imi.softengineer.personalizedmultitionary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;


public class RegistrationActivity extends AppCompatActivity {

    EditText editUserName, editPhoneNumber, editEmail, editPassword;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();

        setContentView(R.layout.activity_registration);

        editUserName = findViewById(R.id.dEditUserName);
        editPhoneNumber = findViewById(R.id.dEditPhoneNumber);
        editEmail = findViewById(R.id.dEditEmail);
        editPassword = findViewById(R.id.dEditPassword);

        mAuth = FirebaseAuth.getInstance();


    }

    public void butRegistration(View view) {

        String name = editUserName.getText().toString().trim();
        String phoneNo = editPhoneNumber.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        final ProgressBar progressBar;
        progressBar = findViewById(R.id.progressbar);


        //validation
        boolean error = false;

        if (name.isEmpty()) {
            editUserName.setError("Name is required");
            error = true;
        } else if (name.length() < 4) {
            editUserName.setError("Name should be at least 3 char");
            error = true;
        }

        if (phoneNo.isEmpty()) {
            editPhoneNumber.setError("Phone number is missing");
            error = true;

        } else if ((phoneNo.length() == 11) || (phoneNo.length() == 14)) {
            if ((phoneNo.startsWith("015")) || (phoneNo.startsWith("017")) || (phoneNo.startsWith("018")) || (phoneNo.startsWith("019")) || phoneNo.startsWith("016")) {
                error = false;

            } else {
                editPhoneNumber.setError("Enter a valid operator");
                error = true;
            }

        } else {
            editPhoneNumber.setError("Phone no should be at least 11 digit");
            error = true;
        }

        if (email.isEmpty()) {
            editEmail.setError("Email is missing");
            //dEditEmail.requestFocus();
            error = true;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email");
            error = true;
        }


        if (password.isEmpty()) {
            editPassword.setError("Password is required");
            error = true;
        } else if (password.length() < 6) {
            editPassword.setError("Minimum password should be 6");
            error = true;
        }

        //validation complete
        //Firebase registration starts
        if (error) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);

                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(RegistrationActivity.this, "Registration Successful :)", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegistrationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


    }

    public void AlreadyRegistered(View view) {
        startActivity(new Intent(RegistrationActivity.this, SignInActivity.class));
    }
}
