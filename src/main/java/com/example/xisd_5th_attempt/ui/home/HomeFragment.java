package com.example.xisd_5th_attempt.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.xisd_5th_attempt.Login;
import com.example.xisd_5th_attempt.R;
import com.example.xisd_5th_attempt.Register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    FirebaseDatabase fData;
    DatabaseReference myRef;
    FirebaseAuth fAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private String UserID;
    private static final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    Button btnLogout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        fAuth = FirebaseAuth.getInstance();
        fData = FirebaseDatabase.getInstance();
        myRef = fData.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        fAuth = FirebaseAuth.getInstance();
        btnLogout = root.findViewById(R.id.btnLogout);
        fs = FirebaseFirestore.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                // Check if user is signed in (non-null)
                FirebaseUser currentUser = fAuth.getCurrentUser();
                if(currentUser != null){
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());
                    Toast toast = Toast.makeText(getContext(), "signed in now", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    Log.d(TAG, "onAuthStateChanged:Signed_out");
                    //Toast toast = Toast.makeText(Profile.this, "signed out now",Toast.LENGTH_LONG);
                    //toast.show();
                }
            }
        };
        btnLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fAuth.signOut();
                startActivity(new Intent(getActivity(), Login.class));
            }
        });


        return root;
    }
}