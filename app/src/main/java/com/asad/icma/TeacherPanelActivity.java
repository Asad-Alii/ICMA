package com.asad.icma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class TeacherPanelActivity extends AppCompatActivity {

    LinearLayout attendance, todaysclass, studentattendance, classstatus;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherpanel);

        username = getIntent().getStringExtra("login_username");

        attendance = findViewById(R.id.attendance_layout);
        todaysclass = findViewById(R.id.todayclass_layout);
        studentattendance = findViewById(R.id.studentattendance_layout);
        classstatus = findViewById(R.id.classstatus_layout);

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(), TeacherDetailsActivity.class);
                in.putExtra("username", username);
                startActivity(in);
                finish();
            }
        });

        todaysclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(), TodaysClassActivity.class);
                startActivity(in);
                finish();
            }
        });

        studentattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(), StudentAttendanceActivity.class);
                startActivity(in);
                finish();
            }
        });

        classstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(), ClassStatusActivity.class);
                startActivity(in);
                finish();
            }
        });
    }
}
