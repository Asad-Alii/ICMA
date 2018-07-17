package com.asad.icma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class TodaysClassActivity extends AppCompatActivity {

    LinearLayout room1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todaysclass);

        room1 = findViewById(R.id.room1_layout);
        room1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), StudentAttendanceActivity.class);
                startActivity(in);
            }
        });
    }
}
