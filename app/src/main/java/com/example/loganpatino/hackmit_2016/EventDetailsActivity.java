package com.example.loganpatino.hackmit_2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nameText = (TextView)findViewById(R.id.name);
        TextView descriptionText = (TextView)findViewById(R.id.description);
        TextView eventTypeText = (TextView)findViewById(R.id.type);

        Intent intent = getIntent();
        nameText.setText(intent.getStringExtra("name"));
        descriptionText.setText(intent.getStringExtra("description"));
        eventTypeText.setText(intent.getStringExtra("eventType"));
    }

}
