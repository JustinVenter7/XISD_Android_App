package com.example.xisd_5th_attempt;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText Email, Password;
    Button btnLogin;
    TextView txtNotRegYet;
    ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Email = (EditText) findViewById(R.id.txtEmail);
        Password = (EditText) findViewById(R.id.txtPassword);
        progressBar = findViewById(R.id.progressBarLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtNotRegYet = findViewById(R.id.txtNotRegYet);
        fAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                // Check if user is signed in (non-null)
                FirebaseUser currentUser = fAuth.getCurrentUser();
                if(currentUser != null){
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());
                    Toast.makeText(Login.this, "Signed in Successfully as: " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    //fAuth.signOut();
                }
                else{
                    Log.d(TAG, "onAuthStateChanged:Signed_out");
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                //setting constraints........
                if(TextUtils.isEmpty(email))
                {
                    Email.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Password.setError("Password is required.");
                    return;
                }
                if(password.length()<5)
                {
                    Password.setError("Password must contain 6 characters or more.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Authenticate the user
                fAuth.signInWithEmailAndPassword(email, password);
            }
        });

        txtNotRegYet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), Register.class));
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);    //calls the transition type set in 'anim'
            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        fAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        fAuth.removeAuthStateListener(mAuthListener);
    }
}
