package com.example.loganpatino.hackmit_2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;

public class CreateEventActivity extends AppCompatActivity {

    private Spinner mEventSpinner;
    private EditText mEventName;
    private EditText mEventDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEventSpinner = (Spinner)findViewById(R.id.event_types);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.event_types,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEventSpinner.setAdapter(adapter);

        mEventName = (EditText)findViewById(R.id.edit_name);
        mEventDescription = (EditText)findViewById(R.id.edit_description);

        Intent intent = this.getIntent();
        final String latitude = intent.getStringExtra("latitude");
        final String longitude = intent.getStringExtra("longitude");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFormFilledOut()) {
                    Event newEvent = new Event(latitude,
                            longitude,
                            mEventName.getText().toString(),
                            mEventSpinner.getSelectedItem().toString(),
                            mEventDescription.getText().toString());

                    Firebase ref = new Firebase("https://hackmit-2016-1742c.firebaseio.com/events");
                    ref.push().setValue(newEvent);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("success", true);
                    startActivity(intent);
                }
                else {
                    Snackbar.make(view, "Fill out the rest", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isFormFilledOut() {
        if (mEventName.getText().length() > 0 && mEventSpinner.getSelectedItem() != null) {
            return true;
        }
        return false;
    }

}
