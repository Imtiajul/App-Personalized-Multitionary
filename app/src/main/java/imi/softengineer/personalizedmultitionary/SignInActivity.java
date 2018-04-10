package imi.softengineer.personalizedmultitionary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    EditText editUserEmail, editUserPassword;
    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    //Text Button
    TextView btReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        //getActionBar().hide(); //for higher sdk upper 11
        setContentView(R.layout.activity_signin);
        //For fullscreen window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                }

            }
        };



        editUserEmail = findViewById(R.id.edtEmail);
        editUserPassword = findViewById(R.id.edtPassword);
        progressDialog = new ProgressDialog(this);

        //Text Button
        btReg = findViewById(R.id.txtButRegister);
        btReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, RegistrationActivity.class));
            }
        });
    }//onCreate

    public void skip(View view) {

        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
        //intent.putExtra("input_from_SignInActivity", editUserEmail.getText().toString());
        startActivity(intent);
        Toast.makeText(SignInActivity.this, "You are login in as a Guest", Toast.LENGTH_LONG).show();
    }


    public void butSignIn(View view) {

        String email, password;
        email = editUserEmail.getText().toString().trim();
        password = editUserPassword.getText().toString().trim();

        //validation start for login info
        boolean error;
        error = false;
        if (email.isEmpty()) {
            editUserEmail.setError("Name is required");
            error = true;
        } else if (email.length() < 4) {
            editUserEmail.setError("Name should be at least 3 char");
            error = true;
        }

        if (password.isEmpty()) {
            editUserPassword.setError("Password is required");
            error = true;
        } else if (password.length() < 6) {
            editUserPassword.setError("Minimum password length should be 6");
            error = true;
        }

        if (error) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        } else {
            //userProgressBar.setVisibility(View.VISIBLE);
            progressDialog.setMessage("Login please wait...");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //userProgressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                                //updateUI(user);
                            } else {
                                progressDialog.dismiss();
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Check username & password",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
        }//login code end


    }// end of SIGN IN Button Function

  /*  private void updateUI(FirebaseUser currentUser) {
        *//*if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
        }
        else {

        }*//*
    }*/


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);

        //updateUI(currentUser);
    }

}//super class

