package com.example.xisd_5th_attempt;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xisd_5th_attempt.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText txtName, txtSurname, txtEmail, txtPassword, txtUnitNum;
    Button btnRegister;
    TextView txtLoginHere;

    FirebaseAuth fAuth;
    ProgressBar progressBar;
    DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseDatabase;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        txtName = findViewById(R.id.txtName);
        txtSurname = findViewById(R.id.txtSurname);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtLoginHere = findViewById(R.id.txtLoginHere);
        txtUnitNum = findViewById(R.id.txtUnitNum);

        fAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        progressBar = findViewById(R.id.pbCalories);

        //If user already has an account
       /* if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }*/

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                //setting constraints........
                if(TextUtils.isEmpty(email)) {
                    txtEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    txtPassword.setError("Password is required.");
                    return;
                }
                if(password.length()<5) {
                    txtPassword.setError("Password must contain 6 characters or more.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Registering the user in firebase..

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this, "User Successfully Created!", Toast.LENGTH_SHORT).show();
                            mDatabase = mFirebaseDatabase.getReference();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            String UserID = user.getUid();
                            //String Email = user.getEmail();

                            mDatabase.child("Users").child(UserID).child("Name").setValue(txtName.getText().toString());
                            mDatabase.child("Users").child(UserID).child("Surname").setValue(txtSurname.getText().toString());
                            mDatabase.child("Users").child(UserID).child("Email").setValue(txtEmail.getText().toString());
                            mDatabase.child("Users").child(UserID).child("Unit Number").setValue(txtUnitNum.getText().toString());

                            /*
                             *Create Condition here to see who logs in
                             */
                            startActivity(new Intent(getApplicationContext(), HomeFragment.class));

                            /*
                            */
                        }
                        else
                        {
                            Toast.makeText(Register.this, "ERROR!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        txtLoginHere.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), Login.class));
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);    //calls the transition type set in 'anim'
            }
        });
    }
}
