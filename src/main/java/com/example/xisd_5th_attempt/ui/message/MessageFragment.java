package com.example.xisd_5th_attempt.ui.message;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.xisd_5th_attempt.R;
import com.example.xisd_5th_attempt.Trustee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.common.internal.service.Common.API;

public class MessageFragment extends Fragment {

    private MessageViewModel messageViewModel;

    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;
    String TrusteeID, UserID, item, ResID, SelectedTrusteeID, Name, Surname;;
    int selectedPosition;
    FirebaseDatabase fData;
    DatabaseReference myRef;
    ListView lwTrusteesList;
    Button btnMessage ;
    TextView txtTrusteeName, txtTextContent;
    ImageButton btnSend;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messageViewModel =
                new ViewModelProvider(this).get(MessageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_message, container, false);
        fAuth = FirebaseAuth.getInstance();
        lwTrusteesList = root.findViewById(R.id.lwTrusteesList);
        fData = FirebaseDatabase.getInstance();
        myRef = fData.getReference();
        fs = FirebaseFirestore.getInstance();
        btnMessage =  root.findViewById(R.id.btnMessage);
        txtTrusteeName = root.findViewById(R.id.txtTrusteeName);
        btnSend = root.findViewById(R.id.btnSend);
        txtTextContent = root.findViewById(R.id.txtTextContent);

        final List<String> TempList = new ArrayList<>();

        final List<String> my_list = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>
                (getActivity(), android.R.layout.simple_list_item_1, my_list);

        lwTrusteesList.setAdapter(arrayAdapter);

        ResID = fAuth.getCurrentUser().getUid();


        lwTrusteesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
                selectedPosition = position;
                lwTrusteesList.setItemChecked(selectedPosition, true);
                btnMessage.setEnabled(true);
            }
        });
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTrusteeName.setText(item);

                Log.w(TAG, "..........--> " + fs.collection("Trustee").document());

                ViewFlipper vf = (ViewFlipper) root.findViewById(R.id.viewFlipper);
                vf.showNext();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fs.collectionGroup("Trustee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            Log.w(TAG, "..query........--> " + SelectedTrusteeID);
                            for (QueryDocumentSnapshot document : task.getResult()) {

                            }
                        } else {
                            Log.d(TAG, "Error getting documents...........: ", task.getException());
                        }
                    }
                });

                //Commented out for for testing something else

                /*Date currentTime = Calendar.getInstance().getTime();

                Map<String, Object> text_data = new HashMap<>();
                text_data.put("SenderID", ResID);
                text_data.put("Text", txtTextContent);
                text_data.put("Date", currentTime);


                fs.collection("Trustee").
                        document(ResID).collection("MessageID")
                        .add(text_data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });*/
            }
        });
        fs.collectionGroup("Trustee").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    my_list.clear();

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Name = document.getString("First Name");
                        Surname = document.getString("Surname");

                        SelectedTrusteeID = document.getId();

                        my_list.add(Name);
                        /*
                        TempList.add(SelectedTrusteeID);    //Where i last was (Maybe try 2d array)
                        */
                        arrayAdapter.notifyDataSetChanged();

                        Log.d(TAG, ".............--> " + document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents...........: ", task.getException());
                }
            }
        });
        return root;
    }
}