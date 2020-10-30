package com.example.xisd_5th_attempt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trustee  extends AppCompatActivity {
    ListView lwAnnouncements;
    Button btnSomething, btnSearch, btnViewResidents, btnHomeFromViewRes;
    private String m_Text = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trustee_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        lwAnnouncements = findViewById(R.id.lwAnnouncements);
        btnSomething = findViewById(R.id.btnAddAnnouncement);
        btnSearch = findViewById(R.id.btnSearch);
        btnViewResidents = findViewById(R.id.btnViewResidents);
        btnHomeFromViewRes = findViewById(R.id.btnHomeFromViewRes);

        String[] fruits = new String[]{
                "Number 1",
                "Number 2"
        };

        // Create a List from String Array elements
        final List<String> my_list = new ArrayList<String>(Arrays.asList(fruits));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, my_list);

        // DataBind ListView with items from ArrayAdapter
        lwAnnouncements.setAdapter(arrayAdapter);

        btnSomething.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add new Items to List
               /* my_list.add("Number 3");
                my_list.add("Number 4");

                arrayAdapter.notifyDataSetChanged();*/

                AlertDialog.Builder builder = new AlertDialog.Builder(Trustee.this);
                builder.setTitle("Title");

                // Set up the input
                final EditText input = new EditText(Trustee.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                /*
                 *
                 *
                 * Add a date selection for announcements
                 *
                 *
                 *
                 * */

                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        my_list.add(m_Text);

                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnViewResidents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.view_residents);
            }
        });
        /*btnHomeFromViewRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.trustee_home);
            }
        });*/
    }
}
